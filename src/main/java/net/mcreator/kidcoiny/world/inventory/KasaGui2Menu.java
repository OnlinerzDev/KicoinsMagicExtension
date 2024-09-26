package net.mcreator.kidcoiny.world.inventory;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import net.mcreator.kidcoiny.init.KidcoinyModMenus;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class KasaGui2Menu extends AbstractContainerMenu implements Supplier<Map<Integer, Slot>> {
   public static final HashMap<String, Object> guistate = new HashMap<>();
   public final Level world;
   public final Player entity;
   public int x, y, z;
   private IItemHandler internal;
   private final Map<Integer, Slot> customSlots = new HashMap<>();
   private boolean bound = false;

   public KasaGui2Menu(int id, Inventory inv, FriendlyByteBuf extraData) {
      super(KidcoinyModMenus.KASA_GUI_2.get(), id);
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
   }

   @Override
   public boolean stillValid(Player player) {
      return true;
   }

   @Override
   public ItemStack quickMoveStack(Player playerIn, int index) {
      return ItemStack.EMPTY; // No functionality for item movement, can be customized later
   }

   @Override
   public Map<Integer, Slot> get() {
      return this.customSlots;
   }
}
