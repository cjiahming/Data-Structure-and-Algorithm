/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import Entity.CartItem;
import Entity.Combination;
import Entity.Delivery;
import Entity.Medicine;
import Entity.Patient;
import Entity.Payment;
import Entity.Symptom;
import adt.ArrayList;

import adt.ArrayStack;
import adt.DynamicQueue;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
/**
 *
 * @author Gab
 */
public class DeliveryClient {
    
    public static void main(String args[]){
        
        
        
    }
    
    public static void addDelivery(DynamicQueue<Delivery> deliveryQ,Delivery newdelivery){
        deliveryQ.enqueue(newdelivery);
    }
    
    
    //does not validate whose the user , need add userID 
    public static void displayAllDelivery(DynamicQueue<Delivery> deliveryQ){
        if(deliveryQ.isEmpty() != true){
            int count = 0 ; 
            
            System.out.println("==============Display all Delivery==========");
            System.out.println("|Delivery ID | Delivery Status | PaymentID |");
            
            Iterator iterator = deliveryQ.getIterator();

            while(iterator.hasNext()){
                Delivery deliveryl = (Delivery)iterator.next();
                
                System.out.printf("|%-15s%-17s%-10s|\n" , deliveryl.getdID() , deliveryl.getdStatus() , deliveryl.getPayment().getPaymentID());
                
                count++;
            }
            System.out.println("============================================");
            System.out.printf("Total %d delivery found!\n\n", count);
        }else{
            System.out.println("ERROR : The Queue is empty\n\n");
        }
    }
    
    
    
    public static int findDynamicStackIndex(DynamicQueue<Delivery> deliveryQ,Delivery deliver){
        Iterator iterator = deliveryQ.getIterator();
        int index = 0 ; 
            while(iterator.hasNext()){
                Delivery delivery1 = (Delivery)iterator.next();
                
                if(delivery1.getdID().equals(deliver.getdID())){
                    //if found the index then 
                    return index;
                }
                
                //if not found 
                index++;
            }
            
        return -1;
    }
    
    public static void editDeliveryStatus (DynamicQueue<Delivery> deliveryQ){
        int found = 0 ;
        if(deliveryQ.isEmpty() != true){
            //read index
            Scanner scan = new Scanner(System.in);
            System.out.println("======================");
            System.out.println("|Edit Delivery Status|");
            System.out.println("======================");
            System.out.print("Enter the Delivery ID:");
            String inputD = scan.nextLine().toUpperCase();
            
            
            Iterator iterator = deliveryQ.getIterator();

            while(iterator.hasNext()){

                Delivery delivery1 = (Delivery)iterator.next();

                if(delivery1.getdID().equals(inputD)){
                    found = 1 ;
                    //if found ask for which to choose
                    System.out.println("=======Status Update Option========");
                    System.out.println("|        1. Packaging             |");
                    System.out.println("|        2. Deliver               |");
                    System.out.println("|        3. Receive               |");
                    System.out.println("===================================");
                    
                    int ans =0;
                    do{
                    ans = scanIndex(3,"Choose Update Status Option :");
                    if(ans == 0 ){
                        System.out.println("Choice cannot be 0 !");
                    }
                    }while(ans == 0);

                    String status ="";

                    switch(ans){
                        case 1:
                            status = "Packaging";
                            break;
                        case 2:
                            status = "Deliver";
                            break;
                        case 3:
                            status = "Receive";
                            break;

                    }

                    //create new Delivery 
                    Delivery deliveryNew = delivery1;
                    deliveryNew.setdStatus(status);

                    //find the index of the delivery queue
                    int index = findDynamicStackIndex(deliveryQ,deliveryNew);

                    //replace the exisiting delivery
                    deliveryQ.replace(index ,deliveryNew );

                    System.out.println("Successfully Edited ");
                }

            }
                
            if(found ==0){
                System.out.printf("No Delivery with %s found in the system\n\n",inputD);
            }
            
        }
    }
    
