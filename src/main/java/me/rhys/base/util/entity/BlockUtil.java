package me.rhys.base.util.entity;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;

import java.util.Objects;

/**
 * Created on 03/12/2019 Package cc.flycode.stitch.util
 */
public class BlockUtil {

    private static Minecraft mc = Minecraft.getMinecraft();

    public static Block getBlockAtPos(BlockPos pos) {
        IBlockState blockState = getBlockStateAtPos(pos);
        if (blockState == null)
            return null;
        return blockState.getBlock();
    }

    public static IBlockState getBlockStateAtPos(BlockPos pos) {
        if (Minecraft.getMinecraft() == null || Minecraft.getMinecraft().theWorld == null)
            return null;
        return Minecraft.getMinecraft().theWorld.getBlockState(pos);
    }

    public static boolean isOnGround() {
        for (double d = 0.0; d <= 1.00; d+=0.05) {
            if (!Objects.requireNonNull(getBlockAtPos(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - d, mc.thePlayer.posZ))).getUnlocalizedName().toLowerCase().contains("air")) {
                return true;
            }
        }
        return false;
    }
    public static boolean isOnGround(double height) {
        if (!Minecraft.getMinecraft().theWorld.getCollidingBoundingBoxes(Minecraft.getMinecraft().thePlayer,
                Minecraft.getMinecraft().thePlayer.getEntityBoundingBox().offset(0.0D, -height, 0.0D)).isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isBlockUnderEntity(Entity entity) {

        for (int i = 0; (double) i < entity.posY + (double) entity.getEyeHeight(); i += 2) {

            AxisAlignedBB boundingBox = entity.getEntityBoundingBox().offset(0.0D, -i, 0.0D);

            if (!mc.theWorld.getCollidingBoundingBoxes(entity, boundingBox).isEmpty()) {
                return true;
            }
        }

        return false;
    }

    public static boolean onSlab(double amount) {
        boolean found = false;

        for (double i = 0.0; i < amount; i+=0.1f) {
            if (BlockUtil.getBlockAtPos(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - i, mc.thePlayer.posZ)).getUnlocalizedName().toLowerCase().contains("slab") ||
                    BlockUtil.getBlockAtPos(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - i, mc.thePlayer.posZ)).getUnlocalizedName().toLowerCase().contains("stair")) {
                found = true;
            }
        }

        return found;
    }
}
