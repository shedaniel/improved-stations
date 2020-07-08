/*
 * Improved Stations by shedaniel.
 * Licensed under the MIT.
 */

package me.shedaniel.istations;

import me.shedaniel.istations.blocks.entities.renderer.CraftingStationBlockEntityRenderer;
import me.shedaniel.istations.containers.CraftingStationScreen;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = "improved-stations", bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ImprovedStationsClient {
    @SubscribeEvent
    public static void setupClient(FMLClientSetupEvent event) {
        ClientRegistry.bindTileEntityRenderer(ImprovedStations.CRAFTING_STATION_BLOCK_ENTITY.get(), CraftingStationBlockEntityRenderer::new);
        ScreenManager.registerFactory(ImprovedStations.CRAFTING_STATION_CONTAINER.get(), CraftingStationScreen::new);
        RenderTypeLookup.setRenderLayer(ImprovedStations.CRAFTING_STATION.get(), RenderType.getCutout());
    }
}
