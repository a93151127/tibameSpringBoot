package com.example.myweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/hello")
public class HelloController {
    public HelloController() {
        System.out.println("建立Hello Controller物件");
    }
    @RequestMapping("/sayHello")
    public String sayHello(){
        System.out.println("inside sayHello");
        return "hello";
    }
    @RequestMapping("/sayHello2")
    public String sayHello2(){
        System.out.println("inside sayHelloaaa");
        return "hello";
    }
}
