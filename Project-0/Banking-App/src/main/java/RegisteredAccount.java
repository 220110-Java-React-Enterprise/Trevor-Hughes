import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegisteredAccount
{
    private final Connection connection;

    RegisteredAccount(){
        connection = ConnectionManager.getConnection();
    }

    // registers a new account into the database
    // input : fname, first name of user
    //       : lName, last name of user
    //       : email, email of user
    //       : pword, password of user
    // output: NA
    public void registerAccount(String fName, String lName, String email, String pword){
        try {
            String sql = "INSERT INTO Users (first_name, last_name, email, password) VALUES (?,?,?,?)";
            PreparedStatement pstmt = connection.prepareStatement(sql);

            pstmt.setString(1, fName);
            pstmt.setString(2, lName);
            pstmt.setString(3, email);
            pstmt.setString(4, pword);

            pstmt.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
