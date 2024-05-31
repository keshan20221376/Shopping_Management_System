import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
    private String userName;
    private String password;
    private ArrayList<Purchase> purchaseHistory = new ArrayList<>();

    //User class constructor
    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public ArrayList<Purchase> getPurchaseHistory() {
        return purchaseHistory;
    }

    public String getUserName(){
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public void setUserName(String userName){
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPurchaseHistory(ArrayList<Purchase> purchaseHistory) {
        this.purchaseHistory = purchaseHistory;
    }
}
