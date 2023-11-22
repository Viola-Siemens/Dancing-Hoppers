package com.hexagram2021.dancing_hoppers.common.register;

import com.google.common.collect.ImmutableSet;
import com.hexagram2021.dancing_hoppers.common.block.entity.InvertedHopperBlockEntity;
import com.hexagram2021.dancing_hoppers.common.block.entity.SidedHopperBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.hexagram2021.dancing_hoppers.DancingHoppers.MODID;

@SuppressWarnings("ConstantConditions")
public final class DHBlockEntities {
	private static final DeferredRegister<BlockEntityType<?>> REGISTER = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MODID);

	public static final RegistryObject<BlockEntityType<InvertedHopperBlockEntity>> INVERTED_HOPPER = REGISTER.register(
			"inverted_hopper", () -> new BlockEntityType<>(InvertedHopperBlockEntity::new, ImmutableSet.of(DHBlocks.INVERTED_HOPPER.get()), null)
	);
	public static final RegistryObject<BlockEntityType<SidedHopperBlockEntity>> SIDED_HOPPER = REGISTER.register(
			"sided_hopper", () -> new BlockEntityType<>(SidedHopperBlockEntity::new, ImmutableSet.of(DHBlocks.SIDED_HOPPER.get()), null)
	);

	private DHBlockEntities() {
	}

	public static void init(IEventBus bus) {
		REGISTER.register(bus);
	}
}
