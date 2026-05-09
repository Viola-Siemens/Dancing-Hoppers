package com.hexagram2021.dancing_hoppers.client.gui;
import com.hexagram2021.dancing_hoppers.DancingHoppers;
import com.hexagram2021.dancing_hoppers.common.inventory.EnhancedClassificationHopperMenu;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
public class EnhancedClassificationHopperScreen extends AbstractContainerScreen<EnhancedClassificationHopperMenu> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(DancingHoppers.MODID, "textures/gui/container/enhanced_classification_hopper.png");
    public EnhancedClassificationHopperScreen(EnhancedClassificationHopperMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.imageWidth = 176;
        this.imageHeight = 166;
        this.inventoryLabelY = this.imageHeight - 109;
    }
    @Override
    protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;
        graphics.blit(TEXTURE, x, y, 0, 0, this.imageWidth, this.imageHeight);
        int startX = 43;
        for (int i = 0; i < 5; i++) {
            if (this.menu.getSlot(i).getItem().isEmpty()) {
                graphics.blit(TEXTURE, x + startX + i * 18, y + 20, this.imageWidth, 0, 16, 16);
            }
        }
    }
    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(graphics);
        super.render(graphics, mouseX, mouseY, partialTick);
        this.renderTooltip(graphics, mouseX, mouseY);
    }
    @Override
    protected void renderLabels(GuiGraphics graphics, int mouseX, int mouseY) {
        graphics.drawString(this.font, this.title, 8, 6, 4210752, false);
        graphics.drawString(this.font, this.playerInventoryTitle, 8, this.inventoryLabelY, 4210752, false);
    }
}