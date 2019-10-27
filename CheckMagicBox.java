package com.remco1337.bots.ImpHunter;

import com.runemate.game.api.hybrid.entities.GameObject;
import com.runemate.game.api.hybrid.entities.GroundItem;
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;
import com.runemate.game.api.hybrid.region.GameObjects;
import com.runemate.game.api.hybrid.region.GroundItems;
import com.runemate.game.api.script.framework.tree.BranchTask;
import com.runemate.game.api.script.framework.tree.TreeTask;

// import path.to.your.TrapsPlaced
// import path.to.your.CheckForDueling

/**
 * NOTES:
 * 
 */
public class CheckMagicBox extends BranchTask {

    Settings setting = new Settings();

    private TrapsPlaced trapsplaced = new TrapsPlaced();
    private CheckForDueling checkfordueling = new CheckForDueling();

    @Override
    public boolean validate() {

        GameObject magicBox = GameObjects.newQuery().names("Magic box").actions("Retrieve").results().first();
        GameObject magicBoxDeactivated = GameObjects.newQuery().names("Magic box failed").actions("Deactivate").results().first();
        GroundItem magicBoxFailed = GroundItems.newQuery().names("Magic box").actions("Take", "Activate").results().first();
        GameObject magicBoxPlaced = GameObjects.newQuery().names("Magic box").actions("Deactivate", "Investigate").results().first();

        if (setting.gettingDebugBot()) {
            System.out.println("CheckMagicBox.java: started");
            if (Inventory.contains("Magic box") || magicBox != null || magicBoxDeactivated != null || magicBoxFailed != null || magicBoxPlaced != null) {
                System.out.println("Magic box and/or traps found :)");
                return true;
            } else {
                System.out.println("No magic box and/or traps found!");
                return false;
            }
        }
        return Inventory.contains("Magic box") || magicBox != null || magicBoxDeactivated != null || magicBoxFailed != null || magicBoxPlaced != null;
    }

    @Override
    public TreeTask failureTask() {
        return checkfordueling;
    }

    @Override
    public TreeTask successTask() {
        return trapsplaced;
    }
}
