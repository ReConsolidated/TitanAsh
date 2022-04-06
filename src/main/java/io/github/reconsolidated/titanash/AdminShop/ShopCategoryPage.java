package io.github.reconsolidated.titanash.AdminShop;

import io.github.reconsolidated.titanash.CustomInventory.ClickOnlyItem;
import io.github.reconsolidated.titanash.CustomInventory.InventoryMenu;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class ShopCategoryPage extends InventoryMenu {
    private static final int ITEMS_ON_PAGE = 28;


    public ShopCategoryPage(Plugin plugin, Player player, int page) {
        super(plugin, player, getNiceCategoryName(), 6);
        fillWithEmptyItems();

        List<AdminShopItem> items = getCategoryItems(page);
        int row = 2;
        int column = 2;
        for (AdminShopItem item : items) {
            addItem(new ClickOnlyItem(item.getShopPageItem(), row, column, (event) -> {
                // open quantity picking menu
                BuyMethods.onBuy(player, item, 1);
            }));
            column++;
            if (column == 9) {
                column = 2;
                row++;
            }
        }
    }

    private List<AdminShopItem> getCategoryItems(int page) {
        List<AdminShopItem> allItems;
        plugin.reloadConfig(); // todo remove this line on production
        if (plugin.getConfig().contains("shop.titan_ash")) {
            allItems = (List<AdminShopItem>) plugin.getConfig().getList("shop.titan_ash");
        } else {
            allItems = new ArrayList<>();
            allItems.add(new AdminShopItem("BEDROCK", 10));
            allItems.add(new AdminShopItem("WHEAT", 5));
            plugin.getConfig().set("shop.titan_ash", allItems);
            plugin.saveConfig();
        }
        int startingIndex = (page-1) * ITEMS_ON_PAGE;
        int endingIndex = page * ITEMS_ON_PAGE - 1;
        if (startingIndex >= allItems.size()) {
            player.sendMessage(ChatColor.RED + "[You shouldn't see this] Not enough items to open this page.");
            return new ArrayList<>();
        } else {
            List<AdminShopItem> result = new ArrayList<>();
            for (int i = startingIndex; i<=Math.min(endingIndex, allItems.size()-1); i++) {
                result.add(allItems.get(i));
            }
            return result;
        }
    }

    private static String getNiceCategoryName() {
        return "Sklep Tytanów";
    }


}
