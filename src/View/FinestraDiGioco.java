package View;
import javax.imageio.ImageIO;
import javax.swing.*;

import Model.User;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;



public class FinestraDiGioco extends JFrame{
    public FinestraDiGioco(User user) {
        this.init(user);
//		this.setPreferredSize(new Dimension(500,500));
    }

    public void init(User user) {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setTitle("JBomberman");
        this.setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS)); // Layout verticale
        

        // Crea il pannello delle informazioni
        JPanel infoPanel = createInfoPanel();
        

        // Crea il GamePanel e imposta il listener con l'istanza corrente di finestradigioco
        System.out.println("fdg user level" + user.current_level);
        GamePanel gamePanel = new GamePanel(user);
        TopBar top = new TopBar(gamePanel);
        gamePanel.fdg = this;
        // Aggiungi il pannello delle informazioni sopra al GamePanel
        this.add(top);
        this.add(gamePanel);

        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    /* 
     * Qui si aggiungono le componenti tipo informazioni che ci servono a schermo,
     * noi le dovremo prendere dalla classe user quindi magari serve istanziare questo 
     * panel altrove.
     */
    JLabel usernameLabel = new JLabel("USERNAME: placeholder",SwingConstants.LEFT);
    JLabel scoreLabel = new JLabel("SCORE: ", SwingConstants.LEFT); // Ottieni lo score dall'utente
    private JPanel createInfoPanel() {
//    	usernameLabel.setLayout(null);
    	usernameLabel.setPreferredSize(new Dimension(300,300));
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(1,3));
        infoPanel.setPreferredSize(new Dimension(100,100));
//        this.usernameLabel.setPreferredSize(new Dimension(1000,50));
//        this.usernameLabel.setOpaque(true);
//        this.usernameLabel.setAlignmentX(RIGHT_ALIGNMENT);
//        this.scoreLabel.setAlignmentX(RIGHT_ALIGNMENT);
//        this.usernameLabel.setBackground(Color.BLACK);
        // Scelta del layout orizzontale
//        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.X_AXIS));
        this.scoreLabel.setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 20));
//        this.scoreLabel.setForeground(Color.BLUE);
        this.usernameLabel.setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 20));
//        this.usernameLabel.setHorizontalTextPosition(JLabel.CENTER);

        infoPanel.add(usernameLabel);
        infoPanel.add(scoreLabel);


        return infoPanel;
    }
    

    
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> {
//            FinestraDiGioco finestra = new FinestraDiGioco();
//            finestra.setVisible(true);
//        });
//    }
}
