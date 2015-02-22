/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bg.uni_sofia.fmi.oopjava.project.exceptions;

import java.awt.EventQueue;
import javax.swing.JOptionPane;

/**
 *
 * @author Dimitar Panayotov
 */
public class MessageHandler {
 
    public static void showError(String message){
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
    
    public static void showMessage(String message){
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                JOptionPane.showMessageDialog(null, message, "Information", JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }
}
