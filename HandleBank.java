package com.remco1337.bots.ImpHunter;

import com.runemate.game.api.hybrid.Environment;
import com.runemate.game.api.hybrid.entities.GameObject;
import com.runemate.game.api.hybrid.local.Camera;
import com.runemate.game.api.hybrid.local.hud.interfaces.Bank;
import com.runemate.game.api.hybrid.local.hud.interfaces.Equipment;
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;
import com.runemate.game.api.hybrid.local.hud.interfaces.SpriteItem;
import com.runemate.game.api.hybrid.location.Coordinate;
import com.runemate.game.api.hybrid.location.navigation.basic.BresenhamPath;
import com.runemate.game.api.hybrid.location.navigation.cognizant.RegionPath;
import com.runemate.game.api.hybrid.region.GameObjects;
import com.runemate.game.api.osrs.local.hud.interfaces.ControlPanelTab;
import com.runemate.game.api.script.Execution;
import com.runemate.game.api.script.framework.tree.LeafTask;

/**
 * NOTES:
 * 
 */
public class HandleBank extends LeafTask {

    Settings setting = new Settings();
    String[] gloryAmulet = new String[]{"Amulet of glory(1)", "Amulet of glory(2)", "Amulet of glory(3)", "Amulet of glory(4)", "Amulet of glory(5)", "Amulet of glory(6)", "Amulet of glory(7)", "Amulet of glory(8)"};
    String[] gloryAmuletReady = new String[]{"Amulet of glory(2)", "Amulet of glory(3)", "Amulet of glory(4)", "Amulet of glory(5)", "Amulet of glory(6)", "Amulet of glory(7)", "Amulet of glory(8)"};
    String[] ringOfDuelingReady = new String[]{"Ring of dueling(2)", "Ring of dueling(3)", "Ring of dueling(4)", "Ring of dueling(5)", "Ring of dueling(6)", "Ring of dueling(7)", "Ring of dueling(8)"};
    String[] ringOfDueling = new String[]{"Ring of dueling(1)", "Ring of dueling(2)", "Ring of dueling(3)", "Ring of dueling(4)", "Ring of dueling(5)", "Ring of dueling(6)", "Ring of dueling(7)", "Ring of dueling(8)"};

    private void wearJewerly() {
        SpriteItem fullGloryAmulet = Inventory.getItems("Amulet of glory(6)").first();
        SpriteItem fullDuelRing = Inventory.getItems("Ring of dueling(8)").first();

        if (ControlPanelTab.getOpened() == ControlPanelTab.INVENTORY) {
            if (fullDuelRing != null && fullDuelRing.hover()) {
                Execution.delay(100, 250);
                if (fullDuelRing.interact("Wear")) {
                    System.out.println("Wearing full duel ring");
                    Execution.delay(200, 500);
                }
            }
            if (fullGloryAmulet != null && fullGloryAmulet.interact("Wear")) {
                Execution.delay(100, 250);
                if (fullGloryAmulet.interact("Wear")) {
                    System.out.println("Wearing full duel ring");
                    Execution.delay(200, 500);
                }
            }
        } else {
            ControlPanelTab.INVENTORY.open();
            Execution.delay(200, 350);

            if (fullDuelRing != null && fullDuelRing.hover()) {
                Execution.delay(100, 250);
                if (fullDuelRing.interact("Wear")) {
                    System.out.println("Wearing full duel ring");
                    Execution.delay(200, 500);
                }
            }
            if (fullGloryAmulet != null && fullGloryAmulet.interact("Wear")) {
                Execution.delay(100, 250);
                if (fullGloryAmulet.interact("Wear")) {
                    System.out.println("Wearing full duel ring");
                    Execution.delay(200, 500);
                }
            }
        }
    }

    private void teleportToKaramja() {
        SpriteItem gloryAmulet = Equipment.getItems(gloryAmuletReady).first();
        if (gloryAmulet != null) {
            if (ControlPanelTab.getOpened() == ControlPanelTab.EQUIPMENT) {
                if(gloryAmulet.hover()) {
                    if (gloryAmulet.interact("Karamja")) {
                        Execution.delay(4000, 5000);
                    }
                }
            } else {
                ControlPanelTab.EQUIPMENT.open();
                Execution.delay(200, 350);
                if(gloryAmulet.hover()) {
                    if (gloryAmulet.interact("Karamja")) {
                        Execution.delay(4000, 5000);
                    }
                }
            }
        }
    }

