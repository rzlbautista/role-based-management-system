/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Interface;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static java.lang.ProcessBuilder.Redirect.to;
import java.sql.ResultSet;
import java.math.BigDecimal;
import java.security.Timestamp;
import javax.swing.JOptionPane;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.regex.Pattern;
import java.util.regex.*;
import java.util.regex.Matcher;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
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
import java.util.Date;
import static java.util.Date.from;
import java.util.HashMap;
import java.util.Map;
/**
 *
 * @author Renniel
 */
public class InventoryAccountPanel extends javax.swing.JPanel {
    JpanelLoader jpload = new JpanelLoader();

    /**
     * Creates new form InventoryAccountPanel
     */
    
    
    public InventoryAccountPanel() {
        initComponents();
        tb_load();
        categoryComboBox = new javax.swing.JComboBox<>();
        loadCategories(); // Load initial categories

    }
    
    
private javax.swing.JComboBox<String> categoryComboBox; // Declare the ComboBox

public void loadCategories() {
    try {
        java.sql.Connection con = db.mycon();
        String query = "SELECT category_name FROM category";
        java.sql.PreparedStatement stmt = con.prepareStatement(query);
        java.sql.ResultSet rs = stmt.executeQuery();

        // Clear existing items in the ComboBox
        categoryComboBox.removeAllItems();
        while (rs.next()) {
            categoryComboBox.addItem(rs.getString("category_name"));
        }
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error loading categories: " + e.getMessage());
    }
}






private void tb_load() {
    // Define the table model with a checkbox (Boolean) column in the first position
    DefaultTableModel model = new DefaultTableModel(
        new Object[]{"Select", "Product Name", "Category", "Price", "Current Stock", "Supplier Name", "Supplier Email", "Last Updated"}, 0) {
        @Override
        public Class<?> getColumnClass(int columnIndex) {
            // Set the first column as Boolean for checkboxes
            if (columnIndex == 0) {
                return Boolean.class;
            }
            return super.getColumnClass(columnIndex);
        }
    };
    
    // Set the model to the table
    stocktable.setModel(model);
    model.setRowCount(0);  // Clear any existing rows

    try {
        java.sql.Connection con = db.mycon();
        String query = "SELECT p.Product_Name, c.Category_Name, p.Price, st.Current_Stock, " +
                       "s.Supplier_Name, s.Email, st.Last_Updated " +
                       "FROM stock_table st " +
                       "JOIN product_table p ON st.Product_ID = p.Product_ID " +
                       "JOIN supplier_table s ON st.Supplier_ID = s.Supplier_ID " +
                       "JOIN category c ON p.Category_ID = c.Category_ID";

        java.sql.PreparedStatement stmt = con.prepareStatement(query);
        ResultSet rs = stmt.executeQuery();

        // Loop through the result set and add rows to the table model
        while (rs.next()) {
            String productName = rs.getString("Product_Name");
            String categoryName = rs.getString("Category_Name"); // Retrieve the category name
            BigDecimal price = rs.getBigDecimal("Price");
            int currentStock = rs.getInt("Current_Stock");
            String supplierName = rs.getString("Supplier_Name");
            String supplierEmail = rs.getString("Email");
            java.sql.Timestamp lastUpdated = rs.getTimestamp("Last_Updated");

            // Add a new row with a checkbox in the first column
            model.addRow(new Object[]{false, productName, categoryName, price, currentStock, supplierName, supplierEmail, lastUpdated});
        }

    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error loading data: " + e.getMessage());
    }
}


    public class LoggedInUser {
    private static int employeeId;
    private static String fullName;

    // Setters and Getters
    public static void setEmployeeId(int id) {
        employeeId = id;
    }

    public static int getEmployeeId() {
        return employeeId;
    }

    public static void setFullName(String name) {
        fullName = name;
    }

