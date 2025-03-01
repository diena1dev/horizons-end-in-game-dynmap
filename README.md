# --= In-Game Dynmap for Horizon's End =--

# IMPORTANT: Until I update the mod to fix this, install MCEF and launch your game *once* fully, before installing this mod!

Utilizes the MCEF Example Mod for rendering the browser, depends on the MCEF mod.

### Version 0.2.1
In-Game Dynmap with persistence, when closed it opens back up to the same page!
NEW: Added a reload button that can be rebound, default is "R". This can be used whenever the browser gets frozen, but it will send you back to the Ilius map.

Very slight code-cleanup

## Note:
My mod depends on [Fabric Language Kotlin](https://modrinth.com/mod/fabric-language-kotlin) AND [MCEF](https://modrinth.com/mod/mcef/)

## TODO:

Browser scrolling : FIXED

Context menu on right-click : TODO

Versatile interactive button context menu Class : TODO (Ties into above TODO)

Automatically reload the browser every five minutes (Measureed from Ticks?) : TODO

Remember last visited URL for Dynmap page persistence : TODO (This is needed for the page persistence upon auutomatic reloads)

Background browser persistince, not creating a new browser instance every screen toggle : COMPLETE! (Currently functioning great!)
Website toggle, between Wiki and Dynmap : TODO

Browser page scale : TODO

Add error on missing dependency for MCEF : TODO
