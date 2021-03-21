package com.archyx.instanceguard.region;

import com.archyx.instanceguard.flag.FlagType;
import com.archyx.instanceguard.flag.FlagValue;
import net.minestom.server.utils.Position;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class RegionManager {

    private final ConcurrentMap<String, Region> regions;

    public RegionManager() {
        this.regions = new ConcurrentHashMap<>();
    }

    @Nullable
    public Region getRegion(String id) {
        return regions.get(id);
    }

    public Set<Region> getRegions(Position position) {
        Set<Region> regionSet = new HashSet<>();
        for (Region region : regions.values()) {
            if (region.contains(position)) {
                regionSet.add(region);
            }
        }
        return regionSet;
    }

    public void addRegion(Region region) {
        regions.put(region.getId(), region);
    }

    public void removeRegion(String id) {
        regions.remove(id);
    }

    public ConcurrentMap<String, Region> getRegionMap() {
        return regions;
    }

    /**
     * Calculates the correct FlagValue for a given position accounting for region priority
     * @param position The position
     * @param flagType The flag
     * @return The calculated FlagValue
     */
    @Nullable
    public FlagValue resolveFlagValue(Position position, FlagType flagType) {
        FlagValue value = null;
        int highestPriority = 0;
        for (Region region : getRegions(position)) {
            FlagValue regionValue = region.getFlag(flagType);
            if (regionValue != null) { // If the flag is set
                if (value == null) {
                    value = regionValue;
                } else if (!value.getValue().equals(regionValue.getValue()) && region.getPriority() > highestPriority) {
                    value = regionValue;
                    highestPriority = region.getPriority();
                }
            }
        }
        return value;
    }

}
