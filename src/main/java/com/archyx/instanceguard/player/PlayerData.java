package com.archyx.instanceguard.player;

import net.minestom.server.utils.BlockPosition;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class PlayerData {

    private final UUID uniqueId;
    private BlockPosition firstPosition;
    private BlockPosition secondPosition;

    public PlayerData(UUID uniqueId) {
        this.uniqueId = uniqueId;
    }

    public UUID getUniqueId() {
        return uniqueId;
    }

    @Nullable
    public BlockPosition getFirstPosition() {
        return firstPosition;
    }

    public void setFirstPosition(BlockPosition firstPosition) {
        this.firstPosition = firstPosition;
    }

    @Nullable
    public BlockPosition getSecondPosition() {
        return secondPosition;
    }

    public void setSecondPosition(BlockPosition secondPosition) {
        this.secondPosition = secondPosition;
    }

}
