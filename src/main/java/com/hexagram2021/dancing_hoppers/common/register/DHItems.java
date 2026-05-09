package com.hexagram2021.dancing_hoppers.common.register;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.TooltipFlag;
import javax.annotation.Nullable;
import java.util.List;

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
    public static final RegistryObject<BlockItem> ACCELERATED_INVERTED_HOPPER = REGISTER.register(
            "accelerated_inverted_hopper", () -> new BlockItem(DHBlocks.ACCELERATED_INVERTED_HOPPER.get(), new Item.Properties())
    );
    public static final RegistryObject<BlockItem> ACCELERATED_LATERAL_HOPPER = REGISTER.register(
            "accelerated_lateral_hopper", () -> new BlockItem(DHBlocks.ACCELERATED_LATERAL_HOPPER.get(), new Item.Properties())
    );

    public static final RegistryObject<BlockItem> CLASSIFICATION_HOPPER = REGISTER.register(
            "classification_hopper", () -> new BlockItem(DHBlocks.CLASSIFICATION_HOPPER.get(), new Item.Properties())
    );
    public static final RegistryObject<BlockItem> ENHANCED_CLASSIFICATION_HOPPER = REGISTER.register(
            "enhanced_classification_hopper", () -> new BlockItem(DHBlocks.ENHANCED_CLASSIFICATION_HOPPER.get(), new Item.Properties())
    );

    public static final RegistryObject<BlockItem> INVERTED_CLASSIFICATION_HOPPER = REGISTER.register(
            "inverted_classification_hopper", () -> new BlockItem(DHBlocks.INVERTED_CLASSIFICATION_HOPPER.get(), new Item.Properties())
    );
    public static final RegistryObject<BlockItem> INVERTED_ENHANCED_CLASSIFICATION_HOPPER = REGISTER.register(
            "inverted_enhanced_classification_hopper", () -> new BlockItem(DHBlocks.INVERTED_ENHANCED_CLASSIFICATION_HOPPER.get(), new Item.Properties())
    );
    public static final RegistryObject<BlockItem> LATERAL_CLASSIFICATION_HOPPER = REGISTER.register(
            "lateral_classification_hopper", () -> new BlockItem(DHBlocks.LATERAL_CLASSIFICATION_HOPPER.get(), new Item.Properties())
    );
    public static final RegistryObject<BlockItem> LATERAL_ENHANCED_CLASSIFICATION_HOPPER = REGISTER.register(
            "lateral_enhanced_classification_hopper", () -> new BlockItem(DHBlocks.LATERAL_ENHANCED_CLASSIFICATION_HOPPER.get(), new Item.Properties())
    );

    public static final RegistryObject<Item> HOPPER_UPGRADE_SMITHING_TEMPLATE = REGISTER.register(
            "hopper_upgrade_smithing_template", () -> new Item(new Item.Properties()) {
                @Override
                public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
                    super.appendHoverText(stack, level, tooltip, flag);
                    tooltip.add(Component.translatable("item.dancing_hoppers.hopper_upgrade_smithing_template.description")
                            .withStyle(ChatFormatting.GRAY));
                    tooltip.add(CommonComponents.EMPTY);
                    tooltip.add(Component.translatable("item.minecraft.smithing_template.applies_to").withStyle(ChatFormatting.GRAY));
                    tooltip.add(CommonComponents.space().append(
                            Component.translatable("block.minecraft.hopper").withStyle(ChatFormatting.BLUE)));
                    tooltip.add(Component.translatable("item.minecraft.smithing_template.ingredients").withStyle(ChatFormatting.GRAY));
                    tooltip.add(CommonComponents.space().append(
                            Component.translatable("item.minecraft.diamond").withStyle(ChatFormatting.BLUE)));
                }
            }
    );

    public static final RegistryObject<Item> COPPER_FORGING_SMITHING_TEMPLATE = REGISTER.register(
            "copper_forging_smithing_template", () -> new Item(new Item.Properties()) {
                @Override
                public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
                    super.appendHoverText(stack, level, tooltip, flag);
                    tooltip.add(Component.translatable("item.dancing_hoppers.copper_forging_smithing_template.description")
                            .withStyle(ChatFormatting.GRAY));
                    tooltip.add(CommonComponents.EMPTY);
                    tooltip.add(Component.translatable("item.minecraft.smithing_template.applies_to").withStyle(ChatFormatting.GRAY));
                    tooltip.add(CommonComponents.space().append(
                            Component.translatable("block.minecraft.hopper").withStyle(ChatFormatting.BLUE)));
                    tooltip.add(Component.translatable("item.minecraft.smithing_template.ingredients").withStyle(ChatFormatting.GRAY));
                    tooltip.add(CommonComponents.space().append(
                            Component.translatable("item.minecraft.copper_ingot").withStyle(ChatFormatting.BLUE)));
                }
            }
    );

    public static final RegistryObject<Item> GOLD_FORGING_SMITHING_TEMPLATE = REGISTER.register(
            "gold_forging_smithing_template", () -> new Item(new Item.Properties()) {
                @Override
                public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
                    super.appendHoverText(stack, level, tooltip, flag);
                    tooltip.add(Component.translatable("item.dancing_hoppers.gold_forging_smithing_template.description")
                            .withStyle(ChatFormatting.GRAY));
                    tooltip.add(CommonComponents.EMPTY);
                    tooltip.add(Component.translatable("item.minecraft.smithing_template.applies_to").withStyle(ChatFormatting.GRAY));
                    tooltip.add(CommonComponents.space().append(
                            Component.translatable("block.dancing_hoppers.classification_hopper").withStyle(ChatFormatting.BLUE)));
                    tooltip.add(Component.translatable("item.minecraft.smithing_template.ingredients").withStyle(ChatFormatting.GRAY));
                    tooltip.add(CommonComponents.space().append(
                            Component.translatable("item.minecraft.gold_ingot").withStyle(ChatFormatting.BLUE)));
                }
            }
    );

    private DHItems() {}

    public static void init(IEventBus bus) {
        REGISTER.register(bus);
    }
}