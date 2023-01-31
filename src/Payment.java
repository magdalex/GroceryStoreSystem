import java.util.Scanner;

public class Payment {

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    private String orderID;

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

    Payment(String orderID, String accountID) {
        this.orderID = orderID;
        //todo: db paymentID sequence
        this.accountID = accountID;
        isDelivered = "false";
    }

    public static void checkout(Scanner scan, Order order, Account account) {
        Payment payment = new Payment(order.getOrderID(), account.getEmail());
        while (true) {
            System.out.println("What will you use? Enter [default] or [new]:");
            String input = scan.nextLine();
            if (input.equalsIgnoreCase("default")) {
                payment.cardNum = account.getDefaultCardNum();
                payment.cardCVV = account.getDefaultCardCVV();
                payment.cardExp = account.getDefaultCardExp();
                payment.cardType = account.getDefaultCardType();
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
    }

    private static void addToDB(Payment payment) {
        //todo: add to db and create table
    }

    //todo: get payment ordered list from db by date and email for manage account
}