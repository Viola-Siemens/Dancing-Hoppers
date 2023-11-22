package com.hexagram2021.dancing_hoppers.common.block.entity;

import com.google.common.collect.Maps;
import com.hexagram2021.dancing_hoppers.common.block.SidedHopperBlock;
import com.hexagram2021.dancing_hoppers.common.register.DHBlockEntities;
import com.hexagram2021.dancing_hoppers.mixin.BlockEntityAccess;
import net.minecraft.Util;
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

import java.util.Map;

public class SidedHopperBlockEntity extends HopperBlockEntity implements IHopperBlockEntity {
	public static final Map<Direction, VoxelShape> INSIDES = Util.make(Maps.newEnumMap(Direction.class), map -> {
		map.put(Direction.NORTH, Block.box(2.0D, 2.0D, 0.0D, 14.0D, 14.0D, 5.0D));
		map.put(Direction.SOUTH, Block.box(2.0D, 2.0D, 11.0D, 14.0D, 14.0D, 16.0D));
		map.put(Direction.WEST, Block.box(0.0D, 2.0D, 2.0D, 5.0D, 14.0D, 14.0D));
		map.put(Direction.EAST, Block.box(11.0D, 2.0D, 2.0D, 16.0D, 14.0D, 14.0D));
	});

	public static final Map<Direction, VoxelShape> SUCKS = Util.make(Maps.newEnumMap(Direction.class), map -> {
		for(Direction direction: Direction.Plane.HORIZONTAL) {
			int x = direction.getStepX() * 16;
			int z = direction.getStepZ() * 16;
			map.put(direction, Shapes.or(
					INSIDES.get(direction),
					Block.box(x, 0.0D, z, 16.0D + x, 16.0D, 16.0D + z)
			));
		}
	});

	public SidedHopperBlockEntity(BlockPos blockPos, BlockState blockState) {
		super(blockPos, blockState);
		((BlockEntityAccess)this).dh_setType(DHBlockEntities.SIDED_HOPPER.get());
	}

	@Override
	public VoxelShape getSuckShape() {
		return SUCKS.get(this.getBlockState().getValue(SidedHopperBlock.HORIZONTAL));
	}

	@Override @Nullable
	public Container getSourceContainer(Level level) {
		Direction direction = this.getBlockState().getValue(SidedHopperBlock.HORIZONTAL);
		return HopperBlockEntity.getContainerAt(level, this.getLevelX() + direction.getStepX(), this.getLevelY(), this.getLevelZ() + direction.getStepZ());
	}

	@Override
	public Direction getSourceContainerOutputDirection() {
		return this.getBlockState().getValue(SidedHopperBlock.HORIZONTAL).getOpposite();
	}
}
