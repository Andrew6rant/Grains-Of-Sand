package briancomics.grains.of.sand.init.item;

import briancomics.grains.of.sand.cca.MyComponents;
import briancomics.grains.of.sand.cca.TimeComponent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.UUID;

public class TimeClockItem extends Item {
	public TimeClockItem (Settings settings) {
		super(settings);
	}

	@Override
	public TypedActionResult<ItemStack> use (World world, PlayerEntity user, Hand hand) {
		if (!world.isClient()) {
			TimeComponent timeComponent = MyComponents.TIME_COMPONENT.get(world);
			if (timeComponent.getTimeStopped()) {
				timeComponent.setTimeStopped(false);
				timeComponent.setTimeStopper(UUID.randomUUID());
				timeComponent.setFrozenTickDelta(0);
			} else {
				timeComponent.setTimeStopped(true);
				timeComponent.setTimeStopper(user.getUuid());
				timeComponent.setFrozenTickDelta(MinecraftClient.getInstance().getTickDelta());
			}
			MyComponents.TIME_COMPONENT.sync(world);
		}
		return TypedActionResult.success(user.getStackInHand(hand));
	}
}
