import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class OneTimePassDatabase {
    public static boolean checkOneTimePass(String username, String password){
        try{
            String query = "SELECT * FROM users WHERE user_name = ? and one_time_pass = ?";
            PreparedStatement pst = DBconnect.getConnection().prepareStatement(query);
            pst.setString(1, username);
            pst.setString(2, Encrypt.encryptedPass(password));
            ResultSet rs = pst.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void deleteOneTimePass(String username){
        try{
            String query = "UPDATE users set one_time_pass = '' where user_name = ?";
            PreparedStatement pst = DBconnect.getConnection().prepareStatement(query);
            pst.setString(1, username);
            pst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
