import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Menu implements Updatable, Drawable {

    protected ArrayList<Menu_Option> options;
    protected Vector location;
    protected int width, height;
    protected int position = 0;

    public Menu(ArrayList<Menu_Option> options, Vector location, int width, int height){
        this.options = options;
        this.location = location;
        this.width = width;
        this.height = height;
    }

    public void update(String keyPressName){
        //if z select current option
        //arrow keys go up and down
        //if x remove this from the updatable stack
        switch(keyPressName){
            case "UpArrow":
                if(position > 0)
                    position--;
                break;
            case "DownArrow":
                if(position < (options.size() - 1))
                    position++;
                break;
            case "RightArrow":
            case "Z Key":
                options.get(position).selectAction();
                break;
            case "LeftArrow":
            case "X Key":
            case "Escape":
                Driver.clear(1);
                break;
        }
    }

    public BufferedImage getImage(){
        //draw the options
        //the selected option in bold

        //bufferedimage to return is width, height
        BufferedImage toRet = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = toRet.createGraphics();
        //rest of code here
        g2d.setColor(Color.black);
        g2d.fillRect(0, 0, width, height);

        int drawY = 20;
        g2d.setFont(new Font("Monospaced", Font.PLAIN, 20));
        g2d.setColor(Color.white);
        for(int i = 0; i < options.size(); i++){
            if(i == position){
                g2d.setFont(new Font("Monospaced", Font.BOLD, 20));
            }
            for(String s : options.get(i).getName()){
                g2d.drawString(s, 20, drawY);
                drawY += 20;
            }
            g2d.setFont(new Font("Monospaced", Font.PLAIN, 20));
            drawY += 40;
        }

        return toRet;
    }

    public Vector getLocation(){
        return this.location;
    }

}
