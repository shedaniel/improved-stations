/*
 * Improved Stations by shedaniel.
 * Licensed under the MIT.
 */

package me.shedaniel.istations;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import me.shedaniel.istations.blocks.*;
import me.shedaniel.istations.blocks.entities.CraftingStationBlockEntity;
import me.shedaniel.istations.containers.CraftingStationContainer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.*;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

@Mod.EventBusSubscriber(modid = "improved-stations", bus = Mod.EventBusSubscriber.Bus.MOD)
@Mod("improved-stations")
public class ImprovedStations {
    public static final ResourceLocation CRAFTING_STATION_ID = new ResourceLocation("improved-stations", "crafting_station");
    public static final ResourceLocation CRAFTING_STATION_SLAB_ID = new ResourceLocation("improved-stations", "crafting_station_slab");
    public static final ResourceLocation FURNACE_SLAB_ID = new ResourceLocation("improved-stations", "furnace_slab");
    public static final ResourceLocation SMOKER_SLAB_ID = new ResourceLocation("improved-stations", "smoker_slab");
    public static final ResourceLocation BLAST_FURNACE_SLAB_ID = new ResourceLocation("improved-stations", "blast_furnace_slab");
    public static final ResourceLocation CRAFTING_TABLE_SLAB_ID = new ResourceLocation("improved-stations", "crafting_table_slab");
    public static final ResourceLocation JUKEBOX_SLAB_ID = new ResourceLocation("improved-stations", "jukebox_slab");
    public static final ResourceLocation LOOM_SLAB_ID = new ResourceLocation("improved-stations", "loom_slab");
    public static final ResourceLocation CARTOGRAPHY_TABLE_SLAB_ID = new ResourceLocation("improved-stations", "cartography_table_slab");
    public static final Block CRAFTING_STATION = new CraftingStationBlock(Block.Properties.from(Blocks.CRAFTING_TABLE).notSolid());
    public static final Block CRAFTING_STATION_SLAB = new CraftingStationSlabBlock(Block.Properties.from(Blocks.CRAFTING_TABLE).notSolid());
    public static final Block FURNACE_SLAB = new FurnaceSlabBlock(Block.Properties.from(Blocks.FURNACE).notSolid());
    public static final Block SMOKER_SLAB = new SmokerSlabBlock(Block.Properties.from(Blocks.SMOKER).notSolid());
    public static final Block BLAST_FURNACE_SLAB = new BlastFurnaceSlabBlock(Block.Properties.from(Blocks.BLAST_FURNACE).notSolid());
    public static final Block CRAFTING_TABLE_SLAB = new CraftingTableSlabBlock(Block.Properties.from(Blocks.CRAFTING_TABLE).notSolid());
    public static final Block JUKEBOX_SLAB = new JukeboxSlabBlock(Block.Properties.from(Blocks.JUKEBOX).notSolid());
    public static final Block LOOM_SLAB = new LoomSlabBlock(Block.Properties.from(Blocks.LOOM).notSolid());
    public static final Block CARTOGRAPHY_TABLE_SLAB = new CartographyTableSlabBlock(Block.Properties.from(Blocks.CARTOGRAPHY_TABLE).notSolid());
    public static final TileEntityType<CraftingStationBlockEntity> CRAFTING_STATION_BLOCK_ENTITY = TileEntityType.Builder.create(CraftingStationBlockEntity::new, CRAFTING_STATION, CRAFTING_STATION_SLAB).build(null);
    public static final ContainerType<CraftingStationContainer> CRAFTING_STATION_CONTAINER = IForgeContainerType.create((windowId, inv, data) -> {
        return new CraftingStationContainer(windowId, inv, inv.player.world, data.readBlockPos());
    });
    
