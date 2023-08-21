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
import java.util.Scanner;
import adt.ListArrayInterface;
import adt.ListArray;
import adt.ListInterface;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import adt.*;

/**
 *
 * @author Ding Jiar Kee
 */
public class Profile {
    
    public static ListArray<Patient> p = new ListArray<>();
    
    public static Scanner s = new Scanner(System.in).useDelimiter("\n");
    
    Patient patient = new Patient();
    private LinkedList<Medicine> medicineLinkList = new LinkedList<Medicine>();
    private LinkedList<Combination>combinationLinkList = new LinkedList<Combination>();
    private ArrayList<Symptom> symptomArrayList = new ArrayList<Symptom>();
    private ArrayStack<CartItem> cartArrS = new ArrayStack<CartItem>();
    private DynamicQueue<Delivery> deliveryQ = new DynamicQueue<Delivery>();
    ArrayStack<Payment> paymentArrStack = new ArrayStack<Payment>();
    
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    
    EClinic eclinic = new EClinic(medicineLinkList,combinationLinkList,symptomArrayList,cartArrS,deliveryQ ,patient,paymentArrStack);
    
    public  static void main(String[] main){
        Profile profile = new Profile();

        profile.initialise();
        int loginOrRegister;
        
        System.out.println("----------------------------------------------------------------------------------------------------");
        System.out.println("\t\t\t\t   WELCOME TO XXX CLINIC!");
        System.out.println("----------------------------------------------------------------------------------------------------");

        do{
            String id="",name="",ic="",password="",add="";
            int a;
            double w;
            boolean admin = false;
            System.out.println("\nDo you want to LOGIN or REGISTER?");
            loginOrRegister = scanInt("(1.Login / 2.Register / 0.Exit) => ");
            s.nextLine();
            
            switch(loginOrRegister){
                case 1: 
                    profile.login();
                    break;
                      
                case 2:
                    int pa,exit=1;
                    try {
                        do {

                            pa = scanInt("\nRegister as (1.Patient / 2. Admin) : ");

                            switch (pa) {
                                case 1:
                                    admin = false;
                                    break;
                                case 2:
                                    admin = true;
                                    break;
                                default:
                                    do {
                                        System.out.println(ANSI_RED + "\n\t\t\t\tPlease enter 1 / 2 only!" + ANSI_RESET);
                                        exit = scanInt("\nDo you want to continue register?(1.Yes/2.No) : ");
                                    } while (exit != 1 && exit != 2);

                                    break;
                            }
                            if (pa == 1 || pa == 2) {

                                System.out.println("----------------------------------------");
                                System.out.print("Create a User ID\t\t: ");
                                id = s.next();
                                System.out.print("Create a Password\t: ");
                                password = s.next();
                                System.out.print("Create a Username\t: ");
                                name = s.next();
                                System.out.println("Your personal info----------------------");
                                System.out.print("IC No\t: ");
                                ic = s.next();
                                a = scanInt("Age\t: ");
                                System.out.print("Weight\t: ");
                                w = s.nextDouble();
                                System.out.print("Address\t: ");
                                add = s.next();

                                p.add(new Patient(id, name, ic, password, a, w, add, admin));

                                System.out.println(ANSI_GREEN + "\n\t\t\t\tYou have registered an account!" + ANSI_RESET);
                                exit = 2;
                            }

                        } while (exit != 2);        //pa != 1 || pa != 2 || 
                    } catch (Exception e) {
                        System.out.println(ANSI_RED + "\n\t\t\t      Invalid input, exiting the process." + ANSI_RESET);
                    }
                    break;
                        
                case 0:
                    System.out.println(ANSI_BLUE+"\n\t\t\t\t  Thanks for using this system!"+ANSI_RESET);
                    System.out.println(ANSI_BLUE+"\t\t\t\t\tHave A Nice Day :)\n"+ANSI_RESET);
                    break;
                    
                default:
                    System.out.println(ANSI_RED+"\n\t\t\t\tPlease enter 1 / 2 / 0 only!"+ANSI_RESET);
                    break;
            }
        }while(loginOrRegister!=0);
    }
    
