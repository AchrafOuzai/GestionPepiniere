import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.metal.MetalBorders.PaletteBorder;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JPasswordField;

public class Login extends JFrame {

	
	private JPanel contentPane;
	private JTextField textField_1;
	 private Connection connection;
	 private JPasswordField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public Login() {
		connection();
		setUndecorated(true);
	       
        setSize(600, 380);
        setLocationRelativeTo(null);
        
        JPanel panel = new JPanel();
        panel.setBackground(new Color(255, 255, 255));
        getContentPane().add(panel, BorderLayout.CENTER);
        panel.setLayout(null);
        
        JPanel panel_1 = new JPanel();
        panel_1.setBounds(0, 0, 226, 400);
        Color color = Color.decode("#15b63a");
        panel_1.setBackground(color);
        panel.add(panel_1);
        panel_1.setLayout(null);
        
        JLabel lblNewLabel_1 = new JLabel("New label");
        lblNewLabel_1.setIcon(new ImageIcon("src\\images\\logo2.png"));
        lblNewLabel_1.setBounds(-12, 65, 250, 365);
        panel_1.add(lblNewLabel_1);
        
        JLabel lblUsername = new JLabel("Nom Utilisateut");
        lblUsername.setHorizontalAlignment(SwingConstants.CENTER);
        lblUsername.setForeground(new Color(5, 127, 26));
        lblUsername.setFont(new Font("Arial", Font.PLAIN, 14));
        lblUsername.setBorder(new EmptyBorder(10, 0, 0, 0));
        lblUsername.setBounds(236, 184, 139, 34);
        panel.add(lblUsername);
        
        JLabel lblNewLabel = new JLabel("New label");
        lblNewLabel.setIcon(new ImageIcon("C:\\Users\\HP\\palntE.png"));
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setBounds(360, 57, 138, 96);
        panel.add(lblNewLabel);
        
        JLabel label_1 = new JLabel("Bienvenue dans Mon Pépinière");
        label_1.setHorizontalAlignment(SwingConstants.CENTER);
        label_1.setForeground(new Color(5, 127, 26));
        label_1.setFont(new Font("Arial", Font.BOLD, 20));
        label_1.setBorder(new EmptyBorder(10, 0, 0, 0));
        label_1.setBounds(242, 11, 336, 34);
        panel.add(label_1);
        
        JLabel lblMotPass = new JLabel("Mot Passe");
        lblMotPass.setHorizontalAlignment(SwingConstants.CENTER);
        lblMotPass.setForeground(new Color(5, 127, 26));
        lblMotPass.setFont(new Font("Arial", Font.PLAIN, 14));
        lblMotPass.setBorder(new EmptyBorder(10, 0, 0, 0));
        lblMotPass.setBounds(224, 236, 139, 34);
        panel.add(lblMotPass);
        
        textField_1 = new JTextField();
        textField_1.setColumns(10);
        textField_1.setBounds(360, 193, 194, 28);
        panel.add(textField_1);
        
        JButton btnNewButton = new JButton("Login");
        btnNewButton.setForeground(new Color(0, 64, 0));
        btnNewButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        	}
        });
        btnNewButton.setFont(new Font("Century Gothic", Font.BOLD, 17));
        btnNewButton.setBounds(383, 310, 154, 28);
        panel.add(btnNewButton);
        
        JLabel lblX = new JLabel("X");
        lblX.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		System.exit(0);
        	}
        });
        
        
       
        lblX.setHorizontalAlignment(SwingConstants.CENTER);
        lblX.setForeground(new Color(5, 127, 26));
        lblX.setFont(new Font("Arial", Font.PLAIN, 15));
        lblX.setBorder(new EmptyBorder(10, 0, 0, 0));
        lblX.setBounds(563, 0, 37, 34);
        panel.add(lblX);
        
        JLabel lblReset = new JLabel("Reset");
        lblReset.setHorizontalAlignment(SwingConstants.CENTER);
        lblReset.setForeground(new Color(5, 127, 26));
        lblReset.setFont(new Font("Arial", Font.PLAIN, 14));
        lblReset.setBorder(new EmptyBorder(10, 0, 0, 0));
        lblReset.setBounds(403, 335, 120, 34);
        panel.add(lblReset);
        
        textField = new JPasswordField();
        textField.setBounds(360, 245, 194, 28);
        panel.add(textField);

        
        lblReset.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                textField.setText("");
                textField_1.setText("");
            }
        });
       
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = textField_1.getText();
                String password = textField.getText();

               
                if (checkLogin(username, password)) {
                    try {
                    	
						Plante plante = new Plante();
						plante.setVisible(true);
						plante.setLocationRelativeTo(null);
						 dispose();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
                   
                } else {
                    JOptionPane.showMessageDialog(Login.this, "Nom utilisateur ou mot de passe incorrecte", "Erreur de connexion", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

	}
	private void connection() {
        String url = "jdbc:mysql://localhost:3306/gestionpepiniere";
        String username = "root";
        String password = "";

        try {
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connected to the database successfully.");
        } catch (SQLException e) {
            System.out.println("Failed to connect to the database.");
            e.printStackTrace();
        }
    }
	private boolean checkLogin(String nom_utilisateur, String mot_passe) {
	    String query = "SELECT * FROM administrateur WHERE nom_utilisateur = ? AND mot_passe = ?";
	    try {
	        PreparedStatement preparedStatement = connection.prepareStatement(query);
	        preparedStatement.setString(1, nom_utilisateur);
	        preparedStatement.setString(2, mot_passe);
	        ResultSet resultSet = preparedStatement.executeQuery();
	        return resultSet.next(); 
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false; 
	    }
	}
}
