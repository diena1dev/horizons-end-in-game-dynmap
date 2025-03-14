package com.diena1dev.ingamedynmap

import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper
import net.minecraft.client.option.KeyBinding
import net.minecraft.client.util.InputUtil
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
            }
        })
    }

    override fun onInitializeClient() {
        init()
    }
}
