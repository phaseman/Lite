package me.rhys.client.module.combat.aura.modes;

import me.rhys.base.event.Event;
import me.rhys.base.event.data.EventTarget;
import me.rhys.base.event.impl.player.PlayerMotionEvent;
import me.rhys.base.event.impl.player.PlayerUpdateEvent;
import me.rhys.base.module.ModuleMode;
import me.rhys.base.util.MathUtil;
import me.rhys.base.util.RotationUtil;
import me.rhys.base.util.Timer;
import me.rhys.base.util.vec.Vec2f;
import me.rhys.client.module.combat.aura.Aura;

public class Single extends ModuleMode<Aura> {
    public Single(String name, Aura parent) {
        super(name, parent);
    }

    @EventTarget
    void onUpdate(PlayerUpdateEvent event) {
        if (parent.lockView && parent.target != null) {
            Vec2f rotation = RotationUtil.getRotations(parent.target);

            if(parent.minecraftRotation) rotation = RotationUtil.clampRotation(rotation);

            mc.thePlayer.rotationYaw = rotation.getVecX();
            mc.thePlayer.rotationPitch = rotation.getVecY();
        }
    }

    @EventTarget
    void onMotion(PlayerMotionEvent event) {
        if (parent.target != null && event.getType() == (!parent.post ? Event.Type.PRE : Event.Type.POST)) {
            parent.swing(parent.target);

            if (!parent.lockView) {
                parent.aimAtTarget(event, parent.target);
            }
        }
    }
}
