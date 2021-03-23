package me.rhys.client.module.player;

import me.rhys.base.Lite;
import me.rhys.base.event.data.EventTarget;
import me.rhys.base.event.impl.network.PacketEvent;
import me.rhys.base.module.Module;
import me.rhys.base.module.data.Category;
import me.rhys.base.module.setting.manifest.Clamp;
import me.rhys.base.module.setting.manifest.Name;
import net.minecraft.network.play.client.C00PacketKeepAlive;
import net.minecraft.network.play.server.S00PacketKeepAlive;

import java.util.concurrent.TimeUnit;

public class PingSpoof extends Module {
    public PingSpoof(String name, String description, Category category, int keyCode) {
        super(name, description, category, keyCode);
    }

    @Name("delay")
    @Clamp(min = 1, max = 2000)
    public int delay = 100;

    @EventTarget
    public void onEvent(PacketEvent event) {
        if(event.getPacket() instanceof S00PacketKeepAlive) {
            event.setCancelled(true);

            S00PacketKeepAlive keepalive = (S00PacketKeepAlive) event.getPacket();

            Lite.EXECUTOR_SERVICE.schedule(() -> {
                mc.thePlayer.sendQueue.addToSendQueue(new C00PacketKeepAlive(keepalive.func_149134_c()));
            }, delay, TimeUnit.MILLISECONDS);
        }
    }
}
