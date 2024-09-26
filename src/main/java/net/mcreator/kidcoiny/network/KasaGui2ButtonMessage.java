package net.mcreator.kidcoiny.network;

import java.util.HashMap;
import java.util.function.Supplier;
import net.mcreator.kidcoiny.KidcoinyMod;
import net.mcreator.kidcoiny.procedures.Kasaa1Procedure;
import net.mcreator.kidcoiny.procedures.Kasaa2Procedure;
import net.mcreator.kidcoiny.procedures.Kasaa3Procedure;
import net.mcreator.kidcoiny.procedures.Kasaa4Procedure;
import net.mcreator.kidcoiny.procedures.Kasaa5Procedure;
import net.mcreator.kidcoiny.world.inventory.KasaGui2Menu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.network.NetworkEvent.Context;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class KasaGui2ButtonMessage {
   private final int buttonID;
   private final int x;
   private final int y;
   private final int z;

   public KasaGui2ButtonMessage(FriendlyByteBuf buffer) {
      this.buttonID = buffer.readInt();
      this.x = buffer.readInt();
      this.y = buffer.readInt();
      this.z = buffer.readInt();
   }

   public KasaGui2ButtonMessage(int buttonID, int x, int y, int z) {
      this.buttonID = buttonID;
      this.x = x;
      this.y = y;
      this.z = z;
   }

   public static void buffer(KasaGui2ButtonMessage message, FriendlyByteBuf buffer) {
      buffer.writeInt(message.buttonID);
      buffer.writeInt(message.x);
      buffer.writeInt(message.y);
      buffer.writeInt(message.z);
   }

   public static void handler(KasaGui2ButtonMessage message, Supplier<Context> contextSupplier) {
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
      HashMap<?, ?> guistate = KasaGui2Menu.guistate;

      if (!world.hasChunkAt(new BlockPos(x, y, z))) {
         return;
      }

      // Button-Interaktionen
      if (buttonID == 0) {
         Kasaa1Procedure.execute(world, entity);
      } else if (buttonID == 1) {
         Kasaa2Procedure.execute(world, entity);
      } else if (buttonID == 2) {
         Kasaa3Procedure.execute(world, entity);
      } else if (buttonID == 3) {
         Kasaa4Procedure.execute(world, entity);
      } else if (buttonID == 4) {
         Kasaa5Procedure.execute(world, entity);
      }
   }

   @SubscribeEvent
   public static void registerMessage(FMLCommonSetupEvent event) {
      KidcoinyMod.addNetworkMessage(KasaGui2ButtonMessage.class, KasaGui2ButtonMessage::buffer, KasaGui2ButtonMessage::new, KasaGui2ButtonMessage::handler);
   }
}
