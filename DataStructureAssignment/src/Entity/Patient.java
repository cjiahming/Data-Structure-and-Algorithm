/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

/**
 *
 * @author Gab
 */
public class Patient {
    private String userID ; 
    private String username;
    private String icNo;
    private String password;
    private int age;
    private double weight;
    private String address;
    private boolean isAdmin;

    public Patient(){}
    
    public Patient(String userID,String username,String icNo,
            String password,int age , double weight , String address,boolean isAdmin){
        this.userID = userID;
        this.username = username;
        this.icNo = icNo;
        this.password = password;
        this.age = age;
        this.weight = weight; 
        this.address = address;
        this.isAdmin = isAdmin;
    }
    
    public Patient(String userID){
        this.userID = userID;
    }
    
    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIcNo() {
        return icNo;
    }

    public void setIcNo(String icNo) {
        this.icNo = icNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }
    
    

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    
    
}
