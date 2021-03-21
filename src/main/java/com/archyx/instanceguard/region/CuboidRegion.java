package com.archyx.instanceguard.region;

import net.minestom.server.utils.BlockPosition;
import net.minestom.server.utils.Position;

public class CuboidRegion extends Region {

    private BlockPosition corner1;
    private BlockPosition corner2;

    public CuboidRegion(String id, BlockPosition corner1, BlockPosition corner2) {
        super(id);
        this.corner1 = corner1;
        this.corner2 = corner2;
    }

    public BlockPosition getCorner1() {
        return corner1;
    }

    public void setCorner1(BlockPosition corner1) {
        this.corner1 = corner1;
    }

    public BlockPosition getCorner2() {
        return corner2;
    }

    public void setCorner2(BlockPosition corner2) {
        this.corner2 = corner2;
    }

    @Override
    public boolean contains(Position position) {
        double x = position.getX();
        double y = position.getY();
        double z = position.getZ();
        return x >= corner1.getX() && x < corner2.getX() + 1
                && y >= corner1.getY() && y < corner2.getY() + 1
                && z >= corner1.getZ() && z < corner2.getZ() + 1;
    }
}