    public  void login(){
        
        String id="", userid, password="", pass;
        int loginTime = 3, loginSuccess = 0, dltAcc=0;
        boolean admin;
        
        try{
            if(p.sizeOfList()==0){
                System.out.println(ANSI_BLUE+"\n\t\t   There is no registered account in system. Please register!"+ANSI_RESET);
            }
            else{
                do {
                    System.out.print("\n");
                    System.out.println("----------------------------------------");
                    System.out.print("Please enter your User ID\t: ");
                    id = s.next();
                    System.out.print("Please enter your Password\t: ");
                    password = s.next();

                    int noRecord = 0;

                    for (int i = 1; i <= p.sizeOfList(); i++) {
                        userid = p.getArray(i).getUserID();
                        pass = p.getArray(i).getPassword();
                        admin = p.getArray(i).isIsAdmin();
                        if (userid.equals(id) && pass.equals(password)) {
                            System.out.println(ANSI_GREEN+"\n\t\t\t\tYou have login successfully!"+ANSI_RESET);
                            
                            //keep the patient become local
                            patient = p.getArray(i);
                            loginSuccess = 1;
                            dltAcc = patientMenu(i);
                            noRecord = 0;
                            
                        } else {
                            noRecord++;
                        }
                    }
                    if (noRecord == p.sizeOfList()) {
                        if (p.sizeOfList() != 0) {

                            loginTime--;
                            if (loginTime == 2) {
                                System.out.println(ANSI_RED+"\n\t\t     Your User ID or Password is wrong. Please try again!"+ANSI_RESET);
                                System.out.println(ANSI_RED+"\t\t\t     You have 2 more chance to input"+ANSI_RESET);
                            } else if (loginTime == 1) {
                                System.out.println(ANSI_RED+"\n\t\t     Your User ID or Password is wrong. Please try again!"+ANSI_RESET);
                                System.out.println(ANSI_RED+"\t\t\t     You have 1 more chance to input"+ANSI_RESET);

                            }
                        }
                    }
                } while (loginSuccess != 1 && loginTime != 0);

                if (dltAcc == 1) {
                    System.out.println(ANSI_GREEN + "\n\t\t\t\t  Your account is deleted!" + ANSI_RESET);
                } else if (loginSuccess == 1 && dltAcc != 1) {
                    System.out.println(ANSI_GREEN + "\n\t\t\t\t      You have log out!" + ANSI_RESET);
                } else {
                    System.out.println(ANSI_RED + "\n\t\t     You have enter 3 times invalid User ID or Password. " + ANSI_RESET);
                    System.out.println(ANSI_RED + "\t\t  Maybe you havent register an account. Please register first!" + ANSI_RESET);
                }//if(loginTime==0)
            }
        }
        catch(Exception e){
            System.out.println(ANSI_RED + "\n\t\t\t      Invalid input, exiting the system.\n" + ANSI_RESET);
        }
    }
    
    public int patientMenu(int j){
        
        int noPatient, logout = 0, invalid=0, dltAcc=0;
        do{
            System.out.print("\n");
            System.out.println("----------------------------------------------------------------------------------------------------");
            System.out.println("MENU");
            System.out.println("----------------");
            System.out.println("1. Profile");
            System.out.println("2. EClinic");
            System.out.println("0. Log Out.");
            System.out.println("---------------------------------------");
            noPatient = scanInt("Please input your selection\t: ");

            switch (noPatient) {
                case 1:
                    dltAcc = profile(j);
                    if (dltAcc == 1) {
                        logout = 1;
                    } else {
                        logout = 0;
                    }
                    break;
                case 0:
                    do {
                        logout = scanInt("Confirm Log Out? (1.Yes/2.No)\t: ");
                        if (logout != 1 && logout != 2) {
                            invalid = 1;
                            System.out.println(ANSI_RED + "\t\t\t\tPlease enter 1 or 2 only!" + ANSI_RESET);
                        } else {
                            invalid = 0;
                        }
                    } while (invalid == 1);
                    break;
                case 2:
                    eclinic.main(medicineLinkList, combinationLinkList, symptomArrayList,cartArrS,deliveryQ,patient,paymentArrStack);
                    break;
                default:
                    System.out.println(ANSI_RED + "\n\t\t\t\tPlease enter 0 - 2 only!" + ANSI_RESET);
                    break;
            }
        } while (logout != 1);
        
        return dltAcc;
    }
    
