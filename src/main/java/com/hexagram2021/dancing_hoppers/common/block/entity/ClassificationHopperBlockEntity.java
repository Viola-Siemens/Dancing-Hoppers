package com.hexagram2021.dancing_hoppers.common.block.entity;
import com.hexagram2021.dancing_hoppers.common.block.IFacing;
import com.hexagram2021.dancing_hoppers.common.inventory.ClassificationHopperMenu;
import com.hexagram2021.dancing_hoppers.common.register.DHBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.HopperBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.Hopper;
import net.minecraft.world.level.block.entity.HopperBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import javax.annotation.Nullable;
import java.util.List;
public class ClassificationHopperBlockEntity extends BlockEntity implements Hopper, WorldlyContainer, MenuProvider, IHopperBlockEntity {
    public static final int FILTER_SLOT = 0;
    public static final int STORAGE_COUNT = 5;
    public static final int TOTAL_SLOTS = 6;
    private NonNullList<ItemStack> items = NonNullList.withSize(TOTAL_SLOTS, ItemStack.EMPTY);
    private int coolDown = 0;
    private Component customName;
    public ClassificationHopperBlockEntity(BlockPos pos, BlockState state) {
        super(DHBlockEntities.CLASSIFICATION_HOPPER.get(), pos, state);
    }
    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        this.items = NonNullList.withSize(TOTAL_SLOTS, ItemStack.EMPTY);
        ContainerHelper.loadAllItems(tag, this.items);
        this.coolDown = tag.getInt("CoolDown");
        if (tag.contains("CustomName", 8)) {
            this.customName = Component.Serializer.fromJson(tag.getString("CustomName"));
        }
    }
    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        ContainerHelper.saveAllItems(tag, this.items);
        tag.putInt("CoolDown", this.coolDown);
        if (this.customName != null) {
            tag.putString("CustomName", Component.Serializer.toJson(this.customName));
        }
    }
    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = super.getUpdateTag();
        saveAdditional(tag);
        return tag;
    }
    public void setCustomName(Component name) {
        this.customName = name;
    }
    @Override
    public Component getDisplayName() {
        return this.customName != null ? this.customName : Component.translatable("container.dancing_hoppers.classification_hopper");
    }
    @Override
    public int getContainerSize() { return TOTAL_SLOTS; }
    @Override
    public boolean isEmpty() {
        for (int i = 1; i < TOTAL_SLOTS; i++) {
            if (!items.get(i).isEmpty()) return false;
        }
        return true;
    }
    @Override
    public ItemStack getItem(int index) { return items.get(index); }
    @Override
    public ItemStack removeItem(int index, int count) {
        if (index == FILTER_SLOT) return ItemStack.EMPTY;
        return ContainerHelper.removeItem(items, index, count);
    }
    @Override
    public ItemStack removeItemNoUpdate(int index) {
        if (index == FILTER_SLOT) return ItemStack.EMPTY;
        ItemStack stack = items.get(index);
        items.set(index, ItemStack.EMPTY);
        return stack;
    }
    @Override
    public void setItem(int index, ItemStack stack) {
        items.set(index, stack);
        if (stack.getCount() > getMaxStackSize()) stack.setCount(getMaxStackSize());
        setChanged();
    }
    @Override
    public boolean stillValid(Player player) {
        return level != null && level.getBlockEntity(worldPosition) == this &&
                player.distanceToSqr(worldPosition.getX() + 0.5, worldPosition.getY() + 0.5, worldPosition.getZ() + 0.5) <= 64.0;
    }
    @Override
    public void clearContent() {
        items.clear();
        setChanged();
    }
    @Override
    public boolean canTakeItem(Container source, int slot, ItemStack stack) {
        return slot != FILTER_SLOT;
    }
    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inv, Player player) {
        return new ClassificationHopperMenu(id, inv, this);
    }
    public static void entityInside(Level level, BlockPos pos, BlockState state, Entity entity, ClassificationHopperBlockEntity hopper) {
        if (entity instanceof ItemEntity && Shapes.joinIsNotEmpty(Shapes.create(entity.getBoundingBox().move(-pos.getX(), -pos.getY(), -pos.getZ())), hopper.getSuckShape(), BooleanOp.AND)) {
            hopper.serverTick(level, pos, state);
        }
    }
    public void serverTick(Level level, BlockPos pos, BlockState state) {
        if (coolDown > 0) {
            coolDown--;
            return;
        }
        boolean changed = false;
        if (!isEmpty()) changed = pushItems(level, pos, state);
        if (!changed && !isStorageFull()) changed = suckItems(level);
        if (changed) {
            coolDown = 8;
            setChanged();
            level.blockEntityChanged(pos);
        }
    }
    private boolean isStorageFull() {
        for (int i = 1; i < TOTAL_SLOTS; i++) {
            ItemStack stack = items.get(i);
            if (stack.isEmpty() || stack.getCount() < stack.getMaxStackSize()) return false;
        }
        return true;
    }
    protected Direction getOutputDirection(BlockState state) {
        if (state.getBlock() instanceof IFacing facing) {
            return facing.getFacing(state);
        }
        return state.getValue(HopperBlock.FACING);
    }
    private boolean pushItems(Level level, BlockPos pos, BlockState state) {
        Direction outDir = getOutputDirection(state);
        Container target = HopperBlockEntity.getContainerAt(level, pos.relative(outDir));
        if (target == null) return false;
        for (int i = 1; i < TOTAL_SLOTS; i++) {
            ItemStack stack = items.get(i);
            if (stack.isEmpty()) continue;
            ItemStack toTransfer = stack.copy();
            toTransfer.setCount(1);
            ItemStack remaining = tryAddToContainer(target, toTransfer);
            if (remaining.isEmpty()) {
                stack.shrink(1);
                setChanged();
                if (stack.isEmpty()) setItem(i, ItemStack.EMPTY);
                return true;
            }
        }
        return false;
    }
    private ItemStack tryAddToContainer(Container target, ItemStack stack) {
        if (stack.isEmpty()) return ItemStack.EMPTY;
        ItemStack remaining = stack.copy();
        for (int slot = 0; slot < target.getContainerSize(); slot++) {
            ItemStack targetStack = target.getItem(slot);
            if (!targetStack.isEmpty() && isSameItemAndName(targetStack, remaining)) {
                int maxAdd = targetStack.getMaxStackSize() - targetStack.getCount();
                if (maxAdd > 0) {
                    int add = Math.min(remaining.getCount(), maxAdd);
                    targetStack.grow(add);
                    remaining.shrink(add);
                    target.setChanged();
                    if (remaining.isEmpty()) return ItemStack.EMPTY;
                }
            }
        }
        for (int slot = 0; slot < target.getContainerSize(); slot++) {
            if (target.getItem(slot).isEmpty() && target.canPlaceItem(slot, remaining)) {
                target.setItem(slot, remaining.copy());
                target.setChanged();
                return ItemStack.EMPTY;
            }
        }
        return remaining;
    }
    @Nullable
    @Override
    public Container getSourceContainer(Level level) {
        return HopperBlockEntity.getContainerAt(level, getLevelX(), getLevelY() + 1, getLevelZ());
    }
    @Override
    public Direction getSourceContainerOutputDirection() {
        return Direction.DOWN;
    }
    private boolean suckItems(Level level) {
        Container source = getSourceContainer(level);
        if (source != null) {
            for (int slot = 0; slot < source.getContainerSize(); slot++) {
                ItemStack stack = source.getItem(slot);
                if (stack.isEmpty()) continue;
                if (!canAcceptItem(stack)) continue;
                ItemStack toTransfer = stack.copy();
                toTransfer.setCount(1);
                ItemStack remaining = tryAddToSelf(toTransfer);
                if (remaining.isEmpty()) {
                    source.removeItem(slot, 1);
                    source.setChanged();
                    return true;
                }
            }
            return false;
        }
        List<ItemEntity> entities = level.getEntitiesOfClass(ItemEntity.class,
                new AABB(getLevelX() - 0.5, getLevelY() - 0.5, getLevelZ() - 0.5,
                        getLevelX() + 0.5, getLevelY() + 0.5, getLevelZ() + 0.5),
                Entity::isAlive);
        for (ItemEntity item : entities) {
            ItemStack stack = item.getItem();
            if (!canAcceptItem(stack)) continue;
            ItemStack remaining = insertIntoStorage(stack);
            if (remaining.isEmpty()) {
                item.discard();
                return true;
            } else if (remaining.getCount() != stack.getCount()) {
                item.setItem(remaining);
                return true;
            }
        }
        return false;
    }
    private ItemStack tryAddToSelf(ItemStack stack) {
        if (stack.isEmpty()) return ItemStack.EMPTY;
        ItemStack remaining = stack.copy();
        for (int i = 1; i < TOTAL_SLOTS; i++) {
            ItemStack slotStack = items.get(i);
            if (!slotStack.isEmpty() && isSameItemAndName(slotStack, remaining)) {
                int maxAdd = slotStack.getMaxStackSize() - slotStack.getCount();
                if (maxAdd > 0) {
                    int add = Math.min(remaining.getCount(), maxAdd);
                    slotStack.grow(add);
                    remaining.shrink(add);
                    setChanged();
                    if (remaining.isEmpty()) return ItemStack.EMPTY;
                }
            }
        }
        for (int i = 1; i < TOTAL_SLOTS; i++) {
            if (items.get(i).isEmpty()) {
                items.set(i, remaining.copy());
                setChanged();
                return ItemStack.EMPTY;
            }
        }
        return remaining;
    }
    private static boolean isSameItemAndName(ItemStack a, ItemStack b) {
        if (a.isEmpty() || b.isEmpty()) return false;
        if (a.getItem() != b.getItem()) return false;
        String nameA = a.getDisplayName().getString();
        String nameB = b.getDisplayName().getString();
        return nameA.equals(nameB);
    }
    private ItemStack insertIntoStorage(ItemStack stack) {
        return tryAddToSelf(stack);
    }
    private boolean canAcceptItem(ItemStack stack) {
        ItemStack filter = items.get(FILTER_SLOT);
        return filter.isEmpty() || isSameItemAndName(filter, stack);
    }
    @Override
    public int[] getSlotsForFace(Direction side) {
        int[] slots = new int[STORAGE_COUNT];
        for (int i = 0; i < STORAGE_COUNT; i++) slots[i] = i + 1;
        return slots;
    }
    @Override
    public boolean canPlaceItemThroughFace(int index, ItemStack stack, @Nullable Direction direction) {
        if (direction != null && direction != Direction.UP) return false;
        if (index < 1 || index >= TOTAL_SLOTS) return false;
        return canAcceptItem(stack);
    }
    @Override
    public boolean canTakeItemThroughFace(int index, ItemStack stack, Direction direction) {
        return direction == Direction.DOWN && index >= 1 && index < TOTAL_SLOTS;
    }
    @Override public double getLevelX() { return worldPosition.getX() + 0.5; }
    @Override public double getLevelY() { return worldPosition.getY() + 0.5; }
    @Override public double getLevelZ() { return worldPosition.getZ() + 0.5; }
}