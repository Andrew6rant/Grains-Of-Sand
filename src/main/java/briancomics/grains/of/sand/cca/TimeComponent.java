package briancomics.grains.of.sand.cca;

import dev.onyxstudios.cca.api.v3.component.ComponentV3;
import net.minecraft.entity.Entity;

import java.util.UUID;

public interface TimeComponent extends ComponentV3 {
	boolean canBeFrozen (Entity entity);
	boolean getTimeStopped ();
	UUID getTimeStopper ();
	float getFrozenTickDelta ();
	void setTimeStopped (boolean timeStopped);
	void setTimeStopper (UUID timeStopper);
	void setFrozenTickDelta (float tickDelta);
}
