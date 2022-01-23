import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TransactionHistory {
    private final Connection connection;

    TransactionHistory(){
        connection = ConnectionManager.getConnection();

    }

    public void showHistory(int accountID){
        String transaction;
        int counter = 0;
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
            if(counter == 0){
                System.out.println("No transactions for this account yet.");
            }
            System.out.println("\n");
        }catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
