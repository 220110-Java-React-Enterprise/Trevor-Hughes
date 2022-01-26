import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class NewBankAccount{
    private final Connection connection;

    NewBankAccount() {
        connection = ConnectionManager.getConnection();
    }


    // Creates a new bank account in the database for the person signed in;
    //input: userID
    //output: none
    public void createAccount(int userID, CustomArrayList<UserAccount> accountList) {
        Validation test = new Validation();
        Scanner scanner = new Scanner(System.in);
        String name; // holds the name of the account that the user creates
        int accountID = 0; // holds the accountID that was just created

        // lets the user input their account name and makes sure that it is unique
        do {
            System.out.println("What would you like to name your account?");
            name = scanner.nextLine();
        }
        while (!test.validateAccount(userID, name));

        // once confirmed inserts the new account into the database in the Accounts table
        try {
            String sql = "INSERT INTO Accounts (account_name, money_amount) VALUES (?,?)";
            PreparedStatement pstmt = connection.prepareStatement(sql);

            pstmt.setString(1, name);
            pstmt.setFloat(2, 0);

            pstmt.executeUpdate();

            // gets the account_id, so it can get inserted into users_accounts table
            sql = "SELECT account_id FROM Accounts WHERE account_name = ?";
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();


            while (rs.next()) {
                accountID = rs.getInt(1);
            }

            // inserts the user_id and account_id into the users_accounts table
            sql = "INSERT INTO users_accounts (user_id, account_id) VALUES (?,?)";
            pstmt = connection.prepareStatement(sql);

            pstmt.setInt(1, userID);
            pstmt.setInt(2, accountID);

            pstmt.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        }
        UserAccount account = new UserAccount(accountID, name, 0);
        accountList.add(account);
        }
}