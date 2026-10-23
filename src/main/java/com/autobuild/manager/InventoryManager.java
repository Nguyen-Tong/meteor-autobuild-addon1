package com.autobuild.manager;

import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class InventoryManager {
    private final MinecraftClient mc = MinecraftClient.getInstance();

    public boolean isProtectedSurvivalItem(ItemStack stack) {
        if (stack == null || stack.isEmpty()) return false;

        boolean isPickaxe = stack.isOf(Items.NETHERITE_PICKAXE) || 
                            stack.isOf(Items.DIAMOND_PICKAXE) || 
                            stack.isOf(Items.IRON_PICKAXE) || 
                            stack.isOf(Items.STONE_PICKAXE) || 
                            stack.isOf(Items.WOODEN_PICKAXE);

        boolean isAxe = stack.isOf(Items.NETHERITE_AXE) || 
                        stack.isOf(Items.DIAMOND_AXE) || 
                        stack.isOf(Items.IRON_AXE) || 
                        stack.isOf(Items.STONE_AXE) || 
                        stack.isOf(Items.WOODEN_AXE);

        boolean isWaterBucket = stack.isOf(Items.WATER_BUCKET);
        boolean isFood = stack.getItem().getFoodComponent() != null;

        return isPickaxe || isAxe || isWaterBucket || isFood;
    }

    public boolean isProtectedHotbarSlot(int slotIndex) {
        return slotIndex >= 5 && slotIndex <= 8;
    }
}
