package bg.uni_sofia.fmi.oopjava.project.windows;

import bg.uni_sofia.fmi.oopjava.project.user.User;
import javax.swing.JFrame;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Dimitar Panayotov
 */
public class AdminScreen extends javax.swing.JFrame {

    public AdminScreen(User user) {
        initComponents();
        setLocationRelativeTo(null);
        addUserPanel.setUser(user);
        removeUserPanel.init(user);
        encryptDecryptPanel.setUser(user);
        tabbedAminPanel.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                removeUserPanel.loadUsers();
            }
        });
        setVisible(true);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tabbedAminPanel = new javax.swing.JTabbedPane();
        addUserPanel = new bg.uni_sofia.fmi.oopjava.project.windows.AddUserPanel();
        removeUserPanel = new bg.uni_sofia.fmi.oopjava.project.windows.RemoveUserPanel();
        encryptDecryptPanel = new bg.uni_sofia.fmi.oopjava.project.windows.EncryptDecryptPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Admin panel");
        setResizable(false);

        tabbedAminPanel.addTab("Add User", addUserPanel);
        tabbedAminPanel.addTab("Remove User", removeUserPanel);
        tabbedAminPanel.addTab("Encrypt Decrypt", encryptDecryptPanel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tabbedAminPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tabbedAminPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private bg.uni_sofia.fmi.oopjava.project.windows.AddUserPanel addUserPanel;
    private bg.uni_sofia.fmi.oopjava.project.windows.EncryptDecryptPanel encryptDecryptPanel;
    private bg.uni_sofia.fmi.oopjava.project.windows.RemoveUserPanel removeUserPanel;
    private javax.swing.JTabbedPane tabbedAminPanel;
    // End of variables declaration//GEN-END:variables
}
