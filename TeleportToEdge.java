package com.remco1337.bots.ImpHunter;

import com.runemate.game.api.script.framework.tree.BranchTask;
import com.runemate.game.api.script.framework.tree.TreeTask;

// import path.to.your.HandleBank
// import path.to.your.Stop

/**
 * NOTES:
 * 
 */
public class TeleportToEdge extends BranchTask {

    private HandleBank handlebank = new HandleBank();
    private Stop stop = new Stop();

    @Override
    public boolean validate() {
        return false;
    }

    @Override
    public TreeTask failureTask() {
        return stop;
    }

    @Override
    public TreeTask successTask() {
        return handlebank;
    }
}
