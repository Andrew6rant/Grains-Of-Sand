package briancomics.grains.of.sand.mixin;

import briancomics.grains.of.sand.helper.TimeManager;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientWorld.class)
public class ClientWorldMixin {
	@Inject(method = "randomBlockDisplayTick", at = @At("HEAD"), cancellable = true)
	private void doNotSpawnBlockParticle (CallbackInfo ci) {
		if (!TimeManager.shouldTick)
			ci.cancel();
	}

	@Inject(method = "tickEntity", at = @At("HEAD"), cancellable = true)
	private void doNotTickEntity (Entity entity, CallbackInfo ci) {
		if (!TimeManager.shouldTick && TimeManager.canBeFrozen(entity))
			ci.cancel();
	}

	@Inject(method = "tickPassenger", at = @At("HEAD"), cancellable = true)
	private void doNotTickPassenger (Entity vehicle, Entity passenger, CallbackInfo ci) {
		if (!TimeManager.shouldTick && TimeManager.canBeFrozen(passenger))
			ci.cancel();
	}

	@Inject(method = "tickTime", at = @At("HEAD"), cancellable = true)
	private void doNotTickDayNight (CallbackInfo ci) {
		if (!TimeManager.shouldTick)
			ci.cancel();
	}
}
