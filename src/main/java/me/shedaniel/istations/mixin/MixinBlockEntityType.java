/*
 * Improved Stations by shedaniel.
 * Licensed under the MIT.
 */

package me.shedaniel.istations.mixin;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import me.shedaniel.istations.ImprovedStations;
import me.shedaniel.istations.hooks.BlockEntityTypeHooks;
import net.minecraft.block.Block;
import net.minecraft.block.entity.*;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.Set;

@Mixin(BlockEntityType.class)
public class MixinBlockEntityType implements BlockEntityTypeHooks {
    @Shadow @Final public static BlockEntityType<FurnaceBlockEntity> FURNACE;
    @Shadow @Final public static BlockEntityType<SmokerBlockEntity> SMOKER;
    @Shadow @Final public static BlockEntityType<BlastFurnaceBlockEntity> BLAST_FURNACE;
    @Shadow @Final public static BlockEntityType<JukeboxBlockEntity> JUKEBOX;
    @Shadow @Mutable @Final private Set<Block> blocks;
    
    @Inject(method = "<clinit>", at = @At("RETURN"))
    private static void afterStaticInit(CallbackInfo info) {
        ((BlockEntityTypeHooks) FURNACE).istations_applyMoreBlocks(ImprovedStations.FURNACE_SLAB);
        ((BlockEntityTypeHooks) SMOKER).istations_applyMoreBlocks(ImprovedStations.SMOKER_SLAB);
        ((BlockEntityTypeHooks) BLAST_FURNACE).istations_applyMoreBlocks(ImprovedStations.BLAST_FURNACE_SLAB);
        ((BlockEntityTypeHooks) JUKEBOX).istations_applyMoreBlocks(ImprovedStations.JUKEBOX_SLAB);
    }
    
    @Override
    public void istations_applyMoreBlocks(Block... blocks) {
        ArrayList<Block> list = Lists.newArrayList(this.blocks);
        for (Block block : blocks) {
            list.add(block);
        }
        this.blocks = ImmutableSet.copyOf(list);
    }
}
