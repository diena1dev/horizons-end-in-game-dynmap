import com.cinemamod.mcef.MCEF;
import com.cinemamod.mcef.MCEFBrowser;
import com.diena1dev.ingamedynmap.mixin.HEDynmapMixin;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.ShaderProgramKeys;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.LayeredDrawer;
import net.minecraft.client.render.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.MathHelper;

import java.nio.Buffer;

import static net.minecraft.client.realms.gui.screen.RealmsAcceptRejectButton.render;

// TODO: HUD Rendering needs to be implemented.


public class HudRenderingEntrypoint implements HudRenderCallback {

    public HudRenderingEntrypoint(MCEFBrowser browserMaster) {
        browser = browserMaster;
    }

    public MCEFBrowser browser;
    public MinecraftClient minecraft = MinecraftClient.getInstance();
    public Integer BROWSER_DRAW_OFFSET = 2;
    public Integer width;
    public Integer height;

    private int scaleX(double x) {
        return (int) ((x - BROWSER_DRAW_OFFSET * 2) * minecraft.getWindow().getScaleFactor());
    }

    private int scaleY(double y) {
        return (int) ((y - BROWSER_DRAW_OFFSET * 2) * minecraft.getWindow().getScaleFactor());
    }

    private void resizeBrowser() {
        if (width > 100 && height > 100) {
            browser.resize(scaleX(width), scaleY(height));
        }
    }

    @Override
    public void onHudRender(DrawContext drawContext, RenderTickCounter tickCounter) {
        RenderSystem.setShader(ShaderProgramKeys.POSITION_TEX_COLOR);
        RenderSystem.setShaderTexture(0, browser.getRenderer().getTextureID());
        Tessellator t = Tessellator.getInstance();
        BufferBuilder buffer = t.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);
        buffer.vertex(BROWSER_DRAW_OFFSET, height - BROWSER_DRAW_OFFSET, 0).texture(0.0f, 1.0f).color(255, 255, 255, 255);
        buffer.vertex(width - BROWSER_DRAW_OFFSET, height - BROWSER_DRAW_OFFSET, 0).texture(1.0f, 1.0f).color(255, 255, 255, 255);
        buffer.vertex(width - BROWSER_DRAW_OFFSET, BROWSER_DRAW_OFFSET, 0).texture(1.0f, 0.0f).color(255, 255, 255, 255);
        /*buffer.vertex(10, 10, 0).texture(0.0f, 0.0f).color(255, 255, 255, 255);
        buffer.vertex(170, 10, 0).texture(0.0f, 0.0f).color(255, 255, 255, 255);
        buffer.vertex(10, 100, 0).texture(0.0f, 0.0f).color(255, 255, 255, 255);
        buffer.vertex(170, 100, 0).texture(0.0f, 0.0f).color(255, 255, 255, 255);*/
        BufferRenderer.drawWithGlobalProgram(buffer.end());
    }
}