    public int profile(int j){
        
        int noMenu, back = 0, dlt=0;
        String id;
        
        do{
            id = p.getArray(j).getUserID();
            System.out.print("\n");
            System.out.println("----------------------------------------------------------------------------------------------------");
            System.out.println("PROFILE");
            System.out.println("----------------");
            System.out.println("1. Display your profile details.");
            System.out.println("2. Edit your profile details.");
            System.out.println("3. Delete your account.");
            System.out.println("0. Back to Menu.");
            System.out.println("---------------------------------------");
            noMenu = scanInt("Please input your selection\t: ");
            
            switch(noMenu){
                case 1:
                    displayPatientAdmin(id);
                    back = 0;
                    break;
                case 2:
                    System.out.print("\n");
                    editPatientAdmin(id);
                    back = 0;
                    break;
                case 3:
                    dlt=removePatientAdmin(id);
                    if(dlt==1){
                        back=1;
                    }else{
                        back=0;
                    }
                    break;
                case 0:
                    back = 1;
                    break;
                default:
                    System.out.println(ANSI_RED + "\n\t\t\t\tPlease enter 0 - 3 only!" + ANSI_RESET);
                    break;
            }
            
        }while(back !=1);
        return dlt;
    }
    
    public void displayPatientAdmin(String id){
        
        for(int i=1; i<=p.sizeOfList();i++){
            if(p.getArray(i).getUserID().equals(id)){
        
                System.out.println("\n");
                System.out.println("----------------------------------------------------------------------------------------------------");
                System.out.println("\t\t\t\t    YOUR PROFILE DETAILS");
                System.out.println("----------------------------------------------------------------------------------------------------");
                System.out.print("\t\t\t\tUSER ID\t\t: ");
                System.out.println(p.getArray(i).getUserID());
                System.out.print("\t\t\t\tUSERNAME\t\t: ");
                System.out.println(p.getArray(i).getUsername());
                System.out.print("\t\t\t\tIC NO\t\t: ");
                System.out.println(p.getArray(i).getIcNo());
                System.out.print("\t\t\t\tAGE\t\t: ");
                System.out.println(p.getArray(i).getAge());
                System.out.print("\t\t\t\tWEIGHT\t\t: ");
                System.out.println(p.getArray(i).getWeight());
                System.out.print("\t\t\t\tADDRESS\t\t: ");
                System.out.println(p.getArray(i).getAddress());
            }
        }
    }
    
