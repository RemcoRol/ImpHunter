package com.remco1337.bots.ImpHunter;

import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;
import com.runemate.game.api.hybrid.location.Coordinate;
import com.runemate.game.api.hybrid.region.Players;
import com.runemate.game.api.hybrid.util.calculations.Distance;
import com.runemate.game.api.script.framework.tree.BranchTask;
import com.runemate.game.api.script.framework.tree.TreeTask;

// import path.to.your.HandleBank
// import path.to.your.CheckForDueling

/**
 * NOTES:
 * 
 */
public class IsInBank extends BranchTask {

    private HandleBank handlebank = new HandleBank();
    private CheckForDueling checkfordueling = new CheckForDueling();

    @Override
    public boolean validate() {
        Settings setting = new Settings();
        Coordinate bankArea = new Coordinate(2439, 3092, 0);

        if (setting.gettingDebugBot()) {
            System.out.println("IsInBank.java: started");
            if (Players.getLocal() != null && Distance.between(bankArea, Players.getLocal()) < 50) {
                System.out.println("IsInBank.java: Is in bank!");
                return true;
            } else {
                System.out.println("IsInBank.java: Is not in bank!");
                return false;
            }
        }


        return Players.getLocal() != null && Distance.between(bankArea, Players.getLocal()) < 50;

    }

    @Override
    public TreeTask failureTask() {
        return checkfordueling;
    }

    @Override
    public TreeTask successTask() {
        return handlebank;
    }
}
