/*
 * Improved Stations by shedaniel.
 * Licensed under the MIT.
 */

package me.shedaniel.istations.containers;

import me.shedaniel.istations.ImprovedStations;
import me.shedaniel.istations.blocks.CraftingStationBlock;
import me.shedaniel.istations.blocks.entities.CraftingStationBlockEntity;
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

import java.util.Objects;
import java.util.Optional;

public class CraftingStationMenu extends AbstractContainerMenu {
    private final ResultContainer resultInv;
    private final ContainerLevelAccess access;
    private final CraftingStationBlockEntity entity;
    private final CraftingContainer craftingInventory;
    private final Player player;
    
    public CraftingStationMenu(int syncId, Inventory playerInventory, CraftingStationBlockEntity entity, ContainerLevelAccess access) {
        super(ImprovedStations.CRAFTING_STATION_TYPE, syncId);
        this.resultInv = new ResultContainer();
        this.player = playerInventory.player;
        this.craftingInventory = new CraftingContainer(this, 3, 3) {
            @Override
            public int getContainerSize() {
                return entity.getContainerSize();
            }
            
            @Override
            public boolean isEmpty() {
                return entity.isEmpty();
            }
            
            @Override
            public ItemStack getItem(int slot) {
                return entity.getItem(slot);
            }
            
            @Override
            public ItemStack removeItemNoUpdate(int slot) {
                return entity.removeItemNoUpdate(slot);
            }
            
            @Override
            public ItemStack removeItem(int slot, int amount) {
                ItemStack stack = entity.removeItem(slot, amount);
                if (!stack.isEmpty()) {
                    slotsChanged(craftingInventory);
                }
                return stack;
            }
            
            @Override
            public void setItem(int slot, ItemStack stack) {
                entity.setItem(slot, stack);
                slotsChanged(craftingInventory);
            }
            
            @Override
            public void setChanged() {
                entity.markDirty();
            }
            
            @Override
            public boolean stillValid(Player player) {
                return entity.stillValid(player);
            }
            
            @Override
            public void clearContent() {
                entity.clearContent();
            }
            
            @Override
            public void fillStackedContents(StackedContents recipeFinder) {
                entity.fillStackedContents(recipeFinder);
            }
        };
        this.access = access;
        this.entity = entity;
        this.addSlot(new ResultSlot(playerInventory.player, craftingInventory, this.resultInv, 0, 124, 35));
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
    public void broadcastChanges() {
        updateResult(this, player.level, player, craftingInventory, resultInv);
        super.broadcastChanges();
    }
    
    protected void updateResult(AbstractContainerMenu menu, Level level, Player player, CraftingContainer craftingInventory, ResultContainer resultInventory) {
        if (!level.isClientSide) {
            ServerPlayer serverPlayerEntity = (ServerPlayer) player;
            ItemStack itemStack = ItemStack.EMPTY;
            Optional<CraftingRecipe> optional = Objects.requireNonNull(level.getServer()).getRecipeManager().getRecipeFor(RecipeType.CRAFTING, craftingInventory, level);
            if (optional.isPresent()) {
                CraftingRecipe craftingRecipe = optional.get();
                if (resultInventory.setRecipeUsed(level, serverPlayerEntity, craftingRecipe)) {
                    itemStack = craftingRecipe.assemble(craftingInventory);
                }
            }
            
            resultInventory.setItem(0, itemStack);
            menu.setRemoteSlot(0, itemStack);
            serverPlayerEntity.connection.send(new ClientboundContainerSetSlotPacket(menu.containerId, menu.incrementStateId(), 0, itemStack));
        }
    }
    
    public void populateRecipeFinder(StackedContents recipeFinder) {
        this.craftingInventory.fillStackedContents(recipeFinder);
    }
    
    public void clearCraftingSlots() {
        this.craftingInventory.clearContent();
        this.resultInv.clearContent();
    }
    
    @Override
    public void slotsChanged(Container inventory) {
        super.slotsChanged(inventory);
        broadcastChanges();
        if (!player.level.isClientSide) {
            entity.markDirty();
        }
    }
    
    @Override
    public boolean stillValid(Player player) {
        return this.access.evaluate((world, blockPos) -> world.getBlockState(blockPos).getBlock() instanceof CraftingStationBlock && player.distanceToSqr(blockPos.getX() + .5D, blockPos.getY() + .5D, blockPos.getZ() + .5D) < 64D, true);
    }
    
    @Override
    public ItemStack quickMoveStack(Player player, int invSlot) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if (slot != null && slot.hasItem()) {
            ItemStack itemStack2 = slot.getItem();
            itemStack = itemStack2.copy();
            if (invSlot == 0) {
                this.access.execute((world, blockPos) -> {
                    itemStack2.getItem().onCraftedBy(itemStack2, world, player);
                });
                if (!this.moveItemStackTo(itemStack2, 10, 46, true)) {
                    return ItemStack.EMPTY;
                }
                
                slot.onQuickCraft(itemStack2, itemStack);
            } else if (invSlot >= 10 && invSlot < 46) {
                if (!this.moveItemStackTo(itemStack2, 1, 10, false)) {
                    if (invSlot < 37) {
                        if (!this.moveItemStackTo(itemStack2, 37, 46, false)) {
                            return ItemStack.EMPTY;
                        }
                    } else if (!this.moveItemStackTo(itemStack2, 10, 37, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            } else if (!this.moveItemStackTo(itemStack2, 10, 46, false)) {
                return ItemStack.EMPTY;
            }
            
            if (itemStack2.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
            
            if (itemStack2.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }
            
            slot.onTake(player, itemStack2);
            if (invSlot == 0) {
                player.drop(itemStack2, false);
            }
        }
        
        return itemStack;
    }
    
    @Override
    public boolean canTakeItemForPickAll(ItemStack stack, Slot slot) {
        return slot.container != this.resultInv && super.canTakeItemForPickAll(stack, slot);
    }
}
