package me.rhys.client.module.movement.speed.mode;

import me.rhys.base.event.data.EventTarget;
import me.rhys.base.event.impl.player.PlayerUpdateEvent;
import me.rhys.base.module.ModuleMode;
import me.rhys.base.module.setting.manifest.Clamp;
import me.rhys.base.module.setting.manifest.Name;
import me.rhys.client.module.movement.speed.Speed;

public class MinecraftPort extends ModuleMode<Speed> {
    public MinecraftPort(String name, Speed parent) {
        super(name, parent);
    }

    @Name("Cancel Bobbing")
    public boolean cancelBobbing = true;

    @Name("Down")
    @Clamp(min = .10, max = 9)
    public double downValue = 4f;

    @EventTarget
    void onUpdate(PlayerUpdateEvent event) {
        if (this.cancelBobbing) {
            mc.thePlayer.cameraPitch = mc.thePlayer.cameraYaw = 0;
            mc.thePlayer.prevCameraPitch = mc.thePlayer.prevCameraYaw = 0;
        }

        if (!mc.thePlayer.isPlayerMoving()) return;

        mc.gameSettings.keyBindJump.pressed = false;
        mc.thePlayer.setSprinting(true);

        if (mc.thePlayer.onGround) {
            mc.thePlayer.jump();
        } else {
            mc.thePlayer.motionY -= this.downValue;
        }
    }
}
