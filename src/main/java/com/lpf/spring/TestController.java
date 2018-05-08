package com.lpf.spring;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试Controller
 *
 * @author lipengfei
 * @create 2018-05-07 15:18
 **/
@RestController
public class TestController {


    @RequestMapping(value = "hello", method = RequestMethod.GET)
    public String say() {
        return "Hello Spring boot!";
    }
}
