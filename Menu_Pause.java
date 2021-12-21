import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Menu_Pause extends Menu {

    public Menu_Pause(){
        super(null, new Vector(300, 250), 570, 130);
        ArrayList<Menu_Option> options = new ArrayList<>();
        options.add(new Menu_Option_Back());
        options.add(new Menu_Option_BackToMenu());
        options.add(new Menu_Option_ExitGame());
        this.options = options;
    }

    @Override
    public void update(String keyPressName){
        switch(keyPressName){
            case "LeftArrow":
                if(position > 0)
                    position--;
                break;
            case "RightArrow":
                if(position < (options.size() - 1))
                    position++;
                break;
            case "Z Key":
                options.get(position).selectAction();
                break;
            case "Escape":
                Driver.clear(1);
                break;
        }
    }

    @Override
    public BufferedImage getImage(){
        //draw the options
        //the selected option in bold

        //bufferedimage to return is width, height
        BufferedImage toRet = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = toRet.createGraphics();

//        float alpha = (float)0.5;
//        AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
//        g2d.setComposite(ac);
        //rest of code here
        g2d.setColor(Color.black);
        g2d.fillRect(0, 0, width, height);

        int drawX = 20;
        g2d.setFont(new Font("Monospaced", Font.PLAIN, 20));
        g2d.setColor(Color.white);
        g2d.drawString("Paused", 250, 40);
        for(int i = 0; i < options.size(); i++){
            if(i == position){
                g2d.setFont(new Font("Monospaced", Font.BOLD, 20));
            }
            for(String s : options.get(i).getName()){
                g2d.drawString(s, drawX, 100);
                drawX += 15 * s.length();
                drawX += 90;
            }
            g2d.setFont(new Font("Monospaced", Font.PLAIN, 20));
        }

        return toRet;
    }
}
