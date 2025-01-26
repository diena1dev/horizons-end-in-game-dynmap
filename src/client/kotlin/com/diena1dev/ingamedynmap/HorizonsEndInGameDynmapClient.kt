package com.diena1dev.ingamedynmap

import com.cinemamod.mcef.MCEFBrowser
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.DrawContext
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.option.KeyBinding
import net.minecraft.client.util.InputUtil
import net.minecraft.text.Text
import org.lwjgl.glfw.GLFW


// -- Keybindings + Tick Event Registration --

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
					HEBrowser(Text.empty(), "https://youtube.com")
				);
			}
		})
	}
}

// -- Variables --

// nothing yet!

// -- In-Game Browser Renderer --

// class

class HEBrowser(title: Text, url: String) : Screen(title) {
	override fun init() {

		val BROWSER_DRAW_OFFSET = 20
		val minecraft = MinecraftClient.getInstance()
		val browser: MCEFBrowser? = null

		val client = com.cinemamod.mcef.MCEF.getClient()
		var url: String = "https://survival.horizonsend.net"
		com.cinemamod.mcef.MCEF.createBrowser(url, false)
		if (browser != null) {
			resizeBrowser(browser)
		}
	}

	// variables

	private fun mouseX(x: Double, BROWSER_DRAW_OFFSET: Int, minecraft: MinecraftClient): Double {
		return ((x - BROWSER_DRAW_OFFSET) * minecraft.getWindow().getScaleFactor())
	}

	private fun mouseY(y: Double, BROWSER_DRAW_OFFSET: Int, minecraft: MinecraftClient): Double {
		return ((y - BROWSER_DRAW_OFFSET) * minecraft.getWindow().getScaleFactor())
	}

	private fun scaleX(x: Double, BROWSER_DRAW_OFFSET: Int, minecraft: MinecraftClient): Double {
		return ((x - BROWSER_DRAW_OFFSET * 2) * minecraft.getWindow().getScaleFactor())
	}

	private fun scaleY(y: Double, BROWSER_DRAW_OFFSET: Int, minecraft: MinecraftClient): Double {
		return ((y - BROWSER_DRAW_OFFSET * 2) * minecraft.getWindow().getScaleFactor())
	}

	// functions

fun resizeBrowser(browser: MCEFBrowser) {
		if (width > 100 && height > 100) {
			browser.resize(100, 200)
			resizeBrowser(browser)
		}
	}

	// rendering

	override fun render(context: DrawContext, mouseX: Int, mouseY: Int, delta: Float) {
		super.render(context, mouseX, mouseY, delta)

		context.drawBorder(10, 10, 400, 200, 0xFFFF000)
		context.fill(11, 1, 398, 198, 0xFF00FF0)

	}
}



// testing below
/*class HEBrowser(title: Text, url: String) : Screen(title) {
	override fun init() {
			super.init();
				val url = "https://www.google.com"
				val transparent = true
				val browser = com.cinemamod.mcef.MCEF.createBrowser(url, transparent)
				//resizeBrowser()
	}

	override fun render(context: DrawContext, mouseX: Int, mouseY: Int, delta: Float) {
		super.render(context, mouseX, mouseY, delta)

		context.drawBorder(10, 10, 100, 50, 3)
	}
}
*/



/*class CustomScreen(title: Text?) : Screen(title) {
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