package io.github.reconsolidated.titanash;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.persistence.PersistentDataType;

import java.util.Random;

public class GiveAshOnKill implements Listener {

    private NamespacedKey lastUpdateKey = new NamespacedKey(TitanAsh.plugin, "ash_drop_last_update");
    private NamespacedKey deathsAvailableKey = new NamespacedKey(TitanAsh.plugin, "ash_drop_deaths_available");

    public GiveAshOnKill() {

    }


    @EventHandler
    public void onKill(PlayerDeathEvent event) {
        Player player = event.getPlayer();

        long lastUpdate = player.getPersistentDataContainer().getOrDefault(lastUpdateKey, PersistentDataType.LONG, 0L);
        int deathsAvailable = player.getPersistentDataContainer().getOrDefault(deathsAvailableKey, PersistentDataType.INTEGER, 3);

        if (deathsAvailable < 3) {
            long diff = System.currentTimeMillis() - lastUpdate;
            long cooldown = 300000;//test - 5 minutes, 28800000; // 8 hours
            if (diff / cooldown > 0) {
                deathsAvailable = (int) Math.min(3, deathsAvailable + diff / cooldown);
                player.getPersistentDataContainer().set(lastUpdateKey, PersistentDataType.LONG, System.currentTimeMillis());
            }
        }

        int dropChance = 10 * deathsAvailable;
        Random random = new Random();
        if (random.nextInt(100) < dropChance) {
            event.getDrops().add(TitanAsh.plugin.getTitanAsh());
        }

        deathsAvailable = Math.max(0, deathsAvailable - 1);

        player.getPersistentDataContainer().set(deathsAvailableKey, PersistentDataType.INTEGER, deathsAvailable);

    }


}
