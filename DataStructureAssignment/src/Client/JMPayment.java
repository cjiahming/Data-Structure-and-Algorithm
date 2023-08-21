package Client;

import Entity.CartItem;
import Entity.Combination;
import Entity.Delivery;
import Entity.Medicine;
import Entity.Patient;
import Entity.Payment;
import Entity.Symptom;
import adt.ArrayStack;
import adt.DynamicQueue;
import java.util.Scanner;

/**
 *
 * @author Benjamin Choong
 */
public class JMPayment {

    public static void main(String[] args) {

//      //declare a normal profile (FROM GABRIEL SIDE)
        Patient patient1 = new Patient("P00001");
        Patient patient2 = new Patient("P00002");
        Medicine medicine = new Medicine("M00001", "Carrot", 30, "Eat every 3 days");
        Symptom symptom = new Symptom("S0001", "Cough", "Cough multiple times a day");
        Medicine medicine2 = new Medicine("M00002", "Caadwa", 50, "Eat eawday 3 days");
        Combination combination = new Combination("C0001", symptom, medicine);
        Combination combination2 = new Combination("C0002", symptom, medicine2);

        //---------------------------------------------------------------------------------
        //Declare object class(JM)
        Payment p = new Payment();

        //Declare Array Stack (JM)
        ArrayStack<Payment> paymentArrStack = new ArrayStack<Payment>();

        // (FROM GABRIEL SIDE)
        Payment p1 = new Payment("PM000001", "TnG", p.setLocalDateTime());
        Payment p2 = new Payment("PM000002", "Credit/Debit Card", p.setLocalDateTime());
        Payment p3 = new Payment("PM000003", "Online Banking", p.setLocalDateTime());

        paymentArrStack.push(p1);
        paymentArrStack.push(p2);
        paymentArrStack.push(p3);

        CartItem cartItem = new CartItem(1, combination, patient2, new Payment());
        CartItem cartItem2 = new CartItem(4, combination2, patient2, new Payment());
        CartItem cartItem3 = new CartItem(6, combination, patient1, p3);

        //create ArrayStack (FROM GABRIEL SIDE)
        ArrayStack<CartItem> cartArrS = new ArrayStack<CartItem>();
        cartArrS.push(cartItem);
        cartArrS.push(cartItem2);
        cartArrS.push(cartItem3);

        //Declare methods (JM)
        //makePayment(cartArrS, paymentArrStack, p, patient1);
        //paymentMenu(paymentArrStack, patient2, cartArrS);
    }

