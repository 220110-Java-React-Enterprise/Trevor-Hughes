import java.sql.Connection;

public class MainFile {
    public static void main(String[] args){
        Connection conn = ConnectionManager.getConnection();
        MainMenu thing= new MainMenu();
        thing.mainMenuMaker();
    }
}
