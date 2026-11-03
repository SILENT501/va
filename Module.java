package com.varz.client.module;

import com.varz.client.gui.Toast;
import com.varz.client.module.setting.Setting;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;

import java.util.ArrayList;
import java.util.List;

public abstract class Module {
    protected static final MinecraftClient mc = MinecraftClient.getInstance();

    public final String name;
    public final String description;
    public final Category category;
    public final List<Setting<?>> settings = new ArrayList<>();

    private int key = -1;
    private boolean enabled;

    protected Module(String name, String description, Category category) {
        this.name = name;
        this.description = description;
        this.category = category;
    }

    protected <T extends Setting<?>> T add(T setting) {
        settings.add(setting);
        return setting;
    }

    public void toggle() { setEnabled(!enabled); }

    public void setEnabled(boolean value) {
        if (value == enabled) return;
        enabled = value;
        if (value) onEnable(); else onDisable();
        Toast.push(name, value);
    }

    public boolean isEnabled() { return enabled; }
    public int getKey() { return key; }
    public void setKey(int key) { this.key = key; }

    /** False for modules that are registered but have no behaviour yet. */
    public boolean isImplemented() { return true; }

    public void onEnable() {}
    public void onDisable() {}
    public void onTick() {}
    public void onRender2D(DrawContext ctx) {}
}
