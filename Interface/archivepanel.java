/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Interface;

import java.util.Vector;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 *
 * @author Renniel
 */
import javax.swing.table.DefaultTableModel;

public class archivepanel extends javax.swing.JPanel {

 
    public archivepanel() {
        initComponents();
        jComboBox1.setSelectedIndex(0); // default all for jcombo box
        loadArchivedData();
    }

    // Main frame or main panel where both panels are created


  private void restoreEmployee(String username) {
    int confirm = JOptionPane.showConfirmDialog(null, 
            "Are you sure you want to restore this employee?", 
            "Confirm Restore", JOptionPane.YES_NO_OPTION);

    if (confirm == JOptionPane.YES_OPTION) {
        try (java.sql.Connection connection = db.mycon()) {
            connection.setAutoCommit(false);

            // Debugging: Print username
            System.out.println("Restoring Employee: " + username);

            // Step 1: Move data back to employee_table
            String restoreQuery = "INSERT INTO employee_table (employee_id, First_Name, Last_Name, Username, Password, role, status, logged_in, logged_out) " +
                                  "SELECT employee_id, First_Name, Last_Name, Username, Password, role, status, logged_in, logged_out " +
                                  "FROM employee_archive WHERE Username = ?";
            try (java.sql.PreparedStatement restoreStmt = connection.prepareStatement(restoreQuery)) {
                restoreStmt.setString(1, username);
                int rowsInserted = restoreStmt.executeUpdate();

                // Debugging: Check rows inserted
                System.out.println("Rows inserted into employee_table: " + rowsInserted);
            }

            // Step 2: Delete from employee_archive
            String deleteFromArchiveQuery = "DELETE FROM employee_archive WHERE Username = ?";
            try (java.sql.PreparedStatement deleteArchiveStmt = connection.prepareStatement(deleteFromArchiveQuery)) {
                deleteArchiveStmt.setString(1, username);
                int rowsDeleted = deleteArchiveStmt.executeUpdate();

                // Debugging: Check rows deleted
                System.out.println("Rows deleted from employee_archive: " + rowsDeleted);
            }

            connection.commit();
            JOptionPane.showMessageDialog(null, "Employee restored successfully.");
        } catch (java.sql.SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error occurred while restoring employee: " + ex.getMessage());
            ex.printStackTrace();
        }

        // Reload archive data after restoring
        loadArchivedData();
    }
}


    
    public void loadArchivedData() {
    try (java.sql.Connection connection = db.mycon()) {
        String query = "SELECT * FROM employee_archive";
        try (java.sql.PreparedStatement stmt = connection.prepareStatement(query);
             java.sql.ResultSet rs = stmt.executeQuery()) {

            DefaultTableModel archiveModel = (DefaultTableModel) archiveTable.getModel();
            archiveModel.setRowCount(0);  // Clear existing rows

            while (rs.next()) {
                Object[] row = new Object[] {
                    false,
                    rs.getString("First_Name"),
                    rs.getString("Last_Name"),
                    rs.getString("Username"),
                    rs.getString("Password"),
                    rs.getString("role"),
                    "inactive",
                    rs.getTimestamp("archived_date")
                };
                archiveModel.addRow(row);
              
            }
        }
    } catch (java.sql.SQLException ex) {
        JOptionPane.showMessageDialog(null, "Error loading archived data: " + ex.getMessage());
    }
    archiveTable.getColumnModel().getColumn(0).setCellEditor(new DefaultCellEditor(new JCheckBox()));  // For editing
    archiveTable.getColumnModel().getColumn(0).setCellRenderer(new CheckBoxRenderer());  // For rendering
}
    
