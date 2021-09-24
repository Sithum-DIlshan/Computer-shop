package model;

public class PermissionTM {
    private String permissionId;
    private String accountId;

    public PermissionTM(String permissionId, String accountId) {
        this.permissionId = permissionId;
        this.accountId = accountId;
    }

    public PermissionTM() {
    }

    public String getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(String permissionId) {
        this.permissionId = permissionId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    @Override
    public String toString() {
        return "PermissionTM{" +
                "permissionId='" + permissionId + '\'' +
                ", accountId='" + accountId + '\'' +
                '}';
    }
}
