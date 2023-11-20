package com.hexagram2021.dancing_hoppers.common.block;

import com.hexagram2021.dancing_hoppers.common.block.entity.InvertedHopperBlockEntity;
import com.hexagram2021.dancing_hoppers.common.register.DHBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HopperBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.HopperBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class InvertedHopperBlock extends HopperBlock {
	private static final VoxelShape TOP = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 6.0D, 16.0D);
	private static final VoxelShape FUNNEL = Block.box(4.0D, 6.0D, 4.0D, 12.0D, 12.0D, 12.0D);
	private static final VoxelShape CONVEX_BASE = Shapes.or(FUNNEL, TOP);
	private static final VoxelShape BASE = Shapes.join(CONVEX_BASE, InvertedHopperBlockEntity.INSIDE, BooleanOp.ONLY_FIRST);
	private static final VoxelShape DOWN_SHAPE = Shapes.or(BASE, Block.box(6.0D, 12.0D, 6.0D, 10.0D, 16.0D, 10.0D));
	private static final VoxelShape EAST_SHAPE = Shapes.or(BASE, Block.box(12.0D, 8.0D, 6.0D, 16.0D, 12.0D, 10.0D));
	private static final VoxelShape NORTH_SHAPE = Shapes.or(BASE, Block.box(6.0D, 8.0D, 0.0D, 10.0D, 12.0D, 4.0D));
	private static final VoxelShape SOUTH_SHAPE = Shapes.or(BASE, Block.box(6.0D, 8.0D, 12.0D, 10.0D, 12.0D, 16.0D));
	private static final VoxelShape WEST_SHAPE = Shapes.or(BASE, Block.box(0.0D, 8.0D, 6.0D, 4.0D, 12.0D, 10.0D));
	private static final VoxelShape UP_INTERACTION_SHAPE = InvertedHopperBlockEntity.INSIDE;
	private static final VoxelShape EAST_INTERACTION_SHAPE = Shapes.or(InvertedHopperBlockEntity.INSIDE, Block.box(12.0D, 6.0D, 6.0D, 16.0D, 8.0D, 10.0D));
	private static final VoxelShape NORTH_INTERACTION_SHAPE = Shapes.or(InvertedHopperBlockEntity.INSIDE, Block.box(6.0D, 6.0D, 0.0D, 10.0D, 8.0D, 4.0D));
	private static final VoxelShape SOUTH_INTERACTION_SHAPE = Shapes.or(InvertedHopperBlockEntity.INSIDE, Block.box(6.0D, 6.0D, 12.0D, 10.0D, 8.0D, 16.0D));
	private static final VoxelShape WEST_INTERACTION_SHAPE = Shapes.or(InvertedHopperBlockEntity.INSIDE, Block.box(0.0D, 6.0D, 6.0D, 4.0D, 8.0D, 10.0D));

	public InvertedHopperBlock(Properties props) {
		super(props);
	}

	@Override
	public VoxelShape getShape(BlockState blockState, BlockGetter level, BlockPos blockPos, CollisionContext context) {
		return switch (blockState.getValue(FACING)) {
			case DOWN -> DOWN_SHAPE;
			case NORTH -> NORTH_SHAPE;
			case SOUTH -> SOUTH_SHAPE;
			case WEST -> WEST_SHAPE;
			case EAST -> EAST_SHAPE;
			default -> BASE;
		};
	}

	@Override
	public VoxelShape getInteractionShape(BlockState blockState, BlockGetter level, BlockPos blockPos) {
		return switch (blockState.getValue(FACING)) {
			case DOWN -> UP_INTERACTION_SHAPE;
			case NORTH -> NORTH_INTERACTION_SHAPE;
			case SOUTH -> SOUTH_INTERACTION_SHAPE;
			case WEST -> WEST_INTERACTION_SHAPE;
			case EAST -> EAST_INTERACTION_SHAPE;
			default -> InvertedHopperBlockEntity.INSIDE;
		};
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
		return new InvertedHopperBlockEntity(blockPos, blockState);
	}

	@Override @Nullable
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> blockEntityType) {
		return level.isClientSide ? null : createTickerHelper(blockEntityType, DHBlockEntities.INVERTED_HOPPER.get(), HopperBlockEntity::pushItemsTick);
	}
}
