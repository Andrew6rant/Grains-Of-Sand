package briancomics.grains.of.sand.init.item;

import briancomics.grains.of.sand.helper.TimeManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class TimeClockItem extends Item {
	public TimeClockItem (Settings settings) {
		super(settings);
	}

	@Override
	public TypedActionResult<ItemStack> use (World world, PlayerEntity user, Hand hand) {
		if (!world.isClient()) {
			if (TimeManager.shouldTick) {
				TimeManager.shouldTick = false;
				TimeManager.frozenTickDelta = MinecraftClient.getInstance().getTickDelta();
				TimeManager.initiator = user.getUuid();
			} else {
				TimeManager.shouldTick = true;
				TimeManager.frozenTickDelta = 0;
				TimeManager.initiator = null;
			}
		}
		return TypedActionResult.success(user.getStackInHand(hand));
	}
}
