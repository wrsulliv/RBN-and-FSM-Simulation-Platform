import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class ImagePanel extends JPanel {


	    private BufferedImage image;

	    public ImagePanel(String inputFile) {
	       try {                
	          image = ImageIO.read(new File(inputFile));
	       } catch (IOException ex) {
	            // handle exception...
	       }
	    }

	    public void changeImage(String inputFile)
	    {
	    	try {                
		          image = ImageIO.read(new File(inputFile));
		          this.repaint();
		       } catch (IOException ex) {
		            // handle exception...
		       }
	    	
	    }

	   
		@Override
	    protected void paintComponent(Graphics g) {
	        super.paintComponent(g);
	        g.drawImage(image, 0, 0, null); // see javadoc for more info on the parameters            
	    }

	
}
