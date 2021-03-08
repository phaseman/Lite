package me.rhys.client.module.render;

import me.rhys.base.Lite;
import me.rhys.base.event.data.EventTarget;
import me.rhys.base.event.impl.player.PlayerUpdateEvent;
import me.rhys.base.event.impl.render.RenderGameOverlayEvent;
import me.rhys.base.module.Module;
import me.rhys.base.module.data.Category;
import me.rhys.base.module.setting.manifest.Clamp;
import me.rhys.base.module.setting.manifest.Name;
import me.rhys.base.util.MathUtil;
import me.rhys.base.util.render.FontUtil;
import me.rhys.base.util.render.RenderUtil;
import me.rhys.base.util.vec.Vec2f;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumChatFormatting;

import java.awt.*;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class HUD extends Module {

    @Name("Rainbow")
    public boolean rainbow = false;

    @Name("Rainbow type")
    public RainbowType rainbowType = RainbowType.NORMAL;

    @Name("Background")
    public boolean background = true;

    @Name("Watermark")
    public boolean waterMark = true;

    @Name("ArrayList")
    public boolean arrayList = true;

    @Name("Scale")
    @Clamp(min = 0.1f, max = 2f)
    public float scale = 1f;

    @Name("R")
    @Clamp(min = 0, max = 255)
    public int r = 52;

    @Name("G")
    @Clamp(min = 0, max = 255)
    public int g = 152;

    @Name("B")
    @Clamp(min = 0, max = 255)
    public int b = 219;

    @Name("Scoreboard")
    public boolean scoreboard = false;

    @Name("Scoreboard Y")
    @Clamp(min = 0, max = 300)
    public int scoreboardY = 75;

    public int rCopy = r, gCopy = g, bCopy = b;
    private boolean rBack, gBack, bBack;
    private long lastR, lastG, lastB, nextRandomR, nextRandomG, nextRandomB;

    public HUD(String name, String description, Category category, int keyCode) {
        super(name, description, category, keyCode);
        setHidden(true);
    }

    @EventTarget
    public void onUpdate(PlayerUpdateEvent event) {

        if (rainbow && rainbowType == RainbowType.INCREASE) {
            //R

            if ((System.currentTimeMillis() - lastR) > nextRandomR) {
                lastR = System.currentTimeMillis();
                nextRandomR = (long) MathUtil.randFloat(1, 100);
                if (!rBack) {
                    if (rCopy < 255) {
                        rCopy++;
                    } else {
                        rBack = true;
                    }
                }

                if (rBack) {
                    if (rCopy > 0) {
                        rCopy--;
                    } else {
                        rCopy = 1;
                        rBack = false;
                    }
                }
            }

            //G
            if ((System.currentTimeMillis() - lastG) > nextRandomG) {
                lastG = System.currentTimeMillis();
                nextRandomG = (long) MathUtil.randFloat(1, 100);
                if (!gBack) {
                    if (gCopy < 255) {
                        gCopy++;
                    } else {
                        gBack = true;
                    }
                }

                if (gBack) {
                    if (gCopy > 0) {
                        gCopy--;
                    } else {
                        gCopy = 1;
                        gBack = false;
                    }
                }
            }

            //B
            if ((System.currentTimeMillis() - lastB) > nextRandomB) {
                lastB = System.currentTimeMillis();
                nextRandomB = (long) MathUtil.randFloat(1, 100);
                if (!bBack) {
                    if (bCopy < 255) {
                        bCopy++;
                    } else {
                        bBack = true;
                    }
                }

                if (bBack) {
                    if (bCopy > 0) {
                        bCopy--;
                    } else {
                        bCopy = 1;
                        bBack = false;
                    }
                }
            }


            return;
        }

        rCopy = r;
        gCopy = g;
        bCopy = b;
    }

    @EventTarget
    void renderGameOverlay(RenderGameOverlayEvent event) {

        // draw watermark
        if (waterMark) {
            mc.fontRendererObj.drawStringWithShadow(
                    Lite.MANIFEST.getName().toUpperCase(Locale.ROOT).charAt(0)
                            + EnumChatFormatting.WHITE.toString()
                            + Lite.MANIFEST.getName().substring(1),
                    new Vec2f(10, 15), new Color(r, g, b).getRGB());
        }

        // draw the arraylist
        if (arrayList) {
            drawArrayList(event);
        }
    }

    void drawArrayList(RenderGameOverlayEvent event) {
        // default margin between items & the screen border
        int margin = 5;

        // start position
        Vec2f pos =
                new Vec2f(event.getWidth() - margin, margin / 2f - (FontUtil.getFontHeight() + margin) + 1);

        // saving the last location
        AtomicReference<Vec2f> lastPosition =
                new AtomicReference<>(null);

        // loop index
        AtomicInteger index =
                new AtomicInteger(0);

        Lite.MODULE_FACTORY.
                getActiveModules().filter(module -> !module.isHidden())
                .sorted((o1, o2) -> (int) (FontUtil.getStringWidth(o2.getDisplayName()) - FontUtil.getStringWidth(o1.getDisplayName())))
                .forEachOrdered(module -> {
                    float width = FontUtil.getStringWidth(module.getDisplayName());
                    float height = FontUtil.getFontHeight();
                    int color = getColor(index.get(), 25);

                    Vec2f currentPos = pos
                            .add(0, height + margin)
                            .clone()
                            .add(-width, 0);


                    // text
                    FontUtil.drawStringWithShadow(module.getDisplayName(), currentPos, color);

                    lastPosition.set(currentPos);
                    index.addAndGet(1);
                });
    }

    public int getColor(int offset, int rainbowOffset) {
        if (rainbow && rainbowType == RainbowType.NORMAL) {
            return Color.HSBtoRGB(((Minecraft.getSystemTime() + (10 * (Minecraft.getMinecraft().thePlayer.ticksExisted + offset * rainbowOffset))) % 5000F) / 5000F, 1, 1);
        } else {
            return new Color(rCopy, gCopy, bCopy).getRGB();
        }
    }

    public enum RainbowType {
        NORMAL,
        INCREASE
    }
}
