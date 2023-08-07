package com.zacharybarbanell.dimmod;

import org.joml.Matrix4f;

import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;

public class PortalBlockEntityRenderer<T extends PortalBlockEntity> implements BlockEntityRenderer<T> {

    final float TOP_HEIGHT = 0.75f;

    public PortalBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {    
    }

    @Override
    public void render(T entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers,
            int light, int overlay) {
        Matrix4f modelViewMat = matrices.peek().getPositionMatrix();
        VertexConsumer vertices = vertexConsumers.getBuffer(ExampleModClient.portalLayer);

        int px = 0;
        int pz = 1;
        int nx = 2;
        int nz = 3;

        ExampleModClient.portalProgram.getUniformOrDefault("px").set(px);
        ExampleModClient.portalProgram.getUniformOrDefault("pz").set(pz);
        ExampleModClient.portalProgram.getUniformOrDefault("nx").set(nx);
        ExampleModClient.portalProgram.getUniformOrDefault("nz").set(nz);

        vertices.vertex(modelViewMat, 0.0f, TOP_HEIGHT, 1.0f).next();
        vertices.vertex(modelViewMat, 1.0f, TOP_HEIGHT, 1.0f).next();
        vertices.vertex(modelViewMat, 1.0f, TOP_HEIGHT, 0.0f).next();
        vertices.vertex(modelViewMat, 0.0f, TOP_HEIGHT, 0.0f).next();
    }
    
}
