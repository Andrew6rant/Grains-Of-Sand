package briancomics.grains.of.sand;

import briancomics.grains.of.sand.init.item.TimeClockItem;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;

public class GrainsOfSandMain implements ModInitializer {
	public static final TimeClockItem TIME_CLOCK_ITEM = new TimeClockItem(new FabricItemSettings().group(ItemGroup.MISC).fireproof().rarity(Rarity.RARE).maxDamage(1500));

	@Override
	public void onInitialize () {
		Registry.register(Registry.ITEM, new Identifier("sand", "time_clock"), TIME_CLOCK_ITEM);
	}
}
