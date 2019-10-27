package com.remco1337.bots.ImpHunter;

import com.runemate.game.api.hybrid.entities.GameObject;
import com.runemate.game.api.hybrid.local.hud.interfaces.Equipment;
import com.runemate.game.api.hybrid.region.GameObjects;
import com.runemate.game.api.script.framework.tree.BranchTask;
import com.runemate.game.api.script.framework.tree.TreeTask;

// import path.to.your.TeleportCWars
// import path.to.your.CheckForGlory

/**
 * NOTES:
 * 
 */
public class CheckForDueling extends BranchTask {

    Settings setting = new Settings();

    private TeleportCWars teleportcwars = new TeleportCWars();
    private CheckForGlory checkforglory = new CheckForGlory();

    private String[] ringOfDueling = new String[]{"Ring of dueling(1)", "Ring of dueling(2)", "Ring of dueling(3)", "Ring of dueling(4)", "Ring of dueling(5)", "Ring of dueling(6)", "Ring of dueling(7)", "Ring of dueling(8)"};

    @Override
    public boolean validate() {

        if (setting.gettingDebugBot()) {
            System.out.println("CheckForDueling.java: started");
            if (Equipment.containsAnyOf(ringOfDueling)) {
                System.out.println("Dueling exist");
                return true;
            }
            else {
                System.out.println("Dueling doens't exist");
                return false;
            }
        }
        return Equipment.containsAnyOf(ringOfDueling);
    }

    @Override
    public TreeTask failureTask() {
        return checkforglory;
    }

    @Override
    public TreeTask successTask() {
        return teleportcwars;
    }
}