    //After cart, will proceed to this method which lets user to make payment and add the record to the stack
    public static void makePayment(ArrayStack<CartItem> cartArrS, ArrayStack<Payment> paymentArrStack, Patient patient, DynamicQueue<Delivery> deliveryQ) {
        Payment p = new Payment();
        Scanner sc = new Scanner(System.in);

        boolean amountCorrect = false;
        double totalPrice = 0;

        int index = 0;

        int paymentMethod;
        String paymentMethodChosen = null;
        double paymentAmount;

        System.out.println("-----------------------------------------------------------------------------------------");
        System.out.println("| Item Name\t  Unit Price\t\t  Quantity\t\t    Item Subtotal\t|");
        System.out.println("-----------------------------------------------------------------------------------------");

        for (int i = 0; i <= cartArrS.getTopIndex(); i++) {
            if (patient.getUserID().equals(cartArrS.peek(i).getPatient().getUserID()) && cartArrS.peek(i).getPayment().getPaymentID() == null) {
                totalPrice += (cartArrS.peek(i).getCombination().getMedicine().getmPrice()) * cartArrS.peek(i).getQty();
                System.out.printf("| %-15s %-23s %-25s %-20.2f|\n", cartArrS.peek(i).getCombination().getMedicine().getmName(), cartArrS.peek(i).getCombination().getMedicine().getmPrice(), cartArrS.peek(i).getQty(), (cartArrS.peek(i).getCombination().getMedicine().getmPrice()) * cartArrS.peek(i).getQty());
            }
            index = i;
        }

        System.out.println("-----------------------------------------------------------------------------------------");
        System.out.printf("|\t\t\t\t\t\t\t    Total : %-20.2f|", totalPrice);
        System.out.println("\n-----------------------------------------------------------------------------------------\n");

        System.out.println("Select a payment method :");
        System.out.println("----------------------------");
        System.out.println("1. Touch N Go");
        System.out.println("2. Online Banking");
        System.out.println("3. Credit/Debit Card");
        System.out.println("4. Cancel\n");

        System.out.print("Your choice : ");
        paymentMethod = sc.nextInt();
        System.out.println("");

        if (paymentMethod == 1) {
            paymentMethodChosen = "Touch N Go";
            do {
                System.out.print("Enter amount to pay : ");
                paymentAmount = sc.nextDouble();

                if (paymentAmount != totalPrice) {
                    System.out.println("You have given a wrong amount. You have to pay RM" + totalPrice);
                    System.out.println("");
                } else {
                    amountCorrect = true;
                }
            } while (!amountCorrect);
        } else if (paymentMethod == 2) {
            paymentMethodChosen = "Online Banking";
            do {
                System.out.print("Enter amount to pay : ");
                paymentAmount = sc.nextDouble();

                if (paymentAmount != totalPrice) {
                    System.out.println("You have given a wrong amount. You have to pay RM" + totalPrice);
                    System.out.println("");
                } else {
                    amountCorrect = true;
                }
            } while (!amountCorrect);
        } else if (paymentMethod == 3) {
            paymentMethodChosen = "Credit/Debit Card";
            do {
                System.out.print("Enter amount to pay : ");
                paymentAmount = sc.nextDouble();

                if (paymentAmount != totalPrice) {
                    System.out.println("You have given a wrong amount. You have to pay RM" + totalPrice);
                    System.out.println("");
                } else {
                    amountCorrect = true;
                }
            } while (!amountCorrect);
        } else {
            System.out.println("Cancelled");
        }

        if (amountCorrect) {
            cartItemClient cic = new cartItemClient();
            System.out.println("Payment Success!\n");
            p = new Payment("P", paymentMethodChosen, p.setLocalDateTime());
            //Display receipt after payment
            paymentReceipt(cartArrS, paymentMethodChosen, totalPrice, patient);

            for (int i = 0; i <= cartArrS.getTopIndex(); i++) {
                if (patient.getUserID().equals(cartArrS.peek(i).getPatient().getUserID()) && cartArrS.peek(i).getPayment().getPaymentID() == null) {
                    CartItem localCartItem = cartArrS.peek(i);
                    localCartItem.setPayment(p);
                    cic.editCartQty(cartArrS, localCartItem);
                }
            }

            paymentArrStack.push(p);

            //add delivery
            Delivery newDelivery = new Delivery("Order Received", p);

            //add to deliveryStack
            deliveryQ.enqueue(newDelivery);

            System.out.println("Successfully Added Delivery");

        }
    }

    //display payment receipt to user after payment
    public static void paymentReceipt(ArrayStack<CartItem> cartArrS, String paymentMethodChosen, double totalPrice, Patient patient) {
        int qty = 0;

        System.out.println("==========================================================================================");
        System.out.println("||\t\t\t\t\tReceipt\t\t\t\t\t\t||");
        System.out.println("||\t\t\t\t\t\t\t\t\t\t\t||");
        System.out.println("||\t\t\t\t\t\t\t\t\t\t\t||");
        System.out.println("|| Item Name\t  Unit Price\t\t  Quantity\t\t    Item Subtotal\t||");
        System.out.println("==========================================================================================");
        for (int i = 0; i <= cartArrS.getTopIndex(); i++) {
            if (patient.getUserID().equals(cartArrS.peek(i).getPatient().getUserID()) && cartArrS.peek(i).getPayment().getPaymentID() == null) {
                System.out.printf("|| %-14s %-23s %-25s %-20.2f||\n", cartArrS.peek(i).getCombination().getMedicine().getmName(), cartArrS.peek(i).getCombination().getMedicine().getmPrice(), cartArrS.peek(i).getQty(), (cartArrS.peek(i).getCombination().getMedicine().getmPrice()) * cartArrS.peek(i).getQty());
                qty += cartArrS.peek(i).getQty();
            }
        }
        System.out.println("==========================================================================================");
        System.out.printf("|| Total number of medicine purchased : %-48d||\n", qty);
        System.out.printf("|| Total amount paid : RM%-63.2f||\n", totalPrice);
        System.out.printf("|| Paid using : %-72s||\n", paymentMethodChosen);
        System.out.println("||\t\t\t\t\t\t\t\t\t\t\t||");
        System.out.println("||\t\t\t\t      End Of Receipt\t\t\t\t\t||");
        System.out.println("==========================================================================================\n");

    }

