import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;


//this class is used to add another use to one of their existing accounts
public class ChooseAddAccount {
    private final Connection connection;

    ChooseAddAccount(){
        connection = ConnectionManager.getConnection();

    }

    //takes the user ID and arraylist of accounts and asks the user to input the correct
    //information add a new user to their account
    // input : userID, the ID of the user
    //       : accountList, the arrayList that holds the information about the accounts
    // output: NA
    public void addAccount(int userID, CustomArrayList<UserAccount> accountList){
        String email;    //email
        String pword;    // password
        int newID = 0;   // userID of user being added
        int chosenAccountID = -1; //accountID of account being shared

        Validation test = new Validation();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Validate the account you want to add your account to.");
        //makes sure that the username and password match in the database

            // makes sure that they are entering a valid email
            do{System.out.println("Enter your email address");
                email = scanner.nextLine();}
            while(!test.validateEmailReal(email));

            //makes sure the password isn't empty
            do{System.out.println("Enter your password");
                pword = scanner.nextLine();}
            while(!test.validatePassword(pword));


        //makes sure the amil and password match an acccount
        if(!test.checkCredentials(email,pword)){
         return;
        }

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
            chosenAccountID = chooseAccount.chooseAccountID(userID, accountList);
        } catch (SQLException e) {
            e.printStackTrace();
        }


        //check if the user has any accounts
        if(chosenAccountID == -1){
            return;
        }

        String accountName = null;

        //gets the name of the account being added
        String sql = "SELECT account_name FROM Accounts WHERE account_id = " + chosenAccountID;
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ResultSet rs = null;
        try {
            rs = pstmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        while (true) {
            try {
                if (!rs.next()) break;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                accountName = rs.getString(1);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


        //checks if the user we're adding the account to already has an account of that name
        if(test.validateAccount(newID, accountName)) {
            // adds to the many-to-many table so the new user can access the chosen account
            sql = "INSERT INTO users_accounts (user_id, account_id) VALUES (" + newID +
                    "," + chosenAccountID + ")";
            pstmt = null;
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
            System.out.println("Completed\n");
        }

        //if the user already had an account with that name
        else{
            System.out.println("That account already has an account with this name \n");
        }
    }
}
