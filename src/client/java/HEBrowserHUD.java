import com.cinemamod.mcef.MCEFBrowser;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.gl.ShaderProgramKeys;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// DONE: HUD Rendering needs to be implemented.
// and it has, so that's nice- issue is it's not ideal
// TODO: HUD Scale, Transparency, Toggles.
// TODO: OH! I could change the alpha value to 0 to "turn it off"!
// TODO: Not the most ideal, but it will work! :D


public class HEBrowserHUD implements HudRenderCallback {

    public HEBrowserHUD(MCEFBrowser browserMaster, boolean isMinimapOn) {
        browser = browserMaster;
        isMinimapOnLocal = isMinimapOn;
        logger.info("logged update");
        logger.info(String.valueOf(isMinimapOnLocal));
    }

    public final static Logger logger = LoggerFactory.getLogger("HEDynMap");

    public boolean isMinimapOnLocal;
    public MCEFBrowser browser;
    public Integer scaleFactor = 100; // MAKE THIS A CONFIG SLIDER
    public Integer transparencyLevel = 255; // THIS TOO!
    public boolean isOn;

    @Override
    public void onHudRender(DrawContext drawContext, RenderTickCounter tickCounter) {
        if (isMinimapOnLocal) {
            RenderSystem.setShader(ShaderProgramKeys.POSITION_TEX_COLOR);
            RenderSystem.setShaderTexture(0, browser.getRenderer().getTextureID());

            Tessellator t = Tessellator.getInstance();
            BufferBuilder buffer = t.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);
            buffer.vertex(1, scaleFactor, 0).texture(0.25f, 1.0f).color(255, 255, 255, transparencyLevel); // bottom left
            buffer.vertex(scaleFactor, scaleFactor, 0).texture(0.75f, 1.0f).color(255, 255, 255, transparencyLevel); // bottom right
            buffer.vertex(scaleFactor, 1, 0).texture(0.75f, 0.0f).color(255, 255, 255, transparencyLevel); // top right
            buffer.vertex(1, 1, 0).texture(0.25f, 0.0f).color(255, 255, 255, transparencyLevel); // top left
            BufferRenderer.drawWithGlobalProgram(buffer.end());
            logger.info(String.valueOf(isMinimapOnLocal));
        } else {
            BufferRenderer.reset();
            logger.info("actually did something");
        }
    }
}