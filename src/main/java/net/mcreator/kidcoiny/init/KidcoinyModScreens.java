package net.mcreator.kidcoiny.init;

import net.mcreator.kidcoiny.client.gui.*;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(
        bus = Bus.MOD,
        value = {Dist.CLIENT}
)
public class KidcoinyModScreens {

   @SubscribeEvent
   public static void clientLoad(FMLClientSetupEvent event) {
      event.enqueueWork(() -> {
         MenuScreens.register(KidcoinyModMenus.PORTFEL_GUI.get(), PortfelGUIScreen::new);
         MenuScreens.register(KidcoinyModMenus.KANTORGUI.get(), KantorguiScreen::new);
         MenuScreens.register(KidcoinyModMenus.KASA_GUI.get(), KasaGuiScreen::new);
         MenuScreens.register(KidcoinyModMenus.KASA_GUI_2.get(), KasaGui2Screen::new);
         MenuScreens.register(KidcoinyModMenus.KASA_GUI_3.get(), KasaGui3Screen::new);
      });
   }
}

