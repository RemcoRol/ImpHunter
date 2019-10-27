package com.remco1337.bots.ImpHunter;

import com.remco1337.bots.ImpHunter.ui.ImpCatcherUIController;
import com.runemate.game.api.hybrid.entities.Player;
import com.runemate.game.api.hybrid.location.Area;
import com.runemate.game.api.hybrid.location.Coordinate;
import com.runemate.game.api.hybrid.region.Players;
import com.runemate.game.api.hybrid.util.calculations.Distance;
import com.runemate.game.api.script.framework.tree.BranchTask;
import com.runemate.game.api.script.framework.tree.TreeTask;

// import path.to.your.CheckMagicBox
// import path.to.your.IsInKaramja

/**
 * NOTES:
 * This is the root node.

Add children of this branch using the settings to the right.
 */
public class IsInImpArea extends BranchTask {

    private CheckMagicBox checkmagicbox = new CheckMagicBox();
    private IsInKaramja isinkaramja = new IsInKaramja();

    final Area impArea = new Area.Rectangular(new Coordinate (2828, 3182, 0), new Coordinate (2832, 3178, 0));
    Settings setting = new Settings();
    ImpCatcherUIController guiController = new ImpCatcherUIController();

    private Player player;

    @Override
    public boolean validate() {
        guiController.updateGUI();

        if (setting.gettingDebugBot()) {
            System.out.println("IsInImpArea.java: started");
            if (Players.getLocal() != null && Distance.between(impArea, Players.getLocal()) < 5 || impArea.contains(player)) {
                System.out.println("IsInBank.java: Is in imp area!");
                return true;
            } else {
                System.out.println("IsInBank.java: Is not in imp area!");
                return false;
            }
        }
        return Players.getLocal() != null && Distance.between(impArea, Players.getLocal()) < 5;
    }

    @Override
    public TreeTask failureTask() {
        return isinkaramja;
    }

    @Override
    public TreeTask successTask() {
        return checkmagicbox;
    }
}
