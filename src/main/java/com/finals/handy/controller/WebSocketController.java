package com.finals.handy.controller;

import com.auth0.jwt.interfaces.Claim;
import com.finals.handy.bean.Message;
import com.finals.handy.constant.ResponseCode;
import com.finals.handy.service.MessageService;
import com.finals.handy.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xiaoqiang
 * @date $(DATE)-$(TIME)
 */
@Controller
@RequestMapping("/user/websocket")
public class WebSocketController {

    @Autowired
    private MessageService messageService;

    @RequestMapping(value = "/socket.do/{AccessToken}/{fromId}", method = RequestMethod.GET)
    public String toWebSocket(@PathVariable("AccessToken") String AccessToken,@PathVariable("fromId") String fromId, Model model) {
        model.addAttribute("AccessToken",AccessToken );
        model.addAttribute("fromId", fromId);
//        System.out.println(userId);
        // model.addAttribute("address","/javax/websocket");
        return "client";
    }


    //    获取未读消息数返回
    @ResponseBody
    @RequestMapping(value = "/getMessageNum", method = RequestMethod.GET)
    public Map<String, Object> getMessageNum(@RequestParam("AccessToken") String AccessToken) {

        System.out.println(AccessToken);
        Map<String, Claim> claimMap = JwtUtil.verifyAccessToken(AccessToken);
        String id = claimMap.get("userId").asString();
        Integer userId =Integer.parseInt(id);
        Integer num = messageService.findMessageNum(userId);


        Map<String, Object> map = new HashMap<>();
        map.put("num", num);
        map.put("code", String.valueOf(ResponseCode.REQUEST_SUCCEED.getValue()));
        return map;
    }

    //    查找我未读消息的 人 的Id 和对应消息数量
    @ResponseBody
    @RequestMapping(value = "/findIdAndNum", method = RequestMethod.GET)
    public Map<String, Object> findIdAndMsgs(@RequestParam("AccessToken") String AccessToken) {
//        Map<String, Claim> claimMap = JwtUtil.verifyAccessToken(AccessToken);
//        String id = claimMap.get("userId").asString();
        System.out.println(AccessToken);
        Integer userId = 2;//Integer.parseInt(id);
        List<Map<String, Integer>> list = new ArrayList<>();
        Map<Integer, Integer> idAndMsgs = messageService.findIdAndMsgs(userId);
        for (Integer key : idAndMsgs.keySet()) {
            Map<String, Integer> map = new HashMap<>();
            map.put("id", key);
            map.put("num", idAndMsgs.get(key));
            list.add(map);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("list", list);
        map.put("code", String.valueOf(ResponseCode.REQUEST_SUCCEED.getValue()));

        return map;
    }

    /**
     * 查看历史消息
     */

    @ResponseBody
    @RequestMapping(value = "/findHistoryMsgs", method = RequestMethod.GET)
    public Map<String,Object> findHistoryMsgs(@RequestParam("toId") Integer toId,@RequestParam("num") Integer num, @RequestParam("AccessToken") String AccessToken) {
//        Map<String, Claim> claimMap = JwtUtil.verifyAccessToken(AccessToken);
//        String id = claimMap.get("userId").asString();
        System.out.println(AccessToken);
        Integer fromId = 2;//Integer.parseInt(id);
        List<Message> messages = messageService.findHistoryMessages(fromId, toId, num);
        Map<String, Object> map = new HashMap<>();
        map.put("list", messages);
        map.put("code", String.valueOf(ResponseCode.REQUEST_SUCCEED.getValue()));
        return map;
    }

    @ResponseBody
    @RequestMapping(value = "/deleteMsg", method = RequestMethod.GET)
    public Map<String,Object> deleteMsg(Integer id) {
        messageService.deleteMessage(id);
        Map<String, Object> map = new HashMap<>();
        map.put("code", String.valueOf(ResponseCode.REQUEST_SUCCEED.getValue()));
        return map;
    }

}

