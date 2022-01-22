import java.util.Scanner;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Validation {
    private final Connection connection;
    Scanner scanner = new Scanner(System.in);

    public Validation() {
        connection = ConnectionManager.getConnection();

    }

    public boolean validateName(String name) {
        char[] chars = name.toCharArray();
        if (name.equals("")) {
            System.out.println("Name cannot be empty.");
            return false;
        }
        for (char c : chars) {
            if (!Character.isLetter(c)) {
                System.out.println("Please enter a name with only letters");
                return false;
            }
        }
        return true;
    }

    public boolean validateEmail(String email) {

        //checks if what they user entered was empty
        if (email.equals("")) {
            System.out.println("Email can't be empty");
            return false;
        }

        //checks if the email used is in the database already.
        String name = null;
        try {
            String sql = "SELECT first_name FROM Users WHERE email = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();

            // will remain null if the email isn't being used
            while (rs.next()) {
                name = rs.getString(1);
            }

            //checks if the email exists in the database, if not return null
            if (name != null) {
                System.out.println("E-mail is already used for an account. Use different E-mail.");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //returns true if they entered an email that is not empty and has not been used
        return true;
    }

    public boolean validatePassword(String password) {
        if (password.equals("")) {
            System.out.println("Text can't be empty");
            return false;
        }
        return true;
    }

    public boolean checkCredentials(String email, String password) {
        String pword = null;  // The password in the database for the email given
        String name = null;   // The name in the database for the email given
        try {

            // gets the password and the name from the database from the email given and enters it
            // in the variables that were made
            String sql = "SELECT password, first_name FROM Users WHERE email = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                pword = rs.getString(1);
                name = rs.getString(2);
            }

            //checks if the email exists in the database, if not return null
            if (pword == null) {
                System.out.println("E-mail does not exist within the database.");
                return false;
            }

            //if the password equals the password for the email given in the database
            else if (pword.equals(password)) {
                System.out.println("Welcome " + name);
                return true;
            }

            // if the password given does not match the password given in the email
            else {
                System.out.println("Incorrect password");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean validateAccount(int userID, String name) {

        if (name.equals("")) {
            System.out.println("Text can't be empty");
            return false;
        }
        return true;
    }
    public boolean validateEmailReal(String email) {

        //checks if what they user entered was empty
        if (email.equals("")) {
            System.out.println("Email can't be empty");
            return false;
        }
        return true;
    }
};



