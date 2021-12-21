
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.management.ManagementFactory;
import java.util.*;
public class Driver {

    private final static int WIDTH = 1500;
    private final static int HEIGHT = 1000;
    private static double frameSizeMulti = 1;

    //stack of updatable
    //arraylist of drawable
    private static Stack<Updatable> updatable;
    private static ArrayList<Drawable> drawable;

    private static MainPanel panel;

    //updatable must have public void update(String keyName)
    //drawable must have public BufferedImage getImage()

    //Driver has a public method updateImage which redraws starting from the  bottom of the arraylist
    //Driver has a public method clear(int levels) which clears the top (levels) level(s) off the updatable stack
    //and also removes those levels from drawable

    //Driver holds the keypress code, and whenever a key is pressed the top level of "updatable" will recieve the action
    //with a String based on the key pressed.

    public static void main(String[] args){
        readFrameSize();
        JFrame frame = new JFrame("Eucatastrophe: Dodecahedron At Time's End");
        frame.setResizable(false);
        frame.setSize((int)(WIDTH * frameSizeMulti),  (int)(HEIGHT * frameSizeMulti));
        frame.setLayout(new BorderLayout());
        frame.setLocation(0, 0);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new MainPanel();
        panel.setPreferredSize(new Dimension((int)(WIDTH * frameSizeMulti),  (int)(HEIGHT * frameSizeMulti)));
        frame.add(panel, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);

        updatable = new Stack<>();
        drawable = new ArrayList<>();

        doKeys();
        addMainMenu();
        Driver_Music.playTrack("1_Solitary_Dodecahedron", true);
//        Driver_Music.playTrack("15_Inspiration_Of_A_New_World", false);
        updateImage();
    }

    public synchronized static void updateImage(){
        //create bufferedImg width height
        //draw from the bottom of drawable up
        //draw to the frame
        BufferedImage toDraw = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = toDraw.createGraphics();

        g2d.setColor(Color.black);
        g2d.fillRect(0, 0, WIDTH, HEIGHT);

//        System.out.println(drawable.size());
        for(Drawable d : drawable){
            g2d.drawImage(d.getImage(), (int)(d.getLocation().getA()), (int)(d.getLocation().getB()), null);
        }

        toDraw = Driver_GameGraphics.scaleImage(toDraw, (int)(WIDTH * frameSizeMulti),  (int)(HEIGHT * frameSizeMulti));

        //update image panel draws
        panel.updateToDraw(toDraw);
        //have panel draw the image
        panel.repaint();
//        System.out.println("Drawn" + System.currentTimeMillis());

        //        panel.revalidate();
    }


    public synchronized static void clear(int levels){
//        System.out.println("cleared " + System.currentTimeMillis() + " " + levels);
        for(int i = 0; i < levels; i++){
            if(updatable.size() > 1){
                Updatable removed = updatable.pop();
                if(removed instanceof Drawable){
                    drawable.remove(removed);
                }
                if(removed instanceof Driver_Game){
                    ((Driver_Game)removed).endThis();
                }
            }
        }
        if(updatable.peek() instanceof Driver_Game) {
            ((Driver_Game) updatable.peek()).unpause();
//            System.out.println("unpaused");
        }
        updateImage();
    }

    public synchronized static void add(Updatable toAdd){
//        System.out.println("added " + System.currentTimeMillis() + " " + toAdd);
        if(Driver_GameLogic.getCurrentLogicDriver() != null && Driver_GameLogic.getCurrentLogicDriver().defeatRunning){
            return;
        }

        if(updatable.contains(toAdd)){
            return;
        }

        //if this is a driver game and there exists another driver game below this clear that out
        if(toAdd instanceof Driver_Game){
            if(updatable.peek() instanceof Driver_Game){
                ((Driver_Game)(updatable.peek())).endThis();
                clear(1);
            }
        }

        updatable.push(toAdd);
        if(toAdd instanceof Drawable){
            drawable.add((Drawable)toAdd);
        }
        updateImage();

        if(toAdd instanceof Driver_Game){
            //((Driver_Game)toAdd).startGameLoop();

            //otherwise will run on Event Dispatch Thread
            Thread game = new Thread((Driver_Game)toAdd, "Thread-" + toAdd);
//            System.out.println("Thread started " + game);
            game.start();
        }
    }

