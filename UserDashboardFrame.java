import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class UserDashboardFrame extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(UserDashboardFrame.class.getName());

    private int userId; // Global variable to store logged-in user's ID
    private int userNameStr;
    // Tracks the single active instance of the multi-item order frame window
    private PlaceOrderFrame activeOrderWindow = null;

    public UserDashboardFrame() {
        initComponents();
    }


    public UserDashboardFrame(int userId) {
        int staffId = userId;
        initComponents();
        this.userId = userId;
        loadUserProfile(); // Fetch name and wallet balance
        loadFoodMenu();
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    private void loadUserProfile() {
    
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
            String s="jdbc:mysql://localhost:3306/smart_cafeteria";
            Connection con = DriverManager.getConnection(s,"root","YOUR_PASSWORD_HERE");
            if (con == null) {
                JOptionPane.showMessageDialog(this, "Database connection failed!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        String query = "SELECT name, digital_wallet_balance FROM Users WHERE user_id = ?";
        PreparedStatement pst = con.prepareStatement(query);
        pst.setInt(1, this.userId);
        ResultSet rs = pst.executeQuery();
        
        if (rs.next()) {
            lblWelcome.setText("Welcome, " + rs.getString("name") + "!");
            lblWalletBalance.setText("Wallet Balance: $" + rs.getDouble("digital_wallet_balance"));
        }
        con.close();
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error fetching profile: " + e.getMessage());
    }
}

private void loadFoodMenu() {
    
    // Set up table structure
    DefaultTableModel model = new DefaultTableModel(new String[]{"Item ID", "Item Name", "Price"}, 0);
    tblFoodCatalog.setModel(model);
    
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
            String s="jdbc:mysql://localhost:3306/smart_cafeteria";
            Connection con = DriverManager.getConnection(s,"root","YOUR_PASSWORD_HERE");
            if (con == null) {
                JOptionPane.showMessageDialog(this, "Database connection failed!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        // Only load items that are currently marked as available (TRUE)
        String query = "SELECT item_id, item_name, price FROM Food_Items WHERE availability_status = TRUE";
        PreparedStatement pst = con.prepareStatement(query);
        ResultSet rs = pst.executeQuery();
        
        while (rs.next()) {
            int id = rs.getInt("item_id");
            String name = rs.getString("item_name");
            double price = rs.getDouble("price");
            
            model.addRow(new Object[]{id, name, price});
        }
        con.close();
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error loading menu: " + e.getMessage());
    }
}
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblWelcome = new javax.swing.JLabel();
        lblWalletBalance = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblFoodCatalog = new javax.swing.JTable();
        btnOrderFood1 = new javax.swing.JButton();
        btnWallet = new javax.swing.JButton();
        btnRefresh = new javax.swing.JButton();
        btnLogout = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblWelcome.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblWelcome.setText("Welcome to Smart Cafeteria!");

        lblWalletBalance.setText("Wallet Balance: $0.00");
        lblWalletBalance.addActionListener(this::lblWalletBalanceActionPerformed);

        tblFoodCatalog.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Item ID", "Item Name", "Price"
            }
        ));
        jScrollPane1.setViewportView(tblFoodCatalog);

        btnOrderFood1.setText("Order Selected Food");
        btnOrderFood1.addActionListener(this::btnOrderFood1ActionPerformed);

        btnWallet.setText("Manage Wallet");
        btnWallet.addActionListener(this::btnWalletActionPerformed);

        btnRefresh.setText("Refresh Menu");
        btnRefresh.addActionListener(this::btnRefreshActionPerformed);

        btnLogout.setText("Logout");
        btnLogout.addActionListener(this::btnLogoutActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(btnOrderFood1, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(82, 82, 82)
                .addComponent(btnWallet, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(141, 141, 141)
                        .addComponent(lblWelcome, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(167, 167, 167)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(143, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 520, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblWalletBalance, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(lblWelcome, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblWalletBalance, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnOrderFood1, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnWallet, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnRefresh, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(btnLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnWalletActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnWalletActionPerformed
        // TODO add your handling code here:
        new WalletFrame(this.userId).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnWalletActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        // TODO add your handling code here:
        loadFoodMenu();
        loadUserProfile();
    }//GEN-LAST:event_btnRefreshActionPerformed

    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogoutActionPerformed
        // TODO add your handling code here:
        new LoginFrame().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnLogoutActionPerformed

    private void lblWalletBalanceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lblWalletBalanceActionPerformed
        // TODO add your handling code here:
         new WalletFrame(this.userId).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_lblWalletBalanceActionPerformed

    private void btnOrderFood1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOrderFood1ActionPerformed
        // TODO add your handling code here:
    int activeRow = tblFoodCatalog.getSelectedRow(); 
    if (activeRow == -1) {
        javax.swing.JOptionPane.showMessageDialog(this, "Please select an item from the menu list first!", "No Item Selected", javax.swing.JOptionPane.WARNING_MESSAGE);
        return;
    }

    try {
        // 2. Safely parse cell parameters out of your selected catalog row column cells
        int selectedItemId = Integer.parseInt(tblFoodCatalog.getValueAt(activeRow, 0).toString());
        String selectedItemName = tblFoodCatalog.getValueAt(activeRow, 1).toString();
        String cleanPrice = tblFoodCatalog.getValueAt(activeRow, 2).toString().replace("$", "").trim();
        double selectedUnitPrice = Double.parseDouble(cleanPrice);
        int selectedQty = 1; 

        // 3. Match and push selection targets directly to PlaceOrderFrame
        if (activeOrderWindow == null || !activeOrderWindow.isDisplayable()) {
            // Instantiate a new window structure if it's currently completely closed
            activeOrderWindow = new PlaceOrderFrame(this.userId, selectedItemId, selectedItemName, selectedUnitPrice);
            activeOrderWindow.setVisible(true);
        } else {
            // Push directly to the open table grid without replacing row contents 
            activeOrderWindow.addOrUpdateOrderItem(selectedItemId, selectedItemName, selectedQty, selectedUnitPrice);
            activeOrderWindow.toFront();
            activeOrderWindow.requestFocus();
        }
        
    } catch (Exception ex) {
        javax.swing.JOptionPane.showMessageDialog(this, "Error processing table selection data: " + ex.getMessage(), "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
    }
    }//GEN-LAST:event_btnOrderFood1ActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new UserDashboardFrame().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnOrderFood1;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnWallet;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField lblWalletBalance;
    private javax.swing.JLabel lblWelcome;
    private javax.swing.JTable tblFoodCatalog;
    // End of variables declaration//GEN-END:variables
}

