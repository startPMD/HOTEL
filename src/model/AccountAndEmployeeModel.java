package model;

public class AccountAndEmployeeModel {
    private EmployeeAccountModel account;
    private EmployeeModel employee;

    public AccountAndEmployeeModel(EmployeeAccountModel account, EmployeeModel employee) {
        this.account = account;
        this.employee = employee;
    }

    public EmployeeAccountModel getAccount() {
        return account;
    }

    public EmployeeModel getEmployee() {
        return employee;
    }

    public void setAccount(EmployeeAccountModel account) {
        this.account = account;
    }

    public void setEmployee(EmployeeModel employee) {
        this.employee = employee;
    }

    public boolean hasValueEmpty(){

        return false;
    }

    @Override
    public String toString() {
        return "AccountAndEmployeeModel{" +
                "account=" + account +
                ", employee=" + employee +
                '}';
    }

}