package com.hexagram2021.dancing_hoppers.common.register;

import com.hexagram2021.dancing_hoppers.common.block.InvertedHopperBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.hexagram2021.dancing_hoppers.DancingHoppers.MODID;

public final class DHBlocks {
	private static final DeferredRegister<Block> REGISTER = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);

	public static final RegistryObject<InvertedHopperBlock> INVERTED_HOPPER = REGISTER.register(
			"inverted_hopper", () -> new InvertedHopperBlock(BlockBehaviour.Properties.copy(Blocks.HOPPER))
	);

	private DHBlocks() {
	}

	public static void init(IEventBus bus) {
		REGISTER.register(bus);
	}
}
