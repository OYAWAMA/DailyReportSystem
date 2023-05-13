package com.techacademy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TopController {

    @GetMapping("/")
    public String index() {
     // index.htmlに画面遷移
        return "index";
    }
}