    @Override
    public void execute() {
        setting.setBotStatus("Bot status: Banking");
        setting.resetTrapsInBank();
        GameObject bankChest = GameObjects.newQuery().names("Bank chest").actions("Use").results().first();
        if (!Bank.isOpen() && bankChest != null && !bankChest.isVisible()) {
            Camera.concurrentlyTurnTo(bankChest);
            Execution.delay(200, 500);
        }
        if (Bank.isOpen()) {
            if (Equipment.containsAnyOf(ringOfDuelingReady) && Equipment.containsAnyOf(gloryAmuletReady) && Inventory.getQuantity("Magic box") == 28) {
                System.out.println("No need for banking - closing bank!");
                if(Bank.close()) {
                    Execution.delay(200, 500);
                    teleportToKaramja();
                }
            }
            else {
                Bank.depositInventory();
                Execution.delay(200, 500);

                if (Equipment.containsAnyOf(ringOfDuelingReady) && Equipment.containsAnyOf(gloryAmuletReady)) {
                    if (Inventory.contains("Imp-in-a-box (2)")) {
                        Bank.depositInventory();
                    }
                    if(Bank.contains("Magic box") && Bank.getQuantity("Magic box") > 27) {
                        if(Bank.withdraw("Magic box", 28)) {
                            if (Inventory.getQuantity("Magic box") == 28) {
                                Bank.close();
                                Execution.delay(500, 800);
                                teleportToKaramja();
                            }
                        }
                    }
                    else {
                        Environment.getBot().stop("Ran out of Magic boxes :(");
                    }
                }

                if (!Equipment.containsAnyOf(ringOfDuelingReady) || !Equipment.containsAnyOf(gloryAmuletReady)) {
                    if(!Equipment.containsAnyOf(ringOfDuelingReady) && !Inventory.containsAnyOf(ringOfDuelingReady)) {
                        if (Bank.containsAnyOf(ringOfDuelingReady)) {
                            if (Bank.withdraw("Ring of dueling(8)", 1)) {
                                System.out.println("Ring of dueling taken from bank!");
                                Execution.delay(200, 500);
                            }
                        }
                        else {
                            Environment.getBot().stop("Ran out of Ring of dueling :(");
                        }
                    }
                    if(!Equipment.containsAnyOf(gloryAmuletReady) && !Inventory.containsAnyOf(gloryAmuletReady)) {
                        if (Bank.containsAnyOf(gloryAmuletReady)) {
                            if (Bank.withdraw("Amulet of glory(6)", 1)) {
                                System.out.println("Amulet of Glory taken from bank!");
                                Execution.delay(200, 500);
                            }
                        }
                        else {
                            Environment.getBot().stop("Ran out of Ring of glories :(");
                        }
                    }

                    if(Inventory.containsAnyOf(ringOfDuelingReady) || Inventory.containsAnyOf(gloryAmuletReady)) {
                        if (Bank.close()) {
                            Execution.delay(200, 500);
                        }
                        wearJewerly();
                    }
                }
            }
        }
        else
        {
            if (bankChest != null && bankChest.isVisible()) {
                if (Equipment.containsAnyOf(ringOfDuelingReady) && Equipment.containsAnyOf(gloryAmuletReady) && Inventory.getQuantity("Magic box") == 28) {
                    teleportToKaramja();
                }

                if (Inventory.containsAnyOf(ringOfDuelingReady) && Inventory.getQuantity(ringOfDuelingReady) == 1 && Inventory.containsAnyOf(gloryAmuletReady) && Inventory.getQuantity(gloryAmuletReady) == 1 && Inventory.getQuantity("Magic box") == 28) {
                    
                }
                if(bankChest.hover()) {
                    bankChest.interact("Use");
                    Execution.delay(3000, 4000);
                }
            }
            else
            {
                if (bankChest != null) {
                    Camera.concurrentlyTurnTo(bankChest);

                    BresenhamPath bresPath = BresenhamPath.buildTo(bankChest.getPosition());
                    if (bresPath != null) {
                        bresPath.step();
                    } else {
                        getLogger().warn("bresPath was null in Walk");
                    }
                    Camera.concurrentlyTurnTo(bankChest);
                }
            }
        }
    }
}
