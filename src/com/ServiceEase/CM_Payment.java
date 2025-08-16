import java.awt.*;
import java.math.BigDecimal;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class CM_Payment extends JDialog {
    private boolean paymentApproved = false;
    private JComboBox<String> paymentMethodCombo;
    private JTextField amountField;
    private BigDecimal requiredAmount;

    public CM_Payment(JFrame parent, BigDecimal price) {
        super(parent, "Payment Gateway", true);
        this.requiredAmount = price;
        initializeUI();
    }

    private void initializeUI() {
        setSize(400, 250);
        setLocationRelativeTo(getParent());
        setResizable(false);
        
        // Main panel with padding
        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(15, 15, 15, 15));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // Payment Method Section
        JLabel label = new JLabel("Select Payment Method:");
        label.setBounds(15, 15, 173, 38);
        contentPane.add(label);
        paymentMethodCombo = new JComboBox<>(new String[]{
            "Credit Card", "Debit Card", "Digital Wallet", "Bank Transfer", "Cash"
        });
        paymentMethodCombo.setBounds(212, 15, 110, 38);
        contentPane.add(paymentMethodCombo);

        // Amount Section
        JLabel label_1 = new JLabel("Enter Amount:");
        label_1.setBounds(15, 63, 173, 38);
        contentPane.add(label_1);
        amountField = new JTextField();
        amountField.setBounds(198, 63, 173, 38);
        contentPane.add(amountField);
        
        // Add empty labels to fill grid layout
        JLabel label_2 = new JLabel();
        label_2.setBounds(15, 111, 183, 38);
        contentPane.add(label_2);
        JButton btnCancel = new JButton("Cancel");
        btnCancel.setBounds(45, 130, 110, 21);
        contentPane.add(btnCancel);
        btnCancel.setBackground(new Color(244, 67, 54));
        btnCancel.setForeground(Color.WHITE);
        JButton btnPay = new JButton("Confirm Payment");
        btnPay.setBounds(212, 130, 142, 21);
        contentPane.add(btnPay);
        
        // Styling buttons
        btnPay.setBackground(new Color(76, 175, 80));
        btnPay.setForeground(Color.WHITE);
        
                // Event Handlers
                btnPay.addActionListener(e -> validatePayment());
        btnCancel.addActionListener(e -> dispose());
    }

    private void validatePayment() {
        try {
            BigDecimal enteredAmount = new BigDecimal(amountField.getText());
            
            if(enteredAmount.compareTo(requiredAmount) == 0) {
                paymentApproved = true;
                JOptionPane.showMessageDialog(this, "Payment Successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else {
                String message = enteredAmount.compareTo(requiredAmount) > 0 ? 
                    "Amount exceeds service price!" : "Insufficient payment amount!";
                JOptionPane.showMessageDialog(this, message, "Payment Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch(NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid amount format!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean isPaymentApproved() {
        return paymentApproved;
    }
}