import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Signup extends JFrame {
	private Connection getConnection() throws SQLException {
	    try {
	        // Step 1: Explicitly load JDBC driver
	        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	    } 
	    catch (ClassNotFoundException e) {
	        JOptionPane.showMessageDialog(null, "JDBC Driver not found!");
	        e.printStackTrace();
	    }
	    // Step 2: Connection URL
	    String url = "jdbc:sqlserver://localhost\\SQLEXPRESS;databaseName=ServiceEase;integratedSecurity=true;encrypt=true;trustServerCertificate=true;";

	    return DriverManager.getConnection(url);
	}

public class PasswordHasher {
	 public static String hashPassword(String password) {
	        try {
	            MessageDigest md = MessageDigest.getInstance("SHA-256");
	            byte[] hashedBytes = md.digest(password.getBytes());
	            StringBuilder sb = new StringBuilder();
	            for (byte b : hashedBytes) {
	                sb.append(String.format("%02x", b));
	            }
	            return sb.toString();
	        } catch (NoSuchAlgorithmException e) {
	            throw new RuntimeException("SHA-256 algorithm not found", e);
	        }
	    }

}

    public Signup() {
        getContentPane().setBackground(new Color(245, 255, 250));
        setUndecorated(true);
        setSize(900, 500);
        setLocationRelativeTo(null);
        getContentPane().setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel titleBar = new JPanel();
        titleBar.setBounds(450, 0, 450, 44);
        titleBar.setLayout(null);
        titleBar.setBackground(new Color(0, 153, 119));

        JLabel closeLabel = new JLabel("X");
        closeLabel.setForeground(new Color(255, 250, 250));
        closeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        closeLabel.setBounds(430, 5, 20, 20);
        closeLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        closeLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
        });
        titleBar.add(closeLabel);
        getContentPane().add(titleBar);

        JPanel leftPanel = new JPanel();
        leftPanel.setBounds(0, 30, 450, 470);
        leftPanel.setBackground(new Color(245, 255, 250));
        leftPanel.setLayout(null);

        JLabel signUpLabel = new JLabel("Sign Up");
        signUpLabel.setBounds(173, 28, 123, 45);
        signUpLabel.setFont(new Font("Century Gothic", Font.BOLD, 26));
        signUpLabel.setForeground(new Color(0, 153, 119));
        leftPanel.add(signUpLabel);

        JPanel namePanel = createInputPanel("banda.png", 100, 90, "Full Name");
        leftPanel.add(namePanel);

        JPanel emailPanel1 = createInputPanel("mail.png", 100, 150, "Email");
        leftPanel.add(emailPanel1);

        JPanel passwordPanel = createPasswordPanel("pass.png", 100, 210);
        leftPanel.add(passwordPanel);

        JLabel roleLabel = new JLabel("Select Role:");
        roleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        roleLabel.setForeground(new Color(0, 153, 119));
        roleLabel.setBounds(100, 265, 100, 20);
        leftPanel.add(roleLabel);

        JRadioButton customerRadio = new JRadioButton("Customer");
        customerRadio.setBounds(110, 289, 100, 25);
        customerRadio.setBackground(new Color(245, 255, 250));
        customerRadio.setFont(new Font("Arial", Font.BOLD, 14));
        customerRadio.setForeground(Color.GRAY);
        customerRadio.setFocusPainted(false);

        JRadioButton serviceProviderRadio = new JRadioButton("Service Provider");
        serviceProviderRadio.setBounds(212, 289, 150, 25);
        serviceProviderRadio.setBackground(new Color(245, 255, 250));
        serviceProviderRadio.setFont(new Font("Arial", Font.BOLD, 14));
        serviceProviderRadio.setForeground(Color.GRAY);
        serviceProviderRadio.setFocusPainted(false);

        ButtonGroup roleGroup = new ButtonGroup();
        roleGroup.add(customerRadio);
        roleGroup.add(serviceProviderRadio);
        leftPanel.add(customerRadio);
        leftPanel.add(serviceProviderRadio);

        JButton signUpButton = new JButton("SIGN UP") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(new Color(0, 153, 119));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 40, 40);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        signUpButton.setBounds(120, 330, 200, 40);
        signUpButton.setForeground(Color.WHITE);
        signUpButton.setFont(new Font("Century Gothic", Font.BOLD, 14));
        signUpButton.setContentAreaFilled(false);
        signUpButton.setFocusPainted(false);
        signUpButton.setBorderPainted(false);

        signUpButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                signUpButton.setFont(new Font("Century Gothic", Font.BOLD, 15));
                signUpButton.setForeground(Color.WHITE);
            }

            public void mouseExited(MouseEvent e) {
                signUpButton.setFont(new Font("Century Gothic", Font.BOLD, 14));
                signUpButton.setForeground(Color.WHITE);
            }
        });
        signUpButton.addActionListener(e -> {
            // Input Values Capture Karein
            String fullName = ((JTextField) namePanel.getComponent(1)).getText();
            String email = ((JTextField) emailPanel1.getComponent(1)).getText();
            String password = new String(((JPasswordField) passwordPanel.getComponent(1)).getPassword());
            String role = customerRadio.isSelected() ? "Customer" : "ServiceProvider";

            String hashedPassword = PasswordHasher.hashPassword(password);

            // Validations
            if (fullName.isEmpty() || email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(null, "All fields are required!");
                return;
            }
            if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                JOptionPane.showMessageDialog(null, "Invalid email format!");
                return;
            }
            if (password.length() < 8) {
                JOptionPane.showMessageDialog(null, "Password must be at least 8 characters!");
                return;
            }

            // Database Insertion
            try (Connection con = getConnection()) {
                String query = "INSERT INTO Users (FullName, Email, Password, Role) VALUES (?, ?, ?, ?)";
                PreparedStatement pst = con.prepareStatement(query);
                pst.setString(1, fullName);
                pst.setString(2, email);
                pst.setString(3, hashedPassword);	
                pst.setString(4, role);
                pst.executeUpdate();
                JOptionPane.showMessageDialog(null, "Sign-Up Successful!");
            } 
            catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
            }
        });
        leftPanel.add(signUpButton);
        getContentPane().add(leftPanel);

        JPanel rightPanel = new JPanel();
        rightPanel.setBounds(450, 30, 450, 470);
        rightPanel.setBackground(new Color(0, 153, 119));
        rightPanel.setLayout(null);

        JLabel welcomeLabel = new JLabel("Welcome Back!");
        welcomeLabel.setFont(new Font("Century Gothic", Font.BOLD, 24));
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setBounds(120, 100, 300, 30);
        rightPanel.add(welcomeLabel);

        JLabel infoLabel = new JLabel("<html><center>Already have an account? Sign in now.</center></html>");
        infoLabel.setFont(new Font("Century Gothic", Font.PLAIN, 18));
        infoLabel.setForeground(Color.WHITE);
        infoLabel.setBounds(90, 150, 250, 60);
        rightPanel.add(infoLabel);

        JButton loginButton = new JButton("SIGN IN");
        loginButton.setBounds(140, 230, 170, 40);
        loginButton.setFont(new Font("Century Gothic", Font.BOLD, 14));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setContentAreaFilled(false);
        loginButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        loginButton.setOpaque(false);

        loginButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                loginButton.setFont(new Font("Century Gothic", Font.BOLD, 15));
                loginButton.setForeground(new Color(0, 255, 200));
                loginButton.setBackground(new Color(255, 255, 255));
                loginButton.setBorder(BorderFactory.createLineBorder(new Color(0, 255, 200), 2));
            }

            public void mouseExited(MouseEvent e) {
                loginButton.setFont(new Font("Century Gothic", Font.BOLD, 14));
                loginButton.setForeground(Color.WHITE);
                loginButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
            }
        });

        loginButton.addActionListener(e -> {
            dispose();
            new login();
        });

        rightPanel.add(loginButton);
        getContentPane().add(rightPanel);
        setVisible(true);
    }

    private JPanel createInputPanel(String iconPath, int x, int y, String placeholder) {
        JPanel panel = new JPanel(null);
        panel.setBounds(x, y, 250, 40);
        panel.setBackground(new Color(235, 250, 245));

        JLabel icon = new JLabel();
        icon.setBounds(10, 8, 20, 20);
        ImageIcon iconImage = new ImageIcon(this.getClass().getResource(iconPath));
        Image scaledImg = iconImage.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        icon.setIcon(new ImageIcon(scaledImg));
        panel.add(icon);

        JTextField field = new JTextField(placeholder);
        field.setBounds(35, 5, 200, 30);
        field.setFont(new Font("Arial", Font.PLAIN, 14));
        field.setForeground(Color.GRAY);
        field.setBorder(BorderFactory.createEmptyBorder());
        field.setBackground(new Color(235, 250, 245));
        panel.add(field);

        field.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (field.getText().equals(placeholder)) {
                    field.setText("");
                    field.setForeground(Color.GRAY);
                }
            }
            public void focusLost(FocusEvent e) {
                if (field.getText().isEmpty()) {
                    field.setText(placeholder);
                    field.setForeground(Color.GRAY);
                }
            }
        });

        return panel;
    }

    private JPanel createPasswordPanel(String iconPath, int x, int y) {
        JPanel panel = new JPanel(null);
        panel.setBounds(x, y, 250, 40);
        panel.setBackground(new Color(235, 250, 245));

        JLabel icon = new JLabel();
        icon.setBounds(10, 8, 20, 20);
        ImageIcon iconImage = new ImageIcon(this.getClass().getResource(iconPath));
        Image scaledImg = iconImage.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        icon.setIcon(new ImageIcon(scaledImg));
        panel.add(icon);

        JPasswordField field = new JPasswordField("Password");
        field.setBounds(35, 5, 200, 30);
        field.setFont(new Font("Century Gothic", Font.PLAIN, 14));
        field.setForeground(Color.GRAY);
        field.setBorder(BorderFactory.createEmptyBorder());
        field.setBackground(new Color(235, 250, 245));
        field.setEchoChar((char) 0);
        panel.add(field);

        field.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                String pass = new String(field.getPassword());
                if (pass.equals("Password")) {
                    field.setText("");
                    field.setEchoChar('●');
                    field.setForeground(Color.GRAY);
                }
            }
            public void focusLost(FocusEvent e) {
                String pass = new String(field.getPassword());
                if (pass.isEmpty()) {
                    field.setText("Password");
                    field.setEchoChar((char) 0);
                    field.setForeground(Color.GRAY);
                }
            }
        });

        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Signup::new);
    }
}
