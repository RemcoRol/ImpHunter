package com.remco1337.bots.ImpHunter;

import com.runemate.game.api.hybrid.local.hud.interfaces.Equipment;
import com.runemate.game.api.hybrid.local.hud.interfaces.SpriteItem;
import com.runemate.game.api.script.Execution;
import com.runemate.game.api.script.framework.tree.BranchTask;
import com.runemate.game.api.script.framework.tree.TreeTask;

// import path.to.your.HandleBank
// import path.to.your.Stop

/**
 * NOTES:
 * 
 */
public class TeleportCWars extends BranchTask {

    private HandleBank handlebank = new HandleBank();
    private Stop stop = new Stop();

    private String[] ringOfDueling = new String[]{"Ring of dueling(1)", "Ring of dueling(2)", "Ring of dueling(3)", "Ring of dueling(4)", "Ring of dueling(5)", "Ring of dueling(6)", "Ring of dueling(7)", "Ring of dueling(8)"};

    @Override
    public boolean validate() {
        Settings setting = new Settings();
        setting.setBotStatus("Bot status: Teleporting to Castle Wars");
        if (setting.gettingDebugBot()) {
            System.out.println("TeleportCWars.java: started");
            SpriteItem duelring = Equipment.getItems(ringOfDueling).first();
            if (duelring != null) {
                if (duelring.interact("Castle Wars")) {
                    System.out.println("Teleported to Castle Wars");
                    Execution.delay(3000, 4000);
                    return true;
                }
            }
        }
        return true;
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
