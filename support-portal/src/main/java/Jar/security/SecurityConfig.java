package Jar.security;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
//for the @
import org.springframework.context.annotation.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
@Configuration
public class SecurityConfig {
    @Bean
    public PasswordEncoder encryptedPassword(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilter(HttpSecurity http, DaoAuthenticationProvider provider) throws Exception {//for the @post none restricted access
        http.authorizeHttpRequests(rules -> {rules.requestMatchers(HttpMethod.POST, "/user/register").permitAll(); 
                                             rules.requestMatchers(HttpMethod.GET, "/csrf").permitAll();
                                             rules.anyRequest().authenticated();
                                            });
        return http.authenticationProvider(provider).build();
    }

    @Bean //comparing password
    public DaoAuthenticationProvider daoAuthenticationProvider(UserDetailService userDetailsService, PasswordEncoder passwordEncoder){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    @Bean //initialize the authentication manager use Dao
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }
}
