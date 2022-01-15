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
                    logIn();
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
        String fName;
        String lName;
        String email;
        String pword;
        Scanner scanner = new Scanner(System.in);

        System.out.println("What is your First name");
        fName = scanner.nextLine();
        System.out.println("What is your Last name");
        lName = scanner.nextLine();
        System.out.println("What is your email(You will use this to log in");
        email = scanner.nextLine();
        System.out.println("Create a password");
        pword = scanner.nextLine();
        RegisteredAccount new_account = new RegisteredAccount(fName, lName, email, pword);
        PostLoginMenu login = new PostLoginMenu(email, pword);
    }

    public void logIn(){
        String email;
        String pword;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your email address");
        email = scanner.nextLine();
        System.out.println("Enter your password");
        pword = scanner.nextLine();
        PostLoginMenu login = new PostLoginMenu(email, pword);

    }
}

