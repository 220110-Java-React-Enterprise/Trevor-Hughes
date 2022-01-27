import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.Scanner;


//this class is used to view and choose which account the user would like to use
//while using other methods
public class ChooseAccount {
    private final Connection connection;

    ChooseAccount() {
        connection = ConnectionManager.getConnection();
    }

    // prints out the accounts and asks the user to enter which account they would like to use
    // input : userID, the ID of the user that is using this method
    // input : accountList, the arrayList that holds all the information about the accounts
    // output: the accountID of the selected account;
    public int chooseAccountID(int userID, CustomArrayList<UserAccount> accountList) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        int counter;    // holds the total number of accounts +1
        int answer = 0; // holds the answer the user inserted

        // keeps printing the accounts and asking for input as long as the user does
        // not select an appropriate number
        do {
            System.out.println("Please select an account.");

            //prints the accounts and sets counter = to the amount of accounts + 1;
            counter = printAccounts(accountList);

            // runs if there are no accounts
            if (counter == 1) {
                System.out.println("You have not created any accounts yet.");
                return -1;
            }

            //checks if the user enters an integer
            try {

                //gets the user to input their choice
                answer = scanner.nextInt();

                //if the user doesn't select an option tells them to
                if (answer <= 0 || answer >= counter) {
                    System.out.println("Please choose an option.");
                }
            } catch (Exception e) {
                System.out.println("Please choose an integer");
                break;
            }

            //keeps asking while they don't select an option
        } while (answer <= 0 || answer >= counter);

        int accountID; // holds the accountID of the chosen account
        counter = 1;

        //selects the accountID from the database from the account number chosen
        try {
            String sql = "SELECT Accounts.account_id FROM Accounts INNER JOIN users_accounts ON " +
                    "Accounts.account_id = users_accounts.account_id WHERE users_accounts.user_id = " + userID;
            PreparedStatement pstmt = connection.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            // runs through the accounts trying to match the chosen number with the right account
            while (rs.next()) {
                accountID = rs.getInt(1);

                // when the chosen answer and accounts match returns the accountID
                if (answer == counter) {
                    return accountID;
                }
                counter++;
            }

            //returns -1 if there are no accounts for this user
            if (counter == 1) {
                System.out.println("You have not created any accounts yet.");
                return -1;

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }return -1;
    }

    //prints out all the accounts that the user has and returns the number of accounts
    // input : accountList, the arraylist of the accounts the user has
    // output: the number of accounts the user has
    public int printAccounts(CustomArrayList<UserAccount> accountList){
        int counter = 1; // holds the amount of accounts + 1
        int size = accountList.size(); //gets the size of the arraylist

        //creates a formatter that takes a float and turns it into money form
        NumberFormat formatter = NumberFormat.getCurrencyInstance();

        //goes through the arraylist and prints out everything in it
        for ( int i = 0; i < size; i++) {
            UserAccount a;
            a = accountList.get(i);

            //turns the float into money form
            String moneyString = formatter.format(a.getMoneyAmount());
            System.out.println(counter + ": " + a.getAccountName() + "       Value: " + moneyString);
            counter++;
        }
        System.out.println();

        //returns the amount of accounts + 1
        return counter;

    }
}
