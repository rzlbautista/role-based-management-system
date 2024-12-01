/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Interface;
import java.sql.ResultSet;
import java.math.BigDecimal;
import java.security.Timestamp;
import java.sql.Connection;
import javax.swing.JOptionPane;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.regex.Pattern;
import java.util.regex.*;
import java.util.regex.Matcher;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import java.util.logging.Logger;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import java.util.Arrays;


/**
 *
 * @author Renniel
 */
public class PackagePanel extends javax.swing.JPanel {

    /**
     * Creates new form InventoryAccountPanel
     */
    public PackagePanel() {
        initComponents();
        tb_load();
        table_load();
         
    }
    
  

 private void tb_load() {
    DefaultTableModel model = (DefaultTableModel) product.getModel();
    model.setRowCount(0);  // Clear existing rows

    try {
        java.sql.Connection con = db.mycon();
        String query =  """
                SELECT p.product_name, p.price, c.category_name
                FROM product_table p
                JOIN category c ON p.category_id = c.category_id
            """;
        java.sql.PreparedStatement stmt = con.prepareStatement(query);
        java.sql.ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            // Get employee data
            String pname = rs.getString("p.product_name");
            String pprice = rs.getString("p.price");
            String category = rs.getString("c.category_name");

            // Add checkbox column (unchecked by default)
            model.addRow(new Object[]{pname,pprice, category});
        }

    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error loading data: " + e.getMessage());
    }
 
 
 }
 
 
 private void filterTable(String searchText) {
    DefaultTableModel model = (DefaultTableModel) product.getModel();
    model.setRowCount(0);  // Clear existing rows

    try {
        java.sql.Connection con = db.mycon();
        String query = """
                SELECT p.product_name, p.price, c.category_name
                FROM product_table p
                JOIN category c ON p.category_id = c.category_id
                WHERE p.product_name LIKE ? OR c.category_name LIKE ?
            """;
        java.sql.PreparedStatement stmt = con.prepareStatement(query);
        stmt.setString(1, "%" + searchText + "%");
        stmt.setString(2, "%" + searchText + "%");
        java.sql.ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            String pname = rs.getString("product_name");
            String pprice = rs.getString("price");
            String category = rs.getString("category_name");

            model.addRow(new Object[]{pname, pprice, category});
        }

    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error filtering data: " + e.getMessage());
    }
}

 
private void Packagefilter(String searchPackage) {
    DefaultTableModel model = (DefaultTableModel) package_table.getModel();
    model.setRowCount(0); // Clear existing rows

    try {
        java.sql.Connection con = db.mycon();
        String query = """
           SELECT 
               pp.package_option,
               GROUP_CONCAT(p.product_name SEPARATOR ', ') AS product_names,
               pp.unit,
               pp.unit_price,
               pp.total_package_price,
               pp.created_at
           FROM 
               package_items pi
           JOIN 
               product_table p ON pi.product_id = p.product_id
           JOIN 
               product_package pp ON pi.package_id = pp.package_id
           WHERE 
               pp.package_option LIKE ? OR p.product_name LIKE ?
           GROUP BY 
               pi.package_id
           ORDER BY 
               pi.package_id ASC;
        """;

        java.sql.PreparedStatement stmt = con.prepareStatement(query);
        stmt.setString(1, "%" + searchPackage + "%");
        stmt.setString(2, "%" + searchPackage + "%");
        java.sql.ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            String category = rs.getString("package_option");
            String pname = rs.getString("product_names");
            String pprice = rs.getString("unit");
            String uprice = rs.getString("unit_price");
            String tprice = rs.getString("total_package_price");
            String time = rs.getString("created_at");

            model.addRow(new Object[]{category, pname, pprice, uprice, tprice, time});
        }

    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error filtering data: " + e.getMessage());
    }
}

 // Key Released Event for Calculating Total Price
private void selectedProductsFieldKeyReleased(java.awt.event.KeyEvent evt) {
    String[] productNames = pc_item.getText().trim().split(",");
    String unitType = (String) pc_unit.getSelectedItem();
    double totalUnitPrice = 0.0;

    try (java.sql.Connection con = db.mycon()) {
        String query = "SELECT price FROM product_table WHERE product_name = ?";
        java.sql.PreparedStatement stmt = con.prepareStatement(query);

        for (String productName : productNames) {
            stmt.setString(1, productName.trim());
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                double price = rs.getDouble("price");
                totalUnitPrice += price; // Sum up prices for selected products
            } else {
                JOptionPane.showMessageDialog(null, "Product not found: " + productName);
            }
        }

        // Apply multiplier based on unit type
        int multiplier = unitType.equals("set") ? 10 : 1;
        totalUnitPrice *= multiplier;

        // Display the calculated unit price
        pc_price.setText(String.format("%.2f", totalUnitPrice));

    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error calculating price: " + e.getMessage());
    }
}

