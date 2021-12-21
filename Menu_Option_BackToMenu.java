public class Menu_Option_BackToMenu extends Menu_Option {

    public Menu_Option_BackToMenu(){
        super("Quit To Menu");
    }

    @Override
    public void selectAction(){
        Driver.clear(2);
        Driver_Music.playTrack("1_Solitary_Dodecahedron", true);
    }
}