    //Display payment history for customer (Display from newest payment record to oldest payment record) - LIFO
    public static void paymentHistory(ArrayStack<Payment> paymentArrStack, Patient patient, ArrayStack<CartItem> cartArrS) {
        boolean found = false;
        int count = 0;

        System.out.println("\t\tYour Payment History");
        System.out.println("=========================================================");
        for (int i = 0; i < paymentArrStack.getTopIndex() + 1; i++) {
            if (patient.getUserID().equals(getInfo(paymentArrStack.peek(i).getPaymentID(), cartArrS))) {
                if (idExist(paymentArrStack.peek(i).getPaymentID(), cartArrS)) {
                    System.out.println("Payment ID : " + paymentArrStack.peek(i).getPaymentID());
                    System.out.println("Payment Total : RM" + countPrice(paymentArrStack.peek(i).getPaymentID(), cartArrS));
                    System.out.println("Payment Date : " + paymentArrStack.peek(i).getDate());
                    System.out.println("Payment Method : " + paymentArrStack.peek(i).getPaymentMethod());

                    System.out.println("Purchased Medicine : ");
                    
                        for (int z = 0; z <= cartArrS.getTopIndex(); z++) {
                            if (paymentArrStack.peek(i).getPaymentID().equals(cartArrS.peek(z).getPayment().getPaymentID())) {
                                System.out.println("     " + (count + 1) + ". " + cartArrS.peek(z).getCombination().getMedicine().getmName());
                                count++;
                            }
                        }
                    
                    count = 0;

                    System.out.println("---------------------------------------------------");
                    found = true;
                }
            }
        }
    }

    //Display all payment records (Admin side)
    public static void displayAllPayment(ArrayStack<Payment> paymentArrStack, ArrayStack<CartItem> cartArrS) {
        System.out.println("---------------------------------------------------------------------------------------------------------------------------");
        System.out.println("| Payment ID\t  Payment Method\tPayment Time\t\t Paid Amount(RM)\t User ID\t  Username        |");
        System.out.println("---------------------------------------------------------------------------------------------------------------------------");

        for (int i = 0; i < paymentArrStack.getTopIndex() + 1; i++) {
            if (idExist(paymentArrStack.peek(i).getPaymentID(), cartArrS)) {
                Payment payment = paymentArrStack.peek(i);
                System.out.printf("| %-15s %-21s %-24s %-23.2f %-16s %-13s   |\n", payment.getPaymentID(), paymentArrStack.peek(i).getPaymentMethod(), paymentArrStack.peek(i).getDate(), countPrice(paymentArrStack.peek(i).getPaymentID(), cartArrS), getUserID(payment.getPaymentID(), cartArrS), getUsername(payment.getPaymentID(), cartArrS));
            }
        }

        System.out.println("---------------------------------------------------------------------------------------------------------------------------\n");
    }

