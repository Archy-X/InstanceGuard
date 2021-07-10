package com.archyx.instanceguard.listeners;

import com.archyx.instanceguard.InstanceGuard;
import com.archyx.instanceguard.flag.FlagType;
import com.archyx.instanceguard.flag.FlagValue;
import com.archyx.instanceguard.region.RegionManager;
import net.minestom.server.event.trait.CancellableEvent;
import net.minestom.server.instance.Instance;
import net.minestom.server.utils.Position;

public class InstanceGuardListener {

    protected final InstanceGuard extension;
    protected final Instance instance;

    public InstanceGuardListener(InstanceGuard extension, Instance instance) {
        this.extension = extension;
        this.instance = instance;
    }

    public boolean cancelIfFalse(CancellableEvent event, Position position, FlagType flagType) {
        RegionManager manager = extension.getRegionManager(instance);
        if (manager == null) return false;

        FlagValue value = manager.resolveFlagValue(position, flagType);
        if (value != null) {
            if (!value.getAsBoolean()) {
                event.setCancelled(true);
                return true;
            }
        }
        return false;
    }

}