    public String editPatientAdmin(String id){
        
        int noEdit;
        String newUserId="", newUsername, newPassword, newAddress, newIc;
        int newAge;
        double newWeight;
        boolean newAdmin;
        int confirm=1, cont=2;
        boolean valid=true;//invalid=0, 
        
        for (int i = 1; i <= p.sizeOfList(); i++) {
            if (p.getArray(i).getUserID().equals(id)) {
                
                do{
                    System.out.println("\n----------------------------------------------------------------------------------------------------");
                    System.out.println("1. Edit your User ID");
                    System.out.println("2. Edit your Username");
                    System.out.println("3. Edit your password");
                    System.out.println("4. Edit your IC NO");
                    System.out.println("5. Edit your Age");
                    System.out.println("6. Edit your Weight");
                    System.out.println("7. Edit your Address");
                    System.out.println("0. Back to Profile");
                    System.out.println("---------------------------");
                    noEdit = scanInt("Please select\t: ");

                    switch (noEdit) {
                        case 1:
                            System.out.print("\nWhat is your new USER ID? >> ");
                            newUserId = s.next();
                            newUsername = p.getArray(i).getUsername();
                            newPassword = p.getArray(i).getPassword();
                            newAge = p.getArray(i).getAge();
                            newWeight = p.getArray(i).getWeight();
                            newAddress = p.getArray(i).getAddress();
                            newIc = p.getArray(i).getIcNo();
                            newAdmin = p.getArray(i).isIsAdmin();
                            p.replace(i, new Patient(newUserId, newUsername, newIc, newPassword, newAge, newWeight, newAddress, newAdmin));
                            System.out.println(ANSI_GREEN + "\n\t\t\t\tYour User Id have changed! " + ANSI_RESET);
                            System.out.println(ANSI_GREEN + "\t\tPlease go to display your profile details to see the new changes." + ANSI_RESET);
                            do {
                                cont = scanInt("\nDo you want to continue edit other details? (1. Yes / 2. No): ");
                                switch (cont) {
                                    case 1:
                                        System.out.print(ANSI_BLUE + "\n\n\t\t\tWhat is the next info you would like to change?" + ANSI_RESET);
                                        break;
                                    case 2:
                                        System.out.print(ANSI_BLUE + "\n\n\t\t\t    You are going back to Profile Page!" + ANSI_RESET);
                                        break;
                                    default:
                                        System.out.println(ANSI_RED + "\n\t\t\t\tPlease enter 1 or 2 only!" + ANSI_RESET);
                                        break;
                                }
                            } while (cont != 1 && cont != 2);
                            break;
                        case 2:
                            System.out.print("\nWhat is your new USERNAME? >> ");
                            newUsername = s.next();
                            newUserId = p.getArray(i).getUserID();
                            newPassword = p.getArray(i).getPassword();
                            newAge = p.getArray(i).getAge();
                            newWeight = p.getArray(i).getWeight();
                            newAddress = p.getArray(i).getAddress();
                            newIc = p.getArray(i).getIcNo();
                            newAdmin = p.getArray(i).isIsAdmin();
                            p.replace(i, new Patient(newUserId, newUsername, newIc, newPassword, newAge, newWeight, newAddress, newAdmin));
                            System.out.println(ANSI_GREEN + "\n\t\t\t\tYour Username have changed! " + ANSI_RESET);
                            System.out.println(ANSI_GREEN + "\t\tPlease go to display your profile details to see the new changes." + ANSI_RESET);
                            do {
                                cont = scanInt("\nDo you want to continue edit other details? (1. Yes / 2. No): ");
                                switch (cont) {
                                    case 1:
                                        System.out.print(ANSI_BLUE + "\n\n\t\t\tWhat is the next info you would like to change?" + ANSI_RESET);
                                        break;
                                    case 2:
                                        System.out.print(ANSI_BLUE + "\n\n\t\t\t    You are going back to Profile Page!" + ANSI_RESET);
                                        break;
                                    default:
                                        System.out.println(ANSI_RED + "\n\t\t\t\tPlease enter 1 or 2 only!" + ANSI_RESET);
                                        break;
                                }
                            } while (cont != 1 && cont != 2);
                            break;
                        case 3:
                            System.out.print("\nWhat is your new PASSWORD? >> ");
                            newPassword = s.next();
                            newUserId = p.getArray(i).getUserID();
                            newUsername = p.getArray(i).getUsername();
                            newAge = p.getArray(i).getAge();
                            newWeight = p.getArray(i).getWeight();
                            newAddress = p.getArray(i).getAddress();
                            newIc = p.getArray(i).getIcNo();
                            newAdmin = p.getArray(i).isIsAdmin();
                            p.replace(i, new Patient(newUserId, newUsername, newIc, newPassword, newAge, newWeight, newAddress, newAdmin));
                            System.out.println(ANSI_GREEN + "\n\t\t\t\tYour Password have changed!" + ANSI_RESET);
                            do {
                                cont = scanInt("\nDo you want to continue edit other details? (1. Yes / 2. No): ");
                                switch (cont) {
                                    case 1:
                                        System.out.print(ANSI_BLUE + "\n\n\t\t\tWhat is the next info you would like to change?" + ANSI_RESET);
                                        break;
                                    case 2:
                                        System.out.print(ANSI_BLUE + "\n\n\t\t\t    You are going back to Profile Page!" + ANSI_RESET);
                                        break;
                                    default:
                                        System.out.println(ANSI_RED + "\n\t\t\t\tPlease enter 1 or 2 only!" + ANSI_RESET);
                                        break;
                                }
                            } while (cont != 1 && cont != 2);
                            break;
                        case 4:
                            System.out.print("\nWhat is your new IC NO? >> ");
                            newIc = s.next();
                            newUserId = p.getArray(i).getUserID();
                            newUsername = p.getArray(i).getUsername();
                            newPassword = p.getArray(i).getPassword();
                            newAge = p.getArray(i).getAge();
                            newWeight = p.getArray(i).getWeight();
                            newAddress = p.getArray(i).getAddress();
                            newAdmin = p.getArray(i).isIsAdmin();
                            p.replace(i, new Patient(newUserId, newUsername, newIc, newPassword, newAge, newWeight, newAddress, newAdmin));
                            System.out.println(ANSI_GREEN + "\n\t\t\t\tYour IC NO have changed! " + ANSI_RESET);
                            System.out.println(ANSI_GREEN + "\t\tPlease go to display your profile details to see the new changes." + ANSI_RESET);
                            do {
                                cont = scanInt("\nDo you want to continue edit other details? (1. Yes / 2. No): ");
                                switch (cont) {
                                    case 1:
                                        System.out.print(ANSI_BLUE + "\n\n\t\t\tWhat is the next info you would like to change?" + ANSI_RESET);
                                        break;
                                    case 2:
                                        System.out.print(ANSI_BLUE + "\n\n\t\t\t    You are going back to Profile Page!" + ANSI_RESET);
                                        break;
                                    default:
                                        System.out.println(ANSI_RED + "\n\t\t\t\tPlease enter 1 or 2 only!" + ANSI_RESET);
                                        break;
                                }
                            } while (cont != 1 && cont != 2);
                            break;
                        case 5:
                            newAge = scanInt("\nWhat is your current AGE? >> ");
                            newUserId = p.getArray(i).getUserID();
                            newUsername = p.getArray(i).getUsername();
                            newPassword = p.getArray(i).getPassword();
                            newWeight = p.getArray(i).getWeight();
                            newAddress = p.getArray(i).getAddress();
                            newIc = p.getArray(i).getIcNo();
                            newAdmin = p.getArray(i).isIsAdmin();
                            p.replace(i, new Patient(newUserId, newUsername, newIc, newPassword, newAge, newWeight, newAddress, newAdmin));
                            System.out.println(ANSI_GREEN + "\n\t\t\t\tYour Age have changed! " + ANSI_RESET);
                            System.out.println(ANSI_GREEN + "\t\tPlease go to display your profile details to see the new changes." + ANSI_RESET);
                            do {
                                cont = scanInt("\nDo you want to continue edit other details? (1. Yes / 2. No): ");
                                switch (cont) {
                                    case 1:
                                        System.out.print(ANSI_BLUE + "\n\n\t\t\tWhat is the next info you would like to change?" + ANSI_RESET);
                                        break;
                                    case 2:
                                        System.out.print(ANSI_BLUE + "\n\n\t\t\t    You are going back to Profile Page!" + ANSI_RESET);
                                        break;
                                    default:
                                        System.out.println(ANSI_RED + "\n\t\t\t\tPlease enter 1 or 2 only!" + ANSI_RESET);
                                        break;
                                }
                            } while (cont != 1 && cont != 2);
                            break;
                        case 6:
                            try {
                            System.out.print("\nWhat is your current WEIGHT? >> ");
                            newWeight = s.nextDouble();
                            newUserId = p.getArray(i).getUserID();
                            newUsername = p.getArray(i).getUsername();
                            newPassword = p.getArray(i).getPassword();
                            newAge = p.getArray(i).getAge();
                            newAddress = p.getArray(i).getAddress();
                            newIc = p.getArray(i).getIcNo();
                            newAdmin = p.getArray(i).isIsAdmin();
                            p.replace(i, new Patient(newUserId, newUsername, newIc, newPassword, newAge, newWeight, newAddress, newAdmin));
                            System.out.println(ANSI_GREEN + "\n\t\t\t\tYour Weight have changed! " + ANSI_RESET);
                            System.out.println(ANSI_GREEN + "\t\tPlease go to display your profile details to see the new changes." + ANSI_RESET);
                        } catch (Exception e) {
                            System.out.println(ANSI_RED + "\n\t\t\t      Invalid input, exiting the process." + ANSI_RESET);
                        }
                            do {
                                cont = scanInt("\nDo you want to continue edit other details? (1. Yes / 2. No): ");
                                switch (cont) {
                                    case 1:
                                        System.out.print(ANSI_BLUE + "\n\n\t\t\tWhat is the next info you would like to change?" + ANSI_RESET);
                                        break;
                                    case 2:
                                        System.out.print(ANSI_BLUE + "\n\n\t\t\t    You are going back to Profile Page!" + ANSI_RESET);
                                        break;
                                    default:
                                        System.out.println(ANSI_RED + "\n\t\t\t\tPlease enter 1 or 2 only!" + ANSI_RESET);
                                        break;
                                }
                            } while (cont != 1 && cont != 2);
                            break;
                        case 7:
                            System.out.print("\nWhat is your current ADDRESS? >> ");
                            newAddress = s.next();
                            newUserId = p.getArray(i).getUserID();
                            newUsername = p.getArray(i).getUsername();
                            newPassword = p.getArray(i).getPassword();
                            newAge = p.getArray(i).getAge();
                            newWeight = p.getArray(i).getWeight();
                            newIc = p.getArray(i).getIcNo();
                            newAdmin = p.getArray(i).isIsAdmin();
                            p.replace(i, new Patient(newUserId, newUsername, newIc, newPassword, newAge, newWeight, newAddress, newAdmin));
                            System.out.println(ANSI_GREEN + "\n\t\t\t\tYour Address have changed! " + ANSI_RESET);
                            System.out.println(ANSI_GREEN + "\t\tPlease go to display your profile details to see the new changes." + ANSI_RESET);
                            do {
                                cont = scanInt("\nDo you want to continue edit other details? (1. Yes / 2. No): ");
                                switch (cont) {
                                    case 1:
                                        System.out.print(ANSI_BLUE + "\n\n\t\t\tWhat is the next info you would like to change?" + ANSI_RESET);
                                        break;
                                    case 2:
                                        System.out.print(ANSI_BLUE + "\n\n\t\t\t    You are going back to Profile Page!" + ANSI_RESET);
                                        break;
                                    default:
                                        System.out.println(ANSI_RED + "\n\t\t\t\tPlease enter 1 or 2 only!" + ANSI_RESET);
                                        break;
                                }
                            } while (cont != 1 && cont != 2);
                            break;
                        case 0:
                            do {
                                confirm = scanInt("Confirm exit? (1.Yes/2.No): ");
                                if (confirm != 1 && confirm != 2) {
                                    valid = false;
                                    System.out.println(ANSI_RED + "\n\t\t\t\tPlease enter 1 or 2 only!\n" + ANSI_RESET);
                                } else {
                                    valid = true;
                                    if(confirm==2)
                                        cont=1;
                                    else
                                        cont=2;
                                }
                            } while (valid == false);
                            
                            break;
                        default:
                            System.out.println(ANSI_RED + "\n\t\t\t\tPlease enter 0 - 7 only!" + ANSI_RESET);
                            cont=1;
                            break;
                    }
                }while(cont == 1);
            }
        }
        return newUserId;
    }
    
