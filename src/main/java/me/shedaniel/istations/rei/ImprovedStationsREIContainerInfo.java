/*
 * Improved Stations by shedaniel.
 * Licensed under the MIT.
 */

package me.shedaniel.istations.rei;

import me.shedaniel.istations.containers.CraftingStationMenu;
import me.shedaniel.rei.api.common.plugins.REIServerPlugin;
import me.shedaniel.rei.api.common.transfer.RecipeFinder;
import me.shedaniel.rei.api.common.transfer.info.MenuInfoContext;
import me.shedaniel.rei.api.common.transfer.info.MenuInfoRegistry;
import me.shedaniel.rei.api.common.transfer.info.simple.SimpleGridMenuInfo;
import me.shedaniel.rei.api.common.transfer.info.simple.SimpleMenuInfoProvider;
import me.shedaniel.rei.plugin.common.BuiltinPlugin;
import me.shedaniel.rei.plugin.common.displays.crafting.DefaultCraftingDisplay;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.item.ItemStack;

public class ImprovedStationsREIContainerInfo implements REIServerPlugin {
    @Override
    public void registerMenuInfo(MenuInfoRegistry registry) {
        registry.register(BuiltinPlugin.CRAFTING, CraftingStationMenu.class, SimpleMenuInfoProvider.of(CraftingStationMenuInfo::new));
    }
    
    public record CraftingStationMenuInfo(DefaultCraftingDisplay<?> display) implements SimpleGridMenuInfo<CraftingStationMenu, DefaultCraftingDisplay<?>> {
        @Override
        public DefaultCraftingDisplay<?> getDisplay() {
            return display;
        }
        
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
        public void populateRecipeFinder(MenuInfoContext<CraftingStationMenu, ?, DefaultCraftingDisplay<?>> context, RecipeFinder finder) {
            context.getMenu().populateRecipeFinder(new StackedContents() {
                @Override
                public void accountStack(ItemStack itemStack, int i) {
                    finder.addItem(itemStack, i);
                }
            });
        }
    }
}
