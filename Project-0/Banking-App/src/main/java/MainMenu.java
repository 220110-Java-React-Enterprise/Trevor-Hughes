import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class MainMenu {
    private final Connection connection;
    private int userID;

    MainMenu(){
        //creates a connection to the database
        connection = ConnectionManager.getConnection();

    }

    //Prints out the main menu and asks the user what they would want to do
    // takes the users answer and send them to the correct next menu
    //input: NA
    //output: NA
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

            // makes sure that the user enters an integer, no other characters
            catch(Exception e){
                System.out.println("Please enter an integer");
                mainMenuMaker();
                break;
            }


            //sends user to next instructions based on answer
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

                    // if the user doesn't enter anything
                default:
                    System.out.println("Please enter an option.");
            }

            //makes sure the user enters an available option
        }while(answer < 1 || answer > 3);
    }

    // asks the user for information to set up their account
    // then sends the user to the post login menu
    public void registerAccount(){
        String fName;     // first name
        String lName;     // last name
        String email;     // email
        String pword;     // password
        Validation test = new Validation();
        Scanner scanner = new Scanner(System.in);


        // asks the user for all their information and makes sure that
        // the information that they are putting in is the right form
        // and not empty. For the email it makes sure that it is unique
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

        //creates the account in the database
        RegisteredAccount new_account = new RegisteredAccount();
        new_account.registerAccount(fName, lName, email, pword);

        // gets the userid that was given when the user account was created
        // then send them to the next menu
        try {
            String sql = "SELECT user_id FROM Users WHERE email = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
              userID= rs.getInt(1);
            }
        }catch (SQLException e) {
            e.printStackTrace();}
        PostLoginMenu login = new PostLoginMenu(userID);
    }

    // lets the user input their email and password to log in to their account
    // input: NA
    // output: NA
    public void logIn(){
        String email;    //email
        String pword;    // password

        Validation test = new Validation();
        Scanner scanner = new Scanner(System.in);

        //makes sure that the username and password match in the database
        do{

            // makes sure that they are entering a valid email
        do{System.out.println("Enter your email address");
        email = scanner.nextLine();}
        while(!test.validateEmailReal(email));

        //makes sure the password isn't empty
        do{System.out.println("Enter your password");
        pword = scanner.nextLine();}
        while(!test.validatePassword(pword));
        }
        while(!test.checkCredentials(email,pword));

        // gets userID and brings them into the next menu
        try {
            String sql = "SELECT user_id FROM Users WHERE email = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                userID= rs.getInt(1);
            }
        }catch (SQLException e) {
            e.printStackTrace();}
        PostLoginMenu login = new PostLoginMenu(userID);

    }
}

