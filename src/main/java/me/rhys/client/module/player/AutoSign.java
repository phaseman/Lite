package me.rhys.client.module.player;

import me.rhys.base.event.data.EventTarget;
import me.rhys.base.event.impl.player.PlayerMoveEvent;
import me.rhys.base.event.impl.render.RenderEvent;
import me.rhys.base.module.Module;
import me.rhys.base.module.data.Category;
import net.minecraft.client.entity.EntityPlayerSP;

import java.awt.*;
import java.awt.event.InputEvent;

public class AutoSign extends Module {

    public Robot bot;

    public AutoSign(String name, String description, Category category, int keyCode) {
        super(name, description, category, keyCode);
    }

    @EventTarget
    public void playerRender(PlayerMoveEvent event) {
        EntityPlayerSP player = mc.thePlayer;
        if (player != null) {
            if (player.inventory.getCurrentItem()  != null
            && player.inventory.getCurrentItem().getDisplayName().toLowerCase().contains("sign")) {
                try {
                    bot = new Robot();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                bot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
                return;
            }
        }
    }
}
