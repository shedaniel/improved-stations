/*
 * Improved Stations by shedaniel.
 * Licensed under the MIT.
 */

package me.shedaniel.istations.mixin;

import me.shedaniel.istations.ImprovedStations;
import me.shedaniel.istations.blocks.JukeboxSlabBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.JukeboxBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.MusicDiscItem;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MusicDiscItem.class)
public class MixinMusicDiscItem {
    @Inject(method = "useOnBlock", at = @At("RETURN"), cancellable = true)
    public void useOnBlock(ItemUsageContext context, CallbackInfoReturnable<ActionResult> callback) {
        if (callback.getReturnValue().equals(ActionResult.PASS)) {
            World world = context.getWorld();
            BlockPos blockPos = context.getBlockPos();
            BlockState blockState = world.getBlockState(blockPos);
            if (blockState.getBlock() == ImprovedStations.JUKEBOX_SLAB && !blockState.get(JukeboxSlabBlock.HAS_RECORD)) {
                ItemStack itemStack = context.getStack();
                if (!world.isClient) {
                    ((JukeboxSlabBlock) ImprovedStations.JUKEBOX_SLAB).setRecord(world, blockPos, blockState, itemStack);
                    world.playLevelEvent(null, 1010, blockPos, Item.getRawId((MusicDiscItem) (Object) this));
                    itemStack.decrement(1);
                    PlayerEntity playerEntity = context.getPlayer();
                    if (playerEntity != null) {
                        playerEntity.incrementStat(Stats.PLAY_RECORD);
                    }
                }
                callback.setReturnValue(ActionResult.SUCCESS);
            }
        }
    }
}
