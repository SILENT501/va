package com.varz.client.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.GameOptions;

public final class MoveUtil {
    private MoveUtil() {}

    public static boolean isMoving() {
        GameOptions o = MinecraftClient.getInstance().options;
        return o.forwardKey.isPressed() || o.backKey.isPressed() || o.leftKey.isPressed() || o.rightKey.isPressed();
    }

    /** Normalised horizontal movement direction {x, z} from the pressed keys and player yaw. */
    public static double[] direction() {
        MinecraftClient mc = MinecraftClient.getInstance();
        GameOptions o = mc.options;
        double fwd = (o.forwardKey.isPressed() ? 1 : 0) - (o.backKey.isPressed() ? 1 : 0);
        double str = (o.leftKey.isPressed() ? 1 : 0) - (o.rightKey.isPressed() ? 1 : 0);
        if (fwd == 0 && str == 0) return new double[]{0, 0};
        double yaw = Math.toRadians(mc.player.getYaw());
        double dx = -Math.sin(yaw) * fwd + Math.cos(yaw) * str;
        double dz = Math.cos(yaw) * fwd + Math.sin(yaw) * str;
        double len = Math.sqrt(dx * dx + dz * dz);
        return new double[]{dx / len, dz / len};
    }
}