    private void deleteSelectedRows() {
    DefaultTableModel model = (DefaultTableModel) archiveTable.getModel();
    int rowCount = model.getRowCount();

    boolean atLeastOneChecked = false;

    for (int i = 0; i < rowCount; i++) {
        boolean isChecked = (boolean) model.getValueAt(i, 0); // Checkbox in the first column
        if (isChecked) {
            atLeastOneChecked = true;
            String username = model.getValueAt(i, 3).toString(); // Assuming Username is in column 3

            // Confirmation prompt before deletion
            int confirm = JOptionPane.showConfirmDialog(null,
                    "Are you sure you want to permanently delete this employee: " + username + "?",
                    "Confirm Deletion",
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                try (java.sql.Connection connection = db.mycon()) {
                    connection.setAutoCommit(false); // Start transaction

                    // Step 1: Backup the data
                    String backupQuery = "INSERT INTO employee_backup (employee_id, First_Name, Last_Name, Username, Password, role, " +
                            "status, logged_in, logged_out, archived_date, soft_deleted, backup_date) " +
                            "SELECT employee_id, First_Name, Last_Name, Username, Password, role, status, logged_in, " +
                            "logged_out, archived_date, soft_deleted, NOW() " +
                            "FROM employee_archive WHERE Username = ?";

                    try (PreparedStatement backupStmt = connection.prepareStatement(backupQuery)) {
                        backupStmt.setString(1, username);
                        int backupRows = backupStmt.executeUpdate();

                        // Check if the backup was successful
                        if (backupRows == 0) {
                            connection.rollback();
                            JOptionPane.showMessageDialog(null, "Failed to backup employee: " + username);
                            continue; // Skip to the next employee
                        }
                    }

                    // Step 2: Delete the data from employee_archive
                    String deleteQuery = "DELETE FROM employee_archive WHERE username = ?";
                    try (PreparedStatement deleteStmt = connection.prepareStatement(deleteQuery)) {
                        deleteStmt.setString(1, username);
                        deleteStmt.executeUpdate();
                    }

                    connection.commit(); // Commit transaction
                    JOptionPane.showMessageDialog(null, "Employee data has been deleted.");
                } catch (java.sql.SQLException ex) {
                   
                    JOptionPane.showMessageDialog(null, "Error occurred while deleting data: " + ex.getMessage());
                }
            }
        }
    }

    if (!atLeastOneChecked) {
        JOptionPane.showMessageDialog(null, "Please select at least one employee to delete.");
    }

    // Reload the archive table to reflect the deletions
    loadArchivedData();
}


    
    public void loadArchiveData() {
    DefaultTableModel model = (DefaultTableModel) archiveTable.getModel();
    model.setRowCount(0);

    try (java.sql.Connection connection = db.mycon()) {
        String query = "SELECT * FROM employee_archive";
        try (java.sql.Statement statement = connection.createStatement();
             java.sql.ResultSet rs = statement.executeQuery(query)) {

            while (rs.next()) {
                Vector row = new Vector();
                row.add(rs.getString("employee_id"));
                row.add(rs.getString("First_Name"));
                row.add(rs.getString("Last_Name"));
                row.add(rs.getString("Username"));
                row.add(rs.getString("role"));
                row.add(rs.getString("status"));
                model.addRow(row);
            }
        }
    } catch (java.sql.SQLException ex) {
        JOptionPane.showMessageDialog(null, "Error occurred while loading archive data: " + ex.getMessage());
    }
}
    
    private void permanentlyDeleteEmployee(String employeeId) {
    int confirm = JOptionPane.showConfirmDialog(null, 
            "Are you sure you want to permanently delete this employee?", 
            "Confirm Permanent Deletion", JOptionPane.YES_NO_OPTION);

    if (confirm == JOptionPane.YES_OPTION) {
        try (java.sql.Connection connection = db.mycon()) {
            String deleteQuery = "DELETE FROM employee_archive WHERE employee_id = ?";
            try (java.sql.PreparedStatement deleteStmt = connection.prepareStatement(deleteQuery)) {
                deleteStmt.setString(1, employeeId);
                deleteStmt.executeUpdate();
                JOptionPane.showMessageDialog(null, "Employee permanently deleted.");
            }
        } catch (java.sql.SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error occurred while permanently deleting employee: " + ex.getMessage());
        }

        // Reload archive data after deletion
        loadArchiveData();
    }
}

    
    
