package me.rhys.client.module.movement.noslow;

import me.rhys.client.module.movement.noslow.modes.NCP;
import me.rhys.client.module.movement.noslow.modes.Vanilla;
import me.rhys.base.module.Module;
import me.rhys.base.module.data.Category;

/**
 * Created on 07/09/2020 Package me.rhys.client.module.movement.noslow
 */
public class NoSlow extends Module {
    public NoSlow(String name, String description, Category category, int keyCode) {
        super(name, description, category, keyCode);
        add(new Vanilla("Vanilla", this), new NCP("NCP", this));
    }
}
