import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

public class PlanteSucculente extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel panneauPrincipal;
	private JTable table_plantes;
	private JTextField champ_recherche;
	private Connection conn;
	private byte[] image;

	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Arbre frame = new Arbre();
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	private void connectionBaseDonnees() {
	       

        try {
        	conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/gestionpepiniere", "root", "");
            System.out.println("connection avec succes");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la connexion a la base de donnees");
            e.printStackTrace();
        }
    }

	
	public PlanteSucculente() throws SQLException {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setLocationRelativeTo(null);
		setSize(900, 600);
		panneauPrincipal = new JPanel();
		panneauPrincipal.setBackground(new Color(255, 255, 255));
		panneauPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(panneauPrincipal);
		panneauPrincipal.setLayout(null);
		
		JPanel panel_gauche = new JPanel();
		panel_gauche.setBounds(0, 111, 197, 452);
		panel_gauche.setBackground(new Color(0, 128, 0));
		panneauPrincipal.add(panel_gauche);
		panel_gauche.setLayout(null);
		
		JButton btnPlantes = new JButton("Liste des plantes");
		btnPlantes.setFont(new Font("Arial", Font.BOLD, 15));
		btnPlantes.setBorder(null);
		btnPlantes.setForeground(new Color(255, 255, 255));
		btnPlantes.setBounds(10, 22, 177, 23);
		btnPlantes.setBackground(new Color(0, 128, 0));
		btnPlantes.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
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
		btnStock.setBorder(null);
		btnStock.setForeground(new Color(255, 255, 255));
		btnStock.setBackground(new Color(0, 128, 0));
		btnStock.setBounds(10, 56, 177, 23);
		btnStock.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		btnStock.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            		StockPlantes stockPlantes;
					try {
						stockPlantes = new StockPlantes();
						stockPlantes.setVisible(true);
            		stockPlantes.setLocationRelativeTo(null);
                 dispose();
					} catch (SQLException e1) {
						
						e1.printStackTrace();
					}
            		
            }
        });
		panel_gauche.add(btnStock);
		
		JButton btnProduitsPhysiques = new JButton("Produits physiques");
		btnProduitsPhysiques.setFont(new Font("Arial", Font.BOLD, 15));
		btnProduitsPhysiques.setForeground(new Color(255, 255, 255));
		btnProduitsPhysiques.setBackground(new Color(0, 128, 0));
		btnProduitsPhysiques.setBounds(10, 226, 177, 23);
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
		btnProduitsChimiques.setBounds(10, 260, 177, 23);
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
		JButton btnArbre = new JButton("Arbres");
		btnArbre.setBackground(new Color(0, 128, 0));
		btnArbre.setForeground(new Color(255, 255, 255));
		btnArbre.setFont(new Font("Arial", Font.BOLD, 12));
		btnArbre.setBorder(null);
		btnArbre.setBounds(10, 85, 89, 23);
		btnArbre.addActionListener(new ActionListener() {
			 public void actionPerformed(ActionEvent e) {
       		Arbre arbre;
					try {
						arbre = new Arbre();
						arbre.setVisible(true);
						arbre.setLocationRelativeTo(null);
            dispose();
					} catch (SQLException e1) {
						
						e1.printStackTrace();
					}
       		
       }
      });
		panel_gauche.add(btnArbre);
		
		
		JButton btnArbuste = new JButton("Arbustes");
		btnArbuste.setForeground(new Color(255, 255, 255));
		btnArbuste.setFont(new Font("Arial", Font.BOLD, 12));
		btnArbuste.setBorder(null);
		btnArbuste.setBackground(new Color(0, 128, 0));
		btnArbuste.setBounds(5, 110, 117, 23);
		btnArbuste.addActionListener(new ActionListener() {
			 public void actionPerformed(ActionEvent e) {
        		Arbuste arbuste;
					try {
						arbuste = new Arbuste();
						arbuste.setVisible(true);
						arbuste.setLocationRelativeTo(null);
             dispose();
					} catch (SQLException e1) {
						
						e1.printStackTrace();
					}
        		
        }
       });
		panel_gauche.add(btnArbuste);
		
		JButton btnFleur = new JButton("Fleurs");
		btnFleur.setForeground(new Color(255, 255, 255));
		btnFleur.setFont(new Font("Arial", Font.BOLD, 12));
		btnFleur.setBorder(null);
		btnFleur.setBackground(new Color(0, 128, 0));
		btnFleur.setBounds(10, 135, 89, 23);
		btnFleur.addActionListener(new ActionListener() {
			 public void actionPerformed(ActionEvent e) {
       		Fleur fleur;
					try {
						fleur = new Fleur();
						fleur.setVisible(true);
						fleur.setLocationRelativeTo(null);
            dispose();
					} catch (SQLException e1) {
						
						e1.printStackTrace();
					}
       		
       }
      });
	
		panel_gauche.add(btnFleur);
		
		JButton btnPlanteSucculente = new JButton("Plante succulente");
		btnPlanteSucculente.setForeground(new Color(255, 255, 255));
		btnPlanteSucculente.setForeground(new Color(128, 64, 0));
		btnPlanteSucculente.setFont(new Font("Arial", Font.BOLD, 12));
		btnPlanteSucculente.setBorder(null);
		btnPlanteSucculente.setBackground(new Color(0, 128, 0));
		btnPlanteSucculente.setBounds(-3, 160, 187, 23);
		panel_gauche.add(btnPlanteSucculente);
		
		JLabel labelTitre = new JLabel("Stock des plantes succulentes");
		labelTitre.setBounds(330, 11, 450, 42);
		labelTitre.setFont(new Font("Snap ITC", Font.BOLD, 24));
		labelTitre.setHorizontalAlignment(SwingConstants.CENTER);
		labelTitre.setForeground(new Color(0, 128, 0));
		panneauPrincipal.add(labelTitre);
		
		JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(207, 161, 669, 402);
        panneauPrincipal.add(scrollPane);

        table_plantes = new JTable();
        table_plantes.setRowHeight(45);
        
        
        scrollPane.setViewportView(table_plantes);
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("id");
        model.addColumn("Nom");
        model.addColumn("Description");
        model.addColumn("Type");
        model.addColumn("Prix");
        model.addColumn("Image");
        table_plantes.setModel(model);
		
		
		JButton btnRechercher = new JButton("Rechercher par nom");
		btnRechercher.setBackground(new Color(255, 128, 64));
		btnRechercher.setForeground(new Color(255, 255, 255));
		btnRechercher.setBorder(null);
		btnRechercher.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
		                String rech = champ_recherche.getText();
		                rechercherNom(rech);  
			}
		});
		
		
		btnRechercher.setBounds(596, 111, 124, 23);
		btnRechercher.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		panneauPrincipal.add(btnRechercher);
		
		champ_recherche = new JTextField();
		champ_recherche.setBounds(741, 111, 124, 20);
		panneauPrincipal.add(champ_recherche);
		champ_recherche.setColumns(10);
		
		JLabel label_logo = new JLabel("New label");
		label_logo.setIcon(new ImageIcon("src\\images\\palnt.png"));
		label_logo.setBounds(20, 11, 150, 92);
		panneauPrincipal.add(label_logo);
		
		
		
		
		
		connectionBaseDonnees();
		afficherPlantes();

	}
	
	private void afficherPlantes() {
        try {
            String req = "SELECT * FROM plante where type='Plante Succulente'";

            PreparedStatement statement = conn.prepareStatement(req);
            ResultSet resultSet = statement.executeQuery();

            DefaultTableModel model = (DefaultTableModel) table_plantes.getModel();
            model.setRowCount(0);

            
            TableCellRenderer image = (table, value, isSelected, hasFocus, row, column) -> {
                JLabel label = new JLabel();
                if (value instanceof ImageIcon) {
                    label.setIcon((ImageIcon) value);
                    label.setHorizontalAlignment(JLabel.CENTER);
                }
                return label;
            };

            
            table_plantes.getColumnModel().getColumn(5).setCellRenderer(image);

            while (resultSet.next()) {
                int id = resultSet.getInt("id_plante");
                String nom = resultSet.getString("nom_plante");
                String description=resultSet.getString("description");
                String type=resultSet.getString("type");
                double prix = resultSet.getDouble("prix_plante");

                byte[] donnees = resultSet.getBytes("image_plante");
                if (donnees != null) {
                    try {
                        ImageIcon imageIcon = new ImageIcon(donnees);
                     
                        int largeur = table_plantes.getColumnModel().getColumn(5).getWidth(); 
                        int hauteur = table_plantes.getRowHeight(); 
                        Image imag = imageIcon.getImage().getScaledInstance(largeur, hauteur, Image.SCALE_SMOOTH);
                        ImageIcon scaledIcon = new ImageIcon(imag);
                        model.addRow(new Object[]{id, nom,description,type, prix, scaledIcon});
                    } catch (Exception e) {
                        System.out.println("Erreur: " + e.getMessage());
                    }
                } else {
                    model.addRow(new Object[]{id, nom,description,type, prix, null});
                }
            }
        } catch (SQLException e) {
            System.out.println("Erreue " + e.getMessage());
        }
    }
	
	private void rechercherNom(String recherche) {
        try {
            String req = "SELECT * FROM plante WHERE nom_plante LIKE ?";
            PreparedStatement statement = conn.prepareStatement(req);
            statement.setString(1, "%" + recherche + "%"); 
            ResultSet resultSet = statement.executeQuery();

            DefaultTableModel model = (DefaultTableModel) table_plantes.getModel();
            model.setRowCount(0);

            while (resultSet.next()) {
                int id = resultSet.getInt("id_plante");
                String nom = resultSet.getString("nom_plante");
                String description = resultSet.getString("description");
                String type = resultSet.getString("type");
                double prix = resultSet.getDouble("prix_plante");
                byte[] donnees = resultSet.getBytes("image_plante");

                
                ImageIcon imageIcon = null;
                if (donnees != null) {
                    try {
                        imageIcon = new ImageIcon(donnees);
                        
                        int largeur = table_plantes.getColumnModel().getColumn(5).getWidth(); 
                        int hauteur = table_plantes.getRowHeight(); 
                        Image imag = imageIcon.getImage().getScaledInstance(largeur, hauteur, Image.SCALE_SMOOTH);
                        imageIcon = new ImageIcon(imag);
                    } catch (Exception e) {
                        System.out.println("Erreur: " + e.getMessage());
                    }
                }

                model.addRow(new Object[] {id, nom,description,type, prix, imageIcon });
            }
        } catch (SQLException ex) {
            System.out.println("Erreur: " + ex.getMessage());
        }
    }
}
