package com.autobuild.manager;

import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.BlockPos;

public class PlacementManager {
    private final MinecraftClient mc = MinecraftClient.getInstance();

    public void placeBlockNumpadSequence(BlockPos targetPos) {
        if (mc.player == null || mc.world == null) return;
    }
}
