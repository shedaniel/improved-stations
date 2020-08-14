/*
 * Improved Stations by shedaniel.
 * Licensed under the MIT.
 */

package me.shedaniel.istations.containers;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.container.Container;
import net.minecraft.container.NameableContainerFactory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiConsumer;

public class ExtendedScreenHandlerFactoryWrapped implements ExtendedScreenHandlerFactory {
    private final NameableContainerFactory factory;
    private final BiConsumer<ServerPlayerEntity, PacketByteBuf> dataWriter;
    
    public ExtendedScreenHandlerFactoryWrapped(NameableContainerFactory factory, BiConsumer<ServerPlayerEntity, PacketByteBuf> dataWriter) {
        this.factory = factory;
        this.dataWriter = dataWriter;
    }
    
    @Override
    public void writeScreenOpeningData(ServerPlayerEntity serverPlayerEntity, PacketByteBuf packetByteBuf) {
        this.dataWriter.accept(serverPlayerEntity, packetByteBuf);
    }
    
    @Override
    public Text getDisplayName() {
        return factory.getDisplayName();
    }
    
    @Override
    public @Nullable Container createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return factory.createMenu(syncId, inv, player);
    }
}
