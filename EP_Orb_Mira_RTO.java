//import java.util.ArrayList;

public class EP_Orb_Mira_RTO extends EP_Oval {

    public EP_Orb_Mira_RTO(Vector initPos, Vector polar){
        super(initPos, polar, "blue");
    }

    @Override
    protected void updatePath(){
        if(followsPath) {
            if (path.isOver()) {
                followsPath = false;
                this.exists = false;
                return;
            }
            //do i need a velocity conversion here
            //answer is no
            polar = path.getNextPolar(this);
        }
    }

    @Override
    protected void baseMovementBehavior(){
        super.baseMovementBehavior();
        if(!followsPath){
            if (a < 0){
                a = 0;
                polar.setB(0);
                velocity = new Vector();
            }
            if (b < 0) {
                b = 0;
                polar.setB(0);
                velocity = new Vector();
            }
            if(a > Driver_Game.getWidth()){
                a = Driver_Game.getWidth();
                polar.setB(0);
                velocity = new Vector();
            }
            if(b > Driver_Game.getHeight()){
                b = Driver_Game.getHeight();
                polar.setB(0);
                velocity = new Vector();
            }
        }
    }

    public void startPath(){
        Vector point = new Vector(Enemy_Boss.getTheBoss().getA(), Enemy_Boss.getTheBoss().getB());
        newPath(new Path_Polygon(point, false, 600));
        colorCode = "lightBlue";
        initSprite();
    }
}