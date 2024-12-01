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

public class SalesPanel extends javax.swing.JPanel {
    JpanelLoader jpload = new JpanelLoader();



public SalesPanel() {
    initComponents();
    table_load();
    tb_load();
    addListeners();
}


private void setTextStyle(JTextPane textPane, String text, String styleName) {
    StyledDocument doc = textPane.getStyledDocument();
    Style style = textPane.addStyle(styleName, null);
    switch (styleName) {
        case "BoldTitle":
            StyleConstants.setBold(style, true);
            StyleConstants.setFontSize(style, 16);
            StyleConstants.setAlignment(style, StyleConstants.ALIGN_CENTER);
            break;
        case "BodyText":
            StyleConstants.setFontSize(style, 12);
            StyleConstants.setForeground(style, Color.BLACK); // You can also change the color
            break;
        case "DiscountText":
            StyleConstants.setItalic(style, true);
            StyleConstants.setFontSize(style, 12);
            StyleConstants.setForeground(style, Color.RED); // For discount text
            break;
        // Add other styles as needed
    }

    try {
        doc.insertString(doc.getLength(), text + "\n", style);
    } catch (BadLocationException e) {
        e.printStackTrace();
    }
}

    
 private void setInvoiceTitle(String title) {
    // Get the document from the JTextPane
    StyledDocument doc = invoice_textarea.getStyledDocument();

    // Create a style for the title (bold text)
    Style style = invoice_textarea.addStyle("BoldTitle", null);
    StyleConstants.setBold(style, true); // Make the text bold
    StyleConstants.setFontSize(style, 16); // Optional: Set font size to 16
    StyleConstants.setAlignment(style, StyleConstants.ALIGN_CENTER); // Optional: Center align the title

    // Apply the style to the title
    try {
        doc.insertString(doc.getLength(), title + "\n", style); // Insert the bold title
    } catch (BadLocationException e) {
        e.printStackTrace();
    }
}



