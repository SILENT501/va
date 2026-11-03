package com.varz.client.module.modules;

import com.varz.client.module.*;
import com.varz.client.module.Module;

public class AutoWalk extends Module {
    public AutoWalk() { super("Auto Walk", "Holds the forward key for you.", Category.MOVEMENT); }
    @Override public void onTick() { mc.options.forwardKey.setPressed(true); }
    @Override public void onDisable() { mc.options.forwardKey.setPressed(false); }
}
