package net.mcreator.kidcoiny.init;

import com.mojang.datafixers.types.Type;
import net.mcreator.kidcoiny.block.entity.KantorBlockEntity;
import net.mcreator.kidcoiny.block.entity.KasaBlockEntity;
import net.mcreator.kidcoiny.block.entity.KasaaBlockEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BlockEntityType.BlockEntitySupplier;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class KidcoinyModBlockEntities {
   public static final DeferredRegister<BlockEntityType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, "kidcoiny");

   public static final RegistryObject<BlockEntityType<KantorBlockEntity>> KANTOR = register(
           "kantor",
           KidcoinyModBlocks.KANTOR,
           KantorBlockEntity::new
   );
   public static final RegistryObject<BlockEntityType<KasaBlockEntity>> KASA = register(
           "kasa",
           KidcoinyModBlocks.KASA,
           KasaBlockEntity::new
   );
   public static final RegistryObject<BlockEntityType<KasaaBlockEntity>> KASAA = register(
           "kasaa",
           KidcoinyModBlocks.KASAA,
           KasaaBlockEntity::new
   );
   public static final RegistryObject<BlockEntityType<KasaaBlockEntity>> KASAAA = register(
           "kasaaa",
           KidcoinyModBlocks.KASAAA,
           KasaaBlockEntity::new
   );

   // Methode für das Registrieren von BlockEntities mit spezifischen Typen
   private static <T extends BlockEntity> RegistryObject<BlockEntityType<T>> register(
           String registryName,
           RegistryObject<Block> block,
           BlockEntitySupplier<T> supplier
   ) {
      return REGISTRY.register(registryName, () -> BlockEntityType.Builder.of(supplier, block.get()).build((Type<?>) null));
   }
}
