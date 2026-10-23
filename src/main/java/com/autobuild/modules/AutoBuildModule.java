package com.autobuild.modules;

import com.autobuild.AutoBuildAddon;
import meteordevelopment.meteorclient.events.game.SendMessageEvent;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;

public class AutoBuildModule extends Module {
    public BlockPos pos1 = null;
    public BlockPos pos2 = null;

    public AutoBuildModule() {
        super(AutoBuildAddon.CATEGORY, "auto-build", "Addon xây dựng tự động với tính năng khoanh vùng rương bằng #sel 1 và #sel 2.");
    }

    @Override
    public void onActivate() {
        info("Auto Build đã BẬT. Dùng lệnh #sel 1 và #sel 2 trong chat để khoanh vùng khu vực rương.");
    }

    @Override
    public void onDeactivate() {
        info("Auto Build đã TẮT.");
    }

    @EventHandler
    private void onSendMessage(SendMessageEvent event) {
        String msg = event.message.trim();

        if (msg.equalsIgnoreCase("#sel 1")) {
            event.cancel();
            if (mc.crosshairTarget instanceof BlockHitResult hit && !hit.getType().equals(BlockHitResult.Type.MISS)) {
                pos1 = hit.getBlockPos();
                info("Đã đặt góc khoanh vùng 1 (Pos 1) tại: " + pos1.toShortString());
            } else if (mc.player != null) {
                pos1 = mc.player.getBlockPos();
                info("Đã đặt góc khoanh vùng 1 (Pos 1) tại chân: " + pos1.toShortString());
            }
            checkAndNotifyRegion();
        } else if (msg.equalsIgnoreCase("#sel 2")) {
            event.cancel();
            if (mc.crosshairTarget instanceof BlockHitResult hit && !hit.getType().equals(BlockHitResult.Type.MISS)) {
                pos2 = hit.getBlockPos();
                info("Đã đặt góc khoanh vùng 2 (Pos 2) tại: " + pos2.toShortString());
            } else if (mc.player != null) {
                pos2 = mc.player.getBlockPos();
                info("Đã đặt góc khoanh vùng 2 (Pos 2) tại chân: " + pos2.toShortString());
            }
            checkAndNotifyRegion();
        }
    }

    private void checkAndNotifyRegion() {
        if (pos1 != null && pos2 != null) {
            List<BlockPos> chests = getSelectedChests();
            info("Khoanh vùng thành công! Tìm thấy " + chests.size() + " rương trong khu vực đã chọn.");
        }
    }

    // Quét toàn bộ rương nằm trong vùng giới hạn từ pos1 đến pos2 do người chơi khoanh
    public List<BlockPos> getSelectedChests() {
        List<BlockPos> chests = new ArrayList<>();
        if (pos1 == null || pos2 == null || mc.world == null) return chests;

        BlockPos min = new BlockPos(
            Math.min(pos1.getX(), pos2.getX()),
            Math.min(pos1.getY(), pos2.getY()),
            Math.min(pos1.getZ(), pos2.getZ())
        );
        BlockPos max = new BlockPos(
            Math.max(pos1.getX(), pos2.getX()),
            Math.max(pos1.getY(), pos2.getY()),
            Math.max(pos1.getZ(), pos2.getZ())
        );

        for (BlockPos bp : BlockPos.iterate(min, max)) {
            if (mc.world.getBlockState(bp).getBlock().asItem().toString().contains("chest")) {
                chests.add(bp.toImmutable());
            }
        }
        return chests;
    }

    @EventHandler
    private void onTick(TickEvent.Post event) {
        if (mc.player == null || mc.world == null) return;
    }
}
