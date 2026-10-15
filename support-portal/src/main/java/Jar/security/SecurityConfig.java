package Jar.security;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
//for the @
import org.springframework.context.annotation.*;

@Configuration
public class SecurityConfig {
    @Bean
    public PasswordEncoder encryptedPassword(){
        return new BCryptPasswordEncoder();
    }
}
