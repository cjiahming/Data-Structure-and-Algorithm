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
import adt.ArrayStack;
import adt.DynamicQueue;
import java.util.Scanner;

/**
 *
 * @author Gab
 */
public class testing {
    public static void main(String args[]){
        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        ArrayStack<Char> stack = new ArrayStack<Char>();
        
        System.out.println("Enter postfix:");

        String postfix = myObj.nextLine(); 
        
        for(int i = 0 ; i < postfix.length();i++){
            System.out.println(postfix.charAt(i));
            
            
        }
    }
}
