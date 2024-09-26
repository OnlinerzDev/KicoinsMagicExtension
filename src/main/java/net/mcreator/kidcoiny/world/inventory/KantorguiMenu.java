package net.mcreator.kidcoiny.world.inventory;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import net.mcreator.kidcoiny.init.KidcoinyModMenus;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class KantorguiMenu extends AbstractContainerMenu implements Supplier<Map<Integer, Slot>> {
   public static final HashMap<String, Object> guistate = new HashMap<>();
   public final Level world;
   public final Player entity;
   public int x, y, z;
   private IItemHandler internal;
   private final Map<Integer, Slot> customSlots = new HashMap<>();
   private boolean bound = false;

   public KantorguiMenu(int id, Inventory inv, FriendlyByteBuf extraData) {
      super(KidcoinyModMenus.KANTORGUI.get(), id);
      this.entity = inv.player;
      this.world = inv.player.level;
      this.internal = new ItemStackHandler(0);
      BlockPos pos = null;
      if (extraData != null) {
         pos = extraData.readBlockPos();
         this.x = pos.getX();
         this.y = pos.getY();
         this.z = pos.getZ();
      }

      if (pos != null) {
         if (extraData.readableBytes() == 1) {
            byte hand = extraData.readByte();
            ItemStack itemstack = hand == 0 ? this.entity.getMainHandItem() : this.entity.getOffhandItem();

            itemstack.getCapability(ForgeCapabilities.ITEM_HANDLER, null).ifPresent(capability -> {
               this.internal = capability;
               this.bound = true;
            });
         } else if (extraData.readableBytes() > 1) {
            extraData.readByte();
            Entity entity = this.world.getEntity(extraData.readVarInt());
            if (entity != null) {
               entity.getCapability(ForgeCapabilities.ITEM_HANDLER, null).ifPresent(capability -> {
                  this.internal = capability;
                  this.bound = true;
               });
            }
         } else {
            BlockEntity ent = inv.player != null ? inv.player.level.getBlockEntity(pos) : null;
            if (ent != null) {
               ent.getCapability(ForgeCapabilities.ITEM_HANDLER, null).ifPresent(capability -> {
                  this.internal = capability;
                  this.bound = true;
               });
            }
         }
      }

      for (int si = 0; si < 3; si++) {
         for (int sj = 0; sj < 9; sj++) {
            this.addSlot(new Slot(inv, sj + (si + 1) * 9, 8 + sj * 18, 84 + si * 18));
         }
      }

      for (int si = 0; si < 9; si++) {
         this.addSlot(new Slot(inv, si, 8 + si * 18, 142));
      }
   }

   @Override
   public boolean stillValid(Player player) {
      return true;
   }

   @Override
   public ItemStack quickMoveStack(Player playerIn, int index) {
      ItemStack itemstack = ItemStack.EMPTY;
      Slot slot = this.slots.get(index);
      if (slot != null && slot.hasItem()) {
         ItemStack itemstack1 = slot.getItem();
         itemstack = itemstack1.copy();
         if (index < 0) {
            if (!this.moveItemStackTo(itemstack1, 0, this.slots.size(), true)) {
               return ItemStack.EMPTY;
            }
            slot.onQuickCraft(itemstack1, itemstack);
         } else if (!this.moveItemStackTo(itemstack1, 0, 0, false)) {
            if (index < 27) {
               if (!this.moveItemStackTo(itemstack1, 27, this.slots.size(), true)) {
                  return ItemStack.EMPTY;
               }
            } else if (!this.moveItemStackTo(itemstack1, 0, 27, false)) {
               return ItemStack.EMPTY;
            }
            return ItemStack.EMPTY;
         }

         if (itemstack1.isEmpty()) {
            slot.set(ItemStack.EMPTY);
         } else {
            slot.setChanged();
         }

         if (itemstack1.getCount() == itemstack.getCount()) {
            return ItemStack.EMPTY;
         }

         slot.onTake(playerIn, itemstack1);
      }

      return itemstack;
   }

   @Override
   protected boolean moveItemStackTo(ItemStack stack, int startIndex, int endIndex, boolean reverseDirection) {
      boolean flag = false;
      int i = startIndex;
      if (reverseDirection) {
         i = endIndex - 1;
      }

      Slot slot;
      ItemStack itemstack;
      if (stack.isStackable()) {
         while (!stack.isEmpty()) {
            if (reverseDirection) {
               if (i < startIndex) {
                  break;
               }
            } else if (i >= endIndex) {
               break;
            }

            slot = this.slots.get(i);
            itemstack = slot.getItem();

            if (!itemstack.isEmpty() && ItemStack.isSameItemSameTags(stack, itemstack)) {
               int combinedSize = itemstack.getCount() + stack.getCount();
               int maxSize = Math.min(slot.getMaxStackSize(), stack.getMaxStackSize());
               if (combinedSize <= maxSize) {
                  stack.setCount(0);
                  itemstack.setCount(combinedSize);
                  slot.set(itemstack);
                  flag = true;
               } else if (itemstack.getCount() < maxSize) {
                  stack.shrink(maxSize - itemstack.getCount());
                  itemstack.setCount(maxSize);
                  slot.set(itemstack);
                  flag = true;
               }
            }

            if (reverseDirection) {
               --i;
            } else {
               ++i;
            }
         }
      }

      if (!stack.isEmpty()) {
         if (reverseDirection) {
            i = endIndex - 1;
         } else {
            i = startIndex;
         }

         while (true) {
            if (reverseDirection) {
               if (i < startIndex) {
                  break;
               }
            } else if (i >= endIndex) {
               break;
            }

            slot = this.slots.get(i);
            itemstack = slot.getItem();

            if (itemstack.isEmpty() && slot.mayPlace(stack)) {
               if (stack.getCount() > slot.getMaxStackSize()) {
                  slot.set(stack.split(slot.getMaxStackSize()));
               } else {
                  slot.set(stack.split(stack.getCount()));
               }

               slot.setChanged();
               flag = true;
               break;
            }

            if (reverseDirection) {
               --i;
            } else {
               ++i;
            }
         }
      }

      return flag;
   }

   @Override
   public void removed(Player playerIn) {
      super.removed(playerIn);
      if (!this.bound && playerIn instanceof ServerPlayer) {
         ServerPlayer serverPlayer = (ServerPlayer) playerIn;
         if (!serverPlayer.hasDisconnected() && !serverPlayer.isSpectator()) {
            for (int j = 0; j < this.internal.getSlots(); ++j) {
               playerIn.getInventory().placeItemBackInInventory(this.internal.extractItem(j, this.internal.getStackInSlot(j).getCount(), false));
            }
         }
      }
   }

   @Override
   public Map<Integer, Slot> get() {
      return this.customSlots;
   }
}
