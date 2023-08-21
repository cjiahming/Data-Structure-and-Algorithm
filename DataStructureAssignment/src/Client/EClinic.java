package Client;

import Client.DeliveryClient;
import static Client.JMPayment.paymentMenu;
import Entity.CartItem;
import Entity.Combination;
import Entity.Delivery;
import Entity.Medicine;
import Entity.Patient;
import Entity.Payment;
import Entity.Symptom;
import adt.*;
import java.util.InputMismatchException;
import java.lang.NullPointerException;
import java.util.Scanner;



public class EClinic{
    
    Scanner sc = new Scanner(System.in);
    private cartItemClient cartItemClient = new cartItemClient();
    private DeliveryClient deliveryClient = new DeliveryClient();
    public static int choice;
    
    private LinkedList<Medicine> medicineLinkList = new LinkedList<Medicine>();
    private LinkedList<Combination>combinationLinkList = new LinkedList<Combination>();
    private ArrayList<Symptom> symptomArrayList = new ArrayList<Symptom>();
    private ArrayStack<CartItem> cartArrS = new ArrayStack<CartItem>();
    private DynamicQueue<Delivery> deliveryQ = new DynamicQueue<Delivery>();
    //left payment
    ArrayStack<Payment> paymentArrStack = new ArrayStack<Payment>();
    //local patient
    Patient patient1 ;
    
    
    
    
    public void main(LinkedList<Medicine> medicineLinkList,LinkedList<Combination>combinationLinkList,ArrayList<Symptom> symptomArrayList,ArrayStack<CartItem> cartArrS,DynamicQueue<Delivery> deliveryQ ,Patient patient,ArrayStack<Payment> paymentArrStack) {
        this.medicineLinkList = medicineLinkList;
        this.combinationLinkList = combinationLinkList;
        this.symptomArrayList = symptomArrayList;
        this.cartArrS = cartArrS;
        this.deliveryQ=deliveryQ;
        
        //left payment
        this.paymentArrStack = paymentArrStack;
        //patient profile 
        this.patient1 = patient;
        
        

       mainMenu();
        
     
     
     }
    

    public EClinic(LinkedList<Medicine> medicineLinkList,LinkedList<Combination>combinationLinkList,ArrayList<Symptom> symptomArrayList,ArrayStack<CartItem> cartArrS,DynamicQueue<Delivery> deliveryQ ,Patient patient,ArrayStack<Payment> paymentArrStack){
         this.medicineLinkList = medicineLinkList;
        this.combinationLinkList = combinationLinkList;
        this.symptomArrayList = symptomArrayList;
        this.cartArrS = cartArrS;
        this.deliveryQ=deliveryQ;
        
        //left payment
        this.paymentArrStack = paymentArrStack;
        //patient profile 
        this.patient1 = patient;
   
     }
    
    public void cartItemInitialize(){
        CartItem cartItem = new CartItem(1,combinationLinkList.get(3) , patient1 ,new Payment("P0001","Credit Card") );
        CartItem cartItem2 = new CartItem(4,combinationLinkList.get(2) , patient1 ,new Payment("P0001","Credit Card") );
        CartItem cartItem3 = new CartItem(3,combinationLinkList.get(1) , patient1 ,new Payment("P0001","Credit Card") );
        CartItem cartItem4 = new CartItem(5,combinationLinkList.get(4) , patient1 ,new Payment() );
        
        cartArrS.push(cartItem);
        cartArrS.push(cartItem2);
        cartArrS.push(cartItem3);
        cartArrS.push(cartItem4);
    }
    
    public void deliveryInitialize(){
        Delivery delivery = new Delivery( "Received Order" , new Payment("P0001","Credit Card"));
        Delivery delivery1 = new Delivery("Received Order" , new Payment());
        Delivery delivery2 = new Delivery("Received Order" , new Payment());
        
        deliveryClient.addDelivery(deliveryQ , delivery);
        deliveryClient.addDelivery(deliveryQ , delivery1);
        deliveryClient.addDelivery(deliveryQ , delivery2);
    }
     
     public void deliveryMenu(){
         int deliveryChoice = 0 ; 
         do{
         
         
         System.out.println("==========================");
         System.out.println("|     Delivery Module    |");
         System.out.println("==========================");
         System.out.println("|1. Display delivery     |");
         System.out.println("|2. Retrieve delivery    |");
         System.out.println("|3. Edit delivery        |");    
         System.out.println("|4. Delete delivery      |");
         System.out.println("|5. Quit                 |");
         System.out.println("==========================");
         
         deliveryChoice = cartItemClient.scanIndex(5,"Enter your choice :");
         
         switch(deliveryChoice){
             case 1:
                 
                 if(patient1.isIsAdmin()){
                     //if is admin (Display all delivery
                        deliveryClient.displayAllDelivery(deliveryQ);
                 } else if (patient1.isIsAdmin() == false){
                    //if is user (Display All Delivery Customer)
                     ArrayList<Payment> paymentArrlocal = cartItemClient.filterPaymentIDbyPatient(cartArrS , patient1); 
                     
                     if(paymentArrlocal != null){
                         deliveryClient.displayDeliveryCustomer(cartArrS ,patient1, deliveryQ);
                     }else {
                         System.out.println("Please make payment first to view Delivery!");
                     }
                     
                     
                 }
               break;
             case 2:
                 //everyone can retrieve 
                 deliveryClient.searchDelivery(deliveryQ,cartArrS);
               break;
             case 3:
                 //only admin can edit delivery 
                 if(patient1.isIsAdmin()){
                     deliveryClient.editDeliveryStatus(deliveryQ);
                 }
                 else{
                     System.out.println("You do not have permission to edit!");
                 }
               break;
             case 4:
                 //only admin can delete delivery
                 if(patient1.isIsAdmin()){
                     deliveryClient.deleteDelivery(deliveryQ);
                 }
                 else{
                     System.out.println("You do not have permission to delete!");
                 }
               break;
             case 5:
                 //logout
               break;
         }
         }while(deliveryChoice!=5);
     }
    
    
     public void cartMenu(){
         int choice = 0 ;
         do{
            System.out.println("==========================");
            System.out.println("|       Cart Module      |");
            System.out.println("==========================");
            System.out.println("|1. Display Cart Item    |");
            System.out.println("|2. Delete Cart Item     |");
            System.out.println("|3. Edit Cart Item       |");
            System.out.println("|4. Quit Module          |");
            System.out.println("==========================");
            
            
            choice = cartItemClient.scanIndex(4,"Enter your choice :");
            
            

            switch(choice){
                case 1:
                    if(patient1.isIsAdmin()){
                        System.out.println("Admin does not have cart! ");
                    }else{
                        cartItemClient.displayAllPatient(cartArrS,patient1);
                    }
                    break;
               case 2:
                   sc.nextLine();
                   System.out.printf("Enter CartItemID to delete:");
                   
                   String input= sc.nextLine();

                   //local cart stack 
                   ArrayStack<CartItem> localcartArrS = new ArrayStack<CartItem>();

                   localcartArrS = cartItemClient.deleteCartItem(cartArrS, new CartItem(input));

                   if(localcartArrS == null ){
                       System.out.printf("Cart Item delete failed( %s not found )\n",input);
                   }
                   else {
                       cartArrS = localcartArrS ; 
                       System.out.println("Cart Item delete successfully");
                   }
                   break;
               case 3:
                   //edit cart 
                   sc.nextLine();
                   System.out.printf("Enter CartItemID to edit:");
                   
                   String input2= sc.nextLine();
                   
                   //find the cart Item
                   
                   CartItem localCartItem = cartItemClient.retriveCartItem(cartArrS ,new CartItem(input2));
                   
                   if(localCartItem!=null){
                       //if cartItem found 
                       int quantityEdit = 0 ; 
                       do{
                           
                       quantityEdit = cartItemClient.scanIndex(10 , "Enter the quantity (1-10 only) :");
                       
                       if(quantityEdit == 0 ){
                           System.out.println("ERROR : Quantity cannot be 0 ");
                       }
                       }while(quantityEdit == 0);
                       
                       //change the quantity (local cart Item)
                       localCartItem.setQty(quantityEdit) ; 
                       
                       cartItemClient.editCartQty(cartArrS , localCartItem);
                       
                       System.out.println("Cart Item edit successfully\n");
                       
                   }else{
                       System.out.printf("Cart Item edit failed( %s not found )\n",input2);
                   }
                   
                   break;
               case 4:
                   System.out.println("Successfully quit Cart \n\n");
                   break;
            }
         }while (choice!=4);
     }
     
    public void mainMenu(){
        
        
        do{
         boolean input=false;
       do{
       try{
              do{
                System.out.println("-----------------------------------------------------------");
                System.out.println("|\t\tChoose what you want to do\t\t  |");
                System.out.println("-----------------------------------------------------------");
                System.out.println("| 1.Health Assessment                                     |");
                System.out.println("| 2.Medicine Module                                       |");
                System.out.println("| 3.Symptoms Module                                       |");
                System.out.println("| 4.Combination Module                                    |");
                System.out.println("| 5.My Cart                                               |");
                System.out.println("| 6.Proceed to Payment                                    |");
                System.out.println("| 7.Delivery Module                                       |");
                System.out.println("| 8.Logout                                                |");
                System.out.println("-----------------------------------------------------------");
                System.out.print("Selection : ");
                Scanner sl=new Scanner(System.in);
                choice = sl.nextInt();
              input=false;
              }while(choice<=0 || choice>8);
              
       }catch(InputMismatchException ex){
           input=true;
           System.out.println("\t\t\t\t\tPlease enter from 1 to 4 only.\n" );
       }
       }while(input); 
       
       switch(choice){
           case 1:
               clientMenu();
               break;
           case 2:
               if(patient1.isIsAdmin()){
               medicineMenu();
               }else{
                   System.out.println("You do not have permission!");
               }
               break;
           case 3:
               if(patient1.isIsAdmin()){
               symptomMenu();
               }
               else{
                   System.out.println("You do not have permission!");
               }
               break;
           case 4:
               
               if(patient1.isIsAdmin()){
               combinationMenu();
               }
               else{
                   System.out.println("You do not have permission!");
               }
               break;
           case 5:
               //display cart Item
               cartMenu();
               break;
           case 6:
               paymentMenu(paymentArrStack, patient1, cartArrS,deliveryQ);
               break;
            case 7:
                //delivery
                deliveryMenu();
                break;
            case 8:
            //logout
            break;
           default: 
               break;
       }
    }while(choice != 8);
  }
    
///++-----------------------------------------------------------------------------------++//
//||                                client module                                      ||//
//++-----------------------------------------------------------------------------------++//
    public void clientMenu(){
        char question;
        
       
        System.out.println("\n\n");
        System.out.println("-----------------------------------------------------------");
        System.out.println("|\t\tWelcome to our E-Clinic \t\t  |");
        System.out.println("-----------------------------------------------------------");
        
       
         do{
            System.out.print("Do you have any sympthoms (Y/N) ? ");
            question=sc.next().charAt(0);
            }
         while(question!='N'&&question!='n'&&question!='Y'&&question!='y');
         
         if(question=='Y'||question=='y'){
             displaySymptomList();
         }
         else if(question=='N'||question=='n'){
             mainMenu();
         }
         
        
         
     }
    
