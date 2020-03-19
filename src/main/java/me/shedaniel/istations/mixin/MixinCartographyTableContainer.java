/*
 * Improved Stations by shedaniel.
 * Licensed under the MIT.
 */

package me.shedaniel.istations.mixin;

import me.shedaniel.istations.ImprovedStations;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.CartographyTableScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CartographyTableScreenHandler.class)
public abstract class MixinCartographyTableContainer extends ScreenHandler {
    @Shadow
    @Final
    private ScreenHandlerContext context;
    
    public MixinCartographyTableContainer(ScreenHandlerType<?> containerType, int i) {
        super(containerType, i);
    }
    
    @Inject(method = "canUse", at = @At("RETURN"), cancellable = true)
    private void canUse(PlayerEntity player, CallbackInfoReturnable<Boolean> callback) {
        if (!callback.getReturnValue()) callback.setReturnValue(canUse(this.context, player, ImprovedStations.CARTOGRAPHY_TABLE_SLAB));
    }
}
