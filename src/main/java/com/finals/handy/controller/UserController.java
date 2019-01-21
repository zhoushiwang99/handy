package com.finals.handy.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zsw
 */
@RestController
public class UserController {

    @GetMapping("/user/test")
//    @RequiresRoles("user")
    public Map<String,Object> getMessage(){
        System.out.println("user message...");
        Map<String,Object> map = new HashMap<>(16);
        map.put("msg","userMsg");
        return map;
    }

    @GetMapping("/guest/hello")
    public void hello(HttpServletResponse response){
        try {
            PrintWriter writer = response.getWriter();
            Map<String,Object> map = new HashMap<>();
            map.put("cpde","132");
            writer.write(map.toString());
            return;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
