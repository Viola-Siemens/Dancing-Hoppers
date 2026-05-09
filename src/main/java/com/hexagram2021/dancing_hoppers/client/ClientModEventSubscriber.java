package com.hexagram2021.dancing_hoppers.client;
import com.hexagram2021.dancing_hoppers.client.gui.ClassificationHopperScreen;
import com.hexagram2021.dancing_hoppers.client.gui.EnhancedClassificationHopperScreen;
import com.hexagram2021.dancing_hoppers.common.register.DHMenuTypes;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import static com.hexagram2021.dancing_hoppers.DancingHoppers.MODID;
@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEventSubscriber {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            MenuScreens.register(DHMenuTypes.CLASSIFICATION_HOPPER.get(), ClassificationHopperScreen::new);
            MenuScreens.register(DHMenuTypes.ENHANCED_CLASSIFICATION_HOPPER.get(), EnhancedClassificationHopperScreen::new);
        });
    }
}