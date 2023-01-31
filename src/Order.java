import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
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
    private boolean paid;

    Order(Account AccountLink, Cart CartLink) throws ClassNotFoundException {
        //orderID
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String connectionUrl = Main.dbConnection;
        try (Connection connection = DriverManager.getConnection(connectionUrl);
             Statement statement = connection.createStatement()) {
            // Create and execute an insert SQL statement.
            String sql = "SELECT NEXT VALUE FOR OrderSequence";
            ResultSet rs = statement.executeQuery(sql);
            if(rs.next())
                orderID = rs.getString(1);
        }
        catch (Exception e){
        }
        this.cartLink = CartLink;
        this.accountLink = AccountLink;
        orderDate = new Date();
        totalCost = CartLink.getCartCost();
        paid = false;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
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
        Order order = new Order(account,cart);
        //todo:adjust inventory, notify of changes
        Cart.AddCartToDB(cart);
        while(true) {
            System.out.println("What type of order will this be? Enter [pickup] or [delivery]:");
            String input = scan.nextLine();
            if(input.equalsIgnoreCase("pickup")){
                order.deliveryType = "Pickup";
                break;
            }
            if(input.equalsIgnoreCase("delivery")){
                order.deliveryType = "Delivery";
                break;
            }
            System.out.println("bad input, try again.");
        }
        System.out.println("Your Total is:\n\tOrder: "+order.totalCost+"\n\t"+order.deliveryType+"\n\t"+ (order.deliveryType.equalsIgnoreCase("pickup")? 1.99:5.99));
        System.out.println("\tTaxes: "+(order.deliveryType.equalsIgnoreCase("pickup")? 1.99*0.15:5.99*0.15));
        order.totalCost += (order.deliveryType.equalsIgnoreCase("pickup")? 1.99*1.15:5.99*1.15);
        addToDB(order);
        Payment.checkout(scan, order, account);
    }

    private static void addToDB(Order order){
        //todo: add order to db
    }
    public static void updateDB(Order order){
        //todo: update db (after payment) to isPaid="true";
    }
}