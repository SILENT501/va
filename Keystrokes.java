package com.varz.client.module.modules;

import com.varz.client.module.Category;
import com.varz.client.module.Module;
import net.minecraft.client.gui.DrawContext;

/** Small WASD + space overlay that lights up with the keys you are currently holding. */
public class Keystrokes extends Module {
    public Keystrokes() { super("Keystrokes", "Shows a WASD + jump key overlay near the bottom of the screen.", Category.RENDER); }

    @Override public void onRender2D(DrawContext ctx) {
        int sw = mc.getWindow().getScaledWidth();
        int sh = mc.getWindow().getScaledHeight();
        int bx = sw / 2 - 34, by = sh - 70;

        key(ctx, bx + 16, by, "W", mc.options.forwardKey.isPressed());
        key(ctx, bx, by + 16, "A", mc.options.leftKey.isPressed());
        key(ctx, bx + 16, by + 16, "S", mc.options.backKey.isPressed());
        key(ctx, bx + 32, by + 16, "D", mc.options.rightKey.isPressed());
        bar(ctx, bx - 2, by + 34, "SPACE", mc.options.jumpKey.isPressed());
    }

    private void key(DrawContext ctx, int x, int y, String label, boolean down) {
        ctx.fill(x, y, x + 16, y + 16, down ? 0xFF8B5CF6 : 0x801C1934);
        ctx.drawCenteredTextWithShadow(mc.textRenderer, label, x + 8, y + 4, 0xFFFFFFFF);
    }

    private void bar(DrawContext ctx, int x, int y, String label, boolean down) {
        ctx.fill(x, y, x + 52, y + 14, down ? 0xFF8B5CF6 : 0x801C1934);
        ctx.drawCenteredTextWithShadow(mc.textRenderer, label, x + 26, y + 3, 0xFFFFFFFF);
    }
}
