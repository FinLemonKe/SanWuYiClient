package me.lemon.addon;

import com.mojang.logging.LogUtils;
import me.lemon.addon.hud.Logo;
import me.lemon.addon.modules.ChatConfig;
import me.lemon.addon.modules.SanWuYiFly;
import meteordevelopment.meteorclient.addons.MeteorAddon;
import meteordevelopment.meteorclient.systems.hud.Hud;
import meteordevelopment.meteorclient.systems.hud.HudGroup;
import meteordevelopment.meteorclient.systems.modules.Category;
import meteordevelopment.meteorclient.systems.modules.Modules;
import meteordevelopment.meteorclient.systems.modules.combat.AntiAnchor;
import org.slf4j.Logger;

public class LemonClient extends MeteorAddon {
    public static final Logger LOG = LogUtils.getLogger();
    public static final Category Main = new Category("CuteLemonKe");
    public static final HudGroup HUD_GROUP = new HudGroup("SanWuYiClient");

    @Override
    public void onInitialize() {
        LOG.info("Initializing SanWuYi Client");

        // Modules
        Modules.get().add(new AntiAnchor());
        Modules.get().add(new ChatConfig());
        Modules.get().add(new SanWuYiFly());

        // Commands
//        Commands.get().add(new ());

        // HUD
        Hud.get().register(Logo.INFO);
    }

    @Override
    public void onRegisterCategories() {
        Modules.registerCategory(Main);
    }

    @Override
    public String getPackage() {
        return "me.lemon.addon";
    }
}
