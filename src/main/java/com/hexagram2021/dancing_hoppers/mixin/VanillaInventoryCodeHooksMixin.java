package com.hexagram2021.dancing_hoppers.mixin;

import com.hexagram2021.dancing_hoppers.common.block.IFacing;
import com.hexagram2021.dancing_hoppers.common.block.entity.IHopperBlockEntity;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.Hopper;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.VanillaInventoryCodeHooks;
import org.apache.commons.lang3.tuple.Pair;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Optional;

@Mixin(VanillaInventoryCodeHooks.class)
public abstract class VanillaInventoryCodeHooksMixin {
	@Shadow(remap = false)
	private static Optional<Pair<IItemHandler, Object>> getItemHandler(Level level, Hopper hopper, Direction hopperFacing) {
		throw new UnsupportedOperationException("Replaced by Mixin");
	}

	@Redirect(method = "extractHook", at = @At(value = "INVOKE", target = "Lnet/minecraftforge/items/VanillaInventoryCodeHooks;getItemHandler(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/level/block/entity/Hopper;Lnet/minecraft/core/Direction;)Ljava/util/Optional;"), remap = false)
	private static Optional<Pair<IItemHandler, Object>> replacedGetItemHandler(Level level, Hopper hopper, Direction hopperFacing) {
		if(hopper instanceof IHopperBlockEntity hopperBlockEntity) {
			hopperFacing = hopperBlockEntity.getSourceContainerOutputDirection().getOpposite();
		}
		return getItemHandler(level, hopper, hopperFacing);
	}

	@Redirect(method = "insertHook", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;getValue(Lnet/minecraft/world/level/block/state/properties/Property;)Ljava/lang/Comparable;"), remap = false)
	private static Comparable<Direction> replacedFacingDirection(BlockState instance, Property<Direction> property) {
		return instance.getBlock() instanceof IFacing facingBlock ? facingBlock.getFacing(instance) : instance.getValue(property);
	}
}
