package net.mcreator.kidcoiny.block.entity;

import io.netty.buffer.Unpooled;

import java.util.stream.IntStream;
import javax.annotation.Nullable;
import net.mcreator.kidcoiny.init.KidcoinyModBlockEntities;
import net.mcreator.kidcoiny.world.inventory.KasaGui2Menu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;

public class KasaaaBlockEntity extends RandomizableContainerBlockEntity implements WorldlyContainer {
   private NonNullList<ItemStack> stacks;
   private final LazyOptional<? extends IItemHandler>[] handlers;

   // Konstruktor
   public KasaaaBlockEntity(BlockPos position, BlockState state) {
      super(KidcoinyModBlockEntities.KASAAA.get(), position, state);
      this.stacks = NonNullList.withSize(13, ItemStack.EMPTY);
      this.handlers = SidedInvWrapper.create(this, Direction.values());
   }

   // Liest die gespeicherten Daten aus dem CompoundTag (z.B. bei Weltneuladen)
   @Override
   public void load(CompoundTag compound) {
      super.load(compound);
      if (!this.tryLoadLootTable(compound)) {
         this.stacks = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
      }
      ContainerHelper.loadAllItems(compound, this.stacks);
   }

   // Speichert die Daten in den CompoundTag (z.B. bei Weltspeicherung)
   @Override
   protected void saveAdditional(CompoundTag compound) {
      super.saveAdditional(compound);
      if (!this.trySaveLootTable(compound)) {
         ContainerHelper.saveAllItems(compound, this.stacks);
      }
   }

   // Netzwerkpaket zur Client-Synchronisation
   @Override
   public ClientboundBlockEntityDataPacket getUpdatePacket() {
      return ClientboundBlockEntityDataPacket.create(this);
   }

   // Gibt den Zustand als CompoundTag zurück
   @Override
   public CompoundTag getUpdateTag() {
      return this.saveWithoutMetadata();
   }

   @Override
   public int getContainerSize() {
      return this.stacks.size();
   }

   @Override
   public boolean isEmpty() {
      for (ItemStack itemstack : this.stacks) {
         if (!itemstack.isEmpty()) {
            return false;
         }
      }
      return true;
   }

   @Override
   public Component getName() {
      return Component.literal("kasaa");
   }

   @Override
   public int getMaxStackSize() {
      return 64;
   }

   @Override
   public AbstractContainerMenu createMenu(int id, Inventory inventory) {
      return new KasaGui2Menu(id, inventory, new FriendlyByteBuf(Unpooled.buffer()).writeBlockPos(this.worldPosition));
   }

   @Override
   public Component getDisplayName() {
      return Component.literal("Kasa sprzedażowa");
   }

   @Override
   protected NonNullList<ItemStack> getItems() {
      return this.stacks;
   }

   @Override
   protected void setItems(NonNullList<ItemStack> stacks) {
      this.stacks = stacks;
   }

   @Override
   public boolean canPlaceItem(int index, ItemStack stack) {
      return true;
   }

   @Override
   public int[] getSlotsForFace(Direction side) {
      return IntStream.range(0, this.getContainerSize()).toArray();
   }

   @Override
   public boolean canPlaceItemThroughFace(int index, ItemStack stack, @Nullable Direction direction) {
      return this.canPlaceItem(index, stack);
   }

   @Override
   public boolean canTakeItemThroughFace(int index, ItemStack stack, Direction direction) {
      return true;
   }

   @Override
   public <T> LazyOptional<T> getCapability(Capability<T> capability, @Nullable Direction facing) {
      if (!this.remove && facing != null && capability == ForgeCapabilities.ITEM_HANDLER) {
         return this.handlers[facing.ordinal()].cast();
      }
      return super.getCapability(capability, facing);
   }

   @Override
   public void invalidateCaps() {
      super.invalidateCaps();
      for (LazyOptional<? extends IItemHandler> handler : this.handlers) {
         handler.invalidate();
      }
   }
   @Override
   protected Component getDefaultName() {
      return Component.literal("kasaaa");
   }
}
