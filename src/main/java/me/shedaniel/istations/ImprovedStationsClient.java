package me.shedaniel.istations;

import me.shedaniel.istations.blocks.entities.CraftingStationBlockEntity;
import me.shedaniel.istations.blocks.entities.renderer.CraftingStationBlockEntityRenderer;
import me.shedaniel.istations.containers.CraftingStationContainer;
import me.shedaniel.istations.containers.CraftingStationScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.screen.ScreenProviderRegistry;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.container.BlockContext;
import net.minecraft.util.math.BlockPos;

public class ImprovedStationsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ScreenProviderRegistry.INSTANCE.registerFactory(ImprovedStations.CRAFTING_STATION_ID, (syncId, identifier, playerEntity, packetByteBuf) -> {
            BlockPos pos = packetByteBuf.readBlockPos();
            CraftingStationBlockEntity entity = (CraftingStationBlockEntity) playerEntity.getEntityWorld().getBlockEntity(pos);
            return new CraftingStationScreen(new CraftingStationContainer(syncId, playerEntity.inventory, entity, BlockContext.EMPTY), playerEntity.inventory, entity.getName());
        });
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), ImprovedStations.CRAFTING_STATION, ImprovedStations.CRAFTING_STATION_SLAB);
        BlockEntityRendererRegistry.INSTANCE.register(ImprovedStations.CRAFTING_STATION_BLOCK_ENTITY, CraftingStationBlockEntityRenderer::new);
    }
}
