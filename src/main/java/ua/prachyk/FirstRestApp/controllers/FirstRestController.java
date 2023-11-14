package ua.prachyk.FirstRestApp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController // @Controller + @ResponseBody над кожним методом
@RequestMapping("/api")
public class FirstRestController {
    @ResponseBody
    @GetMapping("/sayHello")
    public String sayHello(){
        return "hello world";
    }
}