    public int removePatientAdmin(String id){
        
        int dlt=0;
        
        for(int i=1; i<=p.sizeOfList();i++){
            if (p.getArray(i).getUserID().equals(id)) {
                dlt = scanInt("\nAre you sure want to delete account? (1.Yes/2.No): ");
                switch (dlt) {
                    case 1:
                        p.remove(i);
                        break;
                    case 2:
                        System.out.println(ANSI_BLUE + "\n\t\t\t\tYour account is still exists!" + ANSI_RESET);
                        break;
                    default:
                        System.out.println(ANSI_RED + "\n\t\t\t\tYour input is not in range!" + ANSI_RESET);
                        break;
                }
            }
        }
        return dlt;
    }
    
    public static int scanInt(String question){
        
        boolean error = false;
        int option;
        
        do{
            System.out.printf(question);
            String input = s.next();
            int noDigit = 0;
            
            for(int i=0;i<input.length();i++){
                if(!Character.isDigit(input.charAt(i))){
                    noDigit+=1;
                    break;
                }
            }
            if(noDigit==0){
                option=Integer.parseInt(input);
                return option;
                
            }
            else{
                System.out.println(ANSI_RED+"\n\t\t\t\tYour input is Invalid."+ANSI_RESET);
                error = true;
            }
        }while(error);
        return -1;
    }
    
