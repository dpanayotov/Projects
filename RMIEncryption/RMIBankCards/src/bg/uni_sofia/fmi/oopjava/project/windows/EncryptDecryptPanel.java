package bg.uni_sofia.fmi.oopjava.project.windows;

import bg.uni_sofia.fmi.oopjava.project.Application;
import bg.uni_sofia.fmi.oopjava.project.CreditCard;
import bg.uni_sofia.fmi.oopjava.project.exceptions.ExceptionHandler;
import bg.uni_sofia.fmi.oopjava.project.exceptions.InvalidCardNumberException;
import bg.uni_sofia.fmi.oopjava.project.exceptions.MessageHandler;
import bg.uni_sofia.fmi.oopjava.project.exceptions.NoPermissionException;
import bg.uni_sofia.fmi.oopjava.project.user.Permission;
import bg.uni_sofia.fmi.oopjava.project.user.User;
import java.rmi.RemoteException;

/**
 *
 * @author Dimitar Panayotov
 */
public class EncryptDecryptPanel extends javax.swing.JPanel {

    private User user;

    public EncryptDecryptPanel() {
        initComponents();
    }

    public void setUser(User user) {
        this.user = user;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblPlaintext = new javax.swing.JLabel();
        lblEncrypted = new javax.swing.JLabel();
        txtPlaintext = new javax.swing.JTextField();
        txtEncrypted = new javax.swing.JTextField();
        btnEncrypt = new javax.swing.JButton();
        btnDecrypt = new javax.swing.JButton();

        lblPlaintext.setText("Card number");

        lblEncrypted.setText("Encrypted number");

        btnEncrypt.setText("Encrypt");
        btnEncrypt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEncryptActionPerformed(evt);
            }
        });

        btnDecrypt.setText("Decrypt");
        btnDecrypt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDecryptActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblEncrypted)
                    .addComponent(lblPlaintext))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtEncrypted, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPlaintext, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(btnEncrypt)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnDecrypt)
                .addGap(38, 38, 38))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPlaintext, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPlaintext))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEncrypted)
                    .addComponent(txtEncrypted, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEncrypt)
                    .addComponent(btnDecrypt))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnEncryptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEncryptActionPerformed
        if (user.getPermission() != Permission.None) {
            try {
                CreditCard card = new CreditCard(txtPlaintext.getText());
                card.validateCardNumber();
                int encryptions = Application.getServer().getEncryptionCount(card.getNumber());
                if(encryptions > 12){
                    MessageHandler.showError("The card was already encrypted more than 12 times!");
                }
                txtEncrypted.setText(Application.getServer().encrypt(this.user.getUID(), card.getNumber(), encryptions));
            } catch (RemoteException ex) {
                ExceptionHandler.show(ex);
            } catch (InvalidCardNumberException e){
                MessageHandler.showError(e.getMessage());
            }
        } else {
            ExceptionHandler.show(new NoPermissionException("You don't have permission to perform this action"));
        }
    }//GEN-LAST:event_btnEncryptActionPerformed

    private void btnDecryptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDecryptActionPerformed
        try {
            if (user.getPermission() == Permission.None) {
                ExceptionHandler.show(new NoPermissionException("You don't have permission to perform this action!"));
            }
            CreditCard card = new CreditCard(txtEncrypted.getText());
            txtPlaintext.setText(Application.getServer().decrypt(card.getNumber()));
        } catch (RemoteException ex) {
            ExceptionHandler.show(ex);
        }
    }//GEN-LAST:event_btnDecryptActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDecrypt;
    private javax.swing.JButton btnEncrypt;
    private javax.swing.JLabel lblEncrypted;
    private javax.swing.JLabel lblPlaintext;
    private javax.swing.JTextField txtEncrypted;
    private javax.swing.JTextField txtPlaintext;
    // End of variables declaration//GEN-END:variables

}
