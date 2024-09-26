package net.mcreator.kidcoiny.init;

import net.mcreator.kidcoiny.item.*;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class KidcoinyModItems {
   public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, "kidcoiny");

   public static final RegistryObject<Item> KIDCOIN_10000 = REGISTRY.register("kidcoin_10000", Kidcoin1000Item::new);
   public static final RegistryObject<Item> KIDCOIN_1000 = REGISTRY.register("kidcoin_1000", Kidcoin100Item::new);
   public static final RegistryObject<Item> KIDCOIN_10 = REGISTRY.register("kidcoin_10", Kidcoin10Item::new);
   public static final RegistryObject<Item> KIDCOIN_1 = REGISTRY.register("kidcoin_1", Kidcoin1Item::new);
   public static final RegistryObject<Item> KIDCOIN_ZIELONY_SCRAP1 = REGISTRY.register("kidcoin_zielony_scrap1", KidcoinZielonyScrap1Item::new);
   public static final RegistryObject<Item> KIDCOIN_ZIELONY_SCRAP2 = REGISTRY.register("kidcoin_zielony_scrap2", KidcoinZielonyScrap2Item::new);
   public static final RegistryObject<Item> KIDCOIN_ZIELONY_SCRAP3 = REGISTRY.register("kidcoin_zielony_scrap3", KidcoinZielonyScrap3Item::new);
   public static final RegistryObject<Item> KIDCOIN_ZIELONY_SCRAP4 = REGISTRY.register("kidcoin_zielony_scrap4", KidcoinZielonyScrap4Item::new);
   public static final RegistryObject<Item> KIDCOIN_ZIELONY = REGISTRY.register("kidcoin_zielony", KidcoinZielonyItem::new);

   public static final RegistryObject<Item> KANTOR = block(KidcoinyModBlocks.KANTOR, KidcoinyModTabs.TAB_KIDCOINS);
   public static final RegistryObject<Item> PORTFEL = REGISTRY.register("portfel", PortfelItem::new);
   public static final RegistryObject<Item> KASA = block(KidcoinyModBlocks.KASA, KidcoinyModTabs.TAB_KIDCOINS);
   public static final RegistryObject<Item> KASAA = block(KidcoinyModBlocks.KASAA, KidcoinyModTabs.TAB_KIDCOINS);
   public static final RegistryObject<Item> KASAAA = block(KidcoinyModBlocks.KASAAA, KidcoinyModTabs.TAB_KIDCOINS);

   // Methode zum Registrieren von BlockItems
   private static RegistryObject<Item> block(RegistryObject<Block> block, CreativeModeTab tab) {
      return REGISTRY.register(block.getId().getPath(), () -> new BlockItem(block.get(), new Properties().tab(tab)));
   }
}
