package com.hexagram2021.dancing_hoppers.common.block;
import com.hexagram2021.dancing_hoppers.common.block.entity.AcceleratedLateralHopperBlockEntity;
import com.hexagram2021.dancing_hoppers.common.register.DHBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.HopperBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import javax.annotation.Nullable;
public class AcceleratedLateralHopperBlock extends LateralHopperBlock {
    public AcceleratedLateralHopperBlock(Properties props) {
        super(props);
    }
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new AcceleratedLateralHopperBlockEntity(blockPos, blockState);
    }
    @Override
    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> blockEntityType) {
        return level.isClientSide ? null : createTickerHelper(blockEntityType, DHBlockEntities.ACCELERATED_LATERAL_HOPPER.get(), HopperBlockEntity::pushItemsTick);
    }
}