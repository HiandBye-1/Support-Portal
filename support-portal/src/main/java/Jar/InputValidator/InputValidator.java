package Jar.InputValidator;
import org.springframework.stereotype.Component;
import java.nio.charset.StandardCharsets; //to count bytes in password instead of string which include images
@Component
public class InputValidator {

    public boolean usernameIsValid(String username) {
        if(username == null || username.length() == 0 || username.length() > 20 || username.length() < 3) return false;
        for(int i = 0; i < username.length(); i++){
            if(Character.isWhitespace(username.charAt(i))) return false;
        }
        return true;
    }

    public boolean passwordIsValid(String password) {
        if(password == null || password.getBytes(StandardCharsets.UTF_8).length > 72 || password.length() < 15) return false;
        for(int i = 0; i < password.length(); i++){
            if(Character.isWhitespace(password.charAt(i))) return false;
        }
        return true;
    }
}
