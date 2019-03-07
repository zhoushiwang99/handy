package com.finals.handy.service;

import com.finals.handy.bean.Message;
import com.finals.handy.mapper.MessageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xiaoqiang
 * @date $(DATE)-$(TIME)
 */
@Service
public class MessageService {
    @Autowired
    MessageMapper messageMapper;

    //   fromId 发送 toId
    public Message SendMessage(Integer fromId, Integer toId, String text) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        String time = sdf.format(date);

        Message message = new Message(fromId, toId, text, 0, time);
        messageMapper.addMessage(message);
        return message;
    }

    //    查询聊天记录
    public List<Message> findHistoryMessages(Integer fromId, Integer toId, Integer n) {
        if (fromId > toId) {
            Integer t = toId;
            toId = fromId;
            fromId = t;
        }
        String from_toId = fromId.toString() + toId.toString();
        List<Message> messages = messageMapper.findMessageByfromId_ToId(from_toId, n);
        return messages;
    }

    //    查询未读的聊天记录
    public List<Message> findMessages_NoRead(Integer fromId, Integer toId) {
        return messageMapper.findMessages_NoRead(fromId,toId);
    }

    // 设置为已读
    public Integer setHaveRead(Integer fromId, Integer toId) {
        return messageMapper.setHaveRead(fromId, toId);
    }

    public boolean deleteMessage(Integer id) {
        return messageMapper.delete(id);
    }





//    查找未读消息的数量
    public Integer findMessageNum(Integer userId) {
        return messageMapper.findMessageNum(userId);
    }

    //    查找我未读消息的 人 的Id 和对应消息数量
    public Map<Integer,Integer> findIdAndMsgs(Integer userId) {
        Map<Integer, Integer> map = new HashMap<>();
        List<Integer> userIds = messageMapper.findFromId(userId);
        for (Integer fromId : userIds) {
            List<Message> msgs = messageMapper.findMessages_NoRead(fromId, userId);
            map.put(fromId, msgs.size());
        }

        return map;


    }
}
