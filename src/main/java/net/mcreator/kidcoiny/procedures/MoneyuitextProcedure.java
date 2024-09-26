package net.mcreator.kidcoiny.procedures;

import net.mcreator.kidcoiny.init.KidcoinyModItems;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraft.core.Direction;

import java.util.concurrent.atomic.AtomicReference;

public class MoneyuitextProcedure {

   // Methode als "public" deklariert, damit andere Klassen darauf zugreifen können
   public static String execute(Entity entity) {
      if (entity == null) {
         return "";
      } else {
         double totalCoins = 0.0D;

         // Iteriere durch die Slots 0 bis 40 und summiere die Kidcoins
         for (int i = 0; i <= 40; i++) {
            ItemStack itemStack = getItemStack(i, entity);
            if (itemStack.getItem() == KidcoinyModItems.KIDCOIN_1000.get()) {
               totalCoins += itemStack.getCount() * 1000; // Wert pro Coin multiplizieren
            }
         }

         // Rückgabe des Gesamtwerts als String mit "$"
         return totalCoins + "$";
      }
   }

   // Methode, um einen ItemStack von einem Slot zu bekommen
   private static ItemStack getItemStack(int sltid, Entity entity) {
      AtomicReference<ItemStack> _retval = new AtomicReference<>(ItemStack.EMPTY);
      entity.getCapability(ForgeCapabilities.ITEM_HANDLER, Direction.NORTH).ifPresent((capability) -> {
         _retval.set(capability.getStackInSlot(sltid).copy());
      });
      return _retval.get();
   }
}
