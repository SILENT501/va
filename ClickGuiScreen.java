package com.varz.client.gui;

import com.varz.client.Config;
import com.varz.client.VarzClient;
import com.varz.client.module.Category;
import com.varz.client.module.Module;
import com.varz.client.module.setting.BoolSetting;
import com.varz.client.module.setting.ModeSetting;
import com.varz.client.module.setting.NumberSetting;
import com.varz.client.module.setting.Setting;
import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

/**
 * Tabbed ClickGUI: category tabs on top, scrollable module cards below, search box top-right.
 * Left click a card = toggle module. Right click a card = open/close its settings panel.
 */
public class ClickGuiScreen extends Screen {
    // Palette (matches the HTML concept)
    private static final int BG        = 0xE0080712;
    private static final int PANEL     = 0xF0100D20;
    private static final int CARD      = 0xFF19152D;
    private static final int CARD_ON   = 0xFF7028D8;
    private static final int CARD_HOV  = 0xFF2A2650;
    private static final int ACCENT    = 0xFFC4B5FD;
    private static final int PINK      = 0xFFE879F9;
    private static final int TEXT      = 0xFFE2E8F0;
    private static final int MUTED     = 0xFF7C7C96;

    private static final int CARD_W = 120, CARD_H = 24, GAP = 6, TOP = 66, TAB_W = 92;
    private static final int PANEL_W = 190, ROW_H = 18;

    private Category current = Category.COMBAT;
    private double scroll;
    private TextFieldWidget search;

    /** Module whose settings panel is open, or null. */
    private Module selected;
    /** Module currently waiting for the next key press to bind, or null. */
    private Module awaitingBind;

    public ClickGuiScreen() { super(Text.literal("Varz Client")); }

    @Override protected void init() {
        search = new TextFieldWidget(textRenderer, width - 196, 10, 176, 16, Text.literal("Search"));
        search.setPlaceholder(Text.literal("Search modules..."));
        search.setChangedListener(s -> scroll = 0);
        addDrawableChild(search);
    }

    private List<Module> visible() {
        String q = search == null ? "" : search.getText().trim().toLowerCase();
        List<Module> out = new ArrayList<>();
        for (Module m : VarzClient.MODULES.all()) {
            if (q.isEmpty() ? m.category == current : m.name.toLowerCase().contains(q)) out.add(m);
        }
        return out;
    }

    private int cols() { return Math.max(1, (width - 40 + GAP) / (CARD_W + GAP)); }

    private int cardX(int i) { return 20 + (i % cols()) * (CARD_W + GAP); }
    private int cardY(int i) { return TOP + (i / cols()) * (CARD_H + GAP) - (int) scroll; }

    private int maxScroll(int count) {
        int rows = (count + cols() - 1) / cols();
        return Math.max(0, rows * (CARD_H + GAP) - (height - TOP - 24));
    }

    // ---- settings panel geometry ----------------------------------------------------------
    private int panelX() { return width - PANEL_W - 10; }
    private int panelY() { return 40; }
    private int rowY(int index) { return panelY() + 30 + index * ROW_H; }
    private int panelHeight() {
        int rows = selected == null ? 0 : selected.settings.size();
        return Math.min(height - panelY() - 20, 34 + (rows + 1) * ROW_H + 6);
    }

