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
public class Delivery {
    private String dID;
    private String dStatus;
    private Payment payment;
    private static int deliveryCount = 1;
    
    public Delivery(){
        
    }
    
    public Delivery(String dStatus,Payment payment){
        this.dID = String.format("DEL%06d", deliveryCount);
        deliveryCount++;
        this.dStatus = dStatus;
        this.payment = payment;
    }

    public String getdID() {
        return dID;
    }

    public void setdID(String dID) {
        this.dID = dID;
    }

    public String getdStatus() {
        return dStatus;
    }

    public void setdStatus(String dStatus) {
        this.dStatus = dStatus;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }
}
