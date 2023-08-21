package Entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Payment {
    private String paymentID;
    private String paymentMethod;
    private LocalDateTime paymentTime;
    private static int payCount = 1;

    public Payment(){
        
    }
    
    public Payment(String paymentID,String paymentMethod,LocalDateTime paymentTime){
        this.paymentID = String.format("PM%06d", payCount);
        payCount++;
        this.paymentMethod = paymentMethod;
        this.paymentTime = paymentTime;
    }
    
    public Payment(String paymentID){
        this.paymentID = paymentID;
    }
    
    public Payment(String paymentID , String paymentMethod){
        this.paymentID = paymentID ; 
        this.paymentMethod = paymentMethod;
    }
    
    
    public String getPaymentID() {
        return paymentID;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public LocalDateTime getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentID(String paymentID) {
        this.paymentID = paymentID;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
    
    public LocalDateTime setLocalDateTime(){
        LocalDateTime ldt = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");  
        String formatDateTime = ldt.format(format);   
        LocalDateTime ldt2 = LocalDateTime.parse(formatDateTime, format);
        
        return ldt2;
    }

    public void setPaymentTime(LocalDateTime paymentTime) {
        this.paymentTime = setLocalDateTime();
    }
    
    public String getDate(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return paymentTime.format(formatter); 
    }
    
    
}
