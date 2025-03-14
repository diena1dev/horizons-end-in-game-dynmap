package com.diena1dev.ingamedynmap

import com.cinemamod.mcef.MCEF
import com.cinemamod.mcef.MCEFBrowser
import com.diena1dev.ingamedynmap.HorizonsEndInGameDynmapClient.game

object BrowserFactory {
    lateinit var url: String
    val transparent: Boolean = true
    lateinit var browser: MCEFBrowser

    fun init() {
        if (!MCEF.isInitialized()) {
            MCEF.initialize()
        } else if (MCEF.isInitialized()) {
           println("empty for now")
        }
        url = "https://survival.horizonsend.net"
        browser = MCEF.createBrowser(url, transparent)
    }   // Creation of browser, called upon mod init - TODO: add a function to return the persistent // TODO: browser, or add an if under init for grabbing the variable for the RenderFactory

    object BrowserTools {

        fun mouseX(x: Double): Double {
            return (x * game.window.scaleFactor)
        }

        fun scaleX(x: Float): Double {
            return ((x * 2) * game.window.scaleFactor)
        }

        fun mouseY(y: Double): Double {
            return (y * game.window.scaleFactor)
        }

        fun scaleY(y: Float): Double {
            return ((y * 2) * game.window.scaleFactor)
        }

        @Suppress("unused")
        fun resizeBrowser() {
            if (windowWidth > 100 && windowHeight > 100) {
                browser.resize(scaleX(windowWidth).toInt(), scaleY(windowHeight).toInt())
            }
        }

        fun mouseClicked(mouseX: Double, mouseY: Double, button: Int): Boolean {
            if (button == 0) {
                browser.sendMousePress(mouseX(mouseX).toInt(), mouseY(mouseY).toInt(), button)
                browser.setFocus(true)
            } else if (button == 1) {
                // Add Call to ContextMenuRenderer
            }
            return mouseClicked(mouseX, mouseY, button)
        }  // 0 is printed for left click, 1 for right click.
        // make this an IF statement with the Widget class callable on right click, closed on left.

        fun mouseMoved(mouseX: Double, mouseY: Double) {
            browser.sendMouseMove(mouseX.toInt(), mouseY.toInt())
            mouseMoved(mouseX, mouseY)
        }

        fun mouseReleased(mouseX: Double, mouseY: Double, button: Int) {
            browser.sendMouseRelease(mouseX(mouseX).toInt(), mouseY(mouseY).toInt(), button)
            browser.setFocus(true)
            mouseReleased(mouseX, mouseY, button)
        }

        fun mouseDragged(mouseX: Double, mouseY: Double, button: Int, dragX: Double, dragY: Double) {
            mouseDragged(mouseX, mouseY, button, dragX, dragY)
        }

        fun mouseScrolled(mouseX: Double, mouseY: Double, verticalAmount: Double, horizontalAmount: Double) {
            browser.sendMouseWheel(mouseX(mouseX).toInt(), mouseY(mouseY).toInt(), verticalAmount, 0)
            mouseScrolled(mouseX, mouseY, verticalAmount, horizontalAmount)
        }

        /*
        @Override
        public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
            browser.sendKeyPress(keyCode, scanCode, modifiers);
            browser.setFocus(true);
            return super.keyPressed(keyCode, scanCode, modifiers);
        }

        @Override
        public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
            browser.sendKeyRelease(keyCode, scanCode, modifiers);
            browser.setFocus(true);
            return super.keyReleased(keyCode, scanCode, modifiers);
        }

        @Override
        public boolean charTyped(char codePoint, int modifiers) {
            if (codePoint == (char) 0) return false;
            browser.sendKeyTyped(codePoint, modifiers);
            browser.setFocus(true);
            return super.charTyped(codePoint, modifiers);
        }*/
    }
}