package ca.bigbigbai.onlineshopping.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping
    public String helloWorld() {
        return "Hello World";
    }

    @GetMapping("/echo/{text}")
    public String echo(@PathVariable("text") String text) {
        return "Hello, you just input: " + text;
    }
}
