package com.varz.client.module.modules;

import com.varz.client.module.Category;
import com.varz.client.module.Module;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;

/** Each time you toggle this on, it drops a waypoint at your current position and lists distances to all saved points. */
public class Waypoints extends Module {
    private final List<BlockPos> points = new ArrayList<>();

    public Waypoints() { super("Waypoints", "Each time you toggle this on, it drops a waypoint and shows distances to all saved ones.", Category.RENDER); }

    @Override public void onEnable() {
        if (mc.player != null) points.add(mc.player.getBlockPos());
    }

    @Override public void onRender2D(DrawContext ctx) {
        if (mc.player == null || points.isEmpty()) return;
        int y = 32;
        for (BlockPos p : points) {
            double d = Math.sqrt(mc.player.getBlockPos().getSquaredDistance(p));
            String line = String.format("WP %d, %d, %d  -  %.0fm", p.getX(), p.getY(), p.getZ(), d);
            ctx.drawTextWithShadow(mc.textRenderer, line, 6, y, 0xFFF0ABFC);
            y += 10;
        }
    }
}
