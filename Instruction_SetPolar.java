public class Instruction_SetPolar extends Instruction {
    Vector polar;
    public Instruction_SetPolar(Entity owner, Vector polar){
        super(owner);
        this.polar = polar;
    }

    public Instruction_SetPolar(Entity owner, Behavior next, Vector polar){
        super(owner, next);
        this.polar = polar;
    }

    @Override
    public boolean doAction(){
//        System.out.println(owner + " " + polar);
        owner.setPolarVector(polar);
        return true;
    }
}