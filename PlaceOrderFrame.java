import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class PlaceOrderFrame extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(PlaceOrderFrame.class.getName());

    private int userId;
    private int itemId;
    private String itemName;
    private double unitPrice;

    // Custom constructor to receive dashboard selections
        // Custom constructor to receive dashboard selections
    public PlaceOrderFrame(int userId, int itemId, String itemName, double price) {
        
        // FIX 1: This MUST be the first call. It builds the table, labels, and spinner components in memory.
        initComponents();
        
        this.userId = userId;
        this.itemId = itemId;
        this.itemName = itemName;
        this.unitPrice = price;
        
        if (lblItemName == null) {
            lblItemName = new javax.swing.JLabel();
        }
        if (lblPrice == null) {
            lblPrice = new javax.swing.JLabel();
        }
        if (lblTotalAmount == null) {
            lblTotalAmount = new javax.swing.JLabel();
        }
        if (spinQuantity == null) {
            spinQuantity = new javax.swing.JSpinner();
            spinQuantity.setModel(new javax.swing.SpinnerNumberModel(1, 1, null, 1));
        }
        if (tblOrderSummary == null) {
            tblOrderSummary = new javax.swing.JTable();
            tblOrderSummary.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {},
                new String [] { "Item Name", "Quantity", "Unit Price", "Total Price" }
            ));
        }
        
        // FIX 2: Set text ONLY after components have been initialized safely
        lblItemName.setText("Food Item: " + itemName);
        lblPrice.setText("Price per Unit: $" + price);
        
        javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) tblOrderSummary.getModel();
        model.setRowCount(0); 
        
        // Appends your initial item cleanly to the summary list tracking panel
        addOrUpdateOrderItem(this.itemId, this.itemName, (int) spinQuantity.getValue(), this.unitPrice);
        
        tblOrderSummary.getModel().addTableModelListener(new javax.swing.event.TableModelListener() {
            @Override
            public void tableChanged(javax.swing.event.TableModelEvent e) {
                // Trigger calculation adjustments if row items are updated by direct input typing lines
                if (e.getType() == javax.swing.event.TableModelEvent.UPDATE && e.getColumn() == 1) { 
                    tblOrderSummaryModelChanged();
                }
            }
        });
    }


    private PlaceOrderFrame() {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
            // Monitors if the user overrides values inside the Table grid columns directly
        
            // Dynamic recalculation engine for balancing labels, views, and quantity variables
    private void updateOrderTable() {
        int qty = (int) spinQuantity.getValue();
        double totalAmount;
        
        // If only 1 unit or zero/negative, hold original baseline price, otherwise calculate product rule
        if (qty <= 1) {
            qty = 1; 
            totalAmount = this.unitPrice;
        } else {
            totalAmount = this.unitPrice * qty;
        }
        
        // Refresh structural visual labels
        lblTotalAmount.setText("Total Amount: $" + String.format("%.2f", totalAmount));
        
        // Refresh structural table row grid mapping elements safely
        javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) tblOrderSummary.getModel();
        model.setRowCount(0); 
        model.addRow(new Object[]{this.itemName, qty, "$" + String.format("%.2f", this.unitPrice), "$" + String.format("%.2f", totalAmount)});
    }
    // Paste this method below your constructor to dynamically append or update items in the table grid
public void addOrUpdateOrderItem(int itemId, String name, int qty, double unitPrice) {
    javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) tblOrderSummary.getModel();
    int rowCount = model.getRowCount();
    boolean itemExists = false;
    
    // 1. Scan the table rows to check if this item was already added before
    for (int i = 0; i < rowCount; i++) {
        String existingName = model.getValueAt(i, 0).toString();
        if (existingName.equals(name)) {
            // Item exists: update its quantity
            int existingQty = Integer.parseInt(model.getValueAt(i, 1).toString());
            int newQty = existingQty + qty;
            if (newQty < 1) newQty = 1; // Baseline safety limit
            
            double newTotal = unitPrice * newQty;
            
            // Set updated values back to the specific row cells
            model.setValueAt(newQty, i, 1);
            model.setValueAt("$" + String.format("%.2f", newTotal), i, 3);
            itemExists = true;
            break;
        }
    }
    
    // 2. If it's a completely new selection, add it as a fresh row entry
    if (!itemExists) {
        double totalAmount = unitPrice * qty;
        model.addRow(new Object[]{name, qty, "$" + String.format("%.2f", unitPrice), "$" + String.format("%.2f", totalAmount)});
    }
    
    // 3. Recalculate the grand total bill across all items present in the table rows
    calculateGrandTotal();
}

