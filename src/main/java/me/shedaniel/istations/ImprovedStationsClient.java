/*
 * Improved Stations by shedaniel.
 * Licensed under the MIT.
 */

package me.shedaniel.istations;

import me.shedaniel.istations.blocks.entities.CraftingStationBlockEntity;
import me.shedaniel.istations.blocks.entities.renderer.CraftingStationBlockEntityRenderer;
import me.shedaniel.istations.containers.CraftingStationScreen;
import me.shedaniel.istations.containers.CraftingStationScreenHandler;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.screen.ScreenProviderRegistry;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.util.math.BlockPos;

@Environment(EnvType.CLIENT)
public class ImprovedStationsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ScreenProviderRegistry.INSTANCE.registerFactory(ImprovedStations.CRAFTING_STATION_ID, (syncId, identifier, playerEntity, packetByteBuf) -> {
            BlockPos pos = packetByteBuf.readBlockPos();
            CraftingStationBlockEntity entity = (CraftingStationBlockEntity) playerEntity.getEntityWorld().getBlockEntity(pos);
            return new CraftingStationScreen(new CraftingStationScreenHandler(syncId, playerEntity.inventory, entity, ScreenHandlerContext.EMPTY), playerEntity.inventory, entity.getName());
        });
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), ImprovedStations.CRAFTING_STATION, ImprovedStations.CRAFTING_STATION_SLAB);
        BlockEntityRendererRegistry.INSTANCE.register(ImprovedStations.CRAFTING_STATION_BLOCK_ENTITY, CraftingStationBlockEntityRenderer::new);
    }
}
