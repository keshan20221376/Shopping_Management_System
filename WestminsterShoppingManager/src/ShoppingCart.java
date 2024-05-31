import java.util.ArrayList;

public class ShoppingCart {
    private ArrayList<ShoppingCartElement> productList = new ArrayList<>();
    private double totalCost = 0;
    private User user;

    // Shopping cart constructor
    public ShoppingCart(User user){
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setProductList(ArrayList<ShoppingCartElement> productList) {
        this.productList = productList;
    }

    //Add a product to the shopping cart
    public void addProduct(Product product){
        for (ShoppingCartElement shoppingCartElement : productList) {
            if (shoppingCartElement.getProduct().getId().equals(product.getId())) {
                shoppingCartElement.setQuantity(shoppingCartElement.getQuantity() + 1);
                return;
            }
        }
        productList.add(new ShoppingCartElement(product));
    }

    //Remove a product from the shopping cart
    public void removeProduct(String productId){
        for (ShoppingCartElement shoppingCartElement : productList) {
            if (shoppingCartElement.getProduct().getId().equals(productId)) {
                productList.remove(shoppingCartElement);
                return;
            }
        }
    }

    //Calculate the total cost of the shopping cart
    public void calculateTotalCost(){
        totalCost = 0;
        for (ShoppingCartElement shoppingCartElement : productList) {
            totalCost += shoppingCartElement.getProduct().getPrice() * shoppingCartElement.getQuantity();
        }
    }

    public double getTotalCost() {
        return totalCost;
    }

    public ArrayList<ShoppingCartElement> getProductList() {
        return productList;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }
}
