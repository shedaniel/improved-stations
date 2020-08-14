/*
 * Improved Stations by shedaniel.
 * Licensed under the MIT.
 */

package me.shedaniel.istations;

import me.shedaniel.istations.blocks.entities.renderer.CraftingStationBlockEntityRenderer;
import me.shedaniel.istations.containers.CraftingStationScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.minecraft.client.render.RenderLayer;

@Environment(EnvType.CLIENT)
public class ImprovedStationsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ScreenRegistry.register(ImprovedStations.CRAFTING_STATION_TYPE, CraftingStationScreen::new);
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), ImprovedStations.CRAFTING_STATION, ImprovedStations.CRAFTING_STATION_SLAB);
        BlockEntityRendererRegistry.INSTANCE.register(ImprovedStations.CRAFTING_STATION_BLOCK_ENTITY, CraftingStationBlockEntityRenderer::new);
    }
}
