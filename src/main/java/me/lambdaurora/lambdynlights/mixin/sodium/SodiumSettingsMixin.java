package me.lambdaurora.lambdynlights.mixin.sodium;

import com.google.common.collect.ImmutableList;
import me.jellysquid.mods.sodium.client.gui.SodiumGameOptionPages;
import me.jellysquid.mods.sodium.client.gui.SodiumGameOptions;
import me.jellysquid.mods.sodium.client.gui.SodiumOptionsGUI;
import me.jellysquid.mods.sodium.client.gui.options.*;
import me.jellysquid.mods.sodium.client.gui.options.control.CyclingControl;

import me.jellysquid.mods.sodium.client.gui.options.control.TickBoxControl;
import me.jellysquid.mods.sodium.client.gui.options.storage.SodiumOptionsStorage;
import me.lambdaurora.lambdynlights.DynamicLightsReforged;
import me.lambdaurora.lambdynlights.config.DynamicLightsConfig;
import me.lambdaurora.lambdynlights.config.QualityMode;
import net.minecraft.client.gui.screen.Screen;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
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
                .setName(I18n.get("rb_dynlights.dynlights.dynlight_speed.title"))
                .setTooltip(I18n.get("rb_dynlights.dynlights.dynlight_speed.desc"))
                .setControl(
                        (option) -> new CyclingControl<>(option, QualityMode.class, new String[] {
                                I18n.get("rb_dynlights.options.off"),
                                I18n.get("rb_dynlights.options.slow"),
                                I18n.get("rb_dynlights.options.fast"),
                                I18n.get("rb_dynlights.options.realtime")
                        }
                        )
                )
                .setBinding(
                        (options, value) -> {
                            DynamicLightsConfig.Quality.set(value.toString());
                            DynamicLightsReforged.clearLightSources();
                        },
                        (options) -> QualityMode.valueOf(DynamicLightsConfig.Quality.get()))
                .setImpact(OptionImpact.MEDIUM)
                .build();


        OptionImpl<SodiumGameOptions, Boolean> entityLighting = OptionImpl.createBuilder(Boolean.class, dynamicLightsOpts)
                .setName(I18n.get("rb_dynlights.dynlights.entity_lights.title"))
                .setTooltip(I18n.get("rb_dynlights.dynlights.entity_lights.desc"))
                .setControl(TickBoxControl::new)
                .setBinding(
                        (options, value) -> DynamicLightsConfig.EntityLighting.set(value),
                        (options) -> DynamicLightsConfig.EntityLighting.get())
                .setImpact(OptionImpact.MEDIUM)
                .build();

        OptionImpl<SodiumGameOptions, Boolean> tileEntityLighting = OptionImpl.createBuilder(Boolean.class, dynamicLightsOpts)
                .setName(I18n.get("rb_dynlights.dynlights.block_lights.title"))
                .setTooltip(I18n.get("rb_dynlights.dynlights.block_lights.desc"))
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

        pages.add(new OptionPage(I18n.get("rb_dynlights.dynlights.option.name"), ImmutableList.copyOf(groups)));
    }


}