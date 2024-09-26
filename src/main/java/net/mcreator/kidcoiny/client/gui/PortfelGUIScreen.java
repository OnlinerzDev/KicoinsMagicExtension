package net.mcreator.kidcoiny.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.mcreator.kidcoiny.world.inventory.PortfelGUIMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class PortfelGUIScreen extends AbstractContainerScreen<PortfelGUIMenu> {
   // Hier die Textur des GUI-Screens
   private static final ResourceLocation TEXTURE = new ResourceLocation("kidcoiny", "textures/gui/portfel_gui.png");

   public PortfelGUIScreen(PortfelGUIMenu menu, Inventory playerInventory, Component title) {
      super(menu, playerInventory, title);
      this.imageWidth = 176; // Breite des GUI
      this.imageHeight = 166; // Höhe des GUI
   }

   @Override
   protected void renderBg(PoseStack poseStack, float partialTicks, int mouseX, int mouseY) {
      // Hintergrund des GUI rendern
      RenderSystem.setShaderTexture(0, TEXTURE);
      int x = (this.width - this.imageWidth) / 2;
      int y = (this.height - this.imageHeight) / 2;
      this.blit(poseStack, x, y, 0, 0, this.imageWidth, this.imageHeight);
   }

   @Override
   public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
      this.renderBackground(poseStack); // Hintergrund rendern
      super.render(poseStack, mouseX, mouseY, partialTicks); // Super-Methode für weitere Render-Operationen aufrufen
      this.renderTooltip(poseStack, mouseX, mouseY); // Tooltip rendern
   }

   @Override
   protected void renderLabels(PoseStack poseStack, int mouseX, int mouseY) {
      // Hier kannst du Text oder andere Labels in deinem GUI hinzufügen
      this.font.draw(poseStack, this.title, this.titleLabelX, this.titleLabelY, 4210752);
      this.font.draw(poseStack, this.playerInventoryTitle, this.inventoryLabelX, this.inventoryLabelY, 4210752);
   }

   @Override
   public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
      if (keyCode == 256) { // Escape key closes the GUI
         this.minecraft.player.closeContainer();
         return true;
      }
      return super.keyPressed(keyCode, scanCode, modifiers);
   }
}
