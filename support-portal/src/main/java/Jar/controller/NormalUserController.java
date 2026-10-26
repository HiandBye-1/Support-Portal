package Jar.controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import jakarta.servlet.http.*;

import Jar.service.NormalUserService;
import Jar.entities.NormalUser;
import Jar.dto.LoginRequest;
import Jar.dto.UserProfileResponse;
import Jar.service.AuthenticationService;

@RestController
public class NormalUserController {
    private final NormalUserService normalUserService;
    private final AuthenticationService authenticationService;

    public NormalUserController(NormalUserService normalUSerService,
                                AuthenticationService authenticationService
    ){
        this.normalUserService = normalUSerService;
        this.authenticationService = authenticationService;
    }


    @PostMapping("/user/register")//register users
    @ResponseStatus(HttpStatus.CREATED)
    public String registerUser(@RequestBody NormalUser user){
        normalUserService.register(user.getUsername(), user.getPassword());
        return "User registered successfully";
    }


    @PostMapping("/user/login")
    @ResponseStatus(HttpStatus.OK)
    public void loginUser(@RequestBody LoginRequest loginRequest, HttpServletRequest request, HttpServletResponse response) throws Exception{
        authenticationService.login(loginRequest, request, response);
    }


    //use to check for current user
    @GetMapping("/user/profile")
    @ResponseStatus(HttpStatus.OK)
    public UserProfileResponse getCurrentUserProfile(){
        return authenticationService.userProfile();
    }



}
