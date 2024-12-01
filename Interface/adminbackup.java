/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Interface;
import java.awt.Component;

import javax.swing.table.DefaultTableModel;
import java.util.Calendar;
    
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.*;
import javax.swing.table.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//import java.util.Date;  // For working with date from JDateChooser
//import java.sql.Date;   // For working with SQL Date (for database operations)






/**
 *
 * @author Renniel
 */
public class adminbackup extends javax.swing.JPanel {
    JpanelLoader jpload = new JpanelLoader();

    /**
     * 
     * Creates new form InventoryAccountPanel
     */
    
    // Main frame or main panel where both panels are created

    private archivepanel archivePanelInstance;

    // Constructor to accept archivePanel instance
    public adminbackup(archivepanel archivePanelInstance) {
        this.archivePanelInstance = archivePanelInstance;
        initComponents();
        tb_load();
    }

    public adminbackup() {
        initComponents();
        tb_load();
        jComboBox1.setSelectedIndex(0);
        
        // Set up the table model
       
    }

  


public class PasswordUtils {
    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}

  private void tb_load() {
    DefaultTableModel model = (DefaultTableModel) backup_table.getModel();
    model.setRowCount(0);  // Clear existing rows

    try {
        java.sql.Connection con = db.mycon();
        String query = "SELECT First_Name, Last_Name, Username, Role, Password, Status, archived_date FROM employee_backup";
        java.sql.PreparedStatement stmt = con.prepareStatement(query);
        java.sql.ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            // Get employee data
            String fname = rs.getString("First_Name");
            String lname = rs.getString("Last_Name");
            String username = rs.getString("Username");
            String role = rs.getString("role");
            String password = rs.getString("Password");
            String status = rs.getString("status");
            String createdAt = rs.getString("archived_date");

            // Add checkbox column (unchecked by default)
            model.addRow(new Object[]{false, fname, lname,username,role, password,  status,  createdAt});
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error loading data: " + e.getMessage());
    }

    // Set the checkbox column to use a checkbox editor and renderer
    backup_table.getColumnModel().getColumn(0).setCellEditor(new DefaultCellEditor(new JCheckBox()));  // For editing
    backup_table.getColumnModel().getColumn(0).setCellRenderer(new CheckBoxRenderer());  // For rendering
}

  
  

private void deleteSelectedRows() {
    DefaultTableModel model = (DefaultTableModel) backup_table.getModel();
    int rowCount = model.getRowCount();
    
    boolean atLeastOneChecked = false;

    for (int i = 0; i < rowCount; i++) {
        boolean isChecked = (boolean) model.getValueAt(i, 0);  // Checkbox in the first column
        if (isChecked) {
            atLeastOneChecked = true;
            String username = model.getValueAt(i, 3).toString();  // Assuming Username is in column 3
            
            // Confirmation prompt before deletion
            int confirm = JOptionPane.showConfirmDialog(null,
                    "Are you sure you want to permanently delete from the database this employee: " + username + "?",
                    "Confirm Deletion",
                    JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                try (java.sql.Connection connection = db.mycon()) {
                
                    // Now delete the employee from the main table
                    String deleteQuery = "DELETE FROM employee_backup WHERE username= ?";
                    try (java.sql.PreparedStatement deleteStmt = connection.prepareStatement(deleteQuery)) {
                        deleteStmt.setString(1, username);  // Set the employee ID for deletion
                        deleteStmt.executeUpdate();
                        JOptionPane.showMessageDialog(null, "Employee Deleted successfully.");
                    }
                } catch (java.sql.SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error occurred while archiving data: " + ex.getMessage());
                }
            }
        }
    }

    if (!atLeastOneChecked) {
        JOptionPane.showMessageDialog(null, "Please select at least one employee to delete.");
    }

    tb_load();  // Reload the main table to reflect deletion
    if (archivePanelInstance != null) {
        archivePanelInstance.loadArchivedData();
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

        jPanel3 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        employee = new javax.swing.JToggleButton();
        inventory = new javax.swing.JToggleButton();
        inventory1 = new javax.swing.JToggleButton();
        it_panel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        backup_table = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jDateChooser2 = new com.toedter.calendar.JDateChooser();
        jButton4 = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(1290, 800));

        jPanel3.setPreferredSize(new java.awt.Dimension(1290, 800));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(238, 238, 252));

