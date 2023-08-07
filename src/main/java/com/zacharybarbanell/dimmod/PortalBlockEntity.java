package com.zacharybarbanell.dimmod;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public class PortalBlockEntity extends BlockEntity {
    private PortalBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public PortalBlockEntity(BlockPos pos, BlockState state) {
        super(ExampleMod.PORTAL_BLOCK_ENTITY, pos, state);
    }

    public boolean shouldDrawSide(Direction direction) {
        return direction == Direction.UP;
    }
}
