package nebuli.no_chat_delay.mixin;

import net.minecraft.client.multiplayer.chat.ChatListener;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.PlayerChatMessage;
import com.mojang.authlib.GameProfile;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChatListener.class)
public abstract class MessageHandlerMixin {

    @Shadow
    public abstract void flushQueue();

    @Inject(method = "handlePlayerChatMessage", at = @At("TAIL"))
    private void forceInstantPlayerChat(PlayerChatMessage message, GameProfile profile, ChatType.Bound chatType, CallbackInfo ci) {
        this.flushQueue();
    }

    @Inject(method = "handleSystemMessage", at = @At("TAIL"))
    private void forceInstantSystemChat(Component message, boolean overlay, CallbackInfo ci) {
        this.flushQueue();
    }
}