// Save Package Button Click Event
private void savePackageButtonActionPerformed(java.awt.event.ActionEvent evt) {
    String optionName = pc_name.getText().trim();
    String[] productNames = pc_item.getText().trim().split(",");
    String unitType = (String) pc_unit.getSelectedItem();
    double totalPrice = Double.parseDouble(pc_price.getText().trim());

    if (optionName.isEmpty() || productNames.length == 0 || unitType.isEmpty()) {
        JOptionPane.showMessageDialog(null, "Please fill in all fields.");
        return;
    }

    try (java.sql.Connection con = db.mycon()) {
        // Insert package into product_package
        String packageQuery = """
            INSERT INTO product_package (package_option, unit, unit_price, total_package_price, created_at)
            VALUES (?, ?, ?, ?, ?)
        """;
        java.sql.PreparedStatement packageStmt = con.prepareStatement(packageQuery, Statement.RETURN_GENERATED_KEYS);
        packageStmt.setString(1, optionName);
        packageStmt.setString(2, unitType);
        packageStmt.setDouble(3, totalPrice);
        packageStmt.setDouble(4, totalPrice);
        packageStmt.setTimestamp(5, new java.sql.Timestamp(System.currentTimeMillis()));
        packageStmt.executeUpdate();

        // Get generated package ID
        ResultSet rs = packageStmt.getGeneratedKeys();
        if (!rs.next()) {
            JOptionPane.showMessageDialog(null, "Failed to create package.");
            return;
        }
        int packageId = rs.getInt(1);

        // Insert package items
        String itemQuery = "INSERT INTO package_items (package_id, product_id) VALUES (?, ?)";
        java.sql.PreparedStatement itemStmt = con.prepareStatement(itemQuery);

        for (String productName : productNames) {
            int productId = getProductIdFromName(productName.trim(), con);
            if (productId == -1) {
                JOptionPane.showMessageDialog(null, "Product ID not found for: " + productName);
                continue;
            }
            itemStmt.setInt(1, packageId);
            itemStmt.setInt(2, productId);
            itemStmt.addBatch();
        }

        int[] results = itemStmt.executeBatch();
        JOptionPane.showMessageDialog(null, "Package created successfully with " + results.length + " items.");

        // Reset fields
        pc_name.setText("");
        pc_item.setText("");
        pc_price.setText("");

    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error saving package: " + e.getMessage());
    }
}

// Helper Method: Get Product ID by Name
private int getProductIdFromName(String productName, java.sql.Connection con) throws SQLException {
    String query = "SELECT product_id FROM product_table WHERE product_name = ?";
    java.sql.PreparedStatement stmt = con.prepareStatement(query);
    stmt.setString(1, productName);
    ResultSet rs = stmt.executeQuery();
    if (rs.next()) {
        return rs.getInt("product_id");
    }
    return -1; // Return -1 if product not found
}


