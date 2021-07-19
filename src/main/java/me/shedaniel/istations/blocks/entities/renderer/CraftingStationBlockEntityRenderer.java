/*
 * Improved Stations by shedaniel.
 * Licensed under the MIT.
 */

package me.shedaniel.istations.blocks.entities.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import me.shedaniel.istations.blocks.CraftingStationSlabBlock;
import me.shedaniel.istations.blocks.entities.CraftingStationBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.SlabType;

import java.util.Objects;

public class CraftingStationBlockEntityRenderer implements BlockEntityRenderer<CraftingStationBlockEntity> {
    public CraftingStationBlockEntityRenderer(BlockEntityRendererProvider.Context dispatcher) {
    }
    
    @Override
    public void render(CraftingStationBlockEntity blockEntity, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
        try {
            int lightAbove = LevelRenderer.getLightColor(Objects.requireNonNull(blockEntity.getLevel()), blockEntity.getBlockPos().above());
            BlockState state = blockEntity.getLevel().getBlockState(blockEntity.getBlockPos());
            Direction o = state.getValue(HorizontalDirectionalBlock.FACING);
            SlabType slabType = (state.getBlock() instanceof CraftingStationSlabBlock && state.hasProperty(SlabBlock.TYPE)) ? state.getValue(SlabBlock.TYPE) : SlabType.DOUBLE;
            for (int x = 0; x < 3; x++)
                for (int y = 0; y < 3; y++) {
                    int slotId = x + y * 3;
                    int newX = x - 1;
                    int newY = y - 1;
                    
                    if (o == Direction.NORTH) {
                        newX *= -1;
                        newY *= -1;
                    } else if (o == Direction.EAST) {
                        int tmp = newY;
                        newY = newX;
                        newX = tmp;
                        newY *= -1;
                    } else if (o == Direction.WEST) {
                        int tmp = newY;
                        newY = newX;
                        newX = tmp;
                        newX *= -1;
                    }
                    
                    ItemStack stack = blockEntity.getItem(slotId);
                    if (stack.isEmpty())
                        continue;
                    BakedModel bakedModel = Minecraft.getInstance().getItemRenderer().getModel(stack, null, null, 0);
                    matrices.pushPose();
                    if (slabType == SlabType.BOTTOM) {
                        matrices.translate(5 / 16d + (newX + 1) * 3 / 16d, .5d - .5 / 16d, 5 / 16d + (newY + 1) * 3 / 16d);
                    } else {
                        matrices.translate(5 / 16d + (newX + 1) * 3 / 16d, 1d - .5 / 16d, 5 / 16d + (newY + 1) * 3 / 16d);
                    }
                    if (!bakedModel.isGui3d()) {
                        matrices.translate(0, .55 / 16d, -.5d / 16d);
                        matrices.mulPose(Vector3f.XP.rotationDegrees(90));
                        matrices.scale(.3f, .3f, .3f);
                    } else {
                        matrices.scale(.5f, .5f, .5f);
                    }
                    Minecraft.getInstance().getItemRenderer().render(stack, ItemTransforms.TransformType.GROUND, false, matrices, vertexConsumers, lightAbove, OverlayTexture.NO_OVERLAY, bakedModel);
                    matrices.popPose();
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
