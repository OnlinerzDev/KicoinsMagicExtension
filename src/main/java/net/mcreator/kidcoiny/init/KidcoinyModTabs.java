package net.mcreator.kidcoiny.init;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

public class KidcoinyModTabs {
   public static CreativeModeTab TAB_KIDCOINS;

   public static void load() {
      TAB_KIDCOINS = new CreativeModeTab("tabkidcoins") {

         // Implementierung der abstrakten Methode 'makeIcon'
         @Override
         public ItemStack makeIcon() {
            return new ItemStack(KidcoinyModItems.KIDCOIN_1000.get());
         }

         // Optional: Methode zum Aktivieren der Suchleiste
         @Override
         public boolean hasSearchBar() {
            return true;
         }
      }.setBackgroundSuffix("item_search.png");
   }
}
