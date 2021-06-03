/*
 * Improved Stations by shedaniel.
 * Licensed under the MIT.
 */

package me.shedaniel.istations.rei;

import me.shedaniel.istations.containers.CraftingStationScreenHandler;
import me.shedaniel.rei.api.common.plugins.REIServerPlugin;
import me.shedaniel.rei.api.common.transfer.RecipeFinder;
import me.shedaniel.rei.api.common.transfer.info.MenuInfoRegistry;
import me.shedaniel.rei.api.common.transfer.info.simple.SimpleGridMenuInfo;
import me.shedaniel.rei.plugin.common.BuiltinPlugin;
import net.minecraft.item.ItemStack;

public class ImprovedStationsREIContainerInfo implements REIServerPlugin {
    @Override
    public void registerMenuInfo(MenuInfoRegistry registry) {
        registry.register(BuiltinPlugin.CRAFTING, CraftingStationScreenHandler.class, new SimpleGridMenuInfo<>() {
            @Override
            public int getCraftingResultSlotIndex(CraftingStationScreenHandler menu) {
                return 0;
            }
            
            @Override
            public int getCraftingWidth(CraftingStationScreenHandler menu) {
                return 3;
            }
            
            @Override
            public int getCraftingHeight(CraftingStationScreenHandler menu) {
                return 3;
            }
            
            @Override
            public void populateRecipeFinder(CraftingStationScreenHandler menu, RecipeFinder finder) {
                menu.populateRecipeFinder(new net.minecraft.recipe.RecipeFinder() {
                    @Override
                    public void addInput(ItemStack itemStack, int i) {
                        finder.addItem(itemStack, i);
                    }
                });
            }
        });
    }
}
