package com.diena1dev.ingamedynmap

import com.cinemamod.mcef.MCEFBrowser
import com.mojang.blaze3d.systems.RenderSystem
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gl.ShaderProgramKeys
import net.minecraft.client.gui.DrawContext
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.render.BufferBuilder
import net.minecraft.client.render.BufferRenderer
import net.minecraft.client.render.RenderTickCounter
import net.minecraft.client.render.Tessellator
import net.minecraft.client.render.VertexFormat
import net.minecraft.client.render.VertexFormats
import net.minecraft.text.Text

val game: MinecraftClient = MinecraftClient.getInstance()
lateinit var browser: MCEFBrowser
lateinit var buffer: BufferBuilder
var windowWidth = game.window.scaledWidth // Will be used for scaling of the ScreenHandler Class
var windowHeight = game.window.scaledHeight // Consider forwarding into BrowserFactory.
var alphaLevel = 255 // Change to a ConfigFactory.grabConfig(alphaLevel)
var cornerScale = 20f // ^
// Variables used in renderFactory

object RenderFactory {
    fun init() {
        browser = BrowserFactory.init()
        // Back to BrowserMain, add an if-null check, if false, return browser.
        // Alternatively, make a BrowserFactory.getBrowser() function, and if-null, run BrowserFactory.init() once.

        RenderSystem.setShader(ShaderProgramKeys.POSITION_TEX_COLOR)
        RenderSystem.setShaderTexture(0, browser.renderer.textureID)
        // this is important, but not necessary to nest in an if-null check.

        val t: Tessellator = Tessellator.getInstance()
        buffer = t.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR)
        // "buffer" should ONLY be declared **once**. change to be an "if" with a null check.

        HudRenderCallback.EVENT.register(RenderFactory.HUDHandler())
        // this also should ONLY be called once, to not register multiple HUD layers being rendered.
    } // Init for ClientModInitializer - TODO: confirm this works, finish BrowserMain

    class ScreenHandler : Screen(Text.literal("Screen")) {
        fun placeholder() {
            TODO("Replace function with a working Screen class.")
        }
    } // Handles the creation of Screens - TODO: finalize Screen Handling in a modular fashion

    class HUDHandler : HudRenderCallback {
        override fun onHudRender(
            drawContext: DrawContext?,
            tickCounter: RenderTickCounter?
        ) {
            TODO("Register HUD Rendering, attempt to add a toggle, under ConfigFactory")
        }

    }

    fun setMiniScale(scale: Float) {
        cornerScale = scale
    } // Take given Float and update Minimap scale - TODO: confirm this works

    fun renderAtCorner() {
        buffer.vertex(0f, cornerScale, 0f).texture(0.0f, 1.0f).color(255, 255, 255, alphaLevel) // Top Right
        buffer.vertex(0f, 0f, 0f).texture(1.0f, 1.0f).color(255, 255, 255, alphaLevel) // Top Left
        buffer.vertex(cornerScale, cornerScale, 0f).texture(1.0f, 0.0f).color(255, 255, 255, alphaLevel) // Bottom Right
        buffer.vertex(cornerScale, 1f, 0f).texture(0.0f, 0.0f).color(255, 255, 255, alphaLevel) // Bottom Left
        BufferRenderer.drawWithGlobalProgram(buffer.end());

    } // Renders browser at the top-left - TODO: allow browser orientation at the top-right

    fun renderFullscreen() {
        buffer.vertex(1f, 1f, 0f).texture(0.0f, 1.0f).color(255, 255, 255, alphaLevel) // Top Right
        buffer.vertex(1f, 1f, 0f).texture(1.0f, 1.0f).color(255, 255, 255, alphaLevel) // Top Left
        buffer.vertex(1f, 1f, 0f).texture(1.0f, 0.0f).color(255, 255, 255, alphaLevel) // Bottom Right
        buffer.vertex(1f, 1f, 0f).texture(0.0f, 0.0f).color(255, 255, 255, alphaLevel) // Bottom Left
        BufferRenderer.drawWithGlobalProgram(buffer.end());
    } // Renders the page on a screen - TODO: add automatic scale, call to BrowserMain for resizing
}