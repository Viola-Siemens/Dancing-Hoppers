package com.hexagram2021.dancing_hoppers.common.block.entity;

import net.minecraft.core.Direction;
import net.minecraft.world.Container;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public interface IHopperBlockEntity {
	@Nullable
	Container getSourceContainer(Level level);

	Direction getSourceContainerOutputDirection();
}
