package com.zacharybarbanell.dimmod;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

public class PortalBlock extends BlockWithEntity {
    protected static final VoxelShape SHAPE = Block.createCuboidShape(0.0, 6.0, 0.0, 16.0, 12.0, 16.0);

    protected PortalBlock(Settings settings) {
        super(settings);
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new PortalBlockEntity(pos, state);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (world instanceof ServerWorld && entity.canUsePortals()
                && VoxelShapes.matchesAnywhere(
                        VoxelShapes.cuboid(entity.getBoundingBox().offset((double) (-pos.getX()),
                                (double) (-pos.getY()), (double) (-pos.getZ()))),
                        state.getOutlineShape(world, pos), BooleanBiFunction.AND)
                && entity.getVelocity().y <= 0) {
            // TODO this is where the code that sends the player somewhere goes
        }
    }

    @Override
    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
        return ItemStack.EMPTY;
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if(direction.getAxis().isHorizontal()) {
            if(!neighborState.isOf(this) && !neighborState.isSideSolidFullSquare(world, neighborPos, direction.getOpposite())) {
                return Blocks.AIR.getDefaultState();
            }
        } 
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        for (int i = 0; i < 4; i++) {
            Direction dir = Direction.fromHorizontal(i);
            BlockPos otherPos = pos.offset(dir);
            BlockState otherState = world.getBlockState(otherPos);
            if (!otherState.isOf(this) && !otherState.isSideSolidFullSquare(world, otherPos, dir.getOpposite())) {
                return false;
            }
        }
        return true;
    }

    public int distToEdge(WorldView world, BlockPos pos, Direction dir) {
        int i = 0;
        while (world.getBlockState(pos.offset(dir)).isOf(this)) {
            pos = pos.offset(dir);
            i++;
        }
        return i;
    }
}
