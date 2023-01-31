import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Cart {
    private String cartID;
    private List<Product> products = new ArrayList<>();
    private List<Integer> quantities = new ArrayList<>();
    private List<Double> totalCosts = new ArrayList<>();

    Cart() throws ClassNotFoundException {
        cartID = "";
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String connectionUrl = Main.dbConnection;
        try (Connection connection = DriverManager.getConnection(connectionUrl);
             Statement statement = connection.createStatement()) {
            // Create and execute an insert SQL statement.
            String sql = "SELECT NEXT VALUE FOR CartSequence";
            ResultSet rs = statement.executeQuery(sql);
            if(rs.next())
                cartID = rs.getString(1);
        }
        catch (Exception e){
        }
    }

    public String getCartID() {
        return cartID;
    }

    public Product getProduct(int listPosition) {
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

    public void add(Product product, int quantity) {
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

    public static void AddCartToDB(Cart cart) throws ClassNotFoundException {

        if (cart.getCartSize() >= 1) {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String connectionUrl = Main.dbConnection;
            try (Connection connection = DriverManager.getConnection(connectionUrl);
                 Statement statement = connection.createStatement()) {
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

    //todo: get cart method

}
