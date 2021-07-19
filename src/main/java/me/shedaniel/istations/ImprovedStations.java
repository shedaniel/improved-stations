/*
 * Improved Stations by shedaniel.
 * Licensed under the MIT.
 */

package me.shedaniel.istations;

import me.shedaniel.istations.blocks.*;
import me.shedaniel.istations.blocks.entities.CraftingStationBlockEntity;
import me.shedaniel.istations.containers.CraftingStationMenu;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class ImprovedStations implements ModInitializer {
    public static final ResourceLocation CRAFTING_STATION_ID = new ResourceLocation("improved-stations", "crafting_station");
    public static final ResourceLocation CRAFTING_STATION_SLAB_ID = new ResourceLocation("improved-stations", "crafting_station_slab");
    public static final ResourceLocation FURNACE_SLAB_ID = new ResourceLocation("improved-stations", "furnace_slab");
    public static final ResourceLocation SMOKER_SLAB_ID = new ResourceLocation("improved-stations", "smoker_slab");
    public static final ResourceLocation BLAST_FURNACE_SLAB_ID = new ResourceLocation("improved-stations", "blast_furnace_slab");
    public static final ResourceLocation CRAFTING_TABLE_SLAB_ID = new ResourceLocation("improved-stations", "crafting_table_slab");
    public static final ResourceLocation JUKEBOX_SLAB_ID = new ResourceLocation("improved-stations", "jukebox_slab");
    public static final ResourceLocation LOOM_SLAB_ID = new ResourceLocation("improved-stations", "loom_slab");
    public static final ResourceLocation CARTOGRAPHY_TABLE_SLAB_ID = new ResourceLocation("improved-stations", "cartography_table_slab");
    public static final Block CRAFTING_STATION = new CraftingStationBlock(FabricBlockSettings.copy(Blocks.CRAFTING_TABLE).noOcclusion());
    public static final Block CRAFTING_STATION_SLAB = new CraftingStationSlabBlock(FabricBlockSettings.copy(Blocks.CRAFTING_TABLE).noOcclusion());
    public static final Block FURNACE_SLAB = new FurnaceSlabBlock(FabricBlockSettings.copy(Blocks.FURNACE).noOcclusion());
    public static final Block SMOKER_SLAB = new SmokerSlabBlock(FabricBlockSettings.copy(Blocks.SMOKER).noOcclusion());
    public static final Block BLAST_FURNACE_SLAB = new BlastFurnaceSlabBlock(FabricBlockSettings.copy(Blocks.BLAST_FURNACE).noOcclusion());
    public static final Block CRAFTING_TABLE_SLAB = new CraftingTableSlabBlock(FabricBlockSettings.copy(Blocks.CRAFTING_TABLE).noOcclusion());
    public static final Block JUKEBOX_SLAB = new JukeboxSlabBlock(FabricBlockSettings.copy(Blocks.JUKEBOX).noOcclusion());
    public static final Block LOOM_SLAB = new LoomSlabBlock(FabricBlockSettings.copy(Blocks.LOOM).noOcclusion());
    public static final Block CARTOGRAPHY_TABLE_SLAB = new CartographyTableSlabBlock(FabricBlockSettings.copy(Blocks.CARTOGRAPHY_TABLE).noOcclusion());
    public static final BlockEntityType<CraftingStationBlockEntity> CRAFTING_STATION_BLOCK_ENTITY = FabricBlockEntityTypeBuilder.create(CraftingStationBlockEntity::new, CRAFTING_STATION, CRAFTING_STATION_SLAB).build(null);
    public static final MenuType<CraftingStationMenu> CRAFTING_STATION_TYPE = ScreenHandlerRegistry.registerExtended(CRAFTING_STATION_ID, (syncId, playerInventory, buf) -> {
        BlockPos pos = buf.readBlockPos();
        return new CraftingStationMenu(syncId, playerInventory, (CraftingStationBlockEntity) playerInventory.player.level.getBlockEntity(pos), ContainerLevelAccess.create(playerInventory.player.level, pos));
    });
    
    @Override
    public void onInitialize() {
        Registry.register(Registry.BLOCK, CRAFTING_STATION_ID, CRAFTING_STATION);
        Registry.register(Registry.ITEM, CRAFTING_STATION_ID, new BlockItem(CRAFTING_STATION, new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));
        
        Registry.register(Registry.BLOCK, CRAFTING_STATION_SLAB_ID, CRAFTING_STATION_SLAB);
        Registry.register(Registry.ITEM, CRAFTING_STATION_SLAB_ID, new BlockItem(CRAFTING_STATION_SLAB, new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));
        
        Registry.register(Registry.BLOCK, FURNACE_SLAB_ID, FURNACE_SLAB);
        Registry.register(Registry.ITEM, FURNACE_SLAB_ID, new BlockItem(FURNACE_SLAB, new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));
        
        Registry.register(Registry.BLOCK, SMOKER_SLAB_ID, SMOKER_SLAB);
        Registry.register(Registry.ITEM, SMOKER_SLAB_ID, new BlockItem(SMOKER_SLAB, new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));
        
        Registry.register(Registry.BLOCK, BLAST_FURNACE_SLAB_ID, BLAST_FURNACE_SLAB);
        Registry.register(Registry.ITEM, BLAST_FURNACE_SLAB_ID, new BlockItem(BLAST_FURNACE_SLAB, new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));
        
        Registry.register(Registry.BLOCK, CRAFTING_TABLE_SLAB_ID, CRAFTING_TABLE_SLAB);
        Registry.register(Registry.ITEM, CRAFTING_TABLE_SLAB_ID, new BlockItem(CRAFTING_TABLE_SLAB, new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));
        
        Registry.register(Registry.BLOCK, JUKEBOX_SLAB_ID, JUKEBOX_SLAB);
        Registry.register(Registry.ITEM, JUKEBOX_SLAB_ID, new BlockItem(JUKEBOX_SLAB, new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));
        
        Registry.register(Registry.BLOCK, LOOM_SLAB_ID, LOOM_SLAB);
        Registry.register(Registry.ITEM, LOOM_SLAB_ID, new BlockItem(LOOM_SLAB, new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));
        
        Registry.register(Registry.BLOCK, CARTOGRAPHY_TABLE_SLAB_ID, CARTOGRAPHY_TABLE_SLAB);
        Registry.register(Registry.ITEM, CARTOGRAPHY_TABLE_SLAB_ID, new BlockItem(CARTOGRAPHY_TABLE_SLAB, new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));
        
        Registry.register(Registry.BLOCK_ENTITY_TYPE, CRAFTING_STATION_ID, CRAFTING_STATION_BLOCK_ENTITY);
    }
}