    public ImprovedStations() {
        DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> FMLJavaModLoadingContext.get().getModEventBus().register(new ImprovedStationsClient()));
        MinecraftForge.EVENT_BUS.addListener(ImprovedStations::rightClickBlock);
    }
    
    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        event.getRegistry().registerAll(
                CRAFTING_STATION.setRegistryName(CRAFTING_STATION_ID),
                CRAFTING_STATION_SLAB.setRegistryName(CRAFTING_STATION_SLAB_ID),
                FURNACE_SLAB.setRegistryName(FURNACE_SLAB_ID),
                SMOKER_SLAB.setRegistryName(SMOKER_SLAB_ID),
                BLAST_FURNACE_SLAB.setRegistryName(BLAST_FURNACE_SLAB_ID),
                CRAFTING_TABLE_SLAB.setRegistryName(CRAFTING_TABLE_SLAB_ID),
                JUKEBOX_SLAB.setRegistryName(JUKEBOX_SLAB_ID),
                LOOM_SLAB.setRegistryName(LOOM_SLAB_ID),
                CARTOGRAPHY_TABLE_SLAB.setRegistryName(CARTOGRAPHY_TABLE_SLAB_ID)
        );
    }
    
    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(
                new BlockItem(CRAFTING_STATION, new Item.Properties().group(ItemGroup.DECORATIONS)).setRegistryName(CRAFTING_STATION_ID),
                new BlockItem(CRAFTING_STATION_SLAB, new Item.Properties().group(ItemGroup.DECORATIONS)).setRegistryName(CRAFTING_STATION_SLAB_ID),
                new BlockItem(FURNACE_SLAB, new Item.Properties().group(ItemGroup.DECORATIONS)).setRegistryName(FURNACE_SLAB_ID),
                new BlockItem(SMOKER_SLAB, new Item.Properties().group(ItemGroup.DECORATIONS)).setRegistryName(SMOKER_SLAB_ID),
                new BlockItem(BLAST_FURNACE_SLAB, new Item.Properties().group(ItemGroup.DECORATIONS)).setRegistryName(BLAST_FURNACE_SLAB_ID),
                new BlockItem(CRAFTING_TABLE_SLAB, new Item.Properties().group(ItemGroup.DECORATIONS)).setRegistryName(CRAFTING_TABLE_SLAB_ID),
                new BlockItem(JUKEBOX_SLAB, new Item.Properties().group(ItemGroup.DECORATIONS)).setRegistryName(JUKEBOX_SLAB_ID),
                new BlockItem(LOOM_SLAB, new Item.Properties().group(ItemGroup.DECORATIONS)).setRegistryName(LOOM_SLAB_ID),
                new BlockItem(CARTOGRAPHY_TABLE_SLAB, new Item.Properties().group(ItemGroup.DECORATIONS)).setRegistryName(CARTOGRAPHY_TABLE_SLAB_ID)
        );
    }
    
    @SubscribeEvent
    public static void registerContainerTypes(RegistryEvent.Register<ContainerType<?>> event) {
        event.getRegistry().register(
                CRAFTING_STATION_CONTAINER.setRegistryName(CRAFTING_STATION_ID)
        );
    }
    
    @SubscribeEvent
    public static void registerTileEntityTypes(RegistryEvent.Register<TileEntityType<?>> event) {
        event.getRegistry().register(
                CRAFTING_STATION_BLOCK_ENTITY.setRegistryName(CRAFTING_STATION_ID)
        );
        applyMoreBlocks(TileEntityType.FURNACE, ImprovedStations.FURNACE_SLAB);
        applyMoreBlocks(TileEntityType.SMOKER, ImprovedStations.SMOKER_SLAB);
        applyMoreBlocks(TileEntityType.BLAST_FURNACE, ImprovedStations.BLAST_FURNACE_SLAB);
        applyMoreBlocks(TileEntityType.JUKEBOX, ImprovedStations.JUKEBOX_SLAB);
    }
    
    private static void applyMoreBlocks(TileEntityType<?> type, Block... blocks) {
        try {
            Field field = ObfuscationReflectionHelper.findField(TileEntityType.class, "field_223046_I");
            field.setAccessible(true);
            ArrayList<Block> list = Lists.newArrayList((Set<Block>) field.get(type));
            list.addAll(Arrays.asList(blocks));
            Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
            field.set(type, ImmutableSet.copyOf(list));
        } catch (Throwable throwable) {
            throw new RuntimeException(throwable);
        }
    }
    
    public static void rightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        ItemStack itemStack = event.getItemStack();
        if (itemStack.getItem() instanceof MusicDiscItem) {
            World world = event.getWorld();
            BlockPos blockPos = event.getPos();
            BlockState blockState = world.getBlockState(blockPos);
            if (blockState.getBlock() == ImprovedStations.JUKEBOX_SLAB && !blockState.get(JukeboxSlabBlock.HAS_RECORD)) {
                if (!world.isRemote) {
                    ((JukeboxSlabBlock) ImprovedStations.JUKEBOX_SLAB).insertRecord(world, blockPos, blockState, itemStack);
                    world.playEvent(null, 1010, blockPos, Item.getIdFromItem(itemStack.getItem()));
                    itemStack.shrink(1);
                    PlayerEntity playerEntity = event.getPlayer();
                    if (playerEntity != null) {
                        playerEntity.addStat(Stats.PLAY_RECORD);
                    }
                }
                event.setCanceled(true);
                event.setCancellationResult(ActionResultType.SUCCESS);
            }
        }
    }
}
