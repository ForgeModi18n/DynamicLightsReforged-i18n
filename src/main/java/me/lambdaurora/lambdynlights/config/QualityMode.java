package me.lambdaurora.lambdynlights.config;

import me.jellysquid.mods.sodium.client.gui.options.TextProvider;
import net.minecraft.client.resources.I18n;


public enum QualityMode implements TextProvider
{
    OFF(I18n.get("dynlights.options.off")),
    SLOW(I18n.get("dynlights.options.slow")),
    FAST(I18n.get("dynlights.options.fast")),
    REALTIME(I18n.get("dynlights.options.realtime"));

    private final String name;

    private QualityMode(String name) {
        this.name = name;
    }

    public String getLocalizedName() {
        return this.name;
    }
}