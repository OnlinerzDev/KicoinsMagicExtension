package net.mcreator.kidcoiny.client.screens;

import com.mojang.blaze3d.platform.GlStateManager.DestFactor;
import com.mojang.blaze3d.platform.GlStateManager.SourceFactor;
import com.mojang.blaze3d.systems.RenderSystem;
import net.mcreator.kidcoiny.procedures.MoneyuitextProcedure;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiEvent.Pre;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber({Dist.CLIENT})
public class CoinGui2Overlay {

   @SubscribeEvent(priority = EventPriority.NORMAL)
   public static void eventHandler(Pre event) {
      int w = event.getWindow().getGuiScaledWidth();
      int h = event.getWindow().getGuiScaledHeight();
      int posX = w / 2;
      int posY = h / 2;

      Level world = null;
      double x = 0.0D;
      double y = 0.0D;
      double z = 0.0D;

      Player entity = Minecraft.getInstance().player;
      if (entity != null) {
         world = entity.level;
         x = entity.getX();
         y = entity.getY();
         z = entity.getZ();
      }

      RenderSystem.disableDepthTest();
      RenderSystem.depthMask(false);
      RenderSystem.enableBlend();
      RenderSystem.setShader(GameRenderer::getPositionTexShader);
      RenderSystem.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.setShaderTexture(0, new ResourceLocation("kidcoiny:textures/screens/kidcoins_portfel.png"));

      Gui gui = Minecraft.getInstance().gui;
      gui.blit(event.getPoseStack(), posX - 320, posY - 59, 0, 0, 204, 204, 204, 204);

      Minecraft.getInstance().font.draw(event.getPoseStack(), MoneyuitextProcedure.execute(entity), posX - 292, posY - 13, -1);


      RenderSystem.disableBlend();
      RenderSystem.depthMask(true);
      RenderSystem.enableDepthTest();
   }
}
