package com.varz.client.module.modules;

import com.varz.client.module.*;
import com.varz.client.module.Module;

public class Sneak extends Module {
    public Sneak() { super("Sneak", "Holds the sneak key for you.", Category.MOVEMENT); }
    @Override public void onTick() { mc.options.sneakKey.setPressed(true); }
    @Override public void onDisable() { mc.options.sneakKey.setPressed(false); }
}
