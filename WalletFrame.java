import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

public class WalletFrame extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(WalletFrame.class.getName());

    private int userId; // Global variable to identify user session

    public WalletFrame(int userId) {
        initComponents();
        this.userId = userId;
        fetchWalletBalance(); // Load live balance instantly on open
    }
    
    public WalletFrame() {
        initComponents();
    }
        // Custom constructor to receive bill details and selected payment methods from PlaceOrderFrame
    public WalletFrame(int userId, double totalBillAmount, String paymentMethod) {
        initComponents();
        this.userId = userId;
        fetchWalletBalance(); // Always load live balance first
        
        // Populate the amount text box automatically with the total bill amount
        txtAmount.setText(String.format("%.2f", totalBillAmount));
        
        // Evaluate the payment method and show the specific message block
        if ("Wallet".equalsIgnoreCase(paymentMethod)) {
            lblNotification.setText("Payment Successful! Total of $" + String.format("%.2f", totalBillAmount) + " charged to Wallet.");
            lblNotification.setForeground(new java.awt.Color(0, 128, 0)); // Green for complete success
            
        } else if ("Card".equalsIgnoreCase(paymentMethod)) {
            lblNotification.setText("Order Placed! Please swipe your Card at the counter to finalize.");
            lblNotification.setForeground(new java.awt.Color(0, 0, 255)); // Blue for counter instruction
            
        } else if ("UPI".equalsIgnoreCase(paymentMethod)) {
            lblNotification.setText("Processing UPI Payment... Please choose an open UPI option or scan QR.");
            lblNotification.setForeground(new java.awt.Color(255, 128, 0)); // Orange for pending user action
            
        } else {
            lblNotification.setText("Order processing request registered successfully.");
            lblNotification.setForeground(java.awt.Color.DARK_GRAY);
        }
    }

    private void fetchWalletBalance() {
    
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
            String s="jdbc:mysql://localhost:3306/smart_cafeteria";
            Connection con = DriverManager.getConnection(s,"root","YOUR_PASSWORD_HERE");
            if (con == null) {
                JOptionPane.showMessageDialog(this, "Database connection failed!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        String query = "SELECT digital_wallet_balance FROM Users WHERE user_id = ?";
        PreparedStatement pst = con.prepareStatement(query);
        pst.setInt(1, this.userId);
        ResultSet rs = pst.executeQuery();
        
        if (rs.next()) {
            double balance = rs.getDouble("digital_wallet_balance");
            lblCurrentBalance.setText("Current Balance: $" + String.format("%.2f", balance));
        }
        con.close();
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Wallet extraction error: " + e.getMessage());
    }}
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        lblCurrentBalance = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtAmount = new javax.swing.JTextField();
        btnLoadFunds = new javax.swing.JButton();
        btnBack = new javax.swing.JButton();
        lblNotification = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("DIGITAL WALLET DASHBOARD");

        lblCurrentBalance.setText("Current Balance: $0.00");

        jLabel2.setText("Enter Amount to Top-Up ($):");

        txtAmount.addActionListener(this::txtAmountActionPerformed);

        btnLoadFunds.setText("Add Money via UPI / Card");
        btnLoadFunds.addActionListener(this::btnLoadFundsActionPerformed);

        btnBack.setText("Back to Dashboard");
        btnBack.addActionListener(this::btnBackActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(123, 123, 123)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnLoadFunds, javax.swing.GroupLayout.DEFAULT_SIZE, 205, Short.MAX_VALUE)
                            .addComponent(btnBack, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(52, 52, 52)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(62, 62, 62))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lblCurrentBalance, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblNotification, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(97, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblNotification, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCurrentBalance, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(52, 52, 52)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                    .addComponent(txtAmount))
                .addGap(41, 41, 41)
                .addComponent(btnLoadFunds, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(57, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnLoadFundsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoadFundsActionPerformed
        // TODO add your handling code here:
        String amountText = txtAmount.getText().trim();

        // Validation checks
        if (amountText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please input a value to add!", "Blank Input", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String s="jdbc:mysql://localhost:3306/smart_cafeteria";
            Connection con = DriverManager.getConnection(s,"root","YOUR_PASSWORD_HERE");
            if (con == null) {
                JOptionPane.showMessageDialog(this, "Database connection failed!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            double depositAmount = Double.parseDouble(amountText);
            if (depositAmount <= 0) {
                JOptionPane.showMessageDialog(this, "Amount must be greater than zero!", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Execute dynamic math inside MySQL directly to prevent concurrency overwrites
            String query = "UPDATE Users SET digital_wallet_balance = digital_wallet_balance + ? WHERE user_id = ?";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setDouble(1, depositAmount);
            pst.setInt(2, this.userId);

            int count = pst.executeUpdate();
            if (count > 0) {
                JOptionPane.showMessageDialog(this, "Payment Approved! $" + depositAmount + " loaded to wallet.");
                txtAmount.setText(""); // Wipe input panel clean
                fetchWalletBalance(); // Re-render live counter values
            }
            con.close();

        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(this, "Please type a valid numeric monetary value!", "Formatting Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Database write failure: " + e.getMessage());
        }
    }//GEN-LAST:event_btnLoadFundsActionPerformed

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        // TODO add your handling code here:
        new UserDashboardFrame(this.userId).setVisible(true); // Return home cleanly
        this.dispose(); // Release current frame context window from system
    }//GEN-LAST:event_btnBackActionPerformed

    private void txtAmountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAmountActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAmountActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new WalletFrame().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnLoadFunds;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel lblCurrentBalance;
    private javax.swing.JLabel lblNotification;
    private javax.swing.JTextField txtAmount;
    // End of variables declaration//GEN-END:variables
    
}
