package com.autobuild;

import com.autobuild.modules.AutoBuildModule;
import meteordevelopment.meteorclient.addons.MeteorAddon;
import meteordevelopment.meteorclient.systems.modules.Category;
import meteordevelopment.meteorclient.systems.modules.Modules;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AutoBuildAddon extends MeteorAddon {
    public static final Logger LOG = LoggerFactory.getLogger("AutoBuild");
    public static final Category CATEGORY = new Category("AutoBuild");

    @Override
    public void onInitialize() {
        LOG.info("Initializing AutoBuild Addon");
        Modules.get().add(new AutoBuildModule());
    }

    @Override
    public void onRegisterCategories() {
        Modules.registerCategory(CATEGORY);
    }

    @Override
    public String getPackage() {
        return "com.autobuild";
    }
}