// Helper method to scan all row totals and refresh the lblTotalAmount text component
private void calculateGrandTotal() {
    javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) tblOrderSummary.getModel();
    double grandTotal = 0.0;
    
    for (int i = 0; i < model.getRowCount(); i++) {
        Object totalVal = model.getValueAt(i, 3); // Grab total price cell string (e.g., "$15.50")
        if (totalVal != null) {
            String cleanVal = totalVal.toString().replace("$", "").trim();
            try {
                grandTotal += Double.parseDouble(cleanVal);
            } catch (NumberFormatException ignored) {}
        }
    }
    
    lblTotalAmount.setText("Total Amount: $" + String.format("%.2f", grandTotal));
}                                     
    private void tblOrderSummaryModelChanged() {
        try {
            javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) tblOrderSummary.getModel();
            Object qtyValue = model.getValueAt(0, 1);
            int qty = Integer.parseInt(qtyValue.toString());
            
            double totalAmount;
            if (qty <= 1) {
                qty = 1;
                totalAmount = this.unitPrice;
            } else {
                totalAmount = this.unitPrice * qty;
            }
            
            // Quietly adjust spinner values to keep visual layouts completely consistent
            spinQuantity.setValue(qty);
            
            // Adjust billing metric indicators
            lblTotalAmount.setText("Total Amount: $" + String.format("%.2f", totalAmount));
            model.setValueAt("$" + String.format("%.2f", totalAmount), 0, 3);
            
        } catch (NumberFormatException nfe) {
            // Instantly wipe and reset text inputs back to default metrics if alphabet letters are mistyped
            updateOrderTable(); 
        }
    }    



    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        cmbPayment = new javax.swing.JComboBox<>();
        btnConfirmOrder = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblOrderSummary = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("CONFIRM YOUR ORDER");

        jLabel3.setText("Payment Method:");

        cmbPayment.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Wallet", "Card", "UPI" }));
        cmbPayment.addActionListener(this::cmbPaymentActionPerformed);

        btnConfirmOrder.setText("Confirm & Pay.");
        btnConfirmOrder.addActionListener(this::btnConfirmOrderActionPerformed);

        btnCancel.setText("Cancel");
        btnCancel.addActionListener(this::btnCancelActionPerformed);

        tblOrderSummary.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Item Name", "Quantity", "Unit Price", "Total Price"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblOrderSummary);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(117, 117, 117)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnConfirmOrder, javax.swing.GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE))
                        .addGap(41, 41, 41)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cmbPayment, 0, 86, Short.MAX_VALUE)
                            .addComponent(btnCancel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 629, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(217, 217, 217)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(28, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbPayment, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnConfirmOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnConfirmOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmOrderActionPerformed
        // TODO add your handling code here:
        
        String payMethod = cmbPayment.getSelectedItem().toString();

        // 1. Read layout properties from your summary list tracking component grid
        javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) tblOrderSummary.getModel();
        int rowCount = model.getRowCount();
        
        if (rowCount == 0) {
            JOptionPane.showMessageDialog(this, "Your order summary table is empty!", "Checkout Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Calculate the absolute Grand Total balance requirement
        double grandTotal = 0.0;
        for (int i = 0; i < rowCount; i++) {
            Object totalVal = model.getValueAt(i, 3);
            if (totalVal != null) {
                String cleanVal = totalVal.toString().replace("$", "").trim();
                grandTotal += Double.parseDouble(cleanVal);
            }
        }

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String s = "jdbc:mysql://localhost:3306/smart_cafeteria";
            Connection con = DriverManager.getConnection(s, "root", "YOUR_PASSWORD_HERE");
            if (con == null) {
                JOptionPane.showMessageDialog(this, "Database connection failed!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Turn off auto-commit to start a unified ACID transaction block
            con.setAutoCommit(false);

            // 2. Verify and process digital cash reserves if using the Cafeteria Wallet
            if (payMethod.equals("Wallet")) {
                String checkBalQuery = "SELECT digital_wallet_balance FROM Users WHERE user_id = ?";
                PreparedStatement pstBal = con.prepareStatement(checkBalQuery);
                pstBal.setInt(1, this.userId);
                ResultSet rsBal = pstBal.executeQuery();

                if (rsBal.next()) {
                    double balance = rsBal.getDouble("digital_wallet_balance");
                    if (balance < grandTotal) {
                        JOptionPane.showMessageDialog(this, "Insufficient digital wallet balance!", "Payment Refused", JOptionPane.ERROR_MESSAGE);
                        con.rollback(); 
                        con.close();
                        return;
                    }

                    // Deduct grand total bill amount from user profile
                    String deductQuery = "UPDATE Users SET digital_wallet_balance = digital_wallet_balance - ? WHERE user_id = ?";
                    PreparedStatement pstDeduct = con.prepareStatement(deductQuery);
                    pstDeduct.setDouble(1, grandTotal);
                    pstDeduct.setInt(2, this.userId);
                    pstDeduct.executeUpdate();
                }
            }

            // 3. Add BOTH item_id and quantity fields into the query string to satisfy table structure keys
            String orderQuery = "INSERT INTO Orders (user_id, item_id, quantity, total_amount, order_status) VALUES (?, ?, ?, ?, 'Pending')";
            PreparedStatement pstOrder = con.prepareStatement(orderQuery, Statement.RETURN_GENERATED_KEYS);
            
            // We loop through the user selection grid to push all items safely downstream
            for (int i = 0; i < rowCount; i++) {
                // Fetch the current row's dynamic quantity integer out of cell column index 1
                int rowQty = Integer.parseInt(model.getValueAt(i, 1).toString());
                
                String rowCleanPrice = model.getValueAt(i, 3).toString().replace("$", "").trim();
                double itemRowTotal = Double.parseDouble(rowCleanPrice);
                
                pstOrder.setInt(1, this.userId);
                pstOrder.setInt(2, this.itemId); // Inserts the correct item_id key
                pstOrder.setInt(3, rowQty);      // Inserts the specific quantity value to satisfy database rules
                pstOrder.setDouble(4, itemRowTotal);
                pstOrder.executeUpdate();
                
                // 4. Capture the auto-generated order tracking ID for the transaction ledger mapping
                ResultSet generatedKeys = pstOrder.getGeneratedKeys();
                int newOrderId = 0;
                if (generatedKeys.next()) {
                    newOrderId = generatedKeys.getInt(1);
                }

                // 5. Insert corresponding transaction record trace logs
                String transactionQuery = "INSERT INTO Transactions (order_id, payment_method, payment_status) VALUES (?, ?, 'Success')";
                PreparedStatement pstTrans = con.prepareStatement(transactionQuery);
                pstTrans.setInt(1, newOrderId);
                pstTrans.setString(2, payMethod);
                pstTrans.executeUpdate();
            }

            // Commit all changes together once everything passes processing safely
            con.commit();
            con.close();

            // ==============================================================================
            // CONDITIONAL PAYMENT METHOD DIALOG ALERTS
            // ==============================================================================
            if ("Wallet".equalsIgnoreCase(payMethod)) {
                JOptionPane.showMessageDialog(this, "Payment Successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else if ("Card".equalsIgnoreCase(payMethod)) {
                JOptionPane.showMessageDialog(this, "Please swipe your card at the counter terminal.", "Card Payment", JOptionPane.INFORMATION_MESSAGE);
            } else if ("UPI".equalsIgnoreCase(payMethod)) {
                JOptionPane.showMessageDialog(this, "Processing UPI Payment... Scan the QR code options.", "UPI Payment", JOptionPane.INFORMATION_MESSAGE);
            }
            // ==============================================================================

            // Wipe out component lists clean and shut workspace down
            model.setRowCount(0);
            lblTotalAmount.setText("Total Amount: $0.00");
            this.dispose(); 

        } catch (Exception e) {
            try {
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/smart_cafeteria", "root", "compadmin5059");
                con.setAutoCommit(false);
                con.rollback(); 
                con.close();
            } catch(Exception ex) {} 
            JOptionPane.showMessageDialog(this, "Transaction failed processing: " + e.getMessage(), "Order Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnConfirmOrderActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        new UserDashboardFrame(this.userId).setVisible(true);
        this.dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCancelActionPerformed

    private void cmbPaymentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbPaymentActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbPaymentActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new PlaceOrderFrame().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnConfirmOrder;
    private javax.swing.JComboBox<String> cmbPayment;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblOrderSummary;
    // End of variables declaration//GEN-END:variables
    private javax.swing.JLabel lblTotalAmount;
    private javax.swing.JLabel lblItemName;
    private javax.swing.JLabel lblPrice;
    private javax.swing.JSpinner spinQuantity;
}
