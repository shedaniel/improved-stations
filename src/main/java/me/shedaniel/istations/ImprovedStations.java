package me.shedaniel.istations;

import me.shedaniel.istations.blocks.*;
import me.shedaniel.istations.blocks.entities.CraftingStationBlockEntity;
import me.shedaniel.istations.containers.CraftingStationContainer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.container.BlockContext;
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
    public static final Block CRAFTING_STATION = new CraftingStationBlock(FabricBlockSettings.copy(Blocks.CRAFTING_TABLE).nonOpaque().build());
    public static final Block CRAFTING_STATION_SLAB = new CraftingStationSlabBlock(FabricBlockSettings.copy(Blocks.CRAFTING_TABLE).nonOpaque().build());
    public static final Block FURNACE_SLAB = new FurnaceSlabBlock(FabricBlockSettings.copy(Blocks.FURNACE).nonOpaque().build());
    public static final Block SMOKER_SLAB = new SmokerSlabBlock(FabricBlockSettings.copy(Blocks.SMOKER).nonOpaque().build());
    public static final Block BLAST_FURNACE_SLAB = new BlastFurnaceSlabBlock(FabricBlockSettings.copy(Blocks.BLAST_FURNACE).nonOpaque().build());
    public static final BlockEntityType<CraftingStationBlockEntity> CRAFTING_STATION_BLOCK_ENTITY = BlockEntityType.Builder.create(CraftingStationBlockEntity::new, CRAFTING_STATION, CRAFTING_STATION_SLAB).build(null);
    
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
        
        Registry.register(Registry.BLOCK_ENTITY, CRAFTING_STATION_ID, CRAFTING_STATION_BLOCK_ENTITY);
        ContainerProviderRegistry.INSTANCE.registerFactory(CRAFTING_STATION_ID, (syncId, identifier, playerEntity, packetByteBuf) -> {
            BlockPos pos = packetByteBuf.readBlockPos();
            return new CraftingStationContainer(syncId, playerEntity.inventory, (CraftingStationBlockEntity) playerEntity.world.getBlockEntity(pos), BlockContext.create(playerEntity.world, pos));
        });
    }
}
