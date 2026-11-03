package com.varz.client.module.setting;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

public class NumberSetting extends Setting<Double> {
    public final double min, max;
    public NumberSetting(String name, double min, double max, double def) {
        super(name, def);
        this.min = min;
        this.max = max;
    }
    @Override public void set(Double v) { value = Math.max(min, Math.min(max, v)); }
    public float f() { return value.floatValue(); }
    public int i() { return value.intValue(); }
    @Override public JsonElement save() { return new JsonPrimitive(value); }
    @Override public void load(JsonElement e) { set(e.getAsDouble()); }
}
