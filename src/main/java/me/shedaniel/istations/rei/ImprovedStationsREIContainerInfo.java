/*
 * Improved Stations by shedaniel.
 * Licensed under the MIT.
 */

package me.shedaniel.istations.rei;

import me.shedaniel.istations.containers.CraftingStationMenu;
import me.shedaniel.rei.api.common.plugins.REIServerPlugin;
import me.shedaniel.rei.api.common.transfer.RecipeFinder;
import me.shedaniel.rei.api.common.transfer.info.MenuInfoRegistry;
import me.shedaniel.rei.api.common.transfer.info.simple.SimpleGridMenuInfo;
import me.shedaniel.rei.plugin.common.BuiltinPlugin;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.item.ItemStack;

public class ImprovedStationsREIContainerInfo implements REIServerPlugin {
    @Override
    public void registerMenuInfo(MenuInfoRegistry registry) {
        registry.register(BuiltinPlugin.CRAFTING, CraftingStationMenu.class, new SimpleGridMenuInfo<>() {
            @Override
            public int getCraftingResultSlotIndex(CraftingStationMenu menu) {
                return 0;
            }
            
            @Override
            public int getCraftingWidth(CraftingStationMenu menu) {
                return 3;
            }
            
            @Override
            public int getCraftingHeight(CraftingStationMenu menu) {
                return 3;
            }
            
            @Override
            public void populateRecipeFinder(CraftingStationMenu menu, RecipeFinder finder) {
                menu.populateRecipeFinder(new StackedContents() {
                    @Override
                    public void accountStack(ItemStack itemStack, int i) {
                        finder.addItem(itemStack, i);
                    }
                });
            }
        });
    }
}
