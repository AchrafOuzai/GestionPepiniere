import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.swing.SwingConstants;

public class Chargement extends JFrame {
    private JProgressBar barreprogression;
    private Timer minuteur;
    private int progress = 0;

    public Chargement() {
    	setUndecorated(true);
        setSize(400, 220);
        setLocationRelativeTo(null);
       
        Color couleur = new Color(0x057f1a);

        
        barreprogression = new JProgressBar(0, 100);
        barreprogression.setBounds(0, 208, 400, 12);
        barreprogression.setStringPainted(true);
        barreprogression.setBorder(null);
        Color couleurprog = new Color(0x1abb3b); 
        getContentPane().setLayout(null);
        barreprogression.setForeground(couleurprog);
        barreprogression.setPreferredSize(new Dimension(barreprogression.getPreferredSize().width, 12));
        getContentPane().add(barreprogression);
        
        JPanel panell = new JPanel();
        panell.setBounds(0, 0, 400, 208);
        panell.setBackground(new Color(255, 255, 255));
        getContentPane().add(panell);
        panell.setLayout(null);
        
        JLabel label = new JLabel("label");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setIcon(new ImageIcon("C:\\Users\\HP\\palntE.png"));
        label.setBounds(123, 45, 147, 112);
        panell.add(label);
        
        JLabel labele = new JLabel("Bienvenue dans Mon Pépinière");
        labele.setHorizontalAlignment(SwingConstants.CENTER);
        labele.setForeground(new Color(5, 127, 26));
        labele.setFont(new Font("Arial", Font.BOLD, 20));
        labele.setBorder(new EmptyBorder(10, 0, 0, 0));
        labele.setBackground(Color.WHITE);
        labele.setBounds(0, 0, 400, 40);
        panell.add(labele);
        
        JPanel panel = new JPanel();
        panel.setBounds(0, 0, 400, 34);
        getContentPane().add(panel);

     
        minuteur = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                progress += 2;
                barreprogression.setValue(progress);
                if (progress >= 100) {
                	minuteur.stop();
                    dispose(); 
                    ouvrirLogin();
                }
            }
        });
        minuteur.start();
    }
    private void ouvrirLogin() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Login login = new Login(); 
                login.setVisible(true); 
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Chargement().setVisible(true);
            }
        });
    }
}
