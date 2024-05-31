public class Electronics extends Product{
    private String brandName;
    private int warrantyPeriod;

    // Electronics class constructor
    public Electronics(String id, String productName, int availableItemCount, double price, String brandName, int warrantyPeriod) {
        super(id, productName, availableItemCount, price);
        this.brandName = brandName;
        this.warrantyPeriod = warrantyPeriod;
    }

    public String getBrandName() {
        return brandName;
    }
    public int getWarrantyPeriod() {
        return warrantyPeriod;
    }
    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }
    public void setWarrantyPeriod(int warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod;
    }

    //Override printProductDetails method from Product class
    @Override
    public void printProductDetails(){
        System.out.println("------------------------------------------------------------------");
        System.out.println("Product Category     : Electronics");
        System.out.println("Product ID           : " + getId());
        System.out.println("Product Name         : " + getProductName());
        System.out.println("Available Item Count : " + getAvailableItemCount());
        System.out.println("Price                : £" + String.format("%.2f", getPrice()));
        System.out.println("Brand Name           : " + brandName);
        System.out.println("Warranty Period (M)  : " + warrantyPeriod);
        System.out.println("------------------------------------------------------------------");
    }
}
