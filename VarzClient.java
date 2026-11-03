package com.varz.client;

import com.varz.client.gui.ClickGuiScreen;
import com.varz.client.gui.Toast;
import com.varz.client.module.ModuleManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VarzClient implements ClientModInitializer {
    public static final String NAME = "VARZCLIENT";
    public static final String VERSION = "7.2.0";
    public static final Logger LOGGER = LoggerFactory.getLogger("varzclient");
    public static final ModuleManager MODULES = new ModuleManager();

    /** Key that opens the ClickGUI (Right Shift). */
    public static final int GUI_KEY = GLFW.GLFW_KEY_RIGHT_SHIFT;
    private static boolean guiKeyWasDown;

    @Override
    public void onInitializeClient() {
        MODULES.init();
        Config.load(MODULES);
        LOGGER.info("{} v{} loaded with {} modules", NAME, VERSION, MODULES.all().size());

        ClientTickEvents.END_CLIENT_TICK.register(mc -> {
            pollGuiKey(mc);
            if (mc.player == null || mc.world == null) return;
            MODULES.tick(mc);
        });

        HudRenderCallback.EVENT.register((ctx, tickCounter) -> {
            MinecraftClient mc = MinecraftClient.getInstance();
            if (mc.player == null) return;
            if (mc.currentScreen == null) MODULES.render2D(ctx);
            Toast.render(ctx, mc.textRenderer, mc.getWindow().getScaledWidth());
        });

        ClientLifecycleEvents.CLIENT_STOPPING.register(mc -> Config.save(MODULES));
    }

    private static void pollGuiKey(MinecraftClient mc) {
        boolean down = GLFW.glfwGetKey(mc.getWindow().getHandle(), GUI_KEY) == GLFW.GLFW_PRESS;
        if (down && !guiKeyWasDown && mc.currentScreen == null) {
            mc.setScreen(new ClickGuiScreen());
        }
        guiKeyWasDown = down;
    }
}
