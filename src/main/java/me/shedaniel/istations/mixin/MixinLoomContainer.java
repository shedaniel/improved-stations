/*
 * Improved Stations by shedaniel.
 * Licensed under the MIT.
 */

package me.shedaniel.istations.mixin;

import me.shedaniel.istations.ImprovedStations;
import net.minecraft.container.*;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LoomContainer.class)
public abstract class MixinLoomContainer extends Container {
    @Shadow
    @Final
    private BlockContext context;

    public MixinLoomContainer(ContainerType<?> containerType, int i) {
        super(containerType, i);
    }

    @Inject(method = "canUse", at = @At("RETURN"), cancellable = true)
    private void canUse(PlayerEntity player, CallbackInfoReturnable<Boolean> callback) {
        if (!callback.getReturnValue()) callback.setReturnValue(canUse(this.context, player, ImprovedStations.LOOM_SLAB));
    }
}
