package com.lpf.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

/**
 * 测试Controller
 *
 * @author lipengfei
 * @create 2018-05-07 15:18
 **/
@RestController
@RequestMapping("test")
public class TestController {

    @Value("${cupSize}")
    private String cupSize;

    @Value("${age}")
    private Integer age;

    @Value("${content}")
    private String content;

    @Autowired
    private GirlProperties girlProperties;

    @RequestMapping(value = "hello", method = RequestMethod.GET)
    public String hello() {
        return "Hello Spring boot!";
    }

    @RequestMapping(value = {"demo1","demo2"}, method = RequestMethod.GET)
    public String demo() {
        return "Hello Spring boot!";
    }

    @RequestMapping(value = "/prop", method = RequestMethod.GET)
    public String prop() {
        return cupSize + "---" + age + "\n" + content;
    }

    @RequestMapping(value = "/configProp", method = RequestMethod.GET)
    public String configProp() {
        return girlProperties.getCupSize();
    }

    @PostMapping(value = "query/{id}")
    // @RequestMapping(value = "/query/{id}", method = RequestMethod.Post)
    public String query(@PathVariable String id) {
        return "TestController.query | id=" + id;
    }

    @GetMapping(value = "login")
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String query(@RequestParam(value = "id",required = false,defaultValue = "0") Integer id) {
        return "TestController.login | id=" + id;
    }

    public static void main(String[] args) {

    }
}
