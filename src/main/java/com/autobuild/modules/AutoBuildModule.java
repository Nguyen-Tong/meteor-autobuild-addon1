package com.autobuild.modules;

import com.autobuild.AutoBuildAddon;
import meteordevelopment.meteorclient.settings.DoubleSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.Module;

public class AutoBuildModule extends Module {

    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    // Cài đặt bán kính tự động quét tìm tất cả các rương xung quanh kho
    private final Setting<Double> chestSearchRadius = sgGeneral.add(new DoubleSetting.Builder()
        .name("bán-kính-quét-rương")
        .description("Bán kính (ô) Baritone tự quét và tìm tất cả rương chứa vật liệu.")
        .defaultValue(32.0)
        .min(5.0)
        .sliderMax(64.0)
        .build()
    );

    public AutoBuildModule() {
        super(AutoBuildAddon.CATEGORY, "auto-build", "Xây mượt theo Litematica/Schematic, tự rút đồ từ hệ thống kho rương.");
    }

    @Override
    public void onActivate() {
        if (mc.player == null || mc.player.networkHandler == null) return;

        // 1. ÉP TỐC ĐỘ ĐẶT BLOCK (~6-8 BLOCK/S)
        mc.player.networkHandler.sendChatMessage("#set blockPlacementPenalty 0");
        mc.player.networkHandler.sendChatMessage("#set buildRepeatDelay 0");
        mc.player.networkHandler.sendChatMessage("#set layerOrder true");

        // 2. BẬT TỰ ĐỘNG TÌM RƯƠNG TRONG KHO & RÚT ĐỒ TỪ BALO
        mc.player.networkHandler.sendChatMessage("#set allowInventory true");
        mc.player.networkHandler.sendChatMessage("#set allowChestFetch true");
        
        // Thiết lập khoảng cách quét toàn bộ dãy rương chứa vật liệu
        mc.player.networkHandler.sendChatMessage("#set maxDynamicSearchDistance " + chestSearchRadius.get().intValue());

        // 3. BẬT DI CHUYỂN, BẮC CẦU & LEO TRÈO THÔNG MINH
        mc.player.networkHandler.sendChatMessage("#set allowParkour true");
        mc.player.networkHandler.sendChatMessage("#set allowPlace true");

        // KÍCH HOẠT LỆNH XÂY THEO SCHEMATIC / LITEMATICA ĐANG LOAD
        mc.player.networkHandler.sendChatMessage("#build");
        
        info("Auto Build: Đã BẬT! Tốc độ 6 block/s - Đang quét kho rương trong bán kính " + chestSearchRadius.get().intValue() + " ô.");
    }

    @Override
    public void onDeactivate() {
        if (mc.player != null && mc.player.networkHandler != null) {
            mc.player.networkHandler.sendChatMessage("#stop");
            info("Auto Build: Đã TẮT.");
        }
    }
}
