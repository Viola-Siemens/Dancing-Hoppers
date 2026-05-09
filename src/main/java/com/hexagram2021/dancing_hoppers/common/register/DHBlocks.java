package com.hexagram2021.dancing_hoppers.common.register;

import com.hexagram2021.dancing_hoppers.common.block.*;
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
    public static final RegistryObject<LateralHopperBlock> LATERAL_HOPPER = REGISTER.register(
            "lateral_hopper", () -> new LateralHopperBlock(BlockBehaviour.Properties.copy(Blocks.HOPPER))
    );
    public static final RegistryObject<AcceleratedHopperBlock> ACCELERATED_HOPPER = REGISTER.register(
            "accelerated_hopper", () -> new AcceleratedHopperBlock(BlockBehaviour.Properties.copy(Blocks.HOPPER))
    );
    public static final RegistryObject<AcceleratedInvertedHopperBlock> ACCELERATED_INVERTED_HOPPER = REGISTER.register(
            "accelerated_inverted_hopper", () -> new AcceleratedInvertedHopperBlock(BlockBehaviour.Properties.copy(Blocks.HOPPER))
    );
    public static final RegistryObject<AcceleratedLateralHopperBlock> ACCELERATED_LATERAL_HOPPER = REGISTER.register(
            "accelerated_lateral_hopper", () -> new AcceleratedLateralHopperBlock(BlockBehaviour.Properties.copy(Blocks.HOPPER))
    );

    public static final RegistryObject<ClassificationHopperBlock> CLASSIFICATION_HOPPER = REGISTER.register(
            "classification_hopper", () -> new ClassificationHopperBlock(BlockBehaviour.Properties.copy(Blocks.HOPPER))
    );
    public static final RegistryObject<EnhancedClassificationHopperBlock> ENHANCED_CLASSIFICATION_HOPPER = REGISTER.register(
            "enhanced_classification_hopper", () -> new EnhancedClassificationHopperBlock(BlockBehaviour.Properties.copy(Blocks.HOPPER))
    );

    public static final RegistryObject<InvertedClassificationHopperBlock> INVERTED_CLASSIFICATION_HOPPER = REGISTER.register(
            "inverted_classification_hopper", () -> new InvertedClassificationHopperBlock(BlockBehaviour.Properties.copy(Blocks.HOPPER))
    );
    public static final RegistryObject<InvertedEnhancedClassificationHopperBlock> INVERTED_ENHANCED_CLASSIFICATION_HOPPER = REGISTER.register(
            "inverted_enhanced_classification_hopper", () -> new InvertedEnhancedClassificationHopperBlock(BlockBehaviour.Properties.copy(Blocks.HOPPER))
    );
    public static final RegistryObject<LateralClassificationHopperBlock> LATERAL_CLASSIFICATION_HOPPER = REGISTER.register(
            "lateral_classification_hopper", () -> new LateralClassificationHopperBlock(BlockBehaviour.Properties.copy(Blocks.HOPPER))
    );
    public static final RegistryObject<LateralEnhancedClassificationHopperBlock> LATERAL_ENHANCED_CLASSIFICATION_HOPPER = REGISTER.register(
            "lateral_enhanced_classification_hopper", () -> new LateralEnhancedClassificationHopperBlock(BlockBehaviour.Properties.copy(Blocks.HOPPER))
    );

    private DHBlocks() {}

    public static void init(IEventBus bus) {
        REGISTER.register(bus);
    }
}