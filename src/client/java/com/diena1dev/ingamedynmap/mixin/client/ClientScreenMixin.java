package com.diena1dev.ingamedynmap.mixin.client;

import com.diena1dev.ingamedynmap.HorizonsEndInGameDynmap;
import net.minecraft.client.gui.screen.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Screen.class)
public class ClientScreenMixin {
    @Inject(at = @At("HEAD"), method = "onDisplayed")
    private void run(CallbackInfo info) {
        HorizonsEndInGameDynmap.INSTANCE.onInitialize();
    }
}