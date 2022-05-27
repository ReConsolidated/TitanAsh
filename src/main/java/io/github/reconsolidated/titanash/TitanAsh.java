package io.github.reconsolidated.titanash;

import io.github.reconsolidated.itemprovider.ItemProvider;
import io.github.reconsolidated.titanash.AdminShop.AdminShopItem;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public final class TitanAsh extends JavaPlugin {

    public static TitanAsh plugin;

    @Getter
    private ItemProvider itemProvider;

    public static boolean isTitanAsh(ItemStack itemStack) {
        return itemStack != null
                && itemStack.hasItemMeta()
                && itemStack.getItemMeta().getPersistentDataContainer().get(getTitanAshKey(), PersistentDataType.INTEGER) != null;
    }

    public ItemStack getTitanAsh() {
        return itemProvider.getItem("titan_ash", "titan_ash");
    }


    @Override
    public void onEnable() {
        plugin = this;
        // Plugin startup logic
        ConfigurationSerialization.registerClass(AdminShopItem.class, "admin_shop_item");
        itemProvider = getServer().getServicesManager().load(ItemProvider.class);
        if (itemProvider == null) {
            Bukkit.getPluginManager().disablePlugin(this);
            throw new RuntimeException("Couldn't load ItemProvider plugin. Make sure it's added as a dependency.");
        }

        getCommand("titanshop").setExecutor(new TitanShopCommand());

        getServer().getPluginManager().registerEvents(new GiveAshOnKill(), this);
    }


    private static NamespacedKey getTitanAshKey() {
        return new NamespacedKey(plugin, "titan_ash");
    }


    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
