package com.diena1dev.ingamedynmap

import com.diena1dev.ingamedynmap.*
import com.cinemamod.mcef.MCEF
import com.cinemamod.mcef.MCEFBrowser
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.render.*
import net.minecraft.text.Text

class HEBrowser: Screen {
    val browserDrawOffset = 5
    val browser: MCEFBrowser? = null
    val minecraft = MinecraftClient.getInstance()

    override fun init() {
        init()
        if (browser == null) {
            val url = "https://survival.horizonsend.net"
            val transparent = true
            val browser = MCEF.createBrowser(url, transparent)
            //resizeBrowser() TODO
        }
    }

    val browserMouseXY: Double = (browserDrawOffset)*(minecraft.getWindow().getScaleFactor())
    //val browserMouseY: Double = (browserDrawOffset)*(minecraft.getWindow().getScaleFactor())
    val browserScaleXY: Double = (browserDrawOffset*2)*((minecraft.getWindow().getScaleFactor())
    //val browserScaleY: Double =

    fun HEBrowser(title: Text?) {}

}