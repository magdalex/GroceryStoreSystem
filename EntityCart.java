package com.example.sysdevproject;

public class EntityCart {
    private String cartID;
    private String cartItemID;
    private int quantity;
    private double totalCost;

    public EntityCart() {
    }

    public EntityCart(String cartID, String cartItemID, int quantity, double totalCost) {
        this.cartID = cartID;
        this.cartItemID = cartItemID;
        this.quantity = quantity;
        this.totalCost = totalCost;
    }

    public String getCartID() {
        return cartID;
    }

    public void setCartID(String cartID) {
        this.cartID = cartID;
    }

    public String getCartItemID() {
        return cartItemID;
    }

    public void setCartItemID(String cartItemID) {
        this.cartItemID = cartItemID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }
}
