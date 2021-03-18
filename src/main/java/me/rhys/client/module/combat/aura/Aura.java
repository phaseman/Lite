package me.rhys.client.module.combat.aura;

import me.rhys.base.Lite;
import me.rhys.base.event.data.EventTarget;
import me.rhys.base.event.impl.player.PlayerMotionEvent;
import me.rhys.base.event.impl.player.PlayerUpdateEvent;
import me.rhys.base.module.Module;
import me.rhys.base.module.data.Category;
import me.rhys.base.module.setting.manifest.Clamp;
import me.rhys.base.module.setting.manifest.Name;
import me.rhys.base.util.RotationUtil;
import me.rhys.base.util.vec.Vec2f;
import me.rhys.client.module.combat.aura.modes.Single;
import me.rhys.client.module.combat.criticals.Criticals;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

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

    @Name("rayCheck")
    public boolean rayCheck = true;

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

    @Name("Crack")
    public boolean crack = false;

    @Name("Crack Type")
    public CrackType crackType = CrackType.NORMAL;

    @Name("Crack Size")
    @Clamp(min = 1, max = 20)
    public int crackSize = 4;

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
                if (!this.isEntityValid(entity) || !(entity instanceof EntityLivingBase)) continue;

                return (EntityLivingBase) entity;
            }
        }
        return null;
    }

    private boolean isEntityValid(Entity entity) {
        if (mc.thePlayer.isEntityEqual(entity)) return false;

        //Checking reach distance
        if(rayCheck) {
            final AxisAlignedBB targetBox = entity.getEntityBoundingBox();
            final Vec2f rotation = RotationUtil.getRotations(entity);

            final Vec3 origin = mc.thePlayer.getPositionEyes(1.0f);
            Vec3 look = entity.getVectorForRotation(rotation.y, rotation.x);

            look = origin.addVector(look.xCoord * reach,
                    look.yCoord * reach,
                    look.zCoord * reach);
            MovingObjectPosition collision = targetBox.calculateIntercept(origin, look);

            if (collision == null) return false;
        } else if (mc.thePlayer.getDistanceToEntity(entity) >= reach) return false;

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

    public void doCritical() {
        Criticals criticals = (Criticals) Lite.MODULE_FACTORY.findByClass(Criticals.class);

        if (criticals.getData().isEnabled()) {
            criticals.processCriticalHit();
        }

        if (this.crack) {
            for (int i = 0; i < this.crackSize; i++) {
                if (this.crackType == CrackType.NORMAL) {
                    mc.thePlayer.onCriticalHit(this.target);
                }

                if (this.crackType == CrackType.ENCHANT) {
                    mc.thePlayer.onEnchantmentCritical(this.target);
                }
            }
        }
    }

    public enum CrackType {
        ENCHANT,
        NORMAL
    }
}
