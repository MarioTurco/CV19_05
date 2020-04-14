/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package passwordsaltencryptor;

import java.util.*;
/**
 *
 * @author gpepp
 */
public class PasswordSaltEncryptor {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        System.out.println("Inserisci la password: ");
        String password = sc.nextLine();
        
        String salt = PasswordUtils.getSalt(30);
        
        String securePassword = PasswordUtils.generateSecurePassword(password, salt);
        
        System.out.println("Password inserita: " + password);
        System.out.println("Valore salt: " + salt);
        System.out.println("Password sicura: " + securePassword);
        
        
    }
    
}

