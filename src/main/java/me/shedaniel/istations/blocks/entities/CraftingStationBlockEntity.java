/*
 * Improved Stations by shedaniel.
 * Licensed under the MIT.
 */

package me.shedaniel.istations.blocks.entities;

import me.shedaniel.istations.ImprovedStations;
import me.shedaniel.istations.containers.CraftingStationScreenHandler;
import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.LockableContainerBlockEntity;
import net.minecraft.container.BlockContext;
import net.minecraft.container.Container;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.recipe.RecipeFinder;
import net.minecraft.recipe.RecipeInputProvider;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;

import java.util.Objects;

public class CraftingStationBlockEntity extends LockableContainerBlockEntity implements RecipeInputProvider, BlockEntityClientSerializable {
    
    protected DefaultedList<ItemStack> inventory;
    
    public CraftingStationBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ImprovedStations.CRAFTING_STATION_BLOCK_ENTITY, blockPos, blockState);
        this.inventory = DefaultedList.ofSize(9, ItemStack.EMPTY);
    }
    
    @Override
    protected Text getContainerName() {
        return new TranslatableText("container.crafting");
    }
    
    @Override
    protected Container createContainer(int syncId, PlayerInventory playerInventory) {
        return new CraftingStationScreenHandler(syncId, playerInventory, this, BlockContext.EMPTY);
    }
    
    @Override
    public void fromTag(CompoundTag tag) {
        super.fromTag(tag);
        this.inventory = DefaultedList.ofSize(this.getInvSize(), ItemStack.EMPTY);
        Inventories.fromTag(tag, this.inventory);
    }
    
    @Override
    public CompoundTag toTag(CompoundTag tag) {
        super.toTag(tag);
        Inventories.writeNbt(tag, this.inventory);
        return tag;
    }
    
    @Override
    public int getInvSize() {
        return this.inventory.size();
    }
    
    @Override
    public boolean isInvEmpty() {
        for (ItemStack itemStack : this.inventory) {
            if (!itemStack.isEmpty())
                return false;
        }
        return true;
    }
    
    @Override
    public ItemStack getInvStack(int slot) {
        return this.inventory.get(slot);
    }
    
    @Override
    public ItemStack takeInvStack(int slot, int amount) {
        return Inventories.splitStack(this.inventory, slot, amount);
    }
    
    @Override
    public ItemStack removeInvStack(int slot) {
        return Inventories.removeStack(this.inventory, slot);
    }
    
    @Override
    public void setInvStack(int slot, ItemStack stack) {
        this.inventory.set(slot, stack);
        if (stack.getCount() > this.getInvMaxStackAmount()) {
            stack.setCount(this.getInvMaxStackAmount());
        }
    }
    
    @Override
    public boolean canPlayerUseInv(PlayerEntity player) {
        if (Objects.requireNonNull(this.world).getBlockEntity(this.pos) != this) {
            return false;
        } else {
            return player.squaredDistanceTo((double) this.pos.getX() + 0.5D, (double) this.pos.getY() + 0.5D, (double) this.pos.getZ() + 0.5D) <= 64.0D;
        }
    }
    
    @Override
    public void clear() {
        this.inventory.clear();
    }
    
    @Override
    public void provideRecipeInputs(RecipeFinder recipeFinder) {
        for (ItemStack stack : inventory) {
            recipeFinder.addItem(stack);
        }
    }
    
    @Override
    public void fromClientTag(CompoundTag compoundTag) {
        fromTag(compoundTag);
    }
    
    @Override
    public CompoundTag toClientTag(CompoundTag compoundTag) {
        return toTag(compoundTag);
    }
}
