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

        itemProvider.addItem(getTitanAshItem(), "titan_ash", "titan_ash");

    }

    private ItemStack getTitanAshItem() {
        ItemStack item = new ItemStack(Material.GLOWSTONE_DUST);
        item.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 1);
        ItemMeta meta = item.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES);
        meta.displayName(Component.text(ColorHelper.translate("&#e91ffb&l✦ &#e127fb&lP&#d82efb&lr&#d036fb&lo&#c83dfc&lc&#bf45fc&lh " +
                "&#b74dfc&lT&#af54fc&ly&#a75cfc&lt&#9e64fc&la&#966bfd&ln&#8e73fd&ló&#857afd&lw &#7d82fd&l✦")));
        meta.lore(List.of(
                Component.text("Możesz go wykorzystać u Mrocznego Kowala").color(TextColor.color(152, 195, 34)),
                Component.text("do zakupu najpotężniejszych przedmiotów.")
        ));

        meta.getPersistentDataContainer().set(getTitanAshKey(), PersistentDataType.INTEGER, 1);
        item.setItemMeta(meta);
        return item;
    }

    private static NamespacedKey getTitanAshKey() {
        return new NamespacedKey(plugin, "titan_ash");
    }


    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
