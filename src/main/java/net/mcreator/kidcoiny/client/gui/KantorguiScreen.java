package net.mcreator.kidcoiny.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.HashMap;
import net.mcreator.kidcoiny.KidcoinyMod;
import net.mcreator.kidcoiny.network.KantorguiButtonMessage;
import net.mcreator.kidcoiny.world.inventory.KantorguiMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class KantorguiScreen extends AbstractContainerScreen<KantorguiMenu> {
   private static final HashMap<String, Object> guistate;
   private final Level world;
   private final int x;
   private final int y;
   private final int z;
   private final Player entity;
   ImageButton imagebutton_icon3;
   ImageButton imagebutton_icon2;
   ImageButton imagebutton_icon1;
   ImageButton imagebutton_icon21;
   private static final ResourceLocation texture;

   public KantorguiScreen(KantorguiMenu container, Inventory inventory, Component text) {
      super(container, inventory, text);
      this.world = container.world;
      this.x = container.x;
      this.y = container.y;
      this.z = container.z;
      this.entity = container.entity;
      this.imageWidth = 176; // f_97726_
      this.imageHeight = 166; // f_97727_
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
      blit(ms, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);

      RenderSystem.setShaderTexture(0, new ResourceLocation("kidcoiny:textures/screens/kursy2.png"));
      blit(ms, this.leftPos + 0, this.topPos + 15, 0, 0, 64, 64, 64, 64);

      RenderSystem.setShaderTexture(0, new ResourceLocation("kidcoiny:textures/screens/ramka_tlo2.png"));
      blit(ms, this.leftPos + 55, this.topPos + 13, 0, 0, 121, 68, 121, 68);

      RenderSystem.setShaderTexture(0, new ResourceLocation("kidcoiny:textures/screens/betterarrow2.png"));
      blit(ms, this.leftPos + 98, this.topPos + 25, 0, 0, 32, 24, 32, 24);

      RenderSystem.setShaderTexture(0, new ResourceLocation("kidcoiny:textures/screens/betterarrow2.png"));
      blit(ms, this.leftPos + 98, this.topPos + 48, 0, 0, 32, 24, 32, 24);

      RenderSystem.disableBlend();
   }

   @Override
   public boolean keyPressed(int key, int b, int c) {
      if (key == 256) {
         this.minecraft.player.closeContainer(); // f_96541_ -> minecraft
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
      this.font.draw(poseStack, Component.literal("Kantor wymiany Kidcoins"), 25.0F, 4.0F, -12829636);
   }

   @Override
   public void init() {
      super.init();
      this.minecraft.mouseHandler.releaseMouse(); // Der Mauszeiger wird freigegeben, sodass er sichtbar ist.

      // Image Buttons
      this.imagebutton_icon3 = new ImageButton(this.leftPos + 134, this.topPos + 48, 24, 24, 0, 0, 24, new ResourceLocation("kidcoiny:textures/screens/atlas/imagebutton_icon3.png"), 24, 48, (e) -> {
         KidcoinyMod.PACKET_HANDLER.sendToServer(new KantorguiButtonMessage(0, this.x, this.y, this.z));
         KantorguiButtonMessage.handleButtonAction(this.entity, 0, this.x, this.y, this.z);
      });
      guistate.put("button:imagebutton_icon3", this.imagebutton_icon3);
      this.addRenderableWidget(this.imagebutton_icon3);

      this.imagebutton_icon2 = new ImageButton(this.leftPos + 134, this.topPos + 24, 24, 24, 0, 0, 24, new ResourceLocation("kidcoiny:textures/screens/atlas/imagebutton_icon2.png"), 24, 48, (e) -> {
         KidcoinyMod.PACKET_HANDLER.sendToServer(new KantorguiButtonMessage(1, this.x, this.y, this.z));
         KantorguiButtonMessage.handleButtonAction(this.entity, 1, this.x, this.y, this.z);
      });
      guistate.put("button:imagebutton_icon2", this.imagebutton_icon2);
      this.addRenderableWidget(this.imagebutton_icon2);

      this.imagebutton_icon1 = new ImageButton(this.leftPos + 71, this.topPos + 24, 24, 24, 0, 0, 24, new ResourceLocation("kidcoiny:textures/screens/atlas/imagebutton_icon1.png"), 24, 48, (e) -> {
         KidcoinyMod.PACKET_HANDLER.sendToServer(new KantorguiButtonMessage(2, this.x, this.y, this.z));
         KantorguiButtonMessage.handleButtonAction(this.entity, 2, this.x, this.y, this.z);
      });
      guistate.put("button:imagebutton_icon1", this.imagebutton_icon1);
      this.addRenderableWidget(this.imagebutton_icon1);

      this.imagebutton_icon21 = new ImageButton(this.leftPos + 71, this.topPos + 48, 24, 24, 0, 0, 24, new ResourceLocation("kidcoiny:textures/screens/atlas/imagebutton_icon21.png"), 24, 48, (e) -> {
         KidcoinyMod.PACKET_HANDLER.sendToServer(new KantorguiButtonMessage(3, this.x, this.y, this.z));
         KantorguiButtonMessage.handleButtonAction(this.entity, 3, this.x, this.y, this.z);
      });
      guistate.put("button:imagebutton_icon21", this.imagebutton_icon21);
      this.addRenderableWidget(this.imagebutton_icon21);
   }


   static {
      guistate = KantorguiMenu.guistate;
      texture = new ResourceLocation("kidcoiny:textures/screens/kantorgui.png");
   }
}
