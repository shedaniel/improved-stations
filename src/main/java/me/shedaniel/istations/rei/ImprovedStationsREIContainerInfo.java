/*
 * Improved Stations by shedaniel.
 * Licensed under the MIT.
 */

package me.shedaniel.istations.rei;

import me.shedaniel.istations.containers.CraftingStationContainer;
import me.shedaniel.rei.server.ContainerInfo;
import me.shedaniel.rei.server.ContainerInfoHandler;
import me.shedaniel.rei.server.RecipeFinder;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class ImprovedStationsREIContainerInfo implements Runnable {
    @Override
    public void run() {
        ContainerInfoHandler.registerContainerInfo(new Identifier("minecraft", "plugins/crafting"), new ContainerInfo<CraftingStationContainer>() {
            @Override
            public Class<? extends CraftingStationContainer> getContainerClass() {
                return CraftingStationContainer.class;
            }
            
            @Override
            public int getCraftingResultSlotIndex(CraftingStationContainer container) {
                return 0;
            }
            
            @Override
            public int getCraftingWidth(CraftingStationContainer container) {
                return 3;
            }
            
            @Override
            public int getCraftingHeight(CraftingStationContainer container) {
                return 3;
            }
            
            @Override
            public void clearCraftingSlots(CraftingStationContainer container) {
                container.clearCraftingSlots();
            }
            
            @Override
            public void populateRecipeFinder(CraftingStationContainer container, RecipeFinder recipeFinder) {
                container.populateRecipeFinder(new net.minecraft.recipe.RecipeFinder() {
                    @Override
                    public void method_20478(ItemStack itemStack, int i) {
                        recipeFinder.addItem(itemStack, i);
                    }
                });
            }
        });
    }
}
