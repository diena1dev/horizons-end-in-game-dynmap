package com.diena1dev.ingamedynmap

import HEBrowserHUD
import com.diena1dev.ingamedynmap.BrowserFactory.BrowserTools.mouseX
import com.diena1dev.ingamedynmap.BrowserFactory.BrowserTools.mouseY
import com.diena1dev.ingamedynmap.BrowserFactory.browser
import com.diena1dev.ingamedynmap.HorizonsEndInGameDynmapClient.game
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
import java.nio.Buffer

//lateinit var t: Tessellator
//lateinit var buffer: BufferBuilder
var windowWidth = game.window.scaledWidth.toFloat() // Will be used for scaling of the ScreenHandler Class
var windowHeight = game.window.scaledHeight.toFloat() // Consider forwarding into BrowserFactory.
var alphaLevel = 255 // Change to a ConfigFactory.grabConfig(alphaLevel)
var cornerScale = 20f // ^

@Suppress("unused")
object RenderFactory {
    @Suppress("unused")
    fun init() {

        //HudRenderCallback.EVENT.register(HEBrowserHUD(browser)) // please don't use this

        if (game.shaderLoader != null) {
            println("test")
            // this is important, but not necessary to nest in an if-null check.


            // "buffer" should ONLY be declared **once**. change to be an "if" with a null check.

            //HudRenderCallback.EVENT.register(HUDHandler()) // TODO: FIX THIS
            // this also should ONLY be called once, to not register multiple HUD layers being rendered.
        }
    } // Init for ClientModInitializer - TODO: confirm this works, finish BrowserMain

    @Suppress("unused")
    fun setMiniScale(scale: Float) {
        cornerScale = scale
    } // Take given Float and update Minimap scale - TODO: confirm this works

    @Suppress("unused")
    fun updateWindowSize() {
        windowWidth = game.window.scaledWidth.toFloat()
        windowHeight = game.window.scaledHeight.toFloat()
    }

    @Suppress("unused")
    class ScreenHandler : Screen(Text.literal("Test")) {
        override fun render(context: DrawContext, i: Int, j: Int, f: Float) {
            super.render(context, i, j, f)
            RenderSystem.setShader(ShaderProgramKeys.POSITION_TEX_COLOR)
            RenderSystem.setShaderTexture(0, browser.renderer.textureID)
            val t = Tessellator.getInstance()
            val buffer = t.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR)

            buffer.vertex(1f, height - 1f, 0f).texture(0.0f, 1.0f).color(255, 255, 255, alphaLevel) // Bottom Left
            buffer.vertex(width - 1f, height - 1f, 0f).texture(1.0f, 1.0f).color(255, 255, 255, alphaLevel) // Bottom Right
            buffer.vertex(width - 1f, 1f, 0f).texture(1.0f, 0.0f).color(255, 255, 255, alphaLevel) // Top Right
            buffer.vertex(1f, 1f, 0f).texture(0.0f, 0.0f).color(255, 255, 255, alphaLevel) // Top Left
            BufferRenderer.drawWithGlobalProgram(buffer.end())

        } // Handles the creation of Screens - TODO: fix

        override fun resize(client: MinecraftClient?, width: Int, height: Int) {
            super.resize(client, width, height)
            BrowserFactory.BrowserTools.resizeBrowser()
        }

        override fun mouseClicked(mouseX: Double, mouseY: Double, button: Int): Boolean {
            if (button == 0) {
                browser.sendMousePress(mouseX(mouseX).toInt(), mouseY(mouseY).toInt(), button)
                browser.setFocus(true)
            } else if (button == 1) {
                // Add Call to ContextMenuRenderer
            }
            return mouseClicked(mouseX, mouseY, button)
        }  // 0 is printed for left click, 1 for right click.

        override fun mouseMoved(mouseX: Double, mouseY: Double) {
            browser.sendMouseMove(mouseX.toInt(), mouseY.toInt())
            //return mouseMoved(mouseX, mouseY)
        }

        override fun mouseReleased(mouseX: Double, mouseY: Double, button: Int): Boolean {
            browser.sendMouseRelease(mouseX(mouseX).toInt(), mouseY(mouseY).toInt(), button)
            browser.setFocus(true)
            return mouseReleased(mouseX, mouseY, button)
        }

        override fun mouseDragged(mouseX: Double, mouseY: Double, button: Int, dragX: Double, dragY: Double): Boolean {
            return mouseDragged(mouseX, mouseY, button, dragX, dragY)
        }

        override fun mouseScrolled(mouseX: Double, mouseY: Double, verticalAmount: Double, horizontalAmount: Double): Boolean {
            browser.sendMouseWheel(mouseX(mouseX).toInt(), mouseY(mouseY).toInt(), verticalAmount, 0)
            return mouseScrolled(mouseX, mouseY, verticalAmount, horizontalAmount)
        }

    } // Renders the page on a screen - TODO: add automatic scale, call to BrowserMain for resizing

    @Suppress("unused")
    class HUDHandler : HudRenderCallback {
        override fun onHudRender(
            drawContext: DrawContext?,
            tickCounter: RenderTickCounter?
        ) {
            RenderSystem.setShader(ShaderProgramKeys.POSITION_TEX_COLOR)
            RenderSystem.setShaderTexture(0, browser.renderer.textureID)
            val t = Tessellator.getInstance()
            val buffer = t.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR)

            buffer.vertex(0f, cornerScale, 0f).texture(0.0f, 1.0f).color(255, 255, 255, alphaLevel) // Top Right
            buffer.vertex(0f, 0f, 0f).texture(1.0f, 1.0f).color(255, 255, 255, alphaLevel) // Top Left
            buffer.vertex(cornerScale, cornerScale, 0f).texture(1.0f, 0.0f).color(255, 255, 255, alphaLevel) // Bottom Right
            buffer.vertex(cornerScale, 1f, 0f).texture(0.0f, 0.0f).color(255, 255, 255, alphaLevel) // Bottom Left
            BufferRenderer.drawWithGlobalProgram(buffer.end())
        } // TODO: Register HUD Rendering, attempt to add a toggle, under ConfigFactory
    } // Renders browser at the top-left - TODO: allow browser orientation at the top-right

    /*class ContextMenuHandler : ClickableWidget() {
        override fun renderWidget(
            context: DrawContext?,
            mouseX: Int,
            mouseY: Int,
            delta: Float
        ) {
            TODO("Not yet implemented")
        }

        override fun appendClickableNarrations(builder: NarrationMessageBuilder?) {
            TODO("Not yet implemented")
        }

    }// TODO: Implement this */
}