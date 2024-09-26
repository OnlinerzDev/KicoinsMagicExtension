package net.mcreator.kidcoiny.network;

import java.util.HashMap;
import java.util.function.Supplier;
import net.mcreator.kidcoiny.KidcoinyMod;
import net.mcreator.kidcoiny.procedures.Kasa1Procedure;
import net.mcreator.kidcoiny.procedures.Kasa2Procedure;
import net.mcreator.kidcoiny.procedures.Kasa3Procedure;
import net.mcreator.kidcoiny.procedures.Kasa4Procedure;
import net.mcreator.kidcoiny.procedures.Kasa5Procedure;
import net.mcreator.kidcoiny.world.inventory.KasaGuiMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.network.NetworkEvent.Context;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class KasaGuiButtonMessage {
   private final int buttonID;
   private final int x;
   private final int y;
   private final int z;

   public KasaGuiButtonMessage(FriendlyByteBuf buffer) {
      this.buttonID = buffer.readInt();
      this.x = buffer.readInt();
      this.y = buffer.readInt();
      this.z = buffer.readInt();
   }

   public KasaGuiButtonMessage(int buttonID, int x, int y, int z) {
      this.buttonID = buttonID;
      this.x = x;
      this.y = y;
      this.z = z;
   }

   public static void buffer(KasaGuiButtonMessage message, FriendlyByteBuf buffer) {
      buffer.writeInt(message.buttonID);
      buffer.writeInt(message.x);
      buffer.writeInt(message.y);
      buffer.writeInt(message.z);
   }

   public static void handler(KasaGuiButtonMessage message, Supplier<Context> contextSupplier) {
      Context context = contextSupplier.get();
      context.enqueueWork(() -> {
         Player entity = context.getSender();
         if (entity != null) {
            handleButtonAction(entity, message.buttonID, message.x, message.y, message.z);
         }
      });
      context.setPacketHandled(true);
   }

   public static void handleButtonAction(Player entity, int buttonID, int x, int y, int z) {
      Level world = entity.level;  // Korrektur von 'f_19853_' auf 'level'
      HashMap<?, ?> guistate = KasaGuiMenu.guistate;

      if (!world.hasChunkAt(new BlockPos(x, y, z))) {
         return;
      }

      // Button-Interaktionen
      if (buttonID == 0) {
         Kasa1Procedure.execute(world, entity);
      } else if (buttonID == 1) {
         Kasa2Procedure.execute(world, entity);
      } else if (buttonID == 2) {
         Kasa3Procedure.execute(world, entity);
      } else if (buttonID == 3) {
         Kasa4Procedure.execute(world, entity);
      } else if (buttonID == 4) {
         Kasa5Procedure.execute(world, entity);
      }
   }

   @SubscribeEvent
   public static void registerMessage(FMLCommonSetupEvent event) {
      KidcoinyMod.addNetworkMessage(KasaGuiButtonMessage.class, KasaGuiButtonMessage::buffer, KasaGuiButtonMessage::new, KasaGuiButtonMessage::handler);
   }
}
