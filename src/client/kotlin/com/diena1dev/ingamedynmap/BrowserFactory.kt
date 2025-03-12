package com.diena1dev.ingamedynmap

import com.cinemamod.mcef.MCEF
import com.cinemamod.mcef.MCEFBrowser

object BrowserFactory {
    fun init(): MCEFBrowser {
        val transparent = false
        val url = ""
        lateinit var browser: MCEFBrowser

        if (MCEF.getClient() == null) {
            browser = MCEF.createBrowser(url, transparent)
        }
        return browser
    } // Creation of browser, called upon mod init - TODO: add a function to return the persistent
      // TODO: browser, or add an if under init for grabbing the variable for the RenderFactory

    class BrowserHandler() {

    }
}