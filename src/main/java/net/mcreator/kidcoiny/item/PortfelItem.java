package net.mcreator.kidcoiny.item;

import io.netty.buffer.Unpooled;
import javax.annotation.Nullable;
import net.mcreator.kidcoiny.init.KidcoinyModTabs;
import net.mcreator.kidcoiny.item.inventory.PortfelInventoryCapability;
import net.mcreator.kidcoiny.world.inventory.PortfelGUIMenu;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.network.NetworkHooks;

public class PortfelItem extends Item {
   public PortfelItem() {
      super(new Properties()
              .tab(KidcoinyModTabs.TAB_KIDCOINS)  // Setzt das CreativeModeTab
              .stacksTo(1)                        // Maximale Stapelgröße
              .rarity(Rarity.COMMON));            // Seltenheit des Items
   }

   @Override
   public InteractionResultHolder<ItemStack> use(Level world, final Player player, final InteractionHand hand) {
      InteractionResultHolder<ItemStack> ar = super.use(world, player, hand);
      ItemStack itemstack = ar.getObject();

      if (player instanceof ServerPlayer serverPlayer) {
         NetworkHooks.openScreen(serverPlayer, new MenuProvider() {
            @Override
            public Component getDisplayName() {
               return Component.translatable("Portfel");
            }

            @Override
            public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
               FriendlyByteBuf packetBuffer = new FriendlyByteBuf(Unpooled.buffer());
               packetBuffer.writeBlockPos(player.blockPosition());
               packetBuffer.writeByte(hand == InteractionHand.MAIN_HAND ? 0 : 1);
               return new PortfelGUIMenu(id, inventory, packetBuffer);
            }
         }, buf -> {
            buf.writeBlockPos(player.blockPosition());
            buf.writeByte(hand == InteractionHand.MAIN_HAND ? 0 : 1);
         });
      }

      return ar;
   }

   @Override
   public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag compound) {
      return new PortfelInventoryCapability();
   }

   @Override
   public CompoundTag getShareTag(ItemStack stack) {
      CompoundTag nbt = super.getShareTag(stack);
      if (nbt != null) {
         stack.getCapability(ForgeCapabilities.ITEM_HANDLER, null).ifPresent(capability -> {
            nbt.put("Inventory", ((ItemStackHandler) capability).serializeNBT());
         });
      }
      return nbt;
   }

   @Override
   public void readShareTag(ItemStack stack, @Nullable CompoundTag nbt) {
      super.readShareTag(stack, nbt);
      if (nbt != null) {
         stack.getCapability(ForgeCapabilities.ITEM_HANDLER, null).ifPresent(capability -> {
            ((ItemStackHandler) capability).deserializeNBT(nbt.getCompound("Inventory"));
         });
      }
   }
}
