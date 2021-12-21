import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;

import java.awt.geom.AffineTransform;
import java.util.*;

//graphics driver class
public class Driver_GameGraphics /*extends JPanel*/ {
    //graphics related fields
    private BufferedImage buffImg;

    //DIY solution: update toDraw to copy buffImg after every render
    //and draw toDraw instead of buffImg
    private BufferedImage toDraw;
    private Graphics2D buffer;

    //map of string to sprites
    private Map<String, BufferedImage> sprites;

    private BufferedImage background;
    private double drawPoint = -1;

    private boolean withBackground = true;


    //constructor
    public Driver_GameGraphics(String playerName, String stageName) {
        buffImg = new BufferedImage((int) Driver_Game.getWidth(), (int) Driver_Game.getHeight(), BufferedImage.TYPE_INT_RGB);
        toDraw = new BufferedImage((int) Driver_Game.getWidth(), (int) Driver_Game.getHeight(), BufferedImage.TYPE_INT_RGB);
        buffer = buffImg.createGraphics();
        buffer.setColor(Color.gray);
        buffer.setFont(new Font("Monospaced", Font.BOLD, 18));
        buffer.fillRect(0, 0, (int) Driver_Game.getWidth(), (int) Driver_Game.getWidth());
//        setDoubleBuffered(true);
        //setIgnoreRepaint(true);
        toDraw.createGraphics().drawImage(buffImg, 0, 0, (int) Driver_Game.getWidth(), (int) Driver_Game.getHeight(), null);
        sprites = new HashMap<>();
        loadImages(playerName, stageName);
    }

    //display game
    //if the game is over, display nothing
//    public void displayGame(double deltaTime){
//        if(Driver_GameLogic.getCurrentLogicDriver().getGameOver()){
//            return;
//        }
//        updateGraphics(deltaTime);
//    }
    //updateGraphics given the delta time
    //draw background
    //draw stuff
    //draw info
    public void displayGame(double deltaTime) {
        if (!withBackground) {
            buffer.setColor(Color.gray);
            buffer.fillRect(0, 0, (int) Driver_Game.getWidth(), (int) Driver_Game.getHeight());
        } else {
            if (drawPoint == -1) {
                drawPoint = background.getHeight() - Driver_Game.getHeight();
            }
            buffer.drawImage(background.getSubimage(0, (int) drawPoint, (int) Driver_Game.getWidth(), (int) Driver_Game.getHeight()), 0, 0, null);
//            System.out.println(drawPoint);
//            updateDrawPoint();
        }
        Driver_GameLogic logicDriver = Driver_GameLogic.getCurrentLogicDriver();

        for (Projectile_Bomb i : logicDriver.getProjectileBombs()) {
            drawObject(i, deltaTime);
        }
        for (Projectile_Player i : logicDriver.getProjectilePlayers()) {
            drawObject(i, deltaTime);
        }
        //moving enemy and pickup in front of enemy projectiles
        drawObject(logicDriver.getPlayer(), deltaTime);
        for (Projectile_Enemy i : logicDriver.getProjectileEnemies()) {
            drawObject(i, deltaTime);
        }
        for (Enemy i : logicDriver.getEnemies()) {
            drawObject(i, deltaTime);
        }
        for (Pickup i : logicDriver.getPickups()) {
            drawObject(i, deltaTime);
        }

//        drawInfo(logicDriver);
//        drawQuadTree(logicDriver.getQuadTree());
        toDraw.createGraphics().drawImage(buffImg, 0, 0, (int) Driver_Game.getWidth(), (int) Driver_Game.getHeight(), null);
        //repaint();

        //moved this to Driver_Game
//        Driver.updateImage();
    }

    private void drawObject(Entity toDraw, double deltaTime) {
        if (!toDraw.exists) {
            return;
        }
        if (toDraw.getSize() <= 0) {
            return;
        }

        Hitbox hitbox = toDraw.getHitbox();

        double x = toDraw.getX();
        double y = toDraw.getY();
        x += toDraw.getVelocityVector().getA() * deltaTime / Driver_Game.getUPS();
        y += toDraw.getVelocityVector().getB() * deltaTime / Driver_Game.getUPS();

        double size = toDraw.getSize();

        if (!(hitbox instanceof Hitbox_Line)) {
//            buffer.setColor(color);

//        //"C:\Users\Joseph\IdeaProjects\res\Sprites\Enemy Mobs\Wisp\wisp_1.png"
//        try {
//            //final BufferedImage image = ImageIO.read(new File("./res/Sprites/Enemy Mobs/Wisp/wisp_1.png"));
//            final BufferedImage image = ImageIO.read(new File(/*"C:/Users/Joseph/IdeaProjects*/"./res/Sprites/Enemy Mobs/Wisp/wisp_1.png"));
//            buffer.drawImage(image, (int)x, (int)y, null);
//
//        }catch(Exception e){
//            System.out.println("file not found");
//        }

            try {
                SpriteInfo info = toDraw.getSpriteInfo();
                //if the size is zero don't draw anything
                if (info.getSize() != 0) {
                    BufferedImage base = sprites.get(info.getName());


                    //rescale image
                    if (info.getSize() != 1) {
                        base = scaleImage(base, (int) (base.getWidth() * info.getSize()), (int) (base.getHeight() * info.getSize()));
                    }
                    //find top left corner
                    double drawPointX = x - (base.getWidth() / 2) - (info.getOffset().getA() * info.getSize());
                    double drawPointY = y - (base.getHeight() / 2) - (info.getOffset().getB() * info.getSize());

                    //rotate
                    if (info.getRotation() != 0) {
                        drawRotateObject(base, info.getRotation(), info.getOffset(), info.getSize(), buffer, new Vector(drawPointX, drawPointY));
                    } else {
                        buffer.drawImage(base, (int) drawPointX, (int) drawPointY, null);
                    }
                }

            } catch (Exception e) {
            }
        } else {
            buffer.setColor(((Hitbox_Line) hitbox).color);
            buffer.setStroke(new BasicStroke(5));
            buffer.drawLine((int) x, (int) y, (int) (((Hitbox_Line) hitbox).getEndPoint().getA()), (int) (((Hitbox_Line) hitbox).getEndPoint().getB()));
            buffer.setStroke(new BasicStroke(1));
        }

//        if(!(toDraw instanceof Projectile_Bomb)) {
//            if (hitbox instanceof Hitbox_Circle) {
//                buffer.fillOval((int) (x - size), (int) (y - size), (int) size * 2, (int) size * 2);
//            } else if (hitbox instanceof Hitbox_AABB) {
//                double xplus = ((Hitbox_AABB) hitbox).xplus;
//                double xminus = ((Hitbox_AABB) hitbox).xminus;
//                double yplus = ((Hitbox_AABB) hitbox).yplus;
//                double yminus = ((Hitbox_AABB) hitbox).yminus;
//                //go to the lowest, fill the rest
//                buffer.fillRect((int) (x - (size * xminus)), (int) (y - (size * yminus)), (int) (size * (xplus + xminus)), (int) (size * (yplus + yminus)));
//            } else if (hitbox instanceof Hitbox_Line) {
//                buffer.drawLine((int) x, (int) y, (int) (((Hitbox_Line) hitbox).getEndPoint().getA()), (int) (((Hitbox_Line) hitbox).getEndPoint().getB()));
//    }
//        }
//        else{
//            if (hitbox instanceof Hitbox_Circle) {
//                buffer.drawOval((int) (x - size), (int) (y - size), (int) size * 2, (int) size * 2);
//            } else if (hitbox instanceof Hitbox_AABB) {
//                double xplus = ((Hitbox_AABB) hitbox).xplus;
//                double xminus = ((Hitbox_AABB) hitbox).xminus;
//                double yplus = ((Hitbox_AABB) hitbox).yplus;
//                double yminus = ((Hitbox_AABB) hitbox).yminus;
//                buffer.drawRect((int) (x - (size * xminus)), (int) (y - (size * yminus)), (int) (size * (xplus + xminus)), (int) (size * (yplus + yminus)));
//            } else if (hitbox instanceof Hitbox_Line) {
//                buffer.drawLine((int) x, (int) y, (int) (((Hitbox_Line) hitbox).getEndPoint().getA()), (int) (((Hitbox_Line) hitbox).getEndPoint().getB()));
//            }
//        }

//          drawObjectHitbox(toDraw, x, y, size);

//        drawObjectHP(toDraw, toDraw.getA(), toDraw.getB());
    }

