package com.archyx.instanceguard.instance;

import com.archyx.instanceguard.InstanceGuard;
import com.archyx.instanceguard.listeners.BlockListener;
import com.archyx.instanceguard.region.RegionManager;
import net.minestom.server.MinecraftServer;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.instance.InstanceChunkLoadEvent;
import net.minestom.server.instance.Instance;

public class InstanceRegionManager {

    private final InstanceGuard extension;

    public InstanceRegionManager(InstanceGuard extension) {
        this.extension = extension;
        init();
    }

    private void init() {
        GlobalEventHandler globalEventHandler = MinecraftServer.getGlobalEventHandler();
        globalEventHandler.addEventCallback(InstanceChunkLoadEvent.class, event -> {
            Instance instance = event.getInstance();
            // Create or load instance manager if not set
            if (extension.getRegionManager(instance) == null) {
                RegionManager regionManager = new RegionManager();
                extension.setRegionManager(instance, regionManager);
                new BlockListener(extension, instance);
            }
        });
    }

}
