package logging;

public class UserEmployeeModel {
    private String fullName;
    private String position;
    private int activeAccount;

    public UserEmployeeModel() {
    }

    public UserEmployeeModel(String fullName, String position,int activeAccount) {
        this.fullName = fullName;
        this.position = position;
        this.activeAccount = activeAccount;

    }

    @Override
    public String toString() {
        return "UserEmployeeModel{" +
                "fullName='" + fullName + '\'' +
                ", position='" + position + '\'' +
                ", activeAccount=" + activeAccount +
                '}';
    }

    public String getFullName() {
        return fullName;
    }

    public String getPosition() {
        return position;
    }
    public boolean hasActive(){
        return activeAccount == 1;
    }

}
