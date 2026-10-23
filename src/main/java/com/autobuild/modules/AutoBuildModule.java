package com.autobuild.modules;

import com.autobuild.AutoBuildAddon;
import meteordevelopment.meteorclient.settings.BlockPosSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.Module;
import net.minecraft.util.math.BlockPos;

public class AutoBuildModule extends Module {

    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    // Ô nhập tọa độ rương lưu trực tiếp trên UI của Meteor Client
    private final Setting<BlockPos> chestPos = sgGeneral.add(new BlockPosSetting.Builder()
        .name("tọa-độ-rương")
        .description("Tọa độ rương kho vật liệu chính cho công trình lớn.")
        .defaultValue(new BlockPos(0, 64, 0))
        .build()
    );

    public AutoBuildModule() {
        super(AutoBuildAddon.CATEGORY, "auto-build", "Xây siêu tốc 6 block/s, tự di chuyển và tự tìm rương theo tọa độ.");
    }

    @Override
    public void onActivate() {
        if (mc.player == null || mc.player.networkHandler == null) return;

        BlockPos pos = chestPos.get();

        // 1. ÉP TỐC ĐỘ XÂY CỰC HẠN (~6 BLOCK/S)
        mc.player.networkHandler.sendChatMessage("#set blockPlacementPenalty 0");
        mc.player.networkHandler.sendChatMessage("#set buildRepeatDelay 0");
        mc.player.networkHandler.sendChatMessage("#set layerOrder true");

        // 2. BẬT TỰ ĐỘNG LẤY ĐỒ VÀ DI CHUYỂN
        mc.player.networkHandler.sendChatMessage("#set allowInventory true");
        mc.player.networkHandler.sendChatMessage("#set allowChestFetch true");
        mc.player.networkHandler.sendChatMessage("#set allowParkour true");
        mc.player.networkHandler.sendChatMessage("#set allowPlace true");

        // 3. ĐỊNH HƯỚNG BARITONE ĐẾN TỌA ĐỘ RƯƠNG ĐÃ LƯU KHI CẦN
        if (pos != null) {
            // Đưa vị trí rương vào danh sách ưu tiên của Baritone
            mc.player.networkHandler.sendChatMessage("#container " + pos.getX() + " " + pos.getY() + " " + pos.getZ());
        }

        // KÍCH HOẠT XÂY DỰNG
        mc.player.networkHandler.sendChatMessage("#build");
        
        info("Auto Build: Đã BẬT! Tốc độ 6 block/s - Đã gán tọa độ rương: " + pos.getX() + ", " + pos.getY() + ", " + pos.getZ());
    }

    @Override
    public void onDeactivate() {
        if (mc.player != null && mc.player.networkHandler != null) {
            mc.player.networkHandler.sendChatMessage("#stop");
            info("Auto Build: Đã TẮT.");
        }
    }
}
