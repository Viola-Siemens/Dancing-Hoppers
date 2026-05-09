package com.hexagram2021.dancing_hoppers.common.block;
import com.hexagram2021.dancing_hoppers.common.block.entity.ClassificationHopperBlockEntity;
import com.hexagram2021.dancing_hoppers.common.register.DHBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.HopperBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import javax.annotation.Nullable;
public class ClassificationHopperBlock extends HopperBlock {
    public ClassificationHopperBlock(Properties props) {
        super(props);
    }
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new ClassificationHopperBlockEntity(pos, state);
    }
    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (level.isClientSide) return InteractionResult.SUCCESS;
        BlockEntity be = level.getBlockEntity(pos);
        if (be instanceof ClassificationHopperBlockEntity hopper) {
            NetworkHooks.openScreen((ServerPlayer) player, hopper, pos);
            player.awardStat(Stats.INSPECT_HOPPER);
        }
        return InteractionResult.CONSUME;
    }
    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        BlockEntity be = level.getBlockEntity(pos);
        if (be instanceof ClassificationHopperBlockEntity hopper) {
            ClassificationHopperBlockEntity.entityInside(level, pos, state, entity, hopper);
        }
    }
    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return level.isClientSide ? null : createTickerHelper(type, DHBlockEntities.CLASSIFICATION_HOPPER.get(), (lvl, pos, st, be) -> be.serverTick(lvl, pos, st));
    }
    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!state.is(newState.getBlock())) {
            BlockEntity be = level.getBlockEntity(pos);
            if (be instanceof ClassificationHopperBlockEntity hopper) {
                for (int i = 1; i <= ClassificationHopperBlockEntity.STORAGE_COUNT; i++) {
                    Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), hopper.getItem(i));
                    hopper.setItem(i, ItemStack.EMPTY);
                }
                ItemStack filter = hopper.getItem(ClassificationHopperBlockEntity.FILTER_SLOT);
                hopper.setItem(ClassificationHopperBlockEntity.FILTER_SLOT, ItemStack.EMPTY);
                super.onRemove(state, level, pos, newState, isMoving);
            } else {
                super.onRemove(state, level, pos, newState, isMoving);
            }
        } else {
            super.onRemove(state, level, pos, newState, isMoving);
        }
    }
    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        super.setPlacedBy(level, pos, state, placer, stack);
        BlockEntity be = level.getBlockEntity(pos);
        if (be instanceof ClassificationHopperBlockEntity hopper) {
            if (stack.hasCustomHoverName()) {
                hopper.setCustomName(stack.getHoverName());
            }
        }
    }
}