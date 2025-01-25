package com.diena1dev.ingamedynmap

import com.cinemamod.mcef.*
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.DrawContext
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.gui.widget.ButtonWidget
import net.minecraft.client.option.KeyBinding
import net.minecraft.client.realms.task.LongRunningTask.setScreen
import net.minecraft.client.render.*
import net.minecraft.client.toast.SystemToast
import net.minecraft.client.util.InputUtil
import net.minecraft.text.Text
import org.lwjgl.glfw.GLFW


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
				MinecraftClient.getInstance().setScreen(
					HEBrowser(Text.empty())
				);
			}
		})
	}
}

class HEBrowser(title: Text) : Screen(title) {
	override fun init() {
		val url = "https://youtube.com	"
		val client = com.cinemamod.mcef.MCEF.getClient()
		//com.cinemamod.mcef.MCEFBrowser(client, url, false)
		com.cinemamod.mcef.MCEF.createBrowser(url, false)
	}
}

// testing below

/*
class CustomScreen(title: Text?) : Screen(title) {
	override fun init() {
		val buttonWidget: ButtonWidget = ButtonWidget.builder(Text.of("Hello World")) { btn ->
			// When the button is clicked, we can display a toast to the screen.
			client!!.toastManager.add(
				SystemToast.create(
					this.client,
					SystemToast.Type.NARRATOR_TOGGLE,
					Text.of("Hello World!"),
					Text.of("This is a toast.")
				)
			)
		}.dimensions(40, 40, 120, 20).build()

		// x, y, width, height
		// It's recommended to use the fixed height of 20 to prevent rendering issues with the button
		// textures.

		// Register the button widget.
		this.addDrawableChild(buttonWidget)
	}

	override fun render(context: DrawContext, mouseX: Int, mouseY: Int, delta: Float) {
		super.render(context, mouseX, mouseY, delta)

		// Minecraft doesn't have a "label" widget, so we'll have to draw our own text.
		// We'll subtract the font height from the Y position to make the text appear above the button.
		// Subtracting an extra 10 pixels will give the text some padding.
		// textRenderer, text, x, y, color, hasShadow
		context.drawText(this.textRenderer, "Special Button", 40, 40 - textRenderer.fontHeight - 10, -0x1, true)
	}
}*/