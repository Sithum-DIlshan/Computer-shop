package model;

public class AddtoCartTM {
    private String itemCode;
    private String description;
    private Double unitPrice;
    private Integer orderedQty;
    private Double total;

    public AddtoCartTM() {
    }

    public AddtoCartTM(String itemCode, String description, Double unitPrice, Integer orderedQty, Double total) {
        this.itemCode = itemCode;
        this.description = description;
        this.unitPrice = unitPrice;
        this.orderedQty = orderedQty;
        this.total = total;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getOrderedQty() {
        return orderedQty;
    }

    public void setOrderedQty(Integer orderedQty) {
        this.orderedQty = orderedQty;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "AddtoCartTM{" +
                "itemCode='" + itemCode + '\'' +
                ", description='" + description + '\'' +
                ", unitPrice=" + unitPrice +
                ", orderedQty=" + orderedQty +
                ", total=" + total +
                '}';
    }
}
