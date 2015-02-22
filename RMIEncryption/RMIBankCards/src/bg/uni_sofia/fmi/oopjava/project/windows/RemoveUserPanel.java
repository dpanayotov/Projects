package bg.uni_sofia.fmi.oopjava.project.windows;

import bg.uni_sofia.fmi.oopjava.project.Application;
import bg.uni_sofia.fmi.oopjava.project.exceptions.ExceptionHandler;
import bg.uni_sofia.fmi.oopjava.project.exceptions.InvalidCredentialsException;
import bg.uni_sofia.fmi.oopjava.project.exceptions.MessageHandler;
import bg.uni_sofia.fmi.oopjava.project.user.User;
import bg.uni_sofia.fmi.oopjava.project.user.Users;
import java.awt.EventQueue;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Dimitar Panayotov
 */
public class RemoveUserPanel extends javax.swing.JPanel {

    private User user;
    private Users users;
    private DefaultTableModel tableModel;
    
    public RemoveUserPanel() {
        initComponents();
    }

    void init(User user) {
        loadUsers();
        setUser(user);
    }

    private void setUser(User user) {
        this.user = user;
    }

    void loadUsers() {
        try {
            tableModel = new DefaultTableModel(new String[]{"Username", "Password", "Permission"}, 0);
            this.users = Application.getServer().getAllUsers();
            users.getAll().stream().map((u) -> {
                String username = u.getUsername();
                String password = u.getPassword();
                String permission = u.getPermission().toString();
                Object[] data = {username, password, permission};
                return data;
            }).forEach((data) -> {
                tableModel.addRow(data);
            });
            userTable.setModel(tableModel);
        } catch (RemoteException ex) {
            ExceptionHandler.show(ex);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnRemove = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        userTable = new javax.swing.JTable();

        setPreferredSize(new java.awt.Dimension(297, 193));

        btnRemove.setText("Remove");
        btnRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveActionPerformed(evt);
            }
        });

        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        userTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Username", "Password", "Permission"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        userTable.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                userTableFocusGained(evt);
            }
        });
        jScrollPane2.setViewportView(userTable);
        if (userTable.getColumnModel().getColumnCount() > 0) {
            userTable.getColumnModel().getColumn(0).setResizable(false);
            userTable.getColumnModel().getColumn(1).setResizable(false);
            userTable.getColumnModel().getColumn(2).setResizable(false);
        }

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addComponent(btnRemove)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 62, Short.MAX_VALUE)
                .addComponent(btnCancel)
                .addGap(50, 50, 50))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnRemove)
                    .addComponent(btnCancel))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveActionPerformed
        try {
            User selectedUser = getSelectedUser();
            if (selectedUser != null) {
                if (selectedUser.compareTo(this.user) == 0) {
                    MessageHandler.showError("You cannot remove yourself!");
                    return;
                }
                btnRemove.setEnabled(true);
                try {
                    users.remove(selectedUser);
                    Application.getServer().saveUsers(users);
                    loadUsers();
                    MessageHandler.showMessage("User removed successfully");
                } catch (RemoteException ex) {
                    Logger.getLogger(RemoveUserPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (InvalidCredentialsException e) {
            MessageHandler.showError(e.getMessage());
        }
    }//GEN-LAST:event_btnRemoveActionPerformed

    private void userTableFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_userTableFocusGained
        tableModel.fireTableDataChanged();
    }//GEN-LAST:event_userTableFocusGained

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

    private User getSelectedUser() throws InvalidCredentialsException {
        int row = userTable.getSelectedRow();
        String username = (String) userTable.getModel().getValueAt(row, 0);
        return users.getByUsername(username);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnRemove;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable userTable;
    // End of variables declaration//GEN-END:variables
}
