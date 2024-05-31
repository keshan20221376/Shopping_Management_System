import java.io.Serializable;

public abstract class Product implements Serializable {
    private String id;
    private String productName;
    private int availableItemCount;
    private double price;

    // Product class constructor
    public Product(String id, String productName, int availableItemCount, double price) {
        this.id = id;
        this.productName = productName;
        this.availableItemCount = availableItemCount;
        this.price = price;
    }

    public abstract void printProductDetails();

    public double getPrice() {
        return price;
    }

    public int getAvailableItemCount() {
        return availableItemCount;
    }

    public String getId() {
        return id;
    }

    public String getProductName() {
        return productName;
    }

    public void setAvailableItemCount(int availableItemCount) {
        this.availableItemCount = availableItemCount;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
