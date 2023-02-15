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
            if (rs.next())
                cartID = rs.getString(1);
        } catch (Exception e) {
        }
    }

    Cart(String cartID) throws ClassNotFoundException {
        this.cartID = cartID;
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String connectionUrl = Main.dbConnection;
        try (Connection connection = DriverManager.getConnection(connectionUrl);
                Statement statement = connection.createStatement()) {
            // Create and execute an insert SQL statement.
            String sql = "Select * from Carts where cartID = '" + cartID + "'";
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                add(Product.getProductFromDB(rs.getString(2)), rs.getInt(3));
            }
        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
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
        totalCosts.set(listPosition, products.get(listPosition).getProductPrice() * quantities.get(listPosition));
        return totalCosts.get(listPosition);
    }

    public Integer getCartSize() {
        return products.size();
    }

    public void add(Product product, int quantity) {
        if (products.stream().filter(p -> p.getProductID().equalsIgnoreCase(product.getProductID())).count() < 1) {
            products.add(product);
            quantities.add(quantity);
            totalCosts.add(product.getProductPrice() * quantity);
        } else {
            for (int i = 0; i < products.size(); i++) {
                if (products.get(i).getProductID().equalsIgnoreCase(product.getProductID())) {
                    quantities.add(i, quantity);
                    totalCosts.add(i, product.getProductPrice() * quantity);
                }
            }

        }
    }

    public void removeProduct(int productId) {
        for (int i = 0; i < products.size(); i++) {
            if (Integer.parseInt(products.get(i).getProductID()) == (productId)) {
                products.remove(i);
                quantities.remove(i);
                totalCosts.remove(i);
            }
        }
    }

    @Override
    public String toString() {
        double cartTotal = 0;
        String output = "[cartID] " + cartID;
        for (int i = 0; i < products.size(); i++) {
            output += "\n" + products.get(i) + " ";
            output += ", [Quantity] " + quantities.get(i);
            output += ", [Total Cost] " + String.format("$%.2f", totalCosts.get(i));
            cartTotal += totalCosts.get(i);
        }
        output += "\n[Cart Total] " + String.format("$%.2f", cartTotal);
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
}
