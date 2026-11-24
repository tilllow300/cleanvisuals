package cleanvisuals;

import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public final class Config {
    public static boolean cleanHitting = true;

    private static Path file() {
        return FabricLoader.getInstance().getConfigDir().resolve("cleanvisuals.properties");
    }

    public static void load() {
        try {
            Path f = file();
            if (!Files.exists(f)) return;
            Properties p = new Properties();
            try (var in = Files.newInputStream(f)) { p.load(in); }
            cleanHitting = Boolean.parseBoolean(p.getProperty("cleanHitting", "true"));
        } catch (IOException ignored) { }
    }

    public static void save() {
        try {
            Properties p = new Properties();
            p.setProperty("cleanHitting", Boolean.toString(cleanHitting));
            try (var out = Files.newOutputStream(file())) { p.store(out, "Clean Visuals"); }
        } catch (IOException ignored) { }
    }

    private Config() { }
}
