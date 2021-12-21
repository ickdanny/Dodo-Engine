import java.util.ArrayList;

public class Menu_Option_Difficulty extends Menu_Option {

    private int difficulty;
    private boolean practice;
    public Menu_Option_Difficulty(int diff, boolean practice){
        this.name = new ArrayList<>();
        difficulty = diff;
        this.practice = practice;
        switch(difficulty){
            case 1:
                this.name.add("Easiest");
                break;
            case 2:
                this.name.add("Easy");
                break;
            case 3:
                this.name.add("Normal");
                break;
            case 4:
                this.name.add("Hard");
                break;
            case 5:
                this.name.add("Harder");
                break;
            case 6:
                this.name.add("Lunatic");
                break;
            case 7:
                this.name.add("Challenge");
                break;
            case 8:
                this.name.add("Untested");
                break;
            case 9:
                this.name.add("Extra");
                break;
            default:
                this.name.add("Difficulty Error");
        }
    }

    @Override
    public void selectAction(){
        if(practice){
            //goto level select
            ArrayList<Menu_Option> options = new ArrayList<>();
            options.add(new Menu_Option_LevelSelect(1, difficulty));
            options.add(new Menu_Option_LevelSelect(2, difficulty));
            options.add(new Menu_Option_LevelSelect(3, difficulty));
            options.add(new Menu_Option_LevelSelect(4, difficulty));
            options.add(new Menu_Option_LevelSelect(5, difficulty));
            options.add(new Menu_Option_LevelSelect(6, difficulty));
//            options.add(new Menu_Option_LevelSelect(7, difficulty));
            options.add(new Menu_Option_Back());
            Menu toAdd = new Menu(options, new Vector(200 * Driver.getUpdatableSize(), 0), Driver.getWidth(), Driver.getHeight());
            Driver.add(toAdd);
        }
        else{
            //goto shot select
            switch(difficulty){
                case 1:
                case 2:
                case 3:
                case 4:
                    ArrayList<Menu_Option> options = new ArrayList<>();
                    options.add(new Menu_Option_ShotSelect(1, difficulty, false, "AbaddonA"));
                    options.add(new Menu_Option_ShotSelect(1, difficulty, false, "AbaddonB"));
                    options.add(new Menu_Option_Back());
                    Menu toAdd = new Menu(options, new Vector(200 * Driver.getUpdatableSize(), 0), Driver.getWidth(), Driver.getHeight());
                    Driver.add(toAdd);
                    break;
                case 5:
                case 6:
                case 7:
                case 8:
                    options = new ArrayList<>();
                    options.add(new Menu_Option_ShotSelect(1, difficulty, false, "AzraelA"));
                    options.add(new Menu_Option_ShotSelect(1, difficulty, false, "AzraelB"));
                    options.add(new Menu_Option_Back());
                    toAdd = new Menu(options, new Vector(200 * Driver.getUpdatableSize(), 0), Driver.getWidth(), Driver.getHeight());
                    Driver.add(toAdd);
                    break;
                case 9:
                    options = new ArrayList<>();
                    options.add(new Menu_Option_ShotSelect(7, difficulty, false, "MiraA"));
                    options.add(new Menu_Option_ShotSelect(7, difficulty, false, "MiraB"));
                    options.add(new Menu_Option_Back());
                    toAdd = new Menu(options, new Vector(200 * Driver.getUpdatableSize(), 0), Driver.getWidth(), Driver.getHeight());
                    Driver.add(toAdd);
                    break;
            }
        }
    }
}
