package me.rhys.client.module.movement;

import me.rhys.base.event.data.EventTarget;
import me.rhys.base.event.impl.player.PlayerMoveEvent;
import me.rhys.base.module.Module;
import me.rhys.base.module.data.Category;
import me.rhys.base.module.setting.manifest.Name;

public class Sprint extends Module {

    @Name("Omni")
    public static boolean omniDir = false;

    public Sprint(String name, String description, Category category, int keyCode) {
        super(name, description, category, keyCode);
    }

    @EventTarget
    void onPlayerMove(PlayerMoveEvent event) {
        mc.thePlayer.setSprinting(omniDir ? mc.thePlayer.moveForward != 0
                || mc.thePlayer.moveStrafing != 0 : mc.thePlayer.moveForward > 0);
    }
}
