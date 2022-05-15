package briancomics.grains.of.sand.mixin;

import briancomics.grains.of.sand.cca.MyComponents;
import briancomics.grains.of.sand.cca.TimeComponent;
import net.minecraft.client.MinecraftClient;
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
		ClientWorld world = MinecraftClient.getInstance().world;
		if (world == null)
			return;
		if (MyComponents.TIME_COMPONENT.get(world).getTimeStopped())
			ci.cancel();
	}

	@Inject(method = "tickEntity", at = @At("HEAD"), cancellable = true)
	private void doNotTickEntity (Entity entity, CallbackInfo ci) {
		ClientWorld world = MinecraftClient.getInstance().world;
		if (world == null)
			return;
		TimeComponent timeComponent = MyComponents.TIME_COMPONENT.get(world);
		if (timeComponent.getTimeStopped() && timeComponent.canBeFrozen(entity))
			ci.cancel();
	}

	@Inject(method = "tickPassenger", at = @At("HEAD"), cancellable = true)
	private void doNotTickPassenger (Entity vehicle, Entity passenger, CallbackInfo ci) {
		ClientWorld world = MinecraftClient.getInstance().world;
		if (world == null)
			return;
		TimeComponent timeComponent = MyComponents.TIME_COMPONENT.get(world);
		if (timeComponent.getTimeStopped() && timeComponent.canBeFrozen(passenger))
			ci.cancel();
	}

	@Inject(method = "tickTime", at = @At("HEAD"), cancellable = true)
	private void doNotTickDayNight (CallbackInfo ci) {
		ClientWorld world = MinecraftClient.getInstance().world;
		if (world == null)
			return;
		if (MyComponents.TIME_COMPONENT.get(world).getTimeStopped())
			ci.cancel();
	}
}