    public void displaySymptomList(){
        
        ArrayList<Symptom> symp =new ArrayList<Symptom>();
        String symptom2;
        Scanner sc = new Scanner(System.in);
        char choice3,choice4;
        boolean found=false,found2=false;
        
        //Local linked list to keep the combination user get
        LinkedList<Combination> userCombination = new LinkedList<Combination>();
        
                   
        System.out.println("+----------------------------------------------------------------------------------------+");
        System.out.println("|No.  | Symptom\t\t      | Symtom Description\t\t\t\t         |");
        System.out.println("+----------------------------------------------------------------------------------------+");
        int no=1;
        
        for(int i=1;i<=symptomArrayList.getLength();i++){
            
            System.out.printf("|%-5s| %-21s | %-57s|\n",i,symptomArrayList.getEntry(i).getSName(),symptomArrayList.getEntry(i).getSDesc());
           
        }
        System.out.println("+----------------------------------------------------------------------------------------+");
       
        do{
           do{
           try{
                found=false;
            System.out.print("\nWhat symptom do you have?(0 to exit) : ");
            int sympNo= sc.nextInt();
            
            if(sympNo <0 || sympNo >= symptomArrayList.getLength()){
                System.out.println(" Out of Range !");
                found=true;
            }
            
            else if(sympNo==0){
                clientMenu();
            }
            
            else{
            symp.add(new Symptom(symptomArrayList.getEntry(sympNo).getSID(),symptomArrayList.getEntry(sympNo).getSName(),symptomArrayList.getEntry(sympNo).getSDesc()));
            }
            
            }catch(InputMismatchException e){
             System.out.println("Please enter INTEGER only!");
               sc.nextLine();
               found=true;
            }
           
           
           }while(found);
        
           do{
               found2=false;
            Scanner c2 = new Scanner(System.in);
            System.out.print("\nAny Other Symptom? (Y/N) : ");
            choice3=c2.next().charAt(0);
           
            if(choice3 == 'N' || choice3=='n'){
                System.out.println("+------------------------------------------------------------------------------------------------------------------------------------------------------+");
                System.out.println("| NO.  | Symptom \t\t | Medicine \t\t  | Price \t       | Description \t\t\t\t\t\t\t       |");
                System.out.println("+------------------------------------------------------------------------------------------------------------------------------------------------------+");
            
                for(int i=1;i<=symp.getLength();i++){
                    symptom2=symp.getEntry(i).getSName();
            
            
 
                    for(int n=1;n<=combinationLinkList.size();n++){
                        if(combinationLinkList.get(n).getSymptom().getSName().compareTo(symptom2)==0){
                            
                            //add to local Link List (So can ask user whether want to have upload into cart)
                            userCombination.add(combinationLinkList.get(n));
                            
                            System.out.printf("| %-5s| %-23s | %-22s | RM %-15s | %-69s |\n",no,combinationLinkList.get(n).getSymptom().getSName(),combinationLinkList.get(n).getMedicine().getmName(),combinationLinkList.get(n).getMedicine().getmPrice(),combinationLinkList.get(n).getMedicine().getmDesc());
                            no++;
                        }
                    }        
                }
            System.out.println("+------------------------------------------------------------------------------------------------------------------------------------------------------+");
            
            
            ///// let the user choose which medicine they want to add to cart
            char addChoice; 
            do{
                System.out.printf("Add to Cart (Y/N) : ");
                addChoice = sc.next().charAt(0);   
            }while(Character.toUpperCase(addChoice) !='Y' &&Character.toUpperCase(addChoice) !='N' );
            
            //if yes then add to cart
            if(Character.toUpperCase(addChoice) == 'Y'){
                for(int i = 1 ; i <= userCombination.size();i++ ){
                    cartArrS.push(new CartItem(1,userCombination.get(i),patient1,new Payment() ));
                }
                
                System.out.println("Successfully add to cart");
            }
            
            
            do{
                System.out.print("\nBack to menu (Y=Yes) ?");
                choice4=c2.next().charAt(0);
            }while(choice4!='Y'&&choice4!='y');
            
            if(choice4=='Y'||choice4=='y'){
                mainMenu();
            }
            
            
            }
        
            else if(choice3 != 'y' && choice3 != 'Y' &&choice3 != 'n' && choice3 != 'N' ){
                System.out.println("Error Input ! ");
                found2=true;
            }
        
           }while(found2);
                          
        }while(choice3 == 'y' || choice3 == 'Y');             
    }
     
//++-----------------------------------------------------------------------------------++//
//||                                medicine module                                    ||//
//++-----------------------------------------------------------------------------------++//

    private void medicineInitialize(){
         
        
        medicineLinkList.add(new Medicine("MED001","Paracetamol",15.00,"One or two 500mg tablets up to 4 times in 24 hours."));
        medicineLinkList.add(new Medicine("MED002","Delsym",20.00,"Take this medication by mouth, usually every 4 to 12 hours as needed"));
        medicineLinkList.add(new Medicine("MED003","Antihistamin",18.00,"50 to 100 milligrams (mg) every four to six hours as needed."));
        medicineLinkList.add(new Medicine("MED004","Imodium",22.00,"Take Imodium half an hour before a meal."));
        medicineLinkList.add(new Medicine("MED005","Ondansetron",30.00,"An hour before food, or 2 hours after meals."));
        medicineLinkList.add(new Medicine("MED006","Senokot",12.50,"Do not take this medication for more than 7 days"));
        medicineLinkList.add(new Medicine("MED007","Carbamazepine-Tablet", 23.00,"Take carbamazepine up to 4 times a day"));
        medicineLinkList.add(new Medicine("MED008","Lasix Furosemide",29.00,"It is best taken in the morning"));    
    }
     
    public void medicineMenu(){
       Scanner s = new Scanner(System.in);
        int choice=0;
        boolean isint1=false;

        do {
            do{
            System.out.println("-----------------------------------------------------------");
            System.out.println("|\t\tChoose what you want to do\t\t  |");
            System.out.println("-----------------------------------------------------------");
            System.out.println("|\t1.  Display Medicine                              |");
            System.out.println("|\t2.  Add Medicine                                  |");
            System.out.println("|\t3.  Edit Medicine                                 |");
            System.out.println("|\t4.  Delete Medicine                               |");
            System.out.println("|\t5.  Retrieve Medicine                             |");
            System.out.println("|\t6.  Back                                          |");
            System.out.println("-----------------------------------------------------------");
           
           try{
                   System.out.print("\nEnter your choice : ");
                   choice = s.nextInt();
                   System.out.println("");
                   isint1=false;
               }catch(InputMismatchException e){
                        System.out.println("Please enter INTEGER only!");
                        s.nextLine();
                        isint1=true;
                    }
               
           }while(isint1);
                
            

            switch (choice) {
                case 1:
                    adminDisplayMedicineList();
                    break;
                case 2:
                   adminAddMedicineList();
                    break;
                case 3:
                    adminEditMedicine();
                    break;
                case 4:
                    adminDeleteMedicine();
                    break;
                case 5: 
                    adminrRetrieveMedicine();
                    break;
                case 6:
                    mainMenu();
                    break;
                default:
                System.out.println("Please enter from 1 to 6 only.\n" );
                break;
            }
        } while (choice > 6 || choice <1);
  }
    
    public void adminDisplayMedicineList(){
        boolean back=false;
         Scanner sc = new Scanner(System.in);
         
        do{
           
            System.out.println("+------------------------------------------------------------------------------------------------------------------------------+");
            System.out.println("| Medicine ID\t  | Medicine\t\t   | Price\t| Medicine Description\t\t\t\t\t               |");
            System.out.println("+------------------------------------------------------------------------------------------------------------------------------+");
        
        for(int i=1;i<=medicineLinkList.size();i++){
            System.out.printf("| %-15s | %-22s | RM %.2f   | %-69s|\n",medicineLinkList.get(i).getmID(),medicineLinkList.get(i).getmName(),medicineLinkList.get(i).getmPrice(),medicineLinkList.get(i).getmDesc());
        }
        System.out.println("+------------------------------------------------------------------------------------------------------------------------------+");
       
        System.out.println("\n*-1 to exit* ");
        System.out.print(" Do You Want To Back To Sympthom Menu (Y/N):");
        String backM=sc.nextLine();

        if(backM.matches("-1")){
                medicineMenu();
            }
        switch(backM){
            case "Y":
            case "y":
                back=false;
                break;
            case "N":
            case "n":
                back=true;
                break;
            default:
                back=true;
                break;
        }
        }while(back);
        medicineMenu();
    }
     
    public void adminAddMedicineList(){
        char choice5,add;
        boolean back1=false;
        boolean continueadd=false;
        boolean exists=false,exists1=false;
        boolean isdouble=false;
        double newmPrice=0.00;
        String first3="", newmID,newMedDesc,newMed="";
        Scanner scan=new Scanner(System.in);
        Scanner scan1=new Scanner(System.in);

        
        do{
            
        System.out.println("\t\t**** Add Medicine ****\t\t\n");
        System.out.println(" -1 to exit ");
        do{
        System.out.print("New Medicine ID :  ");
        newmID=scan.next();
       
        exists =false;
         if(newmID.matches("-1")){
                medicineMenu();
            }
         newmID =newmID.toUpperCase();
       
    
            if(newmID.length()==6){
                 first3=newmID.substring(0, 3);
                if(first3.matches("MED")){
                    for(int j=1;j<=medicineLinkList.size();j++){
                        if(medicineLinkList.get(j).getmID().compareTo(newmID)==0){
                           System.out.println("Medicine ID exists !");
                           exists =true;
                        }
                        
                    }
                    
                }
                else{
                    System.out.println("Enter 6 character that start from MED  \n");
                }
            }
            
            else{
                if(newmID.length()!=6){
                    System.out.println("Enter 6 character that start from MED  \n");
                }else{
                   System.out.println("Enter Symtoms ID that start from MED  \n"); 
                }
            }
           
            }while(newmID.length()!=6||!first3.matches("MED")||exists);
        
        do{
        System.out.print("New Medicine    :  ");
       // newMed+=scan1.next();
        newMed=scan1.nextLine();
        exists1 =false;
        for(int j=1;j<=medicineLinkList.size();j++){
                        if(medicineLinkList.get(j).getmName().compareTo(newMed)==0){
                           System.out.println("Medicine exists !");
                           exists1 =true;
                        }
        }
        }while(exists1);
        
      do{
          try{
              isdouble=true;
              System.out.print("New Price       :  ");
              newmPrice=scan.nextDouble();
              
              if(newmPrice<0){
                  System.out.println("Please enter a valid price!");
              }
              
          }catch(InputMismatchException e){
              System.out.println("Please enter INTEGER only!");
              scan.nextLine();
             
             
              isdouble=false;
          } 
         
      }while(!isdouble||newmPrice<0);
        
        System.out.print("New Medicine Description    :  ");
         newMedDesc=scan1.nextLine();
        
        
        System.out.println("\n\n----------------------------------------------");
        System.out.println("|                    Medicine                 |");
        System.out.println("----------------------------------------------");
        System.out.print("New Medicine ID :  ");
        System.out.println(newmID);
        System.out.print("New Medicine    :  ");
        System.out.printf("%s",newMed);
        System.out.print("\nNew Price       :  ");
        System.out.print("RM ");
        System.out.printf("%.2f \n",newmPrice);
        System.out.print("New Medicine Description    :  ");
        System.out.printf("%s",newMedDesc);
        System.out.println("\n----------------------------------------------");
         
         do{
         System.out.print("Are you sure want to add (Y/N) ? ");
         choice5 = scan.next().charAt(0);
         if(choice5=='Y'||choice5=='y'){
             medicineLinkList.add(new Medicine(newmID,newMed,newmPrice,newMedDesc));
         }
         else if(choice5=='N'||choice5=='n'){
             System.out.println("Nothing to be added ! ");
         }
         }while(choice5!='N'&&choice5!='n'&&choice5!='Y'&&choice5!='y');
        
          do{
            System.out.print("Are you want to continue to add (Y/N) ? ");
            add=scan.next().charAt(0);
            
          }while(add!='N'&&add!='n'&&add!='Y'&&add!='y');
         
          
  }while(add=='Y'||add=='y');
        if(add=='N'||add=='n'){
            medicineMenu();
        }
}
    
