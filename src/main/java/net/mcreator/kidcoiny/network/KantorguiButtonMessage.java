package net.mcreator.kidcoiny.network;

import java.util.HashMap;
import java.util.function.Supplier;
import net.mcreator.kidcoiny.KidcoinyMod;
import net.mcreator.kidcoiny.procedures.Kantogui4Procedure;
import net.mcreator.kidcoiny.procedures.Kantorgui2Procedure;
import net.mcreator.kidcoiny.procedures.Kantorgui3Procedure;
import net.mcreator.kidcoiny.procedures.KantoruiProcedure;
import net.mcreator.kidcoiny.world.inventory.KantorguiMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.network.NetworkEvent.Context;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class KantorguiButtonMessage {
   private final int buttonID;
   private final int x;
   private final int y;
   private final int z;

   public KantorguiButtonMessage(FriendlyByteBuf buffer) {
      this.buttonID = buffer.readInt();
      this.x = buffer.readInt();
      this.y = buffer.readInt();
      this.z = buffer.readInt();
   }

   public KantorguiButtonMessage(int buttonID, int x, int y, int z) {
      this.buttonID = buttonID;
      this.x = x;
      this.y = y;
      this.z = z;
   }

   public static void buffer(KantorguiButtonMessage message, FriendlyByteBuf buffer) {
      buffer.writeInt(message.buttonID);
      buffer.writeInt(message.x);
      buffer.writeInt(message.y);
      buffer.writeInt(message.z);
   }

   public static void handler(KantorguiButtonMessage message, Supplier<Context> contextSupplier) {
      Context context = contextSupplier.get();
      context.enqueueWork(() -> {
         Player entity = context.getSender();
         if (entity != null) {
            // Aufruf der statischen Methode handleButtonAction
            KantorguiButtonMessage.handleButtonAction(entity, message.buttonID, message.x, message.y, message.z);
         }
      });
      context.setPacketHandled(true);
   }

   // Statische Methode zur Handhabung von Button-Interaktionen
   public static void handleButtonAction(Player entity, int buttonID, int x, int y, int z) {
      Level world = entity.level;  // Korrektur von 'f_19853_' auf 'level'
      HashMap<?, ?> guistate = KantorguiMenu.guistate;

      if (!world.hasChunkAt(new BlockPos(x, y, z))) {
         return;
      }

      if (buttonID == 0) {
         Kantorgui2Procedure.execute(world, entity);
      } else if (buttonID == 1) {
         KantoruiProcedure.execute(world, entity);
      } else if (buttonID == 2) {
         Kantorgui3Procedure.execute(world, entity);
      } else if (buttonID == 3) {
         Kantogui4Procedure.execute(world, entity);
      }
   }

   @SubscribeEvent
   public static void registerMessage(FMLCommonSetupEvent event) {
      KidcoinyMod.addNetworkMessage(KantorguiButtonMessage.class, KantorguiButtonMessage::buffer, KantorguiButtonMessage::new, KantorguiButtonMessage::handler);
   }
}
