public class EP_Orb_Zephres_AS_Medium extends EP_Orb_Medium {

    private int killTick;
    public EP_Orb_Zephres_AS_Medium(Vector initPos, Vector initPolar){
        super(initPos, initPolar, "red");
        destroyOutsideBounds = false;
        killTick = 100 + ((int)(Math.random() * 40));
    }

    @Override
    public void update(){
        if(tick < killTick){
            super.update();
        }
        else{
            explode();
            this.exists = false;
        }
    }

    @Override
    protected void baseMovementBehavior(){
        polar.setB(polar.getB() * .99);
        super.baseMovementBehavior();
    }

    private void explode(){
        for(int i = 0; i < 20; i++){
            double angle = Math.random() * 360;
            double speed = 30 + (Math.random() * 300);
            EP_Orb_Small spawn = new EP_Orb_Small(this, new Vector(angle, speed), "orange");
            spawn.destroyOutsideBounds = false;
            toSpawn.add(spawn);
        }
    }
}