private void table_load() {
    DefaultTableModel model = (DefaultTableModel) package_table.getModel();
    model.setRowCount(0);  // Clear existing rows

    try {
        java.sql.Connection con = db.mycon();
        String query = """
           SELECT 
               pp.package_option,
               GROUP_CONCAT(p.product_name SEPARATOR ', ') AS product_names,
               pp.unit,
               pp.unit_price,
               pp.total_package_price,
               pp.created_at
           FROM 
               package_items pi
           JOIN 
               product_table p ON pi.product_id = p.product_id
           JOIN 
               product_package pp ON pi.package_id = pp.package_id
           GROUP BY 
               pi.package_id
           ORDER BY 
               pi.package_id ASC;
        """;

        java.sql.PreparedStatement stmt = con.prepareStatement(query);
        java.sql.ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            String packageOption = rs.getString("package_option");
            String productNames = rs.getString("product_names");
            String unit = rs.getString("unit");
            double unitPrice = rs.getDouble("unit_price");
            double totalPrice = rs.getDouble("total_package_price");
            java.sql.Timestamp createdAt = rs.getTimestamp("created_at");

            model.addRow(new Object[]{packageOption, productNames, unit, unitPrice, totalPrice, createdAt});
        }

    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error loading data: " + e.getMessage());
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
        inventoryaccount_panel = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        pc_name = new javax.swing.JTextField();
        pc_price = new javax.swing.JTextField();
        pc_unit = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        pc_item = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        package_table = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        searchField = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        product = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        search_package = new javax.swing.JTextField();
        jButton5 = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(1290, 800));

        jPanel3.setPreferredSize(new java.awt.Dimension(1290, 800));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        inventoryaccount_panel.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(255, 238, 238));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.setPreferredSize(new java.awt.Dimension(1290, 223));

        jLabel1.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 51, 102));
        jLabel1.setText("Option Name:");

        jLabel2.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 51, 102));
        jLabel2.setText("Input Items on Package");

        jLabel4.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 51, 102));
        jLabel4.setText("Unit Price:");

        pc_name.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pc_nameActionPerformed(evt);
            }
        });
        pc_name.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                pc_nameKeyReleased(evt);
            }
        });

        pc_price.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pc_priceActionPerformed(evt);
            }
        });
        pc_price.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                pc_priceKeyReleased(evt);
            }
        });

        pc_unit.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SET", "EA", " " }));
        pc_unit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pc_unitActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 51, 102));
        jLabel8.setText("Unit:");

        jButton2.setBackground(new java.awt.Color(238, 238, 248));
        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton2.setForeground(new java.awt.Color(0, 51, 102));
        jButton2.setText("ADD");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        pc_item.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                pc_itemKeyReleased(evt);
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

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(jLabel2)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(pc_item, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(46, 46, 46)
                            .addComponent(pc_name))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(pc_unit, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(jButton3)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jButton2))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                            .addComponent(jLabel4)
                            .addGap(18, 18, 18)
                            .addComponent(pc_price, javax.swing.GroupLayout.PREFERRED_SIZE, 398, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(27, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(pc_name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(pc_item, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(13, 13, 13)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(pc_unit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(pc_price, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        package_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Package Option", "Product Names", "Unit", "Unit Price", "Total Price", "Created At"
            }
        ));
        jScrollPane1.setViewportView(package_table);

        jLabel6.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 51, 102));
        jLabel6.setText("SEARCH");

        jButton4.setBackground(new java.awt.Color(238, 238, 248));
        jButton4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton4.setForeground(new java.awt.Color(0, 51, 102));
        jButton4.setText("DELETE");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(234, 255, 244));

        searchField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchFieldActionPerformed(evt);
            }
        });
        searchField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchFieldKeyReleased(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 51, 102));
        jLabel3.setText("Search Product:");

        product.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Product Name", "Price", "Category"
            }
        ));
        product.setDragEnabled(true);
        jScrollPane2.setViewportView(product);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(searchField, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 641, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jButton1.setBackground(new java.awt.Color(238, 238, 248));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(0, 51, 102));
        jButton1.setText("UPDATE");

        search_package.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                search_packageActionPerformed(evt);
            }
        });
        search_package.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                search_packageKeyReleased(evt);
            }
        });

        jButton5.setBackground(new java.awt.Color(238, 238, 248));
        jButton5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton5.setForeground(new java.awt.Color(0, 51, 102));
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGE/search 25 x 25.png"))); // NOI18N
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout inventoryaccount_panelLayout = new javax.swing.GroupLayout(inventoryaccount_panel);
        inventoryaccount_panel.setLayout(inventoryaccount_panelLayout);
        inventoryaccount_panelLayout.setHorizontalGroup(
            inventoryaccount_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(inventoryaccount_panelLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(inventoryaccount_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1261, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(inventoryaccount_panelLayout.createSequentialGroup()
                        .addGroup(inventoryaccount_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(inventoryaccount_panelLayout.createSequentialGroup()
                                .addGap(5, 5, 5)
                                .addGroup(inventoryaccount_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton1)
                                    .addGroup(inventoryaccount_panelLayout.createSequentialGroup()
                                        .addComponent(jLabel6)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(search_package, javax.swing.GroupLayout.PREFERRED_SIZE, 398, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jButton4))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton5))
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 515, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 85, Short.MAX_VALUE)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        inventoryaccount_panelLayout.setVerticalGroup(
            inventoryaccount_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(inventoryaccount_panelLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(inventoryaccount_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(inventoryaccount_panelLayout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE))
                    .addGroup(inventoryaccount_panelLayout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1)
                        .addGap(18, 18, 18)
                        .addGroup(inventoryaccount_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, inventoryaccount_panelLayout.createSequentialGroup()
                                .addComponent(jButton4)
                                .addGap(36, 36, 36)
                                .addGroup(inventoryaccount_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel6)
                                    .addComponent(search_package, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jButton5, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(26, 26, 26)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 364, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel3.add(inventoryaccount_panel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1290, 800));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 686, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 114, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void pc_nameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pc_nameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_pc_nameActionPerformed

    private void pc_unitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pc_unitActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_pc_unitActionPerformed

    private void pc_nameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_pc_nameKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_pc_nameKeyReleased

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
 String optionName = pc_name.getText().trim();
    String[] productNames = pc_item.getText().trim().split(",");
    String unitType = (String) pc_unit.getSelectedItem();
    double totalPrice = Double.parseDouble(pc_price.getText().trim());

    if (optionName.isEmpty() || productNames.length == 0 || unitType.isEmpty()) {
        JOptionPane.showMessageDialog(null, "Please fill in all fields.");
        return;
    }

    try (java.sql.Connection con = db.mycon()) {
        // Insert package into product_package
        String packageQuery = """
            INSERT INTO product_package (package_option, unit, unit_price, total_package_price, created_at)
            VALUES (?, ?, ?, ?, ?)
        """;
        java.sql.PreparedStatement packageStmt = con.prepareStatement(packageQuery, Statement.RETURN_GENERATED_KEYS);
        packageStmt.setString(1, optionName);
        packageStmt.setString(2, unitType);
        packageStmt.setDouble(3, totalPrice);
        packageStmt.setDouble(4, totalPrice);
        packageStmt.setTimestamp(5, new java.sql.Timestamp(System.currentTimeMillis()));
        packageStmt.executeUpdate();

        // Get generated package ID
        ResultSet rs = packageStmt.getGeneratedKeys();
        if (!rs.next()) {
            JOptionPane.showMessageDialog(null, "Failed to create package.");
            return;
        }
        int packageId = rs.getInt(1);

        // Insert package items
        String itemQuery = "INSERT INTO package_items (package_id, product_id) VALUES (?, ?)";
        java.sql.PreparedStatement itemStmt = con.prepareStatement(itemQuery);

        for (String productName : productNames) {
            int productId = getProductIdFromName(productName.trim(), con);
            if (productId == -1) {
                JOptionPane.showMessageDialog(null, "Product ID not found for: " + productName);
                continue;
            }
            itemStmt.setInt(1, packageId);
            itemStmt.setInt(2, productId);
            itemStmt.addBatch();
        }

        int[] results = itemStmt.executeBatch();
        JOptionPane.showMessageDialog(null, "Package created successfully with " + results.length + " items.");

        // Reset fields
        pc_name.setText("");
        pc_item.setText("");
        pc_price.setText("");

    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error saving package: " + e.getMessage());
    }   
    
    
    


    }//GEN-LAST:event_jButton2ActionPerformed

    private void pc_priceKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_pc_priceKeyReleased
String[] productNames = pc_item.getText().trim().split(",");
    String unitType = (String) pc_unit.getSelectedItem();
    double totalUnitPrice = 0.0;

    try (java.sql.Connection con = db.mycon()) {
        String query = "SELECT price FROM product_table WHERE product_name = ?";
        java.sql.PreparedStatement stmt = con.prepareStatement(query);

        for (String productName : productNames) {
            stmt.setString(1, productName.trim());
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                double price = rs.getDouble("price");
                totalUnitPrice += price; // Sum up prices for selected products
            } else {
                JOptionPane.showMessageDialog(null, "Product not found: " + productName);
            }
        }

        // Apply multiplier based on unit type
        int multiplier = unitType.equals("set") ? 10 : 1;
        totalUnitPrice *= multiplier;

        // Display the calculated unit price
        pc_price.setText(String.format("%.2f", totalUnitPrice));

    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error calculating price: " + e.getMessage());
    }   
    }//GEN-LAST:event_pc_priceKeyReleased

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
           
        pc_name.setText("");
        pc_item.setText("");
        pc_unit.setSelectedItem("");
        pc_price.setText("");
    
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed

    private void pc_itemKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_pc_itemKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_pc_itemKeyReleased

    private void search_packageKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_search_packageKeyReleased
        // TODO add your handling code here:   String searchText = searchField.getText().trim();
         String search = search_package.getText().trim();

    if (search.isEmpty()) {
        table_load(); // Reload the original content if the search text is empty
    } else {
        Packagefilter(search); // Filter table content
    }
    }//GEN-LAST:event_search_packageKeyReleased

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton5ActionPerformed

    private void searchFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchFieldKeyReleased
        // TODO add your handling code here:
         String searchText = searchField.getText().trim();

    if (searchText.isEmpty()) {
        tb_load(); // Reload the original content if the search text is empty
    } else {
        filterTable(searchText); // Filter table content
    }
        
        
    }//GEN-LAST:event_searchFieldKeyReleased

    private void pc_priceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pc_priceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_pc_priceActionPerformed

    private void search_packageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_search_packageActionPerformed
        // TODO add your handling code here:
        
        
        
    }//GEN-LAST:event_search_packageActionPerformed

    private void searchFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchFieldActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel inventoryaccount_panel;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable package_table;
    private javax.swing.JTextField pc_item;
    private javax.swing.JTextField pc_name;
    private javax.swing.JTextField pc_price;
    private javax.swing.JComboBox<String> pc_unit;
    private javax.swing.JTable product;
    private javax.swing.JTextField searchField;
    private javax.swing.JTextField search_package;
    // End of variables declaration//GEN-END:variables

  
}
