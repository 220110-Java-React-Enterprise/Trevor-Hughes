import java.util.Scanner;

public class PostLoginMenu {
    private final String email;
    PostLoginMenu(String email, String pword) {
        this.email = email;
        System.out.println(email + " " + pword);
        ActualMenu();
    }
    public void ActualMenu(){
        int answer;
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.println("Hello " + email + ". What would you like to do?");
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
        }while (answer < 1 || answer > 9);
        switch(answer) {
            case(1):
                System.out.println("you selected 1");
                break;
            case(2):
                System.out.println("you selected 2");
                break;
            case(3):
                System.out.println("you selected 3");
                break;
            case(4):
                System.out.println("you selected 4");
                break;
            case(5):
                System.out.println("you selected 5");
                break;
            case(6):
                System.out.println("you selected 6");
                break;
            case(7):
                System.out.println("you selected 7");
                break;
            case(8):
                System.out.println("you selected 8");
                break;
            case(9):
                System.out.println("you selected 9");
                break;
        }
    }
}