    public void adminEditMedicine(){
        boolean isint2=false,redo1=false,found=false;
         String medName,medDesc;
         double medPrice=0.0;
         char editChoice,continueEdit;
         int editOption=0,medEdit=0;
          
        
        Scanner scn=new Scanner(System.in);
        do{
            
            System.out.println("+--------------------------------------------------------------------------------------------------------------------------------------------------------+");
            System.out.println("| NO.  | Medicine ID\t | Medicine\t\t | Price\t      | Medicine Description\t\t\t\t\t\t                 |");
            System.out.println("+--------------------------------------------------------------------------------------------------------------------------------------------------------+");
        
        for(int n=1;n<=medicineLinkList.size();n++){
            System.out.printf("|%-5s | %-15s | %-21s | RM %-15s | %-80s |\n",n,medicineLinkList.get(n).getmID(),medicineLinkList.get(n).getmName(),medicineLinkList.get(n).getmPrice(),medicineLinkList.get(n).getmDesc());
        }
        System.out.println("+--------------------------------------------------------------------------------------------------------------------------------------------------------+");
        
        do{
            do{
                try{
                     System.out.print("Which one u want to edit ? (0 to exit):  ");
                     medEdit=scn.nextInt();
                     isint2=false;
                }catch(InputMismatchException e){
                            System.out.println("Please enter INTEGER only!");
                            scn.nextLine();
                            isint2=true;
                }
            }while(isint2);
       
        
        
        
        
        if(medEdit==0){
            redo1=false;
            medicineMenu();
        }
        else if(medEdit>0&&medEdit<=medicineLinkList.size()){
        boolean wrong=false,isdouble=false;
        redo1=false;
        System.out.println("\n\n+---------------------------------------------------------------------------------------------------+");
        System.out.println("|                                  \t\tMedicine\t\t                            |");
        System.out.println("+---------------------------------------------------------------------------------------------------+");
        System.out.print(" Medicine ID :  ");
        System.out.println(medicineLinkList.get(medEdit).getmID());
        System.out.print(" Medicine    :  ");
        System.out.println(medicineLinkList.get(medEdit).getmName());
        System.out.print(" Price       :  ");
        System.out.println(medicineLinkList.get(medEdit).getmPrice());
        System.out.print(" Medicine Description    :  ");
        System.out.println(medicineLinkList.get(medEdit).getmDesc());
        System.out.println("-----------------------------------------------------------------------------------------------------");
      

                do{
                
                System.out.println("\n\nChoose which to edit");
                System.out.println("---------------------------");
                System.out.println("1.Change Medicine Name");
                System.out.println("2.Change Medicine Price");
                System.out.println("3.Change Medicine Description\n");
                
                    do{
                        try{
                            System.out.print("Enter your choice : ");
                           editOption=scn.nextInt();
                            found=false;
                        }catch(InputMismatchException e){
                            System.out.println("Please enter INTEGER only!");
                            scn.nextLine();
                            found=true;
                        }
                    }while(found);
               
                }while(editOption<1||editOption>3);
        
             switch(editOption)
                     {
                         case 1:
                        
                        System.out.print("Enter new medicine name :  ");
                        medName=scn.nextLine();

                        //check if symptom name entered existed or not
                            for (int k = 1; k < medicineLinkList.size(); k++) {
                             if(medicineLinkList.get(k).getmName().compareTo(medName) == 0){
                                System.out.print("Medicine name exist. Please re-enter : ");
                                medName = scn.next();
                                k=0;
                                }
                             }
                        do{
                        System.out.print("\nAre you sure you want to edit this medicine record? (Y/N) : ");
                        editChoice = scn.next().trim().charAt(0);
                                    
                        if (editChoice == 'y' || editChoice == 'Y') {
                            Medicine selectedMedicine= medicineLinkList.get(medEdit);
                            selectedMedicine.setmName(medName);
                            medicineLinkList.replace(medEdit, selectedMedicine);
                            System.out.println("tMedicine record updated!");
                            System.out.println("\n\n+---------------------------------------------------------------------------------------------------+");
                            System.out.println("|                                  \t\tMedicine\t\t                            |");
                            System.out.println("+---------------------------------------------------------------------------------------------------+");
                            System.out.print(" Medicine ID :  ");
                            System.out.println(medicineLinkList.get(medEdit).getmID());
                            System.out.print(" Medicine    :  ");
                            System.out.println(medicineLinkList.get(medEdit).getmName());
                            System.out.print(" Price    :  ");
                            System.out.println(medicineLinkList.get(medEdit).getmPrice());
                            System.out.print(" Medicine Description    :  ");
                            System.out.println(medicineLinkList.get(medEdit).getmDesc());
                            System.out.println("-----------------------------------------------------------------------------------------------------");
                        }else {
                            System.out.println("This Medicine record is not updated.");
                            
                        }
                        }while(editChoice != 'y' && editChoice != 'Y'&&editChoice != 'n' && editChoice != 'N');
    
                        
                    break;
                    
                    case 2:
                        
                        do{
                            try{
                                isdouble=true;
                                System.out.print("Enter new medicine price : ");
                                medPrice=scn.nextDouble();
                                if(medPrice<0){
                                    isdouble=false;
                                }
              
                            }catch(InputMismatchException e){
                                 System.out.println("Please enter INTEGER only!");
                                 scn.nextLine();
             
             
                                isdouble=false;
                            } 
         
                        }while(!isdouble);
                        
                            
                        do{
                        System.out.print("\nAre you sure you want to edit this medicine record? (Y/N) : ");
                        editChoice = scn.next().trim().charAt(0);
                        
                        
                        if (editChoice == 'y' || editChoice == 'Y') {
                            Medicine selectedMedicine= medicineLinkList.get(medEdit);
                            selectedMedicine.setmPrice(medPrice);
                            medicineLinkList.replace(medEdit, selectedMedicine);
                            System.out.println("\n\n+---------------------------------------------------------------------------------------------------+");
                            System.out.println("|                                  \t\tMedicine\t\t                            |");
                            System.out.println("+---------------------------------------------------------------------------------------------------+");
                            System.out.print(" Medicine ID :  ");
                            System.out.println(medicineLinkList.get(medEdit).getmID());
                            System.out.print(" Medicine    :  ");
                            System.out.println(medicineLinkList.get(medEdit).getmName());
                            System.out.print(" Price    :  ");
                            System.out.println(medicineLinkList.get(medEdit).getmPrice());
                            System.out.print(" Medicine Description    :  ");
                            System.out.println(medicineLinkList.get(medEdit).getmDesc());
                            System.out.println("-----------------------------------------------------------------------------------------------------");
                        } else {
                            System.out.println("This medicine record is not updated.");
                            
                        }
                        }while(editChoice != 'y' && editChoice != 'Y'&&editChoice != 'n' && editChoice != 'N');
                        break;
                        
                    case 3:
                        
                        
                        do{
                        System.out.print("Enter new medicine description : ");
                        medDesc=scn.next();

                        System.out.print("\nAre you sure you want to edit this medicine record? (Y/N) : ");
                         editChoice = scn.next().trim().charAt(0);
                         
                        if (editChoice == 'y' || editChoice == 'Y') {
                            Medicine selectedMedicine= medicineLinkList.get(medEdit);
                            selectedMedicine.setmDesc(medDesc);
                            medicineLinkList.replace(medEdit, selectedMedicine);
                            System.out.println("Medicine record updated!");
                            System.out.println("\n\n+---------------------------------------------------------------------------------------------------+");
                            System.out.println("|                                  \t\tMedicine\t\t                            |");
                            System.out.println("+---------------------------------------------------------------------------------------------------+");
                            System.out.print(" Medicine ID :  ");
                            System.out.println(medicineLinkList.get(medEdit).getmID());
                            System.out.print(" Medicine    :  ");
                            System.out.println(medicineLinkList.get(medEdit).getmName());
                            System.out.print(" Price    :  ");
                            System.out.println(medicineLinkList.get(medEdit).getmPrice());
                            System.out.print(" Medicine Description    :  ");
                            System.out.println(medicineLinkList.get(medEdit).getmDesc());
                            System.out.println("-----------------------------------------------------------------------------------------------------");
                        } else {
                            System.out.println("This medicine record is not updated.");
                            
                        }
                        }while(editChoice != 'y' && editChoice != 'Y'&&editChoice != 'n' && editChoice != 'N');
                        
                    break; 
                    
                    
                    default:
                        System.out.println("Please enter number between 1 to 3 only");
                    break;
             }
        
             
        }
                else{
                     System.out.println("Does not exits !");
                     redo1=true;
                     }
        }while(redo1);
       
            do{
            System.out.print("\nDo you want to continue editing? : ");
            continueEdit = scn.next().trim().charAt(0);
            scn.nextLine();
            System.out.println("");  
            }while(continueEdit!= 'y' && continueEdit != 'Y'&&continueEdit!= 'n' && continueEdit != 'N');
        
        
        }while (continueEdit == 'y' || continueEdit == 'Y');
        
        if(continueEdit=='n'||continueEdit=='N'){
            medicineMenu();
        }
    }
    
