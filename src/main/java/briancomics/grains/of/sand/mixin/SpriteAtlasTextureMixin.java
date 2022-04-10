package briancomics.grains.of.sand.mixin;

import briancomics.grains.of.sand.helper.TimeManager;
import net.minecraft.client.texture.SpriteAtlasTexture;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SpriteAtlasTexture.class)
public class SpriteAtlasTextureMixin {
	@Inject(method = "tickAnimatedSprites", at = @At("HEAD"), cancellable = true)
	void doNotTickAnimatedTextures (CallbackInfo ci) {
		if (!TimeManager.shouldTick)
			ci.cancel();
	}
}
