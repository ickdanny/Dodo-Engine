public class EP_Orb_Claire_Default extends EP_Orb_Medium {

    private boolean expanding = false, constricting = false;
    private String deathCode;
    public EP_Orb_Claire_Default(Vector initPos, Vector polar, String deathCode){
        super(initPos, polar, "blue");
        this.deathCode = deathCode;
    }

    @Override
    protected void baseMovementBehavior(){

        if(Vector.getDistance(this, Player.getThePlayer()) <= 60 && !expanding && !constricting && exists){
            expanding = true;
            polar = new Vector();
        }

        if(expanding){
            if(size < 30){
                size += .25;
//                System.out.println(size);
            }
            else{
                expanding = false;
                constricting = true;
                changeColor("red");
            }
        }
        if(constricting){
            if(size > 1){
                size -= 1;
            }
            else if(size > 0){
                size -= 1;
                deathAction();
            }
            else{
                constricting = false;
                this.exists = false;
            }
        }
        super.baseMovementBehavior();
    }

    private void deathAction(){
        if(deathCode.equals("wiggleShot")){
            for(int i = 0; i < Driver_GameLogic.getCurrentLogicDriver().getDifficulty(); i++){
                double angle = Math.random() * 360;
                double speed = 300 + (Math.random() * 200);
                toSpawn.add(new EP_Orb_Wiggle(this, new Vector(angle, speed)));
            }
        }
        else if(deathCode.equals("airStrike")){
            ((Enemy_Boss_Claire)Enemy_Boss.getTheBoss()).airStrike(this);
        }
        else if(deathCode.equals("sideStrike")) {
            for (int i = 0; i < (Driver_GameLogic.getCurrentLogicDriver().getDifficulty())/2; i++) {
                Vector initPos;
                //horizontal
                if (Math.random() > .5) {
                    double x = Math.random() * 850;
                    //top
                    if (Math.random() > .5) {
                        initPos = new Vector(x, 0);
                    }
                    //bottom
                    else {
                        initPos = new Vector(x, 1000);
                    }
                }
                //vertical
                else {
                    double y = Math.random() * 1000;
                    //left
                    if (Math.random() > .5) {
                        initPos = new Vector(0, y);
                    }
                    //right
                    else {
                        initPos = new Vector(850, y);
                    }
                }
                double angle = Vector.getAngle(initPos, this);
                toSpawn.add(new EP_Orb_Phasing(initPos, new Vector(angle, 200)));
            }
        }
        else if(deathCode.equals("sideShot")){
            for(int i = 0; i < Driver_GameLogic.getCurrentLogicDriver().getDifficulty()/2; i++) {
                double a = Math.random() * 45;
                double angle = 90 - ((double) 45) / 2 + a;
                if (Math.random() < .5) {
                    angle = 0 - angle;
                }

                double speed = 400 + (10 * Driver_GameLogic.getCurrentLogicDriver().getDifficulty()) + (Math.random() * 300);
                toSpawn.add(new EP_Orb_Claire_Default4_SideShot(this, new Vector(angle, speed)));
            }
        }
    }

    @Override
    protected void updateSprite(){
        this.sprite.setSize((this.size * 1.3)/50);
    }
}