    private void drawObjectHitbox(Entity toDraw, double x, double y, double size){
        buffer.setColor(Color.white);
        Hitbox_AABB hitboxAABB = toDraw.getHitboxAABB();
        double xplus = hitboxAABB.xplus;
        double xminus = hitboxAABB.xminus;
        double yplus = hitboxAABB.yplus;
        double yminus = hitboxAABB.yminus;
        buffer.drawRect((int)(x - (size * xminus)), (int)(y - (size * yminus)), (int)(size * (xplus + xminus)), (int)(size * (yplus + yminus)));

        hitboxAABB = toDraw.getTwoFrameHitbox();
        xplus = hitboxAABB.xplus;
        xminus = hitboxAABB.xminus;
        yplus = hitboxAABB.yplus;
        yminus = hitboxAABB.yminus;
        buffer.drawRect((int)(x - (size * xminus)), (int)(y - (size * yminus)), (int)(size * (xplus + xminus)), (int)(size * (yplus + yminus)));

        Hitbox hitbox = toDraw.trueHitbox;
        if (hitbox instanceof Hitbox_Circle) {
            buffer.drawOval((int) (x - size), (int) (y - size), (int) size * 2, (int) size * 2);
        } else if (hitbox instanceof Hitbox_AABB) {
            xplus = ((Hitbox_AABB) hitbox).xplus;
            xminus = ((Hitbox_AABB) hitbox).xminus;
            yplus = ((Hitbox_AABB) hitbox).yplus;
            yminus = ((Hitbox_AABB) hitbox).yminus;
            buffer.drawRect((int) (x - (size * xminus)), (int) (y - (size * yminus)), (int) (size * (xplus + xminus)), (int) (size * (yplus + yminus)));
        } else if (hitbox instanceof Hitbox_Line) {
//            Color lineColor = ((Hitbox_Line)hitbox).color;
//            buffer.setStroke(new BasicStroke(3));
            buffer.drawLine((int) x, (int) y, (int) (((Hitbox_Line) hitbox).getEndPoint().getA()), (int) (((Hitbox_Line) hitbox).getEndPoint().getB()));
//            buffer.setStroke(new BasicStroke(1));
        }
    }

    private void drawObjectHP(Entity toDraw, double x, double y){
        buffer.setColor(Color.black);
        buffer.drawString("" + toDraw.getHP(), (int)x, (int)y);
    }

    //recursive quad tree drawing
    private void drawQuadTree(QuadTree quadTree){
        if(quadTree.getNodes()[0] != null){
            drawQuadTree(quadTree.getNodes()[0]);
            drawQuadTree(quadTree.getNodes()[1]);
            drawQuadTree(quadTree.getNodes()[2]);
            drawQuadTree(quadTree.getNodes()[3]);
        }
        buffer.drawRect((int)quadTree.getXmin(), (int)quadTree.getYmin(), (int)quadTree.getXmax() - (int)quadTree.getXmin(), (int)quadTree.getYmax() - (int)quadTree.getYmin());
    }

    private void drawInfo(Driver_GameLogic logicDriver){
        buffer.setColor(Color.white);
        buffer.drawString("Tick:" + logicDriver.getTick(), 30, 40);
        buffer.drawString("Entities:" + logicDriver.getEntities().size(), 30, 70);
        buffer.drawString("Enemies:" + logicDriver.getEnemies().size(), 30, 100);
        buffer.drawString("Enemy Projectiles:" + logicDriver.getProjectileEnemies().size(), 30, 130);
        buffer.drawString("Player Projectiles:" + logicDriver.getProjectilePlayers().size(), 30, 160);
        buffer.drawString("Bomb Projectiles:" + logicDriver.getProjectileBombs().size(), 30, 190);
        buffer.drawString("Pickups: " + logicDriver.getPickups().size(), 30, 220);
        buffer.drawString("Inelligible: " + logicDriver.isInelligible(), 30, 250);
        buffer.drawString("Spell Running: " + logicDriver.isSpellRunning(), 30, 280);
//        buffer.drawString("Lives:" + (int) logicDriver.getPlayer().getHP(), 30, 250);
//        buffer.drawString("Bombs:" + logicDriver.getPlayer().getBombs(), 30, 280);
//        buffer.drawString("Power: " + logicDriver.getPlayer().getPower(), 30, 310);
//        buffer.drawString("| Arrow Keys = Move | Shift = Focus | Z = Attack | X = Bomb |", 80, 950);
    }

    //update every tick not every time the graphics gets updated
    public void updateDrawPoint(){
        if(drawPoint == -1)
            return;
        if(drawPoint != 0){
            double pixelsPerSecond = 30;
            drawPoint -= pixelsPerSecond/60;
            if(drawPoint < 0)
                drawPoint = 0;
        }
//        System.out.println(drawPoint);
    }

    //rotates an image by degrees around center+offset
    //used after image already scaled to size
    //size must be the final size not just the size multiplier
//    private BufferedImage rotateImage(BufferedImage toRotate, double degrees, Vector offset, double size){
//        double radians = Math.toRadians(degrees);
//        double locationX = (toRotate.getWidth() / 2) + (offset.getA() * size);
//        double locationY = (toRotate.getHeight() / 2) + (offset.getB() * size);
//
//        AffineTransform tx = AffineTransform.getRotateInstance(radians, locationX, locationY);
//        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
//
//        return op.filter(toRotate, null);
//    }

    private void drawRotateObject(BufferedImage toDraw, double degrees, Vector offset, double size, Graphics2D g, Vector coords){
        double radians = Math.toRadians(degrees);
        double locationX = (toDraw.getWidth() / 2) + (offset.getA() * size);
        double locationY = (toDraw.getHeight() / 2) + (offset.getB() * size);
        AffineTransform tx = AffineTransform.getRotateInstance(radians, locationX + coords.getA(), locationY + coords.getB());
        //AffineTransform tx = AffineTransform.getRotateInstance(radians);
//        AffineTransform tx = AffineTransform.getTranslateInstance(100, 100);
        AffineTransform backup = g.getTransform();
//        g.drawImage(sprites.get("Pickup_bomb"), (int)coords.getA(), (int)coords.getB(), null);
//        g.rotate(radians, locationX, locationY);
        g.setTransform(tx);
        g.drawImage(toDraw, (int)coords.getA(), (int)coords.getB(), null);
//        g.drawImage(sprites.get("Pickup_power"), (int)coords.getA(), (int)coords.getB(), null);
//        System.out.println(coords);
        g.setTransform(backup);
    }

    public static BufferedImage scaleImage(BufferedImage toScale, int newWidth, int newHeight){
        BufferedImage scaled = new BufferedImage(newWidth, newHeight, toScale.getType());
        Graphics2D g = scaled.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        g.drawImage(toScale, 0, 0, newWidth, newHeight, 0, 0, toScale.getWidth(), toScale.getHeight(), null);
        g.dispose();
        return scaled;
    }


//    @Override
//    public void paint(Graphics g){
//        //g.drawImage(buffImg, 0, 0, (int) Driver_Game.getWidth(), (int) Driver_Game.getHeight(), this);
//        g.drawImage(toDraw, 0, 0, (int) Driver_Game.getWidth(), (int) Driver_Game.getHeight(), this);
//    }
    public BufferedImage getToDraw(){
        return this.toDraw;
    }

