package io.github.woulfiee.ally;

import io.github.woulfiee.ally.commands.AllyCommand;
import io.github.woulfiee.ally.listener.Listeners;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Copyright (c) 2018 by Woulfiee. Created on 8/5/2018.
 * You are not permitted to use this code without my permission.
 * Contact: woulfieejd@outlook.com
 */

public class Ally extends JavaPlugin {

    private static Ally instance;

    public static Ally getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        getCommand("sojusz").setExecutor(new AllyCommand());
        Bukkit.getServer().getPluginManager().registerEvents(new Listeners(), this);

    }
}
