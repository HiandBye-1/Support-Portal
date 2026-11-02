package Jar.util;
import java.security.SecureRandom;//prevent guessing of short code

import org.springframework.stereotype.Component;
@Component 
public class ShortCodeGenerator {
    private static final SecureRandom random = new SecureRandom();

    public String generateShortCode(int length){
        if (length < 1) throw new IllegalArgumentException("Length must be greater than 0");

        String chars = "ABCDEFGHIJKLMNOPQRSTUVWX"
                        +"YZabcdefghijklmnopqrs"
                        +"tuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();

        int randomIndex;
        for(int i = 0; i < length; i++){
            randomIndex = random.nextInt(chars.length());
            sb.append(chars.charAt(randomIndex));
        }

        return sb.toString();
    }

}
