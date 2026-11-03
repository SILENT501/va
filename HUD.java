package com.varz.client.module.modules;

import com.varz.client.VarzClient;
import com.varz.client.module.*;
import com.varz.client.module.setting.*;
import com.varz.client.module.Module;
import net.minecraft.client.gui.DrawContext;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class HUD extends Module {
    private final BoolSetting watermark = add(new BoolSetting("Watermark", true));
    private final BoolSetting arrayList = add(new BoolSetting("Module list", true));
    private final BoolSetting coords = add(new BoolSetting("Coordinates", true));
    private final BoolSetting fps = add(new BoolSetting("FPS", true));

    public HUD() { super("HUD", "On-screen overlay with watermark, module list and info.", Category.RENDER); }

    @Override public void onRender2D(DrawContext ctx) {
        var tr = mc.textRenderer;
        int sw = mc.getWindow().getScaledWidth();
        int sh = mc.getWindow().getScaledHeight();

        if (watermark.get()) {
            String t = VarzClient.NAME + " " + VarzClient.VERSION;
            ctx.fill(4, 4, 10 + tr.getWidth(t), 16, 0x90150F2B);
            ctx.fill(4, 4, 6, 16, 0xFFA855F7);
            ctx.drawTextWithShadow(tr, t, 9, 6, 0xFFC4B5FD);
        }

        if (arrayList.get()) {
            List<String> names = new ArrayList<>();
            for (Module m : VarzClient.MODULES.all()) if (m.isEnabled() && m != this) names.add(m.name);
            names.sort(Comparator.comparingInt((String s) -> tr.getWidth(s)).reversed());
            int y = 4;
            for (String n : names) {
                int w = tr.getWidth(n);
                ctx.fill(sw - w - 8, y - 1, sw - 2, y + 10, 0x80150F2B);
                ctx.fill(sw - 3, y - 1, sw - 2, y + 10, 0xFFD946EF);
                ctx.drawTextWithShadow(tr, n, sw - w - 6, y, 0xFFE9D5FF);
                y += 11;
            }
        }

        int by = sh - 12;
        if (coords.get()) {
            ctx.drawTextWithShadow(tr, String.format("XYZ %.1f / %.1f / %.1f",
                mc.player.getX(), mc.player.getY(), mc.player.getZ()), 4, by, 0xFFA5F3FC);
            by -= 11;
        }
        if (fps.get()) ctx.drawTextWithShadow(tr, mc.getCurrentFps() + " FPS", 4, by, 0xFF86EFAC);
    }
}
