package net.mcreator.kidcoiny.procedures;

import net.mcreator.kidcoiny.init.KidcoinyModItems;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;

public class Kasaaa5Procedure {

    public static void execute(LevelAccessor world, Entity entity) {
        if (entity instanceof Player player) {
            double amountOfItemNeeded = 0.0D;
            boolean itemBought = false;

            // Check if the player has at least 1 KIDCOIN_1
            ItemStack scrap1 = new ItemStack(KidcoinyModItems.KIDCOIN_ZIELONY_SCRAP1.get());
            ItemStack scrap2 = new ItemStack(KidcoinyModItems.KIDCOIN_ZIELONY_SCRAP2.get());
            ItemStack scrap3 = new ItemStack(KidcoinyModItems.KIDCOIN_ZIELONY_SCRAP3.get());
            ItemStack scrap4 = new ItemStack(KidcoinyModItems.KIDCOIN_ZIELONY_SCRAP4.get());

            if (player.getInventory().contains(scrap1)) {
                if (player.getInventory().contains(scrap2)) {
                    if (player.getInventory().contains(scrap3)) {
                        if (player.getInventory().contains(scrap4)) {
                            if(!itemBought) {
                                itemBought = true;
                                removeItemFromPlayer(player, scrap1, 1);
                                removeItemFromPlayer(player, scrap2, 1);
                                removeItemFromPlayer(player, scrap3, 1);
                                removeItemFromPlayer(player, scrap4, 1);

                                // Give the player 10 APPLE items
                                ItemStack appleStack = new ItemStack(KidcoinyModItems.KIDCOIN_ZIELONY.get(), 1);
                                ItemHandlerHelper.giveItemToPlayer(player, appleStack);
                            }
                        }
                    }
                }
            }
        }
    }

    private static void removeItemFromPlayer(Player player, ItemStack itemToRemove, int count) {
        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack stackInSlot = player.getInventory().getItem(i);

            if (stackInSlot.getItem() == itemToRemove.getItem()) {
                int removeCount = Math.min(count, stackInSlot.getCount());
                stackInSlot.shrink(removeCount);
                count -= removeCount;

                if (count <= 0) {
                    break;
                }
            }
        }
    }
}
