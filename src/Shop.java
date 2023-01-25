import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.sql.Statement;

public class Shop {

    private static List<EntityProduct> inventory = new ArrayList<EntityProduct>();
    private static List<EntityCart> carts = new ArrayList<EntityCart>();

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

    public static void Menu(Scanner scan, EntityAccount account) throws ClassNotFoundException {
        String key;
        Boolean loop = true;
        retrieveCarts();
        String cartID = "";
        do {
            cartID = new Random().nextLong(10000000, 999999999) + "";
            for (int i = 0; i < carts.size(); i++) {
                if (!carts.get(i).getCartID().contentEquals(cartID)) {
                    loop = false;
                }
            }
        } while (loop);
        EntityCart cart = new EntityCart(cartID);
        loop = true;
        while (loop) {
            System.out
                    .println(
                            "\nSelect an option:\n\t1.List all products\n\t2.Search\n\t3.Add Item to cart\n\t4.Checkout\n\t5.ManageAccount\n\t6.Exit");
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
                        // TODO: add FK to DB table carts - column productID
                        System.out.println("Enter the desired quantity");
                        int qty = Integer.valueOf(scan.nextLine());
                        EntityProduct p = inventory.stream().filter(e -> e.getProductID().equalsIgnoreCase(id))
                                .findFirst()
                                .get();
                        if (p.getProductAvailability() >= qty) {
                            cart.add(p, qty);
                            System.out.println("Item added");
                        } else {
                            System.out.println("Insufficient Stock, only " + p.getProductAvailability() + " "
                                    + p.getProductName() + " was added to cart");
                            cart.add(p, p.getProductAvailability());
                        }
                    } catch (Exception e) {
                        System.out.println("Bad input, try again.");
                    }
                    System.out.println(cart);
                    break;
                case "4":
                    System.out.println("This is the cart you're trying to checkout:");
                    System.out.println(cart);
                    while (true) {
                        System.out.println("continue to payment? yes/no:");
                        key = scan.nextLine();
                        if (key.equalsIgnoreCase("yes")) {
                            // TODO:decrease inventory
                            AddCartToDB(cart);
                            Checkout.Menu(scan, cart, account);
                            break;
                        } else if (key.equalsIgnoreCase("no")) {
                            break;
                        } else {
                            System.out.println("bad input, try again.");
                        }
                    }
                    break;
                case "5":
                    ManageAccount.menu(scan, account);
                    break;
                case "6":
                    loop = false;
                    break;
                default:
                    System.out.println("Bad input, try again.");
                    break;
            }
        }
    }

    public static void retrieveInvetory() throws ClassNotFoundException {
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

    public static void retrieveCarts() throws ClassNotFoundException {
        carts = new ArrayList<EntityCart>();
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String connectionUrl = LoginSystem.dbConnection;
        ResultSet resultSet = null;
        try (Connection connection = DriverManager.getConnection(connectionUrl);
                Statement statement = connection.createStatement();) {
            // Create and execute a SELECT SQL statement.
            String selectSql = "SELECT * FROM Carts";
            resultSet = statement.executeQuery(selectSql);

            // Print results from select statement
            while (resultSet.next()) {
                boolean cartExist = false;
                int cartIndex = 0;
                for (int i = 0; i < carts.size(); i++) {
                    if (carts.get(i).getCartID().contentEquals(resultSet.getString(1))) {
                        cartExist = true;
                        cartIndex = i;
                    }
                }
                String col2 = resultSet.getString(2);
                if (cartExist) {
                    carts.get(cartIndex).add(
                            inventory.stream().filter(p -> p.getProductID().contentEquals(col2)).findFirst().get(),
                            Integer.valueOf(resultSet.getString(3)));
                } else {
                    carts.add(new EntityCart(resultSet.getString(1)));
                    carts.get(carts.size() - 1).add(
                            inventory.stream().filter(p -> p.getProductID().contentEquals(col2)).findFirst().get(),
                            Integer.valueOf(resultSet.getString(3)));
                }
            }
        }

        // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
