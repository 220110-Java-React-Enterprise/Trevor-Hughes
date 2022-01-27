import java.sql.Connection;

/*
Main file, creates a connection to the database and then starts the
program by bringing the user to the main menu
*/

public class MainFile {
    public static void main(String[] args){
        Connection conn = ConnectionManager.getConnection();
        MainMenu thing= new MainMenu();
        thing.mainMenuMaker();
    }
}
