package me.rhys.client.module.combat.aura;

import me.rhys.base.Lite;
import me.rhys.base.event.data.EventTarget;
import me.rhys.base.event.impl.player.PlayerMotionEvent;
import me.rhys.base.event.impl.player.PlayerUpdateEvent;
import me.rhys.base.module.Module;
import me.rhys.base.module.data.Category;
import me.rhys.base.module.setting.manifest.Clamp;
import me.rhys.base.module.setting.manifest.Name;
import me.rhys.client.module.combat.aura.modes.Single;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;

public class Aura extends Module {
    public Aura(String name, String description, Category category, int keyCode) {
        super(name, description, category, keyCode);
        add(new Single("Single", this));
    }

    @Name("CPS")
    @Clamp(min = 1, max = 20)
    public double cps = 15;

    @Name("Reach")
    @Clamp(min = 1, max = 9)
    public double reach = 4.25f;

    @Name("Monsters")
    public boolean monsters = false;

    @Name("Sleeping")
    public boolean sleeping = true;

    @Name("Invisibles")
    public boolean invisible = true;

    @Name("KeepSprint")
    public boolean keepSprint = false;

    @Name("LockView")
    public boolean lockView = false;

    public EntityLivingBase target;

    @Override
    public void onDisable() {
        this.target = null;
    }

    @EventTarget
    public void onUpdate(PlayerUpdateEvent event) {
        this.target = this.findTarget();

        if (this.target != null) {
            mc.thePlayer.setSprinting(this.keepSprint);

            if (!this.keepSprint) {
                mc.gameSettings.keyBindSprint.pressed = false;
            }
        }
    }



    public EntityLivingBase findTarget() {

        for (Entity entity : mc.theWorld.loadedEntityList) {
            if (entity != null) {
                if (!this.isEntityValid(entity)) continue;

                return (EntityLivingBase) entity;
            }
        }

        return null;
    }

    private boolean isEntityValid(Entity entity) {
        if (mc.thePlayer.isEntityEqual(entity)) return false;

        if (mc.thePlayer.getDistanceToEntity(entity) >= reach) return false;

        if (Lite.FRIEND_MANAGER.getFriend(entity.getName()) != null) return false;

        if (!sleeping && ((EntityLivingBase) entity).isPlayerSleeping())
            return false;

        if (!sleeping && ((EntityLivingBase) entity).isPlayerSleeping())
            return false;

        if (entity.isInvisible() && !invisible)
            return false;

        if (entity instanceof EntityArmorStand)
            return false;

        if (entity.isInvisible() && !invisible)
            return false;

        if (!monsters && (entity instanceof EntityMob || entity instanceof EntityVillager))
            return false;

        return monsters || entity instanceof EntityPlayer;
    }
}