        employee.setText("Employee");
        employee.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                employeeActionPerformed(evt);
            }
        });

        inventory.setText("Inventory");
        inventory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inventoryActionPerformed(evt);
            }
        });

        inventory1.setText("Transaction");
        inventory1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inventory1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(employee, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(inventory, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(inventory1, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(922, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(30, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(employee, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(inventory, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(inventory1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15))
        );

        jPanel3.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1290, 80));

        it_panel.setBackground(new java.awt.Color(255, 255, 255));

        backup_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "CheckBox", "First Name", "Last Name", "Username", "Password", "Role", "Last_Updated"
            }
        ));
        backup_table.setGridColor(new java.awt.Color(204, 255, 255));
        backup_table.setSelectionBackground(new java.awt.Color(204, 204, 204));
        jScrollPane1.setViewportView(backup_table);

        jLabel6.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 51, 102));
        jLabel6.setText("SEARCH");

        jTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField3ActionPerformed(evt);
            }
        });

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "First_Name", "Last_Name", "role", "Username", "Password", "role", "status", "logged_in", "logged_out" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jLabel8.setText("FROM");

        jDateChooser1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jDateChooser1PropertyChange(evt);
            }
        });

        jDateChooser2.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jDateChooser2PropertyChange(evt);
            }
        });

        jButton4.setBackground(new java.awt.Color(238, 238, 248));
        jButton4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton4.setForeground(new java.awt.Color(0, 51, 102));
        jButton4.setText("DELETE");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel9.setText("TO");

        javax.swing.GroupLayout it_panelLayout = new javax.swing.GroupLayout(it_panel);
        it_panel.setLayout(it_panelLayout);
        it_panelLayout.setHorizontalGroup(
            it_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(it_panelLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(93, 93, 93)
                .addComponent(jButton4)
                .addGap(217, 217, 217))
            .addGroup(it_panelLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1240, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(25, Short.MAX_VALUE))
        );
        it_panelLayout.setVerticalGroup(
            it_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(it_panelLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(it_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(it_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(it_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9))
                        .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(it_panelLayout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(jButton4)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 638, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jPanel3.add(it_panel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 80, 1290, 720));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void employeeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_employeeActionPerformed
        ManageAccountPanel iapanel = new ManageAccountPanel()    ;
        jpload.jPanelLoader(it_panel, iapanel);// TODO add your handling code here:
    }//GEN-LAST:event_employeeActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
    deleteSelectedRows();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void inventoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inventoryActionPerformed
        archivepanel arch = new archivepanel();
        jpload.jPanelLoader(it_panel, arch);
        // TODO add your handling code here:
    }//GEN-LAST:event_inventoryActionPerformed

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed
    // Get the search term entered by the user
    String searchQuery = jTextField3.getText().trim();
    
    // Get the selected column from the JComboBox
    String searchBy = jComboBox1.getSelectedItem().toString();

    // Get the from and to dates from the date choosers
    java.sql.Date fromDate = null;
    java.sql.Date toDate = null;

    // Check if dates are selected
    if (jDateChooser1.getDate() != null) {
        fromDate = new java.sql.Date(jDateChooser1.getDate().getTime());
    }
    if (jDateChooser2.getDate() != null) {
        toDate = new java.sql.Date(jDateChooser2.getDate().getTime());
        
        // Adjust toDate to the end of the day (23:59:59.999) if it's set
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(toDate.getTime());
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        toDate = new java.sql.Date(calendar.getTimeInMillis());
    }

    // Check if the search term is empty and show a message if so
    if (searchQuery.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Enter a search term!");
        return;
    }

    // Construct the base query
    String query = "SELECT * FROM employee_table WHERE " + searchBy + " LIKE ?";

    // Add date filtering to the query if dates are provided
    if (fromDate != null && toDate != null) {
        query += " AND Created_At BETWEEN ? AND ?";
    } else if (fromDate != null) {
        query += " AND Created_At >= ?";
    } else if (toDate != null) {
        query += " AND Created_At <= ?";
    }

    // Print the query and parameters for debugging
    System.out.println("SQL Query: " + query);
    System.out.println("Search Query: " + "%" + searchQuery + "%");
    System.out.println("From Date: " + fromDate);
    System.out.println("To Date: " + toDate);

    try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/inventory", "root", "");
         PreparedStatement stmt = conn.prepareStatement(query)) {

        // Set the search query parameter
        stmt.setString(1, "%" + searchQuery + "%");

        // Set the date parameters if applicable
        int parameterIndex = 2;
        if (fromDate != null) {
            stmt.setDate(parameterIndex++, fromDate);
        }
        if (toDate != null) {
            stmt.setDate(parameterIndex, toDate);
        }

        // Execute the query and get the results
        ResultSet rs = stmt.executeQuery();
        DefaultTableModel model = (DefaultTableModel) backup_table.getModel();
        model.setRowCount(0); // Clear the existing data

        // Loop through the result set and add the rows to the table model
        int rowCount = 0;
        while (rs.next()) {
            rowCount++;
            model.addRow(new Object[]{
                false,  // Checkbox (if applicable)
                rs.getString("First_Name"),
                rs.getString("Last_Name"),
                rs.getString("Username"),
                rs.getString("Password"),
                rs.getString("role"),
                rs.getString("status"),
                rs.getString("logged_in"),
                rs.getString("logged_out"),
                rs.getTimestamp("Created_At")
            });
        }

        // Print the number of rows returned
        System.out.println("Rows returned: " + rowCount);

        // If no rows are returned, display a message
        if (rowCount == 0) {
            JOptionPane.showMessageDialog(this, "No results found!");
        }

    } catch (SQLException ex) {
// Print stack trace and display error message if an exception occurs
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
    }
    }//GEN-LAST:event_jTextField3ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jDateChooser1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jDateChooser1PropertyChange
    // Check if the 'date' property was changed
    if ("date".equals(evt.getPropertyName())) {
        // Get the selected date from jDateChooser1
        java.util.Date selectedDate = (java.util.Date) evt.getNewValue();  // java.util.Date
        if (selectedDate != null) {
            // Convert to SQL Date
            java.sql.Date fromDate = new java.sql.Date(selectedDate.getTime());
            // Handle the from date (you can store it, print it, or perform actions)
            System.out.println("From Date: " + fromDate);
        }
    }
    }//GEN-LAST:event_jDateChooser1PropertyChange

    private void jDateChooser2PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jDateChooser2PropertyChange
    // Check if the 'date' property was changed
    if ("date".equals(evt.getPropertyName())) {
        // Get the selected date from jDateChooser2
        java.util.Date selectedDate = (java.util.Date) evt.getNewValue();  // java.util.Date
        if (selectedDate != null) {
            // Convert to SQL Date
            java.sql.Date toDate = new java.sql.Date(selectedDate.getTime());
            // Handle the to date (you can store it, print it, or perform actions)
            System.out.println("To Date: " + toDate);
        }
    }
    }//GEN-LAST:event_jDateChooser2PropertyChange

    private void inventory1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inventory1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_inventory1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable backup_table;
    private javax.swing.JToggleButton employee;
    private javax.swing.JToggleButton inventory;
    private javax.swing.JToggleButton inventory1;
    private javax.swing.JPanel it_panel;
    private javax.swing.JButton jButton4;
    private javax.swing.JComboBox<String> jComboBox1;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private com.toedter.calendar.JDateChooser jDateChooser2;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField3;
    // End of variables declaration//GEN-END:variables
}
