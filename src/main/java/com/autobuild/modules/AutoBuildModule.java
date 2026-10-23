package com.autobuild.modules;

import com.autobuild.AutoBuildAddon;
import meteordevelopment.meteorclient.systems.modules.Module;
import baritone.api.BaritoneAPI;

public class AutoBuildModule extends Module {

    public AutoBuildModule() {
        super(AutoBuildAddon.CATEGORY, "auto-build", "Tự động xây dựng công trình bằng Baritone khi bật module.");
    }

    @Override
    public void onActivate() {
        try {
            if (BaritoneAPI.getProvider() != null) {
                var primary = BaritoneAPI.getProvider().getPrimaryBaritone();
                if (primary != null && primary.getCommandManager() != null) {
                    // Tự động kích hoạt lệnh #build của Baritone ngay khi bật module
                    primary.getCommandManager().execute("build");
                    info("Auto Build đã BẬT: Baritone đang bắt đầu xây dựng!");
                }
            }
        } catch (Throwable t) {
            error("Lỗi kích hoạt Baritone: " + t.getMessage());
            t.printStackTrace();
        }
    }

    @Override
    public void onDeactivate() {
        try {
            if (BaritoneAPI.getProvider() != null) {
                var primary = BaritoneAPI.getProvider().getPrimaryBaritone();
                if (primary != null && primary.getCommandManager() != null) {
                    // Dừng Baritone khi tắt module
                    primary.getCommandManager().execute("stop");
                    info("Auto Build đã TẮT: Đã dừng Baritone.");
                }
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
