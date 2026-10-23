package com.autobuild.modules;

import com.autobuild.AutoBuildAddon;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.orbit.EventHandler;

import baritone.api.BaritoneAPI;
import baritone.api.selection.ISelection;

public class AutoBuildModule extends Module {

    public AutoBuildModule() {
        super(AutoBuildAddon.CATEGORY, "auto-build", "Tự động xây dựng công trình tích hợp Litematica và Baritone.");
    }

    @Override
    public void onActivate() {
        info("Auto Build đã BẬT.");
    }

    @Override
    public void onDeactivate() {
        info("Auto Build đã TẮT.");
    }

    @EventHandler
    private void onTick(TickEvent.Post event) {
        if (mc.player == null || mc.world == null) return;

        // Bọc trong try-catch để dù Baritone chưa sẵn sàng cũng tuyệt đối KHÔNG crash game
        try {
            if (BaritoneAPI.getProvider() != null) {
                var baritone = BaritoneAPI.getProvider().getPrimaryBaritone();
                if (baritone != null && baritone.getSelectionManager() != null) {
                    ISelection[] selections = baritone.getSelectionManager().getSelections();
                    if (selections != null && selections.length > 0) {
                        for (ISelection selection : selections) {
                            if (selection != null) {
                                // Xử lý tọa độ selection.pos1() và selection.pos2() tại đây
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            // Ghi nhận lỗi nhẹ nếu cần, game vẫn chạy bình thường không bị văng
        }
    }
}
