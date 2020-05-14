/*
 * Improved Stations by shedaniel.
 * Licensed under the MIT.
 */

package me.shedaniel.istations.mixin;

import me.shedaniel.istations.ImprovedStations;
import net.minecraft.container.BlockContext;
import net.minecraft.container.CartographyTableContainer;
import net.minecraft.container.Container;
import net.minecraft.container.ContainerType;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CartographyTableContainer.class)
public abstract class MixinCartographyTableContainer extends Container {
    @Shadow
    @Final
    private BlockContext context;
    
    public MixinCartographyTableContainer(ContainerType<?> containerType, int i) {
        super(containerType, i);
    }
    
    @Inject(method = "canUse", at = @At("RETURN"), cancellable = true)
    private void canUse(PlayerEntity player, CallbackInfoReturnable<Boolean> callback) {
        if (!callback.getReturnValue()) callback.setReturnValue(canUse(this.context, player, ImprovedStations.CARTOGRAPHY_TABLE_SLAB));
    }
}
