package com.autobuild.manager;

import meteordevelopment.meteorclient.utils.player.FindItemResult;
import meteordevelopment.meteorclient.utils.player.InvUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.component.DataComponentTypes;

import static meteordevelopment.meteorclient.MeteorClient.mc;

public class InventoryManager {

    /**
     * Kiểm tra vật phẩm có phải là thức ăn hay không (chuẩn API Minecraft 1.21.1)
     */
    public static boolean isFood(ItemStack stack) {
        if (stack.isEmpty()) return false;
        return stack.contains(DataComponentTypes.FOOD);
    }

    /**
     * Tìm rương hoặc khối vật liệu trong thanh hotbar
     */
    public static FindItemResult findBuildingMaterial() {
        return InvUtils.findInHotbar(itemStack -> 
            itemStack.getItem().toString().toLowerCase().contains("chest") ||
            itemStack.getItem().toString().toLowerCase().contains("block")
        );
    }
}
