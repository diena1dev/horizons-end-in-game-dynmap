package com.diena1dev.ingamedynmap

import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper
import net.minecraft.client.MinecraftClient
import net.minecraft.client.option.KeyBinding
import net.minecraft.client.util.InputUtil
import HEBrowser
import net.minecraft.text.Text
import org.lwjgl.glfw.GLFW

// TODO: "if" check for existing browser instance for persistence, focus of browser window.
// TODO: event listener for leaving a world/disconnecting from a server, would terminate existing CEF processes.

// -- Keybindings + Tick Event Registration --

class HEDynmapClient():ClientModInitializer {
	override fun onInitializeClient() {
		//ClientTickEvents.START_CLIENT_TICK.register(MineCraftClient onTick()) TODO: FIX THIS
	}

}

val minecraftClientInstance: MinecraftClient = MinecraftClient.getInstance()

object HorizonsEndInGameDynmapClient : ClientModInitializer {
	override fun onInitializeClient() {
		val openInGameDynmap = KeyBindingHelper.registerKeyBinding(
			KeyBinding(
				"Open Dynmap",
				InputUtil.Type.KEYSYM,
				GLFW.GLFW_KEY_M,
				"HE In-Game Dynmap"
			)
		)

		ClientTickEvents.END_CLIENT_TICK.register(ClientTickEvents.EndTick { client ->
			while (openInGameDynmap.wasPressed()) {
				minecraftClientInstance.setScreen(
					HEBrowser(
					Text.literal("test") // TODO: FIX SCREEN CREATION, FIXED.
				));
			}
		})
	}
}