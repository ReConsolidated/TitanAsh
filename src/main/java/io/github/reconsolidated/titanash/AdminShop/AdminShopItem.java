package io.github.reconsolidated.titanash.AdminShop;

import io.github.reconsolidated.titanash.TitanAsh;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SerializableAs("admin_shop_item")
public class AdminShopItem implements ConfigurationSerializable {
    @Getter
    private ItemStack item;

    private String itemName;

    @Getter
    private int price;


    public AdminShopItem(String name, int price) {
        this.itemName = name;
        item = TitanAsh.plugin.getItemProvider().getItem("titan_ash", name);
        this.price = price;
    }

    @Override
    public @NotNull Map<String, Object> serialize() {
        Map<String, Object> result = new HashMap<>();
        result.put("item", itemName);
        result.put("buy_price", price);
        return result;
    }

    public static AdminShopItem deserialize(Map<String, Object> map) {
        return new AdminShopItem(
                (String) map.get("item"),
                (int) map.get("buy_price")
        );
    }

    public ItemStack getShopPageItem() {
        ItemStack item = this.item.clone();
        ItemMeta meta = item.getItemMeta();

        if (!meta.hasLore()) {
            meta.lore(List.of(Component.text("")));
        }

        List<Component> newLore = meta.lore();
        newLore.add(Component.text("Cena: " + price + "x Proch Tytanów"));
        newLore.add(Component.text("Kliknij, aby kupić!"));
        newLore.add(Component.text(""));
        meta.lore(newLore);
        item.setItemMeta(meta);
        return item;
    }
}
