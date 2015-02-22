package bg.uni_sofia.fmi.oopjava.project.windows;

import bg.uni_sofia.fmi.oopjava.project.user.User;

/**
 *
 * @author Dimitar Panayotov
 */
public class UserScreen extends javax.swing.JFrame {

    public UserScreen(User user) {
        initComponents();
        setLocationRelativeTo(null);
        encryptDecryptPanel.setUser(user);
        setVisible(true);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        encryptDecryptPanel = new bg.uni_sofia.fmi.oopjava.project.windows.EncryptDecryptPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("User panel");
        setResizable(false);
        getContentPane().add(encryptDecryptPanel, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private bg.uni_sofia.fmi.oopjava.project.windows.EncryptDecryptPanel encryptDecryptPanel;
    // End of variables declaration//GEN-END:variables
}
