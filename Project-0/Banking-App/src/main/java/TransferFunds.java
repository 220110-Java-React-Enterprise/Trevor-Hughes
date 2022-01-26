import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class TransferFunds {
    private final Connection connection;

    TransferFunds(){
        connection = ConnectionManager.getConnection();

    }
    public void transferFundsNow(int userID, CustomArrayList<UserAccount> accountList){
        Scanner scanner = new Scanner(System.in);
        int answer1 = -1;
        int answer2 = -1;
        float withdrawAmount = 0;
        ChooseAccount account1 = new ChooseAccount();
        try {
            System.out.println("What account would you like to withdraw money from?");
            answer1 = account1.chooseAccountID(userID, accountList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            System.out.println("Which account would you like to deposit the funds into?");
            answer2 = account1.chooseAccountID(userID, accountList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(answer1 == -1 || answer2 == -1){
            return;
        }
        if(answer1 == answer2){
            System.out.println("You cannot chose the same account\n");{
                return;
            }
        }
        WithdrawFunds withdrawFunds = new WithdrawFunds();
        withdrawAmount = withdrawFunds.withdrawAmount(answer1, accountList);
        DepositFunds depositFunds = new DepositFunds();
        depositFunds.deposit(answer2, withdrawAmount, accountList);
    }
}
