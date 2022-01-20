import java.util.Scanner;

public class PostLoginMenu {
    private String email;
    PostLoginMenu(String email) {
        this.email = email;
        System.out.println(this.email);
        ActualMenu();
    }
    public void ActualMenu(){
        int answer;
        MainMenu goBack = new MainMenu();
        Scanner scanner = new Scanner(System.in);
        do {
            try {
                System.out.println("Account: " + this.email + " What would you like to do?");
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
                    System.out.println("you selected 1");
                    break;
                case (2):
                    System.out.println("you selected 2");
                    break;
                case (3):
                    System.out.println("you selected 3");
                    break;
                case (4):
                    System.out.println("you selected 4");
                    break;
                case (5):
                    System.out.println("you selected 5");
                    break;
                case (6):
                    System.out.println("you selected 6");
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
