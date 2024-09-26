package net.mcreator.kidcoiny.init;

import net.mcreator.kidcoiny.block.KantorBlock;
import net.mcreator.kidcoiny.block.KasaBlock;
import net.mcreator.kidcoiny.block.KasaaBlock;
import net.mcreator.kidcoiny.block.KasaaaBlock;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class KidcoinyModBlocks {
   public static final DeferredRegister<Block> REGISTRY;
   public static final RegistryObject<Block> KANTOR;
   public static final RegistryObject<Block> KASA;
   public static final RegistryObject<Block> KASAA;
   public static final RegistryObject<Block> KASAAA;

   static {
      REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCKS, "kidcoiny");
      KANTOR = REGISTRY.register("kantor", () -> {
         return new KantorBlock();
      });
      KASA = REGISTRY.register("kasa", () -> {
         return new KasaBlock();
      });
      KASAA = REGISTRY.register("kasaa", () -> {
         return new KasaaBlock();
      });
      KASAAA = REGISTRY.register("kasaaa", () -> {
         return new KasaaaBlock();
      });
   }
}
