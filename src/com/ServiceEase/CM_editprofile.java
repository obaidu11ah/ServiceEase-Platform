import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.sql.*;
import java.math.BigDecimal;

public class CM_editprofile extends JFrame {
	private int userID;
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private final JPanel panel = new JPanel();
    private final String url = "jdbc:sqlserver://localhost\\SQLEXPRESS;databaseName=ServiceEase;integratedSecurity=true;encrypt=true;trustServerCertificate=true;";
   
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
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            CM_editprofile frame = new CM_editprofile(1); 
            frame.setLocationRelativeTo(null); // Hardcode a valid UserID for testing
            frame.setVisible(true);
            
            
        });
    }

    public CM_editprofile(int userID) {
    	this.userID = userID;
    	 setUndecorated(true);
    	 
    	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	     // Add to the left panel
    	
    	
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 825, 629);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        panel.setBackground(new Color(0, 153, 119));
        panel.setBounds(0, 0, 237, 629);
        contentPane.add(panel);
        panel.setLayout(null);
        panel.setBackground(new Color(83, 125, 93));
        
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
            	dispose();
                CM editProfileFrame = new CM(userID);
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
        lblDashboard.setFont(new Font("Century Gothic", Font.BOLD, 13));
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
            	JOptionPane.showMessageDialog(null, "Already on manage Profile!!!");
                
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
        // Right side content panel
        
        JPanel panel_1 = new JPanel();
        panel_1.setBackground(new Color(255, 250, 240));
        panel_1.setBounds(236, 34, 589, 595);
        contentPane.add(panel_1);
        panel_1.setLayout(null);

        JLabel image = new JLabel("");
        image.setBounds(153, 21, 300, 34);
        panel_1.add(image);
        ImageIcon emailIconImage = new ImageIcon(this.getClass().getResource("Servicenow(4).png"));
        image.setIcon(emailIconImage);
        
        JPanel panel_3 = new JPanel();
        panel_3.setBackground(new Color(158, 188, 138));
        panel_3.setBounds(10, 65, 569, 37);
        panel_1.add(panel_3);
        panel_3.setLayout(null);
        
        JLabel lblNewLabel = new JLabel("View your profile!!!");
        lblNewLabel.setBackground(new Color(47, 79, 79));
        lblNewLabel.setForeground(new Color(47, 79, 79));
        lblNewLabel.setFont(new Font("Century Gothic", Font.BOLD, 18));
        lblNewLabel.setBounds(10, 0, 300, 37);
        panel_3.add(lblNewLabel);
        
        JPanel panel_4 = new JPanel();
        panel_4.setBackground(new Color(210, 208, 160));
        panel_4.setBounds(10, 156, 569, 470);
        panel_1.add(panel_4);
        panel_4.setLayout(null);
        
        ImageIcon bulb = new ImageIcon(this.getClass().getResource("light-bulb-7-16.png"));
        
        JLabel lblNewLabel_4 = new JLabel("FullName");
        lblNewLabel_4.setForeground(new Color(0, 102, 153));
        lblNewLabel_4.setFont(new Font("Century Gothic", Font.BOLD, 15));
        lblNewLabel_4.setBounds(64, 74, 75, 27);
        panel_4.add(lblNewLabel_4);
        
        JLabel lblNewLabel_4_1 = new JLabel("Email");
        lblNewLabel_4_1.setForeground(new Color(0, 102, 153));
        lblNewLabel_4_1.setFont(new Font("Century Gothic", Font.BOLD, 15));
        lblNewLabel_4_1.setBounds(64, 111, 135, 27);
        panel_4.add(lblNewLabel_4_1);
        
        JLabel lblNewLabel_4_1_1 = new JLabel("Password");
        lblNewLabel_4_1_1.setForeground(new Color(0, 102, 153));
        lblNewLabel_4_1_1.setFont(new Font("Century Gothic", Font.BOLD, 15));
        lblNewLabel_4_1_1.setBounds(64, 142, 97, 27);
        panel_4.add(lblNewLabel_4_1_1);
        
        JLabel lblNewLabel_2 = new JLabel("Profile Infomration:-");
        lblNewLabel_2.setFont(new Font("Arial Black", Font.BOLD, 20));
        lblNewLabel_2.setForeground(new Color(47, 79, 79));
        lblNewLabel_2.setBounds(20, 27, 241, 37);
        panel_4.add(lblNewLabel_2);
        
        JLabel fullname = new JLabel("");
        fullname.setBounds(148, 83, 164, 18);
        panel_4.add(fullname);
        
        JLabel Email = new JLabel("");
        Email.setBounds(148, 120, 164, 18);
        panel_4.add(Email);
        
        JLabel password = new JLabel("");
        password.setBounds(148, 148, 164, 18);
        panel_4.add(password);
        
        String[] userDetails = getUserDetails(userID); 

        // Set FullName Label
        JLabel lblNewLabel_5_111 = new JLabel(userDetails[0]); 
        lblNewLabel_5_111.setForeground(Color.WHITE);
        lblNewLabel_5_111.setFont(new Font("Century Gothic", Font.BOLD, 14));
        lblNewLabel_5_111.setBounds(132, 54, 95, 23);
        panel.add(lblNewLabel_5_111);
        
        JLabel lblEditProfile_1 = new JLabel("Home");
        lblEditProfile_1.setForeground(new Color(240, 255, 240));
        lblEditProfile_1.setFont(new Font("Century Gothic", Font.BOLD, 13));
        lblEditProfile_1.setBounds(60, 205, 150, 31);
        panel.add(lblEditProfile_1);
        
        JLabel lblNewLabel_3 = new JLabel("");
        lblNewLabel_3.setBounds(39, 211, 23, 16);
        panel.add(lblNewLabel_3);
        
        lblNewLabel_3.setIcon(new ImageIcon(this.getClass().getResource("home-7-16.png")));
       
        
     // ======== ADD THIS SECTION ======== //
        lblNewLabel_3.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblEditProfile_1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	dispose();
                CM editProfileFrame = new CM(userID);
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
        // Right Panel mein Email/Password Labels Set Karo
        fullname.setText(userDetails[0]); // FullName
        Email.setText(userDetails[1]);    // Email
        password.setText(userDetails[2]); // Password
        
        JPanel panel_5 = new JPanel();
        panel_5.setBackground(new Color(210, 208, 160));
        panel_5.setBounds(10, 126, 107, 34);
        panel_1.add(panel_5);
        panel_5.setLayout(null);
        
        JLabel lblNewLabel_1 = new JLabel("Pofile");
        lblNewLabel_1.setForeground(new Color(0, 102, 153));
        lblNewLabel_1.setFont(new Font("Century Gothic", Font.BOLD, 16));
        lblNewLabel_1.setBounds(33, 0, 46, 34);
        panel_5.add(lblNewLabel_1);
        
        JPanel panel_6 = new JPanel();
        panel_6.setBackground(new Color(47, 79, 79));
        panel_6.setBounds(10, 119, 107, 10);
        panel_1.add(panel_6);
        
        JPanel panel_8 = new JPanel();
        panel_8.setBackground(new Color(255, 250, 240));
        panel_8.setBounds(236, 0, 589, 36);
        contentPane.add(panel_8);
        panel_8.setLayout(null);

        JLabel lblNewLabel_6 = new JLabel("x");
        lblNewLabel_6.setForeground(new Color(47, 79, 79));
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

    private String getUserFullName(int userID2) {
		// TODO Auto-generated method stub
		return null;
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