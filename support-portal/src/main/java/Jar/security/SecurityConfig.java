package Jar.security;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
//for the @
import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.http.HttpMethod;

@Configuration
public class SecurityConfig {
    @Bean
    public PasswordEncoder encryptedPassword(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilter(HttpSecurity http) throws Exception {//for the @post none restricted access
        http.authorizeHttpRequests(rules -> {rules.requestMatchers(HttpMethod.POST, "/user/register").permitAll(); 
                                             rules.requestMatchers(HttpMethod.GET, "/csrf").permitAll();
                                             rules.anyRequest().authenticated();
                                            });

        return http.build();
    }
}
