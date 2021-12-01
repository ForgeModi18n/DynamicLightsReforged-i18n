/*
 * Copyright © 2020 LambdAurora <aurora42lambda@gmail.com>
 *
 * This file is part of LambDynamicLights.
 *
 * Licensed under the MIT license. For more information,
 * see the LICENSE file.
 */

package me.lambdaurora.lambdynlights;

import me.lambdaurora.spruceui.SpruceTexts;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import org.aperlambda.lambdacommon.utils.Nameable;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Optional;

/**
 * Represents the explosives dynamic lighting mode.
 *
 * @author LambdAurora
 * @version 1.2.1
 * @since 1.2.1
 */
public enum ExplosiveLightingMode implements Nameable {
    OFF(ChatFormatting.RED, SpruceTexts.OPTIONS_OFF),
    SIMPLE(ChatFormatting.YELLOW, SpruceTexts.OPTIONS_GENERIC_SIMPLE),
    FANCY(ChatFormatting.GREEN, SpruceTexts.OPTIONS_GENERIC_FANCY);

    private final Component translatedText;

    ExplosiveLightingMode(@NotNull ChatFormatting formatting, @NotNull Component translatedText) {
        this.translatedText = translatedText.plainCopy().withStyle(formatting);
    }

    /**
     * Returns whether this mode enables explosives dynamic lighting.
     *
     * @return {@code true} if the mode enables explosives dynamic lighting, else {@code false}
     */
    public boolean isEnabled() {
        return this != OFF;
    }

    /**
     * Returns the next explosives dynamic lighting mode available.
     *
     * @return the next available explosives dynamic lighting mode
     */
    public ExplosiveLightingMode next() {
        ExplosiveLightingMode[] v = values();
        if (v.length == this.ordinal() + 1)
            return v[0];
        return v[this.ordinal() + 1];
    }

    /**
     * Returns the translated text of the explosives dynamic lighting mode.
     *
     * @return the translated text of the explosives dynamic lighting mode
     */
    public @NotNull Component getTranslatedText() {
        return this.translatedText;
    }

    @Override
    public @NotNull String getName() {
        return this.name().toLowerCase();
    }

    /**
     * Gets the explosives dynamic lighting mode from its identifier.
     *
     * @param id the identifier of the explosives dynamic lighting mode
     * @return the explosives dynamic lighting mode if found, else empty
     */
    public static @NotNull Optional<ExplosiveLightingMode> byId(@NotNull String id) {
        return Arrays.stream(values()).filter(mode -> mode.getName().equalsIgnoreCase(id)).findFirst();
    }
}
