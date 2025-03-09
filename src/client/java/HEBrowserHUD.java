import com.cinemamod.mcef.MCEFBrowser;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.ShaderProgramKeys;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.*;

// TODO: HUD Rendering needs to be implemented.


public class HEBrowserHUD implements HudRenderCallback {

    public HEBrowserHUD(MCEFBrowser browserMaster) {
        browser = browserMaster;
    }

    public MCEFBrowser browser;
    public MinecraftClient minecraft = MinecraftClient.getInstance();
    public Integer BROWSER_DRAW_OFFSET = 2;
    public Integer width = 0;
    public Integer height = 0;
    public Integer scaleFactor = 100; // MAKE THIS A CONFIG SLIDER
    public Integer transparencyLevel = 255; // THIS TOO!

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
        MinecraftClient client = MinecraftClient.getInstance();
        if (client != null) {
            height = client.getWindow().getScaledHeight();
            width = client.getWindow().getScaledWidth();
        }

        RenderSystem.setShader(ShaderProgramKeys.POSITION_TEX_COLOR);
        RenderSystem.setShaderTexture(0, browser.getRenderer().getTextureID());

        Tessellator t = Tessellator.getInstance();
        BufferBuilder buffer = t.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);
        buffer.vertex(1, scaleFactor, 0).texture(0.25f, 1.0f).color(255, 255, 255, transparencyLevel); // bottom left
        buffer.vertex(scaleFactor, scaleFactor, 0).texture(0.75f, 1.0f).color(255, 255, 255, transparencyLevel); // bottom right
        buffer.vertex(scaleFactor,1, 0).texture(0.75f, 0.0f).color(255, 255, 255, transparencyLevel); // top right
        buffer.vertex(1, 1, 0).texture(0.25f, 0.0f).color(255, 255, 255, transparencyLevel); // top left
        BufferRenderer.drawWithGlobalProgram(buffer.end());
    }
}