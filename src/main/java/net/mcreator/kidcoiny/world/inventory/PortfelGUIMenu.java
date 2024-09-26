package net.mcreator.kidcoiny.world.inventory;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.mcreator.kidcoiny.init.KidcoinyModMenus;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class PortfelGUIMenu extends AbstractContainerMenu implements Supplier<Map<Integer, Slot>> {
    public static final HashMap<String, Object> guistate = new HashMap<>();
    private final IItemHandler internal;
    private final Player player;
    private final Map<Integer, Slot> customSlots = new HashMap<>();
    private final int x, y, z;

    public PortfelGUIMenu(int id, Inventory playerInventory, FriendlyByteBuf extraData) {
        super(KidcoinyModMenus.PORTFEL_GUI.get(), id);
        this.player = playerInventory.player;
        this.internal = new ItemStackHandler(18); // Portemonnaie mit 18 Slots
        BlockPos pos = extraData.readBlockPos();
        this.x = pos.getX();
        this.y = pos.getY();
        this.z = pos.getZ();

        // Füge Slots für das Portemonnaie hinzu
        for (int slotIndex = 0; slotIndex < 18; slotIndex++) {
            this.customSlots.put(slotIndex, this.addSlot(new SlotItemHandler(this.internal, slotIndex, 7 + (slotIndex % 9) * 18, 26 + (slotIndex / 9) * 18)));
        }

        // Füge Slots für das Spielerinventar hinzu
        setupPlayerInventory(playerInventory);
    }

    private void setupPlayerInventory(Inventory playerInventory) {
        // Slots für das Spieler-Inventar (3x9 Grid)
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                this.addSlot(new Slot(playerInventory, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
            }
        }

        // Slots für die Hotbar (1x9 Grid)
        for (int col = 0; col < 9; col++) {
            this.addSlot(new Slot(playerInventory, col, 8 + col * 18, 142));
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

            if (index < 18) { // Custom Slots (Portfel)
                if (!this.moveItemStackTo(itemstack1, 18, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemstack1, 0, 18, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }

        return itemstack;
    }

    @Override
    public Map<Integer, Slot> get() {
        return customSlots;
    }
}

