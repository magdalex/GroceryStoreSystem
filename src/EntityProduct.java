public class EntityProduct {
    private String productID;
    private String productName;
    private String productDescription;
    private double productPrice;
    private String productCategory;
    private int productAvailability;

    // getters and setters
    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public int getProductAvailability() {
        return productAvailability;
    }

    public void setProductAvailability(int productAvailability) {
        this.productAvailability = productAvailability;
    }

    // toString
    @Override
    public String toString() {
        return "[ID] " + productID + ", [Product] " + productName + ", [Description] " + productDescription
                + ", [Category] " + productCategory
                + ", [Price] " + productPrice;
    }

    // constructors
    EntityProduct() {

    }

    public EntityProduct(String productID, String productName, String productDescription, double productPrice,
            String productCategory, int productAvailability) {
        this.productID = productID;
        this.productName = productName;
        this.productDescription = productDescription;
        this.productPrice = productPrice;
        this.productCategory = productCategory;
        this.productAvailability = productAvailability;
    }

}
