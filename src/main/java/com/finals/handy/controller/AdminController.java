package com.finals.handy.controller;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zsw
 */
@RestController
public class AdminController {


    @GetMapping("/admin/test")
    @RequiresRoles("admin")
    public Map<String,Object> getMessage(){
        System.out.println("admin message");
        Map<String,Object> map = new HashMap<>(16);
        map.put("msg","adminMsg");
        return map;
    }

}
