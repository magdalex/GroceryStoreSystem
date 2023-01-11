import java.util.ArrayList;
import java.util.List;

public class EntityCart {
    private String cartID;
    private List<EntityProduct> products = new ArrayList<EntityProduct>();
    private List<Integer> quantities = new ArrayList<Integer>();
    private List<Double> totalCosts = new ArrayList<Double>();

    EntityCart(String cartID) {
        this.cartID = cartID;
    }

    public String getCartID() {
        return cartID;
    }

    public EntityProduct getProduct(int listPosition) {
        return products.get(listPosition);
    }

    public Integer getQuantity(int listPosition) {
        return quantities.get(listPosition);
    }

    public Double getTotalCost(int listPosition) {
        return totalCosts.get(listPosition);
    }

    public void add(EntityProduct product, int quantity) {
        products.add(product);
        quantities.add(quantity);
        totalCosts.add(product.getProductPrice() * quantity);
    }

    @Override
    public String toString() {
        double cartTotal = 0;
        String output = "[cartID] " + cartID;
        for (int i = 0; i < products.size(); i++) {
            output += "\n" + products.get(i) + " ";
            output += ", [Quantity] " + quantities.get(i);
            output += ", [Total Cost] " + totalCosts.get(i);
            cartTotal += totalCosts.get(i);
        }
        output += "\n[Cart Total] " + cartTotal;
        return output;
    }
}