    //Main menu for payment (not sure if gonna use it or not)
    public static void paymentMenu(ArrayStack<Payment> paymentArrStack, Patient patient, ArrayStack<CartItem> cartArrS, DynamicQueue<Delivery> deliveryQ) {
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("-----------------------------------------------------------");
            System.out.println("|\t\tChoose what you want to do\t\t  |");
            System.out.println("-----------------------------------------------------------");
            System.out.println("|\t1.  Make Payment                                  |");
            System.out.println("|\t2.  Payment History                               |");
            System.out.println("|\t3.  Display all payment records (admin)           |");
            System.out.println("|\t4.  Search Payment Records (admin)                |");
            System.out.println("|\t5.  Back to homepage                              |");
            System.out.println("-----------------------------------------------------------");
            System.out.print("\nEnter your choice : ");
            choice = sc.nextInt();

            if (choice == 1) {
                makePayment(cartArrS, paymentArrStack, patient, deliveryQ);
            } else if (choice == 2) {
                paymentHistory(paymentArrStack, patient, cartArrS);
            } else if (choice == 3) {
                if (patient.isIsAdmin()) {
                    displayAllPayment(paymentArrStack, cartArrS);
                } else {
                    System.out.println("You do not have permission to access this function.");
                }
            } else if (choice == 4) {
                if (patient.isIsAdmin()) {
                    searchPayment(paymentArrStack, cartArrS);
                } else {
                    System.out.println("You do not have permission to access this function.");
                }
            } else if (choice == 5) {
                System.out.println("Exit Payment");
                break;
            } else {
                System.out.println("Invalid input. Please input 1 to 5 only\n");
            }

        } while (choice != 5);
    }

    //For admin to search specific payment records (Admin Side)
    public static void searchPayment(ArrayStack<Payment> paymentArrStack, ArrayStack<CartItem> cartArrS) {
        Scanner sc = new Scanner(System.in);
        String paymentID;
        double paidAmount = 0;
        char continueEdit;
        boolean found = false;

        do {
            found = false;
            paidAmount = 0;
            System.out.print("Search Payment ID(TYPE XXX to quit) : ");
            paymentID = sc.nextLine();
            paymentID = paymentID.toUpperCase();

            if (paymentID.equalsIgnoreCase("XXX")) {
                System.out.println("------------------------------------------------------------\n");
                break;
            }

            for (int i = 0; i <= paymentArrStack.getTopIndex(); i++) {
                if (paymentArrStack.peek(i).getPaymentID().equals(paymentID)) {
                    if (idExist(paymentArrStack.peek(i).getPaymentID(), cartArrS)) {
                        found = true;
                        System.out.println("\n1 Record Found!");
                        System.out.println("---------------------------------------------------------------------------------------------------------------------------");
                        System.out.println("| Payment ID\t  Payment Method\tPayment Time\t\t Paid Amount(RM)\t User ID\t  Username        |");
                        System.out.println("---------------------------------------------------------------------------------------------------------------------------");
                        System.out.printf("| %-15s %-21s %-24s %-23.2f %-16s %-13s   |\n", paymentArrStack.peek(i).getPaymentID(), paymentArrStack.peek(i).getPaymentMethod(), paymentArrStack.peek(i).getDate(), countPrice(paymentArrStack.peek(i).getPaymentID(), cartArrS), getUserID(paymentArrStack.peek(i).getPaymentID(), cartArrS), getUsername(paymentArrStack.peek(i).getPaymentID(), cartArrS));
                        System.out.println("---------------------------------------------------------------------------------------------------------------------------");
                    }
                }
            }
            if (!found) {
                System.out.println("Payment ID not found!");
            }

            System.out.print("Do you want to continue searching?(Y/N) : ");
            continueEdit = sc.next().trim().charAt(0);
            sc.nextLine();
            System.out.println("");
        } while (continueEdit == 'y' || continueEdit == 'Y');
    }

    /* ----------------- OTHER METHODS USED FOR OTHER PURPOSES --------------------- */
    //Search whether the paymentID is null or not null in the cart array stack
    public static boolean idExist(String paymentID, ArrayStack<CartItem> cartArrS) {
        boolean idExisted = false;

        for (int i = 0; i <= cartArrS.getTopIndex(); i++) {
            if (paymentID.equals(cartArrS.peek(i).getPayment().getPaymentID())) {
                if (cartArrS.peek(i).getPayment().getPaymentID() != null) {
                    idExisted = true;
                }
            }
        }
        return idExisted;
    }

    //Get user ID of a specific cart
    public static String getUserID(String paymentID, ArrayStack<CartItem> cartArrS) {
        String userID = null;
        String username = null;

        for (int i = 0; i <= cartArrS.getTopIndex(); i++) {
            if (paymentID.equals(cartArrS.peek(i).getPayment().getPaymentID())) {
                if (cartArrS.peek(i).getPayment().getPaymentID() != null) {
                    userID = cartArrS.peek(i).getPatient().getUserID();
                }
            }
        }
        return userID;
    }

    //Get user username of a specific cart
    public static String getUsername(String paymentID, ArrayStack<CartItem> cartArrS) {
        String userID = null;
        String username = null;

        for (int i = 0; i <= cartArrS.getTopIndex(); i++) {
            if (paymentID.equals(cartArrS.peek(i).getPayment().getPaymentID())) {
                if (cartArrS.peek(i).getPayment().getPaymentID() != null) {
                    username = cartArrS.peek(i).getPatient().getUsername();
                }
            }
        }
        return username;
    }

    //Calculate the total price of a specific cart
    public static double countPrice(String paymentID, ArrayStack<CartItem> cartArrS) {
        float totalPrice = 0;
        for (int i = 0; i <= cartArrS.getTopIndex(); i++) {
            if (paymentID.equals(cartArrS.peek(i).getPayment().getPaymentID())) {
                totalPrice += (cartArrS.peek(i).getCombination().getMedicine().getmPrice()) * cartArrS.peek(i).getQty();
            }
        }
        return totalPrice;
    }

    //Get userID for paymentHistory
    public static String getInfo(String paymentID, ArrayStack<CartItem> cartArrS) {
        String userID = null;

        for (int i = 0; i <= cartArrS.getTopIndex(); i++) {
            if (paymentID.equals(cartArrS.peek(i).getPayment().getPaymentID())) {
                userID = cartArrS.peek(i).getPatient().getUserID();
            }
        }
        return userID;
    }
}
