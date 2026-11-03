package com.varz.client.module.modules;

import com.varz.client.module.Category;
import com.varz.client.module.Module;
import com.varz.client.module.setting.NumberSetting;
import net.minecraft.client.util.ScreenshotRecorder;

/** Automatically takes a screenshot on a repeating timer while enabled. */
public class ScreenshotHelper extends Module {
    private final NumberSetting interval = add(new NumberSetting("Every (s)", 5, 300, 30));
    private int ticks;

    public ScreenshotHelper() { super("Screenshot Helper", "Automatically takes a screenshot on a timer while enabled.", Category.MISC); }

    @Override public void onEnable() { ticks = 0; }

    @Override public void onTick() {
        if (++ticks < interval.i() * 20) return;
        ticks = 0;
        ScreenshotRecorder.saveScreenshot(mc.runDirectory, mc.getFramebuffer(), message -> {});
    }
}
