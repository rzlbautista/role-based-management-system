/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Interface;
import java.awt.Component;
import java.sql.ResultSet;
import java.math.BigDecimal;
import java.security.Timestamp;
import javax.swing.JOptionPane;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
    
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

class CheckBoxRenderer extends JCheckBox implements TableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        setSelected(value != null && (Boolean) value);
        return this;
    }
}



/**
 *
 * @author Renniel
 */
public class ManageAccountPanel extends javax.swing.JPanel {
    JpanelLoader jpload = new JpanelLoader();

    /**
     * 
     * Creates new form InventoryAccountPanel
     */
    
    // Main frame or main panel where both panels are created

    private archivepanel archivePanelInstance;

    // Constructor to accept archivePanel instance
    public ManageAccountPanel(archivepanel archivePanelInstance) {
        this.archivePanelInstance = archivePanelInstance;
        initComponents();
        tb_load();
    }

    public ManageAccountPanel() {
        initComponents();
        tb_load();
        
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
    DefaultTableModel model = (DefaultTableModel) it_table.getModel();
    model.setRowCount(0);  // Clear existing rows

    try {
        java.sql.Connection con = db.mycon();
        String query = "SELECT First_Name, Last_Name, Username, Role, Password, Status, Logged_In, Logged_Out FROM employee_table";
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
            String loggedIn = rs.getString("Logged_In");
            String loggedOut = rs.getString("Logged_Out");

            // Add checkbox column (unchecked by default)
            model.addRow(new Object[]{false, fname, lname,username,role, password,  status, loggedIn, loggedOut});
        }

    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error loading data: " + e.getMessage());
    }

    // Set the checkbox column to use a checkbox editor and renderer
    it_table.getColumnModel().getColumn(0).setCellEditor(new DefaultCellEditor(new JCheckBox()));  // For editing
    it_table.getColumnModel().getColumn(0).setCellRenderer(new CheckBoxRenderer());  // For rendering
}

  
  

