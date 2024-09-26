package net.mcreator.kidcoiny.procedures;

import java.util.concurrent.atomic.AtomicReference;
import net.mcreator.kidcoiny.init.KidcoinyModItems;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;

public class Kantorgui2Procedure {
   public static void execute(LevelAccessor world, Entity entity) {
      if (entity instanceof Player player) {
         double amountOfItemNeeded = 0.0D;
         boolean itemBought = false;

         // Check if the player has the required item (KIDCOIN_10)
         ItemStack kidCoin10Stack = new ItemStack(KidcoinyModItems.KIDCOIN_10.get());
         if (player.getInventory().contains(kidCoin10Stack)) {
            AtomicReference<IItemHandler> itemHandlerRef = new AtomicReference<>();
            entity.getCapability(ForgeCapabilities.ITEM_HANDLER, null).ifPresent(itemHandlerRef::set);

            if (itemHandlerRef.get() != null) {
               IItemHandler itemHandler = itemHandlerRef.get();

               // Iterate through the player's inventory to calculate the total amount of KIDCOIN_10
               for (int idx = 0; idx < itemHandler.getSlots(); idx++) {
                  ItemStack itemStackInSlot = itemHandler.getStackInSlot(idx);

                  if (itemStackInSlot.getItem() == KidcoinyModItems.KIDCOIN_10.get()) {
                     amountOfItemNeeded += itemStackInSlot.getCount();
                  }

                  // If the player has enough KIDCOIN_10, remove them and give the new item
                  if (amountOfItemNeeded >= 100.0D && !itemBought) {
                     itemBought = true;

                     // Remove 100 KIDCOIN_10 from the player's inventory manually
                     removeItemFromPlayer(player, kidCoin10Stack, 100);

                     // Give the player 1 KIDCOIN_1000 item
                     ItemStack newStack = new ItemStack(KidcoinyModItems.KIDCOIN_1000.get(), 1);
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
