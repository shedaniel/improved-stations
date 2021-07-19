/*
 * Improved Stations by shedaniel.
 * Licensed under the MIT.
 */

package me.shedaniel.istations.containers;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiConsumer;

public record ExtendedScreenHandlerFactoryWrapped(
        MenuProvider factory,
        BiConsumer<ServerPlayer, FriendlyByteBuf> dataWriter
) implements ExtendedScreenHandlerFactory {
    @Override
    public void writeScreenOpeningData(ServerPlayer serverPlayerEntity, FriendlyByteBuf packetByteBuf) {
        this.dataWriter.accept(serverPlayerEntity, packetByteBuf);
    }
    
    @Override
    public Component getDisplayName() {
        return factory.getDisplayName();
    }
    
    @Override
    @Nullable
    public AbstractContainerMenu createMenu(int syncId, Inventory inv, Player player) {
        return factory.createMenu(syncId, inv, player);
    }
}