    private void Packagefilter(String searchPackage) {
    DefaultTableModel model = (DefaultTableModel) archiveTable.getModel();
    model.setRowCount(0); // Clear existing rows

    try {
        java.sql.Connection con = db.mycon();
        String query = """
           SELECT 
               employee_id, first_name, last_name, username, password, role, status, created_at
           FROM 
               employee_table
           WHERE 
               first_name LIKE ? OR last_name LIKE ?
           ORDER BY 
               employee_id ASC;
        """;

        java.sql.PreparedStatement stmt = con.prepareStatement(query);
        stmt.setString(1, "%" + searchPackage + "%");
        stmt.setString(2, "%" + searchPackage + "%");
        java.sql.ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            String category = rs.getString("first_name");
            String pname = rs.getString("last_name");
            String pprice = rs.getString("username");
            String uprice = rs.getString("password");
            String tprice = rs.getString("role");
            String status = rs.getString("status");
            String time = rs.getString("created_at");

            model.addRow(new Object[]{false, category, pname, pprice, uprice, tprice, status, time});
        }

    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error filtering data: " + e.getMessage());
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
        jToggleButton1 = new javax.swing.JToggleButton();
        jToggleButton2 = new javax.swing.JToggleButton();
        search_emp = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        archiveTable = new javax.swing.JTable();
        jToggleButton3 = new javax.swing.JToggleButton();
        jLabel2 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(1290, 720));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(1396, 735));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jToggleButton1.setText("RESTORE");
        jToggleButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jToggleButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(646, 47, -1, -1));

        jToggleButton2.setText("DELETE");
        jToggleButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jToggleButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(731, 47, -1, -1));

        search_emp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                search_empActionPerformed(evt);
            }
        });
        search_emp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                search_empKeyReleased(evt);
            }
        });
        jPanel1.add(search_emp, new org.netbeans.lib.awtextra.AbsoluteConstraints(209, 47, 197, -1));

        jLabel1.setText("Search:");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(91, 50, 106, -1));

        archiveTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Checkbox", "First Name", "Last Name", "Username", "Password", "Role", "Status", "Archive Date"
            }
        ));
        jScrollPane1.setViewportView(archiveTable);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(24, 134, 1250, -1));

        jToggleButton3.setText("Refresh the table");
        jToggleButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton3ActionPerformed(evt);
            }
        });
        jPanel1.add(jToggleButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 47, -1, -1));

        jLabel2.setText("Search by:");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(91, 88, -1, -1));

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "First_Name", "Last_Name", "Username", "role" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });
        jPanel1.add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(209, 82, -1, -1));

        jPanel2.setBackground(new java.awt.Color(238, 238, 252));
        jPanel2.setPreferredSize(new java.awt.Dimension(1290, 80));

        jLabel3.setBackground(new java.awt.Color(238, 238, 252));
        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 51, 102));
        jLabel3.setText("Archived Accounts");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jLabel3)
                .addContainerGap(963, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel3)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1290, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 683, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jToggleButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton3ActionPerformed
           // TODO add your handling code here:
            loadArchivedData();
             JOptionPane.showMessageDialog(this, "Archive table refreshed.");
    }//GEN-LAST:event_jToggleButton3ActionPerformed

    private void jToggleButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton2ActionPerformed
    deleteSelectedRows();        // TODO add your handling code here:
    }//GEN-LAST:event_jToggleButton2ActionPerformed

    private void jToggleButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton1ActionPerformed
            // TODO add your handling code here:
int selectedRow = archiveTable.getSelectedRow();
    if (selectedRow >= 0) {
        String username = archiveTable.getValueAt(selectedRow, 3).toString(); // Assuming username is in column 3
        restoreEmployee(username);
    } else {
        JOptionPane.showMessageDialog(this, "Please select an employee to restore.");
    }
    }//GEN-LAST:event_jToggleButton1ActionPerformed

    private void search_empKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_search_empKeyReleased
    
        String search = search_emp.getText().trim();

    if (search.isEmpty()) {
        loadArchivedData(); // Reload the original content if the search text is empty
    } else {
        Packagefilter(search); // Filter table content
    }        
        
    }//GEN-LAST:event_search_empKeyReleased

    
    private void search_empActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_search_empActionPerformed
    // Get the search term entered by the user
    
    String searchQuery = search_emp.getText().trim();
    
    // Get the selected column from the JComboBox
    String searchBy = jComboBox1.getSelectedItem().toString();

    // Check if the search term is empty and show a message if so
    if (searchQuery.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Enter a search term!");
        return;
    }

    //SQL Query
    String query = "SELECT * FROM employee_archive WHERE " + searchBy + " LIKE ?";


    try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/inventory", "root", "");
         PreparedStatement stmt = conn.prepareStatement(query)) {

        // Set to 1 column
        stmt.setString(1, "%" + searchQuery + "%");
        

        // Execute the query and get the results
        ResultSet rs = stmt.executeQuery();

        // Get the table model and clear previous rows
        DefaultTableModel model = (DefaultTableModel) archiveTable.getModel();
        model.setRowCount(0); // Clear the existing data

        // Loop through the result set and add the rows to the table model
        while (rs.next()) {
            model.addRow(new Object[]{
                false,  // Checkbox (unchecked)
                rs.getString("First_Name"),
                rs.getString("Last_Name"),
                rs.getString("Username"),
                rs.getString("Password"),
                rs.getString("role"),
                rs.getString("status"),
                rs.getString("archived_date"),
            });
        }

        // If no rows are returned, display a message
        if (model.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "No results found!");
        }

    } catch (SQLException ex) {
        // Print stack trace and display error message if an exception occurs
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
    }
    }//GEN-LAST:event_search_empActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        
    }//GEN-LAST:event_jComboBox1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable archiveTable;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JToggleButton jToggleButton2;
    private javax.swing.JToggleButton jToggleButton3;
    private javax.swing.JTextField search_emp;
    // End of variables declaration//GEN-END:variables
}
