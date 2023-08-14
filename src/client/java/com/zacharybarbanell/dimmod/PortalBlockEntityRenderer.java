package com.zacharybarbanell.dimmod;

import org.joml.Matrix4f;

import net.minecraft.block.Block;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldView;

public class PortalBlockEntityRenderer<T extends PortalBlockEntity> implements BlockEntityRenderer<T> {

    final float TOP_HEIGHT = 0.75f;

    public PortalBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {    
    }

    @Override
    public void render(T entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers,
            int light, int overlay) {
        Matrix4f modelViewMat = matrices.peek().getPositionMatrix();
        VertexConsumer vertices = vertexConsumers.getBuffer(ExampleModClient.portalLayer);
        
        WorldView world = entity.getWorld();
        BlockPos pos = entity.getPos();
        PortalBlock portalBlock;
        if(world.getBlockState(pos).getBlock() instanceof PortalBlock pb) {
            portalBlock = pb;
        }
        else {
            return;
        }

        int px = portalBlock.distToEdge(world, pos, Direction.EAST);
        int pz = portalBlock.distToEdge(world, pos, Direction.SOUTH);
        int nx = portalBlock.distToEdge(world, pos, Direction.WEST);
        int nz = portalBlock.distToEdge(world, pos, Direction.NORTH);

        vertices.vertex(modelViewMat, 0.0f, TOP_HEIGHT, 1.0f).color(px, pz, nx, nz).next();
        vertices.vertex(modelViewMat, 1.0f, TOP_HEIGHT, 1.0f).color(px, pz, nx, nz).next();
        vertices.vertex(modelViewMat, 1.0f, TOP_HEIGHT, 0.0f).color(px, pz, nx, nz).next();
        vertices.vertex(modelViewMat, 0.0f, TOP_HEIGHT, 0.0f).color(px, pz, nx, nz).next();
    }
    
}
