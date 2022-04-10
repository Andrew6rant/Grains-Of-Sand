package briancomics.grains.of.sand.mixin;

import briancomics.grains.of.sand.helper.TimeManager;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ParticleManager.class)
public class ParticleManagerMixin {
	@Inject(method = "tickParticle", at = @At("HEAD"), cancellable = true)
	void doNotTickParticle (Particle particle, CallbackInfo ci) {
		if (!TimeManager.shouldTick)
			ci.cancel();
	}
}
