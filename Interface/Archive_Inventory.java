/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Interface;

import java.util.Vector;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class Archive_Inventory extends javax.swing.JPanel {

    public Archive_Inventory() {
        initComponents();
       loadArchivedData();
    }




public void loadArchivedData() {
    try (java.sql.Connection connection = db.mycon()) {
        String query = "SELECT * FROM stock_archive";
        try (java.sql.PreparedStatement stmt = connection.prepareStatement(query);
             java.sql.ResultSet rs = stmt.executeQuery()) {

            DefaultTableModel archiveModel = (DefaultTableModel) archiveTable.getModel();
            archiveModel.setRowCount(0);  // Clear existing rows

            while (rs.next()) {
                Object[] row = new Object[] {
                    false,
                    rs.getString("product_name"),
                    rs.getString("price"),
                    rs.getString("current_stock"),
                    rs.getString("supplier_name"),
                    rs.getString("email"),
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

private void restoreInventory(String productName) {
    int confirm = JOptionPane.showConfirmDialog(null, 
            "Are you sure you want to restore this item?", 
            "Confirm Restore", JOptionPane.YES_NO_OPTION);

    if (confirm == JOptionPane.YES_OPTION) {
        try (java.sql.Connection connection = db.mycon()) {
            connection.setAutoCommit(false);

            // Debugging: Print productName
            System.out.println("Restoring Item: " + productName);

            // Step 1: Move data back to stock_table
            String restoreQuery = "INSERT INTO stock_table (Product_ID, Supplier_ID, Current_Stock) "
                    + "SELECT p.Product_ID, s.Supplier_ID, sa.Current_Stock "
                    + "FROM stock_archive sa "
                    + "JOIN product_table p ON sa.Product_Name = p.Product_Name "
                    + "JOIN supplier_table s ON sa.Supplier_Name = s.Supplier_Name "
                    + "WHERE sa.Product_Name = ?";
            try (java.sql.PreparedStatement restoreStmt = connection.prepareStatement(restoreQuery)) {
                restoreStmt.setString(1, productName); // Set the correct productName
                int rowsInserted = restoreStmt.executeUpdate();

                // Debugging: Check rows inserted
                System.out.println("Rows inserted into stock_table: " + rowsInserted);
            }

            // Step 2: Delete from stock_archive
            String deleteFromArchiveQuery = "DELETE FROM stock_archive WHERE product_name = ?";
            try (java.sql.PreparedStatement deleteArchiveStmt = connection.prepareStatement(deleteFromArchiveQuery)) {
                deleteArchiveStmt.setString(1, productName);
                int rowsDeleted = deleteArchiveStmt.executeUpdate();

                // Debugging: Check rows deleted
                System.out.println("Rows deleted from stock_archive: " + rowsDeleted);
            }

            connection.commit();
            JOptionPane.showMessageDialog(null, "Item restored successfully.");
        } catch (java.sql.SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error occurred while restoring item: " + ex.getMessage());
            ex.printStackTrace();
        }

        // Reload archived data after restoring
        loadArchivedData();
    }
}


 private void deleteSelectedRows() {
    DefaultTableModel model = (DefaultTableModel) archiveTable.getModel();
    int rowCount = model.getRowCount();
    
    boolean atLeastOneChecked = false;

    for (int i = 0; i < rowCount; i++) {
        boolean isChecked = (boolean) model.getValueAt(i, 0);  // Checkbox in the first column
        if (isChecked) {
            atLeastOneChecked = true;
            String product_name = model.getValueAt(i, 1).toString();  // Assuming Username is in column 3
            
            // Confirmation prompt before deletion
            int confirm = JOptionPane.showConfirmDialog(null,
                    "Are you sure you want to permanently delete this item: " + product_name + "?",
                    "Confirm Deletion",
                    JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                try (java.sql.Connection connection = db.mycon()) {
                    // Permanently delete the employee from the archive table
                    String deleteQuery = "DELETE FROM stock_archive WHERE product_name = ?";
                    try (java.sql.PreparedStatement deleteStmt = connection.prepareStatement(deleteQuery)) {
                        deleteStmt.setString(1,product_name); // Set the employee ID for deletion
                        deleteStmt.executeUpdate();
                        JOptionPane.showMessageDialog(null, "Item data permanently deleted.");
                    }
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
        String query = "SELECT * FROM stock_archive";
        try (java.sql.Statement statement = connection.createStatement();
             java.sql.ResultSet rs = statement.executeQuery(query)) {

            while (rs.next()) {
                Vector row = new Vector();
                 row.add(rs.getString("archive_id"));
                row.add(rs.getString("product_name"));
                row.add(rs.getString("price"));
                row.add(rs.getString("current_stock"));
                row.add(rs.getString("supplier_name"));
                row.add(rs.getString("email"));
                row.add(rs.getString("archived_date"));
                model.addRow(row);
            }
        }
    } catch (java.sql.SQLException ex) {
        JOptionPane.showMessageDialog(null, "Error occurred while loading archive data: " + ex.getMessage());
    }
}
 
   private void permanentlyDeleteEmployee(String employeeId) {
    int confirm = JOptionPane.showConfirmDialog(null, 
            "Are you sure you want to permanently delete this item?", 
            "Confirm Permanent Deletion", JOptionPane.YES_NO_OPTION);

    if (confirm == JOptionPane.YES_OPTION) {
        try (java.sql.Connection connection = db.mycon()) {
            String deleteQuery = "DELETE FROM stock_archive WHERE archive_id = ?";
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
        jTextField1 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        archiveTable = new javax.swing.JTable();
        jToggleButton3 = new javax.swing.JToggleButton();

        setPreferredSize(new java.awt.Dimension(1290, 720));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jToggleButton1.setText("RESTORE");
        jToggleButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton1ActionPerformed(evt);
            }
        });

        jToggleButton2.setText("DELETE");
        jToggleButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton2ActionPerformed(evt);
            }
        });

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField1KeyReleased(evt);
            }
        });

        jLabel1.setText("Search:");

        archiveTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Checkbox", "Product Name", "Price", "Current Stock", "Supplier Name", "Email", "Archived Date"
            }
        ));
        jScrollPane1.setViewportView(archiveTable);

        jToggleButton3.setText("Refresh the table");
        jToggleButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(64, 64, 64)
                .addComponent(jToggleButton3)
                .addGap(56, 56, 56)
                .addComponent(jToggleButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jToggleButton2)
                .addGap(491, 491, 491))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jToggleButton1)
                    .addComponent(jToggleButton2)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jToggleButton3))
                .addGap(61, 61, 61)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(168, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jToggleButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton3ActionPerformed
           // TODO add your handling code here:
            loadArchivedData();
             JOptionPane.showMessageDialog(this, "Archive table refreshed.");
    }//GEN-LAST:event_jToggleButton3ActionPerformed

    private void jToggleButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton2ActionPerformed
        deleteSelectedRows();
        
    }//GEN-LAST:event_jToggleButton2ActionPerformed

    private void jToggleButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton1ActionPerformed
 int selectedRow = archiveTable.getSelectedRow();
    if (selectedRow >= 0) {
        // Get the product name from the second column (index 1)
        String productName = archiveTable.getValueAt(selectedRow, 1).toString(); // Assuming product_name is in column 1
        restoreInventory(productName);
    } else {
        JOptionPane.showMessageDialog(this, "Please select an item to restore.");
    }
    }//GEN-LAST:event_jToggleButton1ActionPerformed

    private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyReleased
        // TODO add your handling code here:
        
        
    }//GEN-LAST:event_jTextField1KeyReleased

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable archiveTable;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JToggleButton jToggleButton2;
    private javax.swing.JToggleButton jToggleButton3;
    // End of variables declaration//GEN-END:variables
}
