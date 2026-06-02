package com.opencore.mod;

import com.opencore.mod.command.TeleportDimCommand;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OpenCoreMod implements ModInitializer {

    public static final String MOD_ID = "opencore";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    // Registry key for the DimensionType (must match dimension_type/opencore.json path)
    public static final RegistryKey<DimensionType> OPENCORE_DIMENSION_TYPE_KEY = RegistryKey.of(
            RegistryKeys.DIMENSION_TYPE,
            Identifier.of(MOD_ID, "opencore")
    );

    // Registry key for the World/Dimension (must match dimension/opencore.json path)
    public static final RegistryKey<World> OPENCORE_WORLD_KEY = RegistryKey.of(
            RegistryKeys.WORLD,
            Identifier.of(MOD_ID, "opencore")
    );

    @Override
    public void onInitialize() {
        LOGGER.info("[OpenCore] Initializing OpenCore Mod v1.0.0");
        LOGGER.info("[OpenCore] Dimension Type: opencore:opencore (The End sky + Overworld terrain)");

        // Register commands
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) ->
                TeleportDimCommand.register(dispatcher)
        );

        // Log when server starts to confirm dimension loaded
        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
            var world = server.getWorld(OPENCORE_WORLD_KEY);
            if (world != null) {
                LOGGER.info("[OpenCore] SUCCESS: OpenCore dimension loaded and ready!");
                LOGGER.info("[OpenCore] Effects: The End (sky/fog/ambience) | Generator: Overworld noise");
            } else {
                LOGGER.error("[OpenCore] FAILED: OpenCore world is null after server start!");
                LOGGER.error("[OpenCore] Check data/opencore/dimension/opencore.json");
            }
        });

        LOGGER.info("[OpenCore] Commands registered: /teleport_dim, /teleport DIM");
    }
}
