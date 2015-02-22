package bg.uni_sofia.fmi.oopjava.project.windows;

import bg.uni_sofia.fmi.oopjava.project.Application;
import bg.uni_sofia.fmi.oopjava.project.exceptions.AlreadyTakenException;
import bg.uni_sofia.fmi.oopjava.project.exceptions.InvalidCredentialsException;
import bg.uni_sofia.fmi.oopjava.project.exceptions.MessageHandler;
import bg.uni_sofia.fmi.oopjava.project.user.Permission;
import bg.uni_sofia.fmi.oopjava.project.user.User;
import bg.uni_sofia.fmi.oopjava.project.user.validation.UserValidation;
import java.awt.EventQueue;
import java.rmi.RemoteException;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author Dimitar Panayotov
 */
public class AddUserPanel extends javax.swing.JPanel implements UserValidation {

    private User user;

    public AddUserPanel() {
        initComponents();
    }

    public void setUser(User user) {
        this.user = user;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblUsername = new javax.swing.JLabel();
        lblPassword = new javax.swing.JLabel();
        lblPermission = new javax.swing.JLabel();
        txtUsername = new javax.swing.JTextField();
        txtPermission = new javax.swing.JTextField();
        txtPassword = new javax.swing.JPasswordField();
        btnAdd = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();

        setPreferredSize(new java.awt.Dimension(290, 171));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblUsername.setText("Username");
        add(lblUsername, new org.netbeans.lib.awtextra.AbsoluteConstraints(23, 26, -1, -1));

        lblPassword.setText("Password");
        add(lblPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(25, 64, -1, -1));

        lblPermission.setText("Permission");
        add(lblPermission, new org.netbeans.lib.awtextra.AbsoluteConstraints(25, 102, -1, -1));
        add(txtUsername, new org.netbeans.lib.awtextra.AbsoluteConstraints(108, 23, 160, -1));

        txtPermission.setText("Administrator / User / None");
        add(txtPermission, new org.netbeans.lib.awtextra.AbsoluteConstraints(108, 99, 160, -1));

        txtPassword.setText("password");
        add(txtPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(108, 61, 160, -1));

        btnAdd.setText("Add");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });
        add(btnAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 140, -1, -1));

        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });
        add(btnCancel, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 140, -1, -1));
    }// </editor-fold>//GEN-END:initComponents

    /**
     * If valid, creates user with the specified parameters.
     * @param evt 
     */
    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        String username = txtUsername.getText();
        String password = new String(txtPassword.getPassword());
        String perm = txtPermission.getText();
        try {
            if (!validatePermission(perm)) {
                throw new InvalidCredentialsException("Invalid permission level!");
            }
            Permission permission = Permission.valueOf(perm.substring(0, 1).toUpperCase() + perm.substring(1).toLowerCase());
            validateUser(username, password, permission);
            User toAdd = new User(username, password, permission);
            if (Application.getServer().hasEntry(username) != null) {
                throw new AlreadyTakenException();
            }
            Application.getServer().addUser(toAdd);
            MessageHandler.showMessage("User added successfully!");
        } catch (InvalidCredentialsException | RemoteException | AlreadyTakenException e) {
            MessageHandler.showError(e.getMessage());
        }
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        int choice = JOptionPane.showConfirmDialog(this, "Are you sure you want to logout?", "Logout", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (choice == JOptionPane.YES_OPTION) {
            SwingUtilities.getWindowAncestor(this).dispose();
            EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new LoginScreen();
                }
            });
        }
    }//GEN-LAST:event_btnCancelActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnCancel;
    private javax.swing.JLabel lblPassword;
    private javax.swing.JLabel lblPermission;
    private javax.swing.JLabel lblUsername;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtPermission;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables
}
