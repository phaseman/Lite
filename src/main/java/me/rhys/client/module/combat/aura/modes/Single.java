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
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.C02PacketUseEntity;

public class Single extends ModuleMode<Aura> {
    public Single(String name, Aura parent) {
        super(name, parent);
    }

    private final Timer attackTimer = new Timer();

    @Override
    public void onEnable() {
        this.attackTimer.reset();
    }

    @EventTarget
    void onUpdate(PlayerUpdateEvent event) {
        if (parent.lockView && parent.target != null) {
            Vec2f rotation = RotationUtil.getRotations(parent.target);
            mc.thePlayer.rotationYaw = rotation.getVecX();
            mc.thePlayer.rotationPitch = rotation.getVecY();
        }
    }

    @EventTarget
    void onMotion(PlayerMotionEvent event) {
        if (parent.target != null && event.getType() == Event.Type.PRE) {
            this.swing(parent.target);

            if (!parent.lockView) {
                this.aimAtTarget(event, parent.target);
            }
        }
    }

    void swing(Entity target) {
        double aps = (parent.cps + MathUtil.randFloat(MathUtil.randFloat(1, 3), MathUtil.randFloat(3, 5)));

        if (this.attackTimer.hasReached(1000L / aps)) {
            this.attackTimer.reset();
            mc.thePlayer.swingItem();
            parent.doCritical();
            mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(target, C02PacketUseEntity.Action.ATTACK));
        }
    }

    void aimAtTarget(PlayerMotionEvent event, Entity target) {
        Vec2f rotation = RotationUtil.getRotations(target);
        event.getPosition().setRotation(rotation);
    }
}
