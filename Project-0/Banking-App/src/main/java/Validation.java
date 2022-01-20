import java.util.Scanner;

public class Validation {
    Scanner scanner = new Scanner(System.in);
    public boolean validateName(String name) {
        char[] chars = name.toCharArray();
        if(name.equals("")){
            System.out.println("Name cannot be empty.");
            return false;
        }
        for (char c : chars) {
            if (!Character.isLetter(c)) {
                System.out.println("Please enter a name with only letters");
                return false;
            }
        }
        return true;
    }
    public boolean validateEmail(String email){
        return true;
    }

    public boolean validatePassword(String password){
        if(password.equals("")){
            System.out.println("Password can't be empty");
            return false;
        }
        return true;
    }
}



