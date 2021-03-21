package com.archyx.instanceguard.player;

import net.minestom.server.entity.Player;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class PlayerManager {

    private final ConcurrentMap<UUID, PlayerData> playerDataMap;

    public PlayerManager() {
        this.playerDataMap = new ConcurrentHashMap<>();
    }

    public PlayerData getPlayerData(UUID uniqueId) {
        PlayerData playerData = this.playerDataMap.get(uniqueId);
        if (playerData == null) { // Create if null
            playerData = new PlayerData(uniqueId);
            this.playerDataMap.put(uniqueId, playerData);
        }
        return playerData;
    }

    public PlayerData getPlayerData(Player player) {
        return getPlayerData(player.getUuid());
    }

}