    public static String getFullName() {
        return fullName;
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
        jToggleButton1 = new javax.swing.JToggleButton();
        jToggleButton2 = new javax.swing.JToggleButton();
        inventoryaccount_panel = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        p_name = new javax.swing.JTextField();
        p_price = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        p_quantity = new javax.swing.JTextField();
        s_name = new javax.swing.JTextField();
        s_emailadd = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        category = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        stocktable = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        i_search_tbl = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        from = new com.toedter.calendar.JDateChooser();
        jLabel8 = new javax.swing.JLabel();
        to = new com.toedter.calendar.JDateChooser();
        jLabel9 = new javax.swing.JLabel();
        searchcatergory = new javax.swing.JComboBox<>();

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(1290, 800));

        jPanel3.setPreferredSize(new java.awt.Dimension(1290, 800));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(238, 238, 252));

        jToggleButton1.setText("Inventory");
        jToggleButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton1ActionPerformed(evt);
            }
        });

        jToggleButton2.setText("Package");
        jToggleButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jToggleButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jToggleButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(1070, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(30, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jToggleButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jToggleButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15))
        );

        jPanel3.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1290, 80));

        inventoryaccount_panel.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.setPreferredSize(new java.awt.Dimension(1290, 223));

        jLabel1.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 51, 102));
        jLabel1.setText("Product Name:");

        jLabel2.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 51, 102));
        jLabel2.setText("Price:");

        jLabel3.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 51, 102));
        jLabel3.setText("Quantity:");

        jLabel4.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 51, 102));
        jLabel4.setText("Supplier Name:");

        p_name.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                p_nameActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 51, 102));
        jLabel5.setText("Supplier Email Address:");

        jButton1.setBackground(new java.awt.Color(238, 238, 248));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(0, 51, 102));
        jButton1.setText("EDIT");
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

        jLabel11.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 51, 102));
        jLabel11.setText("Category:");

        category.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " ", "Solar Power Components", "Circuit Protection", "Enclosures and Distribution", "Mounting Components", "Electrical Cables and Wires", "Connectors and Cable Management", "Conduits and Accessories", "Junction Boxes", "Indicators", "Screws and Fasteners" }));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(s_emailadd, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 102, Short.MAX_VALUE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(46, 46, 46)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(category, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(p_price)
                            .addComponent(p_quantity, javax.swing.GroupLayout.DEFAULT_SIZE, 245, Short.MAX_VALUE)
                            .addComponent(s_name)
                            .addComponent(p_name))
                        .addGap(173, 173, 173)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButton3)
                            .addComponent(jButton1)
                            .addComponent(jButton2))))
                .addContainerGap(29, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(p_name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2))
                .addGap(8, 8, 8)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(p_price, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addGap(8, 8, 8)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(p_quantity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(jButton3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(category, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(s_name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(s_emailadd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(13, 13, 13))
        );

        stocktable.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        stocktable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Product Name", "Price", "Quantity", "Category", "Supplier Name", "Supplier Email Address", "Last Updated"
            }
        ));
        jScrollPane1.setViewportView(stocktable);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jLabel6.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 51, 102));
        jLabel6.setText("SEARCH");

        i_search_tbl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                i_search_tblActionPerformed(evt);
            }
        });
        i_search_tbl.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                i_search_tblKeyReleased(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 51, 102));
        jLabel7.setText("FROM");

        jLabel8.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 51, 102));
        jLabel8.setText("FILTER RESULTS");

        jLabel9.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 51, 102));
        jLabel9.setText("TO");

        searchcatergory.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Product Name", "Price", "Current Stock", "Supplier Name", "Email", " " }));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel6)
                .addGap(18, 18, 18)
                .addComponent(i_search_tbl, javax.swing.GroupLayout.PREFERRED_SIZE, 391, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(searchcatergory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(92, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(from, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(to, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel8))
                .addGap(137, 137, 137))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(i_search_tbl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(searchcatergory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7)
                    .addComponent(from, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(to, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout inventoryaccount_panelLayout = new javax.swing.GroupLayout(inventoryaccount_panel);
        inventoryaccount_panel.setLayout(inventoryaccount_panelLayout);
        inventoryaccount_panelLayout.setHorizontalGroup(
            inventoryaccount_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, inventoryaccount_panelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 677, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11))
            .addGroup(inventoryaccount_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        inventoryaccount_panelLayout.setVerticalGroup(
            inventoryaccount_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(inventoryaccount_panelLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(inventoryaccount_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 211, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(53, Short.MAX_VALUE))
        );

        jPanel3.add(inventoryaccount_panel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 80, 1290, 720));

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

    private void jToggleButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton1ActionPerformed
        InventoryPanel iapanel = new InventoryPanel()    ;
        jpload.jPanelLoader(inventoryaccount_panel, iapanel);// TODO add your handling code here:
    }//GEN-LAST:event_jToggleButton1ActionPerformed

    private void jToggleButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton2ActionPerformed
        PackagePanel ppanel = new PackagePanel();
        jpload.jPanelLoader(inventoryaccount_panel, ppanel);   // TODO add your handling code here:
    }//GEN-LAST:event_jToggleButton2ActionPerformed

    private void p_nameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_p_nameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_p_nameActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
         // Add or insert new employee code
         
         
         

String pname = p_name.getText();
String pprice = p_price.getText();
String pqty = p_quantity.getText();
String pcat = (String) category.getSelectedItem();
String sname = s_name.getText();
String seadd = s_emailadd.getText();


    try {
        // Validate required fields
        if (pname == null || pname.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Product name cannot be empty.");
            return;
        }
        if (pprice == null || pprice.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Price cannot be empty.");
            return;
        }
        if (pqty == null || pqty.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Quantity cannot be empty.");
            return;
        }
        if (pcat == null || pcat.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please select a category.");
            return;
        }
        if (sname == null || sname.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Supplier name cannot be empty.");
            return;
        }
        if (seadd == null || seadd.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Email address cannot be empty.");
            return;
        }

        // Validate email format
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern emailPattern = Pattern.compile(emailRegex);
        if (!emailPattern.matcher(seadd).matches()) {
            JOptionPane.showMessageDialog(null, "Invalid email format.");
            return;
        }

        // Validate that price and quantity are numbers
        BigDecimal price;
        int quantity;
        try {
            price = new BigDecimal(pprice);
            quantity = Integer.parseInt(pqty);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Price must be a number and Quantity must be an integer.");
            return;
        }

        // Database connection
        java.sql.Connection con = db.mycon();
        con.setAutoCommit(false); // Begin transaction

        // Check if category exists
        String checkCategorySql = "SELECT Category_ID FROM category WHERE Category_Name = ?";
        java.sql.PreparedStatement checkCategoryStmt = con.prepareStatement(checkCategorySql);
        checkCategoryStmt.setString(1, pcat);
        ResultSet categoryResult = checkCategoryStmt.executeQuery();

        int categoryId = 0;
        if (categoryResult.next()) {
            categoryId = categoryResult.getInt("Category_ID");
        } else {
            JOptionPane.showMessageDialog(null, "Selected category does not exist in the database.");
            return;
        }

        // Check if the product already exists
        String checkProductSql = "SELECT Product_ID FROM product_table WHERE Product_Name = ?";
        java.sql.PreparedStatement checkProductStmt = con.prepareStatement(checkProductSql);
        checkProductStmt.setString(1, pname);
        ResultSet productResult = checkProductStmt.executeQuery();

        if (productResult.next()) {
            JOptionPane.showMessageDialog(null, "This product already exists.");
            return;
        }

        // Insert product
        String productSql = "INSERT INTO product_table (Product_Name, Price, Category_ID) VALUES (?, ?, ?)";
        java.sql.PreparedStatement productStmt = con.prepareStatement(productSql, Statement.RETURN_GENERATED_KEYS);
        productStmt.setString(1, pname);
        productStmt.setBigDecimal(2, price);
        productStmt.setInt(3, categoryId);
        productStmt.executeUpdate();

        // Get the generated Product_ID
        ResultSet productKeys = productStmt.getGeneratedKeys();
        int productId = 0;
        if (productKeys.next()) {
            productId = productKeys.getInt(1);
        }

        // Insert supplier or get existing Supplier_ID
        String supplierSql = "INSERT INTO supplier_table (Supplier_Name, Email) VALUES (?, ?) " +
                             "ON DUPLICATE KEY UPDATE Supplier_ID=LAST_INSERT_ID(Supplier_ID)";
        java.sql.PreparedStatement supplierStmt = con.prepareStatement(supplierSql, Statement.RETURN_GENERATED_KEYS);
        supplierStmt.setString(1, sname);
        supplierStmt.setString(2, seadd);
        supplierStmt.executeUpdate();

        // Get the Supplier_ID
        ResultSet supplierKeys = supplierStmt.getGeneratedKeys();
        int supplierId = 0;
        if (supplierKeys.next()) {
            supplierId = supplierKeys.getInt(1);
        }

        // Insert stock
        String stockSql = "INSERT INTO stock_table (Product_ID, Supplier_ID, Current_Stock) VALUES (?, ?, ?)";
        java.sql.PreparedStatement stockStmt = con.prepareStatement(stockSql);
        stockStmt.setInt(1, productId);
        stockStmt.setInt(2, supplierId);
        stockStmt.setInt(3, quantity);
        stockStmt.executeUpdate();

        // Commit transaction
        con.commit();
        JOptionPane.showMessageDialog(null, "Product, Supplier, and Stock Data Saved.");
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error saving data: " + e.getMessage());
    } finally {
        tb_load(); // Reload table after insertion
    }

    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        
        p_name.setText("");
        p_price.setText("");
        p_quantity.setText("");
        s_name.setText("");
        s_emailadd.setText("");  
        
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
                                    
    // TODO add your handling code here:
    DefaultTableModel tblModel = (DefaultTableModel) stocktable.getModel();
    
    // Check if exactly one row is selected
    int selectedRow = stocktable.getSelectedRow();
    if (selectedRow == -1) {
        if (stocktable.getRowCount() == 0) {
            // If table is empty
            JOptionPane.showMessageDialog(this, "Table is empty.");
        } else {
            // If no row is selected
            JOptionPane.showMessageDialog(this, "Please select a row for update.");
        }
    } else {
        // Retrieve values from text fields
        String ProductName = p_name.getText();
        String ProductPrice = p_price.getText();
        String CurrentStock = p_quantity.getText();
        String SupplierName = s_name.getText();
        String SupplierEmail = s_emailadd.getText();
        
        // Optional: Validate if all fields are filled in (if needed)
        if (ProductName.isEmpty() || ProductPrice.isEmpty() || CurrentStock.isEmpty() || SupplierName.isEmpty() || SupplierEmail.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields before updating.");
        } else {
            // Update the table with new values
            tblModel.setValueAt(ProductName, selectedRow, 1); // Corrected column index
            tblModel.setValueAt(ProductPrice, selectedRow, 2); // Corrected column index
            tblModel.setValueAt(CurrentStock, selectedRow, 3); // Corrected column index
            tblModel.setValueAt(SupplierName, selectedRow, 4); // Corrected column index
            tblModel.setValueAt(SupplierEmail, selectedRow, 5); // Corrected column index
            
            // Display success message
            JOptionPane.showMessageDialog(this, "Update Successful.");
        }
    }         
    }//GEN-LAST:event_jButton1ActionPerformed

    private void i_search_tblKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_i_search_tblKeyReleased
        
    }//GEN-LAST:event_i_search_tblKeyReleased

    private void stocktableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_stocktableMouseClicked
         // TODO add your handling code here:
           // TODO add your handling code here:                            
 
    // Get the selected row index
    DefaultTableModel tblModel = (DefaultTableModel) stocktable.getModel();
    
    // Ensure that the user has selected a row
    int selectedRow = stocktable.getSelectedRow();
    
   
        // Retrieve values from the selected row
        String ProductName = tblModel.getValueAt(selectedRow, 1).toString(); // Column 1: Product Name
        String ProductPrice = tblModel.getValueAt(selectedRow, 2).toString(); // Column 2: Product Price
        String CurrentStock = tblModel.getValueAt(selectedRow, 3).toString(); // Column 3: Current Stock
        String SupplierName = tblModel.getValueAt(selectedRow, 4).toString(); // Column 4: Supplier Name
        String SupplierEmail = tblModel.getValueAt(selectedRow, 5).toString(); // Column 5: Supplier Email
        
        // Set the text of the respective text fields with the retrieved data
        p_name.setText(ProductName);        // Set Product Name to p_name text field
        p_price.setText(ProductPrice);      // Set Product Price to p_price text field
        p_quantity.setText(CurrentStock);   // Set Current Stock to p_quantity text field
        s_name.setText(SupplierName);       // Set Supplier Name to s_name text field
        s_emailadd.setText(SupplierEmail);  // Set Supplier Email to s_emailadd text field
    
         
    }//GEN-LAST:event_stocktableMouseClicked

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
 
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void i_search_tblActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_i_search_tblActionPerformed
        // TODO add your handling code here:
       
   
    String searchQuery = i_search_tbl.getText().trim();

    // Get the selected column from the JComboBox
    String searchByDisplayName = searchcatergory.getSelectedItem().toString();

    // Map displayed names to actual column names
    Map<String, String> columnMap = new HashMap<>();
    columnMap.put("Product Name", "pt.product_name");
    columnMap.put("Price", "pt.price");
    columnMap.put("Current Stock", "st.current_stock");
    columnMap.put("Supplier Name", "sup.supplier_name");
    columnMap.put("Email", "sup.email");

    // Get the actual column name
    String searchBy = columnMap.get(searchByDisplayName);

    // Check if the search term or column is invalid
    if (searchQuery.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Enter a search term!");
        return;
    }
    if (searchBy == null) {
        JOptionPane.showMessageDialog(this, "Invalid search field selected!");
        return;
    }

    // SQL Query with Joins
    String query = "SELECT st.stock_id, pt.product_name, pt.price, st.current_stock, "
                 + "sup.supplier_name, sup.email, st.last_updated "
                 + "FROM stock_table st "
                 + "JOIN product_table pt ON st.product_id = pt.product_id "
                 + "JOIN supplier_table sup ON st.supplier_id = sup.supplier_id "
                 + "WHERE " + searchBy + " LIKE ?";

    try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/inventory", "root", "");
         PreparedStatement stmt = conn.prepareStatement(query)) {

        // Set the search parameter
        stmt.setString(1, "%" + searchQuery + "%");

        // Execute the query and process results
        ResultSet rs = stmt.executeQuery();

        // Get the table model and clear previous rows
        DefaultTableModel model = (DefaultTableModel) stocktable.getModel();
        model.setRowCount(0); // Clear existing data

        // Populate table with results
        while (rs.next()) {
            model.addRow(new Object[]{
                false, // Checkbox (unchecked)
                rs.getString("product_name"),
                rs.getString("price"),
                rs.getString("current_stock"),
                rs.getString("supplier_name"),
                rs.getString("email"),
                rs.getString("last_updated")
            });
        }

        // If no results are found, show a message
        if (model.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "No results found!");
        }

    } catch (SQLException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
    }

    }//GEN-LAST:event_i_search_tblActionPerformed

    private void fromMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fromMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_fromMouseClicked

    private void toMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_toMouseClicked

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
                 
    // Get the selected dates from the date pickers
    Date fromDate = from.getDate(); // JDateChooser for "From" date
    Date toDate = to.getDate();     // JDateChooser for "To" date

    // Validate the date range
    if (fromDate == null || toDate == null) {
        JOptionPane.showMessageDialog(this, "Please select both From and To dates!");
        return;
    }
    if (fromDate.after(toDate)) {
        JOptionPane.showMessageDialog(this, "From date cannot be after To date!");
        return;
    }

    // Convert dates to Timestamps
    // Convert java.util.Date to java.sql.Timestamp for SQL query
