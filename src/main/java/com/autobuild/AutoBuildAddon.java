package com.autobuild;

import com.mojang.logging.LogUtils;
import meteordevelopment.meteorclient.addons.MeteorAddon;
import meteordevelopment.meteorclient.systems.modules.Category;
import meteordevelopment.meteorclient.systems.modules.Modules;
import com.autobuild.modules.AutoBuildModule;
import org.slf4j.Logger;

public class AutoBuildAddon extends MeteorAddon {
    public static final Logger LOG = LogUtils.getLogger();
    public static final Category CATEGORY = new Category("AutoBuild");

    @Override
    public void onInitialize() {
        LOG.info("Initializing Auto Build Addon for Meteor Client...");
        Modules.get().add(new AutoBuildModule());
    }

    @Override
    public void onRegisterCategories() {
        Modules.registerCategory(CATEGORY);
    }
}
