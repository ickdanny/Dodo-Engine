import java.util.ArrayList;

public class Menu_Option_Options extends Menu_Option {

    public Menu_Option_Options(){
        super("Options");
    }

    @Override
    public void selectAction(){
        //stuff goes here
        //System.out.println("options selected - nothing here yet");
        ArrayList<Menu_Option> options = new ArrayList<>();
        //this creates an extra option
        //NEED TO REMOVE BEFORE FINAL
        options.add(new Menu_Option_Back());
        Driver.add(new Menu(options, new Vector(200 * Driver.getUpdatableSize(), 0), Driver.getWidth(), Driver.getHeight()));
    }
}