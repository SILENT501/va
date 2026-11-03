package com.varz.client;

import com.google.gson.*;
import com.varz.client.module.Module;
import com.varz.client.module.ModuleManager;
import com.varz.client.module.setting.Setting;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/** Saves / loads module state, keybinds and settings to config/varzclient.json. */
public final class Config {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static Path file() {
        return FabricLoader.getInstance().getConfigDir().resolve("varzclient.json");
    }

    public static void save(ModuleManager mm) {
        JsonObject root = new JsonObject();
        for (Module m : mm.all()) {
            JsonObject o = new JsonObject();
            o.addProperty("enabled", m.isEnabled());
            o.addProperty("key", m.getKey());
            JsonObject s = new JsonObject();
            for (Setting<?> st : m.settings) s.add(st.name, st.save());
            o.add("settings", s);
            root.add(m.name, o);
        }
        try {
            Files.writeString(file(), GSON.toJson(root));
        } catch (IOException e) {
            VarzClient.LOGGER.error("Could not save config", e);
        }
    }

    public static void load(ModuleManager mm) {
        Path f = file();
        if (!Files.exists(f)) return;
        try {
            JsonObject root = JsonParser.parseString(Files.readString(f)).getAsJsonObject();
            for (Module m : mm.all()) {
                if (!root.has(m.name)) continue;
                JsonObject o = root.getAsJsonObject(m.name);
                if (o.has("key")) m.setKey(o.get("key").getAsInt());
                if (o.has("settings")) {
                    JsonObject s = o.getAsJsonObject("settings");
                    for (Setting<?> st : m.settings) if (s.has(st.name)) st.load(s.get(st.name));
                }
                if (o.has("enabled")) m.setEnabled(o.get("enabled").getAsBoolean());
            }
        } catch (Exception e) {
            VarzClient.LOGGER.error("Could not load config", e);
        }
    }
}
