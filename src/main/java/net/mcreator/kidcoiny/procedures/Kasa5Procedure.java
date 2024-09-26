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

public class Kasa5Procedure {
   public static void execute(LevelAccessor world, Entity entity) {
      if (entity instanceof Player player) {
         double amountOfItemNeeded = 0.0D;
         boolean itemBought = false;

         // Check if the player has the required item (EMERALD)
         ItemStack emeraldStack = new ItemStack(Items.GREEN_DYE);
         if (player.getInventory().contains(emeraldStack)) {
            AtomicReference<IItemHandler> itemHandlerRef = new AtomicReference<>();
            entity.getCapability(ForgeCapabilities.ITEM_HANDLER, null).ifPresent(itemHandlerRef::set);

            if (itemHandlerRef.get() != null) {
               IItemHandler itemHandler = itemHandlerRef.get();

               // Iterate through the player's inventory to calculate the total amount of EMERALD
               for (int idx = 0; idx < itemHandler.getSlots(); idx++) {
                  ItemStack itemStackInSlot = itemHandler.getStackInSlot(idx);

                  if (itemStackInSlot.getItem() == Items.GREEN_DYE) {
                     amountOfItemNeeded += itemStackInSlot.getCount();
                  }

                  // If the player has enough EMERALDS, remove them and give the new item (KIDCOIN_10)
                  if (amountOfItemNeeded >= 64.0D && !itemBought) {
                     itemBought = true;

                     // Remove 1 EMERALD from the player's inventory
                     removeItemFromPlayer(player, emeraldStack, 64);

                     // Give the player 5 KIDCOIN_10 items
                     ItemStack newStack = new ItemStack(KidcoinyModItems.KIDCOIN_1.get(), 1);
                     ItemHandlerHelper.giveItemToPlayer(player, newStack);
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
