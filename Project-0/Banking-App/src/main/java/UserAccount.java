public class UserAccount {
    private int accountID;
    private String accountName;
    private float moneyAmount;

    public UserAccount(){};


    //holds the account information for the arraylist
    public UserAccount(int accountID, String accountName, float moneyAmount){
        this.accountID= accountID;
        this. accountName = accountName;
        this.moneyAmount = moneyAmount;
    }

    public int getAccountID() {
        return accountID;
    }

    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public float getMoneyAmount() {
        return moneyAmount;
    }

    public void setMoneyAmount(float moneyAmount) {
        this.moneyAmount = moneyAmount;
    }
}

