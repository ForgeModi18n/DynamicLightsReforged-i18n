package dev.lambdaurora.lambdynlights.mixin.sodium;

import com.google.common.collect.ImmutableList;
import dev.lambdaurora.lambdynlights.LambDynLights;
import dev.lambdaurora.lambdynlights.config.DynamicLightsConfig;
import dev.lambdaurora.lambdynlights.config.QualityMode;
import me.jellysquid.mods.sodium.client.gui.SodiumGameOptions;
import me.jellysquid.mods.sodium.client.gui.SodiumOptionsGUI;
import me.jellysquid.mods.sodium.client.gui.options.OptionGroup;
import me.jellysquid.mods.sodium.client.gui.options.OptionImpact;
import me.jellysquid.mods.sodium.client.gui.options.OptionImpl;
import me.jellysquid.mods.sodium.client.gui.options.OptionPage;
import me.jellysquid.mods.sodium.client.gui.options.control.CyclingControl;
import me.jellysquid.mods.sodium.client.gui.options.control.TickBoxControl;
import me.jellysquid.mods.sodium.client.gui.options.storage.SodiumOptionsStorage;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.client.resources.language.I18n;

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
                .setName(Component.nullToEmpty(I18n.get("dynlights.dynlight_speed.name")))
                .setTooltip(Component.nullToEmpty(I18n.get("dynlights.dynlight_speed.tooltip")))
                .setControl(
                        (option) -> new CyclingControl<>(option, QualityMode.class, new Component[] {
                                Component.nullToEmpty(I18n.get("dynlights.options.off")),
                                Component.nullToEmpty(I18n.get("dynlights.options.slow")),
                                Component.nullToEmpty(I18n.get("dynlights.options.fast")),
                                Component.nullToEmpty(I18n.get("dynlights.options.realtime"))
                        }))
                .setBinding(
                        (options, value) -> {
                            DynamicLightsConfig.Quality.set(QualityMode.valueOf(value.toString()));
                            LambDynLights.get().clearLightSources();
                        },
                        (options) -> QualityMode.valueOf(String.valueOf(DynamicLightsConfig.Quality.get())))
                .setImpact(OptionImpact.MEDIUM)
                .build();


        OptionImpl<SodiumGameOptions, Boolean> entityLighting = OptionImpl.createBuilder(Boolean.class, dynamicLightsOpts)
                .setName(Component.nullToEmpty(I18n.get("dynlights.entity_lights.name")))
                .setTooltip(Component.nullToEmpty(I18n.get("dynlights.entity_lights.tooltip")))
                .setControl(TickBoxControl::new)
                .setBinding(
                        (options, value) -> DynamicLightsConfig.EntityLighting.set(value),
                        (options) -> DynamicLightsConfig.EntityLighting.get())
                .setImpact(OptionImpact.MEDIUM)
                .build();

        OptionImpl<SodiumGameOptions, Boolean> tileEntityLighting = OptionImpl.createBuilder(Boolean.class, dynamicLightsOpts)
                .setName(Component.nullToEmpty(I18n.get("dynlights.block_lights.name")))
                .setTooltip(Component.nullToEmpty(I18n.get("dynlights.block_lights.tooltip")))
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

        pages.add(new OptionPage(Component.nullToEmpty(I18n.get("dynlights.page.name")), ImmutableList.copyOf(groups)));
    }


}