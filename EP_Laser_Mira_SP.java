import java.awt.*;

//what does SP mean here?
public class EP_Laser_Mira_SP extends EP_Laser {
    public EP_Laser_Mira_SP(Vector initPos, Vector polar){
        super(initPos, polar, 1, Color.white);
        setTrueHitbox(new Hitbox_Line(this, 0, Color.white));
    }

    @Override
    public void update(){
        if(tick >= 300){
            this.exists = false;
        }
        if(size <= 1200){
            int difficulty = Driver_GameLogic.getCurrentLogicDriver().getDifficulty();
            double growthRate = (1.9 + ((double)difficulty/5))/Driver_Game.getUPS();
            size += (size * growthRate);
        }

        move();
        spawn();
        tick++;
        updateSprite();
        hasUpdated = true;
    }
}