    public void adminDeleteMedicine(){
            Scanner scn = new Scanner(System.in);
            int i=1;
            int medDelete=0;
            String namedlt;
            char choice2,choice3;
            boolean mistake=false;
            
            
           do{
            
            System.out.println("+--------------------------------------------------------------------------------------------------------------------------------------------------------+");
            System.out.println("| NO.  | Medicine ID\t | Medicine\t\t | Price\t      | Medicine Description\t\t\t\t\t\t                 |");
            System.out.println("+--------------------------------------------------------------------------------------------------------------------------------------------------------+");
        
        for(int n=1;n<=medicineLinkList.size();n++){
            System.out.printf("|%-5s | %-15s | %-21s | RM %-15s | %-80s |\n",n,medicineLinkList.get(n).getmID(),medicineLinkList.get(n).getmName(),medicineLinkList.get(n).getmPrice(),medicineLinkList.get(n).getmDesc());
        }
        System.out.println("+--------------------------------------------------------------------------------------------------------------------------------------------------------+");
        
        do{
            
           try{
        System.out.print("Which one u want to delete ? (0 to exit):  ");
        medDelete=scn.nextInt();
           }catch(InputMismatchException ex){
                
                        scn.nextLine();
                        mistake=true;
           }       
        if(medDelete==0){
            medicineMenu();
        }
        else if(medDelete>medicineLinkList.size()){
            System.out.println(" Out of Range!  \n");
            mistake=true;
            
        }
        else {
        boolean wrong=false;
        mistake=false;
        System.out.println("\n\n+---------------------------------------------------------------------------------------------------+");
        System.out.println("|                                  \t\tMedicine\t\t                            |");
        System.out.println("+---------------------------------------------------------------------------------------------------+");
        System.out.print(" Medicine ID :  ");
        System.out.println(medicineLinkList.get(medDelete).getmID());
        System.out.print(" Medicine    :  ");
        System.out.println(medicineLinkList.get(medDelete).getmName());
        System.out.print(" Price    :  ");
        System.out.println(medicineLinkList.get(medDelete).getmPrice());
        System.out.print(" Medicine Description    :  ");
        System.out.println(medicineLinkList.get(medDelete).getmDesc());
        System.out.println("-----------------------------------------------------------------------------------------------------");
        
        namedlt=medicineLinkList.get(medDelete).getmName();
        
        do{
        System.out.print("Are you sure you want to delete this symptom record? (Y/N) : ");
        choice2 = scn.next().charAt(0);
        }while(choice2!='N'&&choice2!='n'&&choice2!='Y'&&choice2!='y');

        if (choice2 == 'y' || choice2 == 'Y') {
            medicineLinkList.remove(medDelete);
            for(int p=1;p<=combinationLinkList.size();p++){
            if(combinationLinkList.get(p).getMedicine().getmName().compareTo(namedlt)==0){
                combinationLinkList.remove(p);
            }
            }
            
            System.out.println("\nSymptom record deleted!\n");
            } else {
                System.out.println("\nThis service record is not deleted.\n");
                }
        }
        
         }while(mistake);
        
        do{
        System.out.print("Do you still want to continue deleting? (Y/N) : ");
        choice3 = scn.next().charAt(0);
        }while(choice3!='N'&&choice3!='n'&&choice3!='Y'&&choice3!='y');
        
        
        }while(choice3 == 'y' || choice3 == 'Y');   
            
        if(choice3=='n'||choice3=='N'){
            medicineMenu();
        }           
    
    }
    
    public  void adminrRetrieveMedicine(){
            Scanner sn=new Scanner(System.in);
            String searchMed,first3;
            char choice4;
            boolean found=false;
            do{
            System.out.println("+---------------------------------------------------------+");
            System.out.println("|\t\t Search Medicine (Medicine ID)\t\t  |");
            System.out.println("+---------------------------------------------------------+\n\n");
        
            do{
            System.out.println(" -1 to exit ");
            System.out.print("Medicine ID  (MED___)   :");
            searchMed=sn.next();
            
            if(searchMed.matches("-1")){
                medicineMenu();
            }
            searchMed=searchMed.toUpperCase();
            first3=searchMed.substring(0, 3);
            if(first3.matches("MED")){
                
                if(searchMed.length()==6){
                    found=false;
                    for(int j=1;j<=medicineLinkList.size();j++){
                        
                        if(medicineLinkList.get(j).getmID().compareTo(searchMed)==0){
                            found=true;
                             System.out.println("\n\n+---------------------------------------------------------------------------------------------------+");
                             System.out.println("|                                  \t\tMedicine\t\t                            |");
                             System.out.println("+---------------------------------------------------------------------------------------------------+");
                             System.out.print(" Medicine ID :  ");
                             System.out.println(medicineLinkList.get(j).getmID());
                             System.out.print(" Medicine    :  ");
                             System.out.println(medicineLinkList.get(j).getmName());
                             System.out.print(" Price    :  ");
                             System.out.println(medicineLinkList.get(j).getmPrice());
                             System.out.print(" Medicine Description    :  ");
                             System.out.println(medicineLinkList.get(j).getmDesc());
                             System.out.println("-----------------------------------------------------------------------------------------------------");
                        }
                        
                    }
                    if(!found){
                        System.out.println("Medicine ID does not exist  \n");
                    }
                    
                }
                else{
                    
                    System.out.println("Enter 6 character that start from MED  \n");
                
                        
                }
            
            }
            
            else{
                if(searchMed.length()!=6){
                    System.out.println("Enter 6 character that start from MED  \n");
                }else{
                   System.out.println("Enter Symtoms ID that start from MED  \n"); 
                }
                 
            
            }
            
            }while(searchMed.length()!=6||!first3.matches("MED")||!found);
            
            do{
            System.out.print("Do you still want to continue searching? (Y/N) : ");
            choice4 = sn.next().charAt(0);
            }while(choice4!='N'&&choice4!='n'&&choice4!='Y'&&choice4!='y');
            
            if(choice4=='N'||choice4=='n'){
                medicineMenu();
            }
            
        }while(choice4=='Y'||choice4=='y');
    }
    
    //--------------------------------------------------------------------------------------//
    //||                              symptom module                                      ||//
    //--------------------------------------------------------------------------------------//
    
    private void symptopmInitialize(){
        
        symptomArrayList.add(new Symptom("SYM001", "Fever", "High Body Temperature Fever"));
        symptomArrayList.add(new Symptom("SYM002", "Cough", "Act That Clears The Throat"));
        symptomArrayList.add(new Symptom("SYM003", "Common Cold", "Runny Or Stuffy Nose; Sore Throat; Cough;"));
        symptomArrayList.add(new Symptom("SYM004", "Headache", "Pain In Any Region Of The Head"));
        symptomArrayList.add(new Symptom("SYM005", "Diarrhea", "Loose Or Watery Stool"));
        symptomArrayList.add(new Symptom("SYM006", "Nausea", "Diffuse Sensation Of Unease And Discomfort"));
        symptomArrayList.add(new Symptom("SYM007", "Vomiting", "Forceful Discharge Of Stomach Contents"));
        symptomArrayList.add(new Symptom("SYM008", "ConstIpate", "Stool Difficult To Pass"));
        symptomArrayList.add(new Symptom("SYM009", "Seizures", "Sudden, Uncontrolled Electrical Disturbance In The Brain"));
        symptomArrayList.add(new Symptom("SYM010", "Oliguria", "Hypouresis Is the Low Output Of Urine"));
        
    }
    
    public void symptomMenu(){
        
        Scanner s = new Scanner(System.in);
        int choice=0;
        boolean isint=false;

        do {
            do{
            System.out.println("-----------------------------------------------------------");
            System.out.println("|\t\tChoose what you want to do\t\t  |");
            System.out.println("-----------------------------------------------------------");
            System.out.println("|\t1.  Display Symptom                               |");
            System.out.println("|\t2.  Add Symptom                                   |");
            System.out.println("|\t3.  Edit Symptom                                  |");
            System.out.println("|\t4.  Delete Symptom                                |");
            System.out.println("|\t5.  Retrieve Symptom                              |");
            System.out.println("|\t6.  Back                                          |");
            System.out.println("-----------------------------------------------------------");
            
           
               try{
                   System.out.print("\nEnter your choice : ");
                   choice = s.nextInt();
                   System.out.println("");
                   isint=false;
               }catch(InputMismatchException e){
                        System.out.println("Please enter INTEGER only!");
                        s.nextLine();
                        isint=true;
                    }
               
           }while(isint);
            

            switch (choice) {
                case 1:
                    adminDisplaySymptomList();
                    break;
                case 2:
                    adminAddSymptom();
                    break;
                case 3:
                    adminEditSymptom();
                    break;
                case 4:
                    adminDeleteSymptom();
                    break;
                case 5: 
                    adminRetrieveSymptom();
                    break;
                case 6: 
                    mainMenu();
                    break;
                default:
                System.out.println("\t\t\t\t\tPlease enter from 1 to 6 only.\n" );
                break;
            }
        } while (choice > 5 || choice <1);
    }
     
    public  void adminDisplaySymptomList(){
         boolean back=false;
         Scanner sc = new Scanner(System.in);
         
        do{
           
            System.out.println("+----------------------------------------------------------------------------------------------------+");
            System.out.println("| Symptom ID\t  | Symptom\t\t   | Symtom Description\t\t\t\t             |");
            System.out.println("+----------------------------------------------------------------------------------------------------+");
        
        for(int i=1;i<=symptomArrayList.getLength();i++){
            System.out.printf("| %-15s | %-21s | %-57s|\n",symptomArrayList.getEntry(i).getSID(),symptomArrayList.getEntry(i).getSName(),symptomArrayList.getEntry(i).getSDesc());
        }
        System.out.println("+----------------------------------------------------------------------------------------------------+");
       
        System.out.print("\n Do You Want To Back To Sympthom Menu (Y/N)");
        String backM=sc.nextLine();
        switch(backM){
            case "Y":
            case "y":
                back=false;
                break;
            case "N":
            case "n":
                back=true;
                break;
            default:
                back=true;
                break;
        }
        }while(back);
        symptomMenu();
        
    }
     
    public void adminAddSymptom(){
        
        boolean back1=false;
        boolean exists2=false;
        boolean exists3=false;
        char backM1,add;
        String newID,newSym,newSymDesc,first3="";
      
        Scanner scan=new Scanner(System.in);
        Scanner scan1=new Scanner(System.in);

        
        do{
        System.out.println("\t\t**** Add Symptoms ****\t\t\n");
        System.out.println(" -1 to exit ");
        do{
        System.out.print("New Symptom ID :  ");
        newID=scan.next();
        newID =newID.toUpperCase();
       
        exists2 =false;
         if(newID.matches("-1")){
                symptomMenu();
            }
          
    
            if(newID.length()==6){
                first3=newID.substring(0, 3);
                if(first3.matches("SYM")){
                    for(int j=1;j<=symptomArrayList.getLength();j++){
                        if(symptomArrayList.getEntry(j).getSID().compareTo(newID)==0){
                           System.out.println("Sympthom ID exists !");
                           exists2 =true;
                        }
                        
                    }
                    
                }
                else{
                    System.out.println("Enter 6 character that start from SYM  \n");
                }
            }
            
            else{
                if(newID.length()!=6){
                    System.out.println("Enter 6 character that start from SYM  \n");
                }else{
                   System.out.println("Enter Symtoms ID that start from SYM  \n"); 
                }
            }
           
            }while(newID.length()!=6||!first3.matches("SYM")||exists2);
        
        
        
        do{
        System.out.print("New Symptom    :  ");
        newSym=scan1.nextLine();
        exists3 =false;
        for(int j=1;j<=symptomArrayList.getLength();j++){
                        if(symptomArrayList.getEntry(j).getSName().compareTo(newSym)==0){
                           System.out.println("Symptom exists !");
                           exists3 =true;
                        }
        }
        }while(exists3);
        
        
        System.out.print("New Symptom Description    :  ");
        newSymDesc=scan1.nextLine();
        
        
        System.out.println("\n\n----------------------------------------------");
        System.out.println("|                    Symptoms                 |");
        System.out.println("----------------------------------------------");
        System.out.print("New Symptom ID :  ");
        System.out.println(newID);
        System.out.print("New Symptom    :  ");
        System.out.printf("%s",newSym);
        System.out.print("\nNew Symptom Description    :  ");
        System.out.printf("%s",newSymDesc);
        System.out.println("\n----------------------------------------------");
         
         do{
         System.out.print("Are you sure want to add (Y/N) ? ");
         backM1 = scan.next().charAt(0);
         if(backM1=='Y'||backM1=='y'){
             symptomArrayList.add(new Symptom(newID,newSym,newSymDesc));
         }
         else if(backM1=='N'||backM1=='n'){
             System.out.println("Nothing to be added ! ");
         }
        }while(backM1!='N'&&backM1!='n'&&backM1!='Y'&&backM1!='y');
         
         
         
        do{
            System.out.print("Are you want to continue to add (Y/N) ? ");
            add=scan.next().charAt(0);
            
          }while(add!='N'&&add!='n'&&add!='Y'&&add!='y');
        
        }while(add=='Y'||add=='y');
        if(add=='N'||add=='n'){
            symptomMenu();
        }
    }
 
