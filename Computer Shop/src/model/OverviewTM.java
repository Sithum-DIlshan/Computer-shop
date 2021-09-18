package model;

import java.sql.Date;

public class OverviewTM {
    private String companyName;
    private String custId;
    private String orderId;
    private Date orderDate;
    private String qty;

    public OverviewTM(String companyName, String custId, String orderId, Date orderDate, String qty) {
        this.companyName = companyName;
        this.custId = custId;
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.qty = qty;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
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

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Override
    public String toString() {
        return "Order{" +
                "custId='" + custId + '\'' +
                ", orderId='" + orderId + '\'' +
                ", orderDate='" + orderDate + '\'' +
                ", qty='" + qty + '\'' +
                '}';
    }
}
