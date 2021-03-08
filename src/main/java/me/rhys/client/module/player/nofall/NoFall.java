package me.rhys.client.module.player.nofall;

import me.rhys.base.module.Module;
import me.rhys.base.module.data.Category;
import me.rhys.client.module.player.nofall.modes.DoublePacket;
import me.rhys.client.module.player.nofall.modes.Normal;
import me.rhys.client.module.player.nofall.modes.Packet;
import me.rhys.client.module.player.nofall.modes.Round;

public class NoFall extends Module {
    public NoFall(String name, String description, Category category, int keyCode) {
        super(name, description, category, keyCode);
        add(
                new Normal("Normal", this),
                new Packet("Packet", this),
                new DoublePacket("DoublePacket", this),
                new Round("Round", this)
        );
    }
}
