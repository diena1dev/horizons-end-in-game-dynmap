package com.diena1dev.ingamedynmap

import net.fabricmc.api.ClientModInitializer
import net.minecraft.client.MinecraftClient
import org.slf4j.LoggerFactory

// TODO: CRITICAL - Move keybindings to a seperate object, clean up this file.

val minecraftClientInstance: MinecraftClient = MinecraftClient.getInstance()

object HorizonsEndInGameDynmapClient : ClientModInitializer {

	val game: MinecraftClient = MinecraftClient.getInstance()

	override fun onInitializeClient() {
		val logger = LoggerFactory.getLogger("HEDynMap");
		logger.info("The Crow says hiya! ;3c")
		if (minecraftClientInstance == null) {
			onInitializeClient()
		} else {
			//ConfigFactory.init() // TODO: Finish ConfigFactory
			KeybindFactory.init()
			BrowserFactory.init()
			RenderFactory.init()

		}
	}
}