import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Payment {

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }


    public String getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(String paymentID) {
        this.paymentID = paymentID;
    }

    public String getAccountID() {
        return accountID;
    }

    public void setAccountID(String accountID) {
        this.accountID = accountID;
    }

    public String getCardFirstName() {
        return cardFirstName;
    }

    public void setCardFirstName(String cardFirstName) {
        this.cardFirstName = cardFirstName;
    }

    public String getCardLastName() {
        return cardLastName;
    }

    public void setCardLastName(String cardLastName) {
        this.cardLastName = cardLastName;
    }

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    public String getCardCVV() {
        return cardCVV;
    }

    public void setCardCVV(String cardCVV) {
        this.cardCVV = cardCVV;
    }

    public String getCardExp() {
        return cardExp;
    }

    public void setCardExp(String cardExp) {
        this.cardExp = cardExp;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getIsDelivered() {
        return isDelivered;
    }

    public void setIsDelivered(String isDelivered) {
        this.isDelivered = isDelivered;
    }

    private String paymentID;
    private String accountID;
    private String cardFirstName;
    private String cardLastName;
    private String cardNum;
    private String cardCVV;
    private String cardExp;
    private String cardType;
    private String isDelivered;
    private String orderID;

    Payment(String orderID, String accountID) throws ClassNotFoundException {
        this.orderID = orderID;
        paymentID = "";
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String connectionUrl = Main.dbConnection;
        try (Connection connection = DriverManager.getConnection(connectionUrl);
             Statement statement = connection.createStatement()) {
            // Create and execute an insert SQL statement.
            String sql = "SELECT NEXT VALUE FOR PaymentSequence";
            ResultSet rs = statement.executeQuery(sql);
            if (rs.next())
                paymentID = rs.getString(1);
        } catch (Exception e) {
        }
        this.accountID = accountID;
        isDelivered = "false";
    }

    public Payment(String paymentID, String accountID, String cardFirstName, String cardLastName, String cardNum, String cardCVV, String cardExp, String cardType, String isDelivered, String orderID) {
        this.paymentID = paymentID;
        this.accountID = accountID;
        this.cardFirstName = cardFirstName;
        this.cardLastName = cardLastName;
        this.cardNum = cardNum;
        this.cardCVV = cardCVV;
        this.cardExp = cardExp;
        this.cardType = cardType;
        this.isDelivered = isDelivered;
        this.orderID = orderID;
    }

    public static void checkout(Scanner scan, Order order, Account account) throws ClassNotFoundException {
        Payment payment = new Payment(order.getOrderID(), account.getEmail());
        account = Account.getFromDB(account.getEmail(), account.getPassword());
        order.setPaymentID(payment.paymentID);
        System.out.print("Accumulated points: $");
        System.out.printf("%.2f\n",account.getPointBalance()/100.00);
        System.out.println("Do you want to use your points? (yes/no)");
        String answer = scan.nextLine();
        while (true) {
            if (answer.equalsIgnoreCase("yes")) {
                System.out.print("order total: ");
                System.out.printf("$%.2f\n", order.getTotalCost());
                System.out.print("point total: ");
                System.out.printf("$%.2f\n", account.getPointBalance()/100.00);
                if (account.getPointBalance() / 100.00 >= order.getTotalCost()) {
                    System.out.print("point used: ");
                    System.out.printf("$%.2f\n", order.getTotalCost());
                    account.setPointBalance(account.getPointBalance() - (int) (order.getTotalCost() * 100));
                    System.out.print("point left: ");
                    System.out.printf("$%.2f\n", account.getPointBalance()/100.00);
                } else {
                    System.out.print("point used: ");
                    System.out.printf("$%.2f\n", account.getPointBalance()/100.00);
                    order.setTotalCost(order.getTotalCost()-account.getPointBalance()/100.00);
                    account.setPointBalance(0);
                    System.out.print("point left: ");
                    System.out.printf("$%.2f\n", account.getPointBalance()/100.00);
                }
                System.out.print("new order total: ");
                System.out.printf("$%.2f\n", order.getTotalCost());
                Account.updateDB(account);

                break;
            } else if (answer.equalsIgnoreCase("no")) {
                break;
            } else {
                System.out.println("Please enter a valid answer!");
                answer = scan.nextLine();
            }
        }


        while (true) {
            System.out.println("What card will you use? Enter [default] or [new]:");
            String input = scan.nextLine();
            if (input.equalsIgnoreCase("default")) {
                payment.cardNum = account.getDefaultCardNum();
                payment.cardCVV = account.getDefaultCardCVV();
                payment.cardExp = account.getDefaultCardExp();
                payment.cardType = account.getDefaultCardType();
                payment.cardFirstName = account.getDefaultFirstCardName();
                payment.cardLastName = account.getDefaultCardLastName();
                break;
            }
            if (input.equalsIgnoreCase("new")) {
                boolean loop = true;
                do {
                    System.out.println("Input card holder's first name:");
                    String firstName = scan.nextLine();
                    while (!Account.isDigit(firstName)) {
                        System.out.println("Re-input first name:");
                        firstName = scan.nextLine();
                    }
                    payment.cardFirstName = firstName.toLowerCase();

                    System.out.println("Input card holder's last name:");
                    String lastName = scan.nextLine();
                    while (!Account.isDigit(lastName)) {
                        System.out.println("Re-input last name:");
                        lastName = scan.nextLine();
                    }
                    payment.cardLastName = lastName.toLowerCase();

                    System.out.println("Input card number:");
                    String cardNum = scan.nextLine();
                    while (!Account.isCharacter(cardNum) || cardNum.length() != 16) {
                        if (cardNum.length() != 16) {
                            System.out.println("Error: Not enough digits.");
                        }
                        System.out.println("Re-input card number with no spaces or hyphens:");
                        cardNum = scan.nextLine();
                    }
                    payment.cardNum = cardNum;

                    System.out.println("Input card CVV:");
                    String cardCVV = scan.nextLine();
                    while (!Account.isCharacter(cardCVV) || cardCVV.length() != 3) {
                        System.out.println("Re-input CVV:");
                        cardCVV = scan.nextLine();
                    }
                    payment.cardCVV = cardCVV;

                    System.out.println("Input card expiration date [MM/YY]:");
                    String cardExp = scan.nextLine();
                    while (!Account.isValidExpFormat(cardExp) || !Account.isValidExp(cardExp)) {
                        System.out.println("Error: Re-input expiration date or type \"RESTART\" to enter a new card:");
                        cardExp = scan.nextLine();
                        if (cardExp.equalsIgnoreCase("restart")) {
                            break;
                        }
                    }
                    if (Account.isValidExpFormat(cardExp) && Account.isValidExp(cardExp)) {
                        loop = false;
                    }
                    payment.cardExp = cardExp;
                } while (loop);

                System.out.println("Input type of card [VISA/MASTERCARD ONLY]:");
                String cardType = scan.nextLine();
                while (!cardType.equalsIgnoreCase("visa") && !cardType.equalsIgnoreCase("mastercard")) {
                    System.out.println("Re-input card type:");
                    cardType = scan.nextLine();
                }
                payment.cardType = cardType.toLowerCase();
                break;
            }
            System.out.println("bad input, try again.");
        }
        Order.updateDB(order);

        addToDB(payment);

        System.out.println("Order and payment complete, Thank you for shopping here!");
        System.out.println("You have accumulated $"+String.format("%.2f",order.getTotalCost()/50.00)+" in points from this order!");
    }

    private static void addToDB(Payment payment) throws ClassNotFoundException {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        try (Connection connection = DriverManager.getConnection(Main.dbConnection); Statement statement = connection.createStatement()) {
            // Create and execute an insert SQL statement.
            String sql = "insert into Payments(paymentID,accountID,cardFirstName,cardLastName,cardNum,cardCVV,cardExp,cardType,orderID)Values('" + payment.paymentID + "','" + payment.accountID + "','" + payment.cardFirstName + "','" + payment.cardLastName + "','" + payment.cardNum + "','" + payment.cardCVV + "','" + payment.cardExp + "','" + payment.cardType + "','" + payment.orderID + "');";
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
        return "Payment{" +
                "paymentID='" + paymentID + '\'' +
                ", cardFirstName='" + cardFirstName + '\'' +
                ", cardLastName='" + cardLastName + '\'' +
                ", cardNum='" + cardNum + '\'' +
                ", cardCVV='" + cardCVV + '\'' +
                ", cardExp='" + cardExp + '\'' +
                ", cardType='" + cardType + '\'' +
                ", isDelivered='" + isDelivered + '\'' +
                ", orderID='" + orderID + '\'' +
                '}';
    }

    public static ArrayList<Payment> getAllPayments(String accountID) {

        ArrayList<Payment> payments = new ArrayList<>();
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (Exception e) {
        }
        String connectionUrl = Main.dbConnection;
        try (Connection connection = DriverManager.getConnection(connectionUrl);
             Statement statement = connection.createStatement()) {
            // Create and execute an insert SQL statement.
            String sql = "Select * from Payments where accountID = '" + accountID + "'";
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                payments.add(new Payment(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10)));
            }
        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
        }
        return payments;
    }
}