package com.hexagram2021.dancing_hoppers.common.block;

import com.google.common.collect.Maps;
import com.hexagram2021.dancing_hoppers.common.block.entity.LateralHopperBlockEntity;
import com.hexagram2021.dancing_hoppers.common.register.DHBlockEntities;
import com.hexagram2021.dancing_hoppers.common.register.DHBlockStateProperties;
import com.hexagram2021.dancing_hoppers.common.util.LateralHopperSide;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HopperBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.HopperBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.Map;

public class LateralHopperBlock extends HopperBlock implements IFacing {
	public static final DirectionProperty HORIZONTAL = BlockStateProperties.HORIZONTAL_FACING;
	public static final EnumProperty<LateralHopperSide> OUTSIDE = DHBlockStateProperties.HOPPER_SIDE;

	private static final Map<Direction, Map<LateralHopperSide, VoxelShape>> SHAPES = Util.make(
			Maps.newEnumMap(Direction.class), map ->
					Direction.Plane.HORIZONTAL.stream().forEach(direction -> {
						VoxelShape baseShape = getHorizontalBaseShape(direction);
						map.put(direction, Util.make(Maps.newEnumMap(LateralHopperSide.class), map1 -> {
							for(LateralHopperSide side: LateralHopperSide.values()) {
								Direction rotated = side.getOutputDirection(direction);
								map1.put(side, Shapes.or(baseShape, getEndShape(direction, rotated)));
							}
						}));
					})
	);

	private static final Map<Direction, Map<LateralHopperSide, VoxelShape>> INTERACTION_SHAPES = Util.make(
			Maps.newEnumMap(Direction.class), map ->
					Direction.Plane.HORIZONTAL.stream().forEach(direction -> {
						VoxelShape baseShape = LateralHopperBlockEntity.INSIDES.get(direction);
						map.put(direction, Util.make(Maps.newEnumMap(LateralHopperSide.class), map1 -> {
							for(LateralHopperSide side: LateralHopperSide.values()) {
								Direction rotated = side.getOutputDirection(direction);
								if(rotated == direction) {
									map1.put(side, baseShape);
								} else {
									map1.put(side, Shapes.or(baseShape, getInteractionShape(direction, rotated)));
								}
							}
						}));
					})
	);

	private static VoxelShape getHorizontalBaseShape(Direction direction) {
		int x = direction.getStepX();
		int z = direction.getStepZ();
		int shapeX1 = x * (x + 1);
		int shapeZ1 = z * (z + 1);
		int shapeX2 = x * (x - 1);
		int shapeZ2 = z * (z - 1);
		VoxelShape top = Block.box(shapeX1 * 5, 0.0D, shapeZ1 * 5, 16.0D - shapeX2 * 5, 16.0D, 16.0D - shapeZ2 * 5);
		VoxelShape funnel = Block.box(shapeX2 + 4.0D, 4.0D, shapeZ2 + 4.0D, 12.0D - shapeX1, 12.0D, 12.0D - shapeZ1);
		return Shapes.join(Shapes.or(top, funnel), LateralHopperBlockEntity.INSIDES.get(direction), BooleanOp.ONLY_FIRST);
	}

	private static VoxelShape getEndShape(Direction direction, Direction rotated) {
		int shiftX = direction.getStepX() * 2;
		int shiftZ = direction.getStepZ() * 2;
		if(direction == rotated.getOpposite()) {
			return Block.box(6.0D - shiftX * 3, 6.0D, 6.0D - shiftZ * 3, 10.0D - shiftX * 3, 10.0D, 10.0D - shiftZ * 3);
		}
		int shapeX = rotated.getStepX() * 6;
		int shapeY = rotated.getStepY() * 6;
		int shapeZ = rotated.getStepZ() * 6;
		return Block.box(6.0D - shiftX + shapeX, 6.0D + shapeY, 6.0D - shiftZ + shapeZ, 10.0D - shiftX + shapeX, 10.0D + shapeY, 10.0D - shiftZ + shapeZ);
	}

