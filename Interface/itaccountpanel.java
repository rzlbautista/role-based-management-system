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
public class itaccountpanel extends javax.swing.JPanel {
    JpanelLoader jpload = new JpanelLoader();

    /**
     * 
     * Creates new form InventoryAccountPanel
     */
    
    // Main frame or main panel where both panels are created

    private archivepanel archivePanelInstance;

    // Constructor to accept archivePanel instance
    public itaccountpanel(archivepanel archivePanelInstance) {
        this.archivePanelInstance = archivePanelInstance;
        initComponents();
        tb_load();
    }

    public itaccountpanel() {
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
    DefaultTableModel model = (DefaultTableModel) it_table.getModel();
    model.setRowCount(0);  // Clear existing rows

    try {
        java.sql.Connection con = db.mycon();
        String query = "SELECT First_Name, Last_Name, Username, Password, Role, Status, Created_At FROM employee_table";
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
            String createdAt = rs.getString("Created_At");

            // Add checkbox column (unchecked by default)
            model.addRow(new Object[]{false, fname, lname,username,role, password,  status,  createdAt});
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




  
private void Packagefilter(String searchPackage) {
    DefaultTableModel model = (DefaultTableModel) it_table.getModel();
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

        jPanel3 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
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
        search_emp = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jDateChooser2 = new com.toedter.calendar.JDateChooser();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(1290, 800));

        jPanel3.setPreferredSize(new java.awt.Dimension(1290, 800));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(238, 238, 252));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 51, 102));
        jLabel10.setText("Manage User Accounts");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jLabel10)
                .addContainerGap(894, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel10)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        jPanel3.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1290, 80));

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

        jButton1.setBackground(new java.awt.Color(222, 238, 255));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(0, 51, 102));
        jButton1.setText("UPDATE");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(222, 238, 255));
        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton2.setForeground(new java.awt.Color(0, 51, 102));
        jButton2.setText("ADD");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(222, 238, 255));
        jButton3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton3.setForeground(new java.awt.Color(0, 51, 102));
        jButton3.setText("CLEAR");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        e_role.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "admin", "sales head", "inventory head", "sales", "inventory", "it" }));
        e_role.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                e_roleActionPerformed(evt);
            }
        });

        jButton4.setBackground(new java.awt.Color(222, 238, 255));
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
                    .addComponent(e_usn)
                    .addComponent(e_pass)
                    .addComponent(e_fname)
                    .addComponent(e_role, 0, 245, Short.MAX_VALUE))
                .addGap(68, 68, 68)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jButton3)
                        .addGap(18, 18, 18)
                        .addComponent(jButton4))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jButton2)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(e_fname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(7, 7, 7)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(e_lname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(e_usn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton2)
                            .addComponent(jButton1))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton3)
                            .addComponent(jButton4))))
                .addGap(13, 13, 13)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(e_pass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(e_role, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(59, Short.MAX_VALUE))
        );

        it_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "CheckBox", "First Name", "Last Name", "Username", "Password", "Role", "Status", "Created_At"
            }
        ));
        it_table.setGridColor(new java.awt.Color(204, 255, 255));
        it_table.setSelectionBackground(new java.awt.Color(0, 0, 0));
        it_table.setSelectionForeground(new java.awt.Color(204, 204, 204));
        jScrollPane1.setViewportView(it_table);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jLabel6.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 51, 102));
        jLabel6.setText("SEARCH");

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

        jLabel7.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 51, 102));
        jLabel7.setText("SEARCH BY");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "First_Name", "Last_Name", "role", "Username", "Password", "role", "status", "logged_in", "logged_out" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jDateChooser2.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jDateChooser2PropertyChange(evt);
            }
        });

        jDateChooser1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jDateChooser1PropertyChange(evt);
            }
        });

        jLabel8.setText("FROM");

        jLabel9.setText("TO");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addComponent(jLabel7)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel9)
                        .addGap(18, 18, 18)
                        .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(jLabel6)
                        .addGap(18, 18, 18)
                        .addComponent(search_emp, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 272, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel9)
                        .addComponent(jLabel7))
                    .addComponent(jDateChooser2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jDateChooser1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(92, 92, 92)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(search_emp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11))
        );

        javax.swing.GroupLayout it_panelLayout = new javax.swing.GroupLayout(it_panel);
        it_panel.setLayout(it_panelLayout);
        it_panelLayout.setHorizontalGroup(
            it_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(it_panelLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(it_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(it_panelLayout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 644, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(11, 11, 11))
                    .addGroup(it_panelLayout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1241, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(118, Short.MAX_VALUE))))
        );
        it_panelLayout.setVerticalGroup(
            it_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(it_panelLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(it_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 442, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34))
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

    private void e_fnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_e_fnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_e_fnameActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
    // Add button
    String efname = e_fname.getText().trim();
    String elname = e_lname.getText().trim();
    String eusn = e_usn.getText().trim();
    String epass = e_pass.getText().trim();
    String erole = e_role.getSelectedItem().toString(); 
    String status = "active";  // the "active" status is set as default value

    // Check for empty fields
    if (efname.isEmpty() || elname.isEmpty() || eusn.isEmpty() || epass.isEmpty()) {
        JOptionPane.showMessageDialog(null, "All fields must be filled in!", "Error", JOptionPane.ERROR_MESSAGE);
        return; // Stop further execution
    }

    // Encrypt the password
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
        JOptionPane.showMessageDialog(null, "Error encrypting password: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    try {
        // Initialize database connection
        java.sql.Connection con = db.mycon();

        // Check for duplicate username
        String checkUsernameSql = "SELECT COUNT(*) FROM employee_table WHERE Username = ?";
        PreparedStatement checkUsernameStmt = con.prepareStatement(checkUsernameSql);
        checkUsernameStmt.setString(1, eusn);
        ResultSet usernameResult = checkUsernameStmt.executeQuery();
        if (usernameResult.next() && usernameResult.getInt(1) > 0) {
            JOptionPane.showMessageDialog(null, "Username already exists!", "Duplicate Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Check for duplicate first and last name combination
        String checkNameSql = "SELECT COUNT(*) FROM employee_table WHERE First_Name = ? AND Last_Name = ?";
        PreparedStatement checkNameStmt = con.prepareStatement(checkNameSql);
        checkNameStmt.setString(1, efname);
        checkNameStmt.setString(2, elname);
        ResultSet nameResult = checkNameStmt.executeQuery();
        if (nameResult.next() && nameResult.getInt(1) > 0) {
            JOptionPane.showMessageDialog(null, "An employee with the same first and last name already exists!", "Duplicate Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Insert employee data into employee_table
        String employeeSql = "INSERT INTO employee_table (First_Name, Last_Name, Username, Password, role, status, logged_in, logged_out, Created_At) VALUES (?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP)";
        PreparedStatement employeeStmt = con.prepareStatement(employeeSql, Statement.RETURN_GENERATED_KEYS);
        employeeStmt.setString(1, efname);
        employeeStmt.setString(2, elname);
        employeeStmt.setString(3, eusn);
        employeeStmt.setString(4, encryptedPass);  // Use encrypted password here
        employeeStmt.setString(5, erole);
        employeeStmt.setString(6, status);  // Set status to "active"
        employeeStmt.setTimestamp(7, null); // For logged_in (initialize as null if not logged in yet)
        employeeStmt.setTimestamp(8, null); // For logged_out (initialize as null if not logged out yet)
        // employeeStmt.setTimestamp(9, null); // Created At

        // Execute the statement
        employeeStmt.executeUpdate();
        JOptionPane.showMessageDialog(null, "Employee Data Saved");

    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error saving data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    } finally {
        tb_load();  // tb_load() is used for loading the data into table
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
            // Ensure this is within your class
DefaultTableModel tblModel = (DefaultTableModel) it_table.getModel();

// Add a listener to detect row selection and populate the text fields
it_table.getSelectionModel().addListSelectionListener(event -> {
    if (!event.getValueIsAdjusting()) { // Prevent duplicate triggers
        int selectedRow = it_table.getSelectedRow();
        System.out.println("Selected Row: " + selectedRow); // Debugging output
        if (selectedRow != -1) { // Check if a row is selected
            // Populate text fields based on the new column arrangement
            e_fname.setText((String) tblModel.getValueAt(selectedRow, 1)); // FirstName is in column 2 (index 1)
            e_lname.setText((String) tblModel.getValueAt(selectedRow, 2)); // LastName is in column 3 (index 2)
            e_usn.setText((String) tblModel.getValueAt(selectedRow, 3)); // Username is in column 4 (index 3)
            e_pass.setText((String) tblModel.getValueAt(selectedRow, 4)); // Password is in column 5 (index 4)
            e_role.setSelectedItem(tblModel.getValueAt(selectedRow, 5));  // Role is in column 6 (index 5)
            System.out.println("Fields populated successfully."); // Debugging output
        }
    }
});

// Code for updating the selected row when a button is clicked
int selectedRow = it_table.getSelectedRow();
if (selectedRow == -1) {
    if (it_table.getRowCount() == 0) {
        JOptionPane.showMessageDialog(this, "Table is empty.");
    } else {
        JOptionPane.showMessageDialog(this, "Please select a row for update.");
    }
} else {
    // Retrieve values from text fields
    String FirstName = e_fname.getText();
    String LastName = e_lname.getText();
    String Username = e_usn.getText();
    String Password = e_pass.getText();
    String Role = (String) e_role.getSelectedItem();

    // Validate inputs
    if (FirstName.isEmpty() || LastName.isEmpty() || Username.isEmpty() || Password.isEmpty() || Role.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Please fill in all fields before updating.");
    } else {
        // Update the selected row in the table
        tblModel.setValueAt(FirstName, selectedRow, 1); // FirstName in column 2 (index 1)
        tblModel.setValueAt(LastName, selectedRow, 2);  // LastName in column 3 (index 2)
        tblModel.setValueAt(Username, selectedRow, 3);  // Username in column 4 (index 3)
        tblModel.setValueAt(Password, selectedRow, 4);  // Password in column 5 (index 4)
        tblModel.setValueAt(Role, selectedRow, 5);      // Role in column 6 (index 5)

        JOptionPane.showMessageDialog(this, "Update Successful.");
    }
}

// Refresh the table view if tb_load() is required
tb_load();

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
    deleteSelectedRows();
    }//GEN-LAST:event_jButton4ActionPerformed

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

// Construct the base query
String query = "SELECT * FROM employee_table WHERE " + searchBy + " LIKE ?";

// Print the query and parameters for debugging
System.out.println("SQL Query: " + query);
System.out.println("Search Query: " + "%" + searchQuery + "%");

try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/inventory", "root", "");
     PreparedStatement stmt = conn.prepareStatement(query)) {

    // Set the search query parameter
    stmt.setString(1, "%" + searchQuery + "%");

    // Execute the query and get the results
    ResultSet rs = stmt.executeQuery();
    DefaultTableModel model = (DefaultTableModel) it_table.getModel();
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
    ex.printStackTrace();
    JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
}
    }//GEN-LAST:event_search_empActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void e_roleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_e_roleActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_e_roleActionPerformed

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

    private void search_empKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_search_empKeyReleased
        // TODO add your handling code here:
        
        String search = search_emp.getText().trim();

    if (search.isEmpty()) {
        tb_load(); // Reload the original content if the search text is empty
    } else {
        Packagefilter(search); // Filter table content
    }
    }//GEN-LAST:event_search_empKeyReleased


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
    private com.toedter.calendar.JDateChooser jDateChooser2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField search_emp;
    // End of variables declaration//GEN-END:variables
}
