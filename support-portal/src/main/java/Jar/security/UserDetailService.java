package Jar.security;
import org.springframework.stereotype.Service;
import Jar.entities.NormalUser;
import Jar.repository.NormalUserRepository;
import org.springframework.security.core.userdetails.*;

@Service
public class UserDetailService implements UserDetailsService {
    private final NormalUserRepository ur;
    public UserDetailService(NormalUserRepository ur) {
        this.ur = ur;
    }


    //loads user by username
    //then loads user details(username, password, roles) for sping security
    public UserDetails loadUserByUsername(String username){ 
        NormalUser user = ur.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return User.withUsername(user.getUsername()).password(user.getPassword()).roles("NORMALUSER").build();
    }

}
