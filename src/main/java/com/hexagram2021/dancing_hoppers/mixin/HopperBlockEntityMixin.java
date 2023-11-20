package com.hexagram2021.dancing_hoppers.mixin;

import com.hexagram2021.dancing_hoppers.common.block.entity.IHopperBlockEntity;
import net.minecraft.world.Container;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.Hopper;
import net.minecraft.world.level.block.entity.HopperBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

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
}