    public void adminEditSymptom(){
      
        boolean found=false,isint1=false,redo=false;
         String symName,symDesc;
         char editChoice,continueEdit;
         int editOption=0 ;
         int symEdit=0;
          
        
        Scanner scn=new Scanner(System.in);
        do{
            
            System.out.println("+-----------------------------------------------------------------------------------------------------------+");
            System.out.println("| NO.  | Symptom ID\t | Symptom\t\t | Symtom Description\t\t\t\t            |");
            System.out.println("+-----------------------------------------------------------------------------------------------------------+");
        
        for(int i=1;i<=symptomArrayList.getLength();i++){
            System.out.printf("|%-5s | %-15s | %-21s | %-57s|\n",i,symptomArrayList.getEntry(i).getSID(),symptomArrayList.getEntry(i).getSName(),symptomArrayList.getEntry(i).getSDesc());
        }
        System.out.println("+-----------------------------------------------------------------------------------------------------------+");
        
        do{
        do{
            try{
                 System.out.print("Which one u want to edit ? (0 to exit):  ");
                     symEdit=scn.nextInt();
                     isint1=false;
            }catch(InputMismatchException e){
                        System.out.println("Please enter INTEGER only!");
                        scn.nextLine();
                        isint1=true;
            }
        }while(isint1);
       
        if(symEdit==0){
            redo=false;
            symptomMenu();
            
        }
        else if(symEdit>0&&symEdit<=symptomArrayList.getLength()) {
        boolean wrong=false;
        redo=false;
        System.out.println("\n\n+---------------------------------------------------------------------------------------------------+");
        System.out.println("|                                  \t\tSymptoms\t\t                            |");
        System.out.println("+---------------------------------------------------------------------------------------------------+");
        System.out.print("| Symptom ID :  ");
        System.out.println(symptomArrayList.getEntry(symEdit).getSID());
        System.out.print("| Symptom    :  ");
        System.out.println(symptomArrayList.getEntry(symEdit).getSName());
        System.out.print("| Symptom Description    :  ");
        System.out.println(symptomArrayList.getEntry(symEdit).getSDesc());
        System.out.println("+---------------------------------------------------------------------------------------------------+");
 
                do{
                
                System.out.println("\n\nChoose which to edit");
                System.out.println("---------------------------");
                System.out.println("1.Change Symptom Name");
                System.out.println("2.Change Symptom Description\n");
                
                do{
                    try{
                        System.out.print("Enter your choice : ");
                        editOption=scn.nextInt();
                        found=false;
                    }catch(InputMismatchException e){
                        System.out.println("Please enter INTEGER only!");
                        scn.nextLine();
                        found=true;
                    }
                }while(found);
                
                }while(editOption<1||editOption>2);
                
                
        
             switch(editOption)
                     {
                         case 1:
                        
                        System.out.print("Enter new symptom name :  ");
                        symName=scn.next();

                        //check if symptom name entered existed or not
                            for (int k = 1; k < symptomArrayList.getLength(); k++) {
                             if(symptomArrayList.getEntry(k).getSName().compareTo(symName) == 0){
                                System.out.print("Symptom name exist. Please re-enter : ");
                                symName = scn.next();
                                k=0;
                                }
                             }
      
                        do{
                        System.out.print("\nAre you sure you want to edit this symptom record? (Y/N) : ");
                         editChoice = scn.next().trim().charAt(0);
                         if (editChoice == 'y' || editChoice == 'Y') {
                            symptomArrayList.getEntry(symEdit).setSName(symName);
                            System.out.println("Symptom record updated!");
                            System.out.println("\n\n+---------------------------------------------------------------------------------------------------+");
                            System.out.println("|                                  \t\tSymptoms\t\t                            |");
                            System.out.println("+---------------------------------------------------------------------------------------------------+");
                            System.out.print(" Symptom ID :  ");
                            System.out.println(symptomArrayList.getEntry(symEdit).getSID());
                            System.out.print(" Symptom    :  ");
                            System.out.println(symptomArrayList.getEntry(symEdit).getSName());
                            System.out.print(" Symptom Description    :  ");
                            System.out.println(symptomArrayList.getEntry(symEdit).getSDesc());
                            System.out.println("+---------------------------------------------------------------------------------------------------+");
                        } else if(editChoice == 'n' || editChoice == 'N'){
                            System.out.println("This symptom record is not updated.");
                            
                        }
                        else{
                            System.out.println("");
                        }
                        }while(editChoice != 'y' && editChoice != 'Y'&&editChoice != 'n' && editChoice != 'N');

                        
                    break; 
                    case 2:
                        
                        
                        
                        System.out.print("Enter new symptom description : ");
                        symDesc=scn.next();
                        
                        do{
                        System.out.print("\nAre you sure you want to edit this symptom record? (Y/N) : ");
                         editChoice = scn.next().trim().charAt(0);
                         if (editChoice == 'y' || editChoice == 'Y') {
                            symptomArrayList.getEntry(symEdit).setSDesc(symDesc);
                            System.out.println("Symptom record updated!");
                            System.out.println("\n\n+---------------------------------------------------------------------------------------------------+");
                            System.out.println("|                                  \t\tSymptoms\t\t                            |");
                            System.out.println("+---------------------------------------------------------------------------------------------------+");
                            System.out.print(" Symptom ID :  ");
                            System.out.println(symptomArrayList.getEntry(symEdit).getSID());
                            System.out.print(" Symptom    :  ");
                            System.out.println(symptomArrayList.getEntry(symEdit).getSName());
                            System.out.print(" Symptom Description    :  ");
                            System.out.println(symptomArrayList.getEntry(symEdit).getSDesc());
                            System.out.println("+---------------------------------------------------------------------------------------------------+");
                        } else if(editChoice == 'n' || editChoice == 'N'){
                            System.out.println("This symptom record is not updated.");
                            
                        }
                        else{
                            System.out.println("");
                        }
                        }while(editChoice != 'y' && editChoice != 'Y'&&editChoice != 'n' && editChoice != 'N');

                        
                    break; 
                    
                    
                    default:
                        System.out.println("Please enter number between 1 to 2 only");
                                                
                    break;
             } 
        }
        else{
            System.out.println("Does not exists ! ");
            redo=true;
        }
        }while(redo);
        
        
            do{
            System.out.print("\nDo you want to continue editing? : ");
            continueEdit = scn.next().trim().charAt(0);
            scn.nextLine();
            System.out.println("");  
            }while(continueEdit!= 'y' && continueEdit != 'Y'&&continueEdit!= 'n' && continueEdit != 'N');
        
        
        }while (continueEdit == 'y' || continueEdit == 'Y');
        
        if(continueEdit=='n'||continueEdit=='N'){
            symptomMenu();
        }
        
}
 
    public void adminDeleteSymptom(){
            Scanner scn = new Scanner(System.in);
            int i=1;
            int sympDelete=0;
            int found=0;
            char choice2,choice3;
            boolean mistake=false;
            String name;
            
           do{
            
            System.out.println("+-----------------------------------------------------------------------------------------------------------+");
            System.out.println("| NO.  | Symptom ID\t | Symptom\t\t | Symtom Description\t\t\t\t            |");
            System.out.println("+-----------------------------------------------------------------------------------------------------------+");
        
        for(int m=1;m<=symptomArrayList.getLength();m++){
            System.out.printf("|%-5s | %-15s | %-21s | %-57s|\n",m,symptomArrayList.getEntry(m).getSID(),symptomArrayList.getEntry(m).getSName(),symptomArrayList.getEntry(m).getSDesc());
        }
        System.out.println("+-----------------------------------------------------------------------------------------------------------+");
        
        do{
            
           try{
        System.out.print("Which one u want to delete ? (0 to exit):  ");
        sympDelete=scn.nextInt();
           }catch(InputMismatchException ex){
                
                        scn.nextLine();
                        mistake=true;
           }       
        if(sympDelete==0){
            symptomMenu();
        }
        else if(sympDelete>symptomArrayList.getLength()){
            System.out.println(" Out of Range!  \n");
            mistake=true;
            
        }
        else {
        boolean wrong=false;
        mistake=false;
        System.out.println("\n\n+---------------------------------------------------------------------------------------------------+");
        System.out.println("|                                  \t\tSymptom\t\t                                    |");
        System.out.println("+---------------------------------------------------------------------------------------------------+");
        System.out.print(" Symptom ID :  ");
        System.out.println(symptomArrayList.getEntry(sympDelete).getSID());
        System.out.print(" Symptom    :  ");
        System.out.println(symptomArrayList.getEntry(sympDelete).getSName());
        System.out.print(" Symptom Description    :  ");
        System.out.println(symptomArrayList.getEntry(sympDelete).getSDesc());
        System.out.println("-----------------------------------------------------------------------------------------------------");
        
        name=symptomArrayList.getEntry(sympDelete).getSID();
        
        
           
        do{
        System.out.print("Are you sure you want to delete this symptom record? (Y/N) : ");
        choice2 = scn.next().charAt(0);
        }while(choice2!='N'&&choice2!='n'&&choice2!='Y'&&choice2!='y');
        
        if (choice2 == 'y' || choice2 == 'Y') {
             
            symptomArrayList.remove(sympDelete);
            
            for(int p=1;p<=combinationLinkList.size();p++){
            if(combinationLinkList.get(p).getSymptom().getSID().compareTo(name)==0){
                combinationLinkList.remove(p);
            }
           
        }
            
//            System.out.println("+---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+");
//            System.out.println("| Combination ID  | Symptom ID\t | Symptom\t    | Symtom Description\t\t\t               | Medicine ID\t | Medicine\t\t  | Price      | Medicine Description\t\t\t\t\t              |");
//            System.out.println("+---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+");
//        
//            for(int h=1;h<=combinationLinkList.getNumberOfEntries();h++){
//                System.out.printf("| %-15s | %-12s | %-16s | %-57s| %-15s | %-22s | RM %.2f   | %-69s|\n",combinationLinkList.getEntry(h).getmSID(),combinationLinkList.getEntry(h).getSymptom().getSID(),combinationLinkList.getEntry(h).getSymptom().getSName(),combinationLinkList.getEntry(h).getSymptom().getSDesc(),combinationLinkList.getEntry(h).getMedicine().getmID(),combinationLinkList.getEntry(h).getMedicine().getmName(),combinationLinkList.getEntry(h).getMedicine().getmPrice(),combinationLinkList.getEntry(h).getMedicine().getmDesc());
//            }
//            System.out.println("+---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+");
//       
            
            System.out.println("\nSymptom record deleted!\n");
            } else {
                System.out.println("\nThis service record is not deleted.\n");
                }
        }
        
         }while(mistake);
        
        do{
        System.out.print("Do you still want to continue deleting? (Y/N) : ");
        choice3 = scn.next().charAt(0);
        }while(choice3!='N'&&choice3!='n'&&choice3!='Y'&&choice3!='y');
        
        
        }while(choice3 == 'y' || choice3 == 'Y');   
            
        if(choice3=='n'||choice3=='N'){
            symptomMenu();
        }           
    
    }

