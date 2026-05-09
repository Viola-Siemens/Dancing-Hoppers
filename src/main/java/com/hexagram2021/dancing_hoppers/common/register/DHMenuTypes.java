package com.hexagram2021.dancing_hoppers.common.register;
import com.hexagram2021.dancing_hoppers.common.block.entity.EnhancedClassificationHopperBlockEntity;
import com.hexagram2021.dancing_hoppers.common.block.entity.ClassificationHopperBlockEntity;
import com.hexagram2021.dancing_hoppers.common.inventory.EnhancedClassificationHopperMenu;
import com.hexagram2021.dancing_hoppers.common.inventory.ClassificationHopperMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import static com.hexagram2021.dancing_hoppers.DancingHoppers.MODID;
public final class DHMenuTypes {
    private static final DeferredRegister<MenuType<?>> REGISTER = DeferredRegister.create(ForgeRegistries.MENU_TYPES, MODID);
    public static final RegistryObject<MenuType<ClassificationHopperMenu>> CLASSIFICATION_HOPPER = REGISTER.register(
            "classification_hopper", () -> IForgeMenuType.create((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                if (inv.player.level().getBlockEntity(pos) instanceof ClassificationHopperBlockEntity hopper) {
                    return new ClassificationHopperMenu(windowId, inv, hopper);
                }
                return null;
            })
    );
    public static final RegistryObject<MenuType<EnhancedClassificationHopperMenu>> ENHANCED_CLASSIFICATION_HOPPER = REGISTER.register(
            "enhanced_classification_hopper", () -> IForgeMenuType.create((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                if (inv.player.level().getBlockEntity(pos) instanceof EnhancedClassificationHopperBlockEntity hopper) {
                    return new EnhancedClassificationHopperMenu(windowId, inv, hopper);
                }
                return null;
            })
    );
    public static void init(IEventBus bus) {
        REGISTER.register(bus);
    }
}