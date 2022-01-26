import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class PostLoginMenu{
    private final Connection connection;
    private final int userID;  // holds the id of the user that signed in
    public CustomArrayList<UserAccount> accountList = new CustomArrayList<UserAccount>();


    // Constructor that sets the private variable to the user id passed in
    // then brings you to the main menu
    PostLoginMenu(int userID) {
        int accountID;
        String accountName;
        float accountAmount;
        connection = ConnectionManager.getConnection();
        this.userID = userID;
        String sql = "SELECT Accounts.account_id, Accounts.account_name, Accounts.money_amount FROM Accounts INNER JOIN users_accounts ON " +
                "Accounts.account_id = users_accounts.account_id WHERE users_accounts.user_id = " + userID;
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement(sql);
            ResultSet rs = null;
            rs = pstmt.executeQuery();
            System.out.println("");
            while (rs.next()) {
                accountID = rs.getInt(1);
                accountName = rs.getString(2);
                accountAmount = rs.getFloat(3);
                UserAccount account = new UserAccount(accountID, accountName, accountAmount);
                accountList.add(account);

            }
        }catch (SQLException e){
            System.out.println("Oops");
        }
        ActualMenu();
    }


    //prints out the menu and asks for the users input about what they would like to do
    // keeps running until the user quits or logs out
    public void ActualMenu(){
        int answer; //holds the users input
        MainMenu goBack = new MainMenu();
        Scanner scanner = new Scanner(System.in);

        //keeps asking what the user would do until they enter a valid choice
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

                // if they enter something that isn't and integer, asks again
            } catch (Exception e) {
                System.out.println("Please enter an integer");
                ActualMenu();
                break;
            }

            //goes to different options based on what the user input
            switch (answer) {

                // if the user chooses 1, creates a new bank account
                case (1):
                    NewBankAccount account = new NewBankAccount();
                    account.createAccount(userID, accountList);
                    ActualMenu();
                break;

                // if the user chooses 2, deposits money in account
                case (2):
                    ChooseAccount getAccount = new ChooseAccount();
                    int accountID = 0;

                    // lets the user chose the account they want to use
                    try {
                        accountID = getAccount.chooseAccountID(userID, accountList);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    // makes sure that there is an account under a user then deposits the money
                    if(accountID != -1){
                    DepositFunds addMoney = new DepositFunds();
                    addMoney.deposit(accountID, accountList);}
                    ActualMenu();
                    break;

                    // if the user chooses 3, withdraws money from account
                case (3):

                    //lets user chose which account
                    getAccount = new ChooseAccount();
                    accountID = 0;
                    try {
                        accountID = getAccount.chooseAccountID(userID, accountList);
                    } catch (SQLException e) {
                        e.printStackTrace();}

                    //checks to see if this user has an account then withdraws the money
                        if(accountID != -1){
                            WithdrawFunds withdrawMoney = new WithdrawFunds();
                            withdrawMoney.withdrawAmount(accountID, accountList);}
                        ActualMenu();
                    break;

                    // lets user display the accounts they have
                case (4):
                    ChooseAccount display = new ChooseAccount();
                    display.printAccounts(userID, accountList);
                    ActualMenu();
                    break;

                    //lets show transactions for a specific account
                case (5):
                    getAccount = new ChooseAccount();
                    accountID = 0;

                    //asks user which account they want to use
                    try {
                        accountID = getAccount.chooseAccountID(userID,accountList);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    //makes sure that the account exists, if it does it shows the transactions
                    if(accountID != -1) {
                        TransactionHistory showHistory = new TransactionHistory();
                        showHistory.showHistory(accountID);
                    }
                    ActualMenu();
                    break;

                    //lets user add another user to their account
                case (6):
                    ChooseAddAccount newAccount = new ChooseAddAccount();
                    newAccount.addAccount(userID,accountList);
                    ActualMenu();
                    break;

                    //lets user transfer funds to another one of their accounts
                case (7):
                    TransferFunds fundTransfer = new TransferFunds();
                    fundTransfer.transferFundsNow(userID, accountList);
                    ActualMenu();
                    break;

                    //brings user back to the first menu
                case (8):
                    System.out.println("You have logged out.");
                    goBack.mainMenuMaker();
                    break;

                    //quits the program
                case (9):
                    System.out.println("Thank you for using this bank.");
                    break;

                    // tells user to pick an option
                    //if the user doesnt choose one of the options
                default:
                    System.out.println("Please enter an option.");
            }

            //brings back the menu if they don't select an option
        }while (answer < 1 || answer > 9);
    }
}
