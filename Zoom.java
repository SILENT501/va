package com.varz.client.module.modules;

import com.varz.client.module.Category;
import com.varz.client.module.Module;
import com.varz.client.module.setting.NumberSetting;

/** Narrows your FOV while toggled on and restores it on disable. */
public class Zoom extends Module {
    private final NumberSetting fov = add(new NumberSetting("Zoom FOV", 5, 50, 15));
    private int originalFov = -1;

    public Zoom() { super("Zoom", "Narrows your field of view so you can see further while toggled on.", Category.RENDER); }

    @Override public void onEnable() {
        originalFov = mc.options.getFov().getValue();
        mc.options.getFov().setValue(fov.i());
    }

    @Override public void onTick() {
        mc.options.getFov().setValue(fov.i());
    }

    @Override public void onDisable() {
        if (originalFov >= 0) mc.options.getFov().setValue(originalFov);
        originalFov = -1;
    }
}
