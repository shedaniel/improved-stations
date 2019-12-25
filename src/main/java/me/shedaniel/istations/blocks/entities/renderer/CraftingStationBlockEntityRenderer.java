/*
 * Improved Stations by shedaniel.
 * Licensed under the MIT.
 */

package me.shedaniel.istations.blocks.entities.renderer;

import me.shedaniel.istations.blocks.CraftingStationSlabBlock;
import me.shedaniel.istations.blocks.entities.CraftingStationBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.enums.SlabType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Direction;

public class CraftingStationBlockEntityRenderer extends BlockEntityRenderer<CraftingStationBlockEntity> {
    public CraftingStationBlockEntityRenderer(BlockEntityRenderDispatcher dispatcher) {
        super(dispatcher);
    }
    
    @Override
    public void render(CraftingStationBlockEntity blockEntity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        int lightAbove = WorldRenderer.getLightmapCoordinates(blockEntity.getWorld(), blockEntity.getPos().up());
        for (int x = 0; x < 3; x++)
            for (int y = 0; y < 3; y++) {
                int slotId = x + y * 3;
                BlockState state = blockEntity.getWorld().getBlockState(blockEntity.getPos());
                Direction o = state.get(HorizontalFacingBlock.FACING);
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
                
                ItemStack stack = blockEntity.getInvStack(slotId);
                if (stack.isEmpty())
                    continue;
                BakedModel bakedModel = MinecraftClient.getInstance().getItemRenderer().getHeldItemModel(stack, null, null);
                matrices.push();
                if (state.getBlock() instanceof CraftingStationSlabBlock && state.get(SlabBlock.TYPE) == SlabType.BOTTOM) {
                    matrices.translate(5 / 16d + (newX + 1) * 3 / 16d, .5d - .5 / 16d, 5 / 16d + (newY + 1) * 3 / 16d);
                } else {
                    matrices.translate(5 / 16d + (newX + 1) * 3 / 16d, 1d - .5 / 16d, 5 / 16d + (newY + 1) * 3 / 16d);
                }
                if (!bakedModel.hasDepthInGui()) {
                    matrices.translate(0, .55 / 16d, -.5d / 16d);
                    matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(90));
                    matrices.scale(.3f, .3f, .3f);
                } else {
                    matrices.scale(.5f, .5f, .5f);
                }
                MinecraftClient.getInstance().getItemRenderer().renderItem(stack, ModelTransformation.Type.GROUND, false, matrices, vertexConsumers, lightAbove, OverlayTexture.DEFAULT_UV, bakedModel);
                matrices.pop();
            }
    }
}
