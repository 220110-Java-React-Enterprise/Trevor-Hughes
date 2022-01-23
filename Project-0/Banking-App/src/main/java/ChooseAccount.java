import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.Scanner;

public class ChooseAccount {
    private final Connection connection;

    ChooseAccount() {
        connection = ConnectionManager.getConnection();
    }

    public int chooseAccountID(int userID) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        int counter = 1;
        int answer = 0;
        String accountName;
        do {
            System.out.println("Please select an account.");
            counter = printAccounts(userID);
                if (counter == 1) {
                    System.out.println("You have not created any accounts yet.");
                    return -1;
                }
                try {
                    answer = scanner.nextInt();
                    if (answer <= 0 || answer >= counter) {
                        System.out.println("Please choose an option.");
                    }
                } catch (Exception e) {
                    System.out.println("Please choose an integer");
                    break;
                }
        } while (answer <= 0 || answer >= counter);
        int accountID = 0;
        counter = 1;
        try {
            String sql = "SELECT Accounts.account_id FROM Accounts INNER JOIN users_accounts ON " +
                    "Accounts.account_id = users_accounts.account_id WHERE users_accounts.user_id = " + userID;
            PreparedStatement pstmt = connection.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                accountID = rs.getInt(1);
                if (answer == counter) {
                    return accountID;
                }
                counter++;
            }
            if (counter == 1) {
                System.out.println("You have not created any accounts yet.");
                return -1;

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }return -1;
    }
    public int printAccounts(int userID){
        String accountName;
        int counter = 1;
        float accountAmount = 0;
        NumberFormat formatter = NumberFormat.getCurrencyInstance();

        String sql = "SELECT Accounts.account_name, Accounts.money_amount FROM Accounts INNER JOIN users_accounts ON " +
                    "Accounts.account_id = users_accounts.account_id WHERE users_accounts.user_id = " + userID;
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement(sql);
            ResultSet rs = null;
            rs = pstmt.executeQuery();
            System.out.println("");
            while (rs.next()) {
                accountName = rs.getString(1);
                accountAmount =rs.getFloat(2);
                String moneyString = formatter.format(accountAmount);
                System.out.println(counter + ": " + accountName + "       Value: " + moneyString);
                counter++;
            }
            System.out.println("");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return counter;
    }
}
