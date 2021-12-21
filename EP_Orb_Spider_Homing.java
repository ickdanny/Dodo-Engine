public class EP_Orb_Spider_Homing extends EP_Orb_Small {
    //for 60 ticks = 180 ish degrees max correction
    private double angleCorrectionSpeed = 3.1;
    public EP_Orb_Spider_Homing(Vector initPos, Vector polar){
        super(initPos, polar, "red");
        //just in case
        destroyOutsideBounds = false;

//        double idealAngle = Vector.getAngle(this, Player.getThePlayer());
//        if(idealAngle < 0){
//            idealAngle += 360;
//        }
//        if (idealAngle != polar.getA()) {
////            clockwise = idealAngle < polar.getA();
////            clockwise = idealAngle > polar.getA();
//              double upDiff, downDiff;
//              if(idealAngle >= polar.getA()){
//                  upDiff = idealAngle - polar.getA();
//                  //wrap around through the entire a angle, then then from 360
//                  downDiff = polar.getA() + (360 - idealAngle);
//              }
//              else{
//                  downDiff = polar.getA() - idealAngle;
//                  upDiff = idealAngle + (360 - polar.getA());
//              }
//              //clockwise = angle goes down
//              clockwise = downDiff < upDiff;
//        }
    }

    @Override
    protected void baseMovementBehavior(){
//        System.out.println(polar.getA());
        if(tick < 60) {
            Player player = Player.getThePlayer();
            if (player != null && player.getExists()) {
                double idealAngle = Vector.getAngle(this, player);
                if (idealAngle < 0) {
                    idealAngle += 360;
                }

                if (idealAngle != polar.getA()) {
                    double upDiff, downDiff;
                    if(idealAngle >= polar.getA()){
                        upDiff = idealAngle - polar.getA();
                        //wrap around through the entire a angle, then then from 360
                        downDiff = polar.getA() + (360 - idealAngle);
                    }
                    else{
                        downDiff = polar.getA() - idealAngle;
                        upDiff = idealAngle + (360 - polar.getA());
                    }
                    //clockwise = angle goes down
                    boolean clockwise = downDiff < upDiff;

                    if (idealAngle != polar.getA()) {
                        if (!clockwise) {
                            polar.addToThis(new Vector(angleCorrectionSpeed, 0));
                            if (idealAngle < polar.getA()) {
                                polar.setA(idealAngle);
                            }
                        } else {
                            polar.addToThis(new Vector(angleCorrectionSpeed * -1, 0));
                            double d = polar.getA();
                            if(d < 0){
                                d += 360;
                            }
                            if (idealAngle > d) {
                                polar.setA(idealAngle);
                            }
                        }
                    }

                }

            }
        }
        if(tick == 60){
            destroyOutsideBounds = true;
        }
        super.baseMovementBehavior();
    }
}
