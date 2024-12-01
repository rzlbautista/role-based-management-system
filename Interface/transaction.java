/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Interface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.*;
import javax.swing.text.*;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class transaction extends javax.swing.JPanel {
    JpanelLoader jpload = new JpanelLoader();



public transaction() {
    initComponents();
    tb_load();
   

}

 private void tb_load() {
    DefaultTableModel model = (DefaultTableModel) transactionpanel.getModel();
    model.setRowCount(0);  // Clear existing rows

    try {
        java.sql.Connection con = db.mycon();
        String query = "SELECT i.invoice_id, i.quotation_no, CONCAT(c.first_name, ' ', c.last_name) AS customer_name, " +
                       "i.package_item_id, i.Discount_Percentage, i.Grand_Total, i.status, i.created_at " +
                       "FROM invoice i JOIN customer_table c ON i.customer_id = c.customer_id";
        
        java.sql.PreparedStatement stmt = con.prepareStatement(query);
        java.sql.ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            // Add data to the table
            model.addRow(new Object[]{
                false, // Checkbox column (unchecked by default)
                rs.getString("invoice_id"),
                rs.getString("quotation_no"),
                rs.getString("customer_name"),
                rs.getString("package_item_id"),
                rs.getString("Discount_Percentage"),
                rs.getString("Grand_Total"),
                rs.getString("status"),
                rs.getString("created_at")
            });
        }
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error loading data: " + e.getMessage());
    }


    // Set the checkbox column to use a checkbox editor and renderer
    transactionpanel.getColumnModel().getColumn(0).setCellEditor(new DefaultCellEditor(new JCheckBox()));  // For editing
    transactionpanel.getColumnModel().getColumn(0).setCellRenderer(new CheckBoxRenderer());  // For rendering
}


 







 
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel12 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        inventoryaccount_panel = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jToggleButton1 = new javax.swing.JToggleButton();
        t_status = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        transactionpanel = new javax.swing.JTable();

        jLabel12.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 51, 102));
        jLabel12.setText("Select Installer:");

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(1290, 800));

        jPanel3.setPreferredSize(new java.awt.Dimension(1290, 800));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        inventoryaccount_panel.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.setPreferredSize(new java.awt.Dimension(1290, 223));

        jLabel3.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 51, 102));
        jLabel3.setText("Select to change status:");

        jToggleButton1.setText("UPDATE");
        jToggleButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton1ActionPerformed(evt);
            }
        });

        t_status.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " ", "Pending", "Completed", "Cancelled", " " }));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(t_status, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(jToggleButton1)
                .addContainerGap(28, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(t_status, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jToggleButton1))
                .addContainerGap(29, Short.MAX_VALUE))
        );

        transactionpanel.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Check Box", "Invoice ID ", "Quotation No.", "Customer Name", "Package ID", "Discount", "Total Amount", "Sttatus", "Created At"
            }
        ));
        jScrollPane2.setViewportView(transactionpanel);
        if (transactionpanel.getColumnModel().getColumnCount() > 0) {
            transactionpanel.getColumnModel().getColumn(6).setResizable(false);
        }

        javax.swing.GroupLayout inventoryaccount_panelLayout = new javax.swing.GroupLayout(inventoryaccount_panel);
        inventoryaccount_panel.setLayout(inventoryaccount_panelLayout);
        inventoryaccount_panelLayout.setHorizontalGroup(
            inventoryaccount_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(inventoryaccount_panelLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(inventoryaccount_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 575, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1244, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        inventoryaccount_panelLayout.setVerticalGroup(
            inventoryaccount_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(inventoryaccount_panelLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 501, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(102, Short.MAX_VALUE))
        );

        jPanel3.add(inventoryaccount_panel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1290, 720));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 722, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void stocktableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_stocktableMouseClicked
  
         
    }//GEN-LAST:event_stocktableMouseClicked

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
 
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void i_search_tblActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_i_search_tblActionPerformed
  
    }//GEN-LAST:event_i_search_tblActionPerformed

    private void fromMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fromMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_fromMouseClicked

    private void toMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_toMouseClicked

    private void jToggleButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton1ActionPerformed
   // Get the status value from the combo box (or text field)
    // Get the status value from the combo box (or text field)
    String status = (String) t_status.getSelectedItem();  // Assuming t_status is a JComboBox

    // Get the selected rows from the JTable
    int[] selectedRows = transactionpanel.getSelectedRows();  // Replace 'yourTable' with your actual JTable variable name

    if (selectedRows.length == 0) {
        JOptionPane.showMessageDialog(null, "Please select at least one invoice to update.");
        return;
    }

    try {
        // Establish a connection to the database
        java.sql.Connection con = db.mycon();
        
        // Prepare the SQL update statement
        String query = "UPDATE invoice SET status = ? WHERE invoice_id = ?";
        java.sql.PreparedStatement preparedStatement = con.prepareStatement(query);

        // Loop through each selected row
        for (int row : selectedRows) {
            // Get the invoice_id from the table (assuming it's in the first column)
            String invoice_id = transactionpanel.getValueAt(row, 1).toString();  // Adjust column index if necessary

            // Set the parameters for the prepared statement
            preparedStatement.setString(1, status);   // Set the new status
            preparedStatement.setString(2, invoice_id); // Set the invoice_id to update

            // Execute the update query
            preparedStatement.executeUpdate();
        }

        JOptionPane.showMessageDialog(null, "Status updated for selected invoices.");
        
        // Reload the table after the update
        tb_load();  // Call this method to reload the data
    } catch (SQLException e) {
        System.out.println("Error updating status: " + e);
        JOptionPane.showMessageDialog(null, "Error updating data: " + e.getMessage());
    }

    }//GEN-LAST:event_jToggleButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel inventoryaccount_panel;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JComboBox<String> t_status;
    private javax.swing.JTable transactionpanel;
    // End of variables declaration//GEN-END:variables
}
