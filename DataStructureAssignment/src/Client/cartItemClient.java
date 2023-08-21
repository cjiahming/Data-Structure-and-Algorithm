/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;
import Entity.CartItem;
import Entity.Combination;
import Entity.Medicine;
import Entity.Patient;
import Entity.Payment;
import Entity.Symptom;
import adt.ArrayList;
import adt.ArrayStack;
import java.util.Scanner;

/**
 *
 * @author Gab
 */
public class cartItemClient {
    
    
    public static void main(String[] args) {
        
        
        
    }
    
    public static void addToCart(ArrayStack<CartItem> cartArrS,CartItem cartItem){
        
        //if already existed then edit the count if not then push 
        boolean existed = false;
        
        for(int i = 0 ; i < cartArrS.getTopIndex()+1;i++){
            CartItem localCartItem = cartArrS.peek(i);
            
            if (localCartItem.getCartItemID().equals( cartItem.getCartItemID())){
                //if existed
                editCartQty(cartArrS,new CartItem(cartItem.getCartItemID(),localCartItem.getQty()+cartItem.getQty(),localCartItem.getCombination(),localCartItem.getPatient(),localCartItem.getPayment()));
                existed = true;
            }
        }   
        
        if(existed = false){
            cartArrS.push(cartItem);
        }
        
    }
    
    public static int displayAllPatient(ArrayStack<CartItem> cartArrS,Patient patient){
        
        int count =0;
        

        System.out.println("========================================================");
        System.out.println("No|CartItemID|Medicine        |Qty|Price     |Sub Total|");
        System.out.println("========================================================");
        
        double grandtotal = 0 ;
        
        for(int i = 0 ; i < cartArrS.getTopIndex()+1;i++){
            //print all value 
            CartItem localCartItem = cartArrS.peek(i);
            
            if(patient.getUserID().equals(localCartItem.getPatient().getUserID()) && localCartItem.getPayment().getPaymentID()==null){
                //count subtotal 
                double subtotal = (localCartItem.getCombination().getMedicine().getmPrice()*localCartItem.getQty());
                
                System.out.printf("%d |%-10s|%-16s|%-3d|RM%-8.2f|RM%-7.2f|\n",i+1 , localCartItem.getCartItemID(),localCartItem.getCombination().getMedicine().getmName(),localCartItem.getQty(),localCartItem.getCombination().getMedicine().getmPrice(),subtotal);
                
                count++;
                
                grandtotal += subtotal;
            }
        }
        
        if(count==0){
            System.out.println("\n\n-------ALERT-------");
            System.out.println("Sorry , you do not have any medicine in cart for current moment!");
            System.out.println("-------ALERT-------\n\n");
        }
        else{
             System.out.println("========================================================");
             System.out.printf("Grand Total : RM%.2f\n",grandtotal);
        }
        
        if(count!=0){
            return count;
        }
        
        return -1;
    }
    
    public static int displayAllPayment(ArrayStack<CartItem> cartArrS,Payment payment){
        int count =0;
        for(int i = 0 ; i < cartArrS.getTopIndex()+1;i++){
            //print all value 
            CartItem localCartItem = cartArrS.peek(i);
            
            //check whether the paymentID is null or not (If null means not payment yet)
            if(localCartItem.getPayment().getPaymentID()!=null){
                    
                if(localCartItem.getPayment().getPaymentID().equals(payment.getPaymentID())){
                  
                    System.out.printf("|%-16s|%-3d|RM%-8.2f\n", localCartItem.getCombination().getMedicine().getmName(),localCartItem.getQty(),localCartItem.getCombination().getMedicine().getmPrice()*localCartItem.getQty());

                    count++;

                }
            }
        }
        
        return count;
    }
    
    public static ArrayStack<CartItem> deleteCartItem(ArrayStack<CartItem> cartArrS, CartItem cartItem ){
        
        for(int i = 0 ; i < cartArrS.getTopIndex()+1;i++){
            CartItem localCartItem = cartArrS.peek(i);
            
            if (localCartItem.getCartItemID().equals( cartItem.getCartItemID())){
                //delete the cartItem
                return cartArrS.removeElementAt(i);
               
            }
        }   
        return null;
    }
    
    public static CartItem retriveCartItem(ArrayStack<CartItem> cartArrS , CartItem cartItem){
        for(int i = 0 ; i < cartArrS.getTopIndex()+1;i++){
            CartItem localCartItem = cartArrS.peek(i);
            
            if (localCartItem.getCartItemID().equals( cartItem.getCartItemID())){
                //retrieve the cartItem
                return localCartItem;
               
            }
        }   
        
        //if not have return null
        return null;
        
        
    }
    
    public static void editCartQty(ArrayStack<CartItem> cartArrS ,CartItem cartItem ){
        //locate the index 
        for(int i = 0 ; i < cartArrS.getTopIndex();i++){
            CartItem localCartItem = cartArrS.peek(i);
            
            if (localCartItem.getCartItemID().equals( cartItem.getCartItemID())){
                //retrieve the cartItem
                cartArrS.deleteIndex(cartArrS , cartArrS.getTopIndex(),0,i);
                //push new items into it 
               cartArrS.push(cartItem);
            }
        }   
        
    }
        
