public class NewBankAccount {
    public void createAccount(String email){
        PostLoginMenu getBack = new PostLoginMenu(email);
        getBack.ActualMenu();
    }
}
