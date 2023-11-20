package com.hexagram2021.dancing_hoppers;

import com.hexagram2021.dancing_hoppers.common.DHContent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@SuppressWarnings("unused")
@Mod(DancingHoppers.MODID)
public class DancingHoppers {
	public static final String MODID = "dancing_hoppers";
	public static final String MODNAME = "Dancing Hoppers";
	public static final String VERSION = ModList.get().getModFileById(MODID).versionString();

	public DancingHoppers() {
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		DHContent.modConstruction(bus);

		MinecraftForge.EVENT_BUS.register(this);
	}
}
