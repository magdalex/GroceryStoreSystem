import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class Product {
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
        return "[ID] " + productID + ", [Product] " + productName + ", [Description] " + productDescription + ", [Availability] " + productAvailability + ", [Category] " + productCategory + ", [Price] " + String.format("$%.2f",productPrice);
    }

    // constructors
    Product() {

    }

    public Product(String productID, String productName, String productDescription, double productPrice, String productCategory, int productAvailability) {
        this.productID = productID;
        this.productName = productName;
        this.productDescription = productDescription;
        this.productPrice = productPrice;
        this.productCategory = productCategory;
        this.productAvailability = productAvailability;
    }

    // DB functions
    public static ArrayList<Product> search(String keyword) throws ClassNotFoundException {
        ArrayList<Product> results = new ArrayList<>();
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String connectionUrl = Main.dbConnection;
        ResultSet resultSet;
        try (Connection connection = DriverManager.getConnection(connectionUrl); Statement statement = connection.createStatement()) {
            // Create and execute a SELECT SQL statement.
            String selectSql = "SELECT * FROM Products WHERE productCategory LIKE '%" + keyword + "%' or productName like '%" + keyword + "%' or productDescription LIKE '%" + keyword + "%'";
            resultSet = statement.executeQuery(selectSql);
            // Print results from select statement
            while (resultSet.next()) {
                results.add(new Product(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), Double.parseDouble(resultSet.getString(4)), resultSet.getString(5), Integer.parseInt(resultSet.getString(6))));
            }
        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }
    public static ArrayList<Product> searchCategory(String keyword) throws ClassNotFoundException {
        ArrayList<Product> results = new ArrayList<>();
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String connectionUrl = Main.dbConnection;
        ResultSet resultSet;
        try (Connection connection = DriverManager.getConnection(connectionUrl); Statement statement = connection.createStatement()) {
            // Create and execute a SELECT SQL statement.
            String selectSql = "SELECT * FROM Products WHERE productCategory = '" + keyword + "'";
            resultSet = statement.executeQuery(selectSql);
            // Print results from select statement
            while (resultSet.next()) {
                results.add(new Product(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), Double.parseDouble(resultSet.getString(4)), resultSet.getString(5), Integer.parseInt(resultSet.getString(6))));
            }
        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

    public static ArrayList<String> getCategories() throws ClassNotFoundException {
        ArrayList<String> results = new ArrayList<>();
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String connectionUrl = Main.dbConnection;
        ResultSet resultSet;
        try (Connection connection = DriverManager.getConnection(connectionUrl); Statement statement = connection.createStatement()) {
            // Create and execute a SELECT SQL statement.
            String selectSql = "Select productCategory from Products group by productCategory";
            resultSet = statement.executeQuery(selectSql);

            // Print results from select statement
            while (resultSet.next()) {
                results.add(resultSet.getString(1));
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
        String connectionUrl = Main.dbConnection;
        try (Connection connection = DriverManager.getConnection(connectionUrl); Statement statement = connection.createStatement()) {
            // Create and execute a SELECT SQL statement.
            String selectSql = "UPDATE Products SET productInventoryQuantity = " + quantity + " WHERE productID = " + ID;
            int rowsUpdated = statement.executeUpdate(selectSql);
            if (rowsUpdated < 1) throw new SQLException("zero row updated");
        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Product getProductFromDB(String ID) throws ClassNotFoundException {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String connectionUrl = Main.dbConnection;
        ResultSet resultSet;
        try (Connection connection = DriverManager.getConnection(connectionUrl); Statement statement = connection.createStatement()) {
            // Create and execute a SELECT SQL statement.
            String selectSql = "SELECT * FROM Products WHERE productID = " + ID;
            resultSet = statement.executeQuery(selectSql);
            if (resultSet.next()) {
                return new Product(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), Double.parseDouble(resultSet.getString(4)), resultSet.getString(5), Integer.parseInt(resultSet.getString(6)));
            }
        } catch (Exception e) {
        }
        return null;
    }

    public static void shopMenu(Scanner scan, Account account) throws ClassNotFoundException {
        Cart cart = new Cart();
        boolean loop = true;
        while (loop) {
            account = Account.getFromDB(account.getEmail(), account.getPassword());
            System.out.print("Accumulated points: $");
            System.out.printf("%.2f\n",account.getPointBalance()/100.00);
            System.out.println("--- Shopping Menu ---");
            System.out.println("\t1. List by category");
            System.out.println("\t2. Search items by keyword");
            System.out.println("\t3. Add item by ID");
            System.out.println("\t4. Checkout cart");
            System.out.println("\t5. Manage Account");
            System.out.println("\t6. Exit");
            String userInput = scan.nextLine();
            switch (userInput) {
                case "1" -> {
                    ArrayList<String> categories = getCategories();
                    System.out.println("CATEGORIES:");
                    categories.forEach(System.out::println);
                    String selected = "";
                    System.out.println("Enter the category you would like to see:");
                    while (true) {
                        String input = scan.nextLine();
                        selected = categories.stream().filter(s -> s.equalsIgnoreCase(input)).findFirst().get();
                        if (selected.equalsIgnoreCase("")) System.out.println("Invalid input, try again.");
                        else break;
                    }
                    searchCategory(selected).forEach(System.out::println);
                }
                case "2" -> {
                    System.out.println("Enter the keyword:");
                    search(scan.nextLine()).forEach(System.out::println);
                }
                case "3" -> {
                    System.out.println("Enter the ID:");
                    String productID = scan.nextLine();
                    Product toAdd = null;
                    try {
                        toAdd = getProductFromDB(productID);
                    } catch (Exception e) {
                        System.out.println("Item not found!");
                    }
                    if (toAdd != null) {
                        System.out.println("Enter the desired quantity:");
                        String qty = scan.nextLine();
                        int quantity = 0;
                        try {
                            quantity = Integer.parseInt(qty);
                            if (quantity < 1) throw new Exception();
                        } catch (Exception e) {
                            System.out.println("Quantity input was not a valid number");
                        }
                        if (quantity > 0) {
                            if (quantity > toAdd.productAvailability) {
                                System.out.println("Insufficient availability. " + toAdd.productAvailability + " added instead of " + quantity + ".");
                                quantity = toAdd.productAvailability;
                            }
                            cart.add(toAdd, quantity);
                            System.out.println(cart);
                        }
                    } else {
                        System.out.println("Item not found!");
                    }
                }
                case "4" -> {
                    boolean loop2 = true;
                    while (loop2) {
                        System.out.println("do you want to take this cart to checkout?");
                        System.out.println(cart);
                        System.out.println("Yes/No:");
                        String input = scan.nextLine();
                        switch (input.toLowerCase()) {
                            case "yes" -> {
                                Order.order(scan, cart, account);
                                loop2 = false;
                                cart = new Cart();
                            }
                            case "no" -> loop2 = false;
                            default -> System.out.println("Invalid input, try again.");
                        }
                    }
                }
                case "5" -> Account.editAccount(scan, account);
                case "6" -> loop = false;
                default -> System.out.println("Invalid input, try again.");
            }
        }
    }
}
