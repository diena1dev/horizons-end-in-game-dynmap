package com.diena1dev.ingamedynmap

import com.cinemamod.mcef.MCEFBrowser
import com.mojang.blaze3d.systems.RenderSystem
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gl.ShaderProgramKeys
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.render.BufferBuilder
import net.minecraft.client.render.BufferRenderer
import net.minecraft.client.render.Tessellator
import net.minecraft.client.render.VertexFormat
import net.minecraft.client.render.VertexFormats
import net.minecraft.text.Text

val game: MinecraftClient = MinecraftClient.getInstance()
lateinit var browser: MCEFBrowser
lateinit var buffer: BufferBuilder
var windowWidth = game.window.scaledWidth
var windowHeight = game.window.scaledHeight
var alphaLevel = 255
var cornerScale = 20f
// Variables used in renderFactory

object RenderFactory {
    fun init() {
        browser = BrowserMain.init()
        RenderSystem.setShader(ShaderProgramKeys.POSITION_TEX_COLOR)
        RenderSystem.setShaderTexture(0, browser.getRenderer().getTextureID())

        val t: Tessellator = Tessellator.getInstance()
        buffer = t.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR)
    } // Init for ClientModInitializer - TODO: confirm this works

    class ScreenHandler : Screen(Text.literal("Screen")) {

    } // Handles the creation of Screens - TODO: finalize Screen Handling in a modular fashion

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