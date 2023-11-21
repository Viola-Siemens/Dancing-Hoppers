package com.hexagram2021.dancing_hoppers.mixin;

import com.hexagram2021.dancing_hoppers.common.block.IFacing;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.HopperBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(HopperBlock.class)
public class HopperBlockMixin implements IFacing {
	@Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;setValue(Lnet/minecraft/world/level/block/state/properties/Property;Ljava/lang/Comparable;)Ljava/lang/Object;", ordinal = 0))
	private Object replacedSetFacing(BlockState instance, Property<Direction> property, Comparable<Direction> comparable) {
		return this.setFacing(instance, this.getDefaultFacing());
	}

	@Override
	public Direction getFacing(BlockState blockState) {
		return blockState.getValue(HopperBlock.FACING);
	}

	@Override
	public BlockState setFacing(BlockState blockState, Direction facing) {
		return blockState.setValue(HopperBlock.FACING, facing);
	}

	@Override
	public Direction getDefaultFacing() {
		return Direction.DOWN;
	}
}
