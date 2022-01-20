import java.util.Scanner;

public class MainMenu {
    public void mainMenuMaker(){
        int answer;
        Scanner scanner = new Scanner(System.in);
        do {
            try{
            System.out.println("What would you like to do?");
            System.out.println("1. Register for first account(New Users)");
            System.out.println("2. Login to my account");
            System.out.println("3. Exit");
            answer = scanner.nextInt();}
            catch(Exception e){
                System.out.println("Please enter an integer");
                mainMenuMaker();
                break;
            }
            switch(answer){
                case(1):
                    registerAccount();
                    break;
                case(2):
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
        Validation test = new Validation();
        Scanner scanner = new Scanner(System.in);

        do{System.out.println("What is your First name");
        fName = scanner.nextLine();}
        while(!test.validateName(fName));
        do{System.out.println("What is your Last name");
        lName = scanner.nextLine();}
        while(!test.validateName(lName));
        do{System.out.println("What is your email(You will use this to log in");
        email = scanner.nextLine();}
        while(!test.validateEmail(email));
        do{System.out.println("Create a password");
        pword = scanner.nextLine();}
        while(!test.validatePassword(pword));
        RegisteredAccount new_account = new RegisteredAccount(fName, lName, email, pword);
        PostLoginMenu login = new PostLoginMenu(email);
    }

    public void logIn(){
        String email;
        String pword;
        Validation test = new Validation();
        Scanner scanner = new Scanner(System.in);
        do{
        do{System.out.println("Enter your email address");
        email = scanner.nextLine();}
        while(!test.validateEmail(email));
        do{System.out.println("Enter your password");
        pword = scanner.nextLine();}
        while(!test.validatePassword(pword));
        }
        while(!test.checkCredentials(email,pword));
        PostLoginMenu login = new PostLoginMenu(email);

    }
}