    @Override public void render(DrawContext ctx, int mouseX, int mouseY, float delta) {
        super.render(ctx, mouseX, mouseY, delta);           // background + search widget
        ctx.fill(0, 0, width, height, BG);
        ctx.fill(0, 0, width, 32, PANEL);
        ctx.fill(0, 31, width, 32, 0x558B5CF6);
        ctx.drawTextWithShadow(textRenderer, "VARZCLIENT", 20, 12, ACCENT);
        ctx.drawTextWithShadow(textRenderer, "v" + VarzClient.VERSION, 20 + textRenderer.getWidth("VARZ CLIENT ") , 12, MUTED);
        search.render(ctx, mouseX, mouseY, delta);

        // tabs
        Category[] cats = Category.values();
        for (int i = 0; i < cats.length; i++) {
            int x = 20 + i * (TAB_W + 4), y = 38;
            boolean sel = cats[i] == current && search.getText().isEmpty();
            boolean hov = mouseX >= x && mouseX < x + TAB_W && mouseY >= y && mouseY < y + 20;
            ctx.fill(x, y, x + TAB_W, y + 20, sel ? 0xFF4C1D95 : hov ? CARD_HOV : CARD);
            if (sel) ctx.fill(x, y + 18, x + TAB_W, y + 20, PINK);
            String label = cats[i].label + " " + VarzClient.MODULES.byCategory(cats[i]).size();
            ctx.drawTextWithShadow(textRenderer, label, x + (TAB_W - textRenderer.getWidth(label)) / 2, y + 6, sel ? 0xFFFFFFFF : MUTED);
        }

        // module cards
        List<Module> list = visible();
        scroll = Math.max(0, Math.min(scroll, maxScroll(list.size())));
        ctx.enableScissor(0, TOP - 4, width, height - 20);
        Module hovered = null;
        for (int i = 0; i < list.size(); i++) {
            Module m = list.get(i);
            int x = cardX(i), y = cardY(i);
            if (y + CARD_H < TOP - 4 || y > height - 20) continue;
            boolean hov = mouseX >= x && mouseX < x + CARD_W && mouseY >= y && mouseY < y + CARD_H
                    && mouseY > TOP - 4 && mouseY < height - 20;
            if (hov) hovered = m;
            int bg = m == selected ? 0xFF2563EB : m.isEnabled() ? CARD_ON : hov ? CARD_HOV : CARD;
            ctx.fill(x, y, x + CARD_W, y + CARD_H, bg);
            if (m.isEnabled()) ctx.fill(x, y, x + 2, y + CARD_H, PINK);
            int color = m.isEnabled() ? 0xFFFFFFFF : m.isImplemented() ? TEXT : MUTED;
            String name = m.name;
            while (textRenderer.getWidth(name) > CARD_W - 12 && name.length() > 3) name = name.substring(0, name.length() - 1);
            ctx.drawTextWithShadow(textRenderer, name, x + 7, y + 8, color);
        }
        ctx.disableScissor();

        // settings panel
        if (selected != null) renderPanel(ctx, mouseX, mouseY);

        // status bar
        ctx.fill(0, height - 18, width, height, PANEL);
        String status = hovered != null
            ? hovered.name + " - " + hovered.description + (hovered.isImplemented() ? "" : "  [no behaviour yet]")
            : "Left: toggle   Right: settings   Scroll: more   Right Shift: open/close";
        ctx.drawTextWithShadow(textRenderer, status, 20, height - 13, MUTED);
        String cnt = "Active " + VarzClient.MODULES.activeCount() + " / " + VarzClient.MODULES.all().size();
        ctx.drawTextWithShadow(textRenderer, cnt, width - textRenderer.getWidth(cnt) - 20, height - 13, ACCENT);
    }

    private void renderPanel(DrawContext ctx, int mouseX, int mouseY) {
        int px = panelX(), py = panelY(), pw = PANEL_W, ph = panelHeight();
        ctx.fill(px, py, px + pw, py + ph, PANEL);
        ctx.fill(px, py, px + pw, py + 2, PINK);
        ctx.drawTextWithShadow(textRenderer, selected.name, px + 8, py + 8, ACCENT);
        ctx.drawTextWithShadow(textRenderer, selected.isImplemented() ? "Implemented" : "No behaviour yet",
            px + 8, py + 18, selected.isImplemented() ? 0xFF86EFAC : MUTED);

        // keybind row
        int kbY = rowY(0);
        boolean kbHov = mouseX >= px + 6 && mouseX < px + pw - 6 && mouseY >= kbY && mouseY < kbY + 16;
        ctx.fill(px + 6, kbY, px + pw - 6, kbY + 16, kbHov ? CARD_HOV : CARD);
        String kbText = "Key: " + (awaitingBind == selected ? "press a key..." : keyName(selected.getKey()));
        ctx.drawTextWithShadow(textRenderer, kbText, px + 10, kbY + 4, TEXT);

        // settings rows
        for (int i = 0; i < selected.settings.size(); i++) {
            Setting<?> s = selected.settings.get(i);
            int ry = rowY(i + 1);
            if (ry + ROW_H > py + ph) break;
            boolean hov = mouseX >= px + 6 && mouseX < px + pw - 6 && mouseY >= ry && mouseY < ry + 16;
            ctx.fill(px + 6, ry, px + pw - 6, ry + 16, hov ? CARD_HOV : CARD);
            String val = settingValue(s);
            ctx.drawTextWithShadow(textRenderer, s.name, px + 10, ry + 4, TEXT);
            ctx.drawTextWithShadow(textRenderer, val, px + pw - 10 - textRenderer.getWidth(val), ry + 4, ACCENT);
        }
    }

