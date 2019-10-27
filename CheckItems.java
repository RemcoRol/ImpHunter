package com.remco1337.bots.ImpHunter;

import com.runemate.game.api.hybrid.entities.GameObject;
import com.runemate.game.api.hybrid.entities.GroundItem;
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;
import com.runemate.game.api.hybrid.region.GameObjects;
import com.runemate.game.api.hybrid.region.GroundItems;
import com.runemate.game.api.script.framework.tree.BranchTask;
import com.runemate.game.api.script.framework.tree.TreeTask;

// import path.to.your.WalkToImpArea
// import path.to.your.CheckForDueling

/**
 * NOTES:
 * 
 */
public class CheckItems extends BranchTask {

    Settings setting = new Settings();

    private WalkToImpArea walktoimparea = new WalkToImpArea();
    private CheckForDueling checkfordueling = new CheckForDueling();

    @Override
    public boolean validate() {
        if (setting.gettingDebugBot()) {
            System.out.println("CheckItems.java: started");
            if (Inventory.contains("Magic box")) {
                System.out.println("Contains magic box :)");
                return true;
            } else {
                System.out.println("Doens't contain magic box");
                return false;
            }
        }
        return Inventory.contains("Magic box");
    }

    @Override
    public TreeTask failureTask() {
        return checkfordueling;
    }

    @Override
    public TreeTask successTask() {
        return walktoimparea;
    }
}
