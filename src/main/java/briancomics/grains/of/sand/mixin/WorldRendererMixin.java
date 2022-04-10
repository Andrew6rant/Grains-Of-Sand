package briancomics.grains.of.sand.mixin;

import briancomics.grains.of.sand.helper.TimeManager;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.WorldRenderer;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {
	@Shadow private int ticks;

	@Redirect(method = "tick", at = @At(value = "FIELD", target = "Lnet/minecraft/client/render/WorldRenderer;ticks:I", opcode = Opcodes.PUTFIELD))
	private void doNotIncreaseRenderTickCounter (WorldRenderer instance, int increasedValue) {
		ticks = increasedValue - (TimeManager.shouldTick ? 0 : 1);
	}

	@ModifyArgs(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/WorldRenderer;renderEntity(Lnet/minecraft/entity/Entity;DDDFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;)V"))
	private void doNotInterpolateEntities (Args args) {
		if (!TimeManager.shouldTick && TimeManager.canBeFrozen(args.get(0)))
			args.set(4, TimeManager.frozenTickDelta);
	}

	@ModifyArg(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/particle/ParticleManager;renderParticles(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider$Immediate;Lnet/minecraft/client/render/LightmapTextureManager;Lnet/minecraft/client/render/Camera;F)V"))
	private float doNotInterpolateParticles (float tickDelta) {
		return TimeManager.shouldTick ? tickDelta : TimeManager.frozenTickDelta;
	}

	@ModifyArg(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/WorldRenderer;renderWeather(Lnet/minecraft/client/render/LightmapTextureManager;FDDD)V"))
	private float doNotInterpolateWeather (float tickDelta) {
		return TimeManager.shouldTick ? tickDelta : TimeManager.frozenTickDelta;
	}

	@ModifyArg(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gl/ShaderEffect;render(F)V"))
	private float doNotInterpolateWeatherShader (float tickDelta) {
		return TimeManager.shouldTick ? tickDelta : TimeManager.frozenTickDelta;
	}

	@Inject(method = "tickRainSplashing", at = @At("HEAD"), cancellable = true)
	private void doNotMakeRainSplashes (Camera camera, CallbackInfo ci) {
		if (!TimeManager.shouldTick)
			ci.cancel();
	}
}
