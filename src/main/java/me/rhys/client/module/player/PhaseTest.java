package me.rhys.client.module.player;

import com.github.creeper123123321.viafabric.platform.VRBossBar;
import com.github.creeper123123321.viafabric.platform.VRViaAPI;
import me.rhys.base.Lite;
import me.rhys.base.event.Event;
import me.rhys.base.event.data.EventTarget;
import me.rhys.base.event.impl.player.PlayerMoveEvent;
import me.rhys.base.module.Module;
import me.rhys.base.module.data.Category;
import me.rhys.base.util.MathUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import us.myles.ViaVersion.api.boss.BossColor;
import us.myles.ViaVersion.api.boss.BossStyle;

import java.awt.*;

public class PhaseTest extends Module {
    public PhaseTest(String name, String description, Category category, int keyCode) {
        super(name, description, category, keyCode);
    }

    @EventTarget
    public void playerMove(PlayerMoveEvent event) {
        EntityPlayerSP player = event.getPlayer();
        player.sendMessage("epic test 1");
        if (player == null) {
            player.sendMessage("epic test 2");
            return;
        }
        try {
            player.sendMessage("epic test 3");
        } catch (Exception e) {
            player.sendMessage("epic test 4");
            e.printStackTrace();
        }
    }
}
