package io.github.reconsolidated.titanash;

import io.github.reconsolidated.titanash.AdminShop.ShopCategoryPage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TitanShopCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                new ShopCategoryPage(TitanAsh.plugin, player, 1);
            }
        }


        return true;
    }
}
