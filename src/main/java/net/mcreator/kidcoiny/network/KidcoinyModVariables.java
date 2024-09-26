package net.mcreator.kidcoiny.network;

import java.util.function.Supplier;
import net.mcreator.kidcoiny.KidcoinyMod;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.Clone;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerChangedDimensionEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerRespawnEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.NetworkEvent.Context;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class KidcoinyModVariables {
   public static final Capability<KidcoinyModVariables.PlayerVariables> PLAYER_VARIABLES_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {});

   @SubscribeEvent
   public static void init(FMLCommonSetupEvent event) {
      KidcoinyMod.addNetworkMessage(KidcoinyModVariables.PlayerVariablesSyncMessage.class, KidcoinyModVariables.PlayerVariablesSyncMessage::buffer, KidcoinyModVariables.PlayerVariablesSyncMessage::new, KidcoinyModVariables.PlayerVariablesSyncMessage::handler);
   }

   @SubscribeEvent
   public static void init(RegisterCapabilitiesEvent event) {
      event.register(KidcoinyModVariables.PlayerVariables.class);
   }

   public static class PlayerVariablesSyncMessage {
      public KidcoinyModVariables.PlayerVariables data;

      public PlayerVariablesSyncMessage(FriendlyByteBuf buffer) {
         this.data = new KidcoinyModVariables.PlayerVariables();
         this.data.readNBT(buffer.readNbt());
      }

      public PlayerVariablesSyncMessage(KidcoinyModVariables.PlayerVariables data) {
         this.data = data;
      }

      public static void buffer(KidcoinyModVariables.PlayerVariablesSyncMessage message, FriendlyByteBuf buffer) {
         buffer.writeNbt((CompoundTag) message.data.writeNBT());
      }

      public static void handler(KidcoinyModVariables.PlayerVariablesSyncMessage message, Supplier<Context> contextSupplier) {
         Context context = contextSupplier.get();
         context.enqueueWork(() -> {
            if (!context.getDirection().getReceptionSide().isServer()) {
               Player player = Minecraft.getInstance().player;
               if (player != null) {
                  KidcoinyModVariables.PlayerVariables variables = player.getCapability(KidcoinyModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new KidcoinyModVariables.PlayerVariables());
                  variables.coin1value = message.data.coin1value;
                  variables.coin10value = message.data.coin10value;
                  variables.coin100value = message.data.coin100value;
                  variables.coin1000value = message.data.coin1000value;
               }
            }
         });
         context.setPacketHandled(true);
      }
   }

   public static class PlayerVariables {
      public double coin1value = 0.0;
      public double coin10value = 0.0;
      public double coin100value = 0.0;
      public double coin1000value = 0.0;

      public void syncPlayerVariables(Player player) {  // Geändert von Entity zu Player
         if (player instanceof ServerPlayer serverPlayer) {
            KidcoinyMod.PACKET_HANDLER.send(PacketDistributor.PLAYER.with(() -> serverPlayer), new PlayerVariablesSyncMessage(this));
         }
      }

      public Tag writeNBT() {
         CompoundTag nbt = new CompoundTag();
         nbt.putDouble("coin1value", this.coin1value);
         nbt.putDouble("coin10value", this.coin10value);
         nbt.putDouble("coin100value", this.coin100value);
         nbt.putDouble("coin1000value", this.coin1000value);
         return nbt;
      }

      public void readNBT(Tag tag) {
         CompoundTag nbt = (CompoundTag) tag;
         this.coin1value = nbt.getDouble("coin1value");
         this.coin10value = nbt.getDouble("coin10value");
         this.coin100value = nbt.getDouble("coin100value");
         this.coin1000value = nbt.getDouble("coin1000value");
      }
   }

   @EventBusSubscriber
   private static class PlayerVariablesProvider implements ICapabilitySerializable<Tag> {
      private final PlayerVariables playerVariables = new PlayerVariables();
      private final LazyOptional<PlayerVariables> instance = LazyOptional.of(() -> this.playerVariables);

      @SubscribeEvent
      public static void onAttachCapabilities(AttachCapabilitiesEvent<Entity> event) {
         if (event.getObject() instanceof Player && !(event.getObject() instanceof FakePlayer)) {
            event.addCapability(new ResourceLocation("kidcoiny", "player_variables"), new PlayerVariablesProvider());
         }
      }

      @Override
      public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
         return cap == PLAYER_VARIABLES_CAPABILITY ? this.instance.cast() : LazyOptional.empty();
      }

      @Override
      public Tag serializeNBT() {
         return this.playerVariables.writeNBT();
      }

      @Override
      public void deserializeNBT(Tag nbt) {
         this.playerVariables.readNBT(nbt);
      }
   }

   @EventBusSubscriber
   public static class EventBusVariableHandlers {
      @SubscribeEvent
      public static void onPlayerLoggedInSyncPlayerVariables(PlayerLoggedInEvent event) {
         if (!event.getEntity().level.isClientSide) {
            event.getEntity().getCapability(PLAYER_VARIABLES_CAPABILITY, null).ifPresent(vars -> vars.syncPlayerVariables(event.getEntity()));
         }
      }

      @SubscribeEvent
      public static void onPlayerRespawnedSyncPlayerVariables(PlayerRespawnEvent event) {
         if (!event.getEntity().level.isClientSide) {
            event.getEntity().getCapability(PLAYER_VARIABLES_CAPABILITY, null).ifPresent(vars -> vars.syncPlayerVariables(event.getEntity()));
         }
      }

      @SubscribeEvent
      public static void onPlayerChangedDimensionSyncPlayerVariables(PlayerChangedDimensionEvent event) {
         if (!event.getEntity().level.isClientSide) {
            event.getEntity().getCapability(PLAYER_VARIABLES_CAPABILITY, null).ifPresent(vars -> vars.syncPlayerVariables(event.getEntity()));
         }
      }

      @SubscribeEvent
      public static void clonePlayer(Clone event) {
         if (!event.isWasDeath()) {
            event.getOriginal().revive();
            event.getOriginal().getCapability(PLAYER_VARIABLES_CAPABILITY, null).ifPresent(original -> {
               event.getEntity().getCapability(PLAYER_VARIABLES_CAPABILITY, null).ifPresent(clone -> {
                  clone.coin1value = original.coin1value;
                  clone.coin10value = original.coin10value;
                  clone.coin100value = original.coin100value;
                  clone.coin1000value = original.coin1000value;
               });
            });
         }
      }
   }
}
