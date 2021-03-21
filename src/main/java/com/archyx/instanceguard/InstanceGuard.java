package com.archyx.instanceguard;

import com.archyx.instanceguard.commands.RegionCommand;
import com.archyx.instanceguard.commands.WandCommand;
import com.archyx.instanceguard.instance.InstanceRegionManager;
import com.archyx.instanceguard.player.PlayerManager;
import com.archyx.instanceguard.region.RegionManager;
import net.minestom.server.MinecraftServer;
import net.minestom.server.command.CommandManager;
import net.minestom.server.extensions.Extension;
import net.minestom.server.instance.Instance;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class InstanceGuard extends Extension {

    private PlayerManager playerManager;
    private ConcurrentMap<Instance, RegionManager> regionManagers;

    @Override
    public void initialize() {
        this.playerManager = new PlayerManager();
        this.regionManagers = new ConcurrentHashMap<>();
        registerCommands();
        new InstanceRegionManager(this);
    }

    @Override
    public void terminate() {

    }

    private void registerCommands() {
        CommandManager commandManager = MinecraftServer.getCommandManager();
        commandManager.register(new RegionCommand(this));
        commandManager.register(new WandCommand());
    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    @Nullable
    public RegionManager getRegionManager(Instance instance) {
        return regionManagers.get(instance);
    }

    public void setRegionManager(Instance instance, RegionManager regionManager) {
        regionManagers.put(instance, regionManager);
    }

}