    public static int Menu(){
        Scanner scan = new Scanner(System.in);
        boolean error = false;
        int choiceInt ;
        
        do{
        
        System.out.println("1.Display cart Item");
        System.out.println("2.Remove Cart Item");
        System.out.println("3.Edit Medicine Quantity");
        System.out.println("4.Proceed to Payment");
        System.out.println("5. Back");
        
        
        
        System.out.print("Enter your Choice : ");
        char userInput = scan.next().charAt(0);
        
        if(Character.isDigit(userInput)){
            //if is a digit (Convert it from char to int)
            choiceInt = Character.getNumericValue(userInput);
            
            //check whether its in range
            if(choiceInt > 5 || choiceInt < 1){
                //false 
                System.out.println("Invalid Choice : Not in Range");
                System.out.println("\n");
                error = true;
            }
            else{
                return choiceInt;
            }
        }
        else{
            System.out.println("Invalid Choice : Input not digit");
            System.out.println("\n");
            error = true; 
        }
        
        }while(error);
        
        return -1;
    }
    
    public static void editMenu(ArrayStack<CartItem> cartArrS, Patient patient){
        
        
        //display all of their records
        int countPatientCartRecords =displayAllPatient(cartArrS,patient);
        
        //if have records
        if(countPatientCartRecords>0){
        
        System.out.println("==============");
        System.out.println("|Edit Cart Qty|");
        System.out.println("==============");
        
        
        int choice = scanIndex(countPatientCartRecords,"Enter index number:");
  
        int qty = scanIndex(9,"Enter new Qty (1-9):");
        
        
        int count=0;
        
            for(int i = 0 ; i < cartArrS.getTopIndex()+1;i++){
                //print all value 
                CartItem localCartItem = cartArrS.peek(i);

                if(patient.getUserID().equals(localCartItem.getPatient().getUserID())){
                    //count subtotal 

                    if(count+1 == choice){
                        //change qty 
                        localCartItem.setQty(qty);

                        //edit the cartQty to new one
                        editCartQty(cartArrS, localCartItem);

                        System.out.println("======Successfully edited======");
                    }

                    count++;
                }
            }
        }else{
            //if no record
            System.out.println("Sorry you have no record to edit currently");
        }
        
        
    }
    
    public static int scanIndex(int Maximum , String question ){
        Scanner scan = new Scanner(System.in);
        boolean error = false;
        int choiceInt ;
        
       
        do{
        System.out.printf(question);
        String userInput = scan.next();
        int countNotDigit = 0;
        
        for(int i = 0 ; i<userInput.length();i++){
            
            if(!Character.isDigit(userInput.charAt(i))){
                //if found the character is not digit 
                //direct break
                countNotDigit +=1;
                break;
            }
        }
        
        if(countNotDigit==0){
            //if is a digit (Convert it from char to int)
            choiceInt = Integer.parseInt(userInput);
            
            //check whether its in range
            if(choiceInt > Maximum || choiceInt < 0){
                //false 
                System.out.println("Invalid Choice : Not in Range");
                
                error = true;
            }
            else{
                return choiceInt;
            }
        }
        else{
            System.out.println("Invalid Choice : Input not digit");
            error = true; 
        }
        
        }while(error);
        
        return -1;
    }
    
    public static ArrayStack<CartItem> deleteMenu(ArrayStack<CartItem> cartArrS, Patient patient){
        
        
        //display all of their records
        int countPatientCartRecords =displayAllPatient(cartArrS,patient);
        
        if(countPatientCartRecords>0){
        System.out.println("=================");
        System.out.println("Delete Cart Item");
        System.out.println("=================");
            
            
        int choice = scanIndex(countPatientCartRecords,"Enter index number:");
        
        
        int count=0;
        
        
        
            for(int i = 0 ; i < cartArrS.getTopIndex()+1;i++){
                //print all value 
                CartItem localCartItem = cartArrS.peek(i);

                if(patient.getUserID().equals(localCartItem.getPatient().getUserID())){
                    //count subtotal 

                    if(count+1 == choice){
                      
                        //edit the cartQty to new one
                        cartArrS = deleteCartItem(cartArrS, localCartItem);

                        System.out.println("======Successfully deleted======");
                        
                        return cartArrS;
                    }

                    count++;
                }
            }
        }
        else{
            System.out.println("Sorry you currenty have no record to delete");
        }
        
        return cartArrS;
    }
    
    
    public static ArrayList<Payment> filterPaymentIDbyPatient(ArrayStack<CartItem> cartArrS , Patient patient){
        //Get all cartItemID
            //Filter under the patient 
                //add PaymentID into string 
                
        ArrayList<Payment> paymentArr = new ArrayList<Payment>();       
                
        int count =0;
        
        for(int i = 0 ; i < cartArrS.getTopIndex()+1;i++){
             
            CartItem localCartItem = cartArrS.peek(i);
            
            if(patient.getUserID().equals(localCartItem.getPatient().getUserID())){
               
                if(localCartItem.getPayment().getPaymentID()!=null){
                    //if not null means they have already pay

                    paymentArr.add(localCartItem.getPayment());

                    count++;
                }
                
                
            }
        }
        
        
        
        if(count>0){
             return paymentArr;
        }else if (count==0){
            return null;
        }
        
        return null;
    }
    
    
    

}
