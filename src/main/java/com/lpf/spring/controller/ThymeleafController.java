package com.lpf.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * thymeleaf使用方式
 *
 * @author lipengfei
 * @create 2018-05-08 14:10
 **/
@Controller
@RequestMapping("thymeleaf")
public class ThymeleafController {


    @RequestMapping("test")
    public String test() {
        return "index";
    }
}
