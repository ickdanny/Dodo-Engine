public class EP_Orb_Mira_WOT extends EP_Orb_Medium {

    public EP_Orb_Mira_WOT(Vector initPos, Vector polar){
        super(initPos, polar, "lightBlue");
        this.destroyOutsideBounds = false;
    }

    @Override
    protected void move(){
        if(!followsPath){
            super.move();
            return;
        }
        if(path instanceof Path_Orbit){
            double currentDistance = ((Path_Orbit)path).getAVelocity().getB();
            if(currentDistance > 100) {
                double newDistance = currentDistance - ((double)300/Driver_Game.getUPS());
                if(newDistance > 100) {
                    ((Path_Orbit) path).setDistance(newDistance);
                }
                else{
                    ((Path_Orbit) path).setDistance(100);
                }
            }
            else if(currentDistance < 100){
                double newDistance = currentDistance + ((double)300/Driver_Game.getUPS());
                if(newDistance < 100) {
                    ((Path_Orbit) path).setDistance(newDistance);
                }
                else{
                    ((Path_Orbit) path).setDistance(100);
                }
            }
        }
        super.move();
    }

    public void changeState(String newState){
        if(Vector.getDistance(this, Enemy_Boss.getTheBoss()) > 1500){
            this.exists = false;
            return;
        }

        switch(newState){
            case "orbit":
                double initDistance = Vector.getDistance(this, Enemy_Boss.getTheBoss());
                newPath(new Path_Orbit(Enemy_Boss.getTheBoss(), new Vector(30, initDistance), Vector.getAngle(Enemy_Boss.getTheBoss(), this)));
                this.colorCode = "white";
                initSprite();
                break;
            case "free":
                this.followsPath = false;
                this.polar.setB(500);
                this.colorCode = "blue";
                initSprite();
                break;
        }
    }
}
