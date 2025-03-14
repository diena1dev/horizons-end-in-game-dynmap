package com.diena1dev.ingamedynmap

import HEBrowser
import com.diena1dev.ingamedynmap.BrowserFactory.browser
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper
import net.minecraft.client.option.KeyBinding
import net.minecraft.client.util.InputUtil
import net.minecraft.text.Text
import org.lwjgl.glfw.GLFW

object KeybindFactory : ClientModInitializer {
    fun init() {
        val openInGameDynmap = KeyBindingHelper.registerKeyBinding(
            KeyBinding("Open Dynmap", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_X, "Crow's Magic Additions :3")
        ) // opens fullscreen map

        val toggleHUDMap = KeyBindingHelper.registerKeyBinding(
            KeyBinding("Toggle HUD Map", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_H, "Crow's Magic Additions :3")
        ) // toggles HUD minimap

        val refreshInGameDynmap = KeyBindingHelper.registerKeyBinding(
            KeyBinding("Refresh Dynmap (When Frozen)", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_BACKSLASH, "Crow's Magic Additions :3")
        ) // reloads browser when frozen

        ClientTickEvents.END_CLIENT_TICK.register(ClientTickEvents.EndTick { client ->
            while (openInGameDynmap.wasPressed()) {
                RenderFactory.updateWindowSize()
                minecraftClientInstance.setScreen(RenderFactory.ScreenHandler())
                //minecraftClientInstance.setScreen(HEBrowser(Text.literal("test"), browser)) // FOR DEMONSTRATION
                /*
                SO WHAT I'M NOW SEEING
                is that this does not make sense
                browser creation is fine, used it in the old HEBrowserHUD class
                it's the rendering that needs work! :D
                good to know.
                how the minimap works is it takes a bit of the fullscreen map screen and displays it
                it's nothing special
                 */
            }
        })
    }

    override fun onInitializeClient() {
        init()
    }
}
