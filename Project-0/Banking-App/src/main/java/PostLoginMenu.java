import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class PostLoginMenu {
    private final Connection connection;
    private final int userID;

    PostLoginMenu(int userID) {
        connection = ConnectionManager.getConnection();
        this.userID = userID;

        ActualMenu();
    }
    public void ActualMenu(){
        int answer;
        MainMenu goBack = new MainMenu();
        Scanner scanner = new Scanner(System.in);
        do {
            try {
                System.out.println("What would you like to do?");
                System.out.println("1. Create new bank account under this user");
                System.out.println("2. Deposit funds into an account");
                System.out.println("3. Withdraw funds from an account");
                System.out.println("4. Display balance of all accounts");
                System.out.println("5. View Transaction History of an account");
                System.out.println("6. Share account with other user");
                System.out.println("7. Transfer funds between accounts");
                System.out.println("8. Log Out");
                System.out.println("9. Exit");
                answer = scanner.nextInt();
            } catch (Exception e) {
                System.out.println("Please enter an integer");
                ActualMenu();
                break;
            }
            switch (answer) {
                case (1):
                    NewBankAccount account = new NewBankAccount();
                    account.createAccount(userID);
                break;
                case (2):
                    ChooseAccount getAccount = new ChooseAccount();
                    int accountID = 0;
                    try {
                        accountID = getAccount.chooseAccountID(userID);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    if(accountID != -1){
                    DepositFunds addMoney = new DepositFunds();
                    addMoney.deposit(accountID);}
                    ActualMenu();
                    break;
                case (3):
                    getAccount = new ChooseAccount();
                    accountID = 0;
                    try {
                        accountID = getAccount.chooseAccountID(userID);
                    } catch (SQLException e) {
                        e.printStackTrace();}
                        if(accountID != -1){
                            WithdrawFunds withdrawMoney = new WithdrawFunds();
                            withdrawMoney.withdrawAmount(accountID);}
                        ActualMenu();
                    break;
                case (4):
                    ChooseAccount display = new ChooseAccount();
                    display.printAccounts(userID);
                    ActualMenu();
                    break;
                case (5):
                    getAccount = new ChooseAccount();
                    accountID = 0;
                    try {
                        accountID = getAccount.chooseAccountID(userID);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    if(accountID != -1) {
                        TransactionHistory showHistory = new TransactionHistory();
                        showHistory.showHistory(accountID);
                    }
                    ActualMenu();
                    break;
                case (6):
                    ChooseAddAccount newAccount = new ChooseAddAccount();
                    newAccount.addAccount(userID);
                    ActualMenu();
                    break;
                case (7):
                    System.out.println("you selected 7");
                    break;
                case (8):
                    System.out.println("You have logged out.");
                    goBack.mainMenuMaker();
                    break;
                case (9):
                    System.out.println("Thank you for using this bank.");
                    break;
                default:
                    System.out.println("Please enter an option.");
            }
        }while (answer < 1 || answer > 9);
    }
}
