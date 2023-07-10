package model;

public class EmployeeAccountModel {
    private String nameAccount;
    private String passAccount;
    private boolean isActiveAccount;

    public EmployeeAccountModel(String nameAccount, String passAccount) {
        this.nameAccount = nameAccount;
        this.passAccount = passAccount;
    }

    public EmployeeAccountModel(String nameAccount, String passAccount, boolean isActiveAccount) {
        this.nameAccount = nameAccount;
        this.passAccount = passAccount;
        this.isActiveAccount = isActiveAccount;
    }

    public String getNameAccount() {
        return nameAccount;
    }

    public String getPassAccount() {
        return passAccount;
    }
    public boolean isActiveAccount() {
        return isActiveAccount;
    }

    public int getStateActive() {
        return isActiveAccount ? 1 : 0;
    }

    @Override
    public String toString() {
        return "EmployeeAccountModel{" +
                "nameAccount='" + nameAccount + '\'' +
                ", passAccount='" + passAccount + '\'' +
                ", isActiveAccount=" + isActiveAccount +
                '}';
    }
}