    private String settingValue(Setting<?> s) {
        if (s instanceof BoolSetting bs) return bs.get() ? "ON" : "OFF";
        if (s instanceof ModeSetting ms) return ms.get();
        if (s instanceof NumberSetting ns) return String.format("%.2f", ns.get());
        return String.valueOf(s.get());
    }

    private static String keyName(int code) {
        if (code <= 0) return "NONE";
        String n = GLFW.glfwGetKeyName(code, 0);
        return n != null ? n.toUpperCase() : "KEY#" + code;
    }

    @Override public boolean mouseClicked(Click click, boolean doubled) {
        if (super.mouseClicked(click, doubled)) return true;
        double mx = click.x(), my = click.y();
        boolean right = click.button() == 1;

        // settings panel takes priority
        if (selected != null) {
            int px = panelX(), py = panelY(), pw = PANEL_W, ph = panelHeight();
            if (mx >= px && mx < px + pw && my >= py && my < py + ph) {
                handlePanelClick(mx, my);
                return true;
            }
        }

        // tabs
        Category[] cats = Category.values();
        for (int i = 0; i < cats.length; i++) {
            int x = 20 + i * (TAB_W + 4), y = 38;
            if (mx >= x && mx < x + TAB_W && my >= y && my < y + 20) {
                current = cats[i];
                search.setText("");
                scroll = 0;
                return true;
            }
        }

        // module cards
        if (my > TOP - 4 && my < height - 20) {
            List<Module> list = visible();
            for (int i = 0; i < list.size(); i++) {
                int x = cardX(i), y = cardY(i);
                if (mx >= x && mx < x + CARD_W && my >= y && my < y + CARD_H) {
                    Module m = list.get(i);
                    if (right) selected = (selected == m) ? null : m;
                    else m.toggle();
                    return true;
                }
            }
        }
        return false;
    }

    private void handlePanelClick(double mx, double my) {
        int kbY = rowY(0);
        if (my >= kbY && my < kbY + 16) {
            awaitingBind = selected;
            return;
        }
        int px = panelX(), pw = PANEL_W;
        for (int i = 0; i < selected.settings.size(); i++) {
            int ry = rowY(i + 1);
            if (my >= ry && my < ry + 16) {
                Setting<?> s = selected.settings.get(i);
                if (s instanceof BoolSetting bs) {
                    bs.set(!bs.get());
                } else if (s instanceof ModeSetting ms) {
                    ms.cycle();
                } else if (s instanceof NumberSetting ns) {
                    double step = (ns.max - ns.min) / 20.0;
                    if (mx < px + pw / 2.0) ns.set(ns.get() - step);
                    else ns.set(ns.get() + step);
                }
                return;
            }
        }
    }

    @Override public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (awaitingBind != null) {
            awaitingBind.setKey(keyCode == GLFW.GLFW_KEY_ESCAPE ? -1 : keyCode);
            awaitingBind = null;
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override public boolean mouseScrolled(double mx, double my, double hAmount, double vAmount) {
        scroll = Math.max(0, Math.min(scroll - vAmount * 24, maxScroll(visible().size())));
        return true;
    }

    @Override public boolean shouldPause() { return false; }

    @Override public void close() {
        Config.save(VarzClient.MODULES);
        super.close();
    }
}
