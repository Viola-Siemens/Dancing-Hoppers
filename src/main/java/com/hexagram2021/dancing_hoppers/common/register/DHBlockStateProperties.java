package com.hexagram2021.dancing_hoppers.common.register;

import com.hexagram2021.dancing_hoppers.common.util.SidedHopperSide;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;

public final class DHBlockStateProperties {
	public static final DirectionProperty FACING_INVERTED_HOPPER = DirectionProperty.create("facing", direction -> direction != Direction.DOWN);
	public static final EnumProperty<SidedHopperSide> HOPPER_SIDE = EnumProperty.create("outside", SidedHopperSide.class);
}
