package com.hexagram2021.dancing_hoppers.common.inventory;
import com.hexagram2021.dancing_hoppers.common.block.entity.ClassificationHopperBlockEntity;
import com.hexagram2021.dancing_hoppers.common.register.DHMenuTypes;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
public class ClassificationHopperMenu extends AbstractContainerMenu {
    private final ClassificationHopperBlockEntity hopper;
    public ClassificationHopperMenu(int windowId, Inventory inv, ClassificationHopperBlockEntity hopper) {
        super(DHMenuTypes.CLASSIFICATION_HOPPER.get(), windowId);
        this.hopper = hopper;
        this.addSlot(new Slot(hopper, ClassificationHopperBlockEntity.FILTER_SLOT, 26, 20) {
            @Override
            public int getMaxStackSize() { return 1; }
            @Override
            public boolean mayPickup(Player player) { return false; }
            @Override
            public boolean mayPlace(ItemStack stack) { return false; }
        });
        for (int i = 1; i <= ClassificationHopperBlockEntity.STORAGE_COUNT; i++) {
            final int idx = i;
            this.addSlot(new Slot(hopper, idx, 62 + (i-1) * 18, 20));
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                this.addSlot(new Slot(inv, j + i * 9 + 9, 8 + j * 18, i * 18 + 51));
            }
        }
        for (int i = 0; i < 9; i++) {
            this.addSlot(new Slot(inv, i, 8 + i * 18, 109));
        }
    }
    @Override
    public boolean stillValid(Player player) {
        return hopper.stillValid(player);
    }
    @Override
    public void clicked(int slotId, int button, ClickType clickType, Player player) {
        if (slotId == 0 && clickType == ClickType.PICKUP) {
            ItemStack cursor = getCarried();
            if (!cursor.isEmpty()) {
                ItemStack filter = cursor.copy();
                filter.setCount(1);
                hopper.setItem(ClassificationHopperBlockEntity.FILTER_SLOT, filter);
            } else {
                hopper.setItem(ClassificationHopperBlockEntity.FILTER_SLOT, ItemStack.EMPTY);
            }
            broadcastChanges();
            return;
        }
        super.clicked(slotId, button, clickType, player);
    }
    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        Slot slot = slots.get(index);
        if (!slot.hasItem()) return ItemStack.EMPTY;
        ItemStack stack = slot.getItem();
        ItemStack copy = stack.copy();
        int totalSlots = ClassificationHopperBlockEntity.TOTAL_SLOTS;
        if (index < totalSlots) {
            if (!moveItemStackTo(stack, totalSlots, slots.size(), true))
                return ItemStack.EMPTY;
        } else {
            if (!moveItemStackTo(stack, 1, totalSlots, false))
                return ItemStack.EMPTY;
        }
        if (stack.isEmpty()) slot.set(ItemStack.EMPTY);
        else slot.setChanged();
        if (stack.getCount() == copy.getCount()) return ItemStack.EMPTY;
        slot.onTake(player, stack);
        return copy;
    }
}