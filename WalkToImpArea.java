package com.remco1337.bots.ImpHunter;

import com.runemate.game.api.hybrid.location.Area;
import com.runemate.game.api.hybrid.location.Coordinate;
import com.runemate.game.api.hybrid.location.navigation.Path;
import com.runemate.game.api.hybrid.location.navigation.Traversal;
import com.runemate.game.api.hybrid.location.navigation.basic.BresenhamPath;
import com.runemate.game.api.hybrid.location.navigation.web.WebPath;
import com.runemate.game.api.hybrid.region.Players;
import com.runemate.game.api.script.framework.tree.LeafTask;

/**
 * NOTES:
 * 
 */
public class WalkToImpArea extends LeafTask {

    @Override
    public void execute() {
        Settings setting = new Settings();
        setting.setBotStatus("Bot status: Walking to Imp Area");
        if (setting.gettingDebugBot()) {
            System.out.println("WalkToImpArea.java: started");
        }
        final Area impArea = new Area.Rectangular(new Coordinate (2828, 3182, 0), new Coordinate (2832, 3178, 0));
        BresenhamPath bresPath = BresenhamPath.buildTo(impArea);
        if (bresPath != null) {
            bresPath.step();
        } else {
            getLogger().warn("bresPath was null in WalkToImpArea");
        }
    }
}
