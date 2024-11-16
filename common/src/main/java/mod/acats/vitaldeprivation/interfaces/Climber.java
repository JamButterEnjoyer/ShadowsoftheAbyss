package mod.acats.vitaldeprivation.interfaces;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public interface Climber {
    float maxClampDist();
    float minClampDist();
    default float yankStrengthMultiplier() {
        return 0.1F;
    }
    default void clampToSurfaceOrDrop() {
        Mob mob = (Mob) this;

        float maxDistSq = maxClampDist() * maxClampDist();
        float minDistSq = maxDistSq;
        Vec3 yank = Vec3.ZERO;
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                for (int k = -1; k < 2; k++) {
                    if (!(i == 0 && j == 0 && k == 0)) {
                        float d = raycastDistSq(new Vec3(i, j, k));
                        if (d < maxDistSq) {
                            yank = yank.add(i, j, k);
                            if (d < minDistSq) {
                                minDistSq = d;
                            }
                        }
                    }
                }
            }
        }

        if (minDistSq > minClampDist() * minClampDist()){
            mob.setDeltaMovement(mob.getDeltaMovement().add(yank.scale(yankStrengthMultiplier() * minDistSq)));
        }

        mob.setNoGravity(minDistSq < maxDistSq);
    }

    default float raycastDistSq(Vec3 direction) {
        Mob mob = (Mob) this;

        Vec3 start = mob.position().add(0.0D, mob.getBbHeight() / 2.0D, 0.0D);
        Vec3 end = start.add(direction.scale(maxClampDist()));

        BlockHitResult result = mob.level().clip(new ClipContext(start, end, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, mob));
        return (float)result.getLocation().distanceToSqr(start);
    }
}
