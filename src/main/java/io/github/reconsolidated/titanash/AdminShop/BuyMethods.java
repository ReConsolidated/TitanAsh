package io.github.reconsolidated.titanash.AdminShop;


import io.github.reconsolidated.titanash.ColorHelper;
import io.github.reconsolidated.titanash.TitanAsh;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;


public class BuyMethods {

    public static void onBuy(Player player, AdminShopItem item, int amount) {
        int space = getFreeInventorySpace(player.getInventory());
        int neededSpace = amount / item.getItem().getMaxStackSize();

        int balance = countPlayerAsh(player);
        int price = amount * item.getPrice();

        if (balance < price) {
            player.sendMessage(ColorHelper.translate("&cNie masz wystarczająco dużo" +
                    " &#e126fb&lP&#da2dfb&lr&#d234fb&lo&#ca3bfc&lc&#c242fc&lh&#bb49fc&lu " +
                    "&#b351fc&lT&#ab58fc&ly&#a45ffc&lt&#9c66fc&la&#946dfd&ln&#8c74fd&ló&#857bfd&lw" +
                    "&c, aby kupić ten item."));
            return;
        }

        if (neededSpace > space) {
            player.sendMessage(ChatColor.RED + "Musisz mieć więcej miejsca w ekwipunku!");
            return;
        }

        removePlayerAsh(player, price);
        item.getItem().setAmount(1);
        for (int i = 0; i<amount; i++) {
            player.getInventory().addItem(item.getItem());
        }
        player.sendMessage(ChatColor.GREEN + "Zakupiono przedmiot!");
    }

    private static void removePlayerAsh(Player player, int amount) {
        int amountToRemove = amount;
        for (ItemStack slot : player.getInventory().getStorageContents()) {
            if (slot != null) {
                if (amountToRemove > 0 && TitanAsh.isTitanAsh(slot)) {
                    if (slot.getAmount() > amountToRemove) {
                        slot.setAmount(slot.getAmount() - amountToRemove);
                        amountToRemove = 0;
                    } else {
                        amountToRemove -= slot.getAmount();
                        slot.setAmount(0);
                    }
                }
            }
        }
    }

    private static int countPlayerAsh(Player player) {
        int counter = 0;
        for (ItemStack slot : player.getInventory().getContents()) {
            if (slot != null) {
                if (TitanAsh.isTitanAsh(slot)) {
                    counter+=slot.getAmount();
                }
            }
        }
        return counter;
    }


    public static int getFreeInventorySpace(Inventory inventory) {
        int space = 0;
        for (ItemStack item : inventory.getStorageContents()) {
            if (item == null || item.getType().equals(Material.AIR)) {
                space++;
            }
        }
        return space;
    }


}
