/*
 * Improved Stations by shedaniel.
 * Licensed under the MIT.
 */

package me.shedaniel.istations.rei;

import me.shedaniel.istations.containers.CraftingStationScreenHandler;
import me.shedaniel.rei.server.ContainerInfo;
import me.shedaniel.rei.server.ContainerInfoHandler;
import me.shedaniel.rei.server.RecipeFinder;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class ImprovedStationsREIContainerInfo implements Runnable {
    @Override
    public void run() {
        ContainerInfoHandler.registerContainerInfo(new Identifier("minecraft", "plugins/crafting"), new ContainerInfo<CraftingStationScreenHandler>() {
            @Override
            public Class<? extends CraftingStationScreenHandler> getContainerClass() {
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
            public void clearCraftingSlots(CraftingStationScreenHandler container) {
                container.clearCraftingSlots();
            }
            
            @Override
            public void populateRecipeFinder(CraftingStationScreenHandler container, RecipeFinder recipeFinder) {
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
