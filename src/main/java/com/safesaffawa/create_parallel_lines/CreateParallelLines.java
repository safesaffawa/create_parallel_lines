package com.safesaffawa.create_parallel_lines;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;

/**
 * Entry point for Create: Parallel Lines.
 *
 * <p>The mod id must stay in sync with {@code mod_id} in gradle.properties and with
 * {@code src/main/templates/META-INF/neoforge.mods.toml}.
 */
@Mod(CreateParallelLines.MODID)
public class CreateParallelLines {
    public static final String MODID = "create_parallel_lines";

    private static final Logger LOGGER = LogUtils.getLogger();

    public CreateParallelLines(IEventBus modEventBus, ModContainer modContainer) {
        LOGGER.info("Create: Parallel Lines is loading");
    }
}
