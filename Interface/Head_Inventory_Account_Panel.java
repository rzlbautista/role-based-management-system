/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Interface;
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

/**
 *
 * @author Renniel
 */
public class Head_Inventory_Account_Panel extends javax.swing.JPanel {
    JpanelLoader jpload = new JpanelLoader();

    /**
     * Creates new form InventoryAccountPanel
     */
    
    
    public Head_Inventory_Account_Panel() {
        initComponents();
        tb_load();
    }
    
private void tb_load() {
        // Define the table model with a checkbox (Boolean) column in the first position
        DefaultTableModel model = new DefaultTableModel(new Object[]{"Select", "Product Name", "Price", "Current Stock", "Supplier Name", "Supplier Email", "Last Updated"}, 0) {
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
            String query = "SELECT p.Product_Name, p.Price, st.Current_Stock, s.Supplier_Name, s.Email, st.Last_Updated " +
                           "FROM stock_table st " +
                           "JOIN product_table p ON st.Product_ID = p.Product_ID " +
                           "JOIN supplier_table s ON st.Supplier_ID = s.Supplier_ID";

            java.sql.PreparedStatement stmt = con.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            // Loop through the result set and add rows to the table model
            while (rs.next()) {
                String productName = rs.getString("Product_Name");
                BigDecimal price = rs.getBigDecimal("Price");
                int currentStock = rs.getInt("Current_Stock");
                String supplierName = rs.getString("Supplier_Name");
                String supplierEmail = rs.getString("Email");
                java.sql.Timestamp lastUpdated = rs.getTimestamp("Last_Updated");

                // Add a new row with a checkbox in the first column
                model.addRow(new Object[]{false, productName, price, currentStock, supplierName, supplierEmail, lastUpdated});
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


    
    private void deleteSelectedRows() {
    DefaultTableModel model = (DefaultTableModel) stocktable.getModel();
    int rowCount = model.getRowCount();
    
    boolean atLeastOneChecked = false;

    // Loop through the rows to check which rows are selected
    for (int i = 0; i < rowCount; i++) {
        boolean isChecked = (boolean) model.getValueAt(i, 0);  // First column is checkbox
        if (isChecked) {
            String productName = model.getValueAt(i, 1).toString();  // Product Name
            String supplierName = model.getValueAt(i, 4).toString();  // Supplier Name
            
            // Confirmation prompt before deletion
            int confirm = JOptionPane.showConfirmDialog(null,
                    "Are you sure you want to delete this product: " + productName + " from supplier " + supplierName + "?",
                    "Confirm Deletion",
                    JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                try (java.sql.Connection connection = db.mycon()) {
                    // Archive the product data before deleting
                    String archiveQuery = "INSERT INTO stock_archive (Product_Name, Price, Current_Stock, Supplier_Name, email) " +
                                          "SELECT p.Product_Name, p.Price, st.Current_Stock, s.Supplier_Name, s.Email " +
                                          "FROM stock_table st " +
                                          "JOIN product_table p ON st.Product_ID = p.Product_ID " +
                                          "JOIN supplier_table s ON st.Supplier_ID = s.Supplier_ID " +
                                          "WHERE p.Product_Name = ? AND s.Supplier_Name = ?";
                    try (java.sql.PreparedStatement archiveStmt = connection.prepareStatement(archiveQuery)) {
                        archiveStmt.setString(1, productName);  // Set product name for archiving
                        archiveStmt.setString(2, supplierName);  // Set supplier name for archiving
                        archiveStmt.executeUpdate();
                    }

                    // Now delete the product from stock_table using JOIN
                    String deleteQuery = "DELETE st " +
                                         "FROM stock_table st " +
                                         "JOIN product_table p ON st.Product_ID = p.Product_ID " +
                                         "JOIN supplier_table s ON st.Supplier_ID = s.Supplier_ID " +
                                         "WHERE p.Product_Name = ? AND s.Supplier_Name = ?";
                    try (java.sql.PreparedStatement deleteStmt = connection.prepareStatement(deleteQuery)) {
                        deleteStmt.setString(1, productName);  // Set the product name
                        deleteStmt.setString(2, supplierName);  // Set the supplier name
                        deleteStmt.executeUpdate();
                        JOptionPane.showMessageDialog(null, "Product archived and deleted successfully.");
                    }
                } catch (java.sql.SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error occurred while archiving or deleting: " + ex.getMessage());
                }
            }
        }
    }

    if (!atLeastOneChecked) {
        JOptionPane.showMessageDialog(null, "Please select at least one product to delete.");
    }

    tb_load();  // Reload the table to reflect the deletion
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
        jToggleButton3 = new javax.swing.JToggleButton();
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
        jButton4 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        stocktable = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        i_search_tbl = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jLabel8 = new javax.swing.JLabel();
        jDateChooser2 = new com.toedter.calendar.JDateChooser();
        jLabel9 = new javax.swing.JLabel();

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

        jToggleButton3.setText("Category");
        jToggleButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jToggleButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jToggleButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jToggleButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(946, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(30, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jToggleButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jToggleButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jToggleButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(46, 46, 46))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(p_price)
                    .addComponent(p_quantity, javax.swing.GroupLayout.DEFAULT_SIZE, 245, Short.MAX_VALUE)
                    .addComponent(s_name)
                    .addComponent(s_emailadd)
                    .addComponent(p_name))
                .addGap(173, 173, 173)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton3)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton4))
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
                .addGap(8, 8, 8)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(s_name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jButton4))
                .addGap(8, 8, 8)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(s_emailadd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addContainerGap(54, Short.MAX_VALUE))
        );

        stocktable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Product Name", "Price", "Quantity", "Supplier Name", "Supplier Email Address", "Last Updated"
            }
        ));
        stocktable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                stocktableMouseClicked(evt);
            }
        });
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

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel6)
                .addGap(18, 18, 18)
                .addComponent(i_search_tbl, javax.swing.GroupLayout.PREFERRED_SIZE, 391, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 170, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                            .addGap(237, 237, 237)
                            .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                            .addComponent(jLabel7)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jLabel9)
                            .addGap(150, 150, 150))))
                .addGap(63, 63, 63))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(i_search_tbl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 135, Short.MAX_VALUE)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING))
                    .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addGap(4, 4, 4))
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
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1257, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        inventoryaccount_panelLayout.setVerticalGroup(
            inventoryaccount_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(inventoryaccount_panelLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(inventoryaccount_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 217, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 456, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
String sname = s_name.getText();
String seadd = s_emailadd.getText();


try {
    // Check if required fields are empty
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
    if (sname == null || sname.trim().isEmpty()) {
        JOptionPane.showMessageDialog(null, "Supplier name cannot be empty.");
        return;
    }

    // Validate email format
    if (seadd == null || seadd.trim().isEmpty()) {
        JOptionPane.showMessageDialog(null, "Email address cannot be empty.");
        return;
    }

    String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    Pattern emailPattern = Pattern.compile(emailRegex);
    Matcher matcher = emailPattern.matcher(seadd);

    if (!matcher.matches()) {
        JOptionPane.showMessageDialog(null, "Invalid email format. Please enter a valid email address.");
        return;
    }

    // Validate that Price and Quantity are numbers
    try {
        Double.parseDouble(pprice); // Check if the price is a valid number
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(null, "Price must be a valid number.");
        return;
    }

    try {
        Integer.parseInt(pqty); // Check if the quantity is a valid integer
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(null, "Quantity must be a valid integer.");
        return;
    }

    // Initialize connection
    java.sql.Connection con = db.mycon();
    con.setAutoCommit(false);

    // Check if the product name already exists in product_table
    String checkProductSql = "SELECT Product_ID FROM product_table WHERE Product_Name = ?";
    java.sql.PreparedStatement checkProductStmt = con.prepareStatement(checkProductSql);
    checkProductStmt.setString(1, pname);
    ResultSet productExistsResult = checkProductStmt.executeQuery();

    if (productExistsResult.next()) {
        JOptionPane.showMessageDialog(null, "This product already exists.");
        return;  // Exit the method if the product already exists
    }

    // Insert product data into product_table
    String productSql = "INSERT INTO product_table (Product_Name, Price) VALUES (?, ?)";
    java.sql.PreparedStatement productStmt = con.prepareStatement(productSql, Statement.RETURN_GENERATED_KEYS);
    productStmt.setString(1, pname);
    productStmt.setBigDecimal(2, new BigDecimal(pprice));
    productStmt.executeUpdate();

    // Retrieve the generated Product_ID
    ResultSet productKeys = productStmt.getGeneratedKeys();
    int productId = 0;
    if (productKeys.next()) {
        productId = productKeys.getInt(1);  // Get the generated Product_ID
    }

    // Insert supplier data or get existing supplier ID if it already exists
    String supplierSql = "INSERT INTO supplier_table (Supplier_Name, Email) VALUES (?, ?) " +
                         "ON DUPLICATE KEY UPDATE Supplier_ID=LAST_INSERT_ID(Supplier_ID)";
    java.sql.PreparedStatement supplierStmt = con.prepareStatement(supplierSql, Statement.RETURN_GENERATED_KEYS);
    supplierStmt.setString(1, sname);
    supplierStmt.setString(2, seadd);
    supplierStmt.executeUpdate();

    // Retrieve the Supplier_ID, even if it was a duplicate
    ResultSet supplierKeys = supplierStmt.getGeneratedKeys();
    int supplierId = 0;
    if (supplierKeys.next()) {
        supplierId = supplierKeys.getInt(1);  // Get the Supplier_ID
    }

    // Insert stock data into stock_table using product_id and supplier_id
    String stockSql = "INSERT INTO stock_table (Product_ID, Supplier_ID, Current_Stock) VALUES (?, ?, ?)";
    java.sql.PreparedStatement stockStmt = con.prepareStatement(stockSql);
    stockStmt.setInt(1, productId);   // Set the Product_ID from product_table
    stockStmt.setInt(2, supplierId);  // Set the Supplier_ID from supplier_table
    stockStmt.setInt(3, Integer.parseInt(pqty));  // Set the stock quantity
    stockStmt.executeUpdate();

    // Commit the transaction
    con.commit();

    JOptionPane.showMessageDialog(null, "Product and Supplier Data Saved");

} catch (SQLException e) {
    e.printStackTrace();
    JOptionPane.showMessageDialog(null, "Error saving data: " + e.getMessage());
} catch (NumberFormatException e) {
    JOptionPane.showMessageDialog(null, "Quantity and Price must be valid numbers.");
} finally {
    tb_load();  
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
            // TODO add  your EDIT handling code here:
            // Get the selected row index from the table
    int selectedRow = stocktable.getSelectedRow();
    
    // Check if a row is selected
    if (selectedRow != -1) {
        // Get the selected data (optional, for example, to pass it to the edit form)
        DefaultTableModel model = (DefaultTableModel) stocktable.getModel();
        String productName = model.getValueAt(selectedRow, 1).toString();
        String price = model.getValueAt(selectedRow, 2).toString();
        String currentStock = model.getValueAt(selectedRow, 3).toString();
        String supplierName = model.getValueAt(selectedRow, 4).toString();
        String supplierEmail = model.getValueAt(selectedRow, 5).toString();

        // Optionally, pass selected data to the editInventory form
        editInventory ei = new editInventory();
        ei.setProductName(productName); // Assuming you have a setter in editInventory
        ei.setPrice(price); // Assuming you have a setter in editInventory
        ei.setStock(currentStock); // Assuming you have a setter in editInventory
        ei.setSupplierName(supplierName); // Assuming you have a setter in editInventory
        ei.setSupplierEmail(supplierEmail); // Assuming you have a setter in editInventory

        ei.setVisible(true);
    } else {
        // Show a message if no row is selected
        JOptionPane.showMessageDialog(null, "Please select a row to edit.");
    }            
    }//GEN-LAST:event_jButton1ActionPerformed

    private void i_search_tblKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_i_search_tblKeyReleased
        
    }//GEN-LAST:event_i_search_tblKeyReleased

    private void stocktableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_stocktableMouseClicked
         // TODO add your handling code here:
         
         
    }//GEN-LAST:event_stocktableMouseClicked

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
            deleteSelectedRows();

    }//GEN-LAST:event_jButton4ActionPerformed

    private void jToggleButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton3ActionPerformed
        CategoryPanel cpanel = new CategoryPanel();
        jpload.jPanelLoader(inventoryaccount_panel, cpanel);        // TODO add your handling code here:
    }//GEN-LAST:event_jToggleButton3ActionPerformed

    private void i_search_tblActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_i_search_tblActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_i_search_tblActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField i_search_tbl;
    private javax.swing.JPanel inventoryaccount_panel;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private com.toedter.calendar.JDateChooser jDateChooser2;
    private javax.swing.JLabel jLabel1;
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
    private javax.swing.JToggleButton jToggleButton3;
    private javax.swing.JTextField p_name;
    private javax.swing.JTextField p_price;
    private javax.swing.JTextField p_quantity;
    private javax.swing.JTextField s_emailadd;
    private javax.swing.JTextField s_name;
    private javax.swing.JTable stocktable;
    // End of variables declaration//GEN-END:variables
}
