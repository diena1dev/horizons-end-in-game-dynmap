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
import org.slf4j.LoggerFactory

// TODO: event listener for leaving a world/disconnecting from a server, would terminate existing CEF processes.

// TODO: CRITICAL - Move keybindings to a seperate object, clean up this file.

val minecraftClientInstance: MinecraftClient = MinecraftClient.getInstance()

object HorizonsEndInGameDynmapClient : ClientModInitializer {

	override fun onInitializeClient() {

		val logger = LoggerFactory.getLogger("HEDynMap");

		val openInGameDynmap = KeyBindingHelper.registerKeyBinding(
			KeyBinding("Open Dynmap", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_X, "Crow's Magic Additions :3")
		) // opens fullscreen map

		val toggleHUDMap = KeyBindingHelper.registerKeyBinding(
			KeyBinding("Toggle HUD Map", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_H, "Crow's Magic Additions :3")
		) // toggles HUD minimap

		val refreshInGameDynmap = KeyBindingHelper.registerKeyBinding(
			KeyBinding("Refresh Dynmap (When Frozen)", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_BACKSLASH, "Crow's Magic Additions :3")
		) // reloads browser when frozen

		MCEF.initialize()
		var url = "https://survival.horizonsend.net"
		var transparent= true
		var browserMaster: MCEFBrowser = MCEF.createBrowser(url, transparent)
		if (MCEF.isInitialized() != false) {
			browserMaster = MCEF.createBrowser(url, transparent)
		}
		var isMinimapOn = true

		HudRenderCallback.EVENT.register(HEBrowserHUD(browserMaster, isMinimapOn))
		// Registers and adds the minimap HUD element to the screen

		ClientTickEvents.END_CLIENT_TICK.register(ClientTickEvents.EndTick { client ->
			while (openInGameDynmap.wasPressed()) {
				minecraftClientInstance.setScreen(
					HEBrowser(
						Text.literal("test"), browserMaster
					))
			}

			while (refreshInGameDynmap.wasPressed()) {
				browserMaster.reload()
			} // reloads, not refreshes browser
			// to refresh, add a check to see if the HUD is toggled to "true", if so then disable HUD, shutdown and initialize,
			// set HUD back to previous variable.

			while (toggleHUDMap.wasPressed()) {
				isMinimapOn = if (isMinimapOn == false) { true } else { false }
				logger.info(isMinimapOn.toString(), "from keybind")
				HEBrowserHUD(browserMaster, isMinimapOn)
			} // toggles minimap rendering
		})
	}
}