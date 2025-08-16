	import java.awt.*;
	import java.awt.event.*;
	import javax.swing.*;
	import javax.swing.border.*;
	import javax.swing.table.DefaultTableModel;
	import javax.swing.table.JTableHeader;
	import javax.swing.table.TableColumnModel;
	
	
	import java.sql.*;
	import java.math.BigDecimal;
	
	public class CM_dashboard extends JFrame {
		private int userID;
	    private static final long serialVersionUID = 1L;
	    private JPanel contentPane;
	    private final JPanel panel = new JPanel();
	    private final String url = "jdbc:sqlserver://localhost\\SQLEXPRESS;databaseName=ServiceEase;integratedSecurity=true;encrypt=true;trustServerCertificate=true;";
	    private JTable table; // Declared here
	    
	    
	    private String[] getUserDetails(int userID) { // Rename method
	        String[] details = new String[3]; // [0]=FullName, [1]=Email, [2]=Password
	        String sql = "SELECT FullName, Email, Password FROM Users WHERE UserID = ?"; // Add columns
	
	        try (Connection conn = DriverManager.getConnection(url);
	             PreparedStatement pstmt = conn.prepareStatement(sql)) {
	
	            pstmt.setInt(1, userID);
	            ResultSet rs = pstmt.executeQuery();
	
	            if (rs.next()) {
	                details[0] = rs.getString("FullName");
	                details[1] = rs.getString("Email");  // Add Email
	                details[2] = rs.getString("Password"); // Add Password
	            }
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	        }
	        return details;
	    }
	    private void fetchServicesData() {
	        DefaultTableModel model = (DefaultTableModel) table.getModel();
	        model.setRowCount(0); 
	        String sql = "SELECT * FROM dbo.GetBookedServicesByCustomer(?)"; 
	        
	        try (Connection conn = DriverManager.getConnection(url);
	             PreparedStatement pstmt = conn.prepareStatement(sql)) {
	
	            pstmt.setInt(1, userID); // Pass customer's UserID
	            ResultSet rs = pstmt.executeQuery();
	
	            while (rs.next()) {
	                model.addRow(new Object[] {
	                    rs.getString("BookedService"),
	                    rs.getString("ProviderName"),
	                    rs.getBigDecimal("Price"),
	                    rs.getTimestamp("BookingDate"),
	                    rs.getString("DeliveryStatus")
	                });
	            }
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
	        }
	    }
	    private String getUserFullName1(int userID) {
	        String[] details = getUserDetails(userID);
	        return (details != null && details.length > 0) ? details[0] : "User";
	    }
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
	            CM_dashboard frame = new CM_dashboard(1); 
	            frame.setLocationRelativeTo(null); // Hardcode a valid UserID for testing
	            frame.setVisible(true);
	        });
	    }
	
	    public CM_dashboard(int userID) {
	    	this.userID = userID;
	    	 setUndecorated(true);
	    	 
	    	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    	     // Add to the left panel
	    	
	    	    setBounds(100, 100, 1180, 620);
	    	    setLocationRelativeTo(null); // ✅ Add this line
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        contentPane = new JPanel();
	        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	        setContentPane(contentPane);
	        contentPane.setLayout(null);
	        panel.setBounds(0, 0, 237, 629);
	
	        panel.setBackground(new Color(83, 125, 93));
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
	        lblEditProfile.setBounds(60, 176, 150, 31);
	        lblEditProfile.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // Make it look clickable
	        panel.add(lblEditProfile);
	
	        
	        // Add click event to open EditProfile frame
	     // Service.java mein, EditProfile label ka MouseListener:
	     // service.java ke lblEditProfile MouseListener mein:
	
	        
	
	        // Create and set icon for profile
	        JLabel iconProfile = new JLabel("");
	        iconProfile.setBounds(39, 180, 24, 23);
	        iconProfile.setIcon(new ImageIcon(this.getClass().getResource("edit-user-16.png")));
	        panel.add(iconProfile);
	
	        // Add hover and click event
	        lblEditProfile.addMouseListener(new MouseAdapter() {
	            @Override
	            public void mouseClicked(MouseEvent e) {
	            	JOptionPane.showMessageDialog(null, "Already on Dashboard!!!");
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
	        lblDashboard.setBounds(60, 204, 150, 31);
	        panel.add(lblDashboard);
	        JLabel iconDashboard = new JLabel("");
	        iconDashboard.setBounds(39, 210, 24, 25);
	        panel.add(iconDashboard);
	        iconDashboard.setIcon(new ImageIcon(this.getClass().getResource("dashboard-16.png")));
	        addHoverAndOpenFrame(lblDashboard, "Dashboard");
	        
	        lblDashboard.addMouseListener(new MouseAdapter() {
	            @Override
	            public void mouseClicked(MouseEvent e) {
	            	dispose();
	                CM_editprofile editProfileFrame = new CM_editprofile(userID);
	                editProfileFrame.setVisible(true);
	                editProfileFrame.setLocationRelativeTo(null); 
	                
	            }
	            // Keep existing mouseEntered/mouseExited handlers
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
	        
	        JLabel lblEditProfile_1 = new JLabel("Home");
	        lblEditProfile_1.setForeground(new Color(240, 255, 240));
	        lblEditProfile_1.setFont(new Font("Century Gothic", Font.BOLD, 13));
	        lblEditProfile_1.setBounds(60, 151, 150, 31);
	        panel.add(lblEditProfile_1);
	
	
	        // ======== ADD THIS SECTION ======== //
	        lblEditProfile_1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	        lblEditProfile_1.addMouseListener(new MouseAdapter() {
	            @Override
	            public void mouseClicked(MouseEvent e) {
	            	dispose();
	                CM editProfileFrame = new CM (userID);
	                editProfileFrame.setVisible(true);
	                editProfileFrame.setLocationRelativeTo(null); 
	            }
	
	            @Override
	            public void mouseEntered(MouseEvent e) {
	                lblEditProfile_1.setFont(new Font("Century Gothic", Font.BOLD, 14)); // Hover size
	            }
	
	            @Override
	            public void mouseExited(MouseEvent e) {
	                lblEditProfile_1.setFont(new Font("Century Gothic", Font.BOLD, 13)); // Original size
	            }
	        });
	        // ======== END OF ADDITION ======== //
	        JLabel lblNewLabel_2 = new JLabel("");
	        lblNewLabel_2.setBounds(39, 161, 24, 13);
	        panel.add(lblNewLabel_2);
	        lblNewLabel_2.setIcon(new ImageIcon(this.getClass().getResource("home-7-16.png")));
	        panel.add(lblNewLabel_2);
	        
	        
	        // Right side content panel
	        
	        JPanel panel_1 = new JPanel();
	        panel_1.setBounds(236, 34, 944, 595);
	        panel_1.setBackground(new Color(255, 250, 240));
	        contentPane.add(panel_1);
	        panel_1.setLayout(null);
	        ImageIcon emailIconImage = new ImageIcon(this.getClass().getResource("Servicenow(4).png"));
	        
	        JPanel panel_4 = new JPanel();
	        panel_4.setBackground(new Color(210, 208, 160));
	        panel_4.setBounds(0, 156, 934, 470);
	        panel_1.add(panel_4);
	        panel_4.setLayout(null);
	        
	        // --- FIX STARTS HERE ---
	        table = new JTable(); // Initialize the JTable object
	        // --- FIX ENDS HERE ---
	
	        table.setModel(new DefaultTableModel(
	        	    new Object[][] {},
	        	    new String[] {
	        	        "Booked Service", "Provider Name", "Price", "Booking Date", "Delivery Status"
	        	    }
	        	));
	     // Replace this:
	        table.setBounds(37, 94, 501, 276);
	        panel_4.add(table);
	            // With this:
	            JScrollPane scrollPane = new JScrollPane(table);
	            scrollPane.setBounds(20, 26, 854, 255);
	            panel_4.add(scrollPane);
	            String[] userDetails = getUserDetails(userID); 
	            
	            fetchServicesData();
	            styleTableHeaders();
	            
	        JPanel panel_5 = new JPanel();
	        panel_5.setForeground(new Color(0, 0, 0));
	        panel_5.setBackground(new Color(210, 208, 160));
	        panel_5.setBounds(0, 126, 160, 34);
	        panel_1.add(panel_5);
	        panel_5.setLayout(null);
	        
	        JLabel lblNewLabel_1 = new JLabel("Booked Service"); // Typo: "Available Service"
	        lblNewLabel_1.setBounds(10, 0, 150, 34);
	        panel_5.add(lblNewLabel_1);
	        lblNewLabel_1.setForeground(new Color(83, 125, 93));
	        lblNewLabel_1.setFont(new Font("Century Gothic", Font.BOLD, 16));
	        
	        JPanel panel_6 = new JPanel();
	        panel_6.setForeground(new Color(47, 79, 79));
	        panel_6.setBackground(new Color(47, 79, 79));
	        panel_6.setBounds(0, 122, 160, 4);
	        panel_1.add(panel_6);
	        
	        JPanel panel_3 = new JPanel();
	        panel_3.setBackground(new Color(158, 188, 138));
	        panel_3.setForeground(new Color(60, 179, 113));
	        panel_3.setBounds(0, 10, 934, 68);
	        panel_1.add(panel_3);
	                panel_3.setLayout(null);
	        
	               JLabel image = new JLabel("");
	                image.setBounds(10, 10, 170, 60);
	                panel_3.add(image);
	                image.setForeground(new Color(152, 251, 152));
	                image.setIcon(emailIconImage);
	        
	        JLabel lblNewLabel = new JLabel("");
	        lblNewLabel.setBounds(382, 55, 0, 0);
	        panel_3.add(lblNewLabel);
	        
	        JPanel panel_9 = new JPanel();
	        panel_9.setBackground(new Color(47, 79, 79));
	        panel_9.setBounds(796, 10, 118, 4);
	        panel_3.add(panel_9);
	        
	        JPanel panel_9_1 = new JPanel();
	        panel_9_1.setBounds(473, 28, 73, 0);
	        panel_3.add(panel_9_1);
	        
	        JPanel panel_9_2 = new JPanel();
	        panel_9_2.setBackground(new Color(47, 79, 79));
	        panel_9_2.setBounds(829, 23, 85, 5);
	        panel_3.add(panel_9_2);
	        
	        JPanel panel_9_2_1 = new JPanel();
	        panel_9_2_1.setBackground(new Color(47, 79, 79));
	        panel_9_2_1.setBounds(872, 38, 42, 4);
	        panel_3.add(panel_9_2_1);
	        
	        JPanel panel_8 = new JPanel();
	        panel_8.setBounds(236, 0, 944, 36);
	        panel_8.setBackground(new Color(255, 250, 240));
	        contentPane.add(panel_8);
	        panel_8.setLayout(null);
	
	        JLabel lblNewLabel_6 = new JLabel("x");
	        lblNewLabel_6.setForeground(new Color(47, 79, 79));
	        lblNewLabel_6.setFont(new Font("Arial", Font.BOLD, 30));
	        lblNewLabel_6.setBounds(919, 0, 25, 33);
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
	
	    private void styleTableHeaders() {
	        // Ensure 'table' is not null before trying to get its header
	        if (table != null) {
	            JTableHeader header = table.getTableHeader();
	            
	            // Set bold font with size 14
	            header.setFont(new Font("Century Gothic", Font.BOLD, 10));
	            
	            // Set background and foreground colors
	            header.setBackground(new Color(83, 125, 93)); // Dark green
	            header.setForeground(Color.WHITE);
	            
	            // Set header height
	            header.setPreferredSize(new Dimension(header.getWidth(), 30)); // 35px height
	        }
	    }
	    
	    
	 // 3. Column Width Configuration (add this method)
	    private void configureColumns() {
	        // Ensure 'table' is not null before trying to get its column model
	        if (table != null) {
	            TableColumnModel cm = table.getColumnModel();
	            cm.getColumn(0).setPreferredWidth(200);  // Service Title (Visible Index 0)
	            cm.getColumn(1).setPreferredWidth(150);  // Service Category (Index 1)
	            cm.getColumn(2).setPreferredWidth(120);  // Description (Index 2)
	            cm.getColumn(3).setPreferredWidth(120);   // Price (Index 3)
	            cm.getColumn(4).setPreferredWidth(120);   // Avail (Index 4)
	            // Estimate Time (Index 5)
	            // Make columns non-resizable
	            for(int i=0; i <cm.getColumnCount(); i++) {
	                cm.getColumn(i).setResizable(false);
	            }
	        }
	    }
	    
	
	    private void addHoverAndOpenFrame(JLabel label, String frameTitle) {
	        Font originalFont = label.getFont();
	        Font hoverFont = originalFont.deriveFont(originalFont.getSize() + 1f);
	
	        label.addMouseListener(new MouseAdapter() {
	            public void mouseEntered(MouseEvent e) {
	                label.setFont(hoverFont);
	            }
	
	            public void  mouseExited(MouseEvent e) { // Typo: 'void' here
	                label.setFont(originalFont);
	            }
	
	           
	        });
	    }
	}