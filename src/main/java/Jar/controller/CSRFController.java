package Jar.controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.web.csrf.CsrfToken;

@RestController
public class CSRFController {
    
    @GetMapping("/csrf")//for postman and website tokens
    public CsrfToken csrf(CsrfToken token) {
        return token;
    }
}
