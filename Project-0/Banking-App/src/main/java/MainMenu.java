import java.util.Scanner;

public class MainMenu {
    public void mainMenuMaker(){
        int answer;
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.println("What would you like to do?");
            System.out.println("1. Register for first account(New Users)");
            System.out.println("2. Login to my account");
            System.out.println("3. Exit");
            answer = scanner.nextInt();
            switch(answer){
                case(1):
                    System.out.println("register");
                    registerAccount();
                    break;
                case(2):
                    System.out.println("Login");
                    break;
                case(3):
                    System.out.println("Thank you for using this bank!");
                    break;
                default:
                    System.out.println("Please enter an option.");
            }
        }while(answer < 1 || answer > 3);
    }
    public void registerAccount(){
        System.out.println("What is your First name");
        System.out.println("What is your Last name");
        System.out.println("What is your email(You will use this to log in");
        System.out.println("Create a password");
    }
}
