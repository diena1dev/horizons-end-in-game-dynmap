import com.cinemamod.mcef.MCEFBrowser;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.Text;

public class HEBrowserContextMenu extends ClickableWidget {

    MCEFBrowser localBrowser;

    public HEBrowserContextMenu(int x, int y, int width, int height, MCEFBrowser browser) {
        super(x, y, width, height, Text.empty());
        localBrowser = browser;
    }

    @Override
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        int color = 0xFFFFFFFF; // WHITE lol
        final MinecraftClient client = MinecraftClient.getInstance();


        context.fill(getX(), getY(), getX() + this.width, getY() + this.height, color);
        context.drawText(client.textRenderer, "test", getX(), getY(), 16, false);
        /*if (ClickEvent.Action.RUN_COMMAND.isUserDefinable()) {
            //System.out.println("Click FOUND!"); // Spammy, does not detect clicks.
            localBrowser.find("Location:", true, true, false);
            localBrowser.stopFinding(false);
        }*/ // Play around with different click methods.... This will work, just have to give it time.
    }

    // TODO: RENDER THE BLOCK BEHIND THE TEXT AS A TEXTURE, should set it to a higher render layer with the context too!


    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {
        //TODO: later maybe, unsure.
    }
}
