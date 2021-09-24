package model;

public class ItemsTM {
    private String code;
    private String description;
    private String brand;
    private Double unitPrice;
    private Integer qtyOnHand;

    public ItemsTM() {
    }

    public ItemsTM(String code, String description, String brand, Double unitPrice, Integer qtyOnHand) {
        this.code = code;
        this.description = description;
        this.brand = brand;
        this.unitPrice = unitPrice;
        this.qtyOnHand = qtyOnHand;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getQtyOnHand() {
        return qtyOnHand;
    }

    public void setQtyOnHand(Integer qtyOnHand) {
        this.qtyOnHand = qtyOnHand;
    }

    @Override
    public String toString() {
        return "ItemsTM{" +
                "code='" + code + '\'' +
                ", description='" + description + '\'' +
                ", brand='" + brand + '\'' +
                ", unitPrice='" + unitPrice + '\'' +
                ", qtyOnHand='" + qtyOnHand + '\'' +
                '}';
    }
}
