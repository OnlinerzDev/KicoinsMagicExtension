package net.mcreator.kidcoiny.item;

import java.util.List;
import net.mcreator.kidcoiny.init.KidcoinyModTabs;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;

public class Kidcoin10Item extends Item {
   public Kidcoin10Item() {
      super(new Properties()
              .tab(KidcoinyModTabs.TAB_KIDCOINS)  // Setzt das CreativeModeTab
              .stacksTo(64)                       // Maximale Stapelgröße
              .fireResistant()                    // Setzt das Item als feuerresistent
              .rarity(Rarity.UNCOMMON));          // Seltenheit des Items (UNCOMMON)
   }

   @Override
   public void appendHoverText(ItemStack itemstack, Level world, List<Component> tooltip, TooltipFlag flag) {
      super.appendHoverText(itemstack, world, tooltip, flag);
      tooltip.add(Component.translatable("Waluta obowiązująca na Kidowicach"));  // Tooltip hinzufügen
   }
}

