/*
 * Improved Stations by shedaniel.
 * Licensed under the MIT.
 */

package me.shedaniel.istations.rei;

import me.shedaniel.istations.containers.CraftingStationScreenHandler;
import me.shedaniel.rei.api.BuiltinPlugin;
import me.shedaniel.rei.server.ContainerInfo;
import me.shedaniel.rei.server.ContainerInfoHandler;
import me.shedaniel.rei.server.RecipeFinder;
import net.minecraft.container.Container;
import net.minecraft.item.ItemStack;

public class ImprovedStationsREIContainerInfo implements Runnable {
    @Override
    public void run() {
        ContainerInfoHandler.registerContainerInfo(BuiltinPlugin.CRAFTING, new ContainerInfo<CraftingStationScreenHandler>() {
            @Override
            public Class<? extends Container> getContainerClass() {
                return CraftingStationScreenHandler.class;
            }
            
            @Override
            public int getCraftingResultSlotIndex(CraftingStationScreenHandler container) {
                return 0;
            }
            
            @Override
            public int getCraftingWidth(CraftingStationScreenHandler container) {
                return 3;
            }
            
            @Override
            public int getCraftingHeight(CraftingStationScreenHandler container) {
                return 3;
            }
            
            @Override
            public void populateRecipeFinder(CraftingStationScreenHandler container, RecipeFinder recipeFinder) {
                container.populateRecipeFinder(new net.minecraft.recipe.RecipeFinder() {
                    @Override
                    public void addInput(ItemStack itemStack, int i) {
                        recipeFinder.addItem(itemStack, i);
                    }
                });
            }
        });
    }
}
