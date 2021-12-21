public class Routine_XL_Default extends Routine_Boss_Attack {
    private Behavior topRoot;
    private Behavior top;
    private double CDticks = 180;

//    public Routine_XL_Default(Enemy_Boss_XiaoLi owner){
//        super(owner);
//    }
//    public Routine_XL_Default(Enemy_Boss_XiaoLi owner, Behavior next){
//        super(owner, next);
//    }
//    public Routine_XL_Default(Enemy_Boss_XiaoLi owner, Behavior next, Routine instructions){
//        super(owner, next, instructions);
//    }
//    public Routine_XL_Default(Enemy_Boss_XiaoLi owner, Behavior next, Routine instructions, Routine top){
//        super(owner, next, instructions);
//        this.top = top;
//    }
    public Routine_XL_Default(Enemy_Boss_XiaoLi owner, Behavior next, Routine instructions, Routine top, double initHP){
        super(owner, next, instructions, initHP);
        this.top = top;
        this.topRoot = top;
    }

    @Override
    public boolean doAction(){
//        System.out.println(top);
//        System.out.println(CDticks);
        if(Player.getThePlayer().getY() >= 80 || CDticks < 180){
//            if(top instanceof Routine) {
//                ((Routine)top).gotoRoot();
//            }
            if(CDticks == 1000){
                CDticks = 0;
            }
            if(CDticks < 180){
                CDticks ++;
            }
            topGotoRoot();
            top = topRoot;
            while(instructions.doAction()) {
                if(instructions.getNext() != null) {
                    if(instructions instanceof Routine){
                        ((Routine)instructions).gotoRoot();
                    }
                    instructions = instructions.getNext();
                }
                //the routine should loop around, this should never occur
                else{
//                    return true;
                    instructions = null;
                    return false;
                }
            }
            return false;
        }
        //top
        else{
            CDticks = 1000;
            if(instructions instanceof Routine){
                ((Routine)instructions).gotoRoot();
//                instructions = instructions.getNext();
            }
            //case of when the instruction is literally to wait for path over lmao fuck me
            else if(instructions instanceof Instruction_WaitPathOver){
                instructions = instructions.getNext();
            }
            else if(instructions instanceof Instruction_Timer){
                ((Instruction_Timer)instructions).reset();
                instructions = instructions.getNext();
            }
            while(top.doAction()){
//                System.out.println("switch top");
                if(top.getNext() != null){
                    if(top instanceof Routine){
                        ((Routine)top).gotoRoot();
                    }
                    top = top.getNext();
                }
                else{
                    if(top instanceof Routine){
                        ((Routine)top).gotoRoot();
                    }
                    top = topRoot;
                }
            }
            return false;
        }
    }

    @Override
    public void setOwner(Entity owner){
        super.setOwner(owner);
//        ((Instruction)top).setOwner(owner);

        Behavior toSet = top;
        int maxLoops = 100;
        int loops = 0;
        while(toSet != null && loops < maxLoops){
            try {
                if(toSet instanceof BSplitter_LeftOrRight){
                    ((BSplitter_LeftOrRight)toSet).setOwner(owner);
                }
                ((Instruction)toSet).setOwner(owner);
            }catch(Exception e){
                //do nothing

            }
            loops++;
            toSet = toSet.getNext();
        }
    }

    public void topGotoRoot(){
        Behavior toSet = top;
        int maxLoops = 100;
        int loops = 0;
        while(toSet != null && loops < maxLoops){
            try {
                ((Routine)toSet).gotoRoot();
            }catch(Exception e){
                //do nothing

            }
            loops++;
            toSet = toSet.getNext();
        }
    }


    public void setTop(Routine top){
        this.top = top;
    }
}
