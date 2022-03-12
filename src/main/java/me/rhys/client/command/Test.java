package me.rhys.client.command;

import me.rhys.base.command.Command;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSign;

import java.awt.*;
import java.awt.event.InputEvent;

public class Test extends Command {

    public Robot bot;

    public Test(String label, String description, String... aliases) {
        super(label, description, aliases);
    }

    @Override
    public boolean handle(EntityPlayerSP player, String label, String[] args) {
        if (args == null) {
        } else {
            player.sendChatMessage("This command has no arguments!");
        }
        return true;
    }

}
