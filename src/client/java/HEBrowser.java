import com.cinemamod.mcef.MCEF;
import com.cinemamod.mcef.MCEFBrowser;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.ShaderProgramKeys;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.render.*;
import net.minecraft.text.Text;

public class HEBrowser extends Screen {
    private static final int BROWSER_DRAW_OFFSET = 10;

    private final MinecraftClient minecraft = MinecraftClient.getInstance();

    public Boolean didStartStatus;

    public HEBrowser(Text title, MCEFBrowser browserMaster) {
        super(title);
        browser = browserMaster;
    }

    public MCEFBrowser browser;

    public String lastURL;

    @Override
    protected void init() {
        super.init();
        if (lastURL == null) {
            lastURL = "https://survival.horizonsend.net";
        } else {
            return;
        }
        if (browser == null) {
            String url = "https://survival.horizonsend.net";
            boolean trans = true;
            browser = MCEF.createBrowser(url, trans);
            resizeBrowser();
        } else {
            resizeBrowser();
        }
    }

    private int mouseX(double x) {
        return (int) ((x - BROWSER_DRAW_OFFSET) * minecraft.getWindow().getScaleFactor());
    }

    private int mouseY(double y) {
        return (int) ((y - BROWSER_DRAW_OFFSET) * minecraft.getWindow().getScaleFactor());
    }

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
    public void resize(MinecraftClient minecraft, int i, int j) {
        super.resize(minecraft, i, j);
        resizeBrowser();
    }

    @Override
    public void close() {
        lastURL = browser.getURL();
        super.close();
    }

    @Override
    public void render(DrawContext guiGraphics, int i, int j, float f) {
        super.render(guiGraphics, i, j, f);
        RenderSystem.setShader(ShaderProgramKeys.POSITION_TEX_COLOR);
        RenderSystem.setShaderTexture(0, browser.getRenderer().getTextureID());
        Tessellator t = Tessellator.getInstance();
        BufferBuilder buffer = t.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);;
        buffer.vertex(BROWSER_DRAW_OFFSET, height - BROWSER_DRAW_OFFSET, 0).texture(0.0f, 1.0f).color(255, 255, 255, 255);
        buffer.vertex(width - BROWSER_DRAW_OFFSET, height - BROWSER_DRAW_OFFSET, 0).texture(1.0f, 1.0f).color(255, 255, 255, 255);
        buffer.vertex(width - BROWSER_DRAW_OFFSET, BROWSER_DRAW_OFFSET, 0).texture(1.0f, 0.0f).color(255, 255, 255, 255);
        buffer.vertex(BROWSER_DRAW_OFFSET, BROWSER_DRAW_OFFSET, 0).texture(0.0f, 0.0f).color(255, 255, 255, 255);
        BufferRenderer.drawWithGlobalProgram(buffer.end());
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0) {
            browser.sendMousePress(mouseX(mouseX), mouseY(mouseY), button);
            browser.setFocus(true);
        } if (button == 1) {
            // browser.find(); // Find coordinates on page, copy them
            this.addDrawableChild(new HEBrowserContextMenu((int) mouseX, (int) mouseY,30, 15, browser));
        } return super.mouseClicked(mouseX, mouseY, button); // Not functioning as yet, soon though!
    }
    // 0 is printed for left click, 1 for right click. make this an IF statement with the Widget class callable on right click, closed on left.

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        browser.sendMouseRelease(mouseX(mouseX), mouseY(mouseY), button);
        browser.setFocus(true);
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public void mouseMoved(double mouseX, double mouseY) {
        browser.sendMouseMove(mouseX(mouseX), mouseY(mouseY));
        lastURL = browser.getURL();
        super.mouseMoved(mouseX, mouseY);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double verticalAmount, double horizontalAmount) {
        browser.sendMouseWheel(mouseX(mouseX), mouseY(mouseY), horizontalAmount, 0);
        return super.mouseScrolled(mouseX, mouseY, verticalAmount, horizontalAmount);
    }

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
    }
}
