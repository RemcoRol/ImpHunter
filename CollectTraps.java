package com.remco1337.bots.ImpHunter;

import com.runemate.game.api.hybrid.cache.sprites.Sprite;
import com.runemate.game.api.hybrid.entities.GameObject;
import com.runemate.game.api.hybrid.entities.GroundItem;
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;
import com.runemate.game.api.hybrid.local.hud.interfaces.SpriteItem;
import com.runemate.game.api.hybrid.location.Coordinate;
import com.runemate.game.api.hybrid.location.navigation.Traversal;
import com.runemate.game.api.hybrid.location.navigation.basic.BresenhamPath;
import com.runemate.game.api.hybrid.location.navigation.cognizant.RegionPath;
import com.runemate.game.api.hybrid.location.navigation.web.WebPath;
import com.runemate.game.api.hybrid.region.GameObjects;
import com.runemate.game.api.hybrid.region.GroundItems;
import com.runemate.game.api.hybrid.region.Players;
import com.runemate.game.api.script.Execution;
import com.runemate.game.api.script.framework.tree.LeafTask;

/**
 * NOTES:
 * 
 */
public class CollectTraps extends LeafTask {

    Settings setting = new Settings();

    @Override
    public void execute() {
        if (setting.gettingDebugBot()) {
            System.out.println("CollectTraps.java: started");
        }

        setting.setBotStatus("Bot status: Collecting traps");

        int oldInventoryCountNonactive = Inventory.getQuantity("Magic box");
        int oldInventoryCountActive = Inventory.getQuantity("Imp-in-a-box(2)");


        GameObject magicBox = GameObjects.newQuery().names("Magic box").actions("Retrieve").results().first();
        GroundItem magicBoxDeactivated = GroundItems.newQuery().names("Magic box").actions("Take", "Activate").results().first();
        GameObject magicBoxFailed = GameObjects.newQuery().names("Magic box failed").actions("Deactivate").results().first();

        if (magicBox != null && magicBox.hover()) {
            if (magicBox.interact("Retrieve")) {
                Execution.delayUntil(()-> oldInventoryCountActive < Inventory.getQuantity("Imp-in-a-box(2)"), 2500, 3500);
                if (oldInventoryCountActive < Inventory.getQuantity("Imp-in-a-box(2)")) {
                    Coordinate destination = magicBox.getPosition();
                    setting.deleteTrap();
                    setting.removeTrapLocation(destination);
                    setting.increaseImpCount();
                    if(Players.getLocal().getPosition() == destination) {
                        placeTrap();
                    }
                    else {
                        walkToBoxCoordinate(destination);
                        Execution.delay(1000, 2000);
                        if(Players.getLocal().getPosition() == destination) {
                            placeTrap();
                        }
                    }
                    return;
                }
            }
        }
        if (magicBoxDeactivated != null && magicBoxDeactivated.hover()) {
            if (magicBoxDeactivated.interact("Activate")) {
                Coordinate destinationDeactivated = magicBoxDeactivated.getPosition();

                Execution.delay(2500, 3500);
                return;
            }
        }
        if (magicBoxFailed != null && magicBoxFailed.hover()) {
            if (magicBoxFailed.interact("Deactivate")) {
                Execution.delay(2500, 3500);
                if (oldInventoryCountNonactive < Inventory.getQuantity("Magic box")) {
                    Coordinate destinationDeactivated = magicBoxFailed.getPosition();

                    setting.deleteTrap();
                    setting.removeTrapLocation(destinationDeactivated);

                    if(Players.getLocal().getPosition() == destinationDeactivated) {
                        placeTrap();
                    }
                    else {
                        walkToBoxCoordinate(destinationDeactivated);
                        Execution.delay(1000, 2000);
                        if(Players.getLocal().getPosition() == destinationDeactivated) {
                            placeTrap();
                        }
                    }
                }
            }
        }
    }
    public void placeTrap() {
        SpriteItem magicBoxInventory = Inventory.getItems("Magic box").first();
        if(setting.getTrapCount() < setting.getMaxTraps()) {
            if (!checkIfStandingOnTrap()) {
                if (magicBoxInventory != null && magicBoxInventory.hover()) {
                    if (magicBoxInventory.interact("Activate")) {
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

    public boolean walkToBoxCoordinate(Coordinate trapCoordinate) {
        RegionPath regionPath = RegionPath.buildTo(trapCoordinate);
        if(regionPath != null){
            regionPath.step();
            Execution.delay(3000, 4000);
            return true;
        } else {
            getLogger().warn("regionPath was null in Walk");
        }
        Execution.delay(200, 500);
        return false;
    }
}
