import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class TransferFunds {
    private final Connection connection;

    TransferFunds(){
        connection = ConnectionManager.getConnection();

    }

    // Transfer funds in between accounts
    // input : userID, ID of the user signed in
    //      : accountList, arraylist of all accounts
    // output: NA
    public void transferFundsNow(int userID, CustomArrayList<UserAccount> accountList){
        Scanner scanner = new Scanner(System.in);
        int answer1 = -1;  // holds the account they want to withdraw from
        int answer2 = -1; // holds the account they want to deposit it into
        float withdrawAmount = 0; // the amount they want to withdraw
        ChooseAccount account1 = new ChooseAccount();

        //gets the account they want to withdraw money from
        try {
            System.out.println("What account would you like to withdraw money from?");
            answer1 = account1.chooseAccountID(userID, accountList);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //gets the account they want to deposit the funds into
        try {
            System.out.println("Which account would you like to deposit the funds into?");
            answer2 = account1.chooseAccountID(userID, accountList);
        } catch (SQLException e) {
            e.printStackTrace();
        }

            //make sure both of the accounts exist
            if(answer1 == -1 || answer2 == -1){

            //return nothing and go back to main menu
            return;
        }

            //make sure they didn't chose the same account
        if(answer1 == answer2){
            System.out.println("You cannot chose the same account\n");{

                //returns nothing and goes back menu
                return;
            }
        }

        //withdraws funds from the first account and sets withdraw ammount to the amount
        WithdrawFunds withdrawFunds = new WithdrawFunds();
        withdrawAmount = withdrawFunds.withdrawAmount(answer1, accountList);

        //uses the overloaded function to deposit withdrawAmount into the second account chosen
        DepositFunds depositFunds = new DepositFunds();
        depositFunds.deposit(answer2, withdrawAmount, accountList);
    }
}
