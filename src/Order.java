import java.sql.*;
import java.util.Date;
import java.util.Scanner;

public class Order {
    private String orderID;
    private Account accountLink;
    private String paymentID;
    private Cart cartLink;
    private Date orderDate;
    private double totalCost;
    private String deliveryType;
    private String paid;

    Order(Account AccountLink, Cart CartLink) throws ClassNotFoundException {
        //orderID
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String connectionUrl = Main.dbConnection;
        try (Connection connection = DriverManager.getConnection(connectionUrl);
             Statement statement = connection.createStatement()) {
            // Create and execute an insert SQL statement.
            String sql = "SELECT NEXT VALUE FOR OrderSequence";
            ResultSet rs = statement.executeQuery(sql);
            if (rs.next())
                orderID = rs.getString(1);
        } catch (Exception e) {
        }
        this.cartLink = CartLink;
        this.accountLink = AccountLink;
        orderDate = new Date();
        totalCost = CartLink.getCartCost();
        paid = "false";
    }

    Order(Account account, String orderID) {
        this.accountLink = account;
        this.orderID = orderID;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (Exception e) {
        }
        String connectionUrl = Main.dbConnection;
        try (Connection connection = DriverManager.getConnection(connectionUrl);
             Statement statement = connection.createStatement()) {
            // Create and execute an insert SQL statement.
            String sql = "Select * from Orders where orderID = '" + orderID + "'";
            ResultSet rs = statement.executeQuery(sql);
            if (rs.next()) {

                this.paymentID = rs.getString(3);
                try {
                    this.cartLink = new Cart(rs.getString(4));
                } catch (Exception e) {
                }
                this.orderDate = rs.getDate(5);
                this.totalCost = Double.parseDouble(rs.getString(6));
                this.deliveryType = rs.getString(7);
                this.paid = rs.getString(8);
            }
        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getPaid() {
        return paid;
    }

    public void setPaid(String paid) {
        this.paid = paid;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public Account getAccountLink() {
        return accountLink;
    }

    public void setAccountLink(Account accountLink) {
        this.accountLink = accountLink;
    }

    public String getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(String paymentID) {
        this.paymentID = paymentID;
    }

    public Cart getCartLink() {
        return cartLink;
    }

    public void setCartLink(Cart cartLink) {
        this.cartLink = cartLink;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public String getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
    }

    public static void order(Scanner scan, Cart cart, Account account) throws ClassNotFoundException {
        Order order = new Order(account, cart);
        for (int i = 0; i < cart.getCartSize(); i++) {
            String productID = cart.getProduct(i).getProductID();
            Product toAdd = null;
            try {
                toAdd = Product.getProductFromDB(productID);
            } catch (Exception e) { }
            if (toAdd != null) {
                int quantity = cart.getQuantity(i);
                    if (quantity > toAdd.getProductAvailability()) {
                        System.out.println("insufficient availability for "+toAdd.getProductName()+". " + toAdd.getProductAvailability() + " in cart instead of " + quantity + ".");
                        quantity = toAdd.getProductAvailability();
                    }
                cart.add(toAdd, quantity);
                Product.adjustInventory(toAdd.getProductID(), toAdd.getProductAvailability()- cart.getQuantity(i));
            }
        }
        Cart.AddCartToDB(cart);
        while (true) {
            System.out.println("What type of order will this be? Enter [pickup] or [delivery]:");
            String input = scan.nextLine();
            if (input.equalsIgnoreCase("pickup")) {
                order.deliveryType = "Pickup";
                break;
            }
            if (input.equalsIgnoreCase("delivery")) {
                order.deliveryType = "Delivery";
                break;
            }
            System.out.println("bad input, try again.");
        }
        System.out.println("Your Total is:\n\tOrder: " + order.totalCost + "\n\t" + order.deliveryType + ": " + (order.deliveryType.equalsIgnoreCase("pickup") ? 1.99 : 5.99));
        System.out.println("\tTaxes: " + (order.deliveryType.equalsIgnoreCase("pickup") ? 1.99 * 0.15 : 5.99 * 0.15));
        order.totalCost += (order.deliveryType.equalsIgnoreCase("pickup") ? 1.99 * 1.15 : 5.99 * 1.15);
        System.out.println("\tTOTAL: " + order.totalCost);
        addToDB(order);
        Payment.checkout(scan, order, account);
    }

    private static void addToDB(Order order) throws ClassNotFoundException {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        try (Connection connection = DriverManager.getConnection(Main.dbConnection); Statement statement = connection.createStatement()) {
            // Create and execute an insert SQL statement.
            String sql = "insert into Orders(orderID,accountID,cartID,totalCost,deliveryType)Values('" + order.orderID + "','" + order.accountLink.getEmail() + "','" + order.cartLink.getCartID() + "','" + order.totalCost + "','" + order.deliveryType + "');";
            int rowsUpdated = statement.executeUpdate(sql);
            if (rowsUpdated < 1) throw new SQLException("zero row updated");
        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateDB(Order order) throws ClassNotFoundException {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        try (Connection connection = DriverManager.getConnection(Main.dbConnection); Statement statement = connection.createStatement()) {
            // Create and execute an insert SQL statement.
            String sql = "UPDATE Orders SET  paymentID = '" + order.paymentID + "', isPaid = 'true' WHERE orderID = '" + order.orderID + "' ";
            int rowsUpdated = statement.executeUpdate(sql);
            if (rowsUpdated < 1) throw new SQLException("zero row updated");
        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "orderID='" + orderID + '\'' +
                ", paymentID='" + paymentID + '\'' +
                ", cartLink=" + cartLink +
                ", orderDate=" + orderDate +
                ", totalCost=" + totalCost +
                ", deliveryType='" + deliveryType + '\'' +
                ", paid=" + paid +
                '}';
    }
}