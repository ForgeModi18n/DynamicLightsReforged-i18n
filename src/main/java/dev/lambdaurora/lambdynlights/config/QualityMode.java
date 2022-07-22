package dev.lambdaurora.lambdynlights.config;

//import me.jellysquid.mods.sodium.client.gui.options.TextProvider;
import net.minecraft.network.chat.Component;
//import net.minecraft.network.chat.TextComponent;
import net.minecraft.client.resources.language.I18n;

public enum QualityMode //implements TextProvider
{
    OFF("Off"),
    SLOW("Slow"),
    FAST("Fast"),
    REALTIME("Realtime");

    private final String name;

    private QualityMode(String name) {
        this.name = name;
    }

    public Component getLocalizedName() {
        return Component.nullToEmpty(this.name);
    }
}