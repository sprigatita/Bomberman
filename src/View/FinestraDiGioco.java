package View;
import javax.swing.JFrame;


public class FinestraDiGioco extends JFrame {

    public FinestraDiGioco () {
    	this.init();
    }
    
    public void init() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setTitle("JBomberman - Mignola col Prof");
        this.setLocationRelativeTo(null);
        
    }
    

}
