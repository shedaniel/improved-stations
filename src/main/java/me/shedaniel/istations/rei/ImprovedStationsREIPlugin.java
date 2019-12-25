/*
 * Improved Stations by shedaniel.
 * Licensed under the MIT.
 */

package me.shedaniel.istations.rei;

import me.shedaniel.istations.ImprovedStations;
import me.shedaniel.rei.api.EntryStack;
import me.shedaniel.rei.api.RecipeHelper;
import me.shedaniel.rei.api.plugins.REIPluginV0;
import net.fabricmc.loader.api.SemanticVersion;
import net.fabricmc.loader.util.version.VersionParsingException;
import net.minecraft.util.Identifier;

public class ImprovedStationsREIPlugin implements REIPluginV0 {
    @Override
    public Identifier getPluginIdentifier() {
        return new Identifier("improved-stations", "rei_plugin");
    }
    
    @Override
    public SemanticVersion getMinimumVersion() throws VersionParsingException {
        return SemanticVersion.parse("3.2.29");
    }
    
    @Override
    public void registerOthers(RecipeHelper recipeHelper) {
        recipeHelper.registerWorkingStations(new Identifier("minecraft", "plugins/crafting"), EntryStack.create(ImprovedStations.CRAFTING_STATION), EntryStack.create(ImprovedStations.CRAFTING_STATION_SLAB));
        recipeHelper.registerWorkingStations(new Identifier("minecraft", "plugins/smelting"), EntryStack.create(ImprovedStations.FURNACE_SLAB));
        recipeHelper.registerWorkingStations(new Identifier("minecraft", "plugins/smoking"), EntryStack.create(ImprovedStations.SMOKER_SLAB));
        recipeHelper.registerWorkingStations(new Identifier("minecraft", "plugins/blasting"), EntryStack.create(ImprovedStations.BLAST_FURNACE_SLAB));
    }
}
