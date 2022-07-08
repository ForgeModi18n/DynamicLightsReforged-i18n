package me.lambdaurora.lambdynlights.config;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import lombok.val;
import net.minecraftforge.common.ForgeConfigSpec;
import  net.minecraft.client.resources.I18n;

import java.nio.file.Path;

public class DynamicLightsConfig
{
    public static ForgeConfigSpec ConfigSpec;

    public static ForgeConfigSpec.ConfigValue<String> Quality;
    public static ForgeConfigSpec.ConfigValue<Boolean> EntityLighting;
    public static ForgeConfigSpec.ConfigValue<Boolean> TileEntityLighting;

    public static ForgeConfigSpec.ConfigValue<Boolean> OnlyUpdateOnPositionChange;

    static
    {
        val builder = new ConfigBuilder(I18n.get("dynlights.dynlights_settings.option.name"));

        builder.Block(I18n.get("dynlights.lighting_setting.option.name"), b -> {
            Quality = b.define(I18n.get("dynlights.lighting_setting.quality_mode.title"), "REALTIME");
            EntityLighting = b.define(I18n.get("dynlights.lighting_setting.entity_lighting.title"), true);
            TileEntityLighting = b.define(I18n.get("dynlights.lighting_setting.tile_entity_lighting.title"), true);
            OnlyUpdateOnPositionChange = b.define(I18n.get("dynlights.lighting_setting.only_update_on_position_change.title"), true);
        });

        ConfigSpec = builder.Save();
    }

    public static void loadConfig(Path path) {
        final CommentedFileConfig configData = CommentedFileConfig.builder(path).sync().autosave().writingMode(WritingMode.REPLACE).build();

        configData.load();
        ConfigSpec.setConfig(configData);
    }

}
