package briancomics.grains.of.sand.init.item;

import briancomics.grains.of.sand.cca.MyComponents;
import briancomics.grains.of.sand.cca.TimeComponent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

import java.util.UUID;

public class TimeClockItem extends Item {
	public TimeClockItem (Settings settings) {
		super(settings);
	}

	@Override
	public TypedActionResult<ItemStack> use (World world, PlayerEntity user, Hand hand) {
		ItemStack itemStack = user.getStackInHand(hand);
		itemStack.damage(1, user, (p) -> {
			p.sendToolBreakStatus(hand);
		});
		if (!world.isClient()) {
			TimeComponent timeComponent = MyComponents.TIME_COMPONENT.get(world);
			if (timeComponent.getTimeStopped()) {
				user.getItemCooldownManager().set(this, 200);
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
		return TypedActionResult.success(itemStack, world.isClient());
	}

	@Override
	public void inventoryTick (ItemStack stack, World world, Entity player, int slot, boolean selected) {
		if (!world.isClient()) {
			TimeComponent timeComponent = MyComponents.TIME_COMPONENT.get(world);
			if (timeComponent.getTimeStopped()) {
				stack.damage(1, (LivingEntity) player, (e) -> {
					e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND);
				});
				//stack.setDamage(stack.getDamage() + 1);
				if (stack.getDamage() == 0) {
					//stack.use(world, (PlayerEntity) player, Hand.MAIN_HAND);
					if (timeComponent.getTimeStopped()) {
						timeComponent.setTimeStopped(false);
						timeComponent.setTimeStopper(UUID.randomUUID());
						timeComponent.setFrozenTickDelta(0);
					}
				}
				System.out.println(stack.getDamage());
			}
		}
	}
}
