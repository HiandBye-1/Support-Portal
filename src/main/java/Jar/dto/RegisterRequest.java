package Jar.dto;

public class RegisterRequest {
    private final String username;
    private final String password;
    private final String reenteredPassword;
    public RegisterRequest(String username, String password, String reenteredPassword) {
        this.username = username;
        this.password = password;
        this.reenteredPassword = reenteredPassword;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getReenteredPassword() {
        return reenteredPassword;
    }
}
