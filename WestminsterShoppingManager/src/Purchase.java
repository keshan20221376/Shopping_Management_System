import java.io.Serializable;
import java.util.ArrayList;

public class Purchase implements Serializable {
    private ArrayList<ShoppingCartElement> shoppingCartElement;
    private double totalCost;

    // Purchase class constructor
    public Purchase(ArrayList<ShoppingCartElement> shoppingCartElement, double totalCost){
        this.shoppingCartElement = shoppingCartElement;
        this.totalCost = totalCost;
    }

    public ArrayList<ShoppingCartElement> getShoppingCartElement() {
        return shoppingCartElement;
    }

    public void setShoppingCartElement(ArrayList<ShoppingCartElement> shoppingCartElement) {
        this.shoppingCartElement = shoppingCartElement;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }
}
