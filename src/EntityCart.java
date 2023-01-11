import java.util.List;

public class EntityCart {
    private String cartID;
    private List<EntityProduct> products;
    private List<Integer> quantities;
    private List<Double> totalCosts;

    public String getCartID() {
        return cartID;
    }

    public void setCartID(String cartID) {
        this.cartID = cartID;
    }

    public void get() {
        // TODO:
    }

    public void add(EntityProduct product, int quantity) {
        products.add(product);
        quantities.add(quantity);
        totalCosts.add(product.getProductPrice() * quantity);
    }
}
