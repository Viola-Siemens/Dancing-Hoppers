package com.hexagram2021.dancing_hoppers.common.register;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.hexagram2021.dancing_hoppers.DancingHoppers.MODID;

public final class DHItems {
	private static final DeferredRegister<Item> REGISTER = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);

	public static final RegistryObject<BlockItem> INVERTED_HOPPER = REGISTER.register(
			"inverted_hopper", () -> new BlockItem(DHBlocks.INVERTED_HOPPER.get(), new Item.Properties())
	);
	public static final RegistryObject<BlockItem> SIDED_HOPPER = REGISTER.register(
			"sided_hopper", () -> new BlockItem(DHBlocks.SIDED_HOPPER.get(), new Item.Properties())
	);

	private DHItems() {
	}

	public static void init(IEventBus bus) {
		REGISTER.register(bus);
	}
}
