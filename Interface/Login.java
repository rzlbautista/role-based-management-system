/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Interface;
import javax.swing.JOptionPane;
import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Toolkit;
/**
 *
 * @author Rizelle
 */
public class Login extends javax.swing.JFrame {

    /**
     * Creates new form Login
     */
    
    int x, y;
    
    
    public Login() {
      
        
                
        initComponents();
        
       
        
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jpanelidk = new javax.swing.JPanel();
        jToggleButton2 = new javax.swing.JToggleButton();
        username = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        password = new javax.swing.JPasswordField();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jpanelidk.setBackground(new java.awt.Color(255, 255, 255));
        jpanelidk.setMaximumSize(new java.awt.Dimension(535, 570));

        jToggleButton2.setBackground(javax.swing.UIManager.getDefaults().getColor("Button.default.focusColor"));
        jToggleButton2.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jToggleButton2.setForeground(new java.awt.Color(0, 51, 204));
        jToggleButton2.setText("SIGN IN");
        jToggleButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jToggleButton2MouseClicked(evt);
            }
        });
        jToggleButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton2ActionPerformed(evt);
            }
        });

        username.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        username.setForeground(new java.awt.Color(153, 153, 153));
        username.setText("Username");
        username.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usernameActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 40)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 51, 204));
        jLabel3.setText("SIGN IN");

        password.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        password.setForeground(new java.awt.Color(153, 153, 153));
        password.setText("jPasswordField1");

        javax.swing.GroupLayout jpanelidkLayout = new javax.swing.GroupLayout(jpanelidk);
        jpanelidk.setLayout(jpanelidkLayout);
        jpanelidkLayout.setHorizontalGroup(
            jpanelidkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpanelidkLayout.createSequentialGroup()
                .addGroup(jpanelidkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpanelidkLayout.createSequentialGroup()
                        .addGap(58, 58, 58)
                        .addGroup(jpanelidkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(password, javax.swing.GroupLayout.PREFERRED_SIZE, 329, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(username, javax.swing.GroupLayout.PREFERRED_SIZE, 329, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jpanelidkLayout.createSequentialGroup()
                        .addGap(118, 118, 118)
                        .addComponent(jToggleButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jpanelidkLayout.createSequentialGroup()
                        .addGap(148, 148, 148)
                        .addComponent(jLabel3)))
                .addContainerGap(63, Short.MAX_VALUE))
        );
        jpanelidkLayout.setVerticalGroup(
            jpanelidkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpanelidkLayout.createSequentialGroup()
                .addGap(65, 65, 65)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44)
                .addComponent(username, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(55, 55, 55)
                .addComponent(password, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 124, Short.MAX_VALUE)
                .addComponent(jToggleButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(110, 110, 110))
        );

        getContentPane().add(jpanelidk, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 450, 570));

        jPanel1.setBackground(new java.awt.Color(0, 0, 102));
        jPanel1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jPanel1MouseDragged(evt);
            }
        });
        jPanel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jPanel1MousePressed(evt);
            }
        });

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGE/x-icon30x30.png"))); // NOI18N
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 1040, Short.MAX_VALUE)
                .addComponent(jLabel2))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGE/solarrr (1).png"))); // NOI18N
        jLabel8.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 30, 650, 570));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jToggleButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton2ActionPerformed
      // Signin button code
String un = username.getText();
String pass = new String(password.getText());

try (java.sql.Connection connection = db.mycon();
     java.sql.Statement statement = connection.createStatement();
     // Select both password and role from the employee_table
     java.sql.ResultSet rs = statement.executeQuery("SELECT password, role FROM employee_table WHERE username= '" + un + "'")) {

    if (rs.next()) {
        String storedPassword = rs.getString("password");
        String role = rs.getString("role");

        // Compare the stored password with the input password
        if (storedPassword.equals(pass)) {
            // Use a switch-case to handle role-based redirection
            switch (role.toLowerCase()) {
                case "admin":
                    NewHomeAdmin adminPage = new NewHomeAdmin();
                    adminPage.setVisible(true);
                    break;

                case "inventory":
                    NewInventoryAdmin inventoryPage = new NewInventoryAdmin();
                    inventoryPage.setVisible(true);
                    break;
                case "inventory head":
                    NewHeadInventory headinventoryPage = new NewHeadInventory();
                    headinventoryPage.setVisible(true);
                    break;

                case "sales":
                    NewHomeSales salesPage = new NewHomeSales();
                    salesPage.setVisible(true);
                    break;
                    
                case "it":
                    NewITAccount itPage = new NewITAccount();
                    itPage.setVisible(true);
                    break;

                default:
                    JOptionPane.showMessageDialog(rootPane, "Unknown role");
                    break;
            }
            this.dispose(); // Close the current window
        } else {
            // Incorrect Password
            JOptionPane.showMessageDialog(rootPane, "Password Incorrect");
        }

    } else {
        // Username doesn't exist
        JOptionPane.showMessageDialog(rootPane, "Username doesn't exist. Please try again.");
    }

} catch (Exception e) {
    // Handle exceptions here and display an error message
    JOptionPane.showMessageDialog(rootPane, "An error occurred: " + e.getMessage());
}

    }//GEN-LAST:event_jToggleButton2ActionPerformed

    private void usernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usernameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_usernameActionPerformed

    private void jPanel1MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseDragged
        // Login Mouse Dragged
        
        int xx = evt.getXOnScreen();
        int yy = evt.getYOnScreen();
        this.setLocation(xx-x,yy-y);
        
    }//GEN-LAST:event_jPanel1MouseDragged

    private void jPanel1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MousePressed
        // Login Mouse Pressed 
        x = evt.getX();
        y = evt.getY();
        
        
    }//GEN-LAST:event_jPanel1MousePressed

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
        // X button mouse clicked
        
        System.exit(0);
        
        
    }//GEN-LAST:event_jLabel2MouseClicked

    private void jToggleButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jToggleButton2MouseClicked
        // Sign In Mouse Clicked
     
    }//GEN-LAST:event_jToggleButton2MouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JToggleButton jToggleButton2;
    private javax.swing.JPanel jpanelidk;
    private javax.swing.JPasswordField password;
    private javax.swing.JTextField username;
    // End of variables declaration//GEN-END:variables

    
}