    private void table_load() {
    // Define the table model with a checkbox (Boolean) column in the first position
    DefaultTableModel model = new DefaultTableModel(
        new Object[]{"Select", "First Name", "Last Name"}, 0) {
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
    employee.setModel(model);
    model.setRowCount(0);  // Clear any existing rows

    try {
        java.sql.Connection con = db.mycon();
        String query = """
               SELECT 
                   First_Name, Last_Name
                FROM
                       employee_table
            """;

        java.sql.PreparedStatement stmt = con.prepareStatement(query);
        ResultSet rs = stmt.executeQuery();

        // Loop through the result set and add rows to the table model
        while (rs.next()) {
            String FirstName=  rs.getString("First_Name");
            String LastName= rs.getString("Last_Name"); // Retrieve the category name

            // Add a new row with a checkbox in the first column
            model.addRow(new Object[]{false, FirstName, LastName});
        }

    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error loading data: " + e.getMessage());
    }
    employee.getColumnModel().getColumn(0).setCellEditor(new DefaultCellEditor(new JCheckBox()));  // For editing
    employee.getColumnModel().getColumn(0).setCellRenderer(new CheckBoxRenderer());  // For rendering
}
    
 private void tb_load() {
    // Define the table model with a checkbox (Boolean) column in the first position
    DefaultTableModel model = new DefaultTableModel(
        new Object[]{"Select", "Package Option", "Product Names", "Package Item ID"}, 0) {  // Add column for package_item_id
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
    package_table.setModel(model);
    model.setRowCount(0);  // Clear any existing rows

    try {
        java.sql.Connection con = db.mycon();
        String query = """
              SELECT 
                             pp.package_option,
                             GROUP_CONCAT(p.product_name SEPARATOR ', ') AS product_names,
                             pi.package_item_id  -- Add package_item_id to the SELECT
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
        ResultSet rs = stmt.executeQuery();

        // Loop through the result set and add rows to the table model
        while (rs.next()) {
            String packageOption = rs.getString("package_option");
            String productNames = rs.getString("product_names");
            int packageItemId = rs.getInt("package_item_id");  // Retrieve the package_item_id

            // Add a new row with a checkbox in the first column and the package_item_id in the table
            model.addRow(new Object[]{false, packageOption, productNames, packageItemId});
        }

    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error loading data: " + e.getMessage());
    }
    package_table.getColumnModel().getColumn(0).setCellEditor(new DefaultCellEditor(new JCheckBox()));  // For editing
    package_table.getColumnModel().getColumn(0).setCellRenderer(new CheckBoxRenderer());  // For rendering
}

    
    
private void reduceStockForPackageItems(int packageItemId) {
    try {
        java.sql.Connection con = db.mycon();
        String query = "UPDATE stock_table SET current_stock = current_stock - 1 "
                     + "WHERE product_id IN (SELECT product_id FROM package_items WHERE package_id = ?)";

        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setInt(1, packageItemId);
            stmt.executeUpdate();
        }

    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error updating stock: " + e.getMessage());
    }
}

    
    
    
    
    // Get selected discount percentage from ComboBox
 private double getDiscountPercentage() {
    String selectedDiscount = (String) discountComboBox.getSelectedItem();
    

    // Validate the selected item
    if (selectedDiscount == null || selectedDiscount.trim().isEmpty()) {
        JOptionPane.showMessageDialog(this, "Please select a valid discount.", "Error", JOptionPane.ERROR_MESSAGE);
        return 0.0; // Default to 0% discount
    }

    try {
        // Parse the percentage value after removing the '%' symbol
        return Double.parseDouble(selectedDiscount.replace("%", "").trim());
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Invalid discount format. Please select a valid option.", "Error", JOptionPane.ERROR_MESSAGE);
        return 0.0; // Default to 0% discount
    }
}


   

    

    private String getSelectedInstaller() {
        StringBuilder installer = new StringBuilder();
        DefaultTableModel model = (DefaultTableModel) employee.getModel();
        for (int i = 0; i < model.getRowCount(); i++) {
            Boolean isSelected = (Boolean) model.getValueAt(i, 0);
            if (isSelected != null && isSelected) {
                installer.append(model.getValueAt(i, 1)).append(" ").append(model.getValueAt(i, 2)).append("\n");
            }
        }
        return installer.toString().trim().isEmpty() ? "Not Selected" : installer.toString().trim();
    }
    
private int getEmployeeIdFromSelectedInstaller() {
    DefaultTableModel model = (DefaultTableModel) employee.getModel();
    for (int i = 0; i < model.getRowCount(); i++) {
        Boolean isSelected = (Boolean) model.getValueAt(i, 0);
        if (isSelected != null && isSelected) {
            String firstName = (String) model.getValueAt(i, 1); // Assume column 1 is First Name
            String lastName = (String) model.getValueAt(i, 2);  // Assume column 2 is Last Name
            return getEmployeeId(firstName, lastName);
        }
    }
    return -1; // Default if no installer is selected
}

    private String getSelectedPackage() {
        StringBuilder packages = new StringBuilder();
        DefaultTableModel model = (DefaultTableModel) package_table.getModel();
        for (int i = 0; i < model.getRowCount(); i++) {
            Boolean isSelected = (Boolean) model.getValueAt(i, 0);
            if (isSelected != null && isSelected) {
                packages.append("Option: ").append(model.getValueAt(i, 1)).append("\n")
                        .append("Products: ").append(model.getValueAt(i, 2)).append("\n");
            }
        }
        return packages.toString().trim().isEmpty() ? "Not Selected" : packages.toString().trim();
    }
    
    
    private int getPackageItemId(String packageOption) {
    int packageItemId = -1; // Default value if not found
    try {
        java.sql.Connection con = db.mycon();
        String query = "SELECT package_id FROM product_package WHERE package_option = ?";
        java.sql.PreparedStatement stmt = con.prepareStatement(query);
        stmt.setString(1, packageOption);

        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            packageItemId = rs.getInt("package_id");
        }
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error retrieving Package Item ID: " + e.getMessage());
    }
    return packageItemId;
}



private int getEmployeeId(String firstName, String lastName) {
    try (java.sql.Connection con = db.mycon()) {
        String query = "SELECT employee_id FROM employee_table WHERE First_Name = ? AND Last_Name = ?";
        PreparedStatement stmt = con.prepareStatement(query);
        stmt.setString(1, firstName);
        stmt.setString(2, lastName);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return rs.getInt("employee_id");
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error retrieving Employee ID: " + e.getMessage());
        e.printStackTrace();
    }
    return -1;
}
    
    private String getPackageDetailsWithProducts() {
    StringBuilder invoiceDetails = new StringBuilder();
    
     try {
        java.sql.Connection con = db.mycon();
    
    // Sample query to get package details, products, and prices
    String query = "SELECT pp.package_option, " +
                   "GROUP_CONCAT(p.product_name SEPARATOR ', ') AS product_names, " +
                   "pp.unit, pp.unit_price, pp.total_package_price " +
                   "FROM package_items pi " +
                   "JOIN product_table p ON pi.product_id = p.product_id " +
                   "JOIN product_package pp ON pi.package_id = pp.package_id " +
                   "GROUP BY pi.package_id " +
                   "ORDER BY pi.package_id ASC";
    
        java.sql.PreparedStatement stmt = con.prepareStatement(query);
        ResultSet rs = stmt.executeQuery();
        
        while (rs.next()) {
            String packageOption = rs.getString("package_option");
            String productNames = rs.getString("product_names");
            double unitPrice = rs.getDouble("unit_price");
            double totalPackagePrice = rs.getDouble("total_package_price");

            // Append the data to the invoice details string
            invoiceDetails.append("Package: ").append(packageOption).append("\n")
                          .append("Products: ").append(productNames).append("\n")
                          .append("Unit Price: $").append(String.format("%.2f", unitPrice)).append("\n")
                          .append("Total Package Price: $").append(String.format("%.2f", totalPackagePrice)).append("\n\n");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    
     
     
    return invoiceDetails.toString();
}

    
    private String generateQuotationNo() {
    // Generate a Quotation Number based on the current date and a unique identifier
    String prefix = "QTN"; // Or any other prefix you want to use
    String date = new SimpleDateFormat("yyyyMMdd").format(new Date()); // Date in format yyyyMMdd
    String uniqueId = UUID.randomUUID().toString().substring(0, 8); // Generate a unique ID (first 8 characters of a UUID)

    // Combine to create the Quotation No
    return prefix + "-" + date + "-" + uniqueId;
}

    private double calculateGrandTotal(String packageDetails) {
    double total = 0.0;

    // Parse the packageDetails string and extract total prices (you can modify this if you're extracting prices from the query results)
    // For this example, assuming packageDetails includes the totalPackagePrice (extracted from the database query).
    
    // The packageDetails string contains the total price for each package. 
    // You'll need to extract and sum them. This is a placeholder implementation.
    
    // Example: Iterate over the package details to sum up the total prices (replace this with actual data extraction)
    String[] lines = packageDetails.split("\n");
    for (String line : lines) {
        if (line.contains("Total Package Price")) {
            String priceStr = line.replace("Total Package Price: $", "").trim();
            try {
                total += Double.parseDouble(priceStr);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
    }

    return total;
}

    
private void updateInvoiceArea() {
    // Clear the text area before updating
    invoice_textarea.setText("");

    // Get selected package, installer, and discount
    String selectedInstaller = getSelectedInstaller();
    String selectedPackage = getSelectedPackage();
    double discountPercentage = getDiscountPercentage();
    
    // Get the package details and product information
    String packageDetails = getPackageDetailsWithProducts();
    
    // Calculate the total price (you need to implement this method to sum up package prices)
    double grandTotal = calculateGrandTotal(packageDetails);
    
    // Calculate the discount amount and final price
    double discountValue = grandTotal * (discountPercentage / 100.0);
    double finalPrice = grandTotal - discountValue;

    // Start building the invoice text
    StringBuilder invoiceText = new StringBuilder();
    
    // Set Title
    setInvoiceTitle("Invoice Details:");  // Make the title bold and centered

    // Append Client Information
    invoiceText.append("Client Name: ").append(s_pname.getText()).append(" ")
               .append(s_mname.getText()).append(" ")
               .append(s_lname.getText()).append("\n");
    invoiceText.append("Address: ").append(s_street.getText()).append(", ")
               .append(s_city.getText()).append(", ")
               .append(s_province.getText()).append("\n");
    invoiceText.append("Contact: ").append(s_contact.getText()).append("\n");
    invoiceText.append("Email: ").append(s_email.getText()).append("\n");
    
    // Apply BodyText style for client details
    setTextStyle(invoice_textarea, invoiceText.toString(), "BodyText");

    // Append Installer, Package, and Discount Information
    invoiceText.setLength(0);  // Reset StringBuilder for the next section
    invoiceText.append("Installer:\n").append(selectedInstaller).append("\n");
    invoiceText.append("Package:\n").append(selectedPackage).append("\n");
    invoiceText.append("Discount: \n").append(discountPercentage).append("%\n");

    // Apply BodyText style for installer, package, and discount information
    setTextStyle(invoice_textarea, invoiceText.toString(), "BodyText");

    // Append Discount Amount, Grand Total Before Discount, and Final Price
    invoiceText.setLength(0);  // Reset StringBuilder for the final section
    invoiceText.append("Discount Amount: ₱").append(String.format("%.2f", discountValue)).append("\n");
    invoiceText.append("Grand Total Before Discount: ₱").append(String.format("%.2f", grandTotal)).append("\n");
    invoiceText.append("Final Price After Discount: ₱").append(String.format("%.2f", finalPrice)).append("\n");

    // Apply DiscountText style for the financial details
    setTextStyle(invoice_textarea, invoiceText.toString(), "DiscountText");
}



    private void addListeners() {
        KeyAdapter keyListener = new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                updateInvoiceArea();
            }
        };

        s_pname.addKeyListener(keyListener);
        s_mname.addKeyListener(keyListener);
        s_lname.addKeyListener(keyListener);
        s_street.addKeyListener(keyListener);
        s_city.addKeyListener(keyListener);
        s_province.addKeyListener(keyListener);
        s_contact.addKeyListener(keyListener);
        s_email.addKeyListener(keyListener);
    }

    
private void addInvoiceToDatabase() {
    // Add customer and get the generated customer_id
    int customerId = addCustomerAndGetCustomerId();
    if (customerId == -1) {
        JOptionPane.showMessageDialog(this, "Failed to add customer. Cannot proceed with invoice.");
        return; // Stop execution if customer was not added successfully
    }
    
    String packageDetails = getPackageDetailsWithProducts();

    double discountPercentage = getDiscountPercentage();
    double grandTotal = calculateGrandTotal(packageDetails);
    
    // Calculate the discount amount and final price
    double discountValue = grandTotal * (discountPercentage / 100.0);
    double finalPrice = grandTotal - discountValue;
    
    int selectedRow = package_table.getSelectedRow();
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(null, "Please select a package.");
        return;
    }

    int packageItemId = (int) package_table.getValueAt(selectedRow, 3); // Column 3 stores package_item_id

    
    
    try (java.sql.Connection con = db.mycon()) {
        String fetchLastQuotationNoSql = "SELECT Quotation_No FROM invoice ORDER BY invoice_id DESC LIMIT 1";
        PreparedStatement fetchStmt = con.prepareStatement(fetchLastQuotationNoSql);
        ResultSet rs = fetchStmt.executeQuery();

        String nextQuotationNo;
        if (rs.next() && rs.getString("Quotation_No") != null) {
            String lastQuotationNo = rs.getString("Quotation_No");
            int lastNumber = Integer.parseInt(lastQuotationNo.replace("QTN-", ""));
            nextQuotationNo = String.format("QTN-%03d", lastNumber + 1);
        } else {
            nextQuotationNo = "QTN-001";
        }

        String insertSql = "INSERT INTO invoice (Quotation_No, Customer_ID, Employee_ID, package_item_id, Discount_Percentage, Grand_Total) " +
                           "VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement pstmt = con.prepareStatement(insertSql);
        pstmt.setString(1, nextQuotationNo); 
        pstmt.setInt(2, customerId); // Use the new customer ID
        pstmt.setInt(3, getEmployeeIdFromSelectedInstaller());
        pstmt.setInt(4, packageItemId);
        pstmt.setDouble(5, discountPercentage);
        pstmt.setDouble(6, calculateGrandTotal(packageDetails));

        pstmt.executeUpdate();
        JOptionPane.showMessageDialog(this, "Invoice added successfully!");
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(this, "Error saving invoice: " + ex.getMessage());
        ex.printStackTrace();
    }
}

private int addCustomerAndGetCustomerId() {
    int customerId = -1;
    String firstName = s_pname.getText().trim();
    String middleName = s_mname.getText().trim();
    String lastName = s_lname.getText().trim();
    String street = s_street.getText().trim();
    String city = s_city.getText().trim();
    String province =s_province.getText().trim();
    String contact = s_contact.getText().trim();
    String email = s_email.getText().trim();
    
    // SQL query to insert new customer data
    String insertCustomerSql = "INSERT INTO customer_table (first_name, middle_name, last_name, street, city, province, contact_no, email) " +
                               "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    try (java.sql.Connection con = db.mycon()) {
        // Prepare the insert statement
        PreparedStatement pstmt = con.prepareStatement(insertCustomerSql, PreparedStatement.RETURN_GENERATED_KEYS);
        pstmt.setString(1, firstName);
        pstmt.setString(2, middleName);
        pstmt.setString(3, lastName);
        pstmt.setString(4, street);
        pstmt.setString(5, city);
        pstmt.setString(6, province);
        pstmt.setString(7, contact);
        pstmt.setString(8, email);

        // Execute the update
        int affectedRows = pstmt.executeUpdate();
        
        if (affectedRows > 0) {
            // Retrieve the generated customer_id
            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                customerId = generatedKeys.getInt(1); // The generated customer_id
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error adding customer: " + e.getMessage());
    }
    
    return customerId;
}




    private double calculateGrandTotal() {
        return 1000.0; // Placeholder logic
    }

    
  private void Employeefilter(String searchPackage) {
    DefaultTableModel model = (DefaultTableModel) employee.getModel();
    model.setRowCount(0); // Clear existing rows

    try {
        java.sql.Connection con = db.mycon();
        // Modified query to include WHERE clause with placeholders
        String query = """
            SELECT employee_id,
                   first_name, 
                   last_name
            FROM employee_table
            WHERE first_name LIKE ? OR last_name LIKE ?
        """;

        java.sql.PreparedStatement stmt = con.prepareStatement(query);
        stmt.setString(1, "%" + searchPackage + "%"); // Bind searchPackage for first_name
        stmt.setString(2, "%" + searchPackage + "%"); // Bind searchPackage for last_name
        java.sql.ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            String fname = rs.getString("first_name");
            String lname = rs.getString("last_name");
            String emid = rs.getString("employee_id");

            model.addRow(new Object[]{false, fname, lname, emid}); // Add row to the table model
        }

    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error filtering data: " + e.getMessage());
    }
}


    
    
private void PackageFilter(String searchPackage) {
    DefaultTableModel model = (DefaultTableModel) package_table.getModel();
    model.setRowCount(0); // Clear existing rows

    try {
        java.sql.Connection con = db.mycon();
        String query = """
           SELECT 
               pp.package_option,
               GROUP_CONCAT(p.product_name SEPARATOR ', ') AS product_names,
               package_item_id
           FROM 
               package_items pi
           JOIN 
               product_table p ON pi.product_id = p.product_id
           JOIN 
               product_package pp ON pi.package_id = pp.package_id
           WHERE 
               pp.package_option LIKE ? OR p.product_name LIKE ? OR package_item_id LIKE ?
           GROUP BY 
               pi.package_id
           ORDER BY 
               pi.package_id ASC;
        """;

        java.sql.PreparedStatement stmt = con.prepareStatement(query);
        stmt.setString(1, "%" + searchPackage + "%"); // For package_option
        stmt.setString(2, "%" + searchPackage + "%"); // For product_name
        stmt.setString(3, "%" + searchPackage + "%"); // For package_item_id
        java.sql.ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            String category = rs.getString("package_option");
            String pname = rs.getString("product_names");
            String tprice = rs.getString("package_item_id");

            // Adjust the row data to match the table column types
            model.addRow(new Object[]{false, category, pname, tprice}); // Assuming first column is a checkbox (Boolean)
        }

    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error filtering data: " + e.getMessage());
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

        jLabel12 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jToggleButton1 = new javax.swing.JToggleButton();
        jToggleButton2 = new javax.swing.JToggleButton();
        inventoryaccount_panel = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        s_pname = new javax.swing.JTextField();
        s_lname = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        s_province = new javax.swing.JTextField();
        s_email = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        s_city = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        s_street = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        s_contact = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        s_mname = new javax.swing.JTextField();
        package_search = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        package_table = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        employee = new javax.swing.JTable();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        employee_search = new javax.swing.JTextField();
        jButton5 = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        discountComboBox = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        invoice_textarea = new javax.swing.JTextPane();

        jLabel12.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 51, 102));
        jLabel12.setText("Select Installer:");

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(1290, 800));

        jPanel3.setPreferredSize(new java.awt.Dimension(1290, 800));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(238, 238, 252));

        jToggleButton1.setText("Invoice");
        jToggleButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton1ActionPerformed(evt);
            }
        });

        jToggleButton2.setText("Transaction");
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
                .addGap(17, 17, 17)
                .addComponent(jToggleButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jToggleButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(1059, Short.MAX_VALUE))
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
        jLabel1.setText("First Name");

        jLabel2.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 51, 102));
        jLabel2.setText("Last Name");

        jLabel4.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 51, 102));
        jLabel4.setText("Province");

        s_pname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                s_pnameActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 51, 102));
        jLabel5.setText("Contact");

        jLabel11.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 51, 102));
        jLabel11.setText("City");

        jLabel6.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 51, 102));
        jLabel6.setText("Street");

        s_street.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                s_streetActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 51, 102));
        jLabel7.setText("Email");

        jLabel3.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 51, 102));
        jLabel3.setText("Middle Name");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 81, Short.MAX_VALUE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jLabel6)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(s_lname)
                    .addComponent(s_pname, javax.swing.GroupLayout.DEFAULT_SIZE, 245, Short.MAX_VALUE)
                    .addComponent(s_street)
                    .addComponent(s_mname))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(s_province)
                            .addComponent(s_city)
                            .addComponent(s_contact, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 245, Short.MAX_VALUE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                        .addComponent(s_email, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(s_pname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(9, 9, 9)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3)
                            .addComponent(s_mname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel11)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(s_city, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(s_province, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(12, 12, 12)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(s_contact, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(s_lname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel6)
                        .addComponent(s_street, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel7)
                        .addComponent(s_email, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(26, Short.MAX_VALUE))
        );

        package_search.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                package_searchKeyReleased(evt);
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

        jButton1.setBackground(new java.awt.Color(238, 238, 248));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(0, 51, 102));
        jButton1.setText("PRINT");
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

        jLabel10.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 51, 102));
        jLabel10.setText("Select Package:");

        package_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Select", "Package Option", "Product Names", "Package Item ID"
            }
        ));
        jScrollPane2.setViewportView(package_table);
        if (package_table.getColumnModel().getColumnCount() > 0) {
            package_table.getColumnModel().getColumn(2).setResizable(false);
        }

        employee.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Select", "First Name", "Last Name", "Employee ID"
            }
        ));
        jScrollPane3.setViewportView(employee);
        if (employee.getColumnModel().getColumnCount() > 0) {
            employee.getColumnModel().getColumn(2).setResizable(false);
        }

        jLabel13.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 51, 102));
        jLabel13.setText("Select Installer:");

        jLabel14.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(0, 51, 102));
        jLabel14.setText("Search:");

        jLabel15.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(0, 51, 102));
        jLabel15.setText("Search:");

        employee_search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                employee_searchActionPerformed(evt);
            }
        });
        employee_search.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                employee_searchKeyReleased(evt);
            }
        });

        jButton5.setBackground(new java.awt.Color(238, 238, 248));
        jButton5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton5.setForeground(new java.awt.Color(0, 51, 102));
        jButton5.setText("ADD");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(0, 51, 102));
        jLabel16.setText("Select Discount:");

        discountComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0%", "10%", "50%" }));

        invoice_textarea.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jScrollPane1.setViewportView(invoice_textarea);

        javax.swing.GroupLayout inventoryaccount_panelLayout = new javax.swing.GroupLayout(inventoryaccount_panel);
        inventoryaccount_panel.setLayout(inventoryaccount_panelLayout);
        inventoryaccount_panelLayout.setHorizontalGroup(
            inventoryaccount_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(inventoryaccount_panelLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(inventoryaccount_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(inventoryaccount_panelLayout.createSequentialGroup()
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 259, Short.MAX_VALUE)
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(employee_search, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(inventoryaccount_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 668, Short.MAX_VALUE)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, inventoryaccount_panelLayout.createSequentialGroup()
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 185, Short.MAX_VALUE)
                            .addComponent(jLabel14)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(package_search, javax.swing.GroupLayout.PREFERRED_SIZE, 313, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jScrollPane3)
                        .addComponent(jScrollPane2)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(inventoryaccount_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(inventoryaccount_panelLayout.createSequentialGroup()
                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(discountComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 326, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(inventoryaccount_panelLayout.createSequentialGroup()
                        .addGroup(inventoryaccount_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(inventoryaccount_panelLayout.createSequentialGroup()
                                .addComponent(jButton1)
                                .addGap(349, 349, 349)
                                .addComponent(jButton3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton5)))
                        .addGap(84, 84, 84)
                        .addComponent(jButton2)
                        .addGap(25, 25, 25))))
        );
        inventoryaccount_panelLayout.setVerticalGroup(
            inventoryaccount_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(inventoryaccount_panelLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(inventoryaccount_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(inventoryaccount_panelLayout.createSequentialGroup()
                        .addGroup(inventoryaccount_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel16)
                            .addComponent(discountComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 457, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)
                        .addGroup(inventoryaccount_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton2)
                            .addComponent(jButton3)
                            .addComponent(jButton1)
                            .addComponent(jButton5))
                        .addGap(0, 148, Short.MAX_VALUE))
                    .addGroup(inventoryaccount_panelLayout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(inventoryaccount_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15)
                            .addComponent(employee_search, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(21, 21, 21)
                        .addGroup(inventoryaccount_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(package_search, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
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
        InvoicePanel iapanel = new InvoicePanel()    ;
        jpload.jPanelLoader(inventoryaccount_panel, iapanel);// TODO add your handling code here:
    }//GEN-LAST:event_jToggleButton1ActionPerformed

    private void jToggleButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton2ActionPerformed
        transaction tpanel = new transaction();
        jpload.jPanelLoader(inventoryaccount_panel, tpanel);   // TODO add your handling code here:
    }//GEN-LAST:event_jToggleButton2ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
  
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        
        s_pname.setText("");
        s_lname.setText("");
        s_mname.setText("");
        s_street.setText("");
        s_city.setText("");
        s_province.setText("");
        s_contact.setText("");
        s_email.setText("");  
        
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
                                    
   
    }//GEN-LAST:event_jButton1ActionPerformed

    private void package_searchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_package_searchKeyReleased
      // TODO add your handling code here:
           String searchText = package_search.getText().trim();

    if (searchText.isEmpty()) {
        tb_load(); // Reload the original content if the search text is empty
    } else {
        PackageFilter(searchText); // Filter table content
    }        
    }//GEN-LAST:event_package_searchKeyReleased

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

    private void s_pnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_s_pnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_s_pnameActionPerformed

    private void employee_searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_employee_searchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_employee_searchActionPerformed

    private void s_streetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_s_streetActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_s_streetActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
addInvoiceToDatabase();
       

    }//GEN-LAST:event_jButton5ActionPerformed

    private void employee_searchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_employee_searchKeyReleased
        // TODO add your handling code here:
           String searchText = employee_search.getText().trim();

    if (searchText.isEmpty()) {
        table_load(); // Reload the original content if the search text is empty
    } else {
        Employeefilter(searchText); // Filter table content
    }
    }//GEN-LAST:event_employee_searchKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> discountComboBox;
    private javax.swing.JTable employee;
    private javax.swing.JTextField employee_search;
    private javax.swing.JPanel inventoryaccount_panel;
    private javax.swing.JTextPane invoice_textarea;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JToggleButton jToggleButton2;
    private javax.swing.JTextField package_search;
    private javax.swing.JTable package_table;
    private javax.swing.JTextField s_city;
    private javax.swing.JTextField s_contact;
    private javax.swing.JTextField s_email;
    private javax.swing.JTextField s_lname;
    private javax.swing.JTextField s_mname;
    private javax.swing.JTextField s_pname;
    private javax.swing.JTextField s_province;
    private javax.swing.JTextField s_street;
    // End of variables declaration//GEN-END:variables
}
