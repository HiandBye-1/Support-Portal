package Jar.service;

import java.util.ArrayList;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import Jar.dto.UserProfileResponse;

import Jar.exception.NotLogin;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.context.SecurityContextRepository;
import java.util.List;
import jakarta.servlet.http.*;
import Jar.dto.LoginRequest;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.AnonymousAuthenticationToken;

@Service
public class AuthenticationService {//everything that have context holder

    private final AuthenticationManager authenticationManager;
    private final SecurityContextRepository securityContextRepository;


    public AuthenticationService(AuthenticationManager authenticationManager,
                                 SecurityContextRepository securityContextRepository) {
        
        this.authenticationManager = authenticationManager;
        this.securityContextRepository = securityContextRepository;

    }

        public UserProfileResponse userProfile(){//return user name and roles
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if(authentication == null || !authentication.isAuthenticated() || authentication instanceof AnonymousAuthenticationToken) throw new NotLogin("Not logged in");

            List<String> roles = new ArrayList<>();
            //iterate through the user's roles
            for(GrantedAuthority authority : authentication.getAuthorities()){
                roles.add(authority.getAuthority());
            }

            UserProfileResponse user = new UserProfileResponse(authentication.getName(), roles );//create a new user object
            return user;

        }

        public void login(LoginRequest loginRequest, HttpServletRequest request, HttpServletResponse response){//login user
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
            SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
            securityContext.setAuthentication(authentication);
            SecurityContextHolder.setContext(securityContext);
            securityContextRepository.saveContext(securityContext, request, response);
        }





    
}
