package com.hexagram2021.dancing_hoppers.common.block;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;

public interface IFacing {
	Direction getFacing(BlockState blockState);
	BlockState setFacing(BlockState blockState, Direction facing);

	Direction getDefaultFacing();
}
