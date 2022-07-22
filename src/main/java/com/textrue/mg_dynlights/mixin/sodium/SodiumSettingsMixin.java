package com.textrue.mg_dynlights.mixin.sodium;

import com.google.common.collect.ImmutableList;
import com.textrue.mg_dynlights.config.DynamicLightsConfig;
import com.textrue.mg_dynlights.config.QualityMode;
import me.jellysquid.mods.sodium.client.gui.SodiumGameOptions;
import me.jellysquid.mods.sodium.client.gui.SodiumOptionsGUI;
import me.jellysquid.mods.sodium.client.gui.options.*;
import me.jellysquid.mods.sodium.client.gui.options.control.CyclingControl;

import me.jellysquid.mods.sodium.client.gui.options.control.TickBoxControl;
import me.jellysquid.mods.sodium.client.gui.options.storage.SodiumOptionsStorage;
import com.textrue.mg_dynlights.MgDynamicLights;
import net.minecraft.client.gui.screen.Screen;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.client.resources.I18n;

import java.util.ArrayList;
import java.util.List;

@Pseudo
@Mixin(SodiumOptionsGUI.class)
public abstract class SodiumSettingsMixin {

    @Shadow
    @Final
    private List<OptionPage> pages;

    private static final SodiumOptionsStorage dynamicLightsOpts = new SodiumOptionsStorage();


    @Inject(method = "<init>", at = @At("RETURN"))
    private void DynamicLights(Screen prevScreen, CallbackInfo ci)
    {
        List<OptionGroup> groups = new ArrayList<>();

        OptionImpl<SodiumGameOptions, QualityMode> qualityMode = OptionImpl.createBuilder(QualityMode.class, dynamicLightsOpts)
                .setName(I18n.get("dynlights.dynlights.dynlight_speed.title"))
                .setTooltip(I18n.get("dynlights.dynlights.dynlight_speed.desc"))
                .setControl(
                        (option) -> new CyclingControl<>(option, QualityMode.class, new String[] { I18n.get("dynlights.options.off"), I18n.get("dynlights.options.slow"), I18n.get("dynlights.options.fast"), I18n.get("dynlights.options.realtime") }))
                .setBinding(
                        (options, value) -> {
                            DynamicLightsConfig.Quality.set(value.toString());
                            MgDynamicLights.clearLightSources();
                        },
                        (options) -> QualityMode.valueOf(DynamicLightsConfig.Quality.get()))
                .setImpact(OptionImpact.MEDIUM)
                .build();


        OptionImpl<SodiumGameOptions, Boolean> entityLighting = OptionImpl.createBuilder(Boolean.class, dynamicLightsOpts)
                .setName(I18n.get("dynlights.dynlights.entity_lights.title"))
                .setTooltip(I18n.get("dynlights.dynlights.entity_lights.desc"))
                .setControl(TickBoxControl::new)
                .setBinding(
                        (options, value) -> DynamicLightsConfig.EntityLighting.set(value),
                        (options) -> DynamicLightsConfig.EntityLighting.get())
                .setImpact(OptionImpact.MEDIUM)
                .build();

        OptionImpl<SodiumGameOptions, Boolean> tileEntityLighting = OptionImpl.createBuilder(Boolean.class, dynamicLightsOpts)
                .setName(I18n.get("dynlights.dynlights.block_lights.title"))
                .setTooltip(I18n.get("dynlights.dynlights.block_lights.desc"))
                .setControl(TickBoxControl::new)
                .setBinding(
                        (options, value) -> DynamicLightsConfig.TileEntityLighting.set(value),
                        (options) -> DynamicLightsConfig.TileEntityLighting.get())
                .setImpact(OptionImpact.MEDIUM)
                .build();

        groups.add(OptionGroup
            .createBuilder()
                .add(qualityMode)
                .add(entityLighting)
                .add(tileEntityLighting)
            .build()
        );

        pages.add(new OptionPage(I18n.get("dynlights.dynlights.option.name"), ImmutableList.copyOf(groups)));
    }


}