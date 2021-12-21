import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class MainPanel extends JPanel {

    private BufferedImage toDraw = new BufferedImage(Driver.getWidth(), Driver.getHeight(), BufferedImage.TYPE_INT_RGB);

    public void updateToDraw(BufferedImage newImage){
        toDraw = newImage;
//        repaint();
    }

    @Override
    public void paint(Graphics g){
        g.drawImage(toDraw, 0, 0, toDraw.getWidth(), toDraw.getHeight(), this);
//        System.out.println("drawn");
    }

//    @Override
//    public void paintComponent(Graphics g){
//        super.paintComponent(g);
//        g.drawImage(toDraw, 0, 0, toDraw.getWidth(), toDraw.getHeight(), this);
//    }
}