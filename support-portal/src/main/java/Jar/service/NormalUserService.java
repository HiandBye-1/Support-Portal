package Jar.service;
import org.springframework.stereotype.Service;
import Jar.repository.NormalUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import Jar.entities.NormalUser;
import Jar.InputValidator.*;
import Jar.exception.DuplicateUsernameException;
@Service
public class NormalUserService {
    private final NormalUserRepository ur;
    private final InputValidator validator;
    private final PasswordEncoder passwordEncoder;

    public NormalUserService(NormalUserRepository ur, PasswordEncoder passwordEncoder, InputValidator validator) {
        this.ur = ur;
        this.passwordEncoder = passwordEncoder;
        this.validator = validator;
    }

    public NormalUser register(String username, String password){
        if(!validator.usernameIsValid(username)) throw new IllegalArgumentException("Invalid username");
        if(!validator.passwordIsValid(password)) throw new IllegalArgumentException("Invalid password");
        if (ur.findByUsername(username).isPresent()){//if present mean there is a username
            throw new DuplicateUsernameException("Username already exists");
        }

        password = passwordEncoder.encode(password);
        NormalUser user = new NormalUser();
        user.setUsername(username);
        user.setPassword(password);
        return ur.save(user);
    }

}
