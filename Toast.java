package com.varz.client.gui;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;

import java.util.ArrayList;
import java.util.List;

/** Small sliding notifications, top-right, for module toggles and module-triggered alerts. */
public final class Toast {
    private record Entry(String text, long start) {}

    private static final List<Entry> entries = new ArrayList<>();
    private static final long DURATION_MS = 1800;
    private static final long FADE_MS = 300;

    private Toast() {}

    /** Used by Module when it is toggled on/off. */
    public static void push(String moduleName, boolean enabled) {
        push(moduleName + (enabled ? " §aON" : " §cOFF"));
    }

    /** Used by modules that want to show a free-form message (e.g. a low-health warning). */
    public static void push(String text) {
        entries.add(new Entry(text, System.currentTimeMillis()));
        while (entries.size() > 5) entries.remove(0);
    }

    public static void render(DrawContext ctx, TextRenderer tr, int screenWidth) {
        if (entries.isEmpty()) return;
        long now = System.currentTimeMillis();
        entries.removeIf(e -> now - e.start() > DURATION_MS);

        int y = 40;
        for (Entry e : entries) {
            long age = now - e.start();
            float alpha = age > DURATION_MS - FADE_MS
                ? Math.max(0f, (DURATION_MS - age) / (float) FADE_MS)
                : 1f;
            int a = (int) (alpha * 220) & 0xFF;
            int w = tr.getWidth(e.text()) + 16;
            int x = screenWidth - w - 10;

            ctx.fill(x, y, x + w, y + 16, (a << 24) | 0x150F2B);
            ctx.fill(x, y, x + 3, y + 16, (a << 24) | 0xD946EF);
            ctx.drawTextWithShadow(tr, e.text(), x + 8, y + 4, (Math.max(a, 60) << 24) | 0xFFFFFF);
            y += 19;
        }
    }
}
