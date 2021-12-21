public class Routine_Boss_Attack extends Routine {

    public Routine_Boss_Attack(Enemy_Boss owner, Behavior next, Behavior instructions, double initHP){
        super(owner, next, instructions);
        Behavior root = this.instructions;
        Instruction_SetHP setHP = new Instruction_SetHP(owner, root, initHP);
        Instruction_Boss_SetSpellName setSpellName = new Instruction_Boss_SetSpellName(owner, setHP, "");
        this.instructions = setSpellName;
//        setOwner(owner);
    }

    //always returns false - the behavior will go to the next routine when boss dies
    @Override
    public boolean doAction(){
        if(instructions == null){
            return false;
        }
        //if or while?
        while(instructions.doAction()) {
//            System.out.println(instructions);
            if(instructions.getNext() != null) {
                instructions = instructions.getNext();
            }
            else{
                instructions = null;
                return false;
            }
        }
        return false;
    }
}
