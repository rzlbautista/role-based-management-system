/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Interface;

import java.util.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author Renniel
 */
public class NewITAccount extends javax.swing.JFrame {
    JpanelLoader jpload = new JpanelLoader();

    /**
     * Creates new form NewHomeAdmin
     */
    public NewITAccount() {
        initComponents();
        this.setExtendedState(MAXIMIZED_BOTH);
       setDataToCards();
    }

    
public void setDataToCards() {
    long l = System.currentTimeMillis();
    Date todaysDate = new Date();

    try {
        java.sql.Connection con = db.mycon();
        
        // Use TYPE_SCROLL_INSENSITIVE to allow moving to the last row
        String query = "SELECT * FROM employee_table";
        java.sql.PreparedStatement stmt = con.prepareStatement(
            query,
            java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE,
            java.sql.ResultSet.CONCUR_READ_ONLY
        );

        java.sql.ResultSet rs = stmt.executeQuery();
        
        // Move to the last row to count the total rows
        if (rs.last()) {
            totalemployees.setText(Integer.toString(rs.getRow()));
        } else {
            totalemployees.setText("0"); // If no rows exist
        }
        
        String archiveQuery = "SELECT COUNT(*) AS total FROM employee_archive";
        java.sql.PreparedStatement completedStmt = con.prepareStatement(archiveQuery);
        java.sql.ResultSet archiveRs = completedStmt.executeQuery();
        if (archiveRs.next()) {
            archive.setText(archiveRs.getString("total"));
        } else {
            archive.setText("0");
        }
        
        
        
        
    } catch (Exception e) {
        e.printStackTrace();
    }
}
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jToggleButton1 = new javax.swing.JToggleButton();
        jToggleButton2 = new javax.swing.JToggleButton();
        jToggleButton5 = new javax.swing.JToggleButton();
        jLabel3 = new javax.swing.JLabel();
        jToggleButton4 = new javax.swing.JToggleButton();
        itaccountpanel_load = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        itpanel = new javax.swing.JPanel();
        panelemp = new javax.swing.JPanel();
        totalemployees = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        panelemp1 = new javax.swing.JPanel();
        archive = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(0, 51, 102));

        jToggleButton1.setBackground(new java.awt.Color(222, 238, 255));
        jToggleButton1.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jToggleButton1.setForeground(new java.awt.Color(0, 51, 102));
        jToggleButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGE/NEW ICONS/Accounts.png"))); // NOI18N
        jToggleButton1.setText("Accounts");
        jToggleButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jToggleButton1.setIconTextGap(20);
        jToggleButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton1ActionPerformed(evt);
            }
        });

        jToggleButton2.setBackground(new java.awt.Color(222, 238, 255));
        jToggleButton2.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jToggleButton2.setForeground(new java.awt.Color(0, 51, 102));
        jToggleButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGE/NEW ICONS/Archives.png"))); // NOI18N
        jToggleButton2.setText("Archives");
        jToggleButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jToggleButton2.setIconTextGap(20);
        jToggleButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton2ActionPerformed(evt);
            }
        });

        jToggleButton5.setBackground(new java.awt.Color(222, 238, 255));
        jToggleButton5.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jToggleButton5.setForeground(new java.awt.Color(0, 51, 102));
        jToggleButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGE/NEW ICONS/Dashboard.png"))); // NOI18N
        jToggleButton5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jToggleButton5.setIconTextGap(20);
        jToggleButton5.setLabel("Dashboard");
        jToggleButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton5ActionPerformed(evt);
            }
        });

        jLabel3.setBackground(new java.awt.Color(0, 153, 51));
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGE/NEW ICONS/FRAPS Solar Works Logo New.png"))); // NOI18N

        jToggleButton4.setBackground(new java.awt.Color(0, 51, 102));
        jToggleButton4.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jToggleButton4.setForeground(new java.awt.Color(0, 51, 102));
        jToggleButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGE/NEW ICONS/Log Out.png"))); // NOI18N
        jToggleButton4.setBorder(null);
        jToggleButton4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jToggleButton4.setIconTextGap(20);
        jToggleButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jToggleButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jToggleButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jToggleButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(102, 102, 102)
                        .addComponent(jToggleButton4)))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(jToggleButton5)
                .addGap(39, 39, 39)
                .addComponent(jToggleButton1)
                .addGap(36, 36, 36)
                .addComponent(jToggleButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 186, Short.MAX_VALUE)
                .addComponent(jToggleButton4)
                .addGap(156, 156, 156))
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 250, 800));

        itaccountpanel_load.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBackground(new java.awt.Color(238, 238, 253));

        jLabel1.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 51, 102));
        jLabel1.setText("IT Dashboard");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel1)
                .addContainerGap(1153, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(28, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(29, 29, 29))
        );

        itaccountpanel_load.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1290, 80));

        itpanel.setBackground(new java.awt.Color(255, 255, 255));

        panelemp.setBackground(new java.awt.Color(239, 239, 255));
        panelemp.setBorder(javax.swing.BorderFactory.createMatteBorder(15, 0, 0, 0, new java.awt.Color(102, 0, 102)));

        totalemployees.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        totalemployees.setForeground(new java.awt.Color(102, 102, 102));
        totalemployees.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGE/employee.png"))); // NOI18N
        totalemployees.setText(" 0");

        javax.swing.GroupLayout panelempLayout = new javax.swing.GroupLayout(panelemp);
        panelemp.setLayout(panelempLayout);
        panelempLayout.setHorizontalGroup(
            panelempLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelempLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(totalemployees, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(34, Short.MAX_VALUE))
        );
        panelempLayout.setVerticalGroup(
            panelempLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelempLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(totalemployees, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(72, Short.MAX_VALUE))
        );

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 102));
        jLabel2.setText("No. of Employees");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 102));
        jLabel4.setText("Archived Employees");

        panelemp1.setBackground(new java.awt.Color(239, 239, 255));
        panelemp1.setBorder(javax.swing.BorderFactory.createMatteBorder(15, 0, 0, 0, new java.awt.Color(102, 0, 102)));

        archive.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        archive.setForeground(new java.awt.Color(102, 102, 102));
        archive.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGE/employee.png"))); // NOI18N
        archive.setText(" 0");

        javax.swing.GroupLayout panelemp1Layout = new javax.swing.GroupLayout(panelemp1);
        panelemp1.setLayout(panelemp1Layout);
        panelemp1Layout.setHorizontalGroup(
            panelemp1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelemp1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(archive, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(34, Short.MAX_VALUE))
        );
        panelemp1Layout.setVerticalGroup(
            panelemp1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelemp1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(archive, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(72, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout itpanelLayout = new javax.swing.GroupLayout(itpanel);
        itpanel.setLayout(itpanelLayout);
        itpanelLayout.setHorizontalGroup(
            itpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(itpanelLayout.createSequentialGroup()
                .addGap(58, 58, 58)
                .addGroup(itpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelemp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(100, 100, 100)
                .addGroup(itpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelemp1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(702, Short.MAX_VALUE))
        );
        itpanelLayout.setVerticalGroup(
            itpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(itpanelLayout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addGroup(itpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(itpanelLayout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(panelemp1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(itpanelLayout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(panelemp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(478, Short.MAX_VALUE))
        );

        itaccountpanel_load.add(itpanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 81, 1290, 720));

        jPanel1.add(itaccountpanel_load, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 0, 1290, 800));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jToggleButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton1ActionPerformed
        itaccountpanel itaccpanel = new itaccountpanel();
        jpload.jPanelLoader(itaccountpanel_load, itaccpanel);// TODO add your handling code here:
    }//GEN-LAST:event_jToggleButton1ActionPerformed

    private void jToggleButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton4ActionPerformed
        // TODO add your handling code here:
        int a = JOptionPane.showConfirmDialog(null, "Do you want to logout?", "Select", JOptionPane.YES_NO_OPTION);
        if (a ==0){
        setVisible(false);
        new Login().setVisible(true);
        }
    }//GEN-LAST:event_jToggleButton4ActionPerformed

    private void jToggleButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton5ActionPerformed
    ITDashboard db = new ITDashboard();
    jpload.jPanelLoader(itaccountpanel_load, db);
    // TODO add your handling code here:
    }//GEN-LAST:event_jToggleButton5ActionPerformed

    private void jToggleButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton2ActionPerformed
        // TODO add your handling code here:
        archivepanel arch = new archivepanel();
        jpload.jPanelLoader(itaccountpanel_load, arch);
    }//GEN-LAST:event_jToggleButton2ActionPerformed

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
            java.util.logging.Logger.getLogger(NewITAccount.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NewITAccount.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NewITAccount.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NewITAccount.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NewITAccount().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel archive;
    private javax.swing.JPanel itaccountpanel_load;
    private javax.swing.JPanel itpanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JToggleButton jToggleButton2;
    private javax.swing.JToggleButton jToggleButton4;
    private javax.swing.JToggleButton jToggleButton5;
    private javax.swing.JPanel panelemp;
    private javax.swing.JPanel panelemp1;
    private javax.swing.JLabel totalemployees;
    // End of variables declaration//GEN-END:variables
}