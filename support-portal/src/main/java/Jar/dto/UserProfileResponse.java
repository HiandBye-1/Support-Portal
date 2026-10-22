package Jar.dto;
import java.util.List;
public class UserProfileResponse {
    private final String username;
    private final List<String> roles;

    public UserProfileResponse(String username, List<String> roles) {
        this.username = username;
        this.roles = roles;
    }

    public String getUsername() {
        return username;
    }

    public List<String> getRoles() {
        return roles;
    }

}
