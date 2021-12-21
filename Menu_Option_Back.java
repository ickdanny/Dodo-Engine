public class Menu_Option_Back extends Menu_Option {

    public Menu_Option_Back(){
        super("Back");
    }

    @Override
    public void selectAction(){
        Driver.clear(1);
    }
}
