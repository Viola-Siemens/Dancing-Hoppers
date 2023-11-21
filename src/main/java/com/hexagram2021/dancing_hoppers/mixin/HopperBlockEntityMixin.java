package com.hexagram2021.dancing_hoppers.mixin;

import com.hexagram2021.dancing_hoppers.common.block.IFacing;
import com.hexagram2021.dancing_hoppers.common.block.entity.IHopperBlockEntity;
import net.minecraft.core.Direction;
import net.minecraft.world.Container;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.Hopper;
import net.minecraft.world.level.block.entity.HopperBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;

@Mixin(HopperBlockEntity.class)
public abstract class HopperBlockEntityMixin implements Hopper, IHopperBlockEntity {
	@Nullable
	public Container getSourceContainer(Level level) {
		return HopperBlockEntity.getContainerAt(level, this.getLevelX(), this.getLevelY() + 1.0D, this.getLevelZ());
	}

	@Redirect(method = "suckInItems", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/entity/HopperBlockEntity;getSourceContainer(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/level/block/entity/Hopper;)Lnet/minecraft/world/Container;"))
	@Nullable
	private static Container replacedGetSourceContainer(Level level, Hopper hopper) {
		return hopper instanceof IHopperBlockEntity hopperBlockEntity ? hopperBlockEntity.getSourceContainer(level) : HopperBlockEntity.getSourceContainer(level, hopper);
	}

	@Nullable
	private static Direction direction = null;

	@Inject(method = "suckInItems", at = @At(value = "FIELD", target = "Lnet/minecraft/core/Direction;DOWN:Lnet/minecraft/core/Direction;", shift = At.Shift.BEFORE, opcode = Opcodes.GETSTATIC))
	private static void injectGetDefaultDirection(Level level, Hopper hopper, CallbackInfoReturnable<Boolean> cir) {
		direction = hopper instanceof IHopperBlockEntity hopperBlockEntity ? hopperBlockEntity.getSourceContainerOutputDirection() : Direction.DOWN;
	}

	@Redirect(method = "suckInItems", at = @At(value = "FIELD", target = "Lnet/minecraft/core/Direction;DOWN:Lnet/minecraft/core/Direction;", opcode = Opcodes.GETSTATIC))
	private static Direction replacedGetDirection() {
		Direction ret = Direction.DOWN;
		if(direction != null) {
			ret = direction;
			direction = null;
		}
		return ret;
	}

	@Redirect(method = "ejectItems", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;getValue(Lnet/minecraft/world/level/block/state/properties/Property;)Ljava/lang/Comparable;"))
	private static Comparable<Direction> replacedEjectFacingDirection(BlockState instance, Property<Direction> property) {
		return instance.getBlock() instanceof IFacing facingBlock ? facingBlock.getFacing(instance) : instance.getValue(property);
	}

	@Redirect(method = "getAttachedContainer", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;getValue(Lnet/minecraft/world/level/block/state/properties/Property;)Ljava/lang/Comparable;"))
	private static Comparable<Direction> replacedAttachedFacingDirection(BlockState instance, Property<Direction> property) {
		return instance.getBlock() instanceof IFacing facingBlock ? facingBlock.getFacing(instance) : instance.getValue(property);
	}

	@Override
	public Direction getSourceContainerOutputDirection() {
		return Direction.DOWN;
	}
}
