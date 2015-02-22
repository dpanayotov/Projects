package bg.uni_sofia.fmi.oopjava.project.windows;

import bg.uni_sofia.fmi.oopjava.project.Application;
import bg.uni_sofia.fmi.oopjava.project.exceptions.AlreadyTakenException;
import bg.uni_sofia.fmi.oopjava.project.exceptions.ExceptionHandler;
import bg.uni_sofia.fmi.oopjava.project.exceptions.InvalidCredentialsException;
import bg.uni_sofia.fmi.oopjava.project.exceptions.MessageHandler;
import bg.uni_sofia.fmi.oopjava.project.user.User;
import bg.uni_sofia.fmi.oopjava.project.user.validation.UserValidation;
import java.io.IOException;
import java.rmi.NoSuchObjectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 *
 * @author Dimitar Panayotov
 */
public class LoginScreen extends javax.swing.JFrame implements UserValidation {
    
    public LoginScreen() {
        initComponents();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblUsername = new javax.swing.JLabel();
        lblPwd = new javax.swing.JLabel();
        btnLogin = new javax.swing.JButton();
        btnRegister = new javax.swing.JButton();
        txtUsername = new javax.swing.JTextField();
        txtPwd = new javax.swing.JPasswordField();
        lblInfo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Login");
        setResizable(false);

        lblUsername.setText("Username");

        lblPwd.setText("Password");

        btnLogin.setText("Login");
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });

        btnRegister.setText("Register");
        btnRegister.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegisterActionPerformed(evt);
            }
        });

        txtPwd.setText("password");

        lblInfo.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblInfo.setText("Enter your username and password to proceed.");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblUsername)
                                    .addComponent(lblPwd))
                                .addGap(31, 31, 31)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtUsername)
                                    .addComponent(txtPwd, javax.swing.GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(43, 43, 43)
                                .addComponent(btnLogin)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 82, Short.MAX_VALUE)
                                .addComponent(btnRegister)
                                .addGap(14, 14, 14)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 25, Short.MAX_VALUE)
                        .addComponent(lblInfo)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(lblInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblUsername)
                    .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPwd)
                    .addComponent(txtPwd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLogin)
                    .addComponent(btnRegister))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginActionPerformed
        try {
            User fromDB = Application.getServer().hasEntry(txtUsername.getText());

            if (fromDB == null) {
                throw new InvalidCredentialsException("Invalid username or password!");
            }

            User user = new User(fromDB);

            if (!user.getPassword().equals(new String(txtPwd.getPassword()))) {
                throw new InvalidCredentialsException("Invalid username or password!");
            }
            this.setVisible(false);
            new UserScreenSelector(user);
        } catch (IOException ex) {
            ExceptionHandler.serverError();
        } catch (InvalidCredentialsException e) {
            MessageHandler.showError(e.getMessage());
        }
    }//GEN-LAST:event_btnLoginActionPerformed

    private User createUser() throws InvalidCredentialsException, NoSuchObjectException, RemoteException, NotBoundException {
        String username = txtUsername.getText();
        String password = new String(txtPwd.getPassword());
        System.out.println(username + "  " + password);
        if (!validateUser(username, password)) {
            throw new InvalidCredentialsException("Invalid username or password!");
        }
        return new User(username, password);
    }

    private void btnRegisterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegisterActionPerformed
        User user = null;
        try {
            user = createUser();
            //check if we have a match in the file
            if(Application.getServer().hasEntry(user.getUsername()) != null){
                throw new AlreadyTakenException();
            }
            Application.getServer().addUser(user);
        } catch (IOException | NotBoundException ex) {
            ExceptionHandler.show(ex);
        } catch (InvalidCredentialsException e) {
            MessageHandler.showError("Username and password don't meet the requirements!");
        } catch (AlreadyTakenException ex) {
            MessageHandler.showError(ex.getMessage());
        }

        if (user != null) {
            this.setVisible(false);
            new UserScreenSelector(user);
        }
    }//GEN-LAST:event_btnRegisterActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLogin;
    private javax.swing.JButton btnRegister;
    private javax.swing.JLabel lblInfo;
    private javax.swing.JLabel lblPwd;
    private javax.swing.JLabel lblUsername;
    private javax.swing.JPasswordField txtPwd;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables
}
