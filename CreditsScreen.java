import java.awt.*;
import java.awt.image.BufferedImage;

public class CreditsScreen implements Updatable, Drawable {

    private BufferedImage toRet;
    public CreditsScreen(){
        toRet = new BufferedImage(Driver.getWidth(), Driver.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d= toRet.createGraphics();
        g2d.setColor(Color.white);
        g2d.setFont(new Font("Monospaced", Font.PLAIN, 40));
        g2d.drawString("Eucatastrophe: The Dodecahedron", 100, 100);
        g2d.drawString("Programming: ickdanny", 100, 200);
        g2d.drawString("Visuals: ickdanny", 100, 250);
        g2d.drawString("Audio: ickdanny", 100, 300);
        g2d.drawString("Writing: ickdanny", 100, 350);
        g2d.drawString("Gameplay: ickdanny", 100, 400);
        g2d.drawString("Beta Testers: Teacup, Justin", 100, 500);
        g2d.drawString("Thank you for playing.", 100, 550);
//        g2d.drawString("Thank YOU for playing my game. And congratulations.", 100, 700);
//        Driver_Music.playTrack("15_Inspiration_Of_A_New_World", false);
        Runnable endRunnable = new Runnable() {
            public void run() {
                try {
                    Driver_Music.playTrack("15_Inspiration_Of_A_New_World", false);
                    Thread.sleep(35000);
                    Driver.clear(10);
                    Driver_Music.playTrack("1_Solitary_Dodecahedron", true);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.exit(0);
                }
            }
        };
        Thread thread = new Thread(endRunnable);
        thread.start();
    }

    @Override
    public void update(String keyPressName){

    }

    @Override
    public BufferedImage getImage(){
        return toRet;
    }

    @Override
    public Vector getLocation(){
        return new Vector();
    }
}