    public void initialise(){
        
         
        
        medicineLinkList.add(new Medicine("MED001","Paracetamol",15.00,"One or two 500mg tablets up to 4 times in 24 hours."));
        medicineLinkList.add(new Medicine("MED002","Delsym",20.00,"Take this medication by mouth, usually every 4 to 12 hours as needed"));
        medicineLinkList.add(new Medicine("MED003","Antihistamin",18.00,"50 to 100 milligrams (mg) every four to six hours as needed."));
        medicineLinkList.add(new Medicine("MED004","Imodium",22.00,"Take Imodium half an hour before a meal."));
        medicineLinkList.add(new Medicine("MED005","Ondansetron",30.00,"An hour before food, or 2 hours after meals."));
        medicineLinkList.add(new Medicine("MED006","Senokot",12.50,"Do not take this medication for more than 7 days"));
        medicineLinkList.add(new Medicine("MED007","Carbamazepine-Tablet", 23.00,"Take carbamazepine up to 4 times a day"));
        medicineLinkList.add(new Medicine("MED008","Lasix Furosemide",29.00,"It is best taken in the morning"));    
        
        
       
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
        
        p.add(new Patient("P001","Charles","010203140101","123abc",20,45,"Taman Kepong, KL",false));
        p.add(new Patient("A001","Macy","010203144444","111aaa",21,50,"Taman Menjalara, Selangor",true));
        p.add(new Patient("P003","Jamesa","010203145555","123abc",20,45,"Taman Coconut, KL",false));
        
        DeliveryClient deliveryClient = new DeliveryClient();
        
        Payment payment1 = new Payment("P00001","Credit Card");
        //Payment ====================================================
        Payment paymentLocal = new Payment();
        
        Payment p1 = new Payment("PM000001", "TnG", paymentLocal.setLocalDateTime());
        Payment p2 = new Payment("PM000002", "Credit/Debit Card", paymentLocal.setLocalDateTime());
        Payment p3 = new Payment("PM000003", "Online Banking", paymentLocal.setLocalDateTime());
        
        //CART =======================================================================================================
        
        cartArrS.push(new CartItem(1,combinationLinkList.get(3) , p.getArray(1) ,p1));
        cartArrS.push(new CartItem(4,combinationLinkList.get(2) , p.getArray(1) ,p1 ));
        cartArrS.push(new CartItem(3,combinationLinkList.get(1) , p.getArray(1) ,p3 ));
        cartArrS.push(new CartItem(5,combinationLinkList.get(4) , p.getArray(1) ,new Payment() ));
        cartArrS.push(new CartItem(4,combinationLinkList.get(2) , p.getArray(3) ,p2 ));
        
        //DELIVERY =====================================================================================================

        
        deliveryClient.addDelivery(deliveryQ , new Delivery("Received Order" , p1));
        deliveryClient.addDelivery(deliveryQ , new Delivery( "Received Order" , p3));
        deliveryClient.addDelivery(deliveryQ , new Delivery("Received Order" , p2));
       
        

        
        paymentArrStack.push(p1);
        paymentArrStack.push(p2);
        paymentArrStack.push(p3);
        
    }
    
}
