import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import java.awt.FlowLayout;
import java.awt.BorderLayout;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import java.awt.Font;
import java.awt.Image;
import java.awt.TextArea;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class Plante extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel panneauPrincipal;
	private JTextField champ_nom;
	private JTextField champ_prix;
	private JTextArea champ_description;
	private JComboBox comboBoxType;
	private JTable table_plantes;
	private JTextField champ_recherche;
	private Connection conn;
	
	private JTextField champ_image;
	private JLabel labelImage;

	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Plante frame = new Plante();
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
	
	public Plante() throws SQLException {
		
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
		btnPlantes.setForeground(new Color(255, 255, 255));
		btnPlantes.setBounds(5, 22, 177, 23);
		btnPlantes.setBackground(new Color(0, 128, 0));
		btnPlantes.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		
		panel_gauche.add(btnPlantes);
		
		JButton btnStock = new JButton("Stock des plantes");
		btnStock.setFont(new Font("Arial", Font.BOLD, 15));
		btnStock.setForeground(new Color(255, 255, 255));
		btnStock.setBackground(new Color(0, 128, 0));
		btnStock.setBounds(5, 56, 177, 23);
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
		
		JLabel labelNom = new JLabel("Nom :");
		labelNom.setBounds(233, 53, 65, 14);
		labelNom.setFont(new Font("Tahoma", Font.BOLD, 17));
		labelNom.setForeground(new Color(5, 127, 26));
		panneauPrincipal.add(labelNom);
		
		champ_nom = new JTextField();
		champ_nom.setBounds(233, 78, 190, 20);
		panneauPrincipal.add(champ_nom);
		champ_nom.setColumns(10);
		
		JLabel labelDescription = new JLabel("Description :");
		labelDescription.setBounds(233, 109, 124, 27);
		labelDescription.setFont(new Font("Tahoma", Font.BOLD, 17));
		labelDescription.setForeground(new Color(5, 127, 26));
		panneauPrincipal.add(labelDescription);
		
		champ_description = new JTextArea();
		champ_description.setBounds(233, 147, 190, 48);
		champ_description.setBackground(new Color(200, 200, 200));
		panneauPrincipal.add(champ_description);
		
		JLabel labelPrix = new JLabel("Prix :");
		labelPrix.setBounds(658, 53, 65, 14);
		labelPrix.setFont(new Font("Tahoma", Font.BOLD, 17));
		labelPrix.setForeground(new Color(5, 127, 26));
		panneauPrincipal.add(labelPrix);
		
		champ_prix = new JTextField();
		champ_prix.setBounds(658, 78, 190, 20);
		panneauPrincipal.add(champ_prix);
		champ_prix.setColumns(10);
		
		JLabel labelType = new JLabel("Type :");
		labelType.setBounds(658, 109, 77, 27);
		labelType.setFont(new Font("Tahoma", Font.BOLD, 17));
		labelType.setForeground(new Color(5, 127, 26));
		panneauPrincipal.add(labelType);
		
		comboBoxType = new JComboBox();
		comboBoxType.setBounds(658, 147, 132, 22);
		comboBoxType.setFont(new Font("Tahoma", Font.PLAIN, 12));
		comboBoxType.setModel(new DefaultComboBoxModel(new String[] {"Choisir le type","Arbre", "Arbuste", "Fleur", "Plante Succulente"}));
		panneauPrincipal.add(comboBoxType);
		
		JButton btnAjouter = new JButton("Ajouter");
		btnAjouter.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnAjouter.setBounds(287, 262, 96, 23);
		btnAjouter.setBackground(new Color(0, 128, 0));
		btnAjouter.setForeground(new Color(255, 255, 255));
		btnAjouter.setBorder(null);
		btnAjouter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nom = champ_nom.getText();
                String description = champ_description.getText();
                
                String type = (String) comboBoxType.getSelectedItem();
                double prix = Double.parseDouble(champ_prix.getText());
                String image =champ_image.getText();
                
                try {
					ajouterPlante(nom,description,type, prix,image);
				} catch (IOException e1) {
					
					e1.printStackTrace();
				}
            }
        });
		panneauPrincipal.add(btnAjouter);
		
		JButton btnModifier = new JButton("Modifier");
		btnModifier.setBackground(new Color(0, 128, 192));
		btnModifier.setForeground(new Color(255, 255, 255));
		btnModifier.setBounds(434, 262, 96, 23);
		btnModifier.setBorder(null);
		btnModifier.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	            	String nom = champ_nom.getText();
	                String description = champ_description.getText();
	                String type = (String) comboBoxType.getSelectedItem();
	                double prix = Double.parseDouble(champ_prix.getText());
	                
	                
	                String image =champ_image.getText();
	                modifierPlante(nom,description,type, prix, image);
	            }
	        });
		panneauPrincipal.add(btnModifier);
		
		JButton btnSupprimer = new JButton("Supprimer");
		btnSupprimer.setBackground(new Color(255, 0, 0));
		btnSupprimer.setForeground(new Color(255, 255, 255));
		btnSupprimer.setBounds(590, 262, 96, 23);
		btnSupprimer.setBorder(null);
		btnSupprimer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
                int champ = table_plantes.getSelectedRow();
                if (champ != -1) { 
                    
                    int id_plante = (int) table_plantes.getValueAt(champ, 0);
                    
                    supprimerProduit(id_plante);
                } else {
                    
                    System.out.println("Aucun champ selectionne");
                }
            }
        });
		panneauPrincipal.add(btnSupprimer);
		
		JButton btnVider = new JButton("Vider");
		btnVider.setBackground(new Color(0, 0, 0));
		btnVider.setForeground(new Color(255, 255, 255));
		btnVider.setBounds(740, 262, 96, 23);
		btnVider.setBorder(null);
		btnVider.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	            	viderChamps();
	            }
	        });
		panneauPrincipal.add(btnVider);
		
		JLabel labelTitre = new JLabel("Gestion des plantes");
		labelTitre.setBounds(383, 11, 289, 42);
		labelTitre.setFont(new Font("Snap ITC", Font.BOLD, 24));
		labelTitre.setHorizontalAlignment(SwingConstants.CENTER);
		labelTitre.setForeground(new Color(0, 128, 0));
		panneauPrincipal.add(labelTitre);
		
		JLabel labelImagetext = new JLabel("Image :");
		labelImagetext.setBounds(513, 168, 93, 27);
		labelImagetext.setFont(new Font("Tahoma", Font.BOLD, 17));
		labelImagetext.setForeground(new Color(5, 127, 26));
		panneauPrincipal.add(labelImagetext);
		
		JButton btnChoix_image = new JButton("Choisir une image");
		btnChoix_image.setBounds(546, 214, 140, 23);
		btnChoix_image.setBorder(null);
		btnChoix_image.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
                int resultat = fileChooser.showOpenDialog(null);
                if (resultat == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    
                }
			}
		});
		panneauPrincipal.add(btnChoix_image);
		
		JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(207, 356, 669, 207);
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
		
		
		JButton btnRecherche = new JButton("Rechercher par nom");
		btnRecherche.setBackground(new Color(255, 128, 64));
		btnRecherche.setForeground(new Color(255, 255, 255));
		btnRecherche.setBorder(null);
		
		btnRecherche.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
		                String recherche = champ_recherche.getText();
		                rechercherNom(recherche);  
			}
		});
		btnRecherche.setBounds(611, 322, 124, 23);
		btnRecherche.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		panneauPrincipal.add(btnRecherche);
		
		champ_recherche = new JTextField();
		champ_recherche.setBounds(752, 325, 124, 20);
		panneauPrincipal.add(champ_recherche);
		champ_recherche.setColumns(10);
		
		JLabel label_logo = new JLabel("New label");
		label_logo.setIcon(new ImageIcon("src\\images\\palnt.png"));
		label_logo.setBounds(20, 11, 150, 92);
		panneauPrincipal.add(label_logo);
		
		labelImage = new JLabel("");
		labelImage.setForeground(new Color(0, 0, 0));
		labelImage.setBackground(new Color(255, 255, 255));
		labelImage.setBounds(434, 206, 96, 37);
		panneauPrincipal.add(labelImage);
		
		champ_image = new JTextField();
		champ_image.setEnabled(false);
		champ_image.setBounds(433, 206, 77, 37);
		panneauPrincipal.add(champ_image);
		champ_image.setColumns(10);
		
		
		
		connectionBaseDonnees();
		afficherPlantes();
		selectionnerChamptable();
	}
	

	
	
	private void selectionnerChamptable() {
		table_plantes.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int ligne = table_plantes.getSelectedRow();
                if (ligne != -1) {
                    int id = (int) table_plantes.getValueAt(ligne, 0);
                    String nom = (String) table_plantes.getValueAt(ligne, 1);
                    String description = (String) table_plantes.getValueAt(ligne, 2);
                    String type = (String) table_plantes.getValueAt(ligne, 3);
                    double prix = (double) table_plantes.getValueAt(ligne, 4);
                    ImageIcon imageIcon = (ImageIcon) table_plantes.getValueAt(ligne, 5);
                    
                    champ_nom.setText(nom);
                    champ_description.setText(description);
                    comboBoxType.setSelectedItem(type);
                    champ_prix.setText(String.valueOf(prix));
                    
                    
                    labelImage.setIcon(imageIcon);
                }
            }
        });
    }
	
    private void ajouterPlante(String nom,String description,String type, double prix, String image) throws IOException {
    	 String prixText = champ_prix.getText().trim();
    	if (nom.equals("") || description.equals("") || type.equals("") || image.equals("") || prixText.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Erreur: Entrée invalide. Veuillez réessayer.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }else {
        try {
        	byte[] img = null;
            if (image != null && !image.isEmpty()) {
                
                File img_File = new File(image);
                BufferedImage bufferedImage = ImageIO.read(img_File);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                
                String extension = image.substring(image.lastIndexOf(".") + 1).toLowerCase();
                
                ImageIO.write(bufferedImage, extension, byteArrayOutputStream);
                img = byteArrayOutputStream.toByteArray();
            }
            String req = "INSERT INTO plante (nom_plante, description,type,prix_plante, image_plante) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(req);
            statement.setString(1, nom);
            statement.setString(2, description);
            statement.setString(3, type);
            statement.setDouble(4, prix);
            statement.setBytes(5, img);
            statement.executeUpdate();
            System.out.println("Plante ajoutee avec succes");
            viderChamps();
            afficherPlantes();
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout de la plante:" + e.getMessage());
        }}
    }

    private void afficherPlantes() {
        try {
            String query = "SELECT * FROM plante";

            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            DefaultTableModel model = (DefaultTableModel) table_plantes.getModel();
            model.setRowCount(0);

            
            TableCellRenderer imageAffichee = (table, value, isSelected, hasFocus, row, column) -> {
                JLabel label = new JLabel();
                if (value instanceof ImageIcon) {
                    label.setIcon((ImageIcon) value);
                    label.setHorizontalAlignment(JLabel.CENTER);
                }
                return label;
            };

            
            table_plantes.getColumnModel().getColumn(5).setCellRenderer(imageAffichee);

            while (resultSet.next()) {
                int id = resultSet.getInt("id_plante");
                String nom = resultSet.getString("nom_plante");
                String description=resultSet.getString("description");
                String type=resultSet.getString("type");
                double prix = resultSet.getDouble("prix_plante");

                byte[] donnees_img = resultSet.getBytes("image_plante");
                if (donnees_img != null) {
                    try {
                        ImageIcon imageIcon = new ImageIcon(donnees_img);
                     
                        int largeur = table_plantes.getColumnModel().getColumn(5).getWidth(); 
                        int hauteur = table_plantes.getRowHeight(); 
                        Image image = imageIcon.getImage().getScaledInstance(largeur, hauteur, Image.SCALE_SMOOTH);
                        ImageIcon imgIcon = new ImageIcon(image);
                        model.addRow(new Object[]{id, nom,description,type, prix, imgIcon});
                    } catch (Exception e) {
                        System.out.println("Erreur: " + e.getMessage());
                    }
                } else {
                    model.addRow(new Object[]{id, nom,description,type, prix, null});
                }
            }
        } catch (SQLException e) {
            System.out.println("Erreur " + e.getMessage());
        }
    }
  


    private void modifierPlante(String nom,String description,String type, double prix, String cheminImage) {
        try {
            
            if (cheminImage != null && !cheminImage.isEmpty()) {
                
                byte[] donnees = null;
                
                File imageFile = new File(cheminImage);
                BufferedImage bufferedImage = ImageIO.read(imageFile);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                
                String extension = cheminImage.substring(cheminImage.lastIndexOf(".") + 1).toLowerCase();
                
                ImageIO.write(bufferedImage, extension, byteArrayOutputStream);
                donnees = byteArrayOutputStream.toByteArray();

                
                String req = "UPDATE plante SET nom_plante=?,description=?,type=?, prix_plante=?, image_plante=? WHERE id_plante=?";
                PreparedStatement statement = conn.prepareStatement(req);
                statement.setString(1, nom);
                statement.setString(2, description);
                statement.setString(3, type);
                statement.setDouble(4, prix);
                statement.setBytes(5, donnees);

                
               
                int ligne = table_plantes.getSelectedRow();
                int id_plante = (int) table_plantes.getValueAt(ligne, 0);
                statement.setInt(6, id_plante);

                int lignes = statement.executeUpdate();
                if (lignes > 0) {
                    System.out.println("Plante modifiee avec succes!");
                    viderChamps();
                    afficherPlantes();
                } else {
                    System.out.println("Pas de plante trouvee");
                }
            } else {
                
                String req = "UPDATE plante SET nom_plante=?,description=?,type=?, prix_plante=? WHERE id_plante=?";
                PreparedStatement statement = conn.prepareStatement(req);
                statement.setString(1, nom);
                statement.setString(2, description);
                statement.setString(3, type);
                statement.setDouble(4, prix);
                
                
                
                int ligne = table_plantes.getSelectedRow();
                int id_plante = (int) table_plantes.getValueAt(ligne, 0);
                statement.setInt(5, id_plante);

                int lignes = statement.executeUpdate();
                if (lignes > 0) {
                    System.out.println("Plante modifiee avec succes!");
                    viderChamps();
                    afficherPlantes();
                } else {
                    System.out.println("Pas de plante trouvee");
                }
            }
        } catch (SQLException | IOException e) {
            System.out.println("problem : " + e.getMessage());
        }
    }

    private void supprimerProduit(int id_plante) {
        try {
            String mess = "Êtes-vous sûr de vouloir supprimer ce produit ?";
            int confirm = JOptionPane.showOptionDialog(null, mess, "Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, new Object[]{"Oui", "Non"}, "Non");
            
            if (confirm == JOptionPane.YES_OPTION) {
                String requete = "DELETE FROM plante WHERE id_plante=?";
                PreparedStatement statement = conn.prepareStatement(requete);
                statement.setInt(1, id_plante);
                int lignes = statement.executeUpdate();
                if (lignes > 0) {
                    System.out.println("Plante supprimee avec succes !");
                    viderChamps();
                    afficherPlantes();
                } else {
                    System.out.println("Aucune plante trouvee avec cet id : " + id_plante);
                }
            } else {
                System.out.println("Suppression annulee");
            }
        } catch (SQLException e) {
            System.out.println("Erreur : " + e.getMessage());
            e.printStackTrace(); 
        }
    }
  
    private void rechercherNom(String recherche) {
        try {
            String query = "SELECT * FROM plante WHERE nom_plante LIKE ?";
            PreparedStatement statement = conn.prepareStatement(query);
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
                        Image scaledImage = imageIcon.getImage().getScaledInstance(largeur, hauteur, Image.SCALE_SMOOTH);
                        imageIcon = new ImageIcon(scaledImage);
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

    private void viderChamps() {
    	champ_nom.setText("");
        champ_prix.setText("");
        champ_description.setText("");
        comboBoxType.setSelectedItem("Choisir le type");
        labelImage.setIcon(null);
        //textField_3.setText("");
    }
}
