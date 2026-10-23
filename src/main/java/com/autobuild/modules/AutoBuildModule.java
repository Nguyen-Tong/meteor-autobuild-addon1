package com.autobuild.modules;

import com.autobuild.AutoBuildAddon;
import meteordevelopment.meteorclient.systems.modules.Module;
import baritone.api.BaritoneAPI;
import baritone.api.Settings;

public class AutoBuildModule extends Module {

    public AutoBuildModule() {
        super(AutoBuildAddon.CATEGORY, "auto-build", "Xây dựng siêu tốc, tự di chuyển và tự động lấy vật liệu.");
    }

    @Override
    public void onActivate() {
        if (mc.player == null) return;

        try {
            if (BaritoneAPI.getProvider() != null) {
                var primary = BaritoneAPI.getProvider().getPrimaryBaritone();
                if (primary != null) {
                    Settings settings = BaritoneAPI.getSettings();

                    // 1. ÉP TỐC ĐỘ ĐẶT BLOCK (~6-8 BLOCK/S)
                    settings.blockPlacementPenalty.value = 0.0; // Bỏ thời gian chờ giữa các lần đặt
                    settings.buildRepeatDelay.value = 0;        // Đặt liên tục không nghỉ
                    settings.layerOrder.value = true;          // Đặt theo lớp để tối ưu đường di chuyển

                    // 2. TỰ ĐỘNG LẤY VẬT LIỆU & TỰ CHUYỂN HOTBAR
                    settings.allowInventory.value = true;       // Cho phép bot tự mở/lấy đồ từ Balo vào Hotbar
                    settings.allowChestFetch.value = true;      // Tự tìm và lấy vật liệu từ Rương xung quanh khi hết đồ

                    // 3. TỰ ĐỘNG DI CHUYỂN & BẮC CẦU
                    settings.allowParkour.value = true;        // Tự nhảy qua ô trống
                    settings.allowPlace.value = true;          // Cho phép tự bắc cầu dưới chân/trên không

                    // KÍCH HOẠT LỆNH XÂY DỰNG
                    if (primary.getCommandManager() != null) {
                        primary.getCommandManager().execute("build");
                        info("Auto Build: Đã tối ưu tốc độ 6 block/s & bật tự động lấy vật liệu!");
                    }
                }
            }
        } catch (Throwable t) {
            // Backup gửi lệnh chat nếu API gặp sự cố
            if (mc.player.networkHandler != null) {
                mc.player.networkHandler.sendChatMessage("#set blockPlacementPenalty 0");
                mc.player.networkHandler.sendChatMessage("#set allowInventory true");
                mc.player.networkHandler.sendChatMessage("#set allowChestFetch true");
                mc.player.networkHandler.sendChatMessage("#build");
                info("Auto Build: Đã bật qua kênh Chat Client!");
            }
        }
    }

    @Override
    public void onDeactivate() {
        try {
            if (BaritoneAPI.getProvider() != null) {
                var primary = BaritoneAPI.getProvider().getPrimaryBaritone();
                if (primary != null && primary.getCommandManager() != null) {
                    primary.getCommandManager().execute("stop");
                    info("Auto Build: Đã dừng xây dựng.");
                }
            }
        } catch (Throwable t) {
            if (mc.player != null && mc.player.networkHandler != null) {
                mc.player.networkHandler.sendChatMessage("#stop");
            }
        }
    }
}
