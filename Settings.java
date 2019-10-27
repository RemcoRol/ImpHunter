package com.remco1337.bots.ImpHunter;

import com.runemate.game.api.hybrid.entities.GameObject;
import com.runemate.game.api.hybrid.entities.GroundItem;
import com.runemate.game.api.hybrid.local.Skill;
import com.runemate.game.api.hybrid.local.Skills;
import com.runemate.game.api.hybrid.location.Coordinate;
import com.runemate.game.api.hybrid.region.GameObjects;
import com.runemate.game.api.hybrid.region.GroundItems;
import com.runemate.game.api.hybrid.region.Players;
import com.runemate.game.api.hybrid.util.StopWatch;

public class Settings {

    public boolean DEBUG_BOT = true;
    public static int MAX_TRAPS = 5;

    public static String botStatus;

    private static StopWatch runTimer = new StopWatch();

    private static int impsCaught;
    public static int trapsSet;
    public static int hunterXpGained;
    public static int levelsGained;

    private int xpGained = Skill.HUNTER.getExperience()-hunterXpGained;

    private static String[] trapsLocations= {"None", "None", "None", "None", "None"};

    public Settings() {
    }

    public void firstLoad() {
        if (runTimer != null) {
            runTimer.reset();
        }
        hunterXpGained = Skill.HUNTER.getExperience();
        botStatus = "Bot status: Warming up";
        impsCaught = 0;
        trapsSet = 0;
        levelsGained = Skill.HUNTER.getCurrentLevel();
        trapsLocations = new String[]{"None", "None", "None", "None", "None"};
        runTimer.start();
    }

    public void resetTrapsInBank() {
        trapsSet = 0;
    }

    public int getMaxTraps() {
        return MAX_TRAPS;
    }

    public void debugTraps() {
        for (int i = 0; i < MAX_TRAPS; i++) {
            System.out.println("DEBUGTRAPS: " + trapsLocations[i]);
        }
    }

    public void addTrapLocation () {
        for (int i=0; i<MAX_TRAPS; i++)
        {
            if (trapsLocations[i] == "None") {
                trapsLocations[i] = Players.getLocal().getPosition().toString();
                System.out.println("ADDTRAPLOCATION: (" + i + ") | " + Players.getLocal().getPosition());
                break;
            }
        }
    }

    public void removeTrapLocation (Coordinate trapCoordinate) {
        for (int i=0; i<MAX_TRAPS; i++)
        {
            if (trapsLocations[i] == trapCoordinate.toString()) {
                trapsLocations[i] = "None";
                System.out.println("removeTrapLocation: (" + i + ") | " + Players.getLocal().getPosition());
                break;
            }
        }
    }

    public String getRunTime() { return runTimer.getRuntimeAsString(); }

    public String getBotStatus() { return botStatus; }

    public String getExperienceGained() {
        return "" +xpGained;
    }

    public int getLevelsGained() { return levelsGained-Skill.HUNTER.getCurrentLevel(); }

    public String getImpCount() { return "" +impsCaught; }

    public void increaseImpCount() { impsCaught++; }

    public void setBotStatus(String status) {
        botStatus = status;
    }

    public int getTrapCount() {
        return trapsSet;
    }

    public void deleteTrap() {
        --trapsSet;
        System.out.println("deleteTrap called: " + trapsSet);
    }

    public void addTrap() {
        addTrapLocation();
        trapsSet++;
        System.out.println("addTrap called: " + trapsSet);
    }

    public boolean gettingDebugBot() {
        return this.DEBUG_BOT;
    }
}
