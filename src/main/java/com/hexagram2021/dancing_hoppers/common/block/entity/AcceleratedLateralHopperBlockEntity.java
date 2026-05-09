package com.hexagram2021.dancing_hoppers.common.block.entity;
import com.hexagram2021.dancing_hoppers.common.register.DHBlockEntities;
import com.hexagram2021.dancing_hoppers.mixin.BlockEntityAccess;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
public class AcceleratedLateralHopperBlockEntity extends LateralHopperBlockEntity {
    public AcceleratedLateralHopperBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(blockPos, blockState);
        ((BlockEntityAccess)this).dh_setType(DHBlockEntities.ACCELERATED_LATERAL_HOPPER.get());
    }
    @Override
    public void setCooldown(int cd) {
        super.setCooldown(cd > 0 ? 1 : cd);
    }
}