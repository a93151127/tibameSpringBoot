package com.example.myweb.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.http.HttpRequest;

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

    @RequestMapping("/passingHello")
    public String passingHello(HttpServletRequest request){
        System.out.println("inside passingHello");
        request.setAttribute("hello", "世界和平");
        return "whoHello";
    }

    @RequestMapping("/qryById")
    public String qryById(){
        System.out.println("inside qryById");
        return "custqryid";
    }

    @RequestMapping(path = {"/qryByParameter"}, method = {RequestMethod.GET})
    public String qryParameter(@RequestParam(name = "customerid", required = false,
            defaultValue="00000") String cid){
        System.out.println("inside qryParameter");
        System.out.println("cid : " + cid);
        return "custqryid";
    }
}