    //search specific records 
    public static void searchDelivery(DynamicQueue<Delivery> deliveryQ, ArrayStack<CartItem> cartArrS){
        //declare the 
        cartItemClient cartItemClient = new cartItemClient();
        
        
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter the Delivery ID:");
        String inputD = scan.nextLine().toUpperCase();
        
        int found = 0 ; 
        
        if(deliveryQ.isEmpty()!=true){
            Iterator iterator = deliveryQ.getIterator();

            while(iterator.hasNext()){
                Delivery delivery1 = (Delivery)iterator.next();
                
                if(delivery1.getdID().toUpperCase().equals(inputD)){
                    found = 1;
                    //if its the same then display out
                    System.out.println("=========================================");
                    System.out.printf("|Delivery ID : %s\n",delivery1.getdID());
                    System.out.printf("|Delivery Status : %s\n",delivery1.getdStatus());
                    System.out.printf("|PaymentID : %s\n",delivery1.getPayment().getPaymentID());
                    System.out.println("=========================================");
                    
                    int count = cartItemClient.displayAllPayment(cartArrS, delivery1.getPayment());
                    
                    if(count >0){
                        //If there is cart item only print this
                        System.out.println("=========================================");
                        System.out.printf("|%d total cartItem in the delivery\n",count);
                        System.out.println("=========================================");
                    }
                    else{
                        System.out.println("No cartItem found in the delivery");
                    }
                    
                    
                }
                
                
            }
        }
        
        //if not found 
        if(found==0){
            System.out.printf("Search for %s not found!",inputD);
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
    
    public static void deleteDelivery(DynamicQueue<Delivery> deliveryQ){
        int found = 0 ;
        //scan the delivery Id
        Scanner scan = new Scanner(System.in);
        System.out.println("================Delete Delivery===============");

        System.out.print("Enter the Delivery ID:");
        String inputD = scan.nextLine().toUpperCase();
        
        if(deliveryQ.isEmpty()!=true){
            Iterator iterator = deliveryQ.getIterator();

            while(iterator.hasNext()){
                Delivery delivery1 = (Delivery)iterator.next();
                
                //if detect the same delivery
                if(delivery1.getdID().toUpperCase().equals(inputD)){
                    found = 1;
                    char choice;
                    do{
                    System.out.printf("Confirm want to delete ? (Y = yes , N =no) :");
                    choice = scan.next().charAt(0);   
                    }while(Character.toUpperCase(choice) != 'Y' && Character.toUpperCase(choice) !='N' );
                    
                    
                    if(Character.toUpperCase(choice) == 'Y'){
                        
                         //get the index
                        int index = findDynamicStackIndex(deliveryQ,delivery1)+1;
                        
                        
                        //then remove
                        Delivery removedeliver = deliveryQ.removeAt(index);
                        

                        System.out.printf("Successfully removed %s\n", inputD);
                    }else{
                        System.out.printf("%s cancelled remove\n",inputD);    
                    }
                    
                }
                
                
            }
        }
        //if didnt found the delivery
        if(found==0){
            System.out.printf("%s delivery not found!\n",inputD);
        }
        
        
    }
    
    public static void initialise(DynamicQueue<Delivery> deliveryQ ){
        Delivery delivery = new Delivery("Ongoing" , new Payment("P0001","Credit Card"));
        Delivery delivery1 = new Delivery("Ongoing" , new Payment());
        Delivery delivery2 = new Delivery("Ongoing" , new Payment());
        
        addDelivery(deliveryQ , delivery);
        addDelivery(deliveryQ , delivery1);
        addDelivery(deliveryQ , delivery2);
        
        
    }
    
    public static void subMenu(DynamicQueue<Delivery> deliveryQ, ArrayStack<CartItem> cartArrS){
        int choice = 0 ; 
        
        do{
                choice =0;
            System.out.println("==========Delivery Menu==========");
            System.out.println("|1.Add Delivery                 |");
            System.out.println("|2.Edit Delivery                |");
            System.out.println("|3.Display All Delivery         |");
            System.out.println("|4.Search specific Delivery     |");
            System.out.println("|5.Delete Delivery              |");
            System.out.println("|6.Quit Delivery Module         |");
             System.out.println("==========Delivery Menu==========");

            choice = scanIndex(6,"Enter Choice:");

            switch(choice){
                case 1:
                    //ADD

                    break;
                case 2:
                    editDeliveryStatus(deliveryQ);
                    break;
                case 3:
                    displayAllDelivery(deliveryQ);
                    break;
                case 4:
                    searchDelivery(deliveryQ,cartArrS);
                    break;
                case 5:
                    //Delete deliverty
                    deleteDelivery(deliveryQ);
                    break;
                case 6:
                    //Quit module
                    System.out.println("Successfully Quite Delivery Module");
                    break;
            }
        }while(choice != 6);
    }
   
    
    public static int removeDuplicateElements(String arr[], int length){  
        if (length==0 || length==1){  
            return length;  
        }  
        String[] temp = new String[length];  
        int j = 0;  
        for (int i=0; i<length-1; i++){  
            if (!arr[i].equals(arr[i+1])){  
                temp[j++] = arr[i];  
            }  
         }  
        temp[j++] = arr[length-1];     
        // Changing original array  
        for (int i=0; i<j; i++){  
            arr[i] = temp[i];  
        }  
        return j;  
    }  
    
    public static void displayDeliveryCustomer(ArrayStack<CartItem> cartArrS , Patient patient,DynamicQueue<Delivery> deliveryQ2){
        
        
        if(!cartArrS.isEmpty() && !deliveryQ2.isEmpty()){
        //payment stack
        ArrayList<Payment> paymentArrS = new ArrayList<Payment>();
        
        
        int count = 0 ; 
        for(int i = 0 ; i < cartArrS.getTopIndex()+1;i++){
            //print all value 
            CartItem localCartItem = cartArrS.peek(i);
            
            if(localCartItem.getPayment().getPaymentID()!=null){
               
                //check the cart Item belongs to the user and got payment dy
                if(localCartItem.getPatient().getUserID().equals(patient.getUserID())){

                    count++;

                    if(count == 1 ){
                        //if its the first one 
                            paymentArrS.add(localCartItem.getPayment());
                            
                    }else if (count >1){
                        
                        //check whether it already exist in the local payment stack
                        int exist = 0 ; 
                         for(int y = 1 ; y <= paymentArrS.getLength(); y++){
                             
                             //if not the same then add into stack
                             if(paymentArrS.getEntry(y).getPaymentID().equals(localCartItem.getPayment().getPaymentID())==true){
                                 exist++;
                             }
                         }
                         
                         if(exist==0){
                             paymentArrS.add(localCartItem.getPayment());
                         }
                         
                    }
                }
            }
        }
        
        
        if(paymentArrS.getLength()!=0){
        
        
        
        System.out.println("==============Display all Delivery==========");
        
        System.out.println("|Delivery ID | Delivery Status | PaymentID |");
        
        Iterator iterator = deliveryQ2.getIterator();
        
        while(iterator.hasNext()){
                   Delivery deliveryl = (Delivery)iterator.next();

                   for(int i = 1 ; i <=paymentArrS.getLength() ; i++){
                       //validate user delivery using payment ID 
                       
                        if(paymentArrS.getEntry(i).getPaymentID().equals(deliveryl.getPayment().getPaymentID())){
                             System.out.printf("|%-15s%-17s%-10s|\n" , deliveryl.getdID() , deliveryl.getdStatus() , deliveryl.getPayment().getPaymentID());

                             count++;
                             
                        }
                       
                   }
               }
        System.out.println("============================================");
    }
        else{
            System.out.println("No delivery record found !");
        }
        }
    }
}
    
    
