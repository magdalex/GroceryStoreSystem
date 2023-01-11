import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.sql.Statement;

public class TestInventory {
    // class for initially seeding the product database
    // or for testing the logic using a temporary makeshift database
    private static List<EntityProduct> inventory = new ArrayList<EntityProduct>();

    public static List<EntityProduct> search(String keyword, boolean searchName, boolean searchDescription,
            boolean searchCategory) {
        List<EntityProduct> productResults = new ArrayList<EntityProduct>();

        for (EntityProduct entityProduct : inventory) {
            if (searchName) {
                if (entityProduct.getProductName().toLowerCase().contains(keyword.toLowerCase()))
                    productResults.add(entityProduct);
            }
            if (searchCategory) {
                if (!productResults.contains(entityProduct)) {
                    if (entityProduct.getProductCategory().toLowerCase().contains(keyword.toLowerCase())) {
                        productResults.add(entityProduct);
                    }
                }
            }
            if (searchDescription) {
                if (!productResults.contains(entityProduct)) {
                    if (entityProduct.getProductDescription().toLowerCase().contains(keyword.toLowerCase())) {
                        productResults.add(entityProduct);
                    }
                }
            }
        }
        return productResults;
    }

    public static void Menu(Scanner scan) {
        String key;
        Boolean loop = true;
        EntityCart cart = new EntityCart(new Random().nextLong(10000000, 999999999) + "");
        while (loop) {

            System.out
                    .println("\nSelect an option:\n\t1.List all products\n\t2.Search\n\t3.Add Item to cart\n\t4.Exit");
            key = scan.nextLine();
            switch (key) {
                case "1":
                    inventory.forEach(System.out::println);
                    break;
                case "2":
                    System.out.println("Enter the keyword:");
                    key = scan.nextLine();
                    search(key, true, true, true).forEach(System.out::println);
                    break;
                case "3":
                    try {
                        System.out.println("Enter the product ID:");
                        String id = scan.nextLine();
                        System.out.println("Enter the desired quantity");
                        int qty = Integer.valueOf(scan.nextLine());
                        EntityProduct p = inventory.stream().filter(e -> e.getProductID().equalsIgnoreCase(id))
                                .findFirst()
                                .get();
                        cart.add(p, qty);

                    } catch (Exception e) {
                        System.out.println("Bad input, try again.");
                    }
                    System.out.println(cart);
                    break;
                case "4":
                    loop = false;
                    break;
                default:
                    System.out.println("Bad input, try again.");
                    break;
            }
        }

    }

    public static void retrieveDB() throws ClassNotFoundException {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String connectionUrl = "jdbc:sqlserver://vanier-grocery-service.database.windows.net:1433;database=VanierGroceryService;user=remyAzure@vanier-grocery-service;password=Vanier1212;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";

        ResultSet resultSet = null;
        try (Connection connection = DriverManager.getConnection(connectionUrl);
                Statement statement = connection.createStatement();) {
            // Create and execute a SELECT SQL statement.
            String selectSql = "SELECT * FROM Products";
            resultSet = statement.executeQuery(selectSql);

            // Print results from select statement
            while (resultSet.next()) {
                inventory.add(new EntityProduct(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3),
                        Double.valueOf(resultSet.getString(4)), resultSet.getString(5),
                        Integer.valueOf(resultSet.getString(6))));
            }
        }

        // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
