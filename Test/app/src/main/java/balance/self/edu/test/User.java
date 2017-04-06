package balance.self.edu.test;

/**
 * Created by bobbybakshi on 11/4/16.
 */

public class User {

    private int age;
    private String email;
    private String sex;
    private double weight;
    private String userName;
    private String ID;
    private String UserType;

    public User(int aa, String email, String sex, String username, double weight,String ID, String UserType){
        this.age = aa;
        this.email = email;
        this.sex = sex;
        this.userName = username;
        this.weight = weight;
        this.ID = ID;
        this.UserType = UserType;
    }
    public User(int aa, String email, String sex, String username, double weight,String ID){
        this.age = aa;
        this.email = email;
        this.sex = sex;
        this.userName = username;
        this.weight = weight;
        this.ID = ID;
    }
    public User(int aa, String email, String sex, String username, double weight){
        this.age = aa;
        this.email = email;
        this.sex = sex;
        this.userName = username;
        this.weight = weight;
    }
    public User(){

    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getUserType() {
        return UserType;
    }

    public void setUserType(String userType) {
        UserType = userType;
    }









}