    public void adminRetrieveSymptom(){
      
            Scanner sn=new Scanner(System.in);
            String searchSym,first3;
            char choice4;
            boolean found=false;
            do{
            System.out.println("\n+---------------------------------------------------------+");
            System.out.println("|\t\t Search Symtoms (Symtoms ID)\t\t  |");
            System.out.println("+---------------------------------------------------------+\n\n");

            do{
            System.out.println(" -1 to exit ");
            System.out.print("Symptoms ID  (SYM___)   :");
            searchSym=sn.next();
            
            if(searchSym.matches("-1")){
                symptomMenu();
            }
            searchSym=searchSym.toUpperCase();
            first3=searchSym.substring(0, 3);
            if(first3.matches("SYM")){
                
                if(searchSym.length()==6){
                    found=false;
                    for(int j=1;j<=symptomArrayList.getLength();j++){
                        
                        if(symptomArrayList.getEntry(j).getSID().compareTo(searchSym)==0){
                            found=true;
                            System.out.println("\n\n+---------------------------------------------------------------------------------------------------+");
                            System.out.println("|                                  \t\tSymptoms\t\t                            |");
                            System.out.println("+---------------------------------------------------------------------------------------------------+");
                            System.out.print(" Symptom ID :  ");
                            System.out.println(symptomArrayList.getEntry(j).getSID());
                            System.out.print(" Symptom    :  ");
                            System.out.println(symptomArrayList.getEntry(j).getSName());
                            System.out.print(" Symptom Description    :  ");
                            System.out.println(symptomArrayList.getEntry(j).getSDesc());
                            System.out.println("+---------------------------------------------------------------------------------------------------+\n");
                        }
                        
                    }
                    
                    if(!found){
                        System.out.println("Combination ID does not exist  \n");
                    }
                    
                }
                else{
                    
                    System.out.println("Enter 6 character that start from SYM  \n");
                
                        
                }
            
            }
            
            else{
                if(searchSym.length()!=6){
                    System.out.println("Enter 6 character that start from SYM  \n");
                }else{
                   System.out.println("Enter Symtoms ID that start from SYM  \n"); 
                }
                 
            
            }
            
            }while(searchSym.length()!=6||!first3.matches("SYM")||!found);
            
            do{
            System.out.print("Do you still want to continue searching? (Y/N) : ");
            choice4 = sn.next().charAt(0);
            }while(choice4!='N'&&choice4!='n'&&choice4!='Y'&&choice4!='y');
            
            if(choice4=='N'||choice4=='n'){
                symptomMenu();
            }
            
        }while(choice4=='Y'||choice4=='y');
            
            
  }
    
    //--------------------------------------------------------------------------------------//
    //||                              combination module                                  ||//
    //--------------------------------------------------------------------------------------//
    
    private void combinationInitialize(){
        //Symptom symptom1 = new Symptom();
        //edicine medicine1 = new Medicine();
        
        combinationLinkList.add(new Combination("COM001",new Symptom("SYM001", "Fever", "High Body Temperature Fever"),new Medicine("MED001","Paracetamol",15.00,"One or two 500mg tablets up to 4 times in 24 hours.")));
        combinationLinkList.add(new Combination("COM002",new Symptom("SYM002", "Cough", "Act That Clears The Throat"),new Medicine("MED002","Delsym",20.00,"Take this medication by mouth, usually every 4 to 12 hours as needed")));
        combinationLinkList.add(new Combination("COM003",new Symptom("SYM003", "Common Cold", "Runny Or Stuffy Nose; Sore Throat; Cough;"),new Medicine("MED003","Amtihistamine",18.00,"50 to 100 milligrams (mg) every four to six hours as needed.")));
        combinationLinkList.add(new Combination("COM004",new Symptom("SYM004", "Headache", "Pain In Any Region Of The Head"),new Medicine("MED001","Paracetamol",15.00,"One or two 500mg tablets up to 4 times in 24 hours.")));
        combinationLinkList.add(new Combination("COM005",new Symptom("SYM005", "Diarrhea", "Loose Or Watery Stool"),new Medicine("MED004","Imodium",22.00,"Take Imodium half an hour before a meal.")));
        combinationLinkList.add(new Combination("COM006",new Symptom("SYM006", "Nausea", "Diffuse Sensation Of Unease And Discomfort"),new Medicine("MED005","Ondansetron",30.00,"An hour before food, or 2 hours after meals.")));
        combinationLinkList.add(new Combination("COM007",new Symptom("SYM007", "Vomiting", "Forceful Discharge Of Stomach Contents"),new Medicine("MED005","Ondansetron",30.00,"An hour before food, or 2 hours after meals.")));
        combinationLinkList.add(new Combination("COM008",new Symptom("SYM008", "Consipate", "Stool Difficult To Pass"),new Medicine("MED006","Senokot",12.50,"Do not take this medication for more than 7 days")));
        combinationLinkList.add(new Combination("COM009",new Symptom("SYM009", "Seizures", "Sudden, Uncontrolled Electrical Disturbance In The Brain"),new Medicine("MED007","Carbamazepine-Tablet", 23.00,"Take carbamazepine up to 4 times a day")));
        combinationLinkList.add(new Combination("COM010",new Symptom("SYM010", "Oliguria", "Hypouresis Is the Low Output Of Urine"),new Medicine("MED008","Lasix Furosemide",29.00,"It is best taken in the morning")));
        
    }
    
    public void combinationMenu(){
       Scanner s = new Scanner(System.in);
        int choice=0;
        boolean isint1=false;

        do {
            do{
            System.out.println("-----------------------------------------------------------");
            System.out.println("|\t\tChoose what you want to do\t\t  |");
            System.out.println("-----------------------------------------------------------");
            System.out.println("|\t1.  Display Combination                           |");
            System.out.println("|\t2.  Add Combination                               |");
            System.out.println("|\t3.  Edit Combination                              |");
            System.out.println("|\t4.  Delete Combination                            |");
            System.out.println("|\t5.  Retrieve Combination                          |");
            System.out.println("|\t6.  Back                                          |");
            System.out.println("-----------------------------------------------------------");
           
           try{
                   System.out.print("\nEnter your choice : ");
                   choice = s.nextInt();
                   System.out.println("");
                   isint1=false;
               }catch(InputMismatchException e){
                        System.out.println("Please enter INTEGER only!");
                        s.nextLine();
                        isint1=true;
                    }
               
           }while(isint1);
                
            

            switch (choice) {
                case 1:
                    adminDisplayCombinationList();
                    break;
                case 2:
                    adminAddCombinationList();
                    break;
                case 3:
                    adminEditCombinationList();
                    break;
                case 4:
                    adminDeleteCombinationList();
                    break;
                case 5: 
                    adminRetrieveCombinationList();
                    break;
                case 6: 
                    mainMenu();
                    break;
                default:
                System.out.println("Please enter from 1 to 6 only.\n" );
                break;
            }
        } while (choice > 6 || choice <1);
  }

    public void adminDisplayCombinationList(){
    boolean back=false;
         Scanner sc = new Scanner(System.in);
         
        do{
           
            System.out.println("+---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+");
            System.out.println("| Combination ID  | Symptom ID  | Symptom\t  | Symtom Description\t\t\t\t             | Medicine ID | Medicine\t\t    | Price      | Medicine Description\t\t\t\t\t                |");
            System.out.println("+---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+");
        
        for(int i=1;i<=combinationLinkList.size();i++){
            System.out.printf("| %-15s | %-11s | %-15s | %-57s| %-11s | %-22s | RM %.2f   | %-69s|\n",combinationLinkList.get(i).getmSID(),combinationLinkList.get(i).getSymptom().getSID(),combinationLinkList.get(i).getSymptom().getSName(),combinationLinkList.get(i).getSymptom().getSDesc(),combinationLinkList.get(i).getMedicine().getmID(),combinationLinkList.get(i).getMedicine().getmName(),combinationLinkList.get(i).getMedicine().getmPrice(),combinationLinkList.get(i).getMedicine().getmDesc());
        }
        System.out.println("+---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+");
       
        System.out.print("\n Do You Want To Back To Sympthom Menu (Y/N)");
        String backM=sc.nextLine();
        switch(backM){
            case "Y":
            case "y":
                back=false;
                break;
            case "N":
            case "n":
                back=true;
                break;
            default:
                back=true;
                break;
        }
        }while(back);
        combinationMenu();
}
    
