
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
public class  ProduitPhysique{

    JFrame frame;
    private JTextField textnom;
    private JTextField textField;
    private JTextField textField_1;
    private JTable table;
    private JTextField textField_2;
    private JTextField imageTextField;
    private Connection connection;
    private JLabel imageLabel;
    private JTextField textDesc;
    
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ProduitPhysique window = new  ProduitPhysique(); 
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

  
    
    public ProduitPhysique() {
        initialize();
        connectToDatabase();
        afficherProduits();
        addTableSelectionListener();
    }
    private void connectToDatabase() {
        String url = "jdbc:mysql://localhost:3306/gestionpepiniere";
        String username = "root";
        String password = "";

        try {
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("connecter avec succe.");
        } catch (SQLException e) {
            System.out.println("Failed to connect to the database.");
            e.printStackTrace();
        }
    }
    private void addTableSelectionListener() {
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    int id = (int) table.getValueAt(selectedRow, 0);
                    String nom = (String) table.getValueAt(selectedRow, 1);
                    double prix = (double) table.getValueAt(selectedRow, 2);
                    String description = (String) table.getValueAt(selectedRow, 3);
                    ImageIcon imageIcon = (ImageIcon) table.getValueAt(selectedRow, 4);
                    // Set the data to the text fields
                    textnom.setText(nom);
                    textField.setText(String.valueOf(prix));
                    textDesc.setText(description);
                    
                    imageLabel.setIcon(imageIcon);
                }
            }
        });
    }
    private void ajouterProduit(String nom, Double prix, String description, String imagePath) {
        try {
            byte[] imageData = null;
          
            if (imagePath != null && !imagePath.isEmpty()) {
               
                File imageFile = new File(imagePath);
                BufferedImage bufferedImage = ImageIO.read(imageFile);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                
                String fileExtension = imagePath.substring(imagePath.lastIndexOf(".") + 1).toLowerCase();
             
                ImageIO.write(bufferedImage, fileExtension, byteArrayOutputStream);
                imageData = byteArrayOutputStream.toByteArray();
            }

           
            String query = "INSERT INTO produit_physique (nom_physique, prix_physique, description_physique, image_physique) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, nom);
            statement.setDouble(2, prix);
            statement.setString(3, description);
            statement.setBytes(4, imageData);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Product ajouter avec succes!");
                clearFields();
                afficherProduits();
            } else {
                System.out.println("Problem d'ajoute un produit.");
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }


    private void modifierProduit(String nom, double prix,String description, String imagePath) {
        try {
            
            if (imagePath != null && !imagePath.isEmpty()) {
                
                byte[] imageData = null;
                File imageFile = new File(imagePath);
                BufferedImage bufferedImage = ImageIO.read(imageFile);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                
                String fileExtension = imagePath.substring(imagePath.lastIndexOf(".") + 1).toLowerCase();
                
                ImageIO.write(bufferedImage, fileExtension, byteArrayOutputStream);
                imageData = byteArrayOutputStream.toByteArray();

               
                String query = "UPDATE produit_physique SET nom_physique=?, prix_physique=?,description_physique=?, image_physique=? WHERE id_physique=?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, nom);
                statement.setDouble(2, prix);
                statement.setString(3, description);
                statement.setBytes(4, imageData);
                
                int selectedRow = table.getSelectedRow();
                int id_physique = (int) table.getValueAt(selectedRow, 0);
                statement.setInt(4, id_physique);

                int rowsAffected = statement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Produit modified avec succes!");
                    clearFields();
                    afficherProduits();
                } else {
                    System.out.println("No produit trouver.");
                }
            } else {
                String query = "UPDATE produit_physique SET nom_physique=?, prix_physique=?,description_physique=? WHERE id_physique=?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, nom);
                statement.setDouble(2, prix);
                statement.setString(3, description);                // Assuming the ID is the primary key and auto-incremented, it is not explicitly provided in the update query.
                
                int selectedRow = table.getSelectedRow();
                int id_physique = (int) table.getValueAt(selectedRow, 0);
                statement.setInt(4, id_physique);

                int rowsAffected = statement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Produit modified avec succes!");
                    clearFields();
                    afficherProduits();
                } else {
                    System.out.println("No produit trouver.");
                }
            }
        } catch (SQLException | IOException e) {
            System.out.println("problem : " + e.getMessage());
        }
    }

    private void afficherProduits() {
        try {
        	String query = "SELECT id_physique, nom_physique, prix_physique, description_physique, image_physique FROM produit_physique";
        	PreparedStatement statement = connection.prepareStatement(query);

            ResultSet resultSet = statement.executeQuery();

            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0);

            TableCellRenderer imageRenderer = (table, value, isSelected, hasFocus, row, column) -> {
                JLabel label = new JLabel();
                if (value instanceof ImageIcon) {
                    label.setIcon((ImageIcon) value);
                    label.setHorizontalAlignment(JLabel.CENTER);
                }
                return label;
            };

            table.getColumnModel().getColumn(4).setCellRenderer(imageRenderer);

            while (resultSet.next()) {
                int id = resultSet.getInt("id_physique");
                String nom = resultSet.getString("nom_physique");
                double prix = resultSet.getDouble("prix_physique");
                String description = resultSet.getString("description_physique");
                byte[] imageData = resultSet.getBytes("image_physique");
                if (imageData != null) {
                    try {
                        ImageIcon imageIcon = new ImageIcon(imageData);
                        int width = table.getColumnModel().getColumn(3).getWidth(); // Get the width of the table column for images
                        int height = table.getRowHeight(); // Get the height of the table rows
                        Image scaledImage = imageIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
                        ImageIcon scaledIcon = new ImageIcon(scaledImage);
                        model.addRow(new Object[]{id, nom, prix, description, scaledIcon});
                    } catch (Exception e) {
                        System.out.println("problem: " + e.getMessage());
                    }
                } else {
                    model.addRow(new Object[]{id, nom, prix,description, null});
                }
            }
        } catch (SQLException e) {
            System.out.println("problem " + e.getMessage());
        }
    }
    
    private void supprimerProduit(int id_physique) {
        try {
            String message = "Êtes-vous sûr de vouloir supprimer ce produit ?";
            int confirmation = JOptionPane.showOptionDialog(frame, message, "Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, new Object[]{"Oui", "Non"}, "Non");
            
            if (confirmation == JOptionPane.YES_OPTION) {
                String query = "DELETE FROM produit_physique WHERE id_physique=?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setInt(1, id_physique);
                int rowsAffected = statement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Produit supprimé avec succès !");
                    clearFields();
                    afficherProduits();
                } else {
                    System.out.println("Aucun produit trouvé avec cet ID : " + id_physique);
                }
            } else {
                System.out.println("Suppression annulée.");
            }
        } catch (SQLException e) {
            System.out.println("Problème : " + e.getMessage());
            e.printStackTrace(); 
        }
    }
  


    private void performSearch(String searchQuery) {
        try {
            String query = "SELECT * FROM produit_physique WHERE nom_physique LIKE ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, "%" + searchQuery + "%"); // Use % to match any characters before or after the search query
            ResultSet resultSet = statement.executeQuery();

            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0);

            while (resultSet.next()) {
                int id = resultSet.getInt("id_physique");
                String nom = resultSet.getString("nom_physique");
                double prix = resultSet.getDouble("prix_physique");
                String description = resultSet.getString("description_physique");
                byte[] imageData = resultSet.getBytes("image_physique");

              
                ImageIcon imageIcon = null;
                if (imageData != null) {
                    try {
                        imageIcon = new ImageIcon(imageData);
                       
                        int width = table.getColumnModel().getColumn(3).getWidth(); 
                        int height = table.getRowHeight(); // Get the height of the table rows
                        Image scaledImage = imageIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
                        imageIcon = new ImageIcon(scaledImage);
                    } catch (Exception e) {
                        System.out.println("problem: " + e.getMessage());
                    }
                }

                model.addRow(new Object[] {id, nom, prix,description, imageIcon });
            }
        } catch (SQLException ex) {
            System.out.println("Error during search: " + ex.getMessage());
        }
    }


    private void clearFields() {
        textnom.setText("");
        textField.setText("");
        textDesc.setText("");
        imageTextField.setText("");
    }

    
  
    private void initialize() {
    	
    	 frame = new JFrame();
    	 frame.getContentPane().setBackground(new Color(255, 255, 255));
         frame.getContentPane().setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
         frame.setBounds(100, 100, 1017, 634);
         
         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         frame.getContentPane().setLayout(null);

         JLabel lblNewLabel = new JLabel("Produit physique");
         lblNewLabel.setForeground(new Color(0, 255, 0));
         lblNewLabel.setToolTipText("");
         lblNewLabel.setBackground(SystemColor.textHighlight);
         lblNewLabel.setFont(new Font("Vladimir Script", Font.BOLD, 50));
         lblNewLabel.setBounds(345, 24, 344, 59);
         frame.getContentPane().add(lblNewLabel);

         JPanel panel = new JPanel();
         panel.setBackground(new Color(255, 255, 255));
         panel.setForeground(Color.GREEN);
         panel.setBorder(new TitledBorder(null, "Ajouter Produit", TitledBorder.LEADING, TitledBorder.TOP, null, null));
         panel.setBounds(216, 173, 286, 302);
         frame.getContentPane().add(panel);
         panel.setLayout(null);

         JLabel labelNom = new JLabel("Nom :");
         labelNom.setFont(new Font("Agency FB", Font.BOLD, 20));
         labelNom.setBounds(10, 52, 60, 24);
         panel.add(labelNom);

         JLabel labelPrix = new JLabel("Prix :");
         labelPrix.setFont(new Font("Agency FB", Font.BOLD, 20));
         labelPrix.setBounds(10, 106, 60, 24);
         panel.add(labelPrix);

         JLabel labelImage = new JLabel("Image :");
         labelImage .setFont(new Font("Agency FB", Font.BOLD, 20));
         labelImage .setBounds(10, 239, 60, 24);
         panel.add(labelImage );
         imageLabel = new JLabel();
         imageLabel.setBounds(86, 233, 200, 44); 
         panel.add(imageLabel);
         textnom = new JTextField();
         textnom.setBounds(84, 47, 179, 42);
         panel.add(textnom);
         textnom.setColumns(10);

         textField = new JTextField();
         textField.setColumns(10);
         textField.setBounds(84, 100, 179, 44);
         panel.add(textField);

         imageTextField = new JTextField();
         imageTextField.setEditable(false);
         imageTextField.setColumns(10);
         imageTextField.setBounds(86, 233, 76, 44);
         panel.add(imageTextField);

         JButton browseButton = new JButton("Browse");
         browseButton.addActionListener(new ActionListener() {
             public void actionPerformed(ActionEvent e) {
                 JFileChooser fileChooser = new JFileChooser();
                 int result = fileChooser.showOpenDialog(frame);
                 if (result == JFileChooser.APPROVE_OPTION) {
                     File selectedFile = fileChooser.getSelectedFile();
                     imageTextField.setText(selectedFile.getAbsolutePath());
                 }
             }
         });
         browseButton.setBounds(172, 233, 88, 44);
         panel.add(browseButton);
         
         textDesc = new JTextField();
         textDesc.setBounds(86, 155, 177, 73);
         panel.add(textDesc);
         textDesc.setColumns(10);
         
         JLabel labelNomDesc = new JLabel("Description :");
         labelNomDesc.setFont(new Font("Agency FB", Font.BOLD, 20));
         labelNomDesc.setBounds(10, 175, 77, 24);
         panel.add( labelNomDesc);
        JButton btnNewButton = new JButton("Ajouter");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nom = textnom.getText();
                double prix = Double.parseDouble(textField.getText());
                String description = textDesc.getText();
                String imagePath = imageTextField.getText(); 
                ajouterProduit(nom, prix,description, imagePath);
            }
        });
        btnNewButton.setBackground(new Color(128, 255, 128));
        btnNewButton.setBounds(216, 486, 89, 39);
        frame.getContentPane().add(btnNewButton);
        JButton btnModifier = new JButton("Modifier");
        btnModifier.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nom = textnom.getText(); // Retrieve the product name from the text field
                double prix = Double.parseDouble(textField.getText());
                String description = textDesc.getText();
                String imagePath = imageTextField.getText(); // Retrieve the image file path
                modifierProduit(nom, prix,description, imagePath);
            }
        });

        btnModifier.setBackground(new Color(128, 255, 255));
        btnModifier.setBounds(314, 486, 89, 39);
        frame.getContentPane().add(btnModifier);
        JButton btnSupprimer = new JButton("Supprimer");
        btnSupprimer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) { // Ensure a row is selected
                    int id_physique = (int) table.getValueAt(selectedRow, 0);
                   
                    supprimerProduit(id_physique);
                } else {
                   
                    System.out.println("No row selected");
                }
            }
        });

        btnSupprimer.setBackground(new Color(255, 128, 128));
        btnSupprimer.setBounds(413, 486, 89, 39);
        frame.getContentPane().add( btnSupprimer);
        JButton btnClear = new JButton("Clear");
        btnClear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clearFields();
            }
        });
        btnClear.setBackground(new Color(255, 240, 245));
        btnClear.setBounds(216, 123, 89, 39);
        frame.getContentPane().add(btnClear);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(528, 206, 452, 378);
        frame.getContentPane().add(scrollPane);

        table = new JTable();
        table.setRowHeight(60);
        scrollPane.setViewportView(table);
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("id");
        model.addColumn("Nom");
        model.addColumn("Prix");
        model.addColumn("description");
        model.addColumn("Image");
        table.setModel(model);

        
   
        JPanel panelPechercher = new JPanel();
        panelPechercher.setForeground(new Color(0, 204, 204));
        panelPechercher.setBackground(new Color(255, 255, 255));
        panelPechercher.setBorder(new TitledBorder(null, "Rechercher", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panelPechercher.setBounds(528, 110, 452, 52);
        frame.getContentPane().add(panelPechercher);
        panelPechercher.setLayout(null);

        textField_2 = new JTextField();
        textField_2.setColumns(10);
        textField_2.setBounds(108, 17, 238, 26);
        panelPechercher.add(textField_2);

        JLabel labelNom_3 = new JLabel("NomProduit :\r\n");
        labelNom_3.setFont(new Font("Agency FB", Font.BOLD, 20));
        labelNom_3.setBounds(10, 14, 112, 24);
        panelPechercher.add(labelNom_3);

        JButton searchButton = new JButton("Search");
        searchButton.setBackground(new Color(255, 218, 185));
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String searchQuery = textField_2.getText();
                performSearch(searchQuery);
            }
        });
        searchButton.setBounds(356, 17, 89, 26); 
        panelPechercher.add(searchButton);
        JLabel lblListesDesProduits = new JLabel("Listes des Produits physique");
        lblListesDesProduits.setToolTipText("");
        lblListesDesProduits.setForeground(new Color(0, 255, 0));
        lblListesDesProduits.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        lblListesDesProduits.setBackground(new Color(0, 255, 0));
        lblListesDesProduits.setBounds(615, 155, 317, 50);
        frame.getContentPane().add(lblListesDesProduits);
        
        JPanel panelPlant = new JPanel();
        panelPlant.setBackground(new Color(50, 205, 50));
        panelPlant.setForeground(new Color(50, 205, 50));
        panelPlant.setBounds(0, 123, 194, 631);
        frame.getContentPane().add(panelPlant);
        panelPlant.setLayout(null);
        
        JButton btnPlant= new JButton("Plantes");
        btnPlant.setFont(new Font("Tahoma", Font.BOLD, 20));
        btnPlant.setForeground(new Color(255, 255, 255));
        btnPlant.setBackground(new Color(50, 205, 50));
        btnPlant.setBounds(35, 113, 120, 39);
        panelPlant.add(btnPlant);
        
        JButton btnPlant1 = new JButton("Plantes");
        btnPlant1.setForeground(Color.WHITE);
        btnPlant1.setFont(new Font("Tahoma", Font.BOLD, 20));
        btnPlant1.setBackground(new Color(50, 205, 50));
        btnPlant1.setBounds(35, 183, 120, 39);
        panelPlant.add(btnPlant1);
        
        JButton btnPlant2 = new JButton("Plantes");
        btnPlant2.setForeground(Color.WHITE);
        btnPlant2.setFont(new Font("Tahoma", Font.BOLD, 20));
        btnPlant2.setBackground(new Color(50, 205, 50));
        btnPlant2.setBounds(35, 251, 120, 39);
        panelPlant.add( btnPlant2);
        
        JButton  btnPlant3 = new JButton("Plantes");
        btnPlant3.setForeground(Color.WHITE);
        btnPlant3.setFont(new Font("Tahoma", Font.BOLD, 20));
        btnPlant3.setBackground(new Color(50, 205, 50));
        btnPlant3 .setBounds(35, 319, 120, 39);
        panelPlant.add(btnPlant3 );
        
        JPanel panel_3 = new JPanel();
        panel_3.setBackground(new Color(255, 255, 255));
        panel_3.setBounds(0, 0, 170, 122);
        frame.getContentPane().add(panel_3);
        panel_3.setLayout(null);
        
        JLabel lblNewLabel_2 = new JLabel("New label");
        lblNewLabel_2.setIcon(new ImageIcon("C:\\Users\\hp\\Desktop\\S_6\\J2ee\\palnt.png"));
        lblNewLabel_2.setBounds(10, 0, 147, 117);
        panel_3.add(lblNewLabel_2);
        
        JPanel panel_4 = new JPanel();
        panel_4.setBackground(new Color(255, 255, 255));
        panel_4.setBounds(826, 0, 165, 122);
        frame.getContentPane().add(panel_4);
        panel_4.setLayout(null);
        
        JLabel lblNewLabel_3 = new JLabel("");
        lblNewLabel_3.setIcon(new ImageIcon("C:\\Users\\hp\\Desktop\\S_6\\J2ee\\plante.png"));
        lblNewLabel_3.setBounds(30, 0, 145, 119);
        panel_4.add(lblNewLabel_3);
        JButton importButton = new JButton("Importer XML");
        importButton.setBackground(new Color(250, 250, 210));
        importButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	importFromXML();
              
            }
        });
        importButton.setBounds(216, 545, 137, 39);
        frame.getContentPane().add(importButton);

        JButton exportButton = new JButton("Exporter XML");
        exportButton.setBackground(new Color(250, 240, 230));
        exportButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                exportToXML();
                
            }
        });
        exportButton.setBounds(363, 545, 139, 39);
        frame.getContentPane().add(exportButton);}
    private void importFromXML() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Importer XML ");
        fileChooser.setFileFilter(new FileNameExtensionFilter("XML Files", "xml"));
        int result = fileChooser.showOpenDialog(frame);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
                Document document = documentBuilder.parse(selectedFile);

                Element rootElement = document.getDocumentElement();

         
                NodeList produitNodes = rootElement.getElementsByTagName("produit");
                for (int i = 0; i < produitNodes.getLength(); i++) {
                    Element produitElement = (Element) produitNodes.item(i);

                  
                    String nom = produitElement.getElementsByTagName("nom").item(0).getTextContent();

                   
                    double prix = Double.parseDouble(produitElement.getElementsByTagName("prix").item(0).getTextContent());

                   
                    String description = produitElement.getElementsByTagName("description").item(0).getTextContent();

               
                    String imagePath = produitElement.getElementsByTagName("imagePath").item(0).getTextContent();

                  
                    ajouterProduit(nom, prix, description, imagePath);
                }

                System.out.println("Data imported successfully!");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    
  
    private void exportToXML() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Exporter XML ");
        fileChooser.setFileFilter(new FileNameExtensionFilter("XML Files", "xml"));
        int result = fileChooser.showSaveDialog(frame);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                Document doc = docBuilder.newDocument();

                
                Element rootElement = doc.createElement("produits");
                doc.appendChild(rootElement);

             
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                for (int i = 0; i < model.getRowCount(); i++) {
                    Element produitElement = doc.createElement("produit");
                    rootElement.appendChild(produitElement);

                    Element nomElement = doc.createElement("nom");
                    nomElement.appendChild(doc.createTextNode(model.getValueAt(i, 1).toString()));
                    produitElement.appendChild(nomElement);

                    Element prixElement = doc.createElement("prix");
                    prixElement.appendChild(doc.createTextNode(model.getValueAt(i, 2).toString()));
                    produitElement.appendChild(prixElement);

                    Element descriptionElement = doc.createElement("description");
                    descriptionElement.appendChild(doc.createTextNode(model.getValueAt(i, 3).toString()));
                    produitElement.appendChild(descriptionElement);

                    Element imagePathElement = doc.createElement("imagePath");
                    imagePathElement.appendChild(doc.createTextNode(model.getValueAt(i, 4).toString()));
                    produitElement.appendChild(imagePathElement);
                }

                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                DOMSource source = new DOMSource(doc);
                StreamResult result1 = new StreamResult(selectedFile);
                transformer.transform(source, result1);

             
                copyImages(model, selectedFile.getParentFile());

                System.out.println("Data exported successfully!");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

  
    private void copyImages(DefaultTableModel model, File destinationFolder) {
        for (int i = 0; i < model.getRowCount(); i++) {
            String imagePath = model.getValueAt(i, 4).toString();
            File sourceFile = new File(imagePath);
            File destinationFile = new File(destinationFolder, sourceFile.getName());
            try {
                Files.copy(sourceFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    }


