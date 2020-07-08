/*
 * Improved Stations by shedaniel.
 * Licensed under the MIT.
 */

package me.shedaniel.istations.blocks.entities;

import me.shedaniel.istations.ImprovedStations;
import me.shedaniel.istations.containers.CraftingStationContainer;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IRecipeHelperPopulator;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.RecipeItemHelper;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.LockableTileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;
import java.util.Iterator;

public class CraftingStationBlockEntity extends LockableTileEntity implements IRecipeHelperPopulator {
    
    protected Inventory inventory;
    
    public CraftingStationBlockEntity() {
        super(ImprovedStations.CRAFTING_STATION_BLOCK_ENTITY.get());
        this.inventory = new Inventory(9);
    }
    
    @Override
    protected ITextComponent getDefaultName() {
        return new TranslationTextComponent("container.crafting");
    }
    
    @Override
    protected Container createMenu(int id, PlayerInventory player) {
        return null;
    }
    
    @Nullable
    @Override
    public Container createMenu(int id, PlayerInventory inventory, PlayerEntity playerEntity) {
        return this.canOpen(playerEntity) ? new CraftingStationContainer(id, inventory, playerEntity.world, getPos()) : null;
    }
    
    @Override
    public void read(BlockState state, CompoundNBT tag) {
        super.read(state, tag);
        this.inventory = new Inventory(9);
        this.inventory.deserializeNBT(tag.getCompound("inv"));
    }
    
    @Override
    public CompoundNBT write(CompoundNBT tag) {
        super.write(tag);
        tag.put("inv", this.inventory.serializeNBT());
        return tag;
    }
    
    @Override
    public int getSizeInventory() {
        return this.inventory.getSlots();
    }
    
    @Override
    public boolean isEmpty() {
        Iterator<ItemStack> var1 = this.inventory.getStacks().iterator();
        ItemStack itemStack;
        do {
            if (!var1.hasNext())
                return true;
            itemStack = var1.next();
        } while (itemStack.isEmpty());
        return false;
    }
    
    @Override
    public ItemStack getStackInSlot(int slot) {
        return this.inventory.getStackInSlot(slot);
    }
    
    @Override
    public ItemStack decrStackSize(int slot, int amount) {
        return ItemStackHelper.getAndSplit(this.inventory.getStacks(), slot, amount);
    }
    
    @Override
    public ItemStack removeStackFromSlot(int slot) {
        return ItemStackHelper.getAndRemove(this.inventory.getStacks(), slot);
    }
    
    @Override
    public void setInventorySlotContents(int slot, ItemStack stack) {
        this.inventory.setStackInSlot(slot, stack);
        if (stack.getCount() > this.getInventoryStackLimit()) {
            stack.setCount(this.getInventoryStackLimit());
        }
    }
    
    @Override
    public boolean isUsableByPlayer(PlayerEntity player) {
        if (this.world.getTileEntity(this.pos) != this) {
            return false;
        } else {
            return player.getDistanceSq((double) this.pos.getX() + 0.5D, (double) this.pos.getY() + 0.5D, (double) this.pos.getZ() + 0.5D) <= 64.0D;
        }
    }
    
    @Override
    public void clear() {
        this.inventory.getStacks().clear();
    }
    
    public void fillStackedContents(RecipeItemHelper helper) {
        for (ItemStack stack : inventory.getStacks()) {
            helper.accountStack(stack);
        }
    }
    
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(getPos(), 1, getUpdateTag());
    }
    
    @Override
    public CompoundNBT getUpdateTag() {
        return write(new CompoundNBT());
    }
    
    @Override
    public void handleUpdateTag(BlockState state, CompoundNBT tag) {
        read(state, tag);
    }
    
    @Override
    public void onDataPacket(NetworkManager manager, SUpdateTileEntityPacket packet) {
        handleUpdateTag(getBlockState(), packet.getNbtCompound());
    }
    
    private static class Inventory extends ItemStackHandler {
        public Inventory() {
        }
        
        public Inventory(int size) {
            super(size);
        }
        
        public Inventory(NonNullList<ItemStack> stacks) {
            super(stacks);
        }
        
        public NonNullList<ItemStack> getStacks() {
            return stacks;
        }
    }
}
