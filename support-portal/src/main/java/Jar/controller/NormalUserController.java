package Jar.controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import Jar.service.NormalUserService;
import Jar.entities.NormalUser;
import Jar.dto.LoginRequest;
@RestController
public class NormalUserController {
    private final NormalUserService normalUserService;
    private final AuthenticationManager authenticationManager;

    public NormalUserController(NormalUserService normalUSerService,
                                AuthenticationManager authenticaionManager
    ){
        this.normalUserService = normalUSerService;
        this.authenticationManager = authenticaionManager;
    }


    @PostMapping("/user/register")//register users
    @ResponseStatus(HttpStatus.CREATED)
    public String registerUser(@RequestBody NormalUser user){
        normalUserService.register(user.getUsername(), user.getPassword());
        return "User registered successfully";
    }


    @PostMapping("/user/login")
    @ResponseStatus(HttpStatus.OK)
    public void loginUser(@RequestBody LoginRequest loginRequest){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
    }
}
