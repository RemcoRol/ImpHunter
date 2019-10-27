package com.remco1337.bots.ImpHunter;

import com.runemate.game.api.hybrid.entities.GameObject;
import com.runemate.game.api.hybrid.entities.GroundItem;
import com.runemate.game.api.hybrid.region.GameObjects;
import com.runemate.game.api.hybrid.region.GroundItems;
import com.runemate.game.api.script.framework.tree.BranchTask;
import com.runemate.game.api.script.framework.tree.TreeTask;

// import path.to.your.CollectTraps
// import path.to.your.SetTraps

/**
 * NOTES:
 * 
 */
public class TrapsPlaced extends BranchTask {

    private CollectTraps collecttraps = new CollectTraps();
    private SetTraps settraps = new SetTraps();

    @Override
    public boolean validate() {
        Settings setting = new Settings();
        GameObject magicBox = GameObjects.newQuery().names("Magic box").actions("Retrieve").results().first();
        GameObject magicBoxDeactivated = GameObjects.newQuery().names("Magic box failed").actions("Deactivate").results().first();
        GroundItem magicBoxFailed = GroundItems.newQuery().names("Magic box").actions("Take", "Activate").results().first();

        if (setting.gettingDebugBot()) {
            System.out.println("TrapsPlaced.java: started");
            if (magicBox != null || magicBoxDeactivated != null || magicBoxFailed != null) {
                System.out.println("TrapsPlaced.java: magic box placed");
                return true;
            } else {
                System.out.println("TrapsPlaced.java: magic box not placed");
                return false;
            }
        }
        return magicBox != null || magicBoxDeactivated != null || magicBoxFailed != null;
    }

    @Override
    public TreeTask failureTask() {
        return settraps;
    }

    @Override
    public TreeTask successTask() {
        return collecttraps;
    }
}
