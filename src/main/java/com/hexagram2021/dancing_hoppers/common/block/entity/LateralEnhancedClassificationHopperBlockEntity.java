package com.hexagram2021.dancing_hoppers.common.block.entity;
import com.hexagram2021.dancing_hoppers.common.block.LateralHopperBlock;
import com.hexagram2021.dancing_hoppers.common.register.DHBlockEntities;
import com.hexagram2021.dancing_hoppers.mixin.BlockEntityAccess;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.Container;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.HopperBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import javax.annotation.Nullable;
public class LateralEnhancedClassificationHopperBlockEntity extends EnhancedClassificationHopperBlockEntity implements IHopperBlockEntity {
    public static final VoxelShape INSIDE = Block.box(2.0D, 2.0D, 2.0D, 14.0D, 14.0D, 14.0D);
    public static VoxelShape getSuckShape(Direction direction) {
        int x = direction.getStepX() * 16;
        int z = direction.getStepZ() * 16;
        return Shapes.or(
                INSIDE,
                Block.box(x, 0.0D, z, 16.0D + x, 16.0D, 16.0D + z)
        );
    }
    public LateralEnhancedClassificationHopperBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state);
        ((BlockEntityAccess) this).dh_setType(DHBlockEntities.LATERAL_ENHANCED_CLASSIFICATION_HOPPER.get());
    }
    @Override
    public VoxelShape getSuckShape() {
        Direction direction = getBlockState().getValue(LateralHopperBlock.HORIZONTAL);
        return getSuckShape(direction);
    }
    @Nullable
    @Override
    public Container getSourceContainer(Level level) {
        Direction direction = getBlockState().getValue(LateralHopperBlock.HORIZONTAL);
        return HopperBlockEntity.getContainerAt(level, getLevelX() + direction.getStepX(), getLevelY(), getLevelZ() + direction.getStepZ());
    }
    @Override
    public Direction getSourceContainerOutputDirection() {
        return getBlockState().getValue(LateralHopperBlock.HORIZONTAL).getOpposite();
    }
}