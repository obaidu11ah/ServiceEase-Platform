import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.sql.*;
import java.math.BigDecimal;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

public class EditProfile extends JFrame {
	private int userID;
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private final JPanel panel = new JPanel();
    private final String url = "jdbc:sqlserver://localhost\\SQLEXPRESS;databaseName=ServiceEase;integratedSecurity=true;encrypt=true;trustServerCertificate=true;";
    private JTable table;
   
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
    
    //table vlaue fetch karny k leya function bnay ha '
    private void fetchServicesData() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        // Modified SQL to use the function
        String sql = "SELECT * FROM dbo.GetServicesByUser(?)";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, userID);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                model.addRow(new Object[] {
                    rs.getString("Service Title"),
                    rs.getString("Service Category"),
                    rs.getString("Description"),
                    rs.getBigDecimal("Price"),
                    rs.getString("Avail"),
                    rs.getString("Estimate time"),
                    rs.getTimestamp("Created Date"),  // Changed to getTimestamp
                    rs.getTimestamp("Last Modified"), // Changed to getTimestamp
                    rs.getString("Live")
                });
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading services: " + ex.getMessage());
        }
    }
    
    
    private String getUserFullName1(int userID) {
        String[] details = getUserDetails(userID);
        return (details != null && details.length > 0) ? details[0] : "User";
    }
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            EditProfile frame = new EditProfile(1); 
            frame.setLocationRelativeTo(null); // Hardcode a valid UserID for testing
            frame.setVisible(true);
            
            
        });
    }

    public EditProfile (int userID) {
    	this.userID = userID;
    	 setUndecorated(true);
    	 
    	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	     // Add to the left panel
    	
    	
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1180, 629);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        panel.setBounds(0, 0, 237, 629);

        panel.setBackground(new Color(0, 153, 119));
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
        lblDashboard.setBounds(60, 183, 150, 31);
        panel.add(lblDashboard);
        JLabel iconDashboard = new JLabel("");
        iconDashboard.setBounds(39, 186, 24, 25);
        panel.add(iconDashboard);
        iconDashboard.setIcon(new ImageIcon(this.getClass().getResource("dashboard-16.png")));
       
        lblDashboard.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblDashboard.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose(); // Close current EditProfile frame
                Dashboard dashboard = new Dashboard(userID); // Assuming Dashboard class accepts userID
                dashboard.setVisible(true);
                dashboard.setLocationRelativeTo(null); // Center the Dashboard window
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                lblDashboard.setFont(new Font("Century Gothic", Font.BOLD, 13)); // Slightly bigger on hover
            }

            @Override
            public void mouseExited(MouseEvent e) {
                lblDashboard.setFont(new Font("Century Gothic", Font.BOLD, 12)); // Reset on exit
            }
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
            	dispose();
                service editProfileFrame = new service(userID);
                editProfileFrame.setVisible(true);
                editProfileFrame.setLocationRelativeTo(null); 
                
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
            	dispose();
                BookedServ d= new BookedServ(userID);
                d.setVisible(true);
                d.setLocationRelativeTo(null); 
                
            }
            // Keep existing mouseEntered/mouseExited handlers
        });
        
        JLabel lblNewLabel_5 = new JLabel("Welcome!..");
        lblNewLabel_5.setForeground(new Color(255, 255, 255));
        lblNewLabel_5.setFont(new Font("Century Gothic", Font.BOLD, 14));
        lblNewLabel_5.setBounds(114, 32, 95, 23);
        panel.add(lblNewLabel_5);
        

        String userName = getUserFullName1(userID); // Fetch name using userID
        JLabel lblNewLabel_5_11 = new JLabel(userName); // Set the label text
        lblNewLabel_5_11.setForeground(Color.WHITE);
        lblNewLabel_5_11.setFont(new Font("Century Gothic", Font.BOLD, 14));
        lblNewLabel_5_11.setBounds(132, 54, 95, 23);
        panel.add(lblNewLabel_5_11); // Add label to the left panel
        // Right side content panel
        
        JPanel panel_1 = new JPanel();
        panel_1.setBounds(236, 34, 943, 595);
        panel_1.setBackground(new Color(255, 255, 255));
        contentPane.add(panel_1);
        panel_1.setLayout(null);

        JLabel image = new JLabel("");
        image.setBounds(320, 21, 300, 34);
        panel_1.add(image);
        ImageIcon emailIconImage = new ImageIcon(this.getClass().getResource("Service Provider(4).png"));
        image.setIcon(emailIconImage);
        
        JPanel panel_3 = new JPanel();
        panel_3.setBackground(new Color(0, 153, 119));
        panel_3.setBounds(10, 65, 923, 37);
        panel_1.add(panel_3);
        panel_3.setLayout(null);
        
        JLabel lblNewLabel = new JLabel("\"Hello here's your dashboard overview!\"");
        lblNewLabel.setForeground(new Color(255, 255, 255));
        lblNewLabel.setFont(new Font("Century Gothic", Font.BOLD, 18));
        lblNewLabel.setBounds(10, 0, 466, 37);
        panel_3.add(lblNewLabel);
        
        JPanel panel_4 = new JPanel();
        panel_4.setBackground(SystemColor.control);
        panel_4.setBounds(10, 156, 923, 439);
        panel_1.add(panel_4);
        panel_4.setLayout(null);
        
        ImageIcon bulb = new ImageIcon(this.getClass().getResource("light-bulb-7-16.png"));
        
        JLabel lblNewLabel_2 = new JLabel("Publish services");
        lblNewLabel_2.setFont(new Font("Arial Black", Font.BOLD, 20));
        lblNewLabel_2.setForeground(new Color(0, 0, 0));
        lblNewLabel_2.setBounds(20, 27, 241, 37);
        panel_4.add(lblNewLabel_2);
        
        table = new JTable();
        table.setModel(new DefaultTableModel(
        	new Object[][] {
        	},
        	new String[] {
        		"Service Title", "Category", "Description", "Price", "Avail", "EstimateTime ", "Created Date", "Last Modified", "Live"
        	}
        ));
     // Replace this:
        table.setBounds(37, 94, 501, 276);
        panel_4.add(table);

        // With this:
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 94, 893, 255);
        panel_4.add(scrollPane);
        String[] userDetails = getUserDetails(userID); 
        
        //function call
        styleTableHeaders(); 
        fetchServicesData();
        configureColumns();
        // Set FullName Label
        JLabel lblNewLabel_5_111 = new JLabel(userDetails[0]); 
        lblNewLabel_5_111.setForeground(Color.WHITE);
        lblNewLabel_5_111.setFont(new Font("Century Gothic", Font.BOLD, 14));
        lblNewLabel_5_111.setBounds(132, 54, 95, 23);
        panel.add(lblNewLabel_5_111);
        
        JPanel panel_5 = new JPanel();
        panel_5.setBackground(SystemColor.control);
        panel_5.setBounds(10, 126, 164, 34);
        panel_1.add(panel_5);
        panel_5.setLayout(null);
        
        JLabel lblNewLabel_1 = new JLabel("Dashboard");
        lblNewLabel_1.setForeground(new Color(0, 102, 153));
        lblNewLabel_1.setFont(new Font("Century Gothic", Font.BOLD, 16));
        lblNewLabel_1.setBounds(10, 0, 141, 34);
        panel_5.add(lblNewLabel_1);
        
        JPanel panel_6 = new JPanel();
        panel_6.setBackground(new Color(0, 102, 153));
        panel_6.setBounds(10, 119, 164, 10);
        panel_1.add(panel_6);
        
        JPanel panel_8 = new JPanel();
        panel_8.setBounds(236, 0, 943, 36);
        panel_8.setBackground(SystemColor.control);
        contentPane.add(panel_8);
        panel_8.setLayout(null);

        JLabel lblNewLabel_6 = new JLabel("x");
        lblNewLabel_6.setForeground(new Color(46, 139, 87));
        lblNewLabel_6.setFont(new Font("Arial", Font.BOLD, 30));
        lblNewLabel_6.setBounds(918, 0, 25, 33);
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
        
        JPanel postButton = new JPanel();
        postButton.setBackground(new Color(0, 102, 153));
        postButton.setBounds(768, 359, 145, 35);
        postButton.setLayout(null);
        postButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        postButton.setBorder(new javax.swing.border.LineBorder(new Color(0, 102, 153), 1, true));
        panel_4.add(postButton);
        
        JLabel lblPost = new JLabel("Delete Services");
        lblPost.setBounds(10, 0, 125, 35);
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
                int selectedRow = table.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(EditProfile.this, 
                        "Please select a service to delete!", 
                        "No Selection", 
                        JOptionPane.WARNING_MESSAGE);
                    return;
                }

                String serviceTitle = (String) table.getValueAt(selectedRow, 0);
                
                int confirm = JOptionPane.showConfirmDialog(
                    EditProfile.this,
                    "Are you sure you want to delete '" + serviceTitle + "'?",
                    "Confirm Delete",
                    JOptionPane.YES_NO_OPTION
                );

                if (confirm == JOptionPane.YES_OPTION) {
                    try (Connection conn = DriverManager.getConnection(url);
                         PreparedStatement pstmt = conn.prepareStatement(
                             "DELETE FROM Services WHERE ServiceTitle = ? AND UserID = ?")) {

                        pstmt.setString(1, serviceTitle);
                        pstmt.setInt(2, userID);
                        
                        int rowsAffected = pstmt.executeUpdate();
                        
                        if (rowsAffected > 0) {
                            // Remove from JTable
                            ((DefaultTableModel) table.getModel()).removeRow(selectedRow);
                            JOptionPane.showMessageDialog(EditProfile.this,
                                "Service deleted successfully!");
                        } else {
                            JOptionPane.showMessageDialog(EditProfile.this,
                                "Deletion failed! Service not found.",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(EditProfile.this,
                            "Database error: " + ex.getMessage(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
       
        });

    }

    private void styleTableHeaders() {
        JTableHeader header = table.getTableHeader();
        
        // Set bold font with size 14
        header.setFont(new Font("Century Gothic", Font.BOLD, 10));
        
        // Set background and foreground colors
        header.setBackground(new Color(0, 153, 119)); // Dark green
        header.setForeground(Color.WHITE);
        
        // Set header height
        header.setPreferredSize(new Dimension(header.getWidth(), 30)); // 35px height
    }
    
    
 // 3. Column Width Configuration (add this method)
    private void configureColumns() {
        TableColumnModel cm = table.getColumnModel();
        cm.getColumn(0).setPreferredWidth(200);  // Service Title
        cm.getColumn(1).setPreferredWidth(200);  // Service Category
        cm.getColumn(2).setPreferredWidth(350);  // Description
        cm.getColumn(3).setPreferredWidth(80);  // Price
        cm.getColumn(4).setPreferredWidth(80);  // IsAvailable
        cm.getColumn(5).setPreferredWidth(140);  // Estimate time
        cm.getColumn(6).setPreferredWidth(200);  // Created Date
        cm.getColumn(7).setPreferredWidth(200);  // Last Modified
        cm.getColumn(8).setPreferredWidth(70);   // Publish
        
        // Make columns non-resizable
        for(int i=0; i <cm.getColumnCount(); i++) {
            cm.getColumn(i).setResizable(false);
        }
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