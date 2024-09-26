package net.mcreator.kidcoiny.effects;

import net.mcreator.kidcoiny.init.KidcoinyModItems;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class GivePotionEffects {


    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.side.isServer()) {
            if (event.phase == TickEvent.Phase.END) {
                applyEffectsBasedOnZielonyCount(event.player, countZielony(event.player));
            }
        }
    }

    public static void applyEffectsBasedOnZielonyCount(Player player, int zielonyCount) {
        // Entferne alle existierenden Effekte, um Überschreibungen zu gewährleisten
        clearEffects(player);

        if (zielonyCount >= 1) {
            // +60% speed = MOVEMENT_SPEED Level 1 (60% ist der Verstärkungswert 1)
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, Integer.MAX_VALUE, 1, true, false));
        }
        if (zielonyCount >= 2) {
            // +7 Attack Damage = DAMAGE_BOOST Level 6 (+7 Schaden, da Level 6 = +7 Schaden)
            player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, Integer.MAX_VALUE, 6, true, false));
        }
        if (zielonyCount >= 3) {
            // Jump Boost 3 = JUMP Level 2 (Level 2 = Jump Boost 3)
            player.addEffect(new MobEffectInstance(MobEffects.JUMP, Integer.MAX_VALUE, 2, true, false));
        }
        if (zielonyCount >= 4) {
            // Water Breathing = WATER_BREATHING
            player.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, Integer.MAX_VALUE, 0, true, false));
        }
        if (zielonyCount >= 5) {
            // +120% speed = MOVEMENT_SPEED Level 2 (120% Verstärkung)
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, Integer.MAX_VALUE, 2, true, false));
        }
        if (zielonyCount >= 6) {
            // +14 Attack Damage = DAMAGE_BOOST Level 13 (+14 Schaden, da Level 13 = +14 Schaden)
            player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, Integer.MAX_VALUE, 13, true, false));
        }
        if (zielonyCount >= 7) {
            // Jump Boost 6 = JUMP Level 5 (Level 5 = Jump Boost 6)
            player.addEffect(new MobEffectInstance(MobEffects.JUMP, Integer.MAX_VALUE, 5, true, false));
        }
        if (zielonyCount >= 8) {
            // Fire Resistance = FIRE_RESISTANCE
            player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, Integer.MAX_VALUE, 0, true, false));
        }
        if (zielonyCount >= 9) {
            // +180% speed = MOVEMENT_SPEED Level 3 (180% Verstärkung)
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, Integer.MAX_VALUE, 3, true, false));
        }
        if (zielonyCount >= 10) {
            // +21 Attack Damage = DAMAGE_BOOST Level 20 (+21 Schaden, da Level 20 = +21 Schaden)
            player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, Integer.MAX_VALUE, 20, true, false));
        }
    }

    private static int countZielony(Player player) {
        int zielonyCount = 0;
        for (ItemStack itemStack : player.getInventory().items) {
            if (itemStack.getItem() == KidcoinyModItems.KIDCOIN_ZIELONY.get()) {
                zielonyCount += itemStack.getCount();
            }
        }
        return zielonyCount;
    }

    private static void clearEffects(Player player) {
        player.removeEffect(MobEffects.MOVEMENT_SPEED);
        player.removeEffect(MobEffects.DAMAGE_BOOST);
        player.removeEffect(MobEffects.JUMP);
        player.removeEffect(MobEffects.WATER_BREATHING);
        player.removeEffect(MobEffects.FIRE_RESISTANCE);
    }


}
