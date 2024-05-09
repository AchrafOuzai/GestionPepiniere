import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.SwingConstants;

public class StockPlantes extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel panneauPrincipal;
	private Connection conn;

	
	 /**
     * Méthode pour établir la connexion à la base de données.
     */
	private void connectionBaseDonnees() {
	       

        try {
        	conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/gestionpepiniere", "root", "");
            System.out.println("connection avec succes");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la connexion a la base de donnees");
            e.printStackTrace();
        }
    }

	 /**
     * Constructeur de la classe StockPlantes.
     * Ce constructeur initialise l'interface utilisateur et établit la connexion à la base de données.
     * @throws SQLException si une erreur SQL survient
     */
	public StockPlantes() throws SQLException {
		
		
		
		connectionBaseDonnees();
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setSize(900,600);
		setLocationRelativeTo(null);
		
		panneauPrincipal = new JPanel();
		panneauPrincipal.setBackground(new Color(255, 255, 255));
		panneauPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(panneauPrincipal);
		panneauPrincipal.setLayout(null);
		
		JPanel panel_gauche = new JPanel();
		panel_gauche.setLayout(null);
		panel_gauche.setBackground(new Color(0, 128, 0));
		panel_gauche.setBounds(0, 111, 197, 452);
		panneauPrincipal.add(panel_gauche);
		
		JButton btnPlantes = new JButton("Liste des plantes");
		btnPlantes.setFont(new Font("Arial", Font.BOLD, 15));
		btnPlantes.setForeground(Color.WHITE);
		btnPlantes.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		btnPlantes.setBackground(new Color(0, 128, 0));
		btnPlantes.setBounds(5, 22, 177, 24);
		btnPlantes.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            		Plante plante;
					try {
						plante = new Plante();
						plante.setVisible(true);
            		plante.setLocationRelativeTo(null);
                 dispose();
					} catch (SQLException e1) {
						
						e1.printStackTrace();
					}
            		
            }
        });
		panel_gauche.add(btnPlantes);
		
		JButton btnStock = new JButton("Stock des plantes");
		btnStock.setFont(new Font("Arial", Font.BOLD, 15));
		btnStock.setForeground(Color.WHITE);
		btnStock.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		btnStock.setBackground(new Color(0, 128, 0));
		btnStock.setBounds(5, 56, 177, 23);
		panel_gauche.add(btnStock);
		
		JButton btnProduitsPhysiques = new JButton("Produits physiques");
		btnProduitsPhysiques.setFont(new Font("Arial", Font.BOLD, 15));
		btnProduitsPhysiques.setForeground(new Color(255, 255, 255));
		btnProduitsPhysiques.setBackground(new Color(0, 128, 0));
		btnProduitsPhysiques.setBounds(10, 90, 177, 23);
		btnProduitsPhysiques.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		btnProduitsPhysiques.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            		ProduitPhysique produitphysique;
					produitphysique = new ProduitPhysique();
					produitphysique.frame.setVisible(true);
					produitphysique.frame.setLocationRelativeTo(null);
             dispose();
            		
            }
        });
		panel_gauche.add(btnProduitsPhysiques);
		
		JButton btnProduitsChimiques = new JButton("Produits chimiques");
		btnProduitsChimiques.setFont(new Font("Arial", Font.BOLD, 15));
		btnProduitsChimiques.setForeground(new Color(255, 255, 255));
		btnProduitsChimiques.setBackground(new Color(0, 128, 0));
		btnProduitsChimiques.setBounds(10, 124, 177, 23);
		btnProduitsChimiques.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		btnProduitsChimiques.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            		ProduiTChemiques produitchemiques;
					
						produitchemiques = new ProduiTChemiques();
						produitchemiques.frame.setVisible(true);
						produitchemiques.frame.setLocationRelativeTo(null);
                 dispose();
					
					
            		
            }
        });
		panel_gauche.add(btnProduitsChimiques);
		
		JLabel labelTitreStock = new JLabel("Stock des plantes");
		labelTitreStock.setForeground(new Color(0, 128, 0));
		labelTitreStock.setFont(new Font("Snap ITC", Font.BOLD, 24));
		labelTitreStock.setBounds(388, 28, 280, 49);
		panneauPrincipal.add(labelTitreStock);
		
		JPanel panelArbre = new JPanel();
		panelArbre.setBackground(new Color(245, 255, 250));
		panelArbre.setBounds(273, 111, 202, 211);
		panelArbre.addMouseListener((MouseListener) new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            		Arbre arbre;
					try {
						arbre = new Arbre();
						 arbre.setVisible(true);
		                 arbre.setLocationRelativeTo(null);
		                 dispose();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
                
            }
        });
		panneauPrincipal.add(panelArbre);
		panelArbre.setLayout(null);
		
		JLabel labelImageArbre = new JLabel("");
		labelImageArbre.setIcon(new ImageIcon("src\\images\\arbre.jpg"));
		labelImageArbre.setBounds(10, 36, 182, 115);
		panelArbre.add(labelImageArbre);
		
		JLabel labelTitreArbre = new JLabel("Arbres");
		labelTitreArbre.setBounds(64, 11, 75, 14);
		panelArbre.add(labelTitreArbre);
		labelTitreArbre.setHorizontalAlignment(SwingConstants.CENTER);
		labelTitreArbre.setFont(new Font("Tahoma", Font.BOLD, 13));
		
		JLabel labelStockArbre = new JLabel("Stock :");
		labelStockArbre.setFont(new Font("Tahoma", Font.PLAIN, 12));
		labelStockArbre.setBounds(10, 186, 49, 14);
		panelArbre.add(labelStockArbre);
		
		JLabel labelNombreArbre = new JLabel();
		labelNombreArbre.setText(String.valueOf(nombreArbre()) );
		labelNombreArbre.setBounds(53, 187, 49, 14);
		panelArbre.add(labelNombreArbre);
		
		JPanel panelArbuste = new JPanel();
		panelArbuste.setLayout(null);
		panelArbuste.setBackground(new Color(245, 255, 250));
		panelArbuste.setBounds(589, 111, 202, 211);
		panelArbuste.addMouseListener((MouseListener) new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            		Arbuste arbuste;
					try {
						arbuste = new Arbuste();
						 arbuste.setVisible(true);
		                 arbuste.setLocationRelativeTo(null);
		                 dispose();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
                
            }
        });
		panneauPrincipal.add(panelArbuste);
		
		JLabel labelImageArbuste = new JLabel("");
		labelImageArbuste.setIcon(new ImageIcon("src\\images\\arbuste.jpg"));
		labelImageArbuste.setBounds(10, 36, 182, 115);
		panelArbuste.add(labelImageArbuste);
		
		JLabel labelTitreArbuste = new JLabel("Arbustes");
		labelTitreArbuste.setHorizontalAlignment(SwingConstants.CENTER);
		labelTitreArbuste.setFont(new Font("Tahoma", Font.BOLD, 13));
		labelTitreArbuste.setBounds(64, 11, 75, 14);
		panelArbuste.add(labelTitreArbuste);
		
		JLabel labelStockArbuste = new JLabel("Stock :");
		labelStockArbuste.setFont(new Font("Tahoma", Font.PLAIN, 12));
		labelStockArbuste.setBounds(10, 186, 42, 14);
		panelArbuste.add(labelStockArbuste);
		
		JLabel labelNombreArbuste = new JLabel();
		labelNombreArbuste.setText(String.valueOf(nombreArbuste()) );
		labelNombreArbuste.setBounds(52, 187, 49, 14);
		panelArbuste.add(labelNombreArbuste);
		
		JPanel panelFleur = new JPanel();
		panelFleur.setLayout(null);
		panelFleur.setBackground(new Color(245, 255, 250));
		panelFleur.setBounds(273, 333, 202, 211);
		panelFleur.addMouseListener((MouseListener) new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            		Fleur fleur;
					try {
						fleur = new Fleur();
						 fleur.setVisible(true);
		                 fleur.setLocationRelativeTo(null);
		                 dispose();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
                
            }
        });
		panneauPrincipal.add(panelFleur);
		
		JLabel labelImageFleur = new JLabel("");
		labelImageFleur.setIcon(new ImageIcon("src\\images\\fleur.jpg"));
		labelImageFleur.setBounds(10, 37, 182, 109);
		panelFleur.add(labelImageFleur);
		
		JLabel labelTitreFleur = new JLabel("Fleurs");
		labelTitreFleur.setHorizontalAlignment(SwingConstants.CENTER);
		labelTitreFleur.setFont(new Font("Tahoma", Font.BOLD, 13));
		labelTitreFleur.setBounds(64, 11, 75, 14);
		panelFleur.add(labelTitreFleur);
		
		JLabel labelStockFleur = new JLabel("Stock :");
		labelStockFleur.setFont(new Font("Tahoma", Font.PLAIN, 12));
		labelStockFleur.setBounds(10, 186, 43, 14);
		panelFleur.add(labelStockFleur);
		
		JLabel labelNombreFleur = new JLabel();
		labelNombreFleur.setText(String.valueOf(nombreFleur()) );
		labelNombreFleur.setBounds(53, 187, 49, 14);
		panelFleur.add(labelNombreFleur);
		
		JPanel panelPlanteSucculente = new JPanel();
		panelPlanteSucculente.setLayout(null);
		panelPlanteSucculente.setBackground(new Color(245, 255, 250));
		panelPlanteSucculente.setBounds(589, 333, 202, 211);
		panelPlanteSucculente.addMouseListener((MouseListener) new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            		PlanteSucculente planteSucculente;
					try {
						planteSucculente = new PlanteSucculente();
						planteSucculente.setVisible(true);
		            	planteSucculente.setLocationRelativeTo(null);
		                 dispose();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
            	
            }
        });
		panneauPrincipal.add(panelPlanteSucculente);
		
		JLabel labelImagePlanteSucculente = new JLabel("");
		labelImagePlanteSucculente.setIcon(new ImageIcon("src\\images\\plante_succulente.jpg"));
		labelImagePlanteSucculente.setBounds(10, 37, 182, 109);
		panelPlanteSucculente.add(labelImagePlanteSucculente);
		
		JLabel labelTitrePlanteSucculente = new JLabel("Plantes succulentes");
		labelTitrePlanteSucculente.setHorizontalAlignment(SwingConstants.CENTER);
		labelTitrePlanteSucculente.setFont(new Font("Tahoma", Font.BOLD, 13));
		labelTitrePlanteSucculente.setBounds(31, 11, 141, 14);
		panelPlanteSucculente.add(labelTitrePlanteSucculente);
		
		JLabel labelStockPlanteSucculente = new JLabel("Stock :");
		labelStockPlanteSucculente.setFont(new Font("Tahoma", Font.PLAIN, 12));
		labelStockPlanteSucculente.setBounds(10, 186, 42, 14);
		panelPlanteSucculente.add(labelStockPlanteSucculente);
		
		JLabel labelNombrePlanteSucculente = new JLabel();
		labelNombrePlanteSucculente.setText(String.valueOf(nombrePlanteSucculente()) );
		labelNombrePlanteSucculente.setBounds(51, 187, 49, 14);
		panelPlanteSucculente.add(labelNombrePlanteSucculente);
		
		JLabel label_logo = new JLabel("New label");
		label_logo.setIcon(new ImageIcon("src\\images\\palnt.png"));
		label_logo.setBounds(20, 11, 150, 92);
		panneauPrincipal.add(label_logo);
		
		
	}
	
	public int nombreArbre() throws SQLException {
	    int compteur = 0;
	    String requete = "SELECT count(*) FROM plante where type='Arbre'";
	    PreparedStatement statement = conn.prepareStatement(requete);

	    ResultSet resultSet = statement.executeQuery();
	    if (resultSet.next()) { 
	        compteur = resultSet.getInt(1); 
	    }

	    
	    resultSet.close();
	    statement.close();

	    return compteur;
	}
	 /**
     * Méthode pour obtenir le nombre d'arbres dans le stock.
     * @return le nombre d'arbres dans le stock
     * @throws SQLException si une erreur SQL survient
     */
	public int nombreArbuste() throws SQLException {
	    int compteur = 0;
	    String requete = "SELECT count(*) FROM plante where type='Arbuste'";
	    PreparedStatement statement = conn.prepareStatement(requete);

	    ResultSet resultSet = statement.executeQuery();
	    if (resultSet.next()) { 
	        compteur = resultSet.getInt(1); 
	    }

	    
	    resultSet.close();
	    statement.close();

	    return compteur;
	} 
	// Les méthodes suivantes (nombreArbuste, nombreFleur, nombrePlanteSucculente) sont similaires
    // Elles retournent le nombre d'arbustes, de fleurs et de plantes succulentes respectivement
	public int nombreFleur() throws SQLException {
	    int compteur = 0;
	    String requete = "SELECT count(*) FROM plante where type='Fleur'";
	    PreparedStatement statement = conn.prepareStatement(requete);

	    ResultSet resultSet = statement.executeQuery();
	    if (resultSet.next()) { 
	        compteur = resultSet.getInt(1); 
	    }

	    
	    resultSet.close();
	    statement.close();

	    return compteur;
	}
	public int nombrePlanteSucculente() throws SQLException {
	    int compteur = 0;
	    String requete = "SELECT count(*) FROM plante where type='Plante Succulente'";
	    PreparedStatement statement = conn.prepareStatement(requete);

	    ResultSet resultSet = statement.executeQuery();
	    if (resultSet.next()) { 
	        compteur = resultSet.getInt(1); 
	    }

	    
	    resultSet.close();
	    statement.close();

	    return compteur;
	}

}
