package bg.uni_sofia.fmi.oopjava.project.windows;

import bg.uni_sofia.fmi.oopjava.project.Application;
import bg.uni_sofia.fmi.oopjava.project.exceptions.ExceptionHandler;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.Timer;

/**
 *
 * @author Dimitar Panayotov
 */
public class ServerWindow extends javax.swing.JFrame {

    public ServerWindow() {
        initComponents();
        setLocationRelativeTo(null);
        setVisible(true);
        run();
    }

    private void run() {
        Timer timer = new Timer(2000, (ActionEvent e) -> {
            try {
                if (Application.getServer().isAlive()) {
                    btnServerStatus.setText("Stop server");
                    lblStatus.setText("Server is running!");
                    lblStatus.setForeground(Color.GREEN);
                    lblStatus.setFont(new Font("Verdana", Font.BOLD, 11));
                } else {
                    btnServerStatus.setText("Start server");
                    lblStatus.setText("Server stopped!");
                    lblStatus.setForeground(Color.RED);
                    lblStatus.setFont(new Font("Verdana", Font.BOLD, 11));
                }
            } catch (RemoteException ex) {
                Logger.getLogger(ServerWindow.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        timer.setRepeats(true);
        timer.setCoalesce(true);
        timer.start();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lblStatus = new javax.swing.JLabel();
        btnServerStatus = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Server");
        setBackground(new java.awt.Color(255, 255, 255));
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        lblStatus.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblStatus.setForeground(new java.awt.Color(255, 153, 0));
        lblStatus.setText("Setting up server...");

        btnServerStatus.setText("Start server");
        btnServerStatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnServerStatusActionPerformed(evt);
            }
        });

        btnSave.setText("Save cards");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(btnServerStatus)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                .addComponent(btnSave)
                .addGap(32, 32, 32))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(72, 72, 72)
                .addComponent(lblStatus)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(34, Short.MAX_VALUE)
                .addComponent(lblStatus)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnServerStatus)
                    .addComponent(btnSave))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnServerStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnServerStatusActionPerformed
        try {
            if (evt.getActionCommand().equals("Stop server")) {
                Application.closeConnection();
            } else {
                Application.restartServer();
            }
        } catch (RemoteException | NotBoundException | MalformedURLException ex) {
            ExceptionHandler.show(ex);
        }
    }//GEN-LAST:event_btnServerStatusActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        try {
            String[] buttons = {"Sort by card numbers", "Sort by encrypted numbers", "Cancel"};
            int choice = JOptionPane.showOptionDialog(null, "Save cards to a file with the chosen sorting", "Save cards",
                    JOptionPane.WARNING_MESSAGE, JOptionPane.YES_NO_CANCEL_OPTION, null, buttons, buttons[2]);
            if(choice == JOptionPane.CANCEL_OPTION){
                return;
            }else{
                Application.getServer().storeCards(choice);
            }
        } catch (RemoteException ex) {
            ExceptionHandler.show(ex);
        }
    }//GEN-LAST:event_btnSaveActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnServerStatus;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblStatus;
    // End of variables declaration//GEN-END:variables
}
