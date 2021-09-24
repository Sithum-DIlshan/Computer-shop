package model;

import java.util.ArrayList;
import java.util.Date;

public class OrderPlaceTM {
private String orderId;
private Date orderDate;
private String customerId;
private double totalCost;
private ArrayList<ItemDetails> items;

    public OrderPlaceTM(String orderId, Date orderDate, String customerId, double totalCost, ArrayList<ItemDetails> items) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.customerId = customerId;
        this.totalCost = totalCost;
        this.items = items;
    }

    public OrderPlaceTM() {
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public ArrayList<ItemDetails> getItems() {
        return items;
    }

    public void setItems(ArrayList<ItemDetails> items) {
        this.items = items;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }
}
