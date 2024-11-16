package mod.acats.vitaldeprivation.client.animation.feet;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.CollisionGetter;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public interface FootPositionTracker {
    void tick(float walkCycleProgress, CollisionGetter collisionGetter, @Nullable Entity entity);
    void resetCycle();
    Vec3 position();
}
