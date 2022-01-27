import java.util.Scanner;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


//validate a bunch of things from other classes
public class Validation {
    private final Connection connection;
    Scanner scanner = new Scanner(System.in);

    public Validation() {
        connection = ConnectionManager.getConnection();

    }

    //validates that a name is not empty and does not contain numbers or non letters
    // input : name; the name that was inserted
    // output: true if the string is valid
    //         false if the string is not valid
    public boolean validateName(String name) {

        //puts string into character array
        char[] chars = name.toCharArray();

        //if the string is empty return false
        if (name.equals("")) {
            System.out.println("Name cannot be empty.");
            return false;
        }

        //if characters are not letters returns false
        for (char c : chars) {
            if (!Character.isLetter(c)) {
                System.out.println("Please enter a name with only letters");
                return false;
            }
        }

        //if it passes everything else returns true;
        return true;
    }

    //validates the email to true if it is valid
    // input : email, a string that will be tested for validity
    // output: true it if passes everything
    //         false if it doesn't
    public boolean validateEmail(String email) {

        //creates a patter where the user has to enter text, then an @ sign, then a period, then more text
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9+_-]+[.]+[A-Za-z0-9+_-]+$";
        Pattern pattern = Pattern.compile(regex);


        //checks if what the user entered was empty
        if (email.equals("")) {
            System.out.println("Email can't be empty");
            return false;
        }
        Matcher matcher = pattern.matcher(email);

        //if the email wasn't in the correct form it doesn't go through with it
        if(!matcher.matches()){
            System.out.println("E-mail must be in the form of an email\n");
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


    //checks to see if the password is empty
    //input : password, the string getting evaluated
    //output: true if it passes
    //        false if it doesn't pass
    public boolean validatePassword(String password) {
        if (password.equals("")) {
            System.out.println("Text can't be empty");
            return false;
        }
        return true;
    }

    //check if email and password match an account in the database
    //input : email, string that holds email
    //      : password, string that holds password
    //output: true it they do connect to an account
    //      : false if they do not
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

    //checks if an account has been made for that user with that unique name
    //input : userID, the id of the user
    //      : name, the name of the account they want to make
    //output: true if they're allowed to make a new account
    //      : false if they aren't
    public boolean validateAccount(int userID, String name) {
        String accountName; //holds the names of the users accounts in the database

        //checkks if the name entered is empty, if so returns false
        if (name.equals("")) {
            System.out.println("Text can't be empty");
            return false;
        }

        //gets all of the names of the accounts that the user has
        try {
            String sql = "SELECT Accounts.account_name FROM Accounts INNER JOIN users_accounts ON " +
                    "Accounts.account_id = users_accounts.account_id WHERE users_accounts.user_id = " + userID;
            PreparedStatement pstmt = connection.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                accountName = rs.getString(1);

                //checks the name that they want to make is already in the database, returns false if it does
                if(name.equals(accountName)){
                    System.out.println("You already have an account with that name");
                    return false;
                }
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }

        //returns true if everything passes
        return true;
    }

    //checks if the email is not empty
    //input : email, the string we are testing
    //output: NA
    public boolean validateEmailReal(String email) {

        //checks if what the user entered was empty
        if (email.equals("")) {
            System.out.println("Email can't be empty");
            return false;
        }
        return true;
    }
};