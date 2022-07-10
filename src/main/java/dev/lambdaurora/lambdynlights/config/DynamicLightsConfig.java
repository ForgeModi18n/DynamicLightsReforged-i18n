package dev.lambdaurora.lambdynlights.config;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraft.client.resources.language.I18n;

import java.nio.file.Path;

public class DynamicLightsConfig
{
    public static ForgeConfigSpec ConfigSpec;

    public static ForgeConfigSpec.EnumValue<QualityMode> Quality;
    public static ForgeConfigSpec.ConfigValue<Boolean> EntityLighting;
    public static ForgeConfigSpec.ConfigValue<Boolean> TileEntityLighting;

    public static ForgeConfigSpec.ConfigValue<Boolean> OnlyUpdateOnPositionChange;

    static
    {
        var builder = new ConfigBuilder(I18n.get("dynlights.settings.group.name"));

        builder.Block(I18n.get("dynlights.lighting_setting.page.name"), b -> {
            Quality = b.defineEnum(I18n.get("dynlights.quality_mode.name"), QualityMode.REALTIME);
            EntityLighting = b.define(I18n.get("dynlights.entity_lighting.name"), true);
            TileEntityLighting = b.define(I18n.get("dynlights.tile_entity_lighting.name"), true);
            OnlyUpdateOnPositionChange = b.define(I18n.get("dynlights.only_update_on_position_change.name"), true);
        });

        ConfigSpec = builder.Save();
    }

    public static void loadConfig(Path path) {
        final CommentedFileConfig configData = CommentedFileConfig.builder(path).sync().autosave().writingMode(WritingMode.REPLACE).build();

        configData.load();
        ConfigSpec.setConfig(configData);
    }



}