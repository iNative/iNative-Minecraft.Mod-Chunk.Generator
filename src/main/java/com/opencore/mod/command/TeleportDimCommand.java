package com.opencore.mod.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.opencore.mod.OpenCoreMod;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import static net.minecraft.server.command.CommandManager.literal;

public class TeleportDimCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {

        // /teleport_dim
        dispatcher.register(
            literal("teleport_dim")
                .requires(source -> source.hasPermissionLevel(2))
                .executes(TeleportDimCommand::execute)
        );

        // /teleport DIM
        dispatcher.register(
            literal("teleport")
                .requires(source -> source.hasPermissionLevel(2))
                .then(literal("DIM")
                    .executes(TeleportDimCommand::execute)
                )
        );

        OpenCoreMod.LOGGER.info("[OpenCore] Commands registered: /teleport_dim | /teleport DIM");
    }

    private static int execute(CommandContext<ServerCommandSource> context) {
        ServerCommandSource source = context.getSource();

        if (!(source.getEntity() instanceof ServerPlayerEntity player)) {
            source.sendError(Text.literal("[OpenCore] Hanya player yang bisa pakai command ini!"));
            return 0;
        }

        ServerWorld targetWorld = source.getServer().getWorld(OpenCoreMod.OPENCORE_WORLD_KEY);

        if (targetWorld == null) {
            player.sendMessage(Text.literal(
                "§c[OpenCore] ERROR: Dimensi OpenCore tidak ditemukan!\n" +
                "§ePastikan mod ter-install dengan benar dan world baru dibuat."
            ), false);
            OpenCoreMod.LOGGER.error("[OpenCore] targetWorld is null! Dimension not loaded.");
            return 0;
        }

        // Sudah di OpenCore?
        if (player.getServerWorld().getRegistryKey().equals(OpenCoreMod.OPENCORE_WORLD_KEY)) {
            player.sendMessage(Text.literal("§e[OpenCore] Kamu sudah di dimensi OpenCore!"), false);
            return 1;
        }

        Vec3d pos = player.getPos();
        // Cari Y yang aman di dimensi target
        int safeY = Math.max(targetWorld.getSeaLevel(), targetWorld.getBottomY() + 64);

        player.sendMessage(Text.literal("§b[OpenCore] Teleporting ke dimensi OpenCore..."), false);

        player.teleport(
            targetWorld,
            pos.x,
            safeY,
            pos.z,
            player.getYaw(),
            player.getPitch()
        );

        player.sendMessage(Text.literal(
            "§a[OpenCore] Selamat datang di OpenCore!\n" +
            "§7Terrain: Overworld | Sky/Fog/Ambience: The End"
        ), false);

        OpenCoreMod.LOGGER.info("[OpenCore] Player {} teleported to OpenCore at ({}, {}, {})",
            player.getName().getString(), (int)pos.x, safeY, (int)pos.z);

        return 1;
    }
}
