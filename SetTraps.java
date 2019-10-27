package com.remco1337.bots.ImpHunter;

import com.runemate.game.api.hybrid.Environment;
import com.runemate.game.api.hybrid.entities.GameObject;
import com.runemate.game.api.hybrid.entities.GroundItem;
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;
import com.runemate.game.api.hybrid.local.hud.interfaces.SpriteItem;
import com.runemate.game.api.hybrid.location.Coordinate;
import com.runemate.game.api.hybrid.location.navigation.basic.BresenhamPath;
import com.runemate.game.api.hybrid.region.GameObjects;
import com.runemate.game.api.hybrid.region.GroundItems;
import com.runemate.game.api.hybrid.region.Players;
import com.runemate.game.api.script.Execution;
import com.runemate.game.api.script.framework.tree.LeafTask;

import java.text.SimpleDateFormat;

/**
 * NOTES:
 * 
 */
public class SetTraps extends LeafTask {

    @Override
    public void execute() {
        SpriteItem magicBox = Inventory.getItems("Magic box").first();
        Settings setting = new Settings();
        setting.setBotStatus("Bot status: Setting traps/Waiting");
        if (setting.gettingDebugBot()) {
            System.out.println("SetTraps.java: started | Traps placed: " + setting.getTrapCount() + " | Runtime: " + setting.getRunTime() + " | Imps caught: " + setting.getImpCount());
        }
        if(setting.getTrapCount() < setting.getMaxTraps()) {
            if (!checkIfStandingOnTrap()) {
                if (magicBox != null && magicBox.hover()) {
                    if (magicBox.interact("Activate")) {
                        setting.addTrap();
                        Execution.delay(3500, 4500);
                    }
                }
            }
        }
    }
    public boolean checkIfStandingOnTrap() {
        GameObject magicBox = GameObjects.newQuery().names("Magic box").on(Players.getLocal().getPosition()).actions("Retrieve").results().first();
        GroundItem magicBoxDeactivated = GroundItems.newQuery().names("Magic box").on(Players.getLocal().getPosition()).actions("Take", "Activate").results().first();
        GameObject magicBoxFailed = GameObjects.newQuery().names("Magic box failed").on(Players.getLocal().getPosition()).actions("Deactivate").results().first();
        GameObject magicBoxPending = GameObjects.newQuery().names("Magic box").on(Players.getLocal().getPosition()).actions("Deactivate", "Investigate").results().first();
        if (magicBox != null || magicBoxDeactivated != null || magicBoxFailed != null || magicBoxPending != null) {
            System.out.println("Standing on magic box :)");
            final Coordinate stepToCoord = Players.getLocal().getPosition().derive(0, 1);
            BresenhamPath bresPath = BresenhamPath.buildTo(stepToCoord);
            if (bresPath != null) {
                bresPath.step();
                return true;
            } else {
                getLogger().warn("bresPath was null in checkifStandingOnTrap");
                return true;
            }
        }
        return false;
    }
}
