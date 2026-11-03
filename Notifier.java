package com.varz.client.module.modules;

import com.varz.client.gui.Toast;
import com.varz.client.module.Category;
import com.varz.client.module.Module;
import com.varz.client.module.setting.NumberSetting;

/** Pops a toast warning once your health drops below a configurable threshold. */
public class Notifier extends Module {
    private final NumberSetting threshold = add(new NumberSetting("Health below", 1, 19, 6));
    private boolean fired;

    public Notifier() { super("Notifier", "Pops a warning toast when your health drops below a threshold.", Category.MISC); }

    @Override public void onTick() {
        if (mc.player == null) return;
        float hp = mc.player.getHealth();
        if (hp <= threshold.get()) {
            if (!fired) {
                Toast.push("§cLow HP! " + (int) hp + " left");
                fired = true;
            }
        } else {
            fired = false;
        }
    }
}
