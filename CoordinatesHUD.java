package com.varz.client.module.modules;

import com.varz.client.module.Category;
import com.varz.client.module.Module;
import net.minecraft.client.gui.DrawContext;

/** Standalone top-left coordinate + facing readout, independent of the main HUD module. */
public class CoordinatesHUD extends Module {
    public CoordinatesHUD() { super("Coordinates HUD", "Shows your exact position and facing direction in the top-left corner.", Category.RENDER); }

    @Override public void onRender2D(DrawContext ctx) {
        if (mc.player == null) return;
        String line = String.format("X %.2f  Y %.2f  Z %.2f  (%s)",
            mc.player.getX(), mc.player.getY(), mc.player.getZ(), facing());
        int y = 20;
        ctx.fill(4, y - 2, 8 + mc.textRenderer.getWidth(line), y + 10, 0x80150F2B);
        ctx.drawTextWithShadow(mc.textRenderer, line, 6, y, 0xFFA5F3FC);
    }

    private String facing() {
        float yaw = mc.player.getYaw() % 360;
        if (yaw < 0) yaw += 360;
        if (yaw < 45 || yaw >= 315) return "South";
        if (yaw < 135) return "West";
        if (yaw < 225) return "North";
        return "East";
    }
}
