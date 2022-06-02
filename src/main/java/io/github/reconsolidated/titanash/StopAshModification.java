package io.github.reconsolidated.titanash;

import com.destroystokyo.paper.event.inventory.PrepareGrindstoneEvent;
import com.destroystokyo.paper.event.inventory.PrepareResultEvent;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;

public class StopAshModification implements Listener {

    @EventHandler
    public void onRename(PrepareAnvilEvent event) {
        if (event.getResult() != null && event.getResult().getType() == Material.GLOWSTONE_DUST) {
            event.setResult(null);
        }
    }

    @EventHandler
    public void onDisenchant(PrepareResultEvent event) {
        if (event.getResult() != null && event.getResult().getType() == Material.GLOWSTONE_DUST) {
            event.setResult(null);
        }

    }
}
