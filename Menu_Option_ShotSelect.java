import java.util.ArrayList;

public class Menu_Option_ShotSelect extends Menu_Option {

    private int difficulty, level;
    private String playerName;
    private boolean practice;
    public Menu_Option_ShotSelect(int level, int diff, boolean practice, String playerName){
        this.name = new ArrayList<>();
        this.level = level;
        this.difficulty = diff;
        this.playerName = playerName;
        this.practice = practice;
        switch(this.playerName){
            case "AbaddonA":
                name.add("Name: \"Abaddon A\"");
                name.add("Attack: \"Seeking The Destruction Of Life\"");
                name.add("Spell: \"Annihilation: Paradigm Shift\"");
                name.add("Speed: 2");
                name.add("Attack Range: 3");
                name.add("Attack Power: 3");
                name.add("Designated Spell Range: 4");
                name.add("Designated Spell Power: 3");
                break;
            case "AbaddonB":
                name.add("Name: \"Abaddon B\"");
                name.add("Attack: \"Destructive Self-Defense Halo\"");
                name.add("Spell: \"Annihilation: Chaos Field\"");
                name.add("Speed: 2");
                name.add("Attack Range: 4");
                name.add("Attack Power: 2");
                name.add("Designated Spell Range: 5");
                name.add("Designated Spell Power: 1");
                break;
            case "AzraelA":
                name.add("Name: \"Azrael A\"");
                name.add("Attack: \"Deadly Barrage Halo\"");
                name.add("Spell: \"Dark Art: Oblivion Path\"");
                name.add("Speed: 4");
                name.add("Attack Range: 2");
                name.add("Attack Power: 5");
                name.add("Designated Spell Range: 2");
                name.add("Designated Spell Power: 3");
                break;
            case "AzraelB":
                name.add("Name: \"Azrael B\"");
                name.add("Attack: \"Human Penetrating Laser\"");
                name.add("Spell: \"Dark Synthesis: Fragment Of Armageddon\"");
                name.add("Speed: 4");
                name.add("Attack Range: 3");
                name.add("Attack Power: 4");
                name.add("Designated Spell Range: 1");
                name.add("Designated Spell Power: 4");
                break;
            case "MiraA":
                name.add("Name: \"Mira A\"");
                name.add("Attack: \"Air Manipulation\"");
                name.add("Spell: \"Wind Art: Constructive Interference\"");
                name.add("Speed: 5");
                name.add("Attack Range: 3");
                name.add("Attack Power: 5");
                name.add("Designated Spell Range: 5");
                name.add("Designated Spell Power: 4");
                break;
            case "MiraB":
                name.add("Name: \"Mira B\"");
                name.add("Attack: \"Frozen Weaponry\"");
                name.add("Spell: \"Time Privilege: Achronological Apocalypse\"");
                name.add("Speed: 4");
                name.add("Attack Range: 1");
                name.add("Attack Power: 5");
                name.add("Designated Spell Range: 5");
                name.add("Designated Spell Power: 5");
                break;
            default:
                name.add("Name: \"ERROR\"");
                name.add("Attack: \"ERROR\"");
                name.add("Spell: \"ERROR\"");
                name.add("Speed: ERROR");
                name.add("Attack Range: ERROR");
                name.add("Attack Power: ERROR");
                name.add("Designated Spell Range: ERROR");
                name.add("Designated Spell Power: ERROR");
        }
    }

    @Override
    public void selectAction() {
        //start the game
        //if campaign clear menu stacks
        //if practice don't
        if (!practice) {
            Driver.clear(10);
        }

        String stageName = "Stage";
        if (level <= 6)
            stageName = stageName.concat("" + level);
        else
            stageName = stageName.concat("EX");
        if (!practice) {
            //campaigns
            if (difficulty <= 8) {
                Driver.add(new Driver_Game(difficulty, stageName, playerName, practice, 3, 8 - difficulty, 1, 0));
                Driver_Music.playStageTrack(stageName);
            }
            //extra stage
            else {
                Driver.add(new Driver_Game(difficulty, stageName, playerName, practice, 3, 0, 1, 0));
                Driver_Music.playStageTrack(stageName);
            }
        } else {
            switch (level) {
                //MANUALLY INPUT THE INIT POWERS HERE
                case 1:
                    Driver.add(new Driver_Game(difficulty, stageName, playerName, practice, 20, 20, 1, 0));
                    Driver_Music.playStageTrack(stageName);
                    break;
                case 2:
                    Driver.add(new Driver_Game(difficulty, stageName, playerName, practice, 20, 20, 50, 0));
                    Driver_Music.playStageTrack(stageName);
                    break;
                case 3:
                    Driver.add(new Driver_Game(difficulty, stageName, playerName, practice, 20, 20, 100, 0));
                    Driver_Music.playStageTrack(stageName);
                    break;
                case 4:
                    Driver.add(new Driver_Game(difficulty, stageName, playerName, practice, 20, 20, 150, 0));
                    Driver_Music.playStageTrack(stageName);
                    break;
                case 5:
                case 6:
                    Driver.add(new Driver_Game(difficulty, stageName, playerName, practice, 20, 20, 200, 0));
                    Driver_Music.playStageTrack(stageName);
                    break;
                default:
                    Driver.add(new Driver_Game(difficulty, stageName, playerName, practice, 20, 20, 1, 0));
                    Driver_Music.playStageTrack(stageName);
            }
        }
    }
}
