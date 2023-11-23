package com.hexagram2021.dancing_hoppers.common.register;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

import static com.hexagram2021.dancing_hoppers.DancingHoppers.MODID;

public final class DHCreativeModeTabs {
	private static final DeferredRegister<CreativeModeTab> REGISTER = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

	public static final RegistryObject<CreativeModeTab> ITEM_GROUP = register(
			"dancing_hoppers",
			Component.translatable("itemGroup.dancing_hoppers"),
			() -> new ItemStack(DHItems.ACCELERATED_HOPPER.get()),
			(parameters, output) -> {
				output.accept(DHItems.ACCELERATED_HOPPER.get());
				output.accept(DHItems.INVERTED_HOPPER.get());
				output.accept(DHItems.LATERAL_HOPPER.get());
				output.accept(DHItems.HOPPER_UPGRADE_SMITHING_TEMPLATE.get());
			}
	);

	private DHCreativeModeTabs() {
	}

	public static void init(IEventBus bus) {
		REGISTER.register(bus);
	}

	@SuppressWarnings("SameParameterValue")
	private static RegistryObject<CreativeModeTab> register(String name, Component title, Supplier<ItemStack> icon, CreativeModeTab.DisplayItemsGenerator generator) {
		return REGISTER.register(name, () -> CreativeModeTab.builder().title(title).icon(icon).displayItems(generator).build());
	}
}
