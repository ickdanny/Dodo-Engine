public class Instruction_DriverEndLevel extends Instruction {

    public Instruction_DriverEndLevel(Entity owner) {
        super(owner);
    }

    public Instruction_DriverEndLevel(Entity owner, Behavior next) {
        super(owner, next);
    }

    //this method ends all behavior (not meaning instructions or routines) of the owner.
    @Override
    public boolean doAction() {
        Driver_Game.getCurrentGameDriver().victory();
//        return false;
        return true;
    }
}