private void deleteSelectedRows() {
    DefaultTableModel model = (DefaultTableModel) it_table.getModel();
    int rowCount = model.getRowCount();
    
    boolean atLeastOneChecked = false;

    for (int i = 0; i < rowCount; i++) {
        boolean isChecked = (boolean) model.getValueAt(i, 0);  // Checkbox in the first column
        if (isChecked) {
            atLeastOneChecked = true;
            String username = model.getValueAt(i, 3).toString();  // Assuming Username is in column 3
            
            // Confirmation prompt before deletion
            int confirm = JOptionPane.showConfirmDialog(null,
                    "Are you sure you want to delete this employee: " + username + "?",
                    "Confirm Deletion",
                    JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                try (java.sql.Connection connection = db.mycon()) {
                    // Archive the employee data before deleting
                    String archiveQuery = "INSERT INTO employee_archive (employee_id, First_Name, Last_Name, Username, Password, role, status, logged_in, logged_out) " +
                                          "SELECT employee_id, First_Name, Last_Name, Username, Password, role, status, logged_in, logged_out " +
                                          "FROM employee_table WHERE username = ?";
                    try (java.sql.PreparedStatement archiveStmt = connection.prepareStatement(archiveQuery)) {
                        archiveStmt.setString(1, username);  // Set the employee ID for archiving
                        archiveStmt.executeUpdate();
                    }

                    // Now delete the employee from the main table
                    String deleteQuery = "DELETE FROM employee_table WHERE username= ?";
                    try (java.sql.PreparedStatement deleteStmt = connection.prepareStatement(deleteQuery)) {
                        deleteStmt.setString(1, username);  // Set the employee ID for deletion
                        deleteStmt.executeUpdate();
                        JOptionPane.showMessageDialog(null, "Employee archived and deleted successfully.");
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
        it_panel = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        e_fname = new javax.swing.JTextField();
        e_lname = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        e_usn = new javax.swing.JTextField();
        e_pass = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        e_role = new javax.swing.JComboBox<>();
        jButton4 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        it_table = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jComboBox1 = new javax.swing.JComboBox<>();

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(1290, 800));

        jPanel3.setPreferredSize(new java.awt.Dimension(1290, 800));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        it_panel.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.setPreferredSize(new java.awt.Dimension(1290, 223));

        jLabel1.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 51, 102));
        jLabel1.setText("First Name");

        jLabel2.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 51, 102));
        jLabel2.setText("Last Name");

        jLabel3.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 51, 102));
        jLabel3.setText("Username");

        jLabel4.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 51, 102));
        jLabel4.setText("Password");

        e_fname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                e_fnameActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 51, 102));
        jLabel5.setText("Role");

        jButton1.setBackground(new java.awt.Color(238, 238, 248));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(0, 51, 102));
        jButton1.setText("UPDATE");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(238, 238, 248));
        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton2.setForeground(new java.awt.Color(0, 51, 102));
        jButton2.setText("ADD");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(238, 238, 248));
        jButton3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton3.setForeground(new java.awt.Color(0, 51, 102));
        jButton3.setText("CLEAR");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        e_role.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " ", "admin", "sales head", "inventory head", "sales", "inventory", "it" }));

        jButton4.setBackground(new java.awt.Color(238, 238, 248));
        jButton4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton4.setForeground(new java.awt.Color(0, 51, 102));
        jButton4.setText("DELETE");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(46, 46, 46)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(e_lname)
                    .addComponent(e_usn, javax.swing.GroupLayout.DEFAULT_SIZE, 245, Short.MAX_VALUE)
                    .addComponent(e_pass)
                    .addComponent(e_fname)
                    .addComponent(e_role, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(95, 95, 95)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton1))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jButton4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton3)))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(e_fname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton2)
                            .addComponent(jButton1))))
                .addGap(8, 8, 8)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(e_lname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4)
                    .addComponent(jButton3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(e_usn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(9, 9, 9)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(e_pass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(9, 9, 9)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(e_role, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(41, Short.MAX_VALUE))
        );

        it_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "CheckBox", "First Name", "Last Name", "Username", "Password", "Role", "Status", "Logged-In", "Logged-Out"
            }
        ));
        jScrollPane1.setViewportView(it_table);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jLabel6.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 51, 102));
        jLabel6.setText("SEARCH");

        jTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField3ActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 51, 102));
        jLabel7.setText("SEARCH BY");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " ", "First_Name", "Last_Name", "role", "Username", "Password", "role", "status", "logged_in", "logged_out" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(18, 18, 18)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(18, 18, 18)
                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 101, Short.MAX_VALUE)
                .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel6)
                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jDateChooser1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout it_panelLayout = new javax.swing.GroupLayout(it_panel);
        it_panel.setLayout(it_panelLayout);
        it_panelLayout.setHorizontalGroup(
            it_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(it_panelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(it_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, it_panelLayout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 677, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(11, 11, 11))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, it_panelLayout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1275, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        it_panelLayout.setVerticalGroup(
            it_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(it_panelLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(it_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 201, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 476, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jPanel3.add(it_panel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1290, 720));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 722, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void e_fnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_e_fnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_e_fnameActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
    // Add button
    String efname = e_fname.getText();
    String elname = e_lname.getText();
    String eusn = e_usn.getText();
    String epass = PasswordUtils.hashPassword(e_pass.getText()); // Hash the password
    String erole = e_role.getSelectedItem().toString(); 
    String status = "active";  // the "active" status is set as default value

    //this one is for encryption of the password
    String encryptedPass = null;
    try {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hash = md.digest(epass.getBytes());
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            hexString.append(String.format("%02x", b));
        }
        encryptedPass = hexString.toString();
    } catch (NoSuchAlgorithmException e) {
     e.printStackTrace();
    }

    try {
        // This one is for initializing the database connection to mysql
        java.sql.Connection con = db.mycon();
        con.setAutoCommit(false);

        // Insert employee data into employee_table
        String employeeSql = "INSERT INTO employee_table (First_Name, Last_Name, Username, Password,role, status, logged_in, logged_out) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        java.sql.PreparedStatement employeeStmt = con.prepareStatement(employeeSql, Statement.RETURN_GENERATED_KEYS);
    
        employeeStmt.setString(1, efname);
        employeeStmt.setString(2, elname);
        employeeStmt.setString(3, eusn);
        employeeStmt.setString(4, encryptedPass);  // Use encrypted password here
        employeeStmt.setString(5, erole);
        employeeStmt.setString(6, status);  // Set status to "active"
        employeeStmt.setTimestamp(7, null); // For logged_in (initialize as null if not logged in yet)
        employeeStmt.setTimestamp(8,null); // For logged_out (initialize as null if not logged out yet)
    
        // Execute the statement
        employeeStmt.executeUpdate();
        con.commit();

        JOptionPane.showMessageDialog(null, "Employee Data Saved");

    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error saving data: " + e.getMessage());
    } finally {
        tb_load();  // tb_load() is used for loading the  data into table
    }


    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
    // Update button. Clears fields for first name, last name, username, password, and role
    e_fname.setText("");  // empty text field first name
    e_lname.setText("");  // empty text field last name
    e_usn.setText("");    // empty text field username
    e_pass.setText("");   // empty text field password
    e_role.setSelectedIndex(0);  // set jcombobox to default which is yung empty
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
    // Update button
    String fname = e_fname.getText(); 
    String lname = e_lname.getText(); 
    String erole = e_role.getSelectedItem().toString();  
    String username = e_usn.getText(); 
    String password = e_pass.getText();

    // Hash the password
    String hashedPassword = null;
    try {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hash = md.digest(password.getBytes());
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            hexString.append(String.format("%02x", b));
        }
        hashedPassword = hexString.toString();  // The hashed password
    } catch (NoSuchAlgorithmException e) {
        e.printStackTrace();
    }

    try {
        java.sql.Connection con = db.mycon();
        java.sql.PreparedStatement stmt = con.prepareStatement("SELECT * FROM Employee_Table WHERE Username = ?");
        stmt.setString(1, username);
        java.sql.ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            // If employee exists, update the record
            String updateQuery = "UPDATE Employee_Table SET First_Name = ?, Last_Name = ?, Role = ?, Password = ? WHERE Username = ?";
            java.sql.PreparedStatement
            updateStmt = con.prepareStatement(updateQuery);
            updateStmt.setString(1, fname);
            updateStmt.setString(2, lname);
            updateStmt.setString(3, erole);
            updateStmt.setString(4, hashedPassword);  // Store the hashed password
            updateStmt.setString(5, username);
            updateStmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "Employee Data Updated");
        } else {
            // If employee does not exist, insert a new record
            String insertQuery = "INSERT INTO Employee_Table (First_Name, Last_Name, Role, Username, Password) VALUES (?, ?, ?, ?, ?)";
            java.sql.PreparedStatement insertStmt = con.prepareStatement(insertQuery);
            insertStmt.setString(1, fname);
            insertStmt.setString(2, lname);
            insertStmt.setString(3, erole);
            insertStmt.setString(4, username);
            insertStmt.setString(5, hashedPassword);  // Store the hashed password
            insertStmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "Employee Data Saved");
        }
    } catch (java.sql.SQLException e) {
        System.out.println(e);
        JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
    } finally {
        tb_load();  // Reload the data into the table
    }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
    deleteSelectedRows();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed
    // Get the search term entered by the user
    String searchQuery = jTextField3.getText().trim();
    
    // Get the selected column from the JComboBox
    String searchBy = jComboBox1.getSelectedItem().toString();

    // Check if the search term is empty and show a message if so
    if (searchQuery.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Enter a search term!");
        return;
    }

    //SQL Query
    String query = "SELECT * FROM employee_table WHERE " + searchBy + " LIKE ?";


    try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/inventory", "root", "");
         PreparedStatement stmt = conn.prepareStatement(query)) {

        // Set to 1 column
        stmt.setString(1, "%" + searchQuery + "%");
        

        // Execute the query and get the results
        ResultSet rs = stmt.executeQuery();

        // Get the table model and clear previous rows
        DefaultTableModel model = (DefaultTableModel) it_table.getModel();
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
                rs.getString("logged_in"),
                rs.getString("logged_out")
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
    }//GEN-LAST:event_jTextField3ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField e_fname;
    private javax.swing.JTextField e_lname;
    private javax.swing.JTextField e_pass;
    private javax.swing.JComboBox<String> e_role;
    private javax.swing.JTextField e_usn;
    private javax.swing.JPanel it_panel;
    private javax.swing.JTable it_table;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JComboBox<String> jComboBox1;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField3;
    // End of variables declaration//GEN-END:variables
}
