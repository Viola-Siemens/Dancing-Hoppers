package com.hexagram2021.dancing_hoppers.common.register;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.properties.DirectionProperty;

public final class DHBlockProperties {
	public static final DirectionProperty FACING_INVERTED_HOPPER = DirectionProperty.create("facing", direction -> direction != Direction.DOWN);
}
