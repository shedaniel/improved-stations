/*
 * Improved Stations by shedaniel.
 * Licensed under the MIT.
 */

package me.shedaniel.istations.blocks.entities;

import me.shedaniel.istations.ImprovedStations;
import me.shedaniel.istations.containers.CraftingStationMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.StackedContentsCompatible;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class CraftingStationBlockEntity extends BaseContainerBlockEntity implements StackedContentsCompatible {
    protected NonNullList<ItemStack> inventory;
    private boolean dirty = false;
    
    public CraftingStationBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ImprovedStations.CRAFTING_STATION_BLOCK_ENTITY, blockPos, blockState);
        this.inventory = NonNullList.withSize(9, ItemStack.EMPTY);
    }
    
    @Override
    protected Component getDefaultName() {
        return Component.translatable("container.crafting");
    }
    
    @Override
    protected AbstractContainerMenu createMenu(int syncId, Inventory playerInventory) {
        return new CraftingStationMenu(syncId, playerInventory, this, ContainerLevelAccess.NULL);
    }
    
    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        this.inventory = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(tag, this.inventory);
    }
    
    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        ContainerHelper.saveAllItems(tag, this.inventory);
    }
    
    public void tick() {
        if (hasLevel() && !getLevel().isClientSide) {
            if (dirty) {
                dirty = false;
                setChanged();
                ((ServerLevel) getLevel()).getChunkSource().blockChanged(getBlockPos());
            }
        }
    }
    
    public void markDirty() {
        this.dirty = true;
    }
    
    @Override
    public int getContainerSize() {
        return this.inventory.size();
    }
    
    @Override
    public boolean isEmpty() {
        for (ItemStack itemStack : this.inventory) {
            if (!itemStack.isEmpty())
                return false;
        }
        return true;
    }
    
    @Override
    public ItemStack getItem(int slot) {
        return this.inventory.get(slot);
    }
    
    @Override
    public ItemStack removeItem(int slot, int amount) {
        return ContainerHelper.removeItem(this.inventory, slot, amount);
    }
    
    @Override
    public ItemStack removeItemNoUpdate(int slot) {
        return ContainerHelper.takeItem(this.inventory, slot);
    }
    
    @Override
    public void setItem(int slot, ItemStack stack) {
        this.inventory.set(slot, stack);
        if (stack.getCount() > this.getMaxStackSize()) {
            stack.setCount(this.getMaxStackSize());
        }
    }
    
    @Override
    public boolean stillValid(Player player) {
        if (Objects.requireNonNull(this.level).getBlockEntity(this.worldPosition) != this) {
            return false;
        } else {
            return player.distanceToSqr((double) this.worldPosition.getX() + 0.5D, (double) this.worldPosition.getY() + 0.5D, (double) this.worldPosition.getZ() + 0.5D) <= 64.0D;
        }
    }
    
    @Override
    public void clearContent() {
        this.inventory.clear();
    }
    
    @Override
    public void fillStackedContents(StackedContents recipeFinder) {
        for (ItemStack stack : inventory) {
            recipeFinder.accountSimpleStack(stack);
        }
    }
    
    @Override
    public CompoundTag getUpdateTag() {
        return saveWithoutMetadata();
    }
    
    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
}
