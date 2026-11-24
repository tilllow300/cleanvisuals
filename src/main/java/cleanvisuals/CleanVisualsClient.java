package cleanvisuals;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

public class CleanVisualsClient implements ClientModInitializer {
    private static KeyBinding openMenu;

    @Override
    public void onInitializeClient() {
        Config.load();

        // Eingebautes Pack: weicherer Armor-Glint (im Ressourcenpakete-Menue ein-/ausschaltbar)
        FabricLoader.getInstance().getModContainer("cleanvisuals").ifPresent(mod ->
                ResourceManagerHelper.registerBuiltinResourcePack(
                        Identifier.of("cleanvisuals", "smooth_glint"), mod,
                        Text.literal("Clean Visuals: Armor Glide"),
                        ResourcePackActivationType.DEFAULT_ENABLED));

        openMenu = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.cleanvisuals.open",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT_SHIFT,
                KeyBinding.Category.MISC));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (openMenu.wasPressed()) {
                if (client.currentScreen == null) {
                    client.setScreen(new SettingsScreen());
                }
            }
        });
    }
}
