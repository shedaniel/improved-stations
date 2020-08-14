/*
 * Improved Stations by shedaniel.
 * Licensed under the MIT.
 */

package me.shedaniel.istations;

import me.shedaniel.istations.blocks.*;
import me.shedaniel.istations.blocks.entities.CraftingStationBlockEntity;
import me.shedaniel.istations.containers.CraftingStationScreenHandler;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.container.BlockContext;
import net.minecraft.container.ContainerType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;

public class ImprovedStations implements ModInitializer {
    public static final Identifier CRAFTING_STATION_ID = new Identifier("improved-stations", "crafting_station");
    public static final Identifier CRAFTING_STATION_SLAB_ID = new Identifier("improved-stations", "crafting_station_slab");
    public static final Identifier FURNACE_SLAB_ID = new Identifier("improved-stations", "furnace_slab");
    public static final Identifier SMOKER_SLAB_ID = new Identifier("improved-stations", "smoker_slab");
    public static final Identifier BLAST_FURNACE_SLAB_ID = new Identifier("improved-stations", "blast_furnace_slab");
    public static final Identifier CRAFTING_TABLE_SLAB_ID = new Identifier("improved-stations", "crafting_table_slab");
    public static final Identifier JUKEBOX_SLAB_ID = new Identifier("improved-stations", "jukebox_slab");
    public static final Identifier LOOM_SLAB_ID = new Identifier("improved-stations", "loom_slab");
    public static final Identifier CARTOGRAPHY_TABLE_SLAB_ID = new Identifier("improved-stations", "cartography_table_slab");
    public static final Block CRAFTING_STATION = new CraftingStationBlock(FabricBlockSettings.copy(Blocks.CRAFTING_TABLE).nonOpaque());
    public static final Block CRAFTING_STATION_SLAB = new CraftingStationSlabBlock(FabricBlockSettings.copy(Blocks.CRAFTING_TABLE).nonOpaque());
    public static final Block FURNACE_SLAB = new FurnaceSlabBlock(FabricBlockSettings.copy(Blocks.FURNACE).nonOpaque());
    public static final Block SMOKER_SLAB = new SmokerSlabBlock(FabricBlockSettings.copy(Blocks.SMOKER).nonOpaque());
    public static final Block BLAST_FURNACE_SLAB = new BlastFurnaceSlabBlock(FabricBlockSettings.copy(Blocks.BLAST_FURNACE).nonOpaque());
    public static final Block CRAFTING_TABLE_SLAB = new CraftingTableSlabBlock(FabricBlockSettings.copy(Blocks.CRAFTING_TABLE).nonOpaque());
    public static final Block JUKEBOX_SLAB = new JukeboxSlabBlock(FabricBlockSettings.copy(Blocks.JUKEBOX).nonOpaque());
    public static final Block LOOM_SLAB = new LoomSlabBlock(FabricBlockSettings.copy(Blocks.LOOM).nonOpaque());
    public static final Block CARTOGRAPHY_TABLE_SLAB = new CartographyTableSlabBlock(FabricBlockSettings.copy(Blocks.CARTOGRAPHY_TABLE).nonOpaque());
    public static final BlockEntityType<CraftingStationBlockEntity> CRAFTING_STATION_BLOCK_ENTITY = BlockEntityType.Builder.create(CraftingStationBlockEntity::new, CRAFTING_STATION, CRAFTING_STATION_SLAB).build(null);
    public static final ContainerType<CraftingStationScreenHandler> CRAFTING_STATION_TYPE = ScreenHandlerRegistry.registerExtended(CRAFTING_STATION_ID, (syncId, playerInventory, buf) -> {
        BlockPos pos = buf.readBlockPos();
        return new CraftingStationScreenHandler(syncId, playerInventory, (CraftingStationBlockEntity) playerInventory.player.world.getBlockEntity(pos), BlockContext.create(playerInventory.player.world, pos));
    });
    
    @Override
    public void onInitialize() {
        Registry.register(Registry.BLOCK, CRAFTING_STATION_ID, CRAFTING_STATION);
        Registry.register(Registry.ITEM, CRAFTING_STATION_ID, new BlockItem(CRAFTING_STATION, new Item.Settings().group(ItemGroup.DECORATIONS)));
        
        Registry.register(Registry.BLOCK, CRAFTING_STATION_SLAB_ID, CRAFTING_STATION_SLAB);
        Registry.register(Registry.ITEM, CRAFTING_STATION_SLAB_ID, new BlockItem(CRAFTING_STATION_SLAB, new Item.Settings().group(ItemGroup.DECORATIONS)));
        
        Registry.register(Registry.BLOCK, FURNACE_SLAB_ID, FURNACE_SLAB);
        Registry.register(Registry.ITEM, FURNACE_SLAB_ID, new BlockItem(FURNACE_SLAB, new Item.Settings().group(ItemGroup.DECORATIONS)));
        
        Registry.register(Registry.BLOCK, SMOKER_SLAB_ID, SMOKER_SLAB);
        Registry.register(Registry.ITEM, SMOKER_SLAB_ID, new BlockItem(SMOKER_SLAB, new Item.Settings().group(ItemGroup.DECORATIONS)));
        
        Registry.register(Registry.BLOCK, BLAST_FURNACE_SLAB_ID, BLAST_FURNACE_SLAB);
        Registry.register(Registry.ITEM, BLAST_FURNACE_SLAB_ID, new BlockItem(BLAST_FURNACE_SLAB, new Item.Settings().group(ItemGroup.DECORATIONS)));
        
        Registry.register(Registry.BLOCK, CRAFTING_TABLE_SLAB_ID, CRAFTING_TABLE_SLAB);
        Registry.register(Registry.ITEM, CRAFTING_TABLE_SLAB_ID, new BlockItem(CRAFTING_TABLE_SLAB, new Item.Settings().group(ItemGroup.DECORATIONS)));
        
        Registry.register(Registry.BLOCK, JUKEBOX_SLAB_ID, JUKEBOX_SLAB);
        Registry.register(Registry.ITEM, JUKEBOX_SLAB_ID, new BlockItem(JUKEBOX_SLAB, new Item.Settings().group(ItemGroup.DECORATIONS)));
        
        Registry.register(Registry.BLOCK, LOOM_SLAB_ID, LOOM_SLAB);
        Registry.register(Registry.ITEM, LOOM_SLAB_ID, new BlockItem(LOOM_SLAB, new Item.Settings().group(ItemGroup.DECORATIONS)));
        
        Registry.register(Registry.BLOCK, CARTOGRAPHY_TABLE_SLAB_ID, CARTOGRAPHY_TABLE_SLAB);
        Registry.register(Registry.ITEM, CARTOGRAPHY_TABLE_SLAB_ID, new BlockItem(CARTOGRAPHY_TABLE_SLAB, new Item.Settings().group(ItemGroup.DECORATIONS)));
        
        Registry.register(Registry.BLOCK_ENTITY_TYPE, CRAFTING_STATION_ID, CRAFTING_STATION_BLOCK_ENTITY);
    }
}
