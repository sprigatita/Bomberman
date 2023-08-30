package View;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;



public class FinestraDiGioco extends JFrame {

    public FinestraDiGioco() {
        this.init();
    }

    public void init() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setTitle("JBomberman - Mignola col Prof");
        this.setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS)); // Layout verticale

        // Crea il pannello delle informazioni
        JPanel infoPanel = createInfoPanel();

        // Crea il GamePanel
        GamePanel gamePanel = new GamePanel();

        // Aggiungi il pannello delle informazioni sopra al GamePanel
        this.add(infoPanel);
        this.add(gamePanel);

        this.pack();
        this.setLocationRelativeTo(null);
    }

    private JPanel createInfoPanel() {
        JPanel infoPanel = new JPanel();
        


        // Scelta del layout orizzontale
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.X_AXIS));

        /* 
         * Qui si aggiungono le componenti tipo informazioni che ci servono a schermo,
         * noi le dovremo prendere dalla classe user quindi magari serve istanziare questo 
         * panel altrove.
         */
        JLabel usernameLabel = new JLabel("Username: I love");
        JLabel scoreLabel = new JLabel("Score: you");

        infoPanel.add(usernameLabel);
        infoPanel.add(scoreLabel);


        return infoPanel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FinestraDiGioco finestra = new FinestraDiGioco();
            finestra.setVisible(true);
        });
    }
}
