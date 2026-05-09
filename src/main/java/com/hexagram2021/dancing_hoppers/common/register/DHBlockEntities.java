package com.hexagram2021.dancing_hoppers.common.register;

import com.google.common.collect.ImmutableSet;
import com.hexagram2021.dancing_hoppers.common.block.entity.*;
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
    public static final RegistryObject<BlockEntityType<LateralHopperBlockEntity>> LATERAL_HOPPER = REGISTER.register(
            "lateral_hopper", () -> new BlockEntityType<>(LateralHopperBlockEntity::new, ImmutableSet.of(DHBlocks.LATERAL_HOPPER.get()), null)
    );
    public static final RegistryObject<BlockEntityType<AcceleratedHopperBlockEntity>> ACCELERATED_HOPPER = REGISTER.register(
            "accelerated_hopper", () -> new BlockEntityType<>(AcceleratedHopperBlockEntity::new, ImmutableSet.of(DHBlocks.ACCELERATED_HOPPER.get()), null)
    );
    public static final RegistryObject<BlockEntityType<AcceleratedInvertedHopperBlockEntity>> ACCELERATED_INVERTED_HOPPER = REGISTER.register(
            "accelerated_inverted_hopper", () -> new BlockEntityType<>(AcceleratedInvertedHopperBlockEntity::new, ImmutableSet.of(DHBlocks.ACCELERATED_INVERTED_HOPPER.get()), null)
    );
    public static final RegistryObject<BlockEntityType<AcceleratedLateralHopperBlockEntity>> ACCELERATED_LATERAL_HOPPER = REGISTER.register(
            "accelerated_lateral_hopper", () -> new BlockEntityType<>(AcceleratedLateralHopperBlockEntity::new, ImmutableSet.of(DHBlocks.ACCELERATED_LATERAL_HOPPER.get()), null)
    );

    public static final RegistryObject<BlockEntityType<ClassificationHopperBlockEntity>> CLASSIFICATION_HOPPER = REGISTER.register(
            "classification_hopper", () -> new BlockEntityType<>(ClassificationHopperBlockEntity::new, ImmutableSet.of(DHBlocks.CLASSIFICATION_HOPPER.get()), null)
    );
    public static final RegistryObject<BlockEntityType<EnhancedClassificationHopperBlockEntity>> ENHANCED_CLASSIFICATION_HOPPER = REGISTER.register(
            "enhanced_classification_hopper", () -> new BlockEntityType<>(EnhancedClassificationHopperBlockEntity::new, ImmutableSet.of(DHBlocks.ENHANCED_CLASSIFICATION_HOPPER.get()), null)
    );

    public static final RegistryObject<BlockEntityType<InvertedClassificationHopperBlockEntity>> INVERTED_CLASSIFICATION_HOPPER = REGISTER.register(
            "inverted_classification_hopper", () -> new BlockEntityType<>(InvertedClassificationHopperBlockEntity::new, ImmutableSet.of(DHBlocks.INVERTED_CLASSIFICATION_HOPPER.get()), null)
    );
    public static final RegistryObject<BlockEntityType<InvertedEnhancedClassificationHopperBlockEntity>> INVERTED_ENHANCED_CLASSIFICATION_HOPPER = REGISTER.register(
            "inverted_enhanced_classification_hopper", () -> new BlockEntityType<>(InvertedEnhancedClassificationHopperBlockEntity::new, ImmutableSet.of(DHBlocks.INVERTED_ENHANCED_CLASSIFICATION_HOPPER.get()), null)
    );
    public static final RegistryObject<BlockEntityType<LateralClassificationHopperBlockEntity>> LATERAL_CLASSIFICATION_HOPPER = REGISTER.register(
            "lateral_classification_hopper", () -> new BlockEntityType<>(LateralClassificationHopperBlockEntity::new, ImmutableSet.of(DHBlocks.LATERAL_CLASSIFICATION_HOPPER.get()), null)
    );
    public static final RegistryObject<BlockEntityType<LateralEnhancedClassificationHopperBlockEntity>> LATERAL_ENHANCED_CLASSIFICATION_HOPPER = REGISTER.register(
            "lateral_enhanced_classification_hopper", () -> new BlockEntityType<>(LateralEnhancedClassificationHopperBlockEntity::new, ImmutableSet.of(DHBlocks.LATERAL_ENHANCED_CLASSIFICATION_HOPPER.get()), null)
    );

    private DHBlockEntities() {}

    public static void init(IEventBus bus) {
        REGISTER.register(bus);
    }
}