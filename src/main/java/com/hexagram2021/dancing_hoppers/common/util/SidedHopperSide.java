package com.hexagram2021.dancing_hoppers.common.util;

import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;

import java.util.function.Function;

public enum SidedHopperSide implements StringRepresentable {
	UP("up", direction -> Direction.UP),
	DOWN("down", direction -> Direction.DOWN),
	FORWARD("forward", Direction::getOpposite),
	LEFT("left", Direction::getClockWise),
	RIGHT("right", Direction::getCounterClockWise);

	private final String name;
	private final Function<Direction, Direction> outputFunction;

	SidedHopperSide(String name, Function<Direction, Direction> outputFunction) {
		this.name = name;
		this.outputFunction = outputFunction;
	}

	public Direction getOutputDirection(Direction input) {
		return this.outputFunction.apply(input);
	}

	@Override
	public String toString() {
		return this.getSerializedName();
	}

	@Override
	public String getSerializedName() {
		return this.name;
	}

	public static SidedHopperSide mirror(SidedHopperSide side) {
		return switch (side) {
			case UP -> UP;
			case DOWN -> DOWN;
			case FORWARD -> FORWARD;
			case LEFT -> RIGHT;
			case RIGHT -> LEFT;
		};
	}

	public static SidedHopperSide getSide(Direction facing, Direction clicked) {
		switch (clicked) {
			case UP -> {
				return UP;
			}
			case DOWN -> {
				return DOWN;
			}
			default -> {
				if(facing.getClockWise() == clicked) {
					return RIGHT;
				}
				if(facing.getCounterClockWise() == clicked) {
					return LEFT;
				}
			}
		}
		return FORWARD;
	}
}
