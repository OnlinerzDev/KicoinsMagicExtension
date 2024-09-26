package net.mcreator.kidcoiny.item;

import net.mcreator.kidcoiny.init.KidcoinyModTabs;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public class KidcoinZielonyScrap4Item extends Item {
    public KidcoinZielonyScrap4Item() {
        super(new Properties()
                .tab(KidcoinyModTabs.TAB_KIDCOINS)  // Setzt das CreativeModeTab
                .stacksTo(64)                       // Maximale Stapelgröße
                .fireResistant()                    // Setzt das Item als feuerresistent
                .rarity(Rarity.RARE));              // Seltenheit des Items (EPIC)
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public boolean isFoil(ItemStack itemstack) {
        return true;  // Rückgabe, dass das Item einen Leuchteffekt hat (foil)
    }

    @Override
    public void appendHoverText(ItemStack itemstack, Level world, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(itemstack, world, tooltip, flag);
        tooltip.add(Component.translatable("Waluta obowiązująca na Kidowicach"));  // Tooltip hinzufügen
    }
}