    private static class KeyAction extends AbstractAction{

        private String cmd;

        KeyAction(String cmd){
            this.cmd = cmd;
        }

        public void actionPerformed(ActionEvent e){
//            System.out.println(cmd + System.currentTimeMillis());
            Driver.updatable.peek().update(cmd);
            updateImage();
//            System.out.println(ManagementFactory.getThreadMXBean().getThreadCount());
//            System.out.println(updatable.size());
        }
    }

    private static void doKeys(){
        InputMap im = panel.getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW);
        ActionMap am = panel.getActionMap();

        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, false), "LeftArrow");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, false), "RightArrow");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, false), "UpArrow");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, false), "DownArrow");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, true), "RLeftArrow");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, true), "RRightArrow");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, true), "RUpArrow");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, true), "RDownArrow");

        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, InputEvent.SHIFT_DOWN_MASK, false), "LeftArrow");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, InputEvent.SHIFT_DOWN_MASK, false), "RightArrow");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, InputEvent.SHIFT_DOWN_MASK, false), "UpArrow");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, InputEvent.SHIFT_DOWN_MASK, false), "DownArrow");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, InputEvent.SHIFT_DOWN_MASK, true), "RLeftArrow");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, InputEvent.SHIFT_DOWN_MASK, true), "RRightArrow");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, InputEvent.SHIFT_DOWN_MASK, true), "RUpArrow");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, InputEvent.SHIFT_DOWN_MASK, true), "RDownArrow");

        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, 0, false), "Z Key");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_X, 0, false), "X Key");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, 0, true), "RZ Key");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_X, 0, true), "RX Key");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.SHIFT_DOWN_MASK, false), "Z Key");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.SHIFT_DOWN_MASK, false), "X Key");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.SHIFT_DOWN_MASK, true), "RZ Key");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.SHIFT_DOWN_MASK, true), "RX Key");

        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_SHIFT, InputEvent.SHIFT_DOWN_MASK, false), "Shift");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_SHIFT, 0, true), "RShift");

        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false), "Escape");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, true), "REscape");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, InputEvent.SHIFT_DOWN_MASK, false), "Escape");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, InputEvent.SHIFT_DOWN_MASK, true), "REscape");

        am.put("LeftArrow", new KeyAction("LeftArrow"));
        am.put("RightArrow", new KeyAction("RightArrow"));
        am.put("UpArrow", new KeyAction("UpArrow"));
        am.put("DownArrow", new KeyAction("DownArrow"));
        am.put("RLeftArrow", new KeyAction("RLeftArrow"));
        am.put("RRightArrow", new KeyAction("RRightArrow"));
        am.put("RUpArrow", new KeyAction("RUpArrow"));
        am.put("RDownArrow", new KeyAction("RDownArrow"));

        am.put("Z Key", new KeyAction("Z Key"));
        am.put("X Key", new KeyAction("X Key"));
        am.put("RZ Key", new KeyAction("RZ Key"));
        am.put("RX Key", new KeyAction("RX Key"));

        am.put("Shift", new KeyAction("Shift"));
        am.put("RShift", new KeyAction("RShift"));

        am.put("Escape", new KeyAction("Escape"));
        am.put("REscape", new KeyAction("REscape"));
    }

    public static int getWidth(){
        return WIDTH;
    }

    public static int getHeight(){
        return HEIGHT;
    }

    public static int getUpdatableSize(){
        return updatable.size();
    }

    private static void addMainMenu(){
        ArrayList<Menu_Option> options = new ArrayList<>();
        options.add(new Menu_Option_Start());
        options.add(new Menu_Option_Practice());
        options.add(new Menu_Option_Options());
        options.add(new Menu_Option_ExitGame());
        Menu toAdd = new Menu(options, new Vector(), WIDTH, HEIGHT);
        add(toAdd);
    }

    private static void readFrameSize(){
        String fileName = "frame_size_multi.txt";
        File file = new File(fileName);
        Scanner scanner;
        String line = new String();
        try{
            scanner = new Scanner(file);
            if(scanner.hasNextLine()){
                line = scanner.nextLine();
            }
        }
        catch(FileNotFoundException e){
            return;
        }
        try{
            frameSizeMulti = Double.parseDouble(line);
        }
        catch(Exception e){
            //return
        }

    }
}
