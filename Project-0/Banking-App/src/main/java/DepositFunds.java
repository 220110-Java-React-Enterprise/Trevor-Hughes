import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.Scanner;

public class DepositFunds {
    private final Connection connection;

    DepositFunds(){
        connection = ConnectionManager.getConnection();
    }

    //asks the user for how much money they would like to deposit into their account.
    //after confirming the amount is allowed, it gets added to their account and
    //tracked in their transaction history
    //input: accountID
    //output: NA
    public void deposit(int accountID){
        Scanner scanner = new Scanner(System.in);
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        float answer = 0;        //holds the amount of money the user wants to enter
        float moneyAmount = 0;   //holds the amount of money that is already in the account

        // gets the user to enter money amount. Makes sure it is a number and that it is not negative
        try {
            do {
                System.out.print("How much money would you like to deposit? \n$");
                answer = scanner.nextFloat();
                if(answer < 0){
                    System.out.println("Entered amount must be positive");
                }
            }while(answer < 0);
    } catch (Exception e) {
            System.out.println("Please enter money amount.");
            deposit(accountID);
        }

        //gets the current amount of money from the specific account being used
        String sql = "SELECT Accounts.money_amount FROM Accounts INNER JOIN users_accounts ON " +
                "Accounts.account_id = users_accounts.account_id WHERE Accounts.account_id = " + accountID;
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement(sql);
        ResultSet rs = null;
            rs = pstmt.executeQuery();
            while (rs.next()) {
                moneyAmount = rs.getFloat(1);
            };

            //adds the current amount to the amount the user wanted to add
        moneyAmount = moneyAmount + answer;

        //sets the new amount to the account total
            sql = "UPDATE Accounts SET money_amount = " + moneyAmount + "where account_id = " + accountID;
            pstmt = connection.prepareStatement(sql);
            pstmt.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }

        //formats the amount to look like money amount
        String moneyString = formatter.format(answer);
        String totalString = formatter.format(moneyAmount);
        System.out.println(moneyString + " was deposited into account");
        System.out.println("New balance = " + totalString);

        //inerts the information to track the transaction into transaction table
            sql = "INSERT INTO Transactions (account_id, transaction_type) VALUES (" + accountID +
                    ", 'Deposited " + moneyString + "')";
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

    public void deposit(int accountID, float answer){
        Scanner scanner = new Scanner(System.in);
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        float moneyAmount = 0;   //holds the amount of money that is already in the account


        //gets the current amount of money from the specific account being used
        String sql = "SELECT Accounts.money_amount FROM Accounts INNER JOIN users_accounts ON " +
                "Accounts.account_id = users_accounts.account_id WHERE Accounts.account_id = " + accountID;
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement(sql);
            ResultSet rs = null;
            rs = pstmt.executeQuery();
            while (rs.next()) {
                moneyAmount = rs.getFloat(1);
            };

            //adds the current amount to the amount the user wanted to add
            moneyAmount = moneyAmount + answer;

            //sets the new amount to the account total
            sql = "UPDATE Accounts SET money_amount = " + moneyAmount + "where account_id = " + accountID;
            pstmt = connection.prepareStatement(sql);
            pstmt.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }

        //formats the amount to look like money amount
        String moneyString = formatter.format(answer);
        String totalString = formatter.format(moneyAmount);
        System.out.println(moneyString + " was deposited into account");
        System.out.println("New balance = " + totalString);



        //inerts the information to track the transaction into transaction table
        sql = "INSERT INTO Transactions (account_id, transaction_type) VALUES (" + accountID +
                ", 'Deposited " + moneyString + "')";
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
