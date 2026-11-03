package com.varz.client.module.setting;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

import java.util.List;

public class ModeSetting extends Setting<String> {
    public final List<String> modes;
    public ModeSetting(String name, String def, String... modes) {
        super(name, def);
        this.modes = List.of(modes);
    }
    public void cycle() { value = modes.get((modes.indexOf(value) + 1) % modes.size()); }
    @Override public JsonElement save() { return new JsonPrimitive(value); }
    @Override public void load(JsonElement e) {
        String s = e.getAsString();
        if (modes.contains(s)) value = s;
    }
}
