package com.remco1337.bots.ImpHunter;

import com.runemate.game.api.hybrid.entities.GameObject;
import com.runemate.game.api.hybrid.local.hud.interfaces.Equipment;
import com.runemate.game.api.hybrid.region.GameObjects;
import com.runemate.game.api.script.framework.tree.BranchTask;
import com.runemate.game.api.script.framework.tree.TreeTask;

// import path.to.your.TeleportToEdge
// import path.to.your.Stop

/**
 * NOTES:
 * 
 */
public class CheckForGlory extends BranchTask {

    Settings setting = new Settings();

    private TeleportToEdge teleporttoedge = new TeleportToEdge();
    private Stop stop = new Stop();

    String[] gloryAmulet = new String[]{"Amulet of glory(1)", "Amulet of glory(2)", "Amulet of glory(3)", "Amulet of glory(4)", "Amulet of glory(5)", "Amulet of glory(6)", "Amulet of glory(7)", "Amulet of glory(8)"};

    @Override
    public boolean validate() {
        if (setting.gettingDebugBot()) {
            System.out.println("CheckForGlory.java: started");
            if (Equipment.containsAnyOf(gloryAmulet)) {
                System.out.println("Amulet of Glory exist");
                return true;
            } else {
                System.out.println("Dueling doens't exist");
                return false;
            }
        }
        return Equipment.containsAnyOf(gloryAmulet);
    }

    @Override
    public TreeTask failureTask() {
        return stop;
    }

    @Override
    public TreeTask successTask() {
        return teleporttoedge;
    }
}
