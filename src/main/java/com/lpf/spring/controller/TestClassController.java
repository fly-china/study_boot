package com.lpf.spring.controller;

import com.lpf.spring.jvm.JavaClassExecuter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintWriter;

/**
 * @author lipengfei
 * @create 2018-08-22 19:58
 **/
@RestController
@RequestMapping("testJvm")
public class TestClassController {


    @RequestMapping(value = "replace", method = RequestMethod.GET)
    public String hello(HttpServletResponse httpResponse) {

        try {
            InputStream is = new FileInputStream("/Users/ran/Desktop/TestJvmDemo.class");
            byte[] b = new byte[is.available()];
            is.read(b);
            is.close();

            PrintWriter out = httpResponse.getWriter();
//            out.println("---------------");

            out.println(JavaClassExecuter.exexute(b));
//            out.println("---------------");


        } catch (Exception e) {
            e.printStackTrace();
        }

        return "Hello Spring boot!";
    }
}
