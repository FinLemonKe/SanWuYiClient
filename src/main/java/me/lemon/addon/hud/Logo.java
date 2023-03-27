package me.lemon.addon.hud;

import me.lemon.addon.LemonClient;
import meteordevelopment.meteorclient.renderer.GL;
import meteordevelopment.meteorclient.renderer.Renderer2D;
import meteordevelopment.meteorclient.settings.*;
import meteordevelopment.meteorclient.systems.hud.HudElement;
import meteordevelopment.meteorclient.systems.hud.HudElementInfo;
import meteordevelopment.meteorclient.systems.hud.HudRenderer;
import meteordevelopment.meteorclient.utils.Utils;
import meteordevelopment.meteorclient.utils.render.color.RainbowColor;
import meteordevelopment.meteorclient.utils.render.color.SettingColor;
import net.minecraft.util.Identifier;

public class Logo extends HudElement {
    public static final HudElementInfo<Logo> INFO = new HudElementInfo<>(LemonClient.HUD_GROUP, "Lemon-logo", "Displays the LemonClient logo.", Logo::new);

    private static final Identifier LOGO = new Identifier("lemon-client", "text.png");
    private static final Identifier LOGO_FLAT = new Identifier("lemon-client", "text.png");

    private static final RainbowColor RAINBOW = new RainbowColor();

    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<Double> scale = sgGeneral.add(new DoubleSetting.Builder().name("scale").description("The scale.").defaultValue(2).min(1).sliderMin(1).sliderMax(5).onChanged(setting -> update()).build());
    public final Setting<Boolean> chroma = sgGeneral.add(new BoolSetting.Builder().name("chroma").description("Chroma logo animation.").defaultValue(false).build());
    private final Setting<Double> chromaSpeed = sgGeneral.add(new DoubleSetting.Builder().name("chroma-speed").description("Speed of the chroma animation.").defaultValue(0.09).min(0.01).sliderMax(5).decimalPlaces(2).onChanged(setting -> RAINBOW.setSpeed(setting / 100)).build());
    private final Setting<SettingColor> color = sgGeneral.add(new ColorSetting.Builder().name("logo-color").description("Color of the logo.").defaultValue(new SettingColor(255, 255, 255)).build());

    public Logo() {
        super(INFO);
        update();
        RAINBOW.setSpeed(chromaSpeed.get() / 100);
    }

    public void update() {
        box.setSize(72 * scale.get(), 15 * scale.get());
    }

    @Override
    public void render(HudRenderer renderer) {
        if (!Utils.canUpdate()) return;
        if (chroma.get()) {
            GL.bindTexture(LOGO_FLAT);
        } else {
            GL.bindTexture(LOGO);
        }
        Renderer2D.TEXTURE.begin();
        if (chroma.get()) {
            Renderer2D.TEXTURE.texQuad(x, y - 29 * scale.get(), 70 * scale.get(), 70 * scale.get(), RAINBOW.getNext(renderer.delta));
        } else {
            Renderer2D.TEXTURE.texQuad(x, y - 29 * scale.get(), 70 * scale.get(), 70 * scale.get(), color.get());
        }
        Renderer2D.TEXTURE.render(null);
    }
}
