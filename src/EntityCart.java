import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EntityCart {
    private String cartID;
    private List<EntityProduct> products = new ArrayList<EntityProduct>();
    private List<Integer> quantities = new ArrayList<Integer>();
    private List<Double> totalCosts = new ArrayList<Double>();

    EntityCart() {
        //TODO: get next cart ID from DB / implement incrementing number function in DB
        cartID = "";
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

    public Integer getCartSize() {
        return products.size();
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

    public double getCartCost() {
        double cartTotal = 0;
        for (int i = 0; i < products.size(); i++) {
            cartTotal += totalCosts.get(i);
        }
        return cartTotal;
    }

    public static void AddCartToDB(EntityCart cart) throws ClassNotFoundException {

        if (cart.getCartSize() >= 1) {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String connectionUrl = LoginSystem.dbConnection;
            try (Connection connection = DriverManager.getConnection(connectionUrl);
                 Statement statement = connection.createStatement();) {
                // Create and execute an insert SQL statement.
                String sql = "insert into Carts(cartID,productID,quantity,totalCost)Values";
                for (int i = 0; i < cart.getCartSize(); i++) {
                    sql += "('" + cart.getCartID() + "',";
                    sql += "'" + cart.getProduct(i).getProductID() + "',";
                    sql += "'" + cart.getQuantity(i) + "',";
                    sql += "'" + cart.getTotalCost(i) + "'),";
                }
                sql = sql.substring(0, sql.lastIndexOf(","));
                sql += ";";
                int rowsUpdated = statement.executeUpdate(sql);
                if (rowsUpdated < 1)
                    throw new SQLException("zero row updated");
            }
            // Handle any errors that may have occurred.
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
