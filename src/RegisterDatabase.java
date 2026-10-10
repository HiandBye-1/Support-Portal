//register to mysql database
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class RegisterDatabase {
    private String username;
    private String encryptedPassword;

    //take in parameter; username, encrypted password
    public RegisterDatabase(String username, String encryptedPassword){
        this.username = username;
        this.encryptedPassword = encryptedPassword;
    }


    public int checkDuplicateUsername(){
        try{
            Connection con = DBconnect.getConnection();
            String sql = "SELECT COUNT(*) FROM USERS WHERE user_name = ?;";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                int count = rs.getInt(1);
                
                if(count > 0){
                    RegisterMenu.clearScreen();
                    System.out.println("--------------------------------");
                    System.out.println("             ERROR");
                    System.out.println("--------------------------------\n");

                    System.out.println("Username already exists. \nPlease choose a different username.\n");
                    System.out.println("--------------------------------");
                    return 1; // Indicate duplicate found
                }else{
                    return 0; // Indicate no duplicate found
                }
            }
        }catch(Exception e){
            RegisterMenu.clearScreen();
            System.out.println("--------------------------------");
            System.out.println("             ERROR");
            System.out.println("--------------------------------\n");
            System.out.println(e);
            System.out.println("--------------------------------");
        }
        
        // Return 0 to indicate successful registration
        return 0;
    }

    
    public void saveToDatabase(){
        try{
            Connection con = DBconnect.getConnection();
            //use call procedure name addUser
            String sql = "CALL addUser(?, ?, ?);";
            PreparedStatement stmt = con.prepareStatement(sql);
            String oneTimePassword = oneTimePass.generateOneTimePass();
            stmt.setString(1, username);
            stmt.setString(2, Encrypt.encryptedPass(encryptedPassword));
            stmt.setString(3, Encrypt.encryptedPass(oneTimePassword));
            stmt.executeUpdate();
            con.close();
            RegisterMenu.clearScreen();
            System.out.println("--------------------------------");
            System.out.println("             SUCCESS");
            System.out.println("--------------------------------\n");
            System.out.println("            User " + username + "\nhas been registered successfully.\n");
            System.out.println("  Your One Time Password is:\n          " + oneTimePassword);
            System.out.println("--------------------------------\n");
            //print out the one time password for the user to use to login
            //////////////////////
            /// ///////////////
        }catch(Exception e){
            RegisterMenu.clearScreen();
            System.out.println("--------------------------------");
            System.out.println("             ERROR");
            System.out.println("--------------------------------\n");
            System.out.println(e);
            System.out.println("--------------------------------");
        }
    }
}
