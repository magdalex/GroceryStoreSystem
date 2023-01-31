import java.util.Date;

public class EntityOrder {
    private String OrderID;
    private Account AccountLink;
    private String PaymentID;
    private Cart CartLink;
    private Date OrderDate;
    private double TotalCost;
    private String DeliveryType;
    private boolean paid;

    EntityOrder(Account AccountLink, Cart CartLink) {
        this.CartLink = CartLink;
        this.AccountLink = AccountLink;
        OrderDate = new Date();
        TotalCost = CartLink.getCartCost();
        paid = false;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public String getOrderID() {
        return OrderID;
    }

    public void setOrderID(String orderID) {
        OrderID = orderID;
    }

    public Account getAccountLink() {
        return AccountLink;
    }

    public void setAccountLink(Account accountLink) {
        AccountLink = accountLink;
    }

    public String getPaymentID() {
        return PaymentID;
    }

    public void setPaymentID(String paymentID) {
        PaymentID = paymentID;
    }

    public Cart getCartLink() {
        return CartLink;
    }

    public void setCartLink(Cart cartLink) {
        CartLink = cartLink;
    }

    public Date getOrderDate() {
        return OrderDate;
    }

    public void setOrderDate(Date orderDate) {
        OrderDate = orderDate;
    }

    public double getTotalCost() {
        return TotalCost;
    }

    public void setTotalCost(double totalCost) {
        TotalCost = totalCost;
    }

    public String getDeliveryType() {
        return DeliveryType;
    }

    public void setDeliveryType(String deliveryType) {
        DeliveryType = deliveryType;
    }
}