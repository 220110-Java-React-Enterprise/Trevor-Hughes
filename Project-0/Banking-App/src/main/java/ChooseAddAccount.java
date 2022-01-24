import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class ChooseAddAccount {
    private final Connection connection;

    ChooseAddAccount(){
        connection = ConnectionManager.getConnection();

    }
    public void addAccount(int userID){
        String email;    //email
        String pword;    // password
        int newID = 0;
        int chosenAccountID = -1;

        Validation test = new Validation();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Validate the account you want to add your account to.");
        //makes sure that the username and password match in the database
        do{

            // makes sure that they are entering a valid email
            do{System.out.println("Enter your email address");
                email = scanner.nextLine();}
            while(!test.validateEmailReal(email));

            //makes sure the password isn't empty
            do{System.out.println("Enter your password");
                pword = scanner.nextLine();}
            while(!test.validatePassword(pword));
        }

        //makes sure the amil and password match an acccount
        while(!test.checkCredentials(email,pword));

        // gets userID and brings them into the next menu
        try {
            String sql = "SELECT user_id FROM Users WHERE email = '" + email + "'";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                newID= rs.getInt(1);
            }

            //checks if they tried logging into account they are currently logged into
            if(newID == userID){
                System.out.println("\nCan't choose the account you're logged into\n");
                return;
            }
        }catch (SQLException e) {
            e.printStackTrace();}
        ChooseAccount chooseAccount = new ChooseAccount();

        //asks the user to pick which account they want to let the other account share with them
        try {
            chosenAccountID = chooseAccount.chooseAccountID(userID);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //check if the user has any accounts
        if(chosenAccountID == -1){
            return;
        }

        // adds to the many-to-many table so the new user can access the chosen account
        String sql = "INSERT INTO users_accounts (user_id, account_id) VALUES (" + newID +
                "," + chosenAccountID+ ")";
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
