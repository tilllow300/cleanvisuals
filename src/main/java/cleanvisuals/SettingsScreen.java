package cleanvisuals;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.option.GameOptions;
import net.minecraft.text.Text;

public class SettingsScreen extends Screen {
    public SettingsScreen() {
        super(Text.literal("Clean Visuals"));
    }

    private static Text label(String name, boolean on) {
        return Text.literal(name + ": " + (on ? "AN" : "AUS"));
    }

    @Override
    protected void init() {
        int x = this.width / 2 - 100;
        int y = this.height / 2 - 40;

        addDrawableChild(ButtonWidget.builder(label("Clean Hitting", Config.cleanHitting), b -> {
            Config.cleanHitting = !Config.cleanHitting;
            Config.save();
            b.setMessage(label("Clean Hitting", Config.cleanHitting));
        }).dimensions(x, y, 200, 20).build());

        addDrawableChild(ButtonWidget.builder(Text.literal("Schoeneres Spielbild anwenden"), b -> applyBetterLook())
                .dimensions(x, y + 28, 200, 20).build());

        addDrawableChild(ButtonWidget.builder(Text.literal("Fertig"), b -> close())
                .dimensions(x, y + 66, 200, 20).build());
    }

    // Setzt einmalig ein paar normale Grafikoptionen auf huebschere Werte.
    private static void applyBetterLook() {
        MinecraftClient mc = MinecraftClient.getInstance();
        GameOptions o = mc.options;
        o.getAo().setValue(true);               // weiche Beleuchtung
        o.getBiomeBlendRadius().setValue(7);    // weichere Biome-Uebergaenge
        o.getEntityShadows().setValue(true);    // Schatten unter Mobs/Spielern
        o.write();
        if (mc.worldRenderer != null) {
            mc.worldRenderer.reload();
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, this.height / 2 - 70, 0xFFFFFFFF);
    }
}
