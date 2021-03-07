package me.rhys.client.module.movement.speed;

import me.rhys.base.module.Module;
import me.rhys.base.module.data.Category;
import me.rhys.client.module.movement.speed.mode.BHop;
import me.rhys.client.module.movement.speed.mode.NCP;

/**
 * Created on 07/09/2020 Package me.rhys.client.module.movement
 */
public class Speed extends Module {
    public Speed(String name, String description, Category category, int keyCode) {
        super(name, description, category, keyCode);
        add(
                new BHop("BHop", this),
                new NCP("NCP", this)
        );
    }
}
