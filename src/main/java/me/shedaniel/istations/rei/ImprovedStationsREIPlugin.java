/*
 * Improved Stations by shedaniel.
 * Licensed under the MIT.
 */

package me.shedaniel.istations.rei;

import me.shedaniel.istations.ImprovedStations;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.common.util.EntryStacks;
import me.shedaniel.rei.plugin.common.BuiltinPlugin;

public class ImprovedStationsREIPlugin implements REIClientPlugin {
    @Override
    public void registerCategories(CategoryRegistry registry) {
        registry.addWorkstations(BuiltinPlugin.CRAFTING, EntryStacks.of(ImprovedStations.CRAFTING_STATION), EntryStacks.of(ImprovedStations.CRAFTING_STATION_SLAB), EntryStacks.of(ImprovedStations.CRAFTING_TABLE_SLAB));
        registry.addWorkstations(BuiltinPlugin.SMELTING, EntryStacks.of(ImprovedStations.FURNACE_SLAB));
        registry.addWorkstations(BuiltinPlugin.SMOKING, EntryStacks.of(ImprovedStations.SMOKER_SLAB));
        registry.addWorkstations(BuiltinPlugin.BLASTING, EntryStacks.of(ImprovedStations.BLAST_FURNACE_SLAB));
    }
}
