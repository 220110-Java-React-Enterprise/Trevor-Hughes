import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.Scanner;

public class WithdrawFunds{
    private final Connection connection;
    public PostLoginMenu ID;

    WithdrawFunds() {
        connection = ConnectionManager.getConnection();

    }

    //asks the user for how much money they would like to withdraw from their account.
    //after confirming the amount is allowed, it gets subtracted from their account and
    //tracked in their transaction history
    //input: accountID
    //output: returns the amount that was withdrawn
    public float withdrawAmount(int accountID,CustomArrayList<UserAccount> accountList) {
        Scanner scanner = new Scanner(System.in);
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        String moneyString;
        float answer = 0;        //holds the amount of money the user wants to enter
        float moneyAmount = 0;   //holds the amount of money that is already in the account

        // gets the current amount of money in the certain account
        String sql = "SELECT Accounts.money_amount FROM Accounts INNER JOIN users_accounts ON " +
                "Accounts.account_id = users_accounts.account_id WHERE Accounts.account_id = " + accountID;
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement(sql);
            ResultSet rs = null;
            rs = pstmt.executeQuery();
            while (rs.next()) {
                moneyAmount = rs.getFloat(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //creates a string that appears like how money is with the current amount in the account
        moneyString = formatter.format(moneyAmount);

        //asks the user to enter the amount of money that they want to withdraw
        try{
        do {
            System.out.println("How much money would you like to withdraw?");
            System.out.print("Current amount = " + moneyString + "\n$");
            answer = scanner.nextFloat();

            // prints if they entered more than they have in their account
            if (answer > moneyAmount) {
                System.out.println("Entered amount can't be higher than current balance.");
            }

            //makes sure entered amount isn't negative
            if (answer < 0) {
                System.out.println("Entered amount can't be negative.");
            }

            //keeps asking while not fitting the criteria
        }while(answer > moneyAmount || answer < 0);

        //if they don't enter a valid float value ask again
            }catch (Exception e){
                System.out.println("Please enter a valid number");
                withdrawAmount(accountID, accountList);
            }

        //changes the amount entered to money form and then prints out
        //the changed they just made
        String answerString = formatter.format(answer);
        System.out.println(answerString + " was withdrawn");
        moneyAmount = moneyAmount - answer;
        moneyString = formatter.format(moneyAmount);
        System.out.println("Your new total is " + moneyString + "\n");


        //changes the accounts' table to the new correct money amount
        try {
            sql = "UPDATE Accounts SET money_amount = " + moneyAmount + "where account_id = " + accountID;
            pstmt = connection.prepareStatement(sql);
            pstmt.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        moneyString = formatter.format(answer);


        // keeps track of the transaction by adding it to the transaction table
        sql = "INSERT INTO Transactions (account_id, transaction_type) VALUES (" + accountID +
                ", 'Withdrew " + moneyString + "')";
        try {
            pstmt = connection.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            assert pstmt != null;
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        //changes the money amount in the specific account in the arraylist
        //to reflect the new changes to it
        int size = accountList.size();
        for ( int i = 0; i < size; i++) {
            UserAccount a;
            a = accountList.get(i);
            if (a.getAccountID() == accountID) {
                a.setMoneyAmount(moneyAmount);
            }
        }


        //returns the amount that was withdrawn
        return answer;

    }
}