    public void adminAddCombinationList(){
        String sname ="",sdesc="",mname="",mdesc="";
        double mprice=0.00;
        boolean exists6=false;
        boolean exists2=false;
        boolean exists=false,exists1=false,exists4 =false,exists5 =true;
        boolean exists7=true;
        int j=1;
        char choice7,choice8;
        String first3="", newcID,newcMed,newcSym,sfirst3="",mfirst3="";
        Scanner scan=new Scanner(System.in);
        
       
       do{     
        System.out.println("\t\t**** Add Combination ****\t\t\n");
        System.out.println(" -1 to exit ");
        do{
        System.out.print("New Combination ID :  ");
        newcID=scan.next();
        exists =false;
        
        
         if(newcID.matches("-1")){
                combinationMenu();
            }
         newcID =newcID.toUpperCase();
         
            if(newcID.length()==6){
                 first3=newcID.substring(0, 3);
                if(first3.matches("COM")){
                    for(j=1;j<=combinationLinkList.size();j++){
                        if(combinationLinkList.get(j).getmSID().compareTo(newcID)==0){
                           System.out.println("Combination ID exists !");
                           exists =true;
                        }
                        
                    }
                    
                }
                else{
                    exists =true;
                    System.out.println("Enter 6 character that start from COM  \n");
                }
            }
            
            else{
                if(newcID.length()!=6){
                    System.out.println("Enter 6 character that start from COM  \n");
                }else{
                   System.out.println("Enter ccombination ID that start from COM  \n"); 
                }
                exists =true;
            }
           
            }while(newcID.length()!=6||!first3.matches("COM")||exists);
        
        do{
        System.out.print("Symptom ID         :  ");
        newcSym=scan.next();
        newcSym=newcSym.toUpperCase();
        exists5=true;
        exists1=false;
        if(newcSym.length()==6){
            
                 sfirst3=newcSym.substring(0, 3);
                if(sfirst3.matches("SYM")){
                    
                    for(j=1;j<=combinationLinkList.size();j++){
                        if(combinationLinkList.get(j).getSymptom().getSID().compareTo(newcSym)!=0){
                           exists4 =true;
                        }else{
                            exists4=false;
                            
                            if(!exists4){
                                sname=combinationLinkList.get(j).getSymptom().getSName();
                                sdesc=combinationLinkList.get(j).getSymptom().getSDesc();
                                exists5=false;
                            }
                        }
                       
                    }
                    if(exists5){
                        System.out.println("Symptom ID does not exists ! Please re-enter !\n");
                        exists5 =true;
                        j=1;
                    }
                    
                }
                else{
                    System.out.println("Enter 6 character that start from SYM  \n");
                    exists1 =true;
                    
                }
            }
            
            else{
                if(newcID.length()!=6){
                    System.out.println("Enter 6 character that start from SYM  \n");
                }else{
                   System.out.println("Enter Symptom ID that start from SYM  \n"); 
                }
                exists1 =true;
            }
        
        
        }while(exists1||exists5);
        
        
        do{
        System.out.print("Medicine ID        :  ");
        newcMed=scan.next();
        newcMed=newcMed.toUpperCase();
        exists7=true;
        exists2=false;
        if(newcMed.length()==6){
                 mfirst3=newcMed.substring(0, 3);
                if(mfirst3.matches("MED")){
                    for(j=1;j<=combinationLinkList.size();j++){
                        if(combinationLinkList.get(j).getMedicine().getmID().compareTo(newcMed)!=0){
                           exists6 =true;
                        }else{
                            exists6=false;
                            
                            if(!exists6){
                                mname=combinationLinkList.get(j).getMedicine().getmName();
                                mprice=combinationLinkList.get(j).getMedicine().getmPrice();
                                mdesc=combinationLinkList.get(j).getMedicine().getmDesc();
                                exists7=false;
                            }
                        }
                       
                    }
                    if(exists7){
                        System.out.println("Medicine ID does not exists ! Please re-enter !\n");   
                    }
                    
                }
                else{
                    System.out.println("Enter 6 character that start from MED  \n");
                    exists2 =true;
                }
            }
            
            else{
                if(newcID.length()!=6){
                    System.out.println("Enter 6 character that start from MED  \n");
                }else{
                   System.out.println("Enter medicine ID that start from MED  \n"); 
                }
                exists2 =true;
            }
        
        
        }while(exists2||exists7);
        
        
        
        System.out.println("\n\n----------------------------------------------------------------------------------------");
        System.out.println("|                                  Combination                                          |");
        System.out.println("----------------------------------------------------------------------------------------");
        System.out.print("\nNew Combination ID      :  ");
        System.out.println(newcID);
        System.out.print("Symptom ID              :  ");
        System.out.println(newcSym);
        System.out.print("Symptom  Name           :  ");
        System.out.println(sname);
        System.out.print("Symptom Description     :  ");
        System.out.println(sdesc);
        System.out.print("Medicine ID             :  ");
        System.out.println(newcMed);
        System.out.print("Medicine Name           :  ");
        System.out.println(mname);
        System.out.print("Medine Price            :  ");
        System.out.print("RM ");
        System.out.printf("%.2f \n",mprice);
        System.out.print("Medicine Description    :  ");
        System.out.println(mdesc);
            System.out.print("\n");
        //System.out.println(newMedDesc);
        System.out.println("----------------------------------------------------------------------------------------\n");
        
         do{
         System.out.print("Are you sure want to add (Y/N) ? ");
         choice7 = scan.next().charAt(0);
         if(choice7=='Y'||choice7=='y'){
             combinationLinkList.add(new Combination(newcID,new Symptom(newcSym,sname,sdesc),new Medicine(newcMed,mname,mprice,mdesc)));
         }
         else if(choice7=='N'||choice7=='n'){
             System.out.println("Nothing to be added ! ");
         }
         }while(choice7!='N'&&choice7!='n'&&choice7!='Y'&&choice7!='y');
         
      do{
            System.out.print("Are you want to continue to add (Y/N) ? ");
            choice8=scan.next().charAt(0);
            
          }while(choice8!='N'&&choice8!='n'&&choice8!='Y'&&choice8!='y');
         
          
  }while(choice8=='Y'||choice8=='y');
        if(choice8=='N'||choice8=='n'){
            combinationMenu();
        }

    }

    public void adminEditCombinationList(){
        
    boolean isint2=false,redo1=false,found=false,exists=true,exists1=false;
    boolean exists3=true;
    char editChoice,continueEdit;
    int editOption=0,sympEdit=0,comEdit=0;
    String sympID,medID;
    String first3="";
    String editSID="",editSName="",editSDes="";
    String editMID="",editMName="",editMDes="";
    Double editMPrice=0.00;
         
    Scanner scn=new Scanner(System.in);      
    
        do{
            System.out.println("+----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+");
            System.out.println("| N0.  | Combination ID  | Symptom ID\t| Symptom\t   | Symtom Description\t\t\t\t              | Medicine ID\t| Medicine\t\t | Price      | Medicine Description\t\t\t\t\t             |");
            System.out.println("+----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+");
        
        for(int i=1;i<=combinationLinkList.size();i++){
            System.out.printf("| %-4s | %-15s | %-12s | %-16s | %-57s| %-15s | %-22s | RM %.2f   | %-69s|\n",i,combinationLinkList.get(i).getmSID(),combinationLinkList.get(i).getSymptom().getSID(),combinationLinkList.get(i).getSymptom().getSName(),combinationLinkList.get(i).getSymptom().getSDesc(),combinationLinkList.get(i).getMedicine().getmID(),combinationLinkList.get(i).getMedicine().getmName(),combinationLinkList.get(i).getMedicine().getmPrice(),combinationLinkList.get(i).getMedicine().getmDesc());
        }
        System.out.println("+----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+");
    
        do{
            do{
                try{
                     System.out.print("Which one u want to edit ? (0 to exit):  ");
                     comEdit=scn.nextInt();
                     isint2=false;
                }catch(InputMismatchException e){
                            System.out.println("Please enter INTEGER only!");
                            scn.nextLine();
                            isint2=true;
                }
            }while(isint2);
            
            if(comEdit==0){
            redo1=false;
            combinationMenu();
            }
            
            else if(comEdit>0 && comEdit<=combinationLinkList.size()){
            boolean wrong=false,isdouble=false;
            redo1=false;  
            
            System.out.println("\n\n+--------------------------------------------------------------------------------------+");
            System.out.println("|                                  Combination                                          |");
            System.out.println("+--------------------------------------------------------------------------------------+");
            System.out.print("\nCombination ID        :  ");
            System.out.println(combinationLinkList.get(comEdit).getmSID());            
            System.out.print("Symptom ID              :  ");
            System.out.println(combinationLinkList.get(comEdit).getSymptom().getSID());            
            System.out.print("Symptom  Name           :  ");
            System.out.println(combinationLinkList.get(comEdit).getSymptom().getSName());
            System.out.print("Symptom Description     :  ");
            System.out.println(combinationLinkList.get(comEdit).getSymptom().getSDesc());
            System.out.print("Medicine ID             :  ");
            System.out.println(combinationLinkList.get(comEdit).getMedicine().getmID());
            System.out.print("Medicine Name           :  ");
            System.out.println(combinationLinkList.get(comEdit).getMedicine().getmName());
            System.out.print("Medicine Price            : RM ");
            System.out.println(combinationLinkList.get(comEdit).getMedicine().getmPrice());
            System.out.print("Medicine Description    :  ");
            System.out.println(combinationLinkList.get(comEdit).getMedicine().getmDesc());
            System.out.print("\n");
            System.out.println("+--------------------------------------------------------------------------------------+\n");                       
                   
            
    do{     
        System.out.println("\nChoose which to edit");
        System.out.println("---------------------------");
        System.out.println("1.Change Symptom ID");
        System.out.println("2.Change Medicine ID");
        
        do{
            try{
                System.out.print("\nEnter your choice : ");
                           editOption=scn.nextInt();
                            found=false;
            }catch(InputMismatchException e){
                            System.out.println("Please enter INTEGER only!");
                            scn.nextLine();
                            found=true;
            }
        }while(found);
    }while(editOption<1||editOption>2);

    switch(editOption)
        {
        case 1:                 
        System.out.println("\n** Edit Symptom **\t\t\n");
        System.out.println(" -1 to exit ");
        do{
        System.out.print("New Symptom ID :  ");
        sympID=scn.next();
       
        exists =true;
         if(sympID.matches("-1")){
                combinationMenu();
            }
          sympID =sympID.toUpperCase();
    
            if(sympID.length()==6){
                          first3=sympID.substring(0, 3);

                if(first3.matches("SYM")){
                    for(int j=1;j<=combinationLinkList.size();j++){
                        if(combinationLinkList.get(j).getSymptom().getSID().compareTo(sympID)==0){ 
                            
                           editSID=sympID;
                           editSName=combinationLinkList.get(j).getSymptom().getSName();
                           editSDes=combinationLinkList.get(j).getSymptom().getSDesc();
                           exists =false;
                        }
                        
                    }
                    
                    if(exists){
                        System.out.println("Medicine ID does not exists ! Please re-enter !\n");   
                    }
                    
                }
                else{
                    System.out.println("Enter 6 character that start from SYM  \n");
                }
            }

            else{
                if(sympID.length()!=6){
                    System.out.println("Enter 6 character that start from SYM  \n");
                }
                else{
                   System.out.println("Enter Symptoms ID that start from SYM  \n"); 
                }
            }
            
           
            }while(sympID.length()!=6||!first3.matches("SYM")||exists);                                 
        
             
                    do{
                        System.out.print("\nAre you sure you want to edit this combination record? (Y/N) : ");
                        editChoice = scn.next().trim().charAt(0);

                        if (editChoice == 'y' || editChoice == 'Y') {
                            Combination selectedCombination= combinationLinkList.get(comEdit);
                            selectedCombination.getSymptom().setSID(sympID);
                            combinationLinkList.replace(comEdit, selectedCombination);
                            
                            System.out.println("Symptom record updated!");
                            System.out.println("\n\n+--------------------------------------------------------------------------------------+");
                            System.out.println("|                                  Combination                                         |");
                            System.out.println("+--------------------------------------------------------------------------------------+");
                            System.out.print("\nCombination ID        :  ");
                            System.out.println(combinationLinkList.get(comEdit).getmSID());            
                            System.out.print("Symptom ID              :  ");
                            System.out.println(editSID);            
                            System.out.print("Symptom  Name           :  ");
                            System.out.println(editSName);
                            System.out.print("Symptom Description     :  ");
                            System.out.println(editSDes);
                            System.out.print("Medicine ID             :  ");
                            System.out.println(combinationLinkList.get(comEdit).getMedicine().getmID());
                            System.out.print("Medicine Name           :  ");
                            System.out.println(combinationLinkList.get(comEdit).getMedicine().getmName());
                            System.out.print("Medicine Price          : RM ");
                            System.out.println(combinationLinkList.get(comEdit).getMedicine().getmPrice());
                            System.out.print("Medicine Description    :  ");
                            System.out.println(combinationLinkList.get(comEdit).getMedicine().getmDesc());
                            System.out.print("\n");
                            System.out.println("+--------------------------------------------------------------------------------------+\n");                       
                        }
                        else {
                            System.out.println("This Symptom record is not updated.");
                            
                        }
                        }while(editChoice != 'y' && editChoice != 'Y'&&editChoice != 'n' && editChoice != 'N');
    
                        
                    break;
                    
                case 2:
                    
                    System.out.println("\n** Edit Medicine **\t\t\n");
                    System.out.println(" -1 to exit ");
               do{
                    System.out.print("New Medicine ID :  ");
                    medID=scn.next();
       
                exists =true;
                
                if(medID.matches("-1")){
                combinationMenu();
                  }
                medID =medID.toUpperCase();
    
                if(medID.length()==6){
                          first3=medID.substring(0, 3);

                if(first3.matches("MED")){
                    for(int j=1;j<=combinationLinkList.size();j++){
                        if(combinationLinkList.get(j).getMedicine().getmID().compareTo(medID)==0){ 
                            
                           editMID=medID;
                           editMName=combinationLinkList.get(j).getMedicine().getmName();
                           editMPrice=combinationLinkList.get(j).getMedicine().getmPrice();
                           editMDes=combinationLinkList.get(j).getMedicine().getmDesc();
                           exists =false;
                        }       
                    }
                    if(exists){
                        System.out.println("Medicine ID does not exists ! Please re-enter !\n");   
                    }
                    
                }
                else{
                    System.out.println("Enter 6 character that start from MED  \n");
                }
            }
            
            else{
                if(medID.length()!=6){
                   System.out.println("Enter 6 character that start from MED  \n");
                }else{
                   System.out.println("Enter Symptoms ID that start from MED  \n"); 
                }
            }
           
            }while(medID.length()!=6||!first3.matches("MED")||exists);        
                            
                    do{
                        System.out.print("\nAre you sure you want to edit this combination record? (Y/N) : ");
                        editChoice = scn.next().trim().charAt(0);

                        if (editChoice == 'y' || editChoice == 'Y') {
                            
                            Combination selectedCombination= combinationLinkList.get(comEdit);
                            selectedCombination.getMedicine().setmID(medID);
                            combinationLinkList.replace(comEdit, selectedCombination);
                            System.out.println("Medicine record updated!");
                            System.out.println("\n\n+--------------------------------------------------------------------------------------+");
                            System.out.println("|                                  Combination                                          |");
                            System.out.println("+--------------------------------------------------------------------------------------+");
                            System.out.print("\nCombination ID        :  ");
                            System.out.println(combinationLinkList.get(comEdit).getmSID());            
                            System.out.print("Symptom ID              :  ");
                            System.out.println(combinationLinkList.get(comEdit).getSymptom().getSID());            
                            System.out.print("Symptom  Name           :  ");
                            System.out.println(combinationLinkList.get(comEdit).getSymptom().getSName());
                            System.out.print("Symptom Description     :  ");
                            System.out.println(combinationLinkList.get(comEdit).getSymptom().getSDesc());
                            System.out.print("Medicine ID             :  ");
                            System.out.println(editMID);
                            System.out.print("Medicine Name           :  ");
                            System.out.println(editMName);
                            System.out.print("Medicine Price          :RM  ");
                            System.out.println(editMPrice);
                            System.out.print("Medicine Description    :  ");
                            System.out.println(editMDes);
                            System.out.print("\n");
                            System.out.println("+--------------------------------------------------------------------------------------+\n");                       
                        }
                        else {
                            System.out.println("This Medicine record is not updated.");
                            
                        }
                        }while(editChoice != 'y' && editChoice != 'Y'&&editChoice != 'n' && editChoice != 'N');                      
                    break;      
                    default:
                        System.out.println("Please enter number between 1 to 2 only");
                    break;
                }  
            }
        else{
            System.out.println("Does not exits !");
                     redo1=true;
            }
    }while(redo1);
    
    
        do{
            System.out.print("\nDo you want to continue editing? : ");
            continueEdit = scn.next().trim().charAt(0);
            scn.nextLine();
            System.out.println("");  
            }while(continueEdit!= 'y' && continueEdit != 'Y'&&continueEdit!= 'n' && continueEdit != 'N');
        
        
        }while (continueEdit == 'y' || continueEdit == 'Y');
        
        if(continueEdit=='n'||continueEdit=='N'){
            combinationMenu();
        }
}

