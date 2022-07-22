package com.textrue.rb_dynlights.config;

import me.jellysquid.mods.sodium.client.gui.options.TextProvider;
import net.minecraft.client.resources.I18n;


public enum QualityMode implements TextProvider
{
    OFF(I18n.get("rb_dynlights.options.off")),
    SLOW(I18n.get("rb_dynlights.options.slow")),
    FAST(I18n.get("rb_dynlights.options.fast")),
    REALTIME(I18n.get("rb_dynlights.options.realtime"));

    private final String name;

    private QualityMode(String name) {
        this.name = name;
    }

    public String getLocalizedName() {
        return this.name;
    }
}