	private static VoxelShape getInteractionShape(Direction direction, Direction rotated) {
		int shiftX = direction.getStepX();
		int shiftZ = direction.getStepZ();
		int shapeX = rotated.getStepX();
		int shapeY = rotated.getStepY();
		int shapeZ = rotated.getStepZ();
		if(shiftX != 0) {
			return Block.box(
					7.0D - shiftX - shapeX * shapeX + 6 * shapeX,
					6.0D + 6 * shapeY,
					7.0D - shiftZ - shapeZ * shapeZ - shapeY * shapeY + 6 * shapeZ,
					9.0D - shiftX + shapeX * shapeX + 6 * shapeX,
					10.0D + 6 * shapeY,
					9.0D - shiftZ + shapeZ * shapeZ + shapeY * shapeY + 6 * shapeZ
			);
		}
		//assert shiftZ != 0
		return Block.box(
				7.0D - shiftX - shapeX * shapeX - shapeY * shapeY + 6 * shapeX,
				6.0D + 6 * shapeY,
				7.0D - shiftZ - shapeZ * shapeZ + 6 * shapeZ,
				9.0D - shiftX + shapeX * shapeX + shapeY * shapeY + 6 * shapeX,
				10.0D + 6 * shapeY,
				9.0D - shiftZ + shapeZ * shapeZ + 6 * shapeZ
		);
	}

	public LateralHopperBlock(Properties props) {
		super(props);
	}

	@Override
	public VoxelShape getShape(BlockState blockState, BlockGetter level, BlockPos blockPos, CollisionContext context) {
		return SHAPES.get(blockState.getValue(HORIZONTAL)).get(blockState.getValue(OUTSIDE));
	}

	@Override
	public VoxelShape getInteractionShape(BlockState blockState, BlockGetter level, BlockPos blockPos) {
		return INTERACTION_SHAPES.get(blockState.getValue(HORIZONTAL)).get(blockState.getValue(OUTSIDE));
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		Direction facing = context.getHorizontalDirection();
		Direction direction = context.getClickedFace().getOpposite();
		return this.setFacing(this.defaultBlockState(), facing.getOpposite(), LateralHopperSide.getSide(facing, direction)).setValue(ENABLED, Boolean.TRUE);
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
		return new LateralHopperBlockEntity(blockPos, blockState);
	}

	@Override @Nullable
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> blockEntityType) {
		return level.isClientSide ? null : createTickerHelper(blockEntityType, DHBlockEntities.LATERAL_HOPPER.get(), HopperBlockEntity::pushItemsTick);
	}

	@Override
	public BlockState rotate(BlockState blockState, Rotation rotation) {
		return this.setFacing(blockState, rotation.rotate(blockState.getValue(HORIZONTAL)), blockState.getValue(OUTSIDE));
	}

	@SuppressWarnings("deprecation")
	@Override
	public BlockState mirror(BlockState blockState, Mirror mirror) {
		return blockState.rotate(mirror.getRotation(this.getFacing(blockState))).setValue(OUTSIDE, LateralHopperSide.mirror(blockState.getValue(OUTSIDE)));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(HORIZONTAL, OUTSIDE, ENABLED);
	}

	@Override
	public Direction getFacing(BlockState blockState) {
		return blockState.getValue(OUTSIDE).getOutputDirection(blockState.getValue(HORIZONTAL));
	}

	@Override @Deprecated
	public BlockState setFacing(BlockState blockState, Direction facing) {
		if(facing.getAxis() == Direction.Axis.Y) {
			return this.setFacing(blockState, this.getDefaultFacing(), facing == Direction.UP ? LateralHopperSide.UP : LateralHopperSide.DOWN);
		}
		return this.setFacing(blockState, facing.getOpposite(), LateralHopperSide.FORWARD);
	}

	public BlockState setFacing(BlockState blockState, Direction input, LateralHopperSide outside) {
		return blockState.setValue(HORIZONTAL, input).setValue(OUTSIDE, outside);
	}

	@Override
	public Direction getDefaultFacing() {
		return Direction.NORTH;
	}
}
