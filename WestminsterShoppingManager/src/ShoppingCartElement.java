public class ShoppingCartElement {
    private Product product;
    private int quantity;

    // ShoppingCartElement class constructor
    public ShoppingCartElement(Product product){
        this.product = product;
        this.quantity = 1;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }
}
