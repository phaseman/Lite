package me.rhys.client.module.movement.speed.mode;

import me.rhys.base.event.data.EventTarget;
import me.rhys.base.event.impl.player.PlayerMoveEvent;
import me.rhys.base.module.ModuleMode;
import me.rhys.base.module.setting.manifest.Clamp;
import me.rhys.base.module.setting.manifest.Name;
import me.rhys.client.module.movement.speed.Speed;

public class YPort extends ModuleMode<Speed> {
    public YPort(String name, Speed parent) {
        super(name, parent);
    }

    @Name("Speed")
    @Clamp(min = 0.1, max = 9)
    public double movementSpeed = 0.5;

    @EventTarget
    void onMove(PlayerMoveEvent event) {
        if (mc.thePlayer.isPlayerMoving()) {
            if (mc.thePlayer.onGround) {
                event.motionY = mc.thePlayer.motionY = .42f;
            } else {
                event.motionY = mc.thePlayer.motionY - .42f;
            }

            event.setMovementSpeed(this.movementSpeed);
        } else {
            parent.pausePlayerSpeed(event);

            if (mc.thePlayer.motionY > .0f) {
                event.motionY = mc.thePlayer.motionY - .42f;
            }
        }
    }
}
