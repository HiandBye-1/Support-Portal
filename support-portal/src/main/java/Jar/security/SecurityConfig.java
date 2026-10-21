package Jar.security;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
//for the @
import org.springframework.context.annotation.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
@Configuration
public class SecurityConfig {
    @Bean
    public PasswordEncoder encryptedPassword(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilter(HttpSecurity http, 
                                              DaoAuthenticationProvider provider,
                                              SecurityContextRepository securityContextRepository) throws Exception {
        http.authorizeHttpRequests(rules -> {rules.requestMatchers(HttpMethod.POST, "/user/register").permitAll(); //for the @post none restricted access
                                             rules.requestMatchers(HttpMethod.GET, "/csrf").permitAll();//csrf code for postman
                                             rules.requestMatchers(HttpMethod.POST, "/user/login").permitAll();//for the @post none restricted access
                                             rules.anyRequest().authenticated();
                                            });
        http.securityContext(context -> context.securityContextRepository(securityContextRepository));//use this repo for retrieving and saving
        return http.authenticationProvider(provider).build();//build the security chain starting from DAO
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


    @Bean
    public SecurityContextRepository securityContextRepository(){
        return new HttpSessionSecurityContextRepository();
    }
}
