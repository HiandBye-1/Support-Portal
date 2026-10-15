package Jar.controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import Jar.service.NormalUserService;
import Jar.entities.NormalUser;
@RestController
public class NormalUserController {
    private final NormalUserService normalUserService;

    public NormalUserController(NormalUserService normalUSerService){
        this.normalUserService = normalUSerService;
    }


    @PostMapping("/user/register")//register users
    @ResponseStatus(HttpStatus.CREATED)
    public String registerUser(@RequestBody NormalUser user){
        normalUserService.register(user.getUsername(), user.getPassword());
        return "User registered successfully";
    }
}
