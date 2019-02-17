package com.finals.handy.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author zsw
 */
@RestController
public class UserController {

    @GetMapping("/guest/test")
    public Map<String,Object> getMessage(){
        return null;
    }
}
