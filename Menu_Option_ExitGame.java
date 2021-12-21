public class Menu_Option_ExitGame extends Menu_Option {

    public Menu_Option_ExitGame(){
        super("Exit Game");
    }

    @Override
    public void selectAction(){
        System.exit(0);
    }
}
