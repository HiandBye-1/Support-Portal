import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class LoginDatabase{

    //check if the username and password match with the mysql database and return T/F
    public static boolean checkLogin(String username, String password){
        try{
            String query = "SELECT * FROM users WHERE user_name = ?";
            PreparedStatement pst = DBconnect.getConnection().prepareStatement(query);
            pst.setString(1, username);
            ResultSet rs = pst.executeQuery();

            if(rs.next()){
                String storedHash = rs.getString("user_password");
                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
                return encoder.matches(password, storedHash);
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
//////////TO BE CONTINUED WITH ONE TIME PASSWORD