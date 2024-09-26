package net.mcreator.kidcoiny.network;

import java.util.HashMap;
import java.util.function.Supplier;
import net.mcreator.kidcoiny.KidcoinyMod;
import net.mcreator.kidcoiny.procedures.*;
import net.mcreator.kidcoiny.world.inventory.KasaGui3Menu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.network.NetworkEvent.Context;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class KasaGui3ButtonMessage {
    private final int buttonID;
    private final int x;
    private final int y;
    private final int z;

    public KasaGui3ButtonMessage(FriendlyByteBuf buffer) {
        this.buttonID = buffer.readInt();
        this.x = buffer.readInt();
        this.y = buffer.readInt();
        this.z = buffer.readInt();
    }

    public KasaGui3ButtonMessage(int buttonID, int x, int y, int z) {
        this.buttonID = buttonID;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static void buffer(KasaGui3ButtonMessage message, FriendlyByteBuf buffer) {
        buffer.writeInt(message.buttonID);
        buffer.writeInt(message.x);
        buffer.writeInt(message.y);
        buffer.writeInt(message.z);
    }

    public static void handler(KasaGui3ButtonMessage message, Supplier<Context> contextSupplier) {
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
        HashMap<?, ?> guistate = KasaGui3Menu.guistate;

        if (!world.hasChunkAt(new BlockPos(x, y, z))) {
            return;
        }

        // Button-Interaktionen
        if (buttonID == 0) {
            Kasaaa1Procedure.execute(world, entity);
        } else if (buttonID == 1) {
            Kasaaa2Procedure.execute(world, entity);
        } else if (buttonID == 2) {
            Kasaaa3Procedure.execute(world, entity);
        } else if (buttonID == 3) {
            Kasaaa4Procedure.execute(world, entity);
        } else if (buttonID == 4) {
            Kasaaa5Procedure.execute(world, entity);
        }
    }

    @SubscribeEvent
    public static void registerMessage(FMLCommonSetupEvent event) {
        KidcoinyMod.addNetworkMessage(KasaGui3ButtonMessage.class, KasaGui3ButtonMessage::buffer, KasaGui3ButtonMessage::new, KasaGui3ButtonMessage::handler);
    }
}
