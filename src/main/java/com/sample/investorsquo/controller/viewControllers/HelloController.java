package com.sample.investorsquo.controller.viewControllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    @GetMapping
    public String helloWorld() {
        return "index";
    }
}
