package model;

public class RepairsTM {
    private String repairId;
    private String repairType;
    private double cost;
    private String custId;

    public RepairsTM(String repairId, String repairType, double cost, String custId) {
        this.repairId = repairId;
        this.repairType = repairType;
        this.cost = cost;
        this.custId = custId;
    }

    public RepairsTM() {
    }

    public String getRepairId() {
        return repairId;
    }

    public void setRepairId(String repairId) {
        this.repairId = repairId;
    }

    public String getRepairType() {
        return repairType;
    }

    public void setRepairType(String repairType) {
        this.repairType = repairType;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    @Override
    public String toString() {
        return "RepairsTM{" +
                "repairId='" + repairId + '\'' +
                ", repairType='" + repairType + '\'' +
                ", cost=" + cost +
                ", custId='" + custId + '\'' +
                '}';
    }
}
