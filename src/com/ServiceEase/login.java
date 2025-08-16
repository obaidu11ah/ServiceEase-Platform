	import javax.swing.*;
	import java.awt.*;
	import java.awt.event.*;
import java.security.MessageDigest;
import java.sql.CallableStatement;
	import java.sql.Connection;
	import java.sql.DriverManager;
	import java.sql.PreparedStatement;
	import java.sql.ResultSet;
	import java.sql.SQLException;
	import java.sql.Types;

	public class login extends JFrame {
		
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
		        } catch (Exception e) {
		            throw new RuntimeException("Hashing failed!", e);
		        }
		    }
		}
		public class SQLConnection {
		    public static void main(String[] args) {
		    	String url = "jdbc:sqlserver://localhost\\SQLEXPRESS;databaseName=ServiceEase;integratedSecurity=true;encrypt=true;trustServerCertificate=true;";
	
		        try {
		            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		            Connection con = DriverManager.getConnection(url);
		           
		            con.close();
		        } 
		        catch (Exception e) {
		            System.out.println("❌ Error: " + e.getMessage());
		        }
		    }
		}
		
		private int getCustomerUserId(String username, String password) throws SQLException {
		    String url = "jdbc:sqlserver://localhost\\SQLEXPRESS;databaseName=ServiceEase;integratedSecurity=true;encrypt=true;trustServerCertificate=true;";
		    String hashedPassword = PasswordHasher.hashPassword(password);
		    try (Connection con = DriverManager.getConnection(url)) {
		        String sql = "SELECT UserID FROM Users WHERE FullName = ? AND Password = ? AND Role = 'Customer'";
		        try (PreparedStatement ps = con.prepareStatement(sql)) {
		            ps.setString(1, username);
		            ps.setString(2, hashedPassword);
		            try (ResultSet rs = ps.executeQuery()) {
		                if (rs.next()) {
		                    return rs.getInt("UserID");
		                }
		            }
		        }
		    }
		    return -1;
		}
	
	    public login() {
	    	getContentPane().setBackground(new Color(245, 255, 250));
	        setUndecorated(true); // Remove default title bar
	        setSize(900, 500);
	        setLocationRelativeTo(null);
	        getContentPane().setLayout(null);
	        setDefaultCloseOperation(EXIT_ON_CLOSE);
	
	        // Custom Title Bar
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
	
	        // Left Panel
	        JPanel leftPanel = new JPanel();
	        leftPanel.setBounds(0, 30, 450, 470); // Adjusted height
	        leftPanel.setBackground(new Color(245, 255, 250));
	        leftPanel.setLayout(null);
	
	        JLabel signInLabel = new JLabel("Login In");
	        signInLabel.setBounds(173, 28, 123, 45);
	        signInLabel.setFont(new Font("Century Gothic", Font.BOLD, 26));
	        signInLabel.setForeground(new Color(0, 153, 119));
	        leftPanel.add(signInLabel);
	        
	        
	        JPanel emailPanel = new JPanel(null);
	        emailPanel.setBounds(100, 90, 250, 40);
	        emailPanel.setBackground(new Color(235, 250, 245));
	        leftPanel.add(emailPanel);
	        
	        JLabel emailIcon = new JLabel();
	        emailIcon.setBounds(10, 8, 20, 20);
	        emailPanel.add(emailIcon);
	
	        // Load and set the banda.png image as email icon
	        ImageIcon emailIconImage = new ImageIcon(this.getClass().getResource("banda.png"));
	        Image scaledImg = emailIconImage.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
	        emailIcon.setIcon(new ImageIcon(scaledImg));
	        
	        // Email Field with Placeholder
	        JTextField emailField = new JTextField("Full Name");
	        emailField.setBounds(35, 5, 200, 30);
	        emailField.setFont(new Font("Arial", Font.PLAIN, 14));
	        emailField.setForeground(Color.GRAY);
	        emailField.setBorder(BorderFactory.createEmptyBorder());
	        emailField.setBackground(new Color(235, 250, 245));
	        emailPanel.add(emailField);
	
	        emailField.addFocusListener(new FocusAdapter() {
	            public void focusGained(FocusEvent e) {
	                if (emailField.getText().equals("Full Name")) {
	                    emailField.setText("");
	                    emailField.setForeground(Color.GRAY);
	                }
	            }
	
	            public void focusLost(FocusEvent e) {
	                if (emailField.getText().isEmpty()) {
	                    emailField.setText("Full Name");
	                    emailField.setForeground(Color.GRAY);
	                }
	            }
	        });
	        
	        
	     // Password Panel
	        JPanel passwordPanel = new JPanel(null);
	        passwordPanel.setBounds(100, 150, 250, 40);
	        passwordPanel.setBackground(new Color(235, 250, 245));
	        leftPanel.add(passwordPanel);
	
	        // Password Icon
	        JLabel passwordIcon = new JLabel();
	        passwordIcon.setBounds(10, 8, 20, 20);
	        ImageIcon passwordIconImage = new ImageIcon(this.getClass().getResource("pass.png"));
	        Image passwordImg = passwordIconImage.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
	        passwordIcon.setIcon(new ImageIcon(passwordImg));
	        passwordPanel.add(passwordIcon);
	
	        // Password Field with Placeholder
	        JPasswordField passwordField = new JPasswordField("Password");
	        passwordField.setBounds(35, 5, 200, 30);
	        passwordField.setFont(new Font("Century Gothic", Font.PLAIN, 14));
	        passwordField.setForeground(Color.GRAY);
	        passwordField.setBorder(BorderFactory.createEmptyBorder());
	        passwordField.setBackground(new Color(235, 250, 245));
	        passwordPanel.add(passwordField);
	
	        passwordField.addFocusListener(new FocusAdapter() {
	            public void focusGained(FocusEvent e) {
	                String pass = new String(passwordField.getPassword());
	                if (pass.equals("Password")) {
	                    passwordField.setText("");
	                    passwordField.setEchoChar('●'); // Hide input
	                    passwordField.setForeground(Color.GRAY);
	                }
	            }
	
	            public void focusLost(FocusEvent e) {
	                String pass = new String(passwordField.getPassword());
	                if (pass.isEmpty()) {
	                    passwordField.setText("Password");
	                    passwordField.setEchoChar((char) 0); // Show plain text
	                    passwordField.setForeground(Color.GRAY);
	                }
	            }
	        });
	
	        // Default to showing plain text for placeholder
	        passwordField.setEchoChar((char) 0);
	
	        JLabel forgotLabel = new JLabel("Forgot your password ?");
	        forgotLabel.setBounds(150, 257, 200, 20);
	        forgotLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
	        forgotLabel.setForeground(Color.GRAY);
	        leftPanel.add(forgotLabel);
	
	        JButton signInButton = new JButton("SIGN IN") {
	        	
	            @Override
	            protected void paintComponent(Graphics g) {
	                Graphics2D g2 = (Graphics2D) g.create();
	                g2.setColor(new Color(0, 153, 119));
	                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 40, 40);
	                g2.dispose();
	                super.paintComponent(g);
	                
	                
	                
	                
	            }
	        };
	        
	        JRadioButton customerRadio = new JRadioButton("Customer");
	        customerRadio.setBounds(110, 226, 100, 25);
	        customerRadio.setBackground(new Color(245, 255, 250));
	        customerRadio.setFont(new Font("Arial", Font.BOLD, 14));
	        customerRadio.setForeground(Color.GRAY);
	        customerRadio.setFocusPainted(false);
	
	        JRadioButton serviceProviderRadio = new JRadioButton("ServiceProvider");
	        serviceProviderRadio.setBounds(223, 226, 150, 25);
	        serviceProviderRadio.setBackground(new Color(245, 255, 250));
	        serviceProviderRadio.setFont(new Font("Arial", Font.BOLD, 14));
	        serviceProviderRadio.setForeground(Color.GRAY);
	        serviceProviderRadio.setFocusPainted(false);
	
	        ButtonGroup roleGroup = new ButtonGroup();
	        roleGroup.add(customerRadio);
	        roleGroup.add(serviceProviderRadio);
	        leftPanel.add(customerRadio);
	        leftPanel.add(serviceProviderRadio);
	
	        signInButton.setBounds(124, 287, 200, 40);
	        signInButton.setForeground(Color.WHITE);
	        signInButton.setFont(new Font("Century Gothic", Font.BOLD, 14));
	        signInButton.setContentAreaFilled(false);
	        signInButton.setFocusPainted(false);
	        signInButton.setBorderPainted(false);
	        leftPanel.add(signInButton);
	        
	        getContentPane().add(leftPanel);
	
	     // Move the ActionListener here, outside of paintComponent
	        signInButton.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                String username = emailField.getText().trim();
	                String password = new String(passwordField.getPassword()).trim();
	                String role = null;
	
	                if (customerRadio.isSelected()) {
	                    role = "Customer";
	                } else if (serviceProviderRadio.isSelected()) {
	                    role = "ServiceProvider";
	                } 
	                if (username.equals("Full Name") || password.equals("Password") || username.isEmpty() || password.isEmpty() || role == null) {
	                    JOptionPane.showMessageDialog(login.this, "Please fill all fields and select a role.", "Error", JOptionPane.ERROR_MESSAGE);
	                    return;
	                }
	                

	                try (Connection con = DriverManager.getConnection(
	                        "jdbc:sqlserver://localhost\\SQLEXPRESS;databaseName=ServiceEase;integratedSecurity=true;encrypt=true;trustServerCertificate=true;")) {
	                	String hashedPassword = PasswordHasher.hashPassword(password);
	                    String sql = "SELECT dbo.CheckLoginFunc(?, ?, ?)";
	                    try (PreparedStatement ps = con.prepareStatement(sql)) {
	                        ps.setString(1, username);
	                        ps.setString(2, hashedPassword);
	                        ps.setString(3, role);
	
	                        try (ResultSet rs = ps.executeQuery()) {
	                            if (rs.next() && rs.getInt(1) == 1) {
	                                JOptionPane.showMessageDialog(login.this, "Login successful as " + role + "!");
	
	                                switch (role.toLowerCase()) {
	                                    
	                                    case "customer":
	                                        try {
	                                            String hashedPassword1 = PasswordHasher.hashPassword(password);
	                                            String url = "jdbc:sqlserver://localhost\\SQLEXPRESS;databaseName=ServiceEase;integratedSecurity=true;encrypt=true;trustServerCertificate=true;";
	                                            
	                                            try (Connection con1 = DriverManager.getConnection(url)) {
	                                                String sql1 = "{call dbo.ValidateCustomer(?, ?, ?)}"; // New stored procedure
	                                                try (CallableStatement cs = con1.prepareCall(sql1)) {
	                                                    cs.setString(1, username);
	                                                    cs.setString(2, hashedPassword1);
	                                                    cs.registerOutParameter(3, Types.INTEGER);
	                                                    cs.execute();

	                                                    int userID = cs.getInt(3);
	                                              

	                                                    if(userID > 0) {
	                                                        new CM(userID).setVisible(true); // Open customer dashboard
	                                                        login.this.dispose();
	                                                    } else {
	                                                        JOptionPane.showMessageDialog(login.this,
	                                                            "Invalid credentials or not a customer!",
	                                                            "Login Failed",
	                                                            JOptionPane.ERROR_MESSAGE
	                                                        );
	                                                    }
	                                                }
	                                            }
	                                        } catch (SQLException ex) {
	                                            ex.printStackTrace();
	                                            JOptionPane.showMessageDialog(login.this,
	                                                "Database error: " + ex.getMessage(),
	                                                "Error",
	                                                JOptionPane.ERROR_MESSAGE
	                                            );
	                                        }
	                                        break;
	                                        
	                                    case "serviceprovider":
	                                        String url = "jdbc:sqlserver://localhost\\SQLEXPRESS;databaseName=ServiceEase;integratedSecurity=true;encrypt=true;trustServerCertificate=true;";
	                                        try (Connection con1 = DriverManager.getConnection(url)) {
	                                            String sql1 = "{call dbo.ValidateServiceProvider(?, ?, ?)}";
	                                            try (CallableStatement cs = con1.prepareCall(sql1)) {
	                                                cs.setString(1, username);
	                                                cs.setString(2, hashedPassword);
	                                                cs.registerOutParameter(3, Types.INTEGER);
	                                                cs.execute();
	
	                                                int userID = cs.getInt(3);
	                                               // System.out.println("[DEBUG] UserID from DB: " + userID); // Debug log
	
	                                                if (userID > 0) {
	                                                    new service(userID).setVisible(true); // Pass userID to service
	                                                    login.this.dispose();
	                                                } else {
	                                                    JOptionPane.showMessageDialog(login.this, 
	                                                        "Invalid credentials or role.", 
	                                                        "Login Failed", 
	                                                        JOptionPane.ERROR_MESSAGE
	                                                    );
	                                                }
	                                            }
	                                        } catch (SQLException ex) {
	                                            ex.printStackTrace();
	                                        }
	                                        break;
	                                }
	
	                                login.this.dispose();
	                            } else {
	                                JOptionPane.showMessageDialog(login.this, "Invalid credentials or role.", "Login Failed", JOptionPane.ERROR_MESSAGE);
	                            }
	                        }
	                    }
	
	                } catch (SQLException ex) {
	                    ex.printStackTrace();
	                    JOptionPane.showMessageDialog(login.this, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	                }
	            }
	        });
	        
	        // Right Panel
	        JPanel rightPanel = new JPanel() {
	            @Override
	            public void paintComponent(Graphics g) {
	                super.paintComponent(g);
	                setBounds(450, 30, getParent().getWidth() - 450, 470);
	            }
	        };
	        rightPanel.setBackground(new Color(0, 153, 119));
	        rightPanel.setLayout(null);
	
	        JLabel helloLabel = new JLabel("Welcome to ServiceEase!");
	        helloLabel.setFont(new Font("Century Gothic", Font.BOLD, 24));
	        helloLabel.setForeground(Color.WHITE);
	        helloLabel.setBounds(90, 100, 300, 30);
	        rightPanel.add(helloLabel);
	
	        JLabel descLabel = new JLabel("<html><center>Join us today and get expert services with ease.</center></html>", SwingConstants.CENTER);
	        descLabel.setFont(new Font("Century Gothic", Font.PLAIN, 18));
	        descLabel.setForeground(Color.WHITE);
	        descLabel.setBounds(100, 150, 250, 60);
	        rightPanel.add(descLabel);
	        
	        JLabel roleLabel = new JLabel("Select Role:");
	        roleLabel.setFont(new Font("Arial", Font.BOLD, 14));
	        roleLabel.setForeground(new Color(0, 153, 119));
	        roleLabel.setBounds(100, 200, 100, 20);
	        leftPanel.add(roleLabel);
	
	
	        
	     // Sign Up Button (Right Panel in Sign In screen)
	        JButton signUpButton = new JButton("Register ");
	        signUpButton.setBounds(140, 230, 170, 40);
	        signUpButton.setFont(new Font("Century Gothic", Font.BOLD, 14));
	        signUpButton.setForeground(Color.WHITE);
	        signUpButton.setFocusPainted(false);
	        signUpButton.setContentAreaFilled(false);
	        signUpButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
	        signUpButton.setOpaque(false);
	        
	        signUpButton.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                dispose(); // Close current signup window
	                new Signup(); // Open the login window
	            }
	        });
	
	
	        // Action to open Sign Up screen
	        signInButton.addMouseListener(new MouseAdapter() {
	            public void mouseEntered(MouseEvent e) {
	                signInButton.setFont(new Font("Century Gothic", Font.BOLD, 15)); // Slightly larger
	                signInButton.setForeground(Color.WHITE);// Lighter shade on hover
	            }
	
	            public void mouseExited(MouseEvent e) {
	                signInButton.setFont(new Font("Century Gothic", Font.BOLD, 14)); // Original size
	                signInButton.setForeground(Color.WHITE); // Original color
	            }
	        });
	        
	        signUpButton.addMouseListener(new MouseAdapter() {
	            public void mouseEntered(MouseEvent e) {
	                signUpButton.setFont(new Font("Century Gothic", Font.BOLD, 15));
	                signUpButton.setForeground(new Color(0, 255, 200));
	                signUpButton.setBackground(new Color(255, 255, 255));// Theme-friendly light color
	                signUpButton.setBorder(BorderFactory.createLineBorder(new Color(0, 255, 200), 2));
	            }
	
	            public void mouseExited(MouseEvent e) {
	                signUpButton.setFont(new Font("Century Gothic", Font.BOLD, 14));
	                signUpButton.setForeground(Color.WHITE);
	                signUpButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
	            }
	        });
	
	        rightPanel.add(signUpButton);
	
	
	        getContentPane().add(rightPanel);
	
	        // Make resizable and refresh right panel on resize
	        addComponentListener(new ComponentAdapter() {
	            public void componentResized(ComponentEvent e) {
	                rightPanel.setBounds(450, 30, getWidth() - 450, getHeight() - 30);
	            }
	        });
	
	        setVisible(true);
	        
	    }
	    
	    
	    public static void main(String[] args) {
	        SwingUtilities.invokeLater(login::new);
	    }
	}
	

