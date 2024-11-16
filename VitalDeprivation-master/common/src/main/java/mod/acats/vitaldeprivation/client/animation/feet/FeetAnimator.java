package mod.acats.vitaldeprivation.client.animation.feet;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.CollisionGetter;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class FeetAnimator {
    public final FootPositionTracker[] feet;
    public FeetAnimator(FootPositionTracker... feet) {
        this.feet = feet;
    }

    float walkProgress;

    public void tick(float speed, CollisionGetter collisionGetter, @Nullable Entity entity) {
        walkProgress += speed;
        for (FootPositionTracker tracker : feet) {
            tracker.tick(walkProgress, collisionGetter, entity);
        }
        if (this.walkProgress > 1.0F) {
            this.walkProgress = 0.0F;
            for (FootPositionTracker foot:
                 feet) {
                foot.resetCycle();
            }
        }
    }

    public void tick(LivingEntity entity, float speedMultiplier, CollisionGetter collisionGetter) {
        this.tick((float) entity.position().subtract(new Vec3(entity.xOld, entity.yOld, entity.zOld)).length() * speedMultiplier, collisionGetter, entity);
    }
}
