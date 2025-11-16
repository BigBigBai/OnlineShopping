package ca.bigbigbai.onlineshopping.controller;

import org.springframework.stereotype.Component;

@Component
public class UserService {
    AddService addService;

    public UserService(AddService addService) {
        this.addService = addService;
    }

    public int add5(int a, int b) {
        return addService.add(a, b) + 5;
    }
}