java.sql.Timestamp sqlFromDate = new java.sql.Timestamp(fromDate.getTime());
java.sql.Timestamp sqlToDate = new java.sql.Timestamp(toDate.getTime() + (24 * 60 * 60 * 1000) - 1);


    // SQL query for filtering by date range
    String query = "SELECT st.stock_id, pt.product_name, pt.price, st.current_stock, "
                 + "sup.supplier_name, sup.email, st.last_updated "
                 + "FROM stock_table st "
                 + "JOIN product_table pt ON st.product_id = pt.product_id "
                 + "JOIN supplier_table sup ON st.supplier_id = sup.supplier_id "
                 + "WHERE st.last_updated BETWEEN ? AND ?";

    try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/inventory", "root", "");
         PreparedStatement stmt = conn.prepareStatement(query)) {

        // Set the date parameters in the query using Timestamps
        stmt.setTimestamp(1, sqlFromDate);
        stmt.setTimestamp(2, sqlToDate);

        // Execute the query and process results
        ResultSet rs = stmt.executeQuery();

        // Get the table model and clear previous rows
        DefaultTableModel model = (DefaultTableModel) stocktable.getModel();
        model.setRowCount(0); // Clear existing data

        // Populate table with results
        while (rs.next()) {
            model.addRow(new Object[]{
                false, // Checkbox (unchecked)
                rs.getString("product_name"),
                rs.getString("price"),
                rs.getString("current_stock"),
                rs.getString("supplier_name"),
                rs.getString("email"),
                rs.getTimestamp("last_updated") // Display last_updated as Timestamp
            });
        }

        // If no results are found, show a message
        if (model.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "No results found!");
        }

    } catch (SQLException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
    }


    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
  
    // SQL Query to fetch all stock quantities and product names
    String query = "SELECT pt.product_name, st.current_stock "
                 + "FROM stock_table st "
                 + "JOIN product_table pt ON st.product_id = pt.product_id";

    try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/inventory", "root", "");
         PreparedStatement stmt = conn.prepareStatement(query)) {

        // Execute the query
        ResultSet rs = stmt.executeQuery();

        // Variables to store data for overview
        int totalStock = 0;
        int maxStock = Integer.MIN_VALUE;
        int minStock = Integer.MAX_VALUE;
        String maxStockItem = "";
        String minStockItem = "";

        // Process the result set to calculate total, max, and min stock
        while (rs.next()) {
            String productName = rs.getString("product_name");
            int currentStock = rs.getInt("current_stock");

            // Add to total stock
            totalStock += currentStock;

            // Check for max stock
            if (currentStock > maxStock) {
                maxStock = currentStock;
                maxStockItem = productName;
            }

            // Check for min stock
            if (currentStock < minStock) {
                minStock = currentStock;
                minStockItem = productName;
            }
        }

        // Display the overview to the user
        String overviewMessage = String.format("Total Stock Inventory: %d\n", totalStock)
                + String.format("Item with the highest stock: %s (%d)\n", maxStockItem, maxStock)
                + String.format("Item with the lowest stock: %s (%d)\n", minStockItem, minStock);

        JOptionPane.showMessageDialog(this, overviewMessage);

    } catch (SQLException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
    }

    

    }//GEN-LAST:event_jButton5ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> category;
    private com.toedter.calendar.JDateChooser from;
    private javax.swing.JTextField i_search_tbl;
    private javax.swing.JPanel inventoryaccount_panel;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
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
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JToggleButton jToggleButton2;
    private javax.swing.JTextField p_name;
    private javax.swing.JTextField p_price;
    private javax.swing.JTextField p_quantity;
    private javax.swing.JTextField s_emailadd;
    private javax.swing.JTextField s_name;
    private javax.swing.JComboBox<String> searchcatergory;
    private javax.swing.JTable stocktable;
    private com.toedter.calendar.JDateChooser to;
    // End of variables declaration//GEN-END:variables
}
