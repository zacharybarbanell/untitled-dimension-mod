package com.zacharybarbanell.dimmod;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.block.AbstractBlock.Settings;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExampleMod implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final String MOD_ID = "dimmod";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final Block PORTAL_BLOCK = Registry.register(
			Registries.BLOCK,
			new Identifier(MOD_ID, "portal"),
			new PortalBlock(Settings.create().mapColor(MapColor.BLACK).noCollision().luminance((state) -> {
				return 15;
			}).strength(-1.0F, 3600000.0F).dropsNothing().pistonBehavior(PistonBehavior.BLOCK)));
	public static final BlockEntityType<PortalBlockEntity> PORTAL_BLOCK_ENTITY = Registry.register(
			Registries.BLOCK_ENTITY_TYPE,
			new Identifier(MOD_ID, "portal"),
			FabricBlockEntityTypeBuilder.create(PortalBlockEntity::new, PORTAL_BLOCK).build());

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Hello Fabric world!");
	}
}