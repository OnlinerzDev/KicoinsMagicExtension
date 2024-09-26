package net.mcreator.kidcoiny.init;

import net.mcreator.kidcoiny.world.inventory.*;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class KidcoinyModMenus {
   public static final DeferredRegister<MenuType<?>> REGISTRY;
   public static final RegistryObject<MenuType<PortfelGUIMenu>> PORTFEL_GUI;
   public static final RegistryObject<MenuType<KantorguiMenu>> KANTORGUI;
   public static final RegistryObject<MenuType<KasaGuiMenu>> KASA_GUI;
   public static final RegistryObject<MenuType<KasaGui2Menu>> KASA_GUI_2;
   public static final RegistryObject<MenuType<KasaGui3Menu>> KASA_GUI_3;

   static {
      REGISTRY = DeferredRegister.create(ForgeRegistries.MENU_TYPES, "kidcoiny");
      PORTFEL_GUI = REGISTRY.register("portfel_gui", () -> {
         return IForgeMenuType.create(PortfelGUIMenu::new);
      });
      KANTORGUI = REGISTRY.register("kantorgui", () -> {
         return IForgeMenuType.create(KantorguiMenu::new);
      });
      KASA_GUI = REGISTRY.register("kasa_gui", () -> {
         return IForgeMenuType.create(KasaGuiMenu::new);
      });
      KASA_GUI_2 = REGISTRY.register("kasa_gui_2", () -> {
         return IForgeMenuType.create(KasaGui2Menu::new);
      });
      KASA_GUI_3 = REGISTRY.register("kasa_gui_3", () -> {
         return IForgeMenuType.create(KasaGui3Menu::new);
      });
   }
}
