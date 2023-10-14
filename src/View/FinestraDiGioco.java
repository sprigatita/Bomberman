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
    public FinestraDiGioco() {
        this.init();
		this.setPreferredSize(new Dimension(500,500));
    }

    public void init() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setTitle("JBomberman");
        this.setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS)); // Layout verticale
        

        // Crea il pannello delle informazioni
        JPanel infoPanel = createInfoPanel();

        // Crea il GamePanel e imposta il listener con l'istanza corrente di finestradigioco
        GamePanel gamePanel = new GamePanel();
        gamePanel.fdg = this;
        // Aggiungi il pannello delle informazioni sopra al GamePanel
        this.add(infoPanel);
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
    JLabel usernameLabel = new JLabel("Username: placeholder");
    JLabel scoreLabel = new JLabel("Score: "); // Ottieni lo score dall'utente
    private JPanel createInfoPanel() {
        JPanel infoPanel = new JPanel();
        


        // Scelta del layout orizzontale
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.X_AXIS));


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