    private void loadImages(String playerName, String stageName){
        switch(playerName){
            case "AbaddonA": case "AbaddonB":
                loadAbaddon();
                break;
            case "AzraelA": case "AzraelB":
                loadAzrael();
                break;
            case "MiraA": case "MiraB":
                loadMira();
                break;
        }
        switch(stageName){
            case "Stage1":
                loadStage1();
                loadBackground("OrdinaryRoom");
                break;
            case "Stage2":
                loadStage2();
                loadBackground("Sandbox");
                break;
            case "Stage3":
                loadStage3();
                loadBackground("SpidersNest");
                break;
            case "Stage4":
                loadStage4();
                loadBackground("Forge");
                break;
            case "Stage5":
                loadStage5();
                loadBackground("LaboratoryLobby");
                break;
            case "Stage6":
                loadStage6();
                loadBackground("DodecahedronHead");
                break;
            case "StageEX":
                loadStageEX();
                loadBackground("Hellscape");
                break;
        }
    }
    private void loadBackground(String name){
        try{
            background = ImageIO.read(new File("./res/Backgrounds/" + name + ".png"));
        }catch(Exception e){
            System.out.println("Background Load Failure");
        }
    }
    private void loadAbaddon(){
        try{
            final BufferedImage sheet = ImageIO.read(new File("./res/Sheets/Abaddon.png"));
            //abaA
            sprites.put("Player_AbaddonA_idle1", sheet.getSubimage(900, 0, 90, 90));
            sprites.put("Player_AbaddonA_idle2", sheet.getSubimage(450, 90, 90, 90));
            sprites.put("Player_AbaddonA_idle3", sheet.getSubimage(630, 90, 90, 90));
            sprites.put("Player_AbaddonA_idle4", sheet.getSubimage(540, 90, 90, 90));
            sprites.put("Player_AbaddonA_leftTurn1", sheet.getSubimage(360, 90, 90, 90));
            sprites.put("Player_AbaddonA_leftTurn2", sheet.getSubimage(270, 90, 90, 90));
            sprites.put("Player_AbaddonA_leftTurn3", sheet.getSubimage(720,90, 90, 90));
            sprites.put("Player_AbaddonA_left1", sheet.getSubimage(0, 90, 90, 90));
            sprites.put("Player_AbaddonA_left2", sheet.getSubimage(180, 90, 90, 90));
            sprites.put("Player_AbaddonA_left3", sheet.getSubimage(90, 180, 90, 90));
            sprites.put("Player_AbaddonA_left4", sheet.getSubimage(180,180, 90, 90));
            sprites.put("Player_AbaddonA_rightTurn1", sheet.getSubimage(360,180, 90, 90));
            sprites.put("Player_AbaddonA_rightTurn2", sheet.getSubimage(450, 180, 90, 90));
            sprites.put("Player_AbaddonA_rightTurn3", sheet.getSubimage(270, 180, 90, 90));
            sprites.put("Player_AbaddonA_right1", sheet.getSubimage(0, 180, 90, 90));
            sprites.put("Player_AbaddonA_right2", sheet.getSubimage(900, 90, 90, 90));
            sprites.put("Player_AbaddonA_right3", sheet.getSubimage(810, 90, 90, 90));
            sprites.put("Player_AbaddonA_right4", sheet.getSubimage(90, 90, 90, 90));
            //abaB
            sprites.put("Player_AbaddonB_idle1", sheet.getSubimage(100, 0, 50, 50));
            sprites.put("Player_AbaddonB_idle2", sheet.getSubimage(250, 0, 50, 50));
            sprites.put("Player_AbaddonB_idle3", sheet.getSubimage(550, 0, 50, 50));
            sprites.put("Player_AbaddonB_idle4", sheet.getSubimage(350, 0, 50, 50));
            sprites.put("Player_AbaddonB_leftTurn1", sheet.getSubimage(300, 0, 50, 50));
            sprites.put("Player_AbaddonB_leftTurn2", sheet.getSubimage(200, 0, 50, 50));
            sprites.put("Player_AbaddonB_leftTurn3", sheet.getSubimage(150, 0, 50, 50));
            sprites.put("Player_AbaddonB_left1", sheet.getSubimage(50, 0, 50, 50));
            sprites.put("Player_AbaddonB_left2", sheet.getSubimage(0, 0, 50, 50));
            sprites.put("Player_AbaddonB_left3", sheet.getSubimage(400, 0, 50, 50));
            sprites.put("Player_AbaddonB_left4", sheet.getSubimage(450, 0, 50, 50));
            sprites.put("Player_AbaddonB_rightTurn1", sheet.getSubimage(750, 0, 50, 50));
            sprites.put("Player_AbaddonB_rightTurn2", sheet.getSubimage(850, 0, 50, 50));
            sprites.put("Player_AbaddonB_rightTurn3", sheet.getSubimage(800, 0, 50, 50));
            sprites.put("Player_AbaddonB_right1", sheet.getSubimage(700, 0, 50, 50));
            sprites.put("Player_AbaddonB_right2", sheet.getSubimage(650, 0, 50, 50));
            sprites.put("Player_AbaddonB_right3", sheet.getSubimage(500, 0, 50, 50));
            sprites.put("Player_AbaddonB_right4", sheet.getSubimage(600, 0, 50, 50));
            //projectiles
            sprites.put("PP_halo", sheet.getSubimage(540, 180, 100, 100));
            sprites.put("PP_orb_black", sheet.getSubimage(740, 180, 100, 100));
            sprites.put("PP_orb_yellow", sheet.getSubimage(640, 180, 100, 100));
            sprites.put("B_waveBomb", sheet.getSubimage(0, 280, 1000, 1000));
            sprites.put("B_fieldBomb", sheet.getSubimage(0, 1280, 1000, 1000));
        }catch(Exception e){
            System.out.println("Error loadAbaddon");
        }
    }
    private void loadAzrael(){
        try{
            final BufferedImage sheet = ImageIO.read(new File("./res/Sheets/Azrael.png"));
            //azr
            sprites.put("Player_Azrael_idle", sheet.getSubimage(250, 0, 50, 50));
            sprites.put("Player_Azrael_leftTurn1", sheet.getSubimage(400, 0, 50, 50));
            sprites.put("Player_Azrael_leftTurn2", sheet.getSubimage(300, 0, 50, 50));
            sprites.put("Player_Azrael_leftTurn3", sheet.getSubimage(0 ,0, 50, 50));
            sprites.put("Player_Azrael_left", sheet.getSubimage(350, 0, 50, 50));
            sprites.put("Player_Azrael_rightTurn1", sheet.getSubimage(150, 0, 50, 50));
            sprites.put("Player_Azrael_rightTurn2", sheet.getSubimage(100, 0, 50, 50));
            sprites.put("Player_Azrael_rightTurn3", sheet.getSubimage(200, 0, 50, 50));
            sprites.put("Player_Azrael_right", sheet.getSubimage(50, 0, 50, 50));
            //projectiles
            sprites.put("PP_halo", sheet.getSubimage(450, 0, 100, 100));
            sprites.put("PP_orb_yellow", sheet.getSubimage(650, 0, 100, 100));
            sprites.put("PP_boomerangScythe", sheet.getSubimage(550, 0, 100, 100));
            sprites.put("B_bombScythe", sheet.getSubimage(0, 100, 150, 150));
            sprites.put("B_bigLaserBomb1", sheet.getSubimage(350, 100, 200, 1300));
            sprites.put("B_bigLaserBomb2", sheet.getSubimage(550, 100, 200, 1300));
            sprites.put("B_bigLaserBomb3", sheet.getSubimage(150, 100, 200, 1300));
        }catch(Exception e){
            System.out.println("Error loadAzrael");
        }
    }
    private void loadMira(){
        try{
            final BufferedImage sheet = ImageIO.read(new File("./res/Sheets/Mira3.png"));
            //mira
            sprites.put("Player_Mira3_idle1", sheet.getSubimage(650, 0, 50, 50));
            sprites.put("Player_Mira3_idle2", sheet.getSubimage(700, 0, 50, 50));
            sprites.put("Player_Mira3_idle3", sheet.getSubimage(600, 0, 50, 50));
            sprites.put("Player_Mira3_idle4", sheet.getSubimage(550, 0, 50, 50));
            sprites.put("Player_Mira3_leftTurn1", sheet.getSubimage(950, 0, 50, 50));
            sprites.put("Player_Mira3_leftTurn2", sheet.getSubimage(900, 0, 50, 50));
            sprites.put("Player_Mira3_leftTurn3", sheet.getSubimage(800, 0, 50, 50));
            sprites.put("Player_Mira3_left1", sheet.getSubimage(850, 0, 50, 50));
            sprites.put("Player_Mira3_left2", sheet.getSubimage(750, 0, 50, 50));
            sprites.put("Player_Mira3_left3", sheet.getSubimage(300, 0, 50, 50));
            sprites.put("Player_Mira3_left4", sheet.getSubimage(200, 0, 50, 50));
            sprites.put("Player_Mira3_rightTurn1", sheet.getSubimage(150, 0, 50, 50));
            sprites.put("Player_Mira3_rightTurn2", sheet.getSubimage(100, 0, 50, 50));
            sprites.put("Player_Mira3_rightTurn3", sheet.getSubimage(250, 0, 50, 50));
            sprites.put("Player_Mira3_right1", sheet.getSubimage(400, 0, 50, 50));
            sprites.put("Player_Mira3_right2", sheet.getSubimage(500, 0, 50, 50));
            sprites.put("Player_Mira3_right3", sheet.getSubimage(450, 0, 50, 50));
            sprites.put("Player_Mira3_right4", sheet.getSubimage(350, 0, 50, 50));
            //projectiles
            //white = miraB attack, other colors = bomb
            sprites.put("PP_iceShard", sheet.getSubimage(400, 50, 20, 100));
            sprites.put("PP_wave", sheet.getSubimage(0, 0, 100, 40));
            sprites.put("PP_waveBall", sheet.getSubimage(300, 50, 100, 100));
            sprites.put("PP_orb_white", sheet.getSubimage(520, 50, 100, 100));
            sprites.put("PP_oval_white", sheet.getSubimage(100, 50, 50, 80));
            sprites.put("B_orb_blue", sheet.getSubimage(200, 50, 100, 100));
            sprites.put("B_orb_green", sheet.getSubimage(420, 50, 100, 100));
            sprites.put("B_orb_red", sheet.getSubimage(620, 50, 100, 100));
            sprites.put("B_oval_blue", sheet.getSubimage(150, 50, 50, 80));
            sprites.put("B_oval_green", sheet.getSubimage(0, 50, 50, 80));
            sprites.put("B_oval_red", sheet.getSubimage(50, 50, 50, 80));
            sprites.put("B_rippleBomb", sheet.getSubimage(0, 150, 1000, 1000));
        }catch(Exception e){
            System.out.println("Error loadMira");
        }
    }
    private void loadStage1(){
        try{
            final BufferedImage sheet = ImageIO.read(new File("./res/Sheets/Stage1.png"));
            //pickups
            sprites.put("Pickup_bomb", sheet.getSubimage(0, 0, 50, 50));
            sprites.put("Pickup_life", sheet.getSubimage(50, 50, 50, 50));
            sprites.put("Pickup_power", sheet.getSubimage(150, 100, 50, 50));
            //enemies
            sprites.put("E_wisp1", sheet.getSubimage(0, 150, 150, 50));
            sprites.put("E_wisp2", sheet.getSubimage(0, 100, 150, 50));
            sprites.put("E_wisp3", sheet.getSubimage(100, 50, 150, 50));
            sprites.put("E_wisp4", sheet.getSubimage(0, 250, 150, 50));
            //boss Mira1
            sprites.put("Boss_Mira1_idle1", sheet.getSubimage(150, 250, 50, 50));
            sprites.put("Boss_Mira1_idle2", sheet.getSubimage(200, 200, 50, 50));
            sprites.put("Boss_Mira1_idle3", sheet.getSubimage(100, 200, 50, 50));
            sprites.put("Boss_Mira1_idle4", sheet.getSubimage(150, 200, 50, 50));
            //boss Mira2
            sprites.put("Boss_Mira2_idle1", sheet.getSubimage(0, 300, 50, 50));
            sprites.put("Boss_Mira2_idle2", sheet.getSubimage(200, 250, 50, 50));
            sprites.put("Boss_Mira2_idle3", sheet.getSubimage(50, 0, 50, 50));
            sprites.put("Boss_Mira2_idle4", sheet.getSubimage(200, 0, 50, 50));
            //enemy projectiles
            sprites.put("PE_iceShard", sheet.getSubimage(0, 480, 70, 100));
            sprites.put("PE_oval_blue", sheet.getSubimage(50, 300, 50, 80));
            sprites.put("PE_oval_grey", sheet.getSubimage(50, 380, 50, 80));
            sprites.put("PE_oval_lightBlue", sheet.getSubimage(100, 380, 50, 80));
            sprites.put("PE_oval_white", sheet.getSubimage(0, 380, 50, 80));
            sprites.put("PE_small_blue", sheet.getSubimage(50, 200, 50, 50));
            sprites.put("PE_small_grey", sheet.getSubimage(150, 0, 50, 50));
            sprites.put("PE_small_lightBlue", sheet.getSubimage(0, 50, 50, 50));
            sprites.put("PE_small_white", sheet.getSubimage(100, 0, 50, 50));
            sprites.put("PE_medium_blue", sheet.getSubimage(150, 380, 100, 100));
            sprites.put("PE_medium_grey", sheet.getSubimage(70, 480, 100, 100));
            sprites.put("PE_medium_lightBlue", sheet.getSubimage(100, 580, 100, 100));
            sprites.put("PE_medium_white", sheet.getSubimage(0, 580, 100, 100));
            sprites.put("PE_large_blue", sheet.getSubimage(0, 1480, 200, 200));
            sprites.put("PE_large_grey", sheet.getSubimage(0, 880, 200, 200));
            sprites.put("PE_large_lightBlue", sheet.getSubimage(0, 1080, 200, 200));
            sprites.put("PE_large_white", sheet.getSubimage(0, 1280, 200, 200));
        }catch(Exception e){
            System.out.println("Error loadStage1");
        }
    }
    private void loadStage2(){
        try{
            final BufferedImage sheet = ImageIO.read(new File("./res/Sheets/Stage2.png"));
            //pickups
            sprites.put("Pickup_bomb", sheet.getSubimage(50, 50, 50, 50));
            sprites.put("Pickup_life", sheet.getSubimage(250, 0, 50, 50));
            sprites.put("Pickup_power", sheet.getSubimage(0, 0, 50, 50));
            //enemies
            sprites.put("E_wisp1", sheet.getSubimage(520, 130, 150, 50));
            sprites.put("E_wisp2", sheet.getSubimage(670, 130, 150, 50));
            sprites.put("E_wisp3", sheet.getSubimage(820, 130, 150, 50));
            sprites.put("E_wisp4", sheet.getSubimage(0, 210, 150, 50));
            sprites.put("E_machine_bullet1", sheet.getSubimage(250, 130, 75, 75));
            sprites.put("E_machine_bullet2", sheet.getSubimage(325, 130, 75, 75));
            sprites.put("E_machine_laser", sheet.getSubimage(500, 50, 55, 55));
            sprites.put("E_machine_shield", sheet.getSubimage(400, 130, 120, 60));
            sprites.put("E_machine_spray", sheet.getSubimage(551, 50, 60, 60));
            //boss Neo
            sprites.put("Boss_Neo_idle1", sheet.getSubimage(50, 0, 50, 50));
            sprites.put("Boss_Neo_idle2", sheet.getSubimage(150, 0, 50, 50));
            sprites.put("Boss_Neo_idle3", sheet.getSubimage(100, 0, 50, 50));
            sprites.put("Boss_Neo_idle4", sheet.getSubimage(500, 0, 50, 50));
            sprites.put("Boss_Neo_left1", sheet.getSubimage(550, 0, 50, 50));
            sprites.put("Boss_Neo_left2", sheet.getSubimage(800, 0, 50, 50));
            sprites.put("Boss_Neo_left3", sheet.getSubimage(400, 50, 50, 50));
            sprites.put("Boss_Neo_left4", sheet.getSubimage(0, 50, 50, 50));
            sprites.put("Boss_Neo_right1", sheet.getSubimage(950, 0, 50, 50));
            sprites.put("Boss_Neo_right2", sheet.getSubimage(850, 0, 50, 50));
            sprites.put("Boss_Neo_right3", sheet.getSubimage(750, 0, 50, 50));
            sprites.put("Boss_Neo_right4", sheet.getSubimage(600, 0, 50, 50));
            sprites.put("Boss_Neo_attack1", sheet.getSubimage(400, 0, 50, 50));
            sprites.put("Boss_Neo_attack2", sheet.getSubimage(300, 0, 50, 50));
            sprites.put("Boss_Neo_attack3", sheet.getSubimage(200, 0, 50, 50));
            //enemy projectiles
            sprites.put("PE_rock", sheet.getSubimage(600, 310, 100, 100));
            sprites.put("PE_pulsar1", sheet.getSubimage(200, 310, 100, 100));
            sprites.put("PE_pulsar2", sheet.getSubimage(300, 310, 100, 100));
            sprites.put("PE_pulsar3", sheet.getSubimage(700, 310, 100, 100));
            sprites.put("PE_pulsar4", sheet.getSubimage(500, 310, 100, 100));

            sprites.put("PE_oval_grey", sheet.getSubimage(815, 50, 50, 80));
            sprites.put("PE_oval_red", sheet.getSubimage(150, 130, 50, 80));
            sprites.put("PE_oval_white", sheet.getSubimage(200, 130, 50, 80));
            sprites.put("PE_oval_yellow", sheet.getSubimage(915, 50, 50, 80));
            sprites.put("PE_oval_blue", sheet.getSubimage(615, 50, 50, 80));
            sprites.put("PE_oval_orange", sheet.getSubimage(715, 50, 50, 80));
            sprites.put("PE_oval_lightBlue", sheet.getSubimage(100, 130, 50, 80));

            sprites.put("PE_small_grey", sheet.getSubimage(150, 50, 50, 50));
            sprites.put("PE_small_red", sheet.getSubimage(250, 50, 50, 50));
            sprites.put("PE_small_white", sheet.getSubimage(200, 50, 50, 50));
            sprites.put("PE_small_yellow", sheet.getSubimage(300, 50, 50, 50));
            sprites.put("PE_small_blue", sheet.getSubimage(700, 0, 50, 50));
            sprites.put("PE_small_orange", sheet.getSubimage(650, 0, 50, 50));
            sprites.put("PE_small_lightBlue", sheet.getSubimage(450, 50, 50, 50));

            sprites.put("PE_medium_grey", sheet.getSubimage(450, 210, 100, 100));
            sprites.put("PE_medium_red", sheet.getSubimage(650, 210, 100, 100));
            sprites.put("PE_medium_white", sheet.getSubimage(850, 210, 100, 100));
            sprites.put("PE_medium_yellow", sheet.getSubimage(750, 210, 100, 100));
            sprites.put("PE_medium_blue", sheet.getSubimage(550, 210, 100, 100));
            sprites.put("PE_medium_orange", sheet.getSubimage(350, 210, 100, 100));
            sprites.put("PE_medium_lightBlue", sheet.getSubimage(250, 210, 100, 100));

            sprites.put("PE_large_blue", sheet.getSubimage(0, 410, 200, 200));
            sprites.put("PE_large_orange", sheet.getSubimage(600, 410, 200, 200));
            sprites.put("PE_large_red", sheet.getSubimage(800, 410, 200, 200));
            sprites.put("PE_large_white", sheet.getSubimage(400, 410, 200, 200));
            sprites.put("PE_large_yellow", sheet.getSubimage(200, 410, 200, 200));
        }catch(Exception e){
            System.out.println("Error loadStage2");
        }
    }
    private void loadStage3(){
        try{
            final BufferedImage sheet = ImageIO.read(new File("./res/Sheets/Stage3.png"));
            //pickups
            sprites.put("Pickup_bomb", sheet.getSubimage(50, 45, 50, 50));
            sprites.put("Pickup_life", sheet.getSubimage(200, 45, 50, 50));
            sprites.put("Pickup_power", sheet.getSubimage(50, 95, 50, 50));
            //enemies
            sprites.put("E_wisp1", sheet.getSubimage(0, 195, 150, 50));
            sprites.put("E_wisp2", sheet.getSubimage(0, 145, 150, 50));
            sprites.put("E_wisp3", sheet.getSubimage(100, 95, 150, 50));
            sprites.put("E_wisp4", sheet.getSubimage(0, 295, 150, 50));
            sprites.put("E_spider1", sheet.getSubimage(180, 0, 45, 45));
            sprites.put("E_spider2", sheet.getSubimage(0, 0, 45, 45));
            sprites.put("E_spider3", sheet.getSubimage(45, 0, 45, 45));
            sprites.put("E_spider4", sheet.getSubimage(90, 0, 45, 45));
            sprites.put("E_spider5", sheet.getSubimage(135, 0, 45, 45));
            //boss Claire
            sprites.put("Boss_Claire_idle1", sheet.getSubimage(100, 395, 50, 50));
            sprites.put("Boss_Claire_idle2", sheet.getSubimage(150, 395, 50, 50));
            sprites.put("Boss_Claire_idle3", sheet.getSubimage(200, 345, 50, 50));
            sprites.put("Boss_Claire_idle4", sheet.getSubimage(200, 395, 50, 50));
            sprites.put("Boss_Claire_left1", sheet.getSubimage(0, 395, 50, 50));
            sprites.put("Boss_Claire_left2", sheet.getSubimage(200, 295, 50, 50));
            sprites.put("Boss_Claire_left3", sheet.getSubimage(150, 295, 50, 50));
            sprites.put("Boss_Claire_left4", sheet.getSubimage(0, 345, 50, 50));
            sprites.put("Boss_Claire_right1", sheet.getSubimage(100, 345, 50, 50));
            sprites.put("Boss_Claire_right2", sheet.getSubimage(50, 395, 50, 50));
            sprites.put("Boss_Claire_right3", sheet.getSubimage(150, 345, 50, 50));
            sprites.put("Boss_Claire_right4", sheet.getSubimage(50, 345, 50, 50));
            //enemy projectiles
            sprites.put("PE_oval_blue", sheet.getSubimage(50, 525, 50, 80));
            sprites.put("PE_oval_red", sheet.getSubimage(200, 445, 50, 80));
            sprites.put("PE_oval_violet", sheet.getSubimage(50, 445, 50, 80));
            sprites.put("PE_oval_lightBlue", sheet.getSubimage(100, 445, 50, 80));
            sprites.put("PE_oval_white", sheet.getSubimage(150, 445, 50, 80));

            sprites.put("PE_small_blue", sheet.getSubimage(50, 245, 50, 50));
            sprites.put("PE_small_red", sheet.getSubimage(100, 45, 50, 50));
            sprites.put("PE_small_violet", sheet.getSubimage(150, 45, 50, 50));
            sprites.put("PE_small_lightBlue", sheet.getSubimage(100, 245, 50, 50));
            sprites.put("PE_small_white", sheet.getSubimage(200, 195, 50, 50));

            sprites.put("PE_medium_blue", sheet.getSubimage(0, 605, 100, 100));
            sprites.put("PE_medium_red", sheet.getSubimage(100, 905, 100, 100));
            sprites.put("PE_medium_violet", sheet.getSubimage(0, 705, 100, 100));
            sprites.put("PE_medium_lightBlue", sheet.getSubimage(0, 905, 100, 100));
            sprites.put("PE_medium_white", sheet.getSubimage(0, 1005, 100, 100));

            sprites.put("PE_large_blue", sheet.getSubimage(0, 1305, 200, 200));
            sprites.put("PE_large_red", sheet.getSubimage(0, 1105, 200, 200));
            sprites.put("PE_large_violet", sheet.getSubimage(0, 1505, 200, 200));
        }catch(Exception e){
            System.out.println("Error loadStage3");
        }
    }
    private void loadStage4(){
        try{
            final BufferedImage sheet = ImageIO.read(new File("./res/Sheets/Stage4.png"));
            //pickups
            sprites.put("Pickup_bomb", sheet.getSubimage(850, 50, 50, 50));
            sprites.put("Pickup_life", sheet.getSubimage(500, 100, 50, 50));
            sprites.put("Pickup_power", sheet.getSubimage(50, 150, 50, 50));
            //enemies
            sprites.put("E_wisp1", sheet.getSubimage(750, 100, 150, 50));
            sprites.put("E_wisp2", sheet.getSubimage(350, 100, 150, 50));
            sprites.put("E_wisp3", sheet.getSubimage(650, 50, 150, 50));
            sprites.put("E_wisp4", sheet.getSubimage(400, 0, 150, 50));
            sprites.put("E_machine_bullet1", sheet.getSubimage(435, 150, 75, 75));
            sprites.put("E_machine_bullet2", sheet.getSubimage(510, 150, 75, 75));
            sprites.put("E_machine_laser", sheet.getSubimage(200, 150, 55, 55));
            sprites.put("E_machine_shield", sheet.getSubimage(255, 150, 120, 60));
            sprites.put("E_machine_spray", sheet.getSubimage(375, 150, 60, 60));
            //boss XLA
            sprites.put("Boss_XiaoLiA_idle1", sheet.getSubimage(550, 0, 50, 50));
            sprites.put("Boss_XiaoLiA_idle2", sheet.getSubimage(650, 0, 50, 50));
            sprites.put("Boss_XiaoLiA_idle3", sheet.getSubimage(800, 0, 50, 50));
            sprites.put("Boss_XiaoLiA_idle4", sheet.getSubimage(700, 0, 50, 50));
            sprites.put("Boss_XiaoLiA_left1", sheet.getSubimage(150, 0, 50, 50));
            sprites.put("Boss_XiaoLiA_left2", sheet.getSubimage(300, 0, 50, 50));
            sprites.put("Boss_XiaoLiA_left3", sheet.getSubimage(200, 0, 50, 50));
            sprites.put("Boss_XiaoLiA_left4", sheet.getSubimage(250, 0, 50, 50));
            sprites.put("Boss_XiaoLiA_right1", sheet.getSubimage(600, 50, 50, 50));
            sprites.put("Boss_XiaoLiA_right2", sheet.getSubimage(500, 50, 50, 50));
            sprites.put("Boss_XiaoLiA_right3", sheet.getSubimage(550, 50, 50, 50));
            sprites.put("Boss_XiaoLiA_right4", sheet.getSubimage(400, 50, 50, 50));
            sprites.put("Boss_XiaoLiA_leftSlash1", sheet.getSubimage(750, 0, 50, 50));
            sprites.put("Boss_XiaoLiA_leftSlash2", sheet.getSubimage(600, 0, 50, 50));
            sprites.put("Boss_XiaoLiA_leftSlash3", sheet.getSubimage(100, 100, 50, 50));
            sprites.put("Boss_XiaoLiA_leftSlash4", sheet.getSubimage(0 ,0, 50, 50));
            sprites.put("Boss_XiaoLiA_leftSlash5", sheet.getSubimage(50, 0, 50, 50));
            sprites.put("Boss_XiaoLiA_rightSlash1", sheet.getSubimage(850, 0, 50, 50));
            sprites.put("Boss_XiaoLiA_rightSlash2", sheet.getSubimage(0, 50, 50, 50));
            sprites.put("Boss_XiaoLiA_rightSlash3", sheet.getSubimage(300, 50, 50, 50));
            sprites.put("Boss_XiaoLiA_rightSlash4", sheet.getSubimage(350, 50, 50, 50));
            sprites.put("Boss_XiaoLiA_rightSlash5", sheet.getSubimage(450, 50, 50, 50));
            sprites.put("Boss_XiaoLiA_spin1", sheet.getSubimage(250, 50, 50, 50));
            sprites.put("Boss_XiaoLiA_spin2", sheet.getSubimage(900, 0, 50, 50));
            sprites.put("Boss_XiaoLiA_spin3", sheet.getSubimage(950, 0, 50, 50));
            sprites.put("Boss_XiaoLiA_spin4", sheet.getSubimage(50, 50, 50, 50));
            sprites.put("Boss_XiaoLiA_thrust1", sheet.getSubimage(200, 50, 50, 50));
            sprites.put("Boss_XiaoLiA_thrust2", sheet.getSubimage(100, 50, 50, 50));
            sprites.put("Boss_XiaoLiA_thrust3", sheet.getSubimage(150, 50, 50, 50));
            sprites.put("Boss_XiaoLiA_thrust4", sheet.getSubimage(100, 0, 50, 50));
            //boss XLB
            sprites.put("Boss_XiaoLiB_idle1", sheet.getSubimage(350, 0, 50, 50));
            sprites.put("Boss_XiaoLiB_idle2", sheet.getSubimage(650, 100, 50, 50));
            sprites.put("Boss_XiaoLiB_idle3", sheet.getSubimage(550, 100, 50, 50));
            sprites.put("Boss_XiaoLiB_idle4", sheet.getSubimage(0, 150, 50, 50));
            sprites.put("Boss_XiaoLiB_spin1", sheet.getSubimage(600, 100, 50, 50));
            sprites.put("Boss_XiaoLiB_spin2", sheet.getSubimage(100, 150, 50, 50));
            sprites.put("Boss_XiaoLiB_spin3", sheet.getSubimage(150, 150, 50, 50));
            sprites.put("Boss_XiaoLiB_spin4", sheet.getSubimage(50, 100, 50, 50));
            sprites.put("Boss_XiaoLiB_thrust1", sheet.getSubimage(150, 100, 50, 50));
            sprites.put("Boss_XiaoLiB_thrust2", sheet.getSubimage(250, 100, 50, 50));
            sprites.put("Boss_XiaoLiB_thrust3", sheet.getSubimage(300, 100, 50, 50));
            sprites.put("Boss_XiaoLiB_thrust4", sheet.getSubimage(200, 100, 50, 50));
            //projectiles
            sprites.put("PE_sword", sheet.getSubimage(200, 230, 70, 100));

            sprites.put("PE_oval_grey", sheet.getSubimage(735, 150, 50, 80));
            sprites.put("PE_oval_red", sheet.getSubimage(785, 150, 50, 80));
            sprites.put("PE_oval_white", sheet.getSubimage(885, 150, 50, 80));
            sprites.put("PE_oval_yellow", sheet.getSubimage(835, 150, 50, 80));
            sprites.put("PE_oval_blue", sheet.getSubimage(50, 230, 50, 80));
            sprites.put("PE_oval_lightBlue", sheet.getSubimage(0, 230, 50, 80));
            sprites.put("PE_oval_orange", sheet.getSubimage(635, 150, 50, 80));

            sprites.put("PE_small_grey", sheet.getSubimage(800, 50, 50, 50));
            sprites.put("PE_small_red", sheet.getSubimage(900, 50, 50, 50));
            sprites.put("PE_small_white", sheet.getSubimage(0, 100, 50, 50));
            sprites.put("PE_small_yellow", sheet.getSubimage(950, 50, 50, 50));
            sprites.put("PE_small_blue", sheet.getSubimage(700, 100, 50, 50));
            sprites.put("PE_small_lightBlue", sheet.getSubimage(900, 100, 50, 50));

            sprites.put("PE_medium_grey", sheet.getSubimage(770, 230, 100, 100));
            sprites.put("PE_medium_red", sheet.getSubimage(100, 230, 100, 100));
            sprites.put("PE_medium_white", sheet.getSubimage(270, 230, 100, 100));
            sprites.put("PE_medium_yellow", sheet.getSubimage(470, 230, 100, 100));
            sprites.put("PE_medium_blue", sheet.getSubimage(570, 230, 100, 100));
            sprites.put("PE_medium_lightBlue", sheet.getSubimage(370, 230, 100, 100));
        }catch(Exception e){
            System.out.println("Error loadStage4");
        }
    }
    private void loadStage5(){
        try{
            final BufferedImage sheet = ImageIO.read(new File("./res/Sheets/Stage5.png"));
            //pickups
            sprites.put("Pickup_bomb", sheet.getSubimage(200, 0, 50, 50));
            sprites.put("Pickup_life", sheet.getSubimage(100, 0, 50, 50));
            sprites.put("Pickup_power", sheet.getSubimage(0, 0, 50, 50));
            //enemies
            sprites.put("E_wisp1", sheet.getSubimage(0, 150, 150, 50));
            sprites.put("E_wisp2", sheet.getSubimage(0, 100, 150, 50));
            sprites.put("E_wisp3", sheet.getSubimage(50, 50, 150, 50));
            sprites.put("E_wisp4", sheet.getSubimage(50, 200, 150, 50));
            sprites.put("E_machine_bullet1", sheet.getSubimage(0, 310, 75, 75));
            sprites.put("E_machine_bullet2", sheet.getSubimage(75, 310, 75, 75));
            sprites.put("E_machine_laser", sheet.getSubimage(0, 250, 55, 55));
            sprites.put("E_machine_shield", sheet.getSubimage(55, 250, 120, 60));
            sprites.put("E_machine_spray", sheet.getSubimage(175, 250, 60, 60));
            //boss Pax
            sprites.put("Boss_Pax_idle1", sheet.getSubimage(150, 150, 50, 50));
            sprites.put("Boss_Pax_idle2", sheet.getSubimage(150, 0, 50, 50));
            //enemy projectiles
            sprites.put("PE_oval_grey", sheet.getSubimage(0, 390, 50, 80));
            sprites.put("PE_oval_red", sheet.getSubimage(200, 310, 50, 80));
            sprites.put("PE_oval_white", sheet.getSubimage(150, 390, 50, 80));
            sprites.put("PE_oval_yellow", sheet.getSubimage(200, 390, 50, 80));
            sprites.put("PE_oval_blue", sheet.getSubimage(50, 390, 50, 80));
            sprites.put("PE_oval_lightBlue", sheet.getSubimage(150, 310, 50, 80));

            sprites.put("PE_small_grey", sheet.getSubimage(200, 150, 50, 50));
            sprites.put("PE_small_red", sheet.getSubimage(0, 200, 50, 50));
            sprites.put("PE_small_white", sheet.getSubimage(200, 200, 50, 50));
            sprites.put("PE_small_yellow", sheet.getSubimage(50, 0, 50, 50));
            sprites.put("PE_small_blue", sheet.getSubimage(0, 50, 50, 50));
            sprites.put("PE_small_lightBlue", sheet.getSubimage(200, 50, 50, 50));

            sprites.put("PE_medium_grey", sheet.getSubimage(0, 770, 100, 100));
            sprites.put("PE_medium_red", sheet.getSubimage(100, 470, 100, 100));
            sprites.put("PE_medium_white", sheet.getSubimage(0, 470, 100, 100));
            sprites.put("PE_medium_yellow", sheet.getSubimage(0, 570, 100, 100));
            sprites.put("PE_medium_blue", sheet.getSubimage(100, 670, 100, 100));
            sprites.put("PE_medium_lightBlue", sheet.getSubimage(0, 670, 100, 100));

        }catch(Exception e){
            System.out.println("Error loadStage5");
        }
    }
    private void loadStage6(){
        try{
            final BufferedImage sheet = ImageIO.read(new File("./res/Sheets/Stage6.png"));
            //pickups
            sprites.put("Pickup_bomb", sheet.getSubimage(450, 100, 50, 50));
            sprites.put("Pickup_life", sheet.getSubimage(50, 150, 50, 50));
            sprites.put("Pickup_power", sheet.getSubimage(400, 100, 50, 50));
            //enemies
            sprites.put("E_wisp1", sheet.getSubimage(0, 460, 150, 50));
            sprites.put("E_wisp2", sheet.getSubimage(300, 460, 150, 50));
            sprites.put("E_wisp3", sheet.getSubimage(150, 460, 150, 50));
            sprites.put("E_wisp4", sheet.getSubimage(340, 360, 150, 50));
            sprites.put("E_machine_bullet1", sheet.getSubimage(75, 360, 75, 75));
            sprites.put("E_machine_bullet2", sheet.getSubimage(0, 360, 75, 75));
            sprites.put("E_machine_laser", sheet.getSubimage(0, 200, 55, 55));
            sprites.put("E_machine_shield", sheet.getSubimage(220, 360, 120, 60));
            sprites.put("E_machine_spray", sheet.getSubimage(55, 200, 60, 60));
            sprites.put("E_spider1", sheet.getSubimage(135, 0, 45, 45));
            sprites.put("E_spider2", sheet.getSubimage(45, 0, 45, 45));
            sprites.put("E_spider3", sheet.getSubimage(90, 0, 45, 45));
            sprites.put("E_spider4", sheet.getSubimage(0, 0, 45, 45));
            sprites.put("E_spider5", sheet.getSubimage(180, 0, 45, 45));
            //boss Lilith1
            sprites.put("Boss_Lilith1_idle1", sheet.getSubimage(250, 100, 50, 50));
            sprites.put("Boss_Lilith1_idle2", sheet.getSubimage(0, 100, 50, 50));
            sprites.put("Boss_Lilith1_idle3", sheet.getSubimage(100, 100, 50, 50));
            sprites.put("Boss_Lilith1_idle4", sheet.getSubimage(200, 100, 50, 50));
            //boss Lilith2
            sprites.put("Boss_Lilith2_idle1", sheet.getSubimage(150, 100, 50, 50));
            sprites.put("Boss_Lilith2_idle2", sheet.getSubimage(100, 150, 50, 50));
            sprites.put("Boss_Lilith2_idle3", sheet.getSubimage(200, 150, 50, 50));
            sprites.put("Boss_Lilith2_idle4", sheet.getSubimage(350, 150, 50, 50));
            sprites.put("Boss_Lilith2_left1", sheet.getSubimage(150, 150, 50, 50));
            sprites.put("Boss_Lilith2_left2", sheet.getSubimage(250, 150, 50, 50));
            sprites.put("Boss_Lilith2_left3", sheet.getSubimage(300, 150, 50, 50));
            sprites.put("Boss_Lilith2_left4", sheet.getSubimage(450, 150, 50, 50));
            sprites.put("Boss_Lilith2_right1", sheet.getSubimage(400, 150, 50, 50));
            sprites.put("Boss_Lilith2_right2", sheet.getSubimage(50, 100, 50, 50));
            sprites.put("Boss_Lilith2_right3", sheet.getSubimage(300, 50, 50, 50));
            sprites.put("Boss_Lilith2_right4", sheet.getSubimage(325, 0, 50, 50));
            //enemy projectiles
            sprites.put("PE_blade", sheet.getSubimage(150, 360, 70, 100));
            sprites.put("PE_fragment1", sheet.getSubimage(400, 710, 100, 100));
            sprites.put("PE_fragment2", sheet.getSubimage(200, 610, 100, 100));
            sprites.put("PE_fragment3", sheet.getSubimage(0, 610, 100, 100));
            sprites.put("PE_fragment4", sheet.getSubimage(300, 810, 100, 100));
            sprites.put("PE_fragment5", sheet.getSubimage(100, 910, 100, 100));
            sprites.put("PE_fragment6", sheet.getSubimage(0, 910, 100, 100));
            sprites.put("PE_fragment7", sheet.getSubimage(200, 910, 100, 100));
            sprites.put("PE_fragment8", sheet.getSubimage(400, 510, 100, 100));

            sprites.put("PE_oval_blue", sheet.getSubimage(300, 280, 50, 80));
            sprites.put("PE_oval_grey", sheet.getSubimage(100, 280, 50, 80));
            sprites.put("PE_oval_orange", sheet.getSubimage(200, 280, 50, 80));
            sprites.put("PE_oval_red", sheet.getSubimage(0, 280, 50, 80));
            sprites.put("PE_oval_white", sheet.getSubimage(350, 280, 50, 80));
            sprites.put("PE_oval_yellow", sheet.getSubimage(400, 280, 50, 80));
            sprites.put("PE_oval_violet", sheet.getSubimage(365, 200, 50, 80));
            sprites.put("PE_oval_lightBlue", sheet.getSubimage(50, 280, 50, 80));

            sprites.put("PE_small_blue", sheet.getSubimage(200, 50, 50, 50));
            sprites.put("PE_small_grey", sheet.getSubimage(300, 100, 50, 50));
            sprites.put("PE_small_orange", sheet.getSubimage(350, 50, 50, 50));
            sprites.put("PE_small_red", sheet.getSubimage(50, 50, 50, 50));
            sprites.put("PE_small_white", sheet.getSubimage(400, 50, 50, 50));
            sprites.put("PE_small_yellow", sheet.getSubimage(250, 50, 50, 50));
            sprites.put("PE_small_violet", sheet.getSubimage(100, 50, 50, 50));
            sprites.put("PE_small_lightBlue", sheet.getSubimage(350, 100, 50, 50));

            sprites.put("PE_medium_blue", sheet.getSubimage(100, 710, 100, 100));
            sprites.put("PE_medium_grey", sheet.getSubimage(300, 710, 100, 100));
            sprites.put("PE_medium_orange", sheet.getSubimage(200, 710, 100, 100));
            sprites.put("PE_medium_red", sheet.getSubimage(0, 710, 100, 100));
            sprites.put("PE_medium_white", sheet.getSubimage(400, 610, 100, 100));
            sprites.put("PE_medium_yellow", sheet.getSubimage(100, 610, 100, 100));
            sprites.put("PE_medium_violet", sheet.getSubimage(0, 510, 100, 100));
            sprites.put("PE_medium_lightBlue", sheet.getSubimage(200, 510, 100, 100));

            sprites.put("PE_large_blue", sheet.getSubimage(200, 1210, 200, 200));
            sprites.put("PE_large_grey", sheet.getSubimage(0, 1010, 200, 200));
            sprites.put("PE_large_orange", sheet.getSubimage(200, 1010, 200, 200));
            sprites.put("PE_large_red", sheet.getSubimage(200, 1410, 200, 200));
            sprites.put("PE_large_white", sheet.getSubimage(0, 1210, 200, 200));
            sprites.put("PE_large_yellow", sheet.getSubimage(0, 1410, 200, 200));
        }catch(Exception e){
            System.out.println("Error loadStage6");
        }
    }
    private void loadStageEX(){
        try{
            final BufferedImage sheet = ImageIO.read(new File("./res/Sheets/StageEX.png"));
            //pickups
            sprites.put("Pickup_bomb", sheet.getSubimage(920, 0, 50, 50));
            sprites.put("Pickup_life", sheet.getSubimage(120, 0, 50, 50));
            sprites.put("Pickup_power", sheet.getSubimage(220, 0, 50, 50));
            //enemies
            sprites.put("E_wisp1", sheet.getSubimage(570, 0, 150, 50));
            sprites.put("E_wisp2", sheet.getSubimage(0, 50, 150, 50));
            sprites.put("E_wisp3", sheet.getSubimage(370, 0, 150, 50));
            sprites.put("E_wisp4", sheet.getSubimage(720, 0, 150, 50));
            sprites.put("E_ember", sheet.getSubimage(300, 50, 50, 50));
            //boss Zephres
            sprites.put("Boss_Zephres_idle1", sheet.getSubimage(90, 0, 30, 30));
            sprites.put("Boss_Zephres_idle2", sheet.getSubimage(0, 0, 30, 30));
            sprites.put("Boss_Zephres_idle3", sheet.getSubimage(30, 0, 30, 30));
            sprites.put("Boss_Zephres_idle4", sheet.getSubimage(60, 0, 30, 30));
            //enemy projectiles
            sprites.put("PE_flames1", sheet.getSubimage(200, 230, 100, 120));
            sprites.put("PE_flames2", sheet.getSubimage(300, 230, 100, 120));
            sprites.put("PE_flames3", sheet.getSubimage(700, 230, 100, 120));
            sprites.put("PE_flames4", sheet.getSubimage(600, 230, 100, 120));
            sprites.put("PE_burningSpace1", sheet.getSubimage(600, 630, 200, 200));
            sprites.put("PE_burningSpace2", sheet.getSubimage(200, 630, 200, 200));
            sprites.put("PE_burningSpace3", sheet.getSubimage(400, 630, 200, 200));
            sprites.put("PE_burningSpace4", sheet.getSubimage(800, 430, 200, 200));
            sprites.put("PE_burningSpace5", sheet.getSubimage(800, 230, 200, 200));
            sprites.put("PE_burningSpace6", sheet.getSubimage(0, 430, 200, 200));
            sprites.put("PE_burningSpace7", sheet.getSubimage(200, 430, 200, 200));

            sprites.put("PE_oval_orange", sheet.getSubimage(450, 50, 50, 80));
            sprites.put("PE_oval_red", sheet.getSubimage(550, 50, 50, 80));
            sprites.put("PE_oval_white", sheet.getSubimage(500, 50, 50, 80));
            sprites.put("PE_oval_yellow", sheet.getSubimage(600, 50, 50, 80));
            sprites.put("PE_oval_blue", sheet.getSubimage(900, 50, 50, 80));
            sprites.put("PE_oval_lightBlue", sheet.getSubimage(850, 50, 50, 80));

            sprites.put("PE_small_orange", sheet.getSubimage(150, 50, 50, 50));
            sprites.put("PE_small_red", sheet.getSubimage(200, 50, 50, 50));
            sprites.put("PE_small_white", sheet.getSubimage(400, 50, 50, 50));
            sprites.put("PE_small_yellow", sheet.getSubimage(350, 50, 50, 50));
            sprites.put("PE_small_blue", sheet.getSubimage(520, 0, 50, 50));
            sprites.put("PE_small_lightBlue", sheet.getSubimage(320, 0, 50, 50));

            sprites.put("PE_medium_orange", sheet.getSubimage(300, 130, 100, 100));
            sprites.put("PE_medium_red", sheet.getSubimage(900, 130, 100, 100));
            sprites.put("PE_medium_white", sheet.getSubimage(500, 130, 100, 100));
            sprites.put("PE_medium_yellow", sheet.getSubimage(400, 130, 100, 100));
            sprites.put("PE_medium_blue", sheet.getSubimage(600, 130, 100, 100));
            sprites.put("PE_medium_lightBlue", sheet.getSubimage(700, 130, 100, 100));

            sprites.put("PE_large_orange", sheet.getSubimage(600, 430, 200, 200));
            sprites.put("PE_large_red", sheet.getSubimage(400, 430, 200, 200));
            sprites.put("PE_large_yellow", sheet.getSubimage(0, 630, 200, 200));
        }catch(Exception e){
            System.out.println("Error loadStageEX");
        }
    }
    //paintComponent
//    @Override
//    public void paintComponent(Graphics g){
//        super.paintComponent(g);
//        g.drawImage(buffImg, 0, 0, (int) Driver_Game.getWidth(), (int) Driver_Game.getHeight(), this);
//    }

//    @Override
//    public void paint(Graphics g){
//
//    }//    @Override
////    public void update(Graphics g){
////        paint(g);
////    }
}