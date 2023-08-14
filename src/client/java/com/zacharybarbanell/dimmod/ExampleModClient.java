package com.zacharybarbanell.dimmod;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.CoreShaderRegistrationCallback;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderPhase;
import net.minecraft.client.render.VertexFormat.DrawMode;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.util.Identifier;

public class ExampleModClient implements ClientModInitializer {
	public static ShaderProgram portalProgram = null;

	public static final RenderLayer portalLayer = RenderLayer.of(
			"dimmod$portal",
			VertexFormats.POSITION_COLOR,
			DrawMode.QUADS,
			256,
			false,
			false,
			RenderLayer.MultiPhaseParameters.builder()
			.program(new RenderPhase.ShaderProgram(() -> portalProgram))
			.build(false)
			);

	@Override
	public void onInitializeClient() {

		CoreShaderRegistrationCallback.EVENT.register(
				ctx -> {
					ctx.register(new Identifier(ExampleMod.MOD_ID, "portal"), VertexFormats.POSITION,
							program -> {
								portalProgram = program;
							});
				});

		BlockEntityRendererFactories.register(ExampleMod.PORTAL_BLOCK_ENTITY, PortalBlockEntityRenderer::new);
	}
}