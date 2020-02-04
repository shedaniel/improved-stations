/*
 * Improved Stations by shedaniel.
 * Licensed under the MIT.
 */

package me.shedaniel.istations.jei;

import me.shedaniel.istations.ImprovedStations;
import me.shedaniel.istations.containers.CraftingStationContainer;
import me.shedaniel.istations.containers.CraftingStationScreen;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaRecipeCategoryUid;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeTransferRegistration;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

@JeiPlugin
public class ImprovedStationsJEIPlugin implements IModPlugin {
    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(CraftingStationScreen.class, 88, 32, 28, 23, VanillaRecipeCategoryUid.CRAFTING);
    }
    
    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation("improved-stations", "jei_plugin");
    }
    
    @Override
    public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
        registration.addRecipeTransferHandler(CraftingStationContainer.class, VanillaRecipeCategoryUid.CRAFTING, 1, 9, 10, 36);
    }
    
    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(ImprovedStations.CRAFTING_STATION), VanillaRecipeCategoryUid.CRAFTING);
        registration.addRecipeCatalyst(new ItemStack(ImprovedStations.CRAFTING_STATION_SLAB), VanillaRecipeCategoryUid.CRAFTING);
        registration.addRecipeCatalyst(new ItemStack(ImprovedStations.CRAFTING_TABLE_SLAB), VanillaRecipeCategoryUid.CRAFTING);
        registration.addRecipeCatalyst(new ItemStack(ImprovedStations.FURNACE_SLAB), VanillaRecipeCategoryUid.FURNACE, VanillaRecipeCategoryUid.FUEL);
        registration.addRecipeCatalyst(new ItemStack(ImprovedStations.SMOKER_SLAB), VanillaRecipeCategoryUid.FURNACE, VanillaRecipeCategoryUid.FUEL);
        registration.addRecipeCatalyst(new ItemStack(ImprovedStations.BLAST_FURNACE_SLAB), VanillaRecipeCategoryUid.FURNACE, VanillaRecipeCategoryUid.FUEL);
    }
}
