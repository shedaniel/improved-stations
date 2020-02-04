/*
 * Improved Stations by shedaniel.
 * Licensed under the MIT.
 */

package me.shedaniel.istations.containers;

import me.shedaniel.istations.ImprovedStations;
import me.shedaniel.istations.blocks.CraftingStationBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftResultInventory;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.IRecipeHelperPopulator;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.CraftingResultSlot;
import net.minecraft.inventory.container.Slot;
import net.minecraft.inventory.container.WorkbenchContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.RecipeItemHelper;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CraftingStationContainer extends Container {
    private final CraftResultInventory resultInv;
    private final IWorldPosCallable context;
    private final CraftingInventory craftingInventory;
    private final PlayerEntity player;
    
    public CraftingStationContainer(int syncId, PlayerInventory playerInventory, World world, BlockPos pos) {
        super(ImprovedStations.CRAFTING_STATION_CONTAINER, syncId);
        this.resultInv = new CraftResultInventory();
        this.player = playerInventory.player;
        IInventory inventory = (IInventory) world.getTileEntity(pos);
        this.craftingInventory = new CraftingInventory(this, 3, 3) {
            @Override
            public int getSizeInventory() {
                return inventory.getSizeInventory();
            }
            
            @Override
            public boolean isEmpty() {
                return inventory.isEmpty();
            }
            
            @Override
            public ItemStack getStackInSlot(int index) {
                return inventory.getStackInSlot(index);
            }
            
            @Override
            public ItemStack removeStackFromSlot(int index) {
                ItemStack stack = inventory.removeStackFromSlot(index);
                onCraftMatrixChanged(this);
                return stack;
            }
            
            @Override
            public ItemStack decrStackSize(int index, int count) {
                ItemStack stack = inventory.decrStackSize(index, count);
                onCraftMatrixChanged(this);
                return stack;
            }
            
            @Override
            public void setInventorySlotContents(int index, ItemStack stack) {
                inventory.setInventorySlotContents(index, stack);
                onCraftMatrixChanged(this);
            }
            
            @Override
            public void markDirty() {
                inventory.markDirty();
            }
            
            @Override
            public boolean isUsableByPlayer(PlayerEntity player) {
                return inventory.isUsableByPlayer(player);
            }
            
            @Override
            public void clear() {
                inventory.clear();
            }
            
            @Override
            public void fillStackedContents(RecipeItemHelper recipeFinder) {
                if (inventory instanceof IRecipeHelperPopulator)
                    ((IRecipeHelperPopulator) inventory).fillStackedContents(recipeFinder);
            }
        };
        this.context = IWorldPosCallable.of(world, pos);
        this.addSlot(new CraftingResultSlot(playerInventory.player, craftingInventory, this.resultInv, 0, 124, 35));
        int m;
        int l;
        for (m = 0; m < 3; ++m) {
            for (l = 0; l < 3; ++l) {
                this.addSlot(new Slot(craftingInventory, l + m * 3, 30 + l * 18, 17 + m * 18));
            }
        }
        
        for (m = 0; m < 3; ++m) {
            for (l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + m * 9 + 9, 8 + l * 18, 84 + m * 18));
            }
        }
        
        for (m = 0; m < 9; ++m) {
            this.addSlot(new Slot(playerInventory, m, 8 + m * 18, 142));
        }
        updateResult(syncId, player.world, player, craftingInventory, resultInv);
    }
    
    protected static void updateResult(int syncId, World world, PlayerEntity player, CraftingInventory craftingInventory, CraftResultInventory resultInventory) {
        DummyWorkbenchContainer.updateResult(syncId, world, player, craftingInventory, resultInventory);
    }
    
    public void populateRecipeFinder(RecipeItemHelper recipeFinder) {
        this.craftingInventory.fillStackedContents(recipeFinder);
    }
    
    public void clearCraftingSlots() {
        this.craftingInventory.clear();
        this.resultInv.clear();
    }
    
    @Override
    public void onCraftMatrixChanged(IInventory inventory) {
        super.onCraftMatrixChanged(inventory);
        updateResult(this.windowId, player.world, this.player, this.craftingInventory, this.resultInv);
    }
    
    @Override
    public boolean canInteractWith(PlayerEntity player) {
        return this.context.applyOrElse((world, blockPos) -> {
            return world.getBlockState(blockPos).getBlock() instanceof CraftingStationBlock && player.getDistanceSq(blockPos.getX() + .5D, blockPos.getY() + .5D, blockPos.getZ() + .5D) < 64D;
        }, true);
    }
    
    @Override
    public ItemStack transferStackInSlot(PlayerEntity player, int invSlot) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(invSlot);
        if (slot != null && slot.getHasStack()) {
            ItemStack itemStack2 = slot.getStack();
            itemStack = itemStack2.copy();
            if (invSlot == 0) {
                this.context.consume((world, blockPos) -> {
                    itemStack2.getItem().onCreated(itemStack2, world, player);
                });
                if (!this.mergeItemStack(itemStack2, 10, 46, true)) {
                    return ItemStack.EMPTY;
                }
                
                slot.onSlotChange(itemStack2, itemStack);
            } else if (invSlot >= 10 && invSlot < 46) {
                if (!this.mergeItemStack(itemStack2, 1, 10, false)) {
                    if (invSlot < 37) {
                        if (!this.mergeItemStack(itemStack2, 37, 46, false)) {
                            return ItemStack.EMPTY;
                        }
                    } else if (!this.mergeItemStack(itemStack2, 10, 37, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            } else if (!this.mergeItemStack(itemStack2, 10, 46, false)) {
                return ItemStack.EMPTY;
            }
            
            if (itemStack2.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }
            
            if (itemStack2.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }
            
            ItemStack itemStack3 = slot.onTake(player, itemStack2);
            if (invSlot == 0) {
                player.dropItem(itemStack3, false);
            }
        }
        
        return itemStack;
    }
    
    @Override
    public boolean canMergeSlot(ItemStack stack, Slot slot) {
        return slot.inventory != this.resultInv && super.canMergeSlot(stack, slot);
    }
    
    private static class DummyWorkbenchContainer extends WorkbenchContainer {
        public DummyWorkbenchContainer(int p_i50089_1_, PlayerInventory p_i50089_2_) {
            super(p_i50089_1_, p_i50089_2_);
        }
        
        private static void updateResult(int syncId, World world, PlayerEntity player, CraftingInventory craftingInventory, CraftResultInventory resultInventory) {
            WorkbenchContainer.func_217066_a(syncId, world, player, craftingInventory, resultInventory);
        }
    }
}
