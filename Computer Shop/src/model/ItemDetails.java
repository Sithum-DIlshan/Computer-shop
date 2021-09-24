package model;

public class ItemDetails {
    private String itemCode;
    private int orderedQty;
    private double discount;

    public ItemDetails(String itemCode, int orderedQty, double discount) {
        this.itemCode = itemCode;
        this.orderedQty = orderedQty;
        this.discount = discount;
    }

    public ItemDetails() {
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public int getOrderedQty() {
        return orderedQty;
    }

    public void setOrderedQty(int orderedQty) {
        this.orderedQty = orderedQty;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }
}
