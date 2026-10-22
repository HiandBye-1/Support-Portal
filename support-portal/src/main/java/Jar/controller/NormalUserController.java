package Jar.controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import jakarta.servlet.http.*;

import org.springframework.security.core.GrantedAuthority;
import java.util.*;

import Jar.service.NormalUserService;
import Jar.entities.NormalUser;
import Jar.dto.LoginRequest;
import Jar.dto.UserProfileResponse;
@RestController
public class NormalUserController {
    private final NormalUserService normalUserService;
    private final AuthenticationManager authenticationManager;
    private final SecurityContextRepository securityContextRepository;

    public NormalUserController(NormalUserService normalUSerService,
                                AuthenticationManager authenticaionManager,
                                SecurityContextRepository securityContextRepository
    ){
        this.normalUserService = normalUSerService;
        this.authenticationManager = authenticaionManager;
        this.securityContextRepository = securityContextRepository;
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
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(authentication);
        SecurityContextHolder.setContext(securityContext);
        securityContextRepository.saveContext(securityContext, request, response);
    }


    //use to check for current user
    @GetMapping("/user/profile")
    @ResponseStatus(HttpStatus.OK)
    public UserProfileResponse getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<String> roles = new ArrayList<>();
        //iterate through the user's roles
        for(GrantedAuthority authority : authentication.getAuthorities()){
            roles.add(authority.getAuthority());
        }
        UserProfileResponse user = new UserProfileResponse(authentication.getName(), roles );//create a new user object
        return user;
    }



}
