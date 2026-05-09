package com.hexagram2021.dancing_hoppers.common.block;
import com.hexagram2021.dancing_hoppers.common.block.entity.InvertedClassificationHopperBlockEntity;
import com.hexagram2021.dancing_hoppers.common.register.DHBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import javax.annotation.Nullable;
public class InvertedClassificationHopperBlock extends InvertedHopperBlock {
    public InvertedClassificationHopperBlock(Properties props) {
        super(props);
    }
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new InvertedClassificationHopperBlockEntity(pos, state);
    }
    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return level.isClientSide ? null : createTickerHelper(type, DHBlockEntities.INVERTED_CLASSIFICATION_HOPPER.get(),
                (lvl, pos, st, be) -> be.serverTick(lvl, pos, st));
    }
    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (level.isClientSide) return InteractionResult.SUCCESS;
        BlockEntity be = level.getBlockEntity(pos);
        if (be instanceof InvertedClassificationHopperBlockEntity hopper) {
            NetworkHooks.openScreen((ServerPlayer) player, hopper, pos);
            player.awardStat(Stats.INSPECT_HOPPER);
        }
        return InteractionResult.CONSUME;
    }
}