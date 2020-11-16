/*
 * Improved Stations by shedaniel.
 * Licensed under the MIT.
 */

package me.shedaniel.istations.blocks.entities.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import me.shedaniel.istations.blocks.CraftingStationSlabBlock;
import me.shedaniel.istations.blocks.entities.CraftingStationBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.state.properties.SlabType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.vector.Vector3f;

import java.util.Objects;

public class CraftingStationBlockEntityRenderer extends TileEntityRenderer<CraftingStationBlockEntity> {
    public CraftingStationBlockEntityRenderer(TileEntityRendererDispatcher dispatcher) {
        super(dispatcher);
    }
    
    @Override
    public void render(CraftingStationBlockEntity blockEntity, float tickDelta, MatrixStack matrices, IRenderTypeBuffer vertexConsumers, int light, int overlay) {
        try {
            int lightAbove = WorldRenderer.getCombinedLight(Objects.requireNonNull(blockEntity.getWorld()), blockEntity.getPos().up());
            blockEntity = (CraftingStationBlockEntity) blockEntity.getWorld().getTileEntity(blockEntity.getPos());
            BlockState state = blockEntity.getWorld().getBlockState(blockEntity.getPos());
            Direction o = state.get(HorizontalBlock.HORIZONTAL_FACING);
            SlabType slabType = (state.getBlock() instanceof CraftingStationSlabBlock && state.hasProperty(SlabBlock.TYPE)) ? state.get(SlabBlock.TYPE) : SlabType.DOUBLE;
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
                    
                    ItemStack stack = blockEntity.getStackInSlot(slotId);
                    if (stack.isEmpty())
                        continue;
                    IBakedModel bakedModel = Minecraft.getInstance().getItemRenderer().getItemModelWithOverrides(stack, null, null);
                    matrices.push();
                    if (slabType == SlabType.BOTTOM) {
                        matrices.translate(5 / 16d + (newX + 1) * 3 / 16d, .5d - .5 / 16d, 5 / 16d + (newY + 1) * 3 / 16d);
                    } else {
                        matrices.translate(5 / 16d + (newX + 1) * 3 / 16d, 1d - .5 / 16d, 5 / 16d + (newY + 1) * 3 / 16d);
                    }
                    if (!bakedModel.isGui3d()) {
                        matrices.translate(0, .55 / 16d, -.5d / 16d);
                        matrices.rotate(Vector3f.XP.rotationDegrees(90));
                        matrices.scale(.3f, .3f, .3f);
                    } else {
                        matrices.scale(.5f, .5f, .5f);
                    }
                    Minecraft.getInstance().getItemRenderer().renderItem(stack, ItemCameraTransforms.TransformType.GROUND, false, matrices, vertexConsumers, lightAbove, OverlayTexture.NO_OVERLAY, bakedModel);
                    matrices.pop();
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
