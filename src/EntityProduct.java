import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

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

    // DB functions
    public static ArrayList<EntityProduct> search(String keyword) throws ClassNotFoundException {
        ArrayList<EntityProduct> results = new ArrayList<EntityProduct>();
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String connectionUrl = LoginSystem.dbConnection;
        ResultSet resultSet = null;
        try (Connection connection = DriverManager.getConnection(connectionUrl);
                Statement statement = connection.createStatement();) {
            // Create and execute a SELECT SQL statement.
            String selectSql = "SELECT * FROM Products WHERE productCategory LIKE '%" + keyword
                    + "%' or productName like '%" + keyword + "%' or productDescription LIKE '%" + keyword + "%'";
            resultSet = statement.executeQuery(selectSql);
            // Print results from select statement
            while (resultSet.next()) {
                results.add(new EntityProduct(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3),
                        Double.valueOf(resultSet.getString(4)), resultSet.getString(5),
                        Integer.valueOf(resultSet.getString(6))));
            }
        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

    public static ArrayList<EntityProduct> listAll() throws ClassNotFoundException {
        ArrayList<EntityProduct> results = new ArrayList<EntityProduct>();
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String connectionUrl = LoginSystem.dbConnection;
        ResultSet resultSet = null;
        try (Connection connection = DriverManager.getConnection(connectionUrl);
                Statement statement = connection.createStatement();) {
            // Create and execute a SELECT SQL statement.
            String selectSql = "SELECT * FROM Products";
            resultSet = statement.executeQuery(selectSql);

            // Print results from select statement
            while (resultSet.next()) {
                results.add(new EntityProduct(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3),
                        Double.valueOf(resultSet.getString(4)), resultSet.getString(5),
                        Integer.valueOf(resultSet.getString(6))));
            }
        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

    public static void adjustInventory(String ID, int quantity) {
        // TODO:
    }
}
