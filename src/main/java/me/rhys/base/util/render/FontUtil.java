package me.rhys.base.util.render;

import me.rhys.base.util.vec.Vec2f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

public class FontUtil {

    private static final FontRenderer fontRenderer = Minecraft.getMinecraft().fontRendererObj;

    private FontUtil() {
    }

    public static void drawString(String str, float x, float y, int color) {
        fontRenderer.drawString(str, x, y, color);
    }

    public static void drawString(String str, Vec2f pos, int color) {
        drawString(str, pos.x, pos.y, color);
    }

    public static void drawCenteredString(String str, float x, float y, int color) {
        drawString(str, x - getStringWidth(str) / 2.0f, y - getFontHeight() / 2.0f, color);
    }

    public static void drawCenteredString(String str, Vec2f pos, int color) {
        drawCenteredString(str, pos.x, pos.y, color);
    }

    public static void drawStringWithShadow(String str, float x, float y, int color) {
        fontRenderer.drawStringWithShadow(str, x, y, color);
    }

    public static void drawStringWithShadow(String str, Vec2f pos, int color) {
        drawStringWithShadow(str, pos.x, pos.y, color);
    }

    public static void drawCenteredStringWithShadow(String str, float x, float y, int color) {
        drawStringWithShadow(str, x - getStringWidth(str) / 2.0f, y - getFontHeight() / 2.0f, color);
    }

    public static void drawCenteredStringWithShadow(String str, Vec2f pos, int color) {
        drawCenteredStringWithShadow(str, pos.x, pos.y, color);
    }

    public static float getStringWidth(String str) {
        return fontRenderer.getStringWidth(str);
    }

    public static float getFontHeight() {
        return fontRenderer.FONT_HEIGHT;
    }

}
