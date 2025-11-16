package ca.bigbigbai.onlineshopping.controller;

import ca.bigbigbai.onlineshopping.UserModel;
import ca.bigbigbai.onlineshopping.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Controller
public class UserDemoController {
    Map<Integer, UserModel> users = new HashMap<>();

    @Resource(name = "nobody")
    UserModel userNobody;

    @Autowired
    private JwtService jwtService;

    @ResponseBody
    @PostMapping("/users")
    public String addUser(@RequestParam("id") int id,
                          @RequestParam("name") String name,
                          @RequestParam("email") String email) {
        UserModel user = UserModel.builder()
                .id(id)
                .name(name)
                .email(email)
                .build();
        users.put(id, user);
        return "User Created";
    }

    @GetMapping("/users/{id}")
    public String getUser(@PathVariable("id") int id, Map<String, Object> model) {
        UserModel userz3 = users.getOrDefault(id, userNobody);
        model.put("user", userz3);
        String jwtToken = jwtService.encryptUser(userz3);
        model.put("jwtToken", jwtToken);
//        String fakeToken = "eyJhbGciOiJIUzI1NiJ9.eyJuYW1lIjoiejMzMzMzIiwiaWQiOjMsImVtYWlsIjoiejNAaG90bWFpbC5jb20iLCJpYXQiOjE3NjE4ODM4MTAsImV4cCI6MTc2NDU2MjIxMH0.D_5jJNt4k6nQ1S5cBHncgpakN8vaf4yMeadt57oJfDs";
        String name = jwtService.decryptUserName(jwtToken);
        model.put("jwtUserName", name);

        return "user_detail";
    }

    @ResponseBody
    @PutMapping("/users/{id}")
    public String updateUser(@PathVariable("id") int id, @RequestParam("email") String email) {
        UserModel user = users.getOrDefault(id, userNobody);
        user.setEmail(email);
        users.put(id, user);
        return "update success";
    }

}
