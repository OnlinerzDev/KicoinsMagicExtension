package net.mcreator.kidcoiny.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.HashMap;
import net.mcreator.kidcoiny.KidcoinyMod;
import net.mcreator.kidcoiny.network.KasaGuiButtonMessage;
import net.mcreator.kidcoiny.world.inventory.KasaGuiMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class KasaGuiScreen extends AbstractContainerScreen<KasaGuiMenu> {
   private static HashMap<String, Object> guistate;
   private final Level world;
   private final int x;
   private final int y;
   private final int z;
   private final Player entity;
   Button button_kup;
   Button button_kup1;
   Button button_kup2;
   Button button_kup3;
   Button button_kup4;
   private static ResourceLocation texture;

   public KasaGuiScreen(KasaGuiMenu container, Inventory inventory, Component text) {
      super(container, inventory, text);
      this.world = container.world;
      this.x = container.x;
      this.y = container.y;
      this.z = container.z;
      this.entity = container.entity;
      this.imageWidth = 176;
      this.imageHeight = 166;
   }

   @Override
   public void render(PoseStack ms, int mouseX, int mouseY, float partialTicks) {
      this.renderBackground(ms);
      super.render(ms, mouseX, mouseY, partialTicks);
      this.renderTooltip(ms, mouseX, mouseY);
   }

   @Override
   protected void renderBg(PoseStack ms, float partialTicks, int gx, int gy) {
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      RenderSystem.setShaderTexture(0, texture);
      blit(ms, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);

      // Custom GUI elements
      RenderSystem.setShaderTexture(0, new ResourceLocation("kidcoiny:textures/screens/tlo3.png"));
      blit(ms, this.leftPos - 34, this.topPos + 23, 0, 0, 242, 136);

      RenderSystem.setShaderTexture(0, new ResourceLocation("kidcoiny:textures/screens/arrow.png"));
      blit(ms, this.leftPos + 50, this.topPos + 24, 0, 0, 32, 32, 32, 32);
      blit(ms, this.leftPos + 50, this.topPos + 75, 0, 0, 32, 32, 32, 32);
      blit(ms, this.leftPos + 50, this.topPos + 50, 0, 0, 32, 32, 32, 32);
      blit(ms, this.leftPos + 50, this.topPos + 100, 0, 0, 32, 32, 32, 32);
      blit(ms, this.leftPos + 50, this.topPos + 124, 0, 0, 32, 32, 32, 32);

      RenderSystem.setShaderTexture(0, new ResourceLocation("kidcoiny:textures/screens/honey16.png"));
      blit(ms, this.leftPos + 21, this.topPos + 30, 0, 0, 24, 24, 24, 24);

      RenderSystem.setShaderTexture(0, new ResourceLocation("kidcoiny:textures/screens/axolotl.png"));
      blit(ms, this.leftPos + 21, this.topPos + 54, 0, 0, 24, 24, 24, 24);

      RenderSystem.setShaderTexture(0, new ResourceLocation("kidcoiny:textures/screens/kidcoins_ramka.png"));
      blit(ms, this.leftPos + 88, this.topPos + 30, 0, 0, 24, 24, 24, 24);

      RenderSystem.setShaderTexture(0, new ResourceLocation("kidcoiny:textures/screens/10kidcoin5.png"));
      blit(ms, this.leftPos + 88, this.topPos + 55, 0, 0, 24, 24, 24, 24);

      RenderSystem.setShaderTexture(0, new ResourceLocation("kidcoiny:textures/screens/kidcoins_ramka_srebny_1.png"));
      blit(ms, this.leftPos + 88, this.topPos + 104, 0, 0, 24, 24, 24, 24);

      RenderSystem.setShaderTexture(0, new ResourceLocation("kidcoiny:textures/screens/kidcoins_ramka_srebny_1.png"));
      blit(ms, this.leftPos + 88, this.topPos + 79, 0, 0, 24, 24, 24, 24);

      RenderSystem.setShaderTexture(0, new ResourceLocation("kidcoiny:textures/screens/kidcoins_ramka_srebny_1.png"));
      blit(ms, this.leftPos + 88, this.topPos + 129, 0, 0, 24, 24, 24, 24);

      RenderSystem.setShaderTexture(0, new ResourceLocation("kidcoiny:textures/screens/slime5.png"));
      blit(ms, this.leftPos + 21, this.topPos + 104, 0, 0, 24, 24, 24, 24);

      RenderSystem.setShaderTexture(0, new ResourceLocation("kidcoiny:textures/screens/coal64.png"));
      blit(ms, this.leftPos + 21, this.topPos + 79, 0, 0, 24, 24, 24, 24);

      RenderSystem.setShaderTexture(0, new ResourceLocation("kidcoiny:textures/screens/greendye.png"));
      blit(ms, this.leftPos + 21, this.topPos + 129, 0, 0, 24, 24, 24, 24);

      RenderSystem.disableBlend();
   }

   @Override
   public boolean keyPressed(int key, int b, int c) {
      if (key == 256) {
         this.minecraft.player.closeContainer();
         return true;
      } else {
         return super.keyPressed(key, b, c);
      }
   }

   @Override
   public void containerTick() {
      super.containerTick();
   }

   @Override
   protected void renderLabels(PoseStack poseStack, int mouseX, int mouseY) {
      this.font.draw(poseStack, Component.translatable("gui.kidcoiny.kasa_gui.label_kasa_fiskalna_kidcoins"), 30.0F, 12.0F, -12829636);
      this.font.draw(poseStack, Component.translatable("gui.kidcoiny.kasa_gui.label_empty"), -125.0F, -37.0F, -12829636);
   }

   @Override
   public void init() {
      super.init();
      this.minecraft.mouseHandler.releaseMouse(); // Freigeben des Mauszeigers

      // Buttons
      this.button_kup = new Button(this.leftPos + 116, this.topPos + 31, 34, 20, Component.translatable("gui.kidcoiny.kasa_gui.button_kup"), (e) -> {
         KidcoinyMod.PACKET_HANDLER.sendToServer(new KasaGuiButtonMessage(0, this.x, this.y, this.z));
         KasaGuiButtonMessage.handleButtonAction(this.entity, 0, this.x, this.y, this.z);
      });
      guistate.put("button:button_kup", this.button_kup);
      this.addRenderableWidget(this.button_kup);

      this.button_kup1 = new Button(this.leftPos + 116, this.topPos + 56, 34, 20, Component.translatable("gui.kidcoiny.kasa_gui.button_kup1"), (e) -> {
         KidcoinyMod.PACKET_HANDLER.sendToServer(new KasaGuiButtonMessage(1, this.x, this.y, this.z));
         KasaGuiButtonMessage.handleButtonAction(this.entity, 1, this.x, this.y, this.z);
      });
      guistate.put("button:button_kup1", this.button_kup1);
      this.addRenderableWidget(this.button_kup1);

      this.button_kup2 = new Button(this.leftPos + 116, this.topPos + 81, 34, 20, Component.translatable("gui.kidcoiny.kasa_gui.button_kup2"), (e) -> {
         KidcoinyMod.PACKET_HANDLER.sendToServer(new KasaGuiButtonMessage(2, this.x, this.y, this.z));
         KasaGuiButtonMessage.handleButtonAction(this.entity, 2, this.x, this.y, this.z);
      });
      guistate.put("button:button_kup2", this.button_kup2);
      this.addRenderableWidget(this.button_kup2);

      this.button_kup3 = new Button(this.leftPos + 116, this.topPos + 106, 34, 20, Component.translatable("gui.kidcoiny.kasa_gui.button_kup3"), (e) -> {
         KidcoinyMod.PACKET_HANDLER.sendToServer(new KasaGuiButtonMessage(3, this.x, this.y, this.z));
         KasaGuiButtonMessage.handleButtonAction(this.entity, 3, this.x, this.y, this.z);
      });
      guistate.put("button:button_kup3", this.button_kup3);
      this.addRenderableWidget(this.button_kup3);

      this.button_kup4 = new Button(this.leftPos + 116, this.topPos + 131, 34, 20, Component.translatable("gui.kidcoiny.kasa_gui.button_kup4"), (e) -> {
         KidcoinyMod.PACKET_HANDLER.sendToServer(new KasaGuiButtonMessage(4, this.x, this.y, this.z));
         KasaGuiButtonMessage.handleButtonAction(this.entity, 4, this.x, this.y, this.z);
      });
      guistate.put("button:button_kup4", this.button_kup4);
      this.addRenderableWidget(this.button_kup4);
   }

   static {
      guistate = KasaGuiMenu.guistate;
      texture = new ResourceLocation("kidcoiny:textures/screens/kasa_gui.png");
   }
}

