package com.diena1dev.ingamedynmap

import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper
import net.minecraft.client.MinecraftClient
import net.minecraft.client.option.KeyBinding
import net.minecraft.client.util.InputUtil
import HEBrowser
import HEBrowserHUD
import com.cinemamod.mcef.MCEF
import com.cinemamod.mcef.MCEFBrowser
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback
import net.minecraft.text.Text
import org.lwjgl.glfw.GLFW

// TODO: "if" check for existing browser instance for persistence, focus of browser window.
// TODO: event listener for leaving a world/disconnecting from a server, would terminate existing CEF processes.

val minecraftClientInstance: MinecraftClient = MinecraftClient.getInstance()

object HorizonsEndInGameDynmapClient : ClientModInitializer {

	override fun onInitializeClient() {

		val openInGameDynmap = KeyBindingHelper.registerKeyBinding(
			KeyBinding(
				"Open Dynmap",
				InputUtil.Type.KEYSYM,
				GLFW.GLFW_KEY_M,
				"Crow's Magic Additions :3"
			)
		)
		// The above sets the keybind to open the Dynmap Screen.

		val refreshInGameDynmap = KeyBindingHelper.registerKeyBinding(
			KeyBinding(
				"Refresh Dynmap (When Frozen)",
				InputUtil.Type.KEYSYM,
				GLFW.GLFW_KEY_R,
				"Crow's Magic Additions :3"
			)
		)
		// The above refreshes the browser if it ever hangs.

		MCEF.initialize()
		var url = "https://survival.horizonsend.net"
		var transparent: Boolean = true
		var browserMaster: MCEFBrowser = MCEF.createBrowser(url, transparent)
		if (MCEF.isInitialized() != false) {
			browserMaster = MCEF.createBrowser(url, transparent)
		}

		HudRenderCallback.EVENT.register(HEBrowserHUD(browserMaster))
		//HudLayerRegistrationCallback.EVENT.register(layeredDrawer -> layeredDrawer.attachLayerBefore(IdentifiedLayer.CHAT, EXAMPLE_LAYER, HEBrowserHUD::renderHUD))

		ClientTickEvents.END_CLIENT_TICK.register(ClientTickEvents.EndTick { client ->
			while (openInGameDynmap.wasPressed()) {
				minecraftClientInstance.setScreen(
					HEBrowser(
						Text.literal("test"), browserMaster
					))
			}
			while (refreshInGameDynmap.wasPressed()) {
				MCEF.shutdown()
				MCEF.initialize()
			}
		})
		// The above creates the Dynmap Screen whenever that keybind is pressed.
	}
}