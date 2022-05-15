package briancomics.grains.of.sand.cca;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;

import java.util.UUID;

class UsedTimeComponent implements TimeComponent, AutoSyncedComponent {
	private final Object world;
	private boolean timeStopped = false;
	private UUID timeStopper = UUID.randomUUID();
	private float frozenTickDelta = 0;

	public UsedTimeComponent (Object world) {
		this.world = world;
	}
	@Override public void readFromNbt (NbtCompound tag) {
		this.timeStopped = tag.getBoolean("timeStopped");
		this.timeStopper = tag.getUuid("timeStopper");
		this.frozenTickDelta = tag.getFloat("frozenTickDelta");
	}
	@Override public void writeToNbt (NbtCompound tag) {
		tag.putBoolean("timeStopped", this.timeStopped);
		tag.putUuid("timeStopper", this.timeStopper);
		tag.putFloat("frozenTickDelta", this.frozenTickDelta);
	}
	@Override public boolean canBeFrozen (Entity entity) {
		return !entity.getUuid().equals(this.timeStopper);
	}
	@Override public boolean getTimeStopped () {
		return this.timeStopped;
	}
	@Override public UUID getTimeStopper () {
		return this.timeStopper;
	}
	@Override public float getFrozenTickDelta () {
		return this.frozenTickDelta;
	}
	@Override public void setTimeStopped (boolean timeStopped) {
		this.timeStopped = timeStopped;
	}
	@Override public void setTimeStopper (UUID timeStopper) {
		this.timeStopper = timeStopper;
	}
	@Override public void setFrozenTickDelta (float tickDelta) {
		this.frozenTickDelta = tickDelta;
	}
}
