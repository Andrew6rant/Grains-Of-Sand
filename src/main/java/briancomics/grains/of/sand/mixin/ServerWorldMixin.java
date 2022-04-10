package briancomics.grains.of.sand.mixin;

import briancomics.grains.of.sand.helper.TimeManager;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.fluid.Fluid;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.WorldChunk;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerWorld.class)
public class ServerWorldMixin {
	@Inject(method = "tickBlock", at = @At("HEAD"), cancellable = true)
	private void doNotTickBlock (BlockPos pos, Block block, CallbackInfo ci) {
		if (!TimeManager.shouldTick)
			ci.cancel();
	}

	@Inject(method = "tickChunk", at = @At("HEAD"), cancellable = true)
	private void tickChunk (WorldChunk chunk, int randomTickSpeed, CallbackInfo ci) {
		if (!TimeManager.shouldTick)
			ci.cancel();
	}

	@Inject(method = "tickEntity", at = @At("HEAD"), cancellable = true)
	private void doNotTickEntity (Entity entity, CallbackInfo ci) {
		if (!TimeManager.shouldTick && TimeManager.canBeFrozen(entity))
			ci.cancel();
	}

	@Inject(method = "tickFluid", at = @At("HEAD"), cancellable = true)
	private void doNotTickFluid (BlockPos pos, @Nullable Fluid fluid, CallbackInfo ci) {
		if (!TimeManager.shouldTick)
			ci.cancel();
	}

	@Inject(method = "tickPassenger", at = @At("HEAD"), cancellable = true)
	private void doNotTickPassenger (Entity vehicle, Entity passenger, CallbackInfo ci) {
		if (!TimeManager.shouldTick && TimeManager.canBeFrozen(passenger))
			ci.cancel();
	}

	@Inject(method = "tickSpawners", at = @At("HEAD"), cancellable = true)
	private void doNotTickSpawners (boolean spawnMonsters, boolean spawnAnimals, CallbackInfo ci) {
		if (!TimeManager.shouldTick)
			ci.cancel();
	}

	@Inject(method = "tickTime", at = @At("HEAD"), cancellable = true)
	private void doNotTickDayNight (CallbackInfo ci) {
		if (!TimeManager.shouldTick)
			ci.cancel();
	}

	@Inject(method = "tickWeather", at = @At("HEAD"), cancellable = true)
	private void doNotTickWeather (CallbackInfo ci) {
		if (!TimeManager.shouldTick)
			ci.cancel();
	}
}
