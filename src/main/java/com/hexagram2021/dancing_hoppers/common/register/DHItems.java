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
	public static final RegistryObject<BlockItem> LATERAL_HOPPER = REGISTER.register(
			"lateral_hopper", () -> new BlockItem(DHBlocks.LATERAL_HOPPER.get(), new Item.Properties())
	);
	public static final RegistryObject<BlockItem> ACCELERATED_HOPPER = REGISTER.register(
			"accelerated_hopper", () -> new BlockItem(DHBlocks.ACCELERATED_HOPPER.get(), new Item.Properties())
	);
	public static final RegistryObject<Item> HOPPER_UPGRADE_SMITHING_TEMPLATE = REGISTER.register(
			"hopper_upgrade_smithing_template", () -> new Item(new Item.Properties())
	);

	private DHItems() {
	}

	public static void init(IEventBus bus) {
		REGISTER.register(bus);
	}
}
