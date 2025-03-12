package com.diena1dev.ingamedynmap

import com.cinemamod.mcef.MCEF
import com.cinemamod.mcef.MCEFBrowser

object BrowserMain {
    fun init(): MCEFBrowser {
        val transparent = false
        val url = ""
        lateinit var browser: MCEFBrowser

        if (MCEF.getClient() == null) {
            browser = MCEF.createBrowser(url, transparent)
        }
        return browser
    }
}