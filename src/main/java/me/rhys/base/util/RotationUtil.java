package me.rhys.base.util;

import me.rhys.base.util.entity.Location;
import me.rhys.base.util.vec.Vec2f;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.Vec3;

/**
 * Created on 07/09/2020 Package me.rhys.lite.util
 */
public class RotationUtil {

    public static Location location, lastLocation;
    private static final Minecraft minecraft = Minecraft.getMinecraft();

    public static Vec2f getNormalRotations(Entity entity) {
        return getNormalRotations(minecraft.thePlayer.getPositionVector().addVector(0.0D,
                minecraft.thePlayer.getEyeHeight(), 0.0D), entity.getPositionVector().addVector(0.0D, entity.getEyeHeight() / 2, 0.0D));
    }

    public static Vec2f getNormalRotations(Vec3 origin, Vec3 position) {
        Vec3 org = new Vec3(origin.xCoord, origin.yCoord, origin.zCoord);
        Vec3 difference = position.subtract(org);

        double distance = difference.flat().lengthVector();

        float yaw = ((float) Math.toDegrees(Math.atan2(difference.zCoord, difference.xCoord)) - 90.0F);
        float pitch = (float) (-Math.toDegrees(Math.atan2(difference.yCoord, distance)));

        return new Vec2f(yaw, pitch);
    }

    public static Vec2f getRotations(Vec3 origin, Vec3 position) {

        Vec3 org = new Vec3(origin.xCoord, origin.yCoord, origin.zCoord);
        Vec3 difference = position.subtract(org);
        double distance = difference.flat().lengthVector();
        float yaw = ((float) Math.toDegrees(Math.atan2(difference.zCoord, difference.xCoord)) - 90.0F);
        float pitch = (float) (-Math.toDegrees(Math.atan2(difference.yCoord, distance)));

        return new Vec2f(yaw, pitch);
    }

    public static Vec2f getRotations(Entity entity) {
        return getRotations(minecraft.thePlayer.getPositionVector().addVector(0.0D,
                minecraft.thePlayer.getEyeHeight(), 0.0D), entity.getPositionVector().addVector(0.0D, entity.getEyeHeight() / 2, 0.0D));
    }

    public static Vec2f getRotations(Vec3 position) {
        return getRotations(minecraft.thePlayer.getPositionVector().addVector(0.0D, minecraft.thePlayer.getEyeHeight(), 0.0D), position);
    }

    public static Vec2f clampRotation(Vec2f rotation) {
        float f = minecraft.gameSettings.mouseSensitivity * 0.6F + 0.2F;
        float f1 = f * f * f * 1.2f;

        return new Vec2f(rotation.x - (rotation.x % f1), rotation.y - (rotation.y % f1));
    }
}