    public void adminDeleteCombinationList(){
    Scanner scn = new Scanner(System.in);
            int i=1;
            int comDelete=0;
            String comdlt;
            char choice2,choice3;
            boolean mistake=false;
            
            
           do{
            System.out.println("+------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+");
            System.out.println("| No | Combination ID  | Symptom ID   | Symptom\t         | Symtom Description\t\t\t                    | Medicine ID   | Medicine\t\t     | Price      | Medicine Description\t\t\t\t\t         |");
            System.out.println("+------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+");
        
            for(int g=1;g<=combinationLinkList.size();g++){
                System.out.printf("| %2d | %-15s | %-12s | %-16s | %-57s| %-13s | %-22s | RM %.2f   | %-69s|\n",g,combinationLinkList.get(g).getmSID(),combinationLinkList.get(g).getSymptom().getSID(),combinationLinkList.get(g).getSymptom().getSName(),combinationLinkList.get(g).getSymptom().getSDesc(),combinationLinkList.get(g).getMedicine().getmID(),combinationLinkList.get(g).getMedicine().getmName(),combinationLinkList.get(g).getMedicine().getmPrice(),combinationLinkList.get(g).getMedicine().getmDesc());
            }
            System.out.println("+-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+");
       
            
        do{
            
           try{
        System.out.print("Which one u want to delete ? (0 to exit):  ");
        comDelete=scn.nextInt();
           }catch(InputMismatchException ex){
                
                        scn.nextLine();
                        mistake=true;
           }       
        if(comDelete==0){
            combinationMenu();
        }
        else if(comDelete>combinationLinkList.size()){
            System.out.println(" Out of Range!  \n");
            mistake=true;
            
        }
        else {
        boolean wrong=false;
        mistake=false;
        System.out.println("\n\n----------------------------------------------------------------------------------------");
        System.out.println("|                                  Combination                                          |");
        System.out.println("----------------------------------------------------------------------------------------");
        System.out.print("\nCombination ID          :  ");
        System.out.println(combinationLinkList.get(comDelete).getmSID());
        System.out.print("Symptom ID              :  ");
        System.out.println(combinationLinkList.get(comDelete).getSymptom().getSID());
        System.out.print("Symptom  Name           :  ");
        System.out.println(combinationLinkList.get(comDelete).getSymptom().getSName());
        System.out.print("Symptom Description     :  ");
        System.out.println(combinationLinkList.get(comDelete).getSymptom().getSDesc());
        System.out.print("Medicine ID             :  ");
        System.out.println(combinationLinkList.get(comDelete).getMedicine().getmID());
        System.out.print("Medicine Name           :  ");
        System.out.println(combinationLinkList.get(comDelete).getMedicine().getmName());
        System.out.print("Medine Price            :  ");
        System.out.print("RM ");
        System.out.printf("%.2f \n",combinationLinkList.get(comDelete).getMedicine().getmPrice());
        System.out.print("Medicine Description    :  ");
        System.out.println(combinationLinkList.get(comDelete).getMedicine().getmDesc());
        System.out.print("\n");
        
        System.out.println("----------------------------------------------------------------------------------------\n");

        
        do{
        System.out.print("Are you sure you want to delete this symptom record? (Y/N) : ");
        choice2 = scn.next().charAt(0);
        }while(choice2!='N'&&choice2!='n'&&choice2!='Y'&&choice2!='y');

        if (choice2 == 'y' || choice2 == 'Y') {
            combinationLinkList.remove(comDelete); 
            System.out.println("\nSymptom record deleted!\n");
            } else {
                System.out.println("\nThis service record is not deleted.\n");
                }
        }
        
         }while(mistake);
        
        do{
        System.out.print("Do you still want to continue deleting? (Y/N) : ");
        choice3 = scn.next().charAt(0);
        }while(choice3!='N'&&choice3!='n'&&choice3!='Y'&&choice3!='y');
        
        
        }while(choice3 == 'y' || choice3 == 'Y');   
            
        if(choice3=='n'||choice3=='N'){
            combinationMenu();
        }
}

    public void adminRetrieveCombinationList(){
    Scanner sn=new Scanner(System.in);
            String searchCom,first3="";
            char choice4;
            boolean found=false;
            do{
            System.out.println("+---------------------------------------------------------+");
            System.out.println("|\t\t Search Medicine (Medicine ID)\t\t  |");
            System.out.println("+---------------------------------------------------------+\n\n");
        
            do{
            System.out.println(" -1 to exit ");
            System.out.print("Medicine ID  (COM___)   :");
            searchCom=sn.next();
            
            if(searchCom.matches("-1")){
                combinationMenu();
            }
            searchCom=searchCom.toUpperCase();
           
            if(searchCom.length()==6){
                 first3=searchCom.substring(0, 3);
                if(first3.matches("COM")){
                    found=false;
                    for(int j=1;j<=combinationLinkList.size();j++){
                        
                        if(combinationLinkList.get(j).getmSID().compareTo(searchCom)==0){
                            found=true;
                             System.out.println("\n\n----------------------------------------------------------------------------------------");
                            System.out.println("|                                  Combination                                          |");
                            System.out.println("----------------------------------------------------------------------------------------");
                            System.out.print("\nCombination ID          :  ");
                            System.out.println(combinationLinkList.get(j).getmSID());
                            System.out.print("Symptom ID              :  ");
                            System.out.println(combinationLinkList.get(j).getSymptom().getSID());
                            System.out.print("Symptom  Name           :  ");
                            System.out.println(combinationLinkList.get(j).getSymptom().getSName());
                            System.out.print("Symptom Description     :  ");
                            System.out.println(combinationLinkList.get(j).getSymptom().getSDesc());
                            System.out.print("Medicine ID             :  ");
                            System.out.println(combinationLinkList.get(j).getMedicine().getmID());
                            System.out.print("Medicine Name           :  ");
                            System.out.println(combinationLinkList.get(j).getMedicine().getmName());
                            System.out.print("Medine Price            :  ");
                            System.out.print("RM ");
                            System.out.printf("%.2f \n",combinationLinkList.get(j).getMedicine().getmPrice());
                            System.out.print("Medicine Description    :  ");
                            System.out.println(combinationLinkList.get(j).getMedicine().getmDesc());
                            System.out.print("\n");
        
                            System.out.println("----------------------------------------------------------------------------------------\n");
                        }
                        
                    }
                    if(!found){
                        System.out.println("Combination ID does not exist  \n");
                    }
                    
                }
                else{
                    
                    System.out.println("Enter 6 character that start from COM  \n");
                
                        
                }
            
            }
            
            else{
                if(searchCom.length()!=6){
                    System.out.println("Enter 6 character that start from COM  \n");
                }else{
                   System.out.println("Enter Symtoms ID that start from COM  \n"); 
                }
                 
            
            }
            
            }while(searchCom.length()!=6||!first3.matches("COM")||!found);
            
            do{
            System.out.print("Do you still want to continue searching? (Y/N) : ");
            choice4 = sn.next().charAt(0);
            }while(choice4!='N'&&choice4!='n'&&choice4!='Y'&&choice4!='y');
            
            if(choice4=='N'||choice4=='n'){
                combinationMenu();
            }
            
        }while(choice4=='Y'||choice4=='y');
}


}