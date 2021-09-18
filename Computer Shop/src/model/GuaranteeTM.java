package model;

public class GuaranteeTM {
    private String guaranteeId;
    private String guaranteePeriod;
        private String itemCode;

    public GuaranteeTM(String guaranteeId, String guaranteePeriod, String itemCode) {
        this.guaranteeId = guaranteeId;
        this.guaranteePeriod = guaranteePeriod;
        this.itemCode = itemCode;
    }

    public GuaranteeTM() {

    }

    public String getGuaranteeId() {
        return guaranteeId;
    }

    public void setGuaranteeId(String guaranteeId) {
        this.guaranteeId = guaranteeId;
    }

    public String getGuaranteePeriod() {
        return guaranteePeriod;
    }

    public void setGuaranteePeriod(String guaranteePeriod) {
        this.guaranteePeriod = guaranteePeriod;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    @Override
    public String toString() {
        return "GuaranteeTM{" +
                "guaranteeId='" + guaranteeId + '\'' +
                ", guaranteePeriod='" + guaranteePeriod + '\'' +
                ", itemCode='" + itemCode + '\'' +
                '}';
    }
}
