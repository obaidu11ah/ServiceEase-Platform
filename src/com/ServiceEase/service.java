import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.sql.*;
import java.math.BigDecimal;

public class service extends JFrame {
	private int userID;
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private final JPanel panel = new JPanel();
    private JTextField textField;
    private JTextField textField_1;
    private JTextField textField_3;
    private JTextField textField_4;
    private JTextArea textArea;
    private JCheckBox chckbxNewCheckBox;
    private JCheckBox chckbxNotAvailable;
    private final String url = "jdbc:sqlserver://localhost\\SQLEXPRESS;databaseName=ServiceEase;integratedSecurity=true;encrypt=true;trustServerCertificate=true;";
   
    private String getUserFullName(int userID) {
        String fullName = "User"; // Default name if not found
        String sql = "SELECT FullName FROM Users WHERE UserID = ?";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userID);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                fullName = rs.getString("FullName"); // Get name from DB
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return fullName;
    }
    
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            service frame = new service(1); 
            frame.setLocationRelativeTo(null); // Hardcode a valid UserID for testing
            frame.setVisible(true);
        });
    }

    public service(int userID) {
    	this.userID = userID;
    	 setUndecorated(true);
    	 
    	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	     // Add to the left panel
    	
    	    setBounds(100, 100, 825, 629);
    	    setLocationRelativeTo(null); // ✅ Add this line
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        panel.setBackground(new Color(0, 153, 119));
        panel.setBounds(0, 0, 237, 629);
        contentPane.add(panel);
        panel.setLayout(null);

        // Logo
        JLabel logo = new JLabel("");
        logo.setBounds(27, 10, 113, 119);
        panel.add(logo);
        ImageIcon logo1 = new ImageIcon(this.getClass().getResource("user1(2).png"));
        logo.setIcon(logo1);

        // Divider line
        JPanel panel_2 = new JPanel();
        panel_2.setBounds(39, 126, 157, 4);
        panel.add(panel_2);

        // Logout
        JLabel logout = new JLabel("");
        logout.setBounds(60, 561, 87, 58);
        panel.add(logout);
        ImageIcon logoutIcon = new ImageIcon(this.getClass().getResource("logout(2)(4).png"));
        logout.setIcon(logoutIcon);

        logout.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                int confirm = JOptionPane.showConfirmDialog(
                        null,
                        "Are you sure you want to logout?",
                        "Confirm Logout",
                        JOptionPane.YES_NO_OPTION
                );

                if (confirm == JOptionPane.YES_OPTION) {
                    dispose();
                    login login = new login();
                    login.setVisible(true);
                }
            }
        });

        // Edit Profile
     // Create and style the Edit Profile label
        JLabel lblEditProfile = new JLabel("Dashboard");
        lblEditProfile.setForeground(new Color(240, 255, 240));
        lblEditProfile.setFont(new Font("Century Gothic", Font.BOLD, 13));
        lblEditProfile.setBounds(60, 154, 150, 31);
        lblEditProfile.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // Make it look clickable
        panel.add(lblEditProfile);

        // Add click event to open EditProfile frame
     // Service.java mein, EditProfile label ka MouseListener:
     // service.java ke lblEditProfile MouseListener mein:

        

        // Create and set icon for profile
        JLabel iconProfile = new JLabel("");
        iconProfile.setBounds(39, 157, 24, 23);
        iconProfile.setIcon(new ImageIcon(this.getClass().getResource("edit-user-16.png")));
        panel.add(iconProfile);

        // Add hover and click event
        lblEditProfile.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Open the EditProfile frame (assuming you have a class called EditProfile)
                EditProfile editProfileFrame = new EditProfile(userID);
                dispose();
                editProfileFrame.setVisible(true);
                editProfileFrame.setLocationRelativeTo(null); // Center the new frame
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                lblEditProfile.setFont(new Font("Century Gothic", Font.BOLD, 14)); // Increase size on hover
            }

            @Override
            public void mouseExited(MouseEvent e) {
                lblEditProfile.setFont(new Font("Century Gothic", Font.BOLD, 13)); // Reset size
            }
        });

     

        // Dashboard
        JLabel lblDashboard = new JLabel("View Profile");
        lblDashboard.setForeground(new Color(240, 255, 240));
        lblDashboard.setFont(new Font("Century Gothic", Font.BOLD, 12));
        lblDashboard.setBounds(60, 183, 150, 31);
        panel.add(lblDashboard);
        JLabel iconDashboard = new JLabel("");
        iconDashboard.setBounds(39, 186, 24, 25);
        panel.add(iconDashboard);
        iconDashboard.setIcon(new ImageIcon(this.getClass().getResource("dashboard-16.png")));
        addHoverAndOpenFrame(lblDashboard, "Dashboard");
        
        lblDashboard.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	dispose();
                Dashboard editProfileFrame = new Dashboard(userID);
                editProfileFrame.setVisible(true);
                editProfileFrame.setLocationRelativeTo(null); 
                
            }
            // Keep existing mouseEntered/mouseExited handlers
        });
        // Manage Services
        JLabel lblServices = new JLabel("Manage Services");
        lblServices.setForeground(new Color(240, 255, 240));
        lblServices.setFont(new Font("Century Gothic", Font.BOLD, 12));
        lblServices.setBounds(60, 212, 150, 31);
        panel.add(lblServices);
        JLabel iconServices = new JLabel("");
        iconServices.setBounds(39, 212, 24, 25);
        panel.add(iconServices);
        iconServices.setIcon(new ImageIcon(this.getClass().getResource("services-16.png")));
        addHoverAndOpenFrame(lblServices, "Manage Services");
        
        lblServices.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	JOptionPane.showMessageDialog(null, "Already on Manage Services !!");
                
            }
            // Keep existing mouseEntered/mouseExited handlers
        });

        // Bookings
        JLabel lblBookings = new JLabel("Bookings");
        lblBookings.setForeground(new Color(240, 255, 240));
        lblBookings.setFont(new Font("Century Gothic", Font.BOLD, 12));
        lblBookings.setBounds(60, 242, 150, 31);
        panel.add(lblBookings);
        JLabel iconBookings = new JLabel("");
        iconBookings.setBounds(39, 247, 16, 17);
        panel.add(iconBookings);
        iconBookings.setIcon(new ImageIcon(this.getClass().getResource("check-mark-11-16.png")));
        addHoverAndOpenFrame(lblBookings, "Bookings");
        
        lblBookings.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Open the EditProfile frame (assuming you have a class called EditProfile)
                BookedServ editProfileFrame = new BookedServ(userID);
                dispose();
                editProfileFrame.setVisible(true);
                editProfileFrame.setLocationRelativeTo(null); // Center the new frame
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                lblEditProfile.setFont(new Font("Century Gothic", Font.BOLD, 14)); // Increase size on hover
            }

            @Override
            public void mouseExited(MouseEvent e) {
                lblEditProfile.setFont(new Font("Century Gothic", Font.BOLD, 13)); // Reset size
            }
        });

        
        JLabel lblNewLabel_5 = new JLabel("Welcome!..");
        lblNewLabel_5.setForeground(new Color(255, 255, 255));
        lblNewLabel_5.setFont(new Font("Century Gothic", Font.BOLD, 14));
        lblNewLabel_5.setBounds(114, 32, 95, 23);
        panel.add(lblNewLabel_5);
        

        String userName = getUserFullName(userID); // Fetch name using userID
        JLabel lblNewLabel_5_11 = new JLabel(userName); // Set the label text
        lblNewLabel_5_11.setForeground(Color.WHITE);
        lblNewLabel_5_11.setFont(new Font("Century Gothic", Font.BOLD, 14));
        lblNewLabel_5_11.setBounds(132, 54, 95, 23);
        panel.add(lblNewLabel_5_11); // Add label to the left panel
        // Right side content panel
        
        JPanel panel_1 = new JPanel();
        panel_1.setBackground(new Color(255, 255, 255));
        panel_1.setBounds(236, 34, 589, 595);
        contentPane.add(panel_1);
        panel_1.setLayout(null);

        JLabel image = new JLabel("");
        image.setBounds(153, 21, 300, 34);
        panel_1.add(image);
        ImageIcon emailIconImage = new ImageIcon(this.getClass().getResource("Service Provider(4).png"));
        image.setIcon(emailIconImage);
        
        JPanel panel_3 = new JPanel();
        panel_3.setBackground(new Color(0, 153, 119));
        panel_3.setBounds(10, 65, 569, 37);
        panel_1.add(panel_3);
        panel_3.setLayout(null);
        
        JLabel lblNewLabel = new JLabel("Manage Your Services Here!!!");
        lblNewLabel.setForeground(new Color(255, 255, 255));
        lblNewLabel.setFont(new Font("Century Gothic", Font.BOLD, 18));
        lblNewLabel.setBounds(10, 0, 300, 37);
        panel_3.add(lblNewLabel);
        
        JPanel panel_4 = new JPanel();
        panel_4.setBackground(SystemColor.control);
        panel_4.setBounds(10, 156, 569, 470);
        panel_1.add(panel_4);
        panel_4.setLayout(null);
        
        JPanel panel_7 = new JPanel();
        panel_7.setBackground(Color.LIGHT_GRAY);
        panel_7.setBounds(10, 21, 499, 33);
        panel_4.add(panel_7);
        panel_7.setLayout(null);
        
        JLabel lblNewLabel_2 = new JLabel("");
        lblNewLabel_2.setBounds(20, 10, 16, 18);
        panel_7.add(lblNewLabel_2);
        
        ImageIcon bulb = new ImageIcon(this.getClass().getResource("light-bulb-7-16.png"));
        lblNewLabel_2.setIcon(bulb);
        
        JLabel lblNewLabel_3 = new JLabel("To activate your listing, please fill in all of the required fields below.");
        lblNewLabel_3.setForeground(SystemColor.textHighlightText);
        lblNewLabel_3.setFont(new Font("Arial", Font.ITALIC, 14));
        lblNewLabel_3.setBounds(39, 10, 450, 13);
        panel_7.add(lblNewLabel_3);
        
        JLabel lblNewLabel_4 = new JLabel("Service Title");
        lblNewLabel_4.setForeground(new Color(0, 102, 153));
        lblNewLabel_4.setFont(new Font("Century Gothic", Font.BOLD, 15));
        lblNewLabel_4.setBounds(64, 74, 135, 27);
        panel_4.add(lblNewLabel_4);
        
        textField = new JTextField();
        textField.setBounds(162, 75, 327, 20);
        panel_4.add(textField);
        textField.setColumns(10);
        
        JLabel lblNewLabel_4_1 = new JLabel("Service Category");
        lblNewLabel_4_1.setForeground(new Color(0, 102, 153));
        lblNewLabel_4_1.setFont(new Font("Century Gothic", Font.BOLD, 15));
        lblNewLabel_4_1.setBounds(64, 105, 135, 27);
        panel_4.add(lblNewLabel_4_1);
        
        textField_1 = new JTextField();
        textField_1.setColumns(10);
        textField_1.setBounds(204, 111, 285, 20);
        panel_4.add(textField_1);
        
        JLabel lblNewLabel_4_1_1 = new JLabel("Description");
        lblNewLabel_4_1_1.setForeground(new Color(0, 102, 153));
        lblNewLabel_4_1_1.setFont(new Font("Century Gothic", Font.BOLD, 15));
        lblNewLabel_4_1_1.setBounds(102, 142, 97, 27);
        panel_4.add(lblNewLabel_4_1_1);
        
        JLabel lblNewLabel_4_2 = new JLabel("Price (PKR)");
        lblNewLabel_4_2.setForeground(new Color(0, 102, 153));
        lblNewLabel_4_2.setFont(new Font("Century Gothic", Font.BOLD, 15));
        lblNewLabel_4_2.setBounds(113, 227, 135, 27);
        panel_4.add(lblNewLabel_4_2);
        
        textField_3 = new JTextField();
        textField_3.setColumns(10);
        textField_3.setBounds(204, 234, 285, 20);
        panel_4.add(textField_3);
        
        JLabel lblNewLabel_4_2_1 = new JLabel("Availability");
        lblNewLabel_4_2_1.setForeground(new Color(0, 102, 153));
        lblNewLabel_4_2_1.setFont(new Font("Century Gothic", Font.BOLD, 15));
        lblNewLabel_4_2_1.setBounds(110, 264, 86, 27);
        panel_4.add(lblNewLabel_4_2_1);
        
        chckbxNewCheckBox = new JCheckBox("Available");
        chckbxNewCheckBox.setForeground(UIManager.getColor("CheckBox.darkShadow"));
        chckbxNewCheckBox.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 14));
        chckbxNewCheckBox.setBounds(204, 270, 147, 21);
        panel_4.add(chckbxNewCheckBox);
        
        chckbxNotAvailable = new JCheckBox("Not Available");
        chckbxNotAvailable.setForeground(UIManager.getColor("CheckBox.darkShadow"));
        chckbxNotAvailable.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 14));
        chckbxNotAvailable.setBounds(353, 270, 147, 21);
        panel_4.add(chckbxNotAvailable);
        
        JPanel postButton = new JPanel();
        postButton.setBackground(new Color(0, 102, 153));
        postButton.setBounds(379, 349, 135, 35);
        postButton.setLayout(null);
        postButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        postButton.setBorder(new javax.swing.border.LineBorder(new Color(0, 102, 153), 1, true));
        panel_4.add(postButton);
        
        JLabel lblPost = new JLabel("Post/Save");
        lblPost.setBounds(10, 0, 110, 35);
        postButton.add(lblPost);
        lblPost.setForeground(Color.WHITE);
        lblPost.setFont(new Font("Century Gothic", Font.BOLD, 14));
        lblPost.setHorizontalAlignment(JLabel.CENTER);

        postButton.addMouseListener(new MouseAdapter() {
            Color defaultColor = new Color(0, 102, 153);
            Color hoverColor = new Color(0, 82, 123);

            @Override
            public void mouseEntered(MouseEvent e) {
                postButton.setBackground(hoverColor);
                postButton.setBorder(new javax.swing.border.LineBorder(hoverColor, 1, true));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                postButton.setBackground(defaultColor);
                postButton.setBorder(new javax.swing.border.LineBorder(defaultColor, 1, true));
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                handleSave();
            }
        });

        
        JLabel lblNewLabel_4_1_2 = new JLabel("Estimated Duration");
        lblNewLabel_4_1_2.setForeground(new Color(0, 102, 153));
        lblNewLabel_4_1_2.setFont(new Font("Century Gothic", Font.BOLD, 15));
        lblNewLabel_4_1_2.setBounds(54, 301, 145, 27);
        panel_4.add(lblNewLabel_4_1_2);
        
        textField_4 = new JTextField();
        textField_4.setColumns(10);
        textField_4.setBounds(204, 308, 285, 20);
        panel_4.add(textField_4);
        
        textArea = new JTextArea();
        textArea.setBounds(204, 146, 285, 71);
        panel_4.add(textArea);
        
        JPanel panel_5 = new JPanel();
        panel_5.setBackground(SystemColor.control);
        panel_5.setBounds(10, 126, 150, 34);
        panel_1.add(panel_5);
        panel_5.setLayout(null);
        
        JLabel lblNewLabel_1 = new JLabel("Service Detail");
        lblNewLabel_1.setForeground(new Color(0, 102, 153));
        lblNewLabel_1.setFont(new Font("Century Gothic", Font.BOLD, 16));
        lblNewLabel_1.setBounds(10, 0, 140, 34);
        panel_5.add(lblNewLabel_1);
        
        JPanel panel_6 = new JPanel();
        panel_6.setBackground(new Color(0, 102, 153));
        panel_6.setBounds(10, 124, 150, 4);
        panel_1.add(panel_6);
        
        JPanel panel_8 = new JPanel();
        panel_8.setBackground(SystemColor.control);
        panel_8.setBounds(236, 0, 589, 36);
        contentPane.add(panel_8);
        panel_8.setLayout(null);

        JLabel lblNewLabel_6 = new JLabel("x");
        lblNewLabel_6.setForeground(new Color(46, 139, 87));
        lblNewLabel_6.setFont(new Font("Arial", Font.BOLD, 30));
        lblNewLabel_6.setBounds(564, 0, 25, 33);
        panel_8.add(lblNewLabel_6);

        // Add MouseListener to the label to handle click events
        lblNewLabel_6.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Close the window when the label is clicked
                Window window = SwingUtilities.getWindowAncestor(lblNewLabel_6);
                if (window != null) {
                    window.dispose();
                }
            }
        });

    }
    private void handleSave() {
        String title = textField.getText().trim();
        String category = textField_1.getText().trim();
        String description = textArea.getText().trim();
        String priceStr = textField_3.getText().trim();
        String duration = textField_4.getText().trim();

        if (title.isEmpty() || category.isEmpty() || priceStr.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please fill all required fields!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        BigDecimal price;
        try {
            price = new BigDecimal(priceStr);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Invalid price format!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean isAvailable = false;
        if (chckbxNewCheckBox.isSelected() && chckbxNotAvailable.isSelected()) {
            JOptionPane.showMessageDialog(null, "Select only one availability option!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        } else if (chckbxNewCheckBox.isSelected()) {
            isAvailable = true;
        } else if (!chckbxNotAvailable.isSelected()) {
            JOptionPane.showMessageDialog(null, "Select availability status!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection conn = DriverManager.getConnection(url)) {
        	String sql = "INSERT INTO Services (ServiceTitle, ServiceCategory, Description, Price, IsAvailable, EstimatedDuration, UserID) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                conn.setAutoCommit(false);
                
                pstmt.setString(1, title);
                pstmt.setString(2, category);
                pstmt.setString(3, description);
                pstmt.setBigDecimal(4, price);
                pstmt.setBoolean(5, isAvailable);
                pstmt.setString(6, duration);
                pstmt.setInt(7, userID);
                
                int rows = pstmt.executeUpdate();
                if (rows > 0) {
                    conn.commit();
                    JOptionPane.showMessageDialog(null, "Service saved successfully!");
                    clearForm();
                } else {
                    conn.rollback();
                    JOptionPane.showMessageDialog(null, "Save failed!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearForm() {
        textField.setText("");
        textField_1.setText("");
        textArea.setText("");
        textField_3.setText("");
        textField_4.setText("");
        chckbxNewCheckBox.setSelected(false);
        chckbxNotAvailable.setSelected(false);
    }

    
    private void addHoverAndOpenFrame(JLabel label, String frameTitle) {
        Font originalFont = label.getFont();
        Font hoverFont = originalFont.deriveFont(originalFont.getSize() + 1f);

        label.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                label.setFont(hoverFont);
            }

            public void mouseExited(MouseEvent e) {
                label.setFont(originalFont);
            }

           
        });
    }
}