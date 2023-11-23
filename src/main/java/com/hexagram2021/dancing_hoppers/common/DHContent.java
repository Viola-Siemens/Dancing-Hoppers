package com.hexagram2021.dancing_hoppers.common;

import com.hexagram2021.dancing_hoppers.common.register.*;
import net.minecraftforge.eventbus.api.IEventBus;

public final class DHContent {
	public static void modConstruction(IEventBus bus) {
		DHBlocks.init(bus);
		DHItems.init(bus);
		DHBlockEntities.init(bus);
		DHCreativeModeTabs.init(bus);
	}

	private DHContent() {
	}
}
