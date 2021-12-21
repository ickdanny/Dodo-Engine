//routine class implements Behavior
public class Routine extends Instruction{
    private boolean repeats = false;
    //behavior next
    //behavior subRoutine //like a list of mini behaviors
    protected Behavior root;
    protected Behavior instructions;

    public Routine(Entity owner){
        super(owner);
    }
    public Routine(Entity owner, Behavior next){
        super(owner, next);
    }
    public Routine(Entity owner, Behavior next, Behavior instructions){
        super(owner, next);
        this.instructions = instructions;
        this.root = instructions;
    }
    //doAction
    //normally does something like calling the subRoutine. If subroutine is over go to next subroutine.
    //complex routines should override the doAction method and add in their own conditions, i.e. switching when low on hp
    @Override
    public boolean doAction(){
        //if or while?
        while(instructions.doAction()) {
            if(instructions.getNext() != null) {
                instructions = instructions.getNext();
            }
            else{
                if(repeats) {
                    gotoRoot();
                }
                return true;
            }
        }
        return false;
    }
    //setNext (already in instruction)

    //setSubRoutine
    public void setInstructions(Behavior instructions){
        this.instructions = instructions;
        this.root = instructions;
    }

    @Override
    public void setOwner(Entity owner){

        if(owner == null){
            return;
        }

        super.setOwner(owner);
        Behavior toSet = instructions;
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

    public void gotoRoot(){
        this.instructions = root;
    }

    public void instructionsGotoRoot(){
        Behavior toSet = instructions;
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

    public void resetTimers(){
        Behavior b = instructions;
        Behavior stop = b;
        int loops = 0;
        while(b != null){
            if(loops > 0 && b == stop){
                break;
            }
            if(b instanceof Instruction_Timer){
                ((Instruction_Timer)b).reset();
            }
            b = b.getNext();
            loops++;
        }
    }

    @Override
    public Behavior getRootBehavior(){
        return instructions.getRootBehavior();
    }

    public void setRepeats(boolean b){
        repeats = b;
    }

}