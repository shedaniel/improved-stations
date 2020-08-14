/*
 * Improved Stations by shedaniel.
 * Licensed under the MIT.
 */

package me.shedaniel.istations.containers;

import me.shedaniel.istations.ImprovedStations;
import me.shedaniel.istations.blocks.CraftingStationBlock;
import me.shedaniel.istations.blocks.entities.CraftingStationBlockEntity;
import net.minecraft.container.BlockContext;
import net.minecraft.container.Container;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.s2c.play.ContainerSlotUpdateS2CPacket;
import net.minecraft.recipe.CraftingRecipe;
import net.minecraft.recipe.RecipeFinder;
import net.minecraft.recipe.RecipeType;
import net.minecraft.screen.slot.CraftingResultSlot;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;

import java.util.Objects;
import java.util.Optional;

public class CraftingStationScreenHandler extends Container {
    private final CraftingResultInventory resultInv;
    private final BlockContext context;
    private final CraftingStationBlockEntity entity;
    private final CraftingInventory craftingInventory;
    private final PlayerEntity player;
    
    public CraftingStationScreenHandler(int syncId, PlayerInventory playerInventory, CraftingStationBlockEntity entity, BlockContext blockContext) {
        super(ImprovedStations.CRAFTING_STATION_TYPE, syncId);
        this.resultInv = new CraftingResultInventory();
        this.player = playerInventory.player;
        this.craftingInventory = new CraftingInventory(this, 3, 3) {
            @Override
            public int getInvSize() {
                return entity.getInvSize();
            }
            
            @Override
            public boolean isInvEmpty() {
                return entity.isInvEmpty();
            }
            
            @Override
            public ItemStack getInvStack(int slot) {
                return entity.getInvStack(slot);
            }
            
            @Override
            public ItemStack removeInvStack(int slot) {
                return entity.removeInvStack(slot);
            }
            
            @Override
            public ItemStack takeInvStack(int slot, int amount) {
                ItemStack stack = entity.takeInvStack(slot, amount);
                if (!stack.isEmpty()) {
                    onContentChanged(craftingInventory);
                }
                return stack;
            }
            
            @Override
            public void setInvStack(int slot, ItemStack stack) {
                entity.setInvStack(slot, stack);
                onContentChanged(craftingInventory);
            }
            
            @Override
            public void markDirty() {
                entity.markDirty();
            }
            
            @Override
            public boolean canPlayerUseInv(PlayerEntity player) {
                return entity.canPlayerUseInv(player);
            }
            
            @Override
            public void clear() {
                entity.clear();
            }
            
            @Override
            public void provideRecipeInputs(RecipeFinder recipeFinder) {
                entity.provideRecipeInputs(recipeFinder);
            }
        };
        this.context = blockContext;
        this.entity = entity;
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
    }
    
    @Override
    public void sendContentUpdates() {
        updateResult(syncId, player.world, player, craftingInventory, resultInv);
        super.sendContentUpdates();
    }
    
    protected void updateResult(int syncId, World world, PlayerEntity player, CraftingInventory craftingInventory, CraftingResultInventory resultInventory) {
        if (!world.isClient) {
            ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity) player;
            ItemStack itemStack = ItemStack.EMPTY;
            Optional<CraftingRecipe> optional = Objects.requireNonNull(world.getServer()).getRecipeManager().getFirstMatch(RecipeType.CRAFTING, craftingInventory, world);
            if (optional.isPresent()) {
                CraftingRecipe craftingRecipe = optional.get();
                if (resultInventory.shouldCraftRecipe(world, serverPlayerEntity, craftingRecipe)) {
                    itemStack = craftingRecipe.craft(craftingInventory);
                }
            }
            
            resultInventory.setInvStack(0, itemStack);
            serverPlayerEntity.networkHandler.sendPacket(new ContainerSlotUpdateS2CPacket(syncId, 0, itemStack));
        }
    }
    
    public void populateRecipeFinder(RecipeFinder recipeFinder) {
        this.craftingInventory.provideRecipeInputs(recipeFinder);
    }
    
    public void clearCraftingSlots() {
        this.craftingInventory.clear();
        this.resultInv.clear();
    }
    
    @Override
    public void onContentChanged(Inventory inventory) {
        super.onContentChanged(inventory);
        sendContentUpdates();
        if (!player.world.isClient) {
            entity.markDirty();
        }
    }
    
    @Override
    public boolean canUse(PlayerEntity player) {
        return this.context.run((world, blockPos) -> world.getBlockState(blockPos).getBlock() instanceof CraftingStationBlock && player.squaredDistanceTo(blockPos.getX() + .5D, blockPos.getY() + .5D, blockPos.getZ() + .5D) < 64D, true);
    }
    
    @Override
    public ItemStack transferSlot(PlayerEntity player, int invSlot) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if (slot != null && slot.hasStack()) {
            ItemStack itemStack2 = slot.getStack();
            itemStack = itemStack2.copy();
            if (invSlot == 0) {
                this.context.run((world, blockPos) -> {
                    itemStack2.getItem().onCraft(itemStack2, world, player);
                });
                if (!this.insertItem(itemStack2, 10, 46, true)) {
                    return ItemStack.EMPTY;
                }
                
                slot.onStackChanged(itemStack2, itemStack);
            } else if (invSlot >= 10 && invSlot < 46) {
                if (!this.insertItem(itemStack2, 1, 10, false)) {
                    if (invSlot < 37) {
                        if (!this.insertItem(itemStack2, 37, 46, false)) {
                            return ItemStack.EMPTY;
                        }
                    } else if (!this.insertItem(itemStack2, 10, 37, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            } else if (!this.insertItem(itemStack2, 10, 46, false)) {
                return ItemStack.EMPTY;
            }
            
            if (itemStack2.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
            
            if (itemStack2.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }
            
            ItemStack itemStack3 = slot.onTakeItem(player, itemStack2);
            if (invSlot == 0) {
                player.dropItem(itemStack3, false);
            }
        }
        
        return itemStack;
    }
    
    @Override
    public boolean canInsertIntoSlot(ItemStack stack, Slot slot) {
        return slot.inventory != this.resultInv && super.canInsertIntoSlot(stack, slot);
    }
}
