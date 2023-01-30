import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

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
                + ", [Availability] " + productAvailability + ", [Category] " + productCategory
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
        ArrayList<EntityProduct> results = new ArrayList<>();
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String connectionUrl = LoginSystem.dbConnection;
        ResultSet resultSet;
        try (Connection connection = DriverManager.getConnection(connectionUrl);
             Statement statement = connection.createStatement()) {
            // Create and execute a SELECT SQL statement.
            String selectSql = "SELECT * FROM Products WHERE productCategory LIKE '%" + keyword
                    + "%' or productName like '%" + keyword + "%' or productDescription LIKE '%" + keyword + "%'";
            resultSet = statement.executeQuery(selectSql);
            // Print results from select statement
            while (resultSet.next()) {
                results.add(new EntityProduct(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3),
                        Double.parseDouble(resultSet.getString(4)), resultSet.getString(5),
                        Integer.parseInt(resultSet.getString(6))));
            }
        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

    public static ArrayList<EntityProduct> getAll() throws ClassNotFoundException {
        ArrayList<EntityProduct> results = new ArrayList<>();
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String connectionUrl = LoginSystem.dbConnection;
        ResultSet resultSet;
        try (Connection connection = DriverManager.getConnection(connectionUrl);
             Statement statement = connection.createStatement()) {
            // Create and execute a SELECT SQL statement.
            String selectSql = "SELECT * FROM Products";
            resultSet = statement.executeQuery(selectSql);

            // Print results from select statement
            while (resultSet.next()) {
                results.add(new EntityProduct(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3),
                        Double.parseDouble(resultSet.getString(4)), resultSet.getString(5),
                        Integer.parseInt(resultSet.getString(6))));
            }
        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

    public static void adjustInventory(String ID, int quantity) throws ClassNotFoundException {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String connectionUrl = LoginSystem.dbConnection;
        try (Connection connection = DriverManager.getConnection(connectionUrl);
             Statement statement = connection.createStatement()) {
            // Create and execute a SELECT SQL statement.
            String selectSql = "UPDATE Products SET productInventoryQuantity = " + quantity + " WHERE productID = "
                    + ID;
            int rowsUpdated = statement.executeUpdate(selectSql);
            if (rowsUpdated < 1)
                throw new SQLException("zero row updated");
        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static EntityProduct getProductFromDB(String ID) throws ClassNotFoundException, SQLException {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String connectionUrl = LoginSystem.dbConnection;
        ResultSet resultSet;
        try (Connection connection = DriverManager.getConnection(connectionUrl);
             Statement statement = connection.createStatement()) {
            // Create and execute a SELECT SQL statement.
            String selectSql = "SELECT * FROM Products WHERE productID = " + ID;
            resultSet = statement.executeQuery(selectSql);
            return new EntityProduct(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3),
                    Double.parseDouble(resultSet.getString(4)), resultSet.getString(5),
                    Integer.parseInt(resultSet.getString(6)));
        }
    }

    public static void shopMenu(Scanner scan, EntityAccount account) throws ClassNotFoundException {
        EntityCart cart = new EntityCart();
        boolean loop = true;
        while (loop) {
            System.out.println("--- Shopping Menu ---");
            System.out.println("\t1. List all items");
            System.out.println("\t2. Search items by keyword");
            System.out.println("\t3. Add item by ID");
            System.out.println("\t4. Checkout cart");
            System.out.println("\t5. Back to main menu");
            String userInput = scan.nextLine();
            switch (userInput) {
                case "1":
                    getAll().forEach(System.out :: println);
                    break;
                case "2":
                    System.out.println("Enter the keyword:");
                    search(scan.nextLine()).forEach(System.out :: println);
                    break;
                case "3":
                    System.out.println("Enter the ID:");
                    String productID = scan.nextLine();
                    EntityProduct toAdd = null;
                    try {
                        toAdd = getProductFromDB(productID);
                    } catch (Exception e){
                        System.out.println("Item not found!");
                    }
                    if (toAdd != null)
                    {
                        System.out.println("Enter the desired quantity:");
                        String qty = scan.nextLine();
                        int quantity = 0;
                        try {
                            quantity = Integer.parseInt(qty);
                            if (quantity < 1)
                                throw new Exception();
                        } catch (Exception e){
                            System.out.println("Quantity input was not a valid number");
                        }
                        if(quantity > 0){
                            cart.add(toAdd, quantity);
                        }
                    }
                    break;
                case "4":
                    boolean loop2 = true;
                    while(loop2){
                        System.out.println("do you want to take this cart to checkout?");
                        System.out.println(cart);
                        System.out.println("Yes/No:");
                        String input = scan.nextLine();
                        switch (input.toLowerCase()){
                            case "yes":
                                EntityOrder order = new EntityOrder(account, cart);
                                //TODO: call order main method: order.menu(scan);
                                loop = false;
                                break;
                            case "no":
                                loop = false;
                                break;
                            default:
                                System.out.println("Invalid input, try again.");
                        }
                    }
                    break;
                case "5":
                    //todo: link to main menu
                    loop = false;
                    break;
                default:
                    System.out.println("Invalid input, try again.");
            }
        }
    }
}
