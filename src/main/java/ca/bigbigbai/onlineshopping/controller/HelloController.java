package ca.bigbigbai.onlineshopping.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    // 用Postman
    @PostMapping
    public String helloPost() {
        return "Hello World with Post";
    }

    @GetMapping
    public String helloGet() {
        return "Hello World with Get";
    }


    @GetMapping("/echo/{text}")
    public String echo(@PathVariable("text") String text) {
        return "Hello " + text;
    }


}
