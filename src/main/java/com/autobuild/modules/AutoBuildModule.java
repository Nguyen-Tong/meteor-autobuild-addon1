package com.autobuild.modules;

import com.autobuild.AutoBuildAddon;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.settings.*;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.util.math.BlockPos;

public class AutoBuildModule extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    public final Setting<BlockPos> chestPos = sgGeneral.add(new BlockPosSetting.Builder()
        .name("storage-chest")
        .description("Tọa độ rương chứa vật liệu và băng.")
        .defaultValue(BlockPos.ORIGIN)
        .build()
    );

    public AutoBuildModule() {
        super(AutoBuildAddon.CATEGORY, "auto-build", "Addon xây dựng tự động thông minh hỗ trợ Ice và quản lý chất lỏng.");
    }

    @Override
    public void onActivate() {
        info("Auto Build đã được BẬT.");
    }

    @Override
    public void onDeactivate() {
        info("Auto Build đã TẮT.");
    }

    @EventHandler
    private void onTick(TickEvent.Post event) {
        if (mc.player == null || mc.world == null) return;
    }
}
