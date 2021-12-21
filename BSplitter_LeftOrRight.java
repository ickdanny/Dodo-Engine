public class BSplitter_LeftOrRight implements Behavior {
    protected Behavior left;
    protected Behavior right;
    protected Entity owner;
    public BSplitter_LeftOrRight(Entity owner){
        this.owner = owner;
    }
    public BSplitter_LeftOrRight(Entity owner, Behavior left, Behavior right){
        this.owner = owner;
        this.left = left;
        this.right = right;
    }

    public void setLeft(Behavior left){
        this.left = left;
    }
    public void setRight(Behavior right){
        this.right = right;
    }
    public void setOwner(Entity owner){
        this.owner = owner;
//        try {
//            ((Instruction)left).setOwner(owner);
//            ((Instruction)right).setOwner(owner);
//        }
//        catch(Exception e){
//
//        }

        Behavior toSet = left;
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

        toSet = right;
        loops = 0;
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

    public Behavior getNext(){
        double ownerX = owner.getX();
        double playerX = Player.getThePlayer().getX();
        //go right
        if(ownerX < playerX){
            return right;
        }
        else{
            return left;
        }
    }





    //never use this probably
    public void setNext(Behavior next){
        left = next;
        right = next;
    }
    public boolean doAction(){
        return true;
    }

    @Override
    public Behavior getRootBehavior(){
        return this;
    }
}
