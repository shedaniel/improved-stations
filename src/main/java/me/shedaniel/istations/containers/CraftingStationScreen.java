/*
 * Improved Stations by shedaniel.
 * Licensed under the MIT.
 */

package me.shedaniel.istations.containers;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.ingame.ContainerScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class CraftingStationScreen extends ContainerScreen<CraftingStationScreenHandler> {
    
    private static final Identifier BG_TEX = new Identifier("textures/gui/container/crafting_table.png");
    
    public CraftingStationScreen(CraftingStationScreenHandler container, PlayerInventory inventory, Text title) {
        super(container, inventory, title);
    }
    
    @Override
    protected void drawForeground(MatrixStack stack, int mouseX, int mouseY) {
        this.textRenderer.draw(stack, this.title, 28.0F, 6.0F, 4210752);
        this.textRenderer.draw(stack, this.playerInventory.getDisplayName(), 8.0F, (float) (this.containerHeight - 96 + 2), 4210752);
    }
    
    @Override
    public void render(MatrixStack stack, int mouseX, int mouseY, float delta) {
        this.renderBackground(stack);
        super.render(stack, mouseX, mouseY, delta);
        this.drawMouseoverTooltip(stack, mouseX, mouseY);
    }
    
    @Override
    protected void drawBackground(MatrixStack stack, float delta, int mouseX, int mouseY) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.client.getTextureManager().bindTexture(BG_TEX);
        int i = this.x;
        int j = (this.height - this.containerHeight) / 2;
        this.drawTexture(stack, i, j, 0, 0, this.containerWidth, this.containerHeight);
    }
    
}
