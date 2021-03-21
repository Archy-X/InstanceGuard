package com.archyx.instanceguard.region;

import com.archyx.instanceguard.flag.FlagType;
import com.archyx.instanceguard.flag.FlagValue;
import net.minestom.server.utils.Position;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public abstract class Region {

    private final String id;
    private ConcurrentMap<FlagType, FlagValue> flags;
    private int priority;

    public Region(String id) {
        this.id = id;
        this.flags = new ConcurrentHashMap<>();
    }

    public String getId() {
        return id;
    }

    @Nullable
    public FlagValue getFlag(FlagType flagType) {
        return flags.get(flagType);
    }

    public Map<FlagType, FlagValue> getFlags() {
        return flags;
    }

    public void setFlag(FlagType flagType, FlagValue value) {
        if (value == null) {
            flags.remove(flagType);
        } else {
            flags.put(flagType, value);
        }
    }

    public void setFlags(Map<FlagType, FlagValue> flags) {
        this.flags = new ConcurrentHashMap<>(flags);
    }

    public boolean removeFlag(FlagType flagType) {
        return flags.remove(flagType) != null;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public abstract boolean contains(Position position);

    @Override
    public int hashCode() {
        return id.hashCode();
    }

}
