public class Clothing extends Product{
    private String color;
    private String size;

    // Clothing class constructor
    public Clothing(String id, String productName, int availableItemCount, double price, String color, String size) {
        super(id, productName, availableItemCount, price);
        this.color = color;
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public String getSize() {
        return size;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setSize(String size) {
        this.size = size;
    }

    //Overriding the printProductDetails method from the Product class
    @Override
    public void printProductDetails(){
        System.out.println("------------------------------------------------------------------");
        System.out.println("Product Category     : Clothing");
        System.out.println("Product ID           : " + getId());
        System.out.println("Product Name         : " + getProductName());
        System.out.println("Available Item Count : " + getAvailableItemCount());
        System.out.println("Price                : £" + String.format("%.2f", getPrice()));
        System.out.println("Color                : " + color);
        System.out.println("Size                 : " + size);
        System.out.println("------------------------------------------------------------------");
    }
}
