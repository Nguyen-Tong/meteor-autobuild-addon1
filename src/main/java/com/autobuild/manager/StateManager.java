package com.autobuild.manager;

import com.autobuild.modules.AutoBuildModule;
import meteordevelopment.meteorclient.utils.player.InvUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.Items;

public class StateManager {
    private final AutoBuildModule module;
    private final MinecraftClient mc = MinecraftClient.getInstance();

    public enum State {
        IDLE,
        BUILDING,
        PLACE_ICE_PLACEHOLDERS,
        PROCESS_FLUIDS,
        FETCHING_ITEMS,
        EMERGENCY_MLG
    }

    private State currentState = State.IDLE;

    public StateManager(AutoBuildModule module) {
        this.module = module;
    }

    public void reset() {
        currentState = State.BUILDING;
    }

    public void tick() {
        if (mc.player == null || mc.world == null) return;

        if (mc.player.fallDistance > 3.0f && hasMLGWaterBucket()) {
            currentState = State.EMERGENCY_MLG;
        }

        if (mc.player.getHungerManager().getFoodLevel() < 14) {
            handleAutoEat();
        }

        switch (currentState) {
            case BUILDING:
                tickBuilding();
                break;
            case EMERGENCY_MLG:
                executeMLG();
                break;
            default:
                break;
        }
    }

    private boolean hasMLGWaterBucket() {
        return mc.player.getInventory().getStack(8).isOf(Items.WATER_BUCKET);
    }

    private void handleAutoEat() {
        InvUtils.swap(7, true);
        mc.options.useKey.setPressed(true);
    }

    private void tickBuilding() {}

    private void executeMLG() {
        mc.player.setPitch(90.0f);
        InvUtils.swap(8, true);
        mc.interactionManager.interactItem(mc.player, mc.world, mc.player.getActiveHand());
        currentState = State.BUILDING;
    }
}
