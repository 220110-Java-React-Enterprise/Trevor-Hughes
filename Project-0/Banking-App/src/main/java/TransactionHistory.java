import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TransactionHistory {
    private final Connection connection;

    TransactionHistory(){
        connection = ConnectionManager.getConnection();

    }

    //Shows all the transactions that took place so far
    //input : accountID, the account that wants to be used to check the transactions for
    //output: NA
    public void showHistory(int accountID){
        String transaction; // going to hold the description for the transaction
        int counter = 0; //checks if any transactions exist

        //gets the type of transactions this account made and prints them out
        try {
            String sql = "SELECT transaction_type FROM Transactions WHERE account_id = " + accountID;
            PreparedStatement pstmt = connection.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            System.out.println("\n");
            while (rs.next()) {
                transaction = rs.getString(1);
                System.out.println(transaction);
                counter ++;
            }

            //checks if there were no transactions
            if(counter == 0){
                System.out.println("No transactions for this account yet.");
            }
            System.out.println("\n");
        }catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
