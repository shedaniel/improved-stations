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
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.Arrays;

@Mod.EventBusSubscriber(modid = "improved-stations", bus = Mod.EventBusSubscriber.Bus.MOD)
@Mod("improved-stations")
public class ImprovedStations {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, "improved-stations");
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, "improved-stations");
    public static final DeferredRegister<ContainerType<?>> CONTAINER_TYPES = DeferredRegister.create(ForgeRegistries.CONTAINERS, "improved-stations");
    public static final RegistryObject<Block> CRAFTING_STATION = BLOCKS.register("crafting_station", () -> new CraftingStationBlock(Block.Properties.from(Blocks.CRAFTING_TABLE).notSolid()));
    public static final RegistryObject<Block> CRAFTING_STATION_SLAB = BLOCKS.register("crafting_station_slab", () -> new CraftingStationSlabBlock(Block.Properties.from(Blocks.CRAFTING_TABLE).notSolid()));
    public static final RegistryObject<Block> FURNACE_SLAB = BLOCKS.register("furnace_slab", () -> new FurnaceSlabBlock(Block.Properties.from(Blocks.FURNACE).notSolid()));
    public static final RegistryObject<Block> SMOKER_SLAB = BLOCKS.register("smoker_slab", () -> new SmokerSlabBlock(Block.Properties.from(Blocks.SMOKER).notSolid()));
    public static final RegistryObject<Block> BLAST_FURNACE_SLAB = BLOCKS.register("blast_furnace_slab", () -> new BlastFurnaceSlabBlock(Block.Properties.from(Blocks.BLAST_FURNACE).notSolid()));
    public static final RegistryObject<Block> CRAFTING_TABLE_SLAB = BLOCKS.register("crafting_table_slab", () -> new CraftingTableSlabBlock(Block.Properties.from(Blocks.CRAFTING_TABLE).notSolid()));
    public static final RegistryObject<Block> JUKEBOX_SLAB = BLOCKS.register("jukebox_slab", () -> new JukeboxSlabBlock(Block.Properties.from(Blocks.JUKEBOX).notSolid()));
    public static final RegistryObject<Block> LOOM_SLAB = BLOCKS.register("loom_slab", () -> new LoomSlabBlock(Block.Properties.from(Blocks.LOOM).notSolid()));
    public static final RegistryObject<Block> CARTOGRAPHY_TABLE_SLAB = BLOCKS.register("cartography_table_slab", () -> new CartographyTableSlabBlock(Block.Properties.from(Blocks.CARTOGRAPHY_TABLE).notSolid()));
    public static final RegistryObject<TileEntityType<CraftingStationBlockEntity>> CRAFTING_STATION_BLOCK_ENTITY = TILE_ENTITY_TYPES.register("crafting_station", () -> TileEntityType.Builder.create(CraftingStationBlockEntity::new, CRAFTING_STATION.get(), CRAFTING_STATION_SLAB.get()).build(null));
    public static final RegistryObject<ContainerType<CraftingStationContainer>> CRAFTING_STATION_CONTAINER = CONTAINER_TYPES.register("crafting_station", () -> IForgeContainerType.create((windowId, inv, data) -> {
        return new CraftingStationContainer(windowId, inv, inv.player.world, data.readBlockPos());
    }));
    
    public ImprovedStations() {
        MinecraftForge.EVENT_BUS.addListener(ImprovedStations::rightClickBlock);
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        TILE_ENTITY_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
        CONTAINER_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
    
    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(
                new BlockItem(CRAFTING_STATION.get(), new Item.Properties().group(ItemGroup.DECORATIONS)).setRegistryName(CRAFTING_STATION.get().getRegistryName()),
                new BlockItem(CRAFTING_STATION_SLAB.get(), new Item.Properties().group(ItemGroup.DECORATIONS)).setRegistryName(CRAFTING_STATION_SLAB.get().getRegistryName()),
                new BlockItem(FURNACE_SLAB.get(), new Item.Properties().group(ItemGroup.DECORATIONS)).setRegistryName(FURNACE_SLAB.get().getRegistryName()),
                new BlockItem(SMOKER_SLAB.get(), new Item.Properties().group(ItemGroup.DECORATIONS)).setRegistryName(SMOKER_SLAB.get().getRegistryName()),
                new BlockItem(BLAST_FURNACE_SLAB.get(), new Item.Properties().group(ItemGroup.DECORATIONS)).setRegistryName(BLAST_FURNACE_SLAB.get().getRegistryName()),
                new BlockItem(CRAFTING_TABLE_SLAB.get(), new Item.Properties().group(ItemGroup.DECORATIONS)).setRegistryName(CRAFTING_TABLE_SLAB.get().getRegistryName()),
                new BlockItem(JUKEBOX_SLAB.get(), new Item.Properties().group(ItemGroup.DECORATIONS)).setRegistryName(JUKEBOX_SLAB.get().getRegistryName()),
                new BlockItem(LOOM_SLAB.get(), new Item.Properties().group(ItemGroup.DECORATIONS)).setRegistryName(LOOM_SLAB.get().getRegistryName()),
                new BlockItem(CARTOGRAPHY_TABLE_SLAB.get(), new Item.Properties().group(ItemGroup.DECORATIONS)).setRegistryName(CARTOGRAPHY_TABLE_SLAB.get().getRegistryName())
        );
    }
    
    @SubscribeEvent
    public static void registerTileEntityTypes(RegistryEvent.Register<TileEntityType<?>> event) {
        applyMoreBlocks(TileEntityType.FURNACE, ImprovedStations.FURNACE_SLAB.get());
        applyMoreBlocks(TileEntityType.SMOKER, ImprovedStations.SMOKER_SLAB.get());
        applyMoreBlocks(TileEntityType.BLAST_FURNACE, ImprovedStations.BLAST_FURNACE_SLAB.get());
        applyMoreBlocks(TileEntityType.JUKEBOX, ImprovedStations.JUKEBOX_SLAB.get());
    }
    
    private static void applyMoreBlocks(TileEntityType<?> type, Block... blocks) {
        ArrayList<Block> list = Lists.newArrayList(type.validBlocks);
        list.addAll(Arrays.asList(blocks));
        type.validBlocks = ImmutableSet.copyOf(list);
    }
    
    public static void rightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        ItemStack itemStack = event.getItemStack();
        if (itemStack.getItem() instanceof MusicDiscItem) {
            World world = event.getWorld();
            BlockPos blockPos = event.getPos();
            BlockState blockState = world.getBlockState(blockPos);
            if (blockState.getBlock() == ImprovedStations.JUKEBOX_SLAB.get() && !blockState.get(JukeboxSlabBlock.HAS_RECORD)) {
                if (!world.isRemote) {
                    ((JukeboxSlabBlock) ImprovedStations.JUKEBOX_SLAB.get()).insertRecord(world, blockPos, blockState, itemStack);
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
