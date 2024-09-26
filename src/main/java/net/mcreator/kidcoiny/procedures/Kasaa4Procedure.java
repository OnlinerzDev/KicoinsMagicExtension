package net.mcreator.kidcoiny.procedures;

import java.util.concurrent.atomic.AtomicReference;
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

public class Kasaa4Procedure {
   public static void execute(LevelAccessor world, Entity entity) {
      if (entity instanceof Player player) {
         double amountOfItemNeeded = 0.0D;
         boolean itemBought = false;

         // Check if the player has at least 1 KIDCOIN_1
         ItemStack kidcoinStack = new ItemStack(KidcoinyModItems.KIDCOIN_10.get());
         int countItem = 0;
         for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack stackInSlot = player.getInventory().getItem(i);
            if (stackInSlot.getItem() == KidcoinyModItems.KIDCOIN_10.get()) {
               countItem = countItem + stackInSlot.getCount();
               AtomicReference<IItemHandler> itemHandlerRef = new AtomicReference<>();
               entity.getCapability(ForgeCapabilities.ITEM_HANDLER, Direction.NORTH).ifPresent(itemHandlerRef::set);
            }
         }

         if (countItem >= 1.0D) {
            itemBought = true;
            removeItemFromPlayer(player, kidcoinStack, 1);

            // Give the player 10 APPLE items
            ItemStack appleStack = new ItemStack(Items.GOLD_INGOT, 32);
            ItemHandlerHelper.giveItemToPlayer(player, appleStack);
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
