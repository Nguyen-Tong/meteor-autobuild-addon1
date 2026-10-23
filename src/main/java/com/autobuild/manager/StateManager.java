package com.autobuild.manager;

import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.util.Hand;

import static meteordevelopment.meteorclient.MeteorClient.mc;

public class StateManager {

    private boolean isWorking = false;

    public void startTask() {
        isWorking = true;
    }

    public void stopTask() {
        isWorking = false;
    }

    @EventHandler
    private void onTick(TickEvent.Post event) {
        if (mc.player == null || mc.world == null) return;

        if (isWorking) {
            // Ví dụ gọi tương tác vật phẩm đã sửa đúng cú pháp 1.21.1 (bỏ tham số mc.world thừa)
            // mc.interactionManager.interactItem(mc.player, mc.player.getActiveHand());
        }
    }
}
