package com.hexagram2021.dancing_hoppers.common.block.entity;

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
import org.jetbrains.annotations.Nullable;

public class InvertedHopperBlockEntity extends HopperBlockEntity implements IHopperBlockEntity {
	public static final VoxelShape INSIDE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 5.0D, 14.0D);
	public static final VoxelShape BELOW = Block.box(0.0D, -16.0D, 0.0D, 16.0D, 0.0D, 16.0D);
	public static final VoxelShape SUCK = Shapes.or(INSIDE, BELOW);

	public InvertedHopperBlockEntity(BlockPos blockPos, BlockState blockState) {
		super(blockPos, blockState);
		((BlockEntityAccess)this).dh_setType(DHBlockEntities.INVERTED_HOPPER.get());
	}

	@Override
	public VoxelShape getSuckShape() {
		return SUCK;
	}

	@Override @Nullable
	public Container getSourceContainer(Level level) {
		return HopperBlockEntity.getContainerAt(level, this.getLevelX(), this.getLevelY() - 1.0D, this.getLevelZ());
	}

	@Override
	public Direction getSourceContainerOutputDirection() {
		return Direction.UP;
	}
}
