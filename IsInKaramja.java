package com.remco1337.bots.ImpHunter;

import com.runemate.game.api.hybrid.location.Coordinate;
import com.runemate.game.api.hybrid.region.Players;
import com.runemate.game.api.hybrid.util.calculations.Distance;
import com.runemate.game.api.script.framework.tree.BranchTask;
import com.runemate.game.api.script.framework.tree.TreeTask;

// import path.to.your.CheckItems
// import path.to.your.IsInBank

/**
 * NOTES:
 * 
 */
public class IsInKaramja extends BranchTask {

    private CheckItems checkitems = new CheckItems();
    private IsInBank isinbank = new IsInBank();

    Settings setting = new Settings();

    Coordinate karamjaArea = new Coordinate(2918, 3176, 0);

    @Override
    public boolean validate() {

        if (setting.gettingDebugBot()) {
            System.out.println("IsInKaramja.java: started");
            if (Players.getLocal() != null && Distance.between(karamjaArea, Players.getLocal()) < 500) {
                System.out.println("IsInKaramja.java: Is in karamja!");
                return true;
            } else {
                System.out.println("IsInKaramja.java: Is not in karamja!");
                return false;
            }
        }

        return Players.getLocal() != null && Distance.between(karamjaArea, Players.getLocal()) < 500;
    }

    @Override
    public TreeTask failureTask() {
        return isinbank;
    }

    @Override
    public TreeTask successTask() {
        return checkitems;
    }
}
