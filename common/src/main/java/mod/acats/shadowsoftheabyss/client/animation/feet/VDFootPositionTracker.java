package mod.acats.vitaldeprivation.client.animation.feet;

import mod.acats.fromanotherlibrary.utilities.Maths;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.CollisionGetter;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class VDFootPositionTracker implements FootPositionTracker {

    public final int moveTime;
    public final float liftHeight;
    public final float movePointInCycle;
    public final float length;
    private final Supplier<Float> desiredOffsetX;
    private final Supplier<Float> desiredOffsetZ;
    private final Supplier<Float> yaw;
    private final Supplier<Vec3> origin;
    private final Supplier<Vec3> connectionPoint;
    private final Supplier<Vec3> oppositeConnectionPoint;
    private final float maxVerticalOffset;
    public VDFootPositionTracker(int moveTime,
                                 float liftHeight,
                                 float movePointInCycle,
                                 float length,
                                 Supplier<Float> desiredOffsetX,
                                 Supplier<Float> desiredOffsetZ,
                                 Supplier<Float> yaw,
                                 Supplier<Vec3> origin,
                                 Supplier<Vec3> connectionPoint,
                                 Supplier<Vec3> oppositeConnectionPoint,
                                 float maxVerticalOffset) {
        this.moveTime = moveTime;
        this.liftHeight = liftHeight;
        this.movePointInCycle = movePointInCycle;
        this.length = length;
        this.desiredOffsetX = desiredOffsetX;
        this.desiredOffsetZ = desiredOffsetZ;
        this.yaw = yaw;
        this.origin = origin;
        this.connectionPoint = connectionPoint;
        this.maxVerticalOffset = maxVerticalOffset;
        this.oppositeConnectionPoint = oppositeConnectionPoint;
    }

    public VDFootPositionTracker(LivingEntity entity,
                                 int moveTime,
                                 float liftHeight,
                                 float movePointInCycle,
                                 float length,
                                 Supplier<Float> desiredOffsetX,
                                 Supplier<Float> desiredOffsetZ,
                                 Supplier<Vec3> connectionPoint,
                                 Supplier<Vec3> oppositeConnectionPoint,
                                 float maxVerticalOffset) {
        this(moveTime, liftHeight, movePointInCycle, length, desiredOffsetX, desiredOffsetZ, () -> entity.yBodyRot, entity::position, connectionPoint, oppositeConnectionPoint, maxVerticalOffset);
    }
    public float yawInDegrees() {
        return yaw.get();
    }
    public Vec3 origin() {
        return origin.get();
    }
    public Vec3 start() {
        return connectionPoint.get();
    }

    private Vec3 desiredPosition = Vec3.ZERO;
    private Vec3 position = Vec3.ZERO;
    @Override
    public Vec3 position() {
        return this.position;
    }

    @Override
    public void resetCycle() {
        this.moved = false;
    }

    @Override
    public void tick(float walkCycleProgress, CollisionGetter collisionGetter, @Nullable Entity entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Vital Deprivation Foot Position Tracker requires a non-null entity!");
        }
        updateDesiredPosition(collisionGetter, entity);
        updatePosition(walkCycleProgress);
    }

    private void updateDesiredPosition(CollisionGetter collisionGetter, Entity entity) {
        Vec3 offset = Vec3.directionFromRotation(0.0F, yawInDegrees() + 90).scale(desiredOffsetZ.get());
        Vec3 offset2 = Vec3.directionFromRotation(0.0F, yawInDegrees()).scale(desiredOffsetX.get());
        Vec3 maxHeight = collisionGetter.clip(new ClipContext(origin(), origin().add(0.0D, maxVerticalOffset, 0.0D), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, entity)).getLocation();
        Vec3 defaultPosition = maxHeight.add(offset).add(offset2);
        //Vec3 decidedDist = Maths.lerpVec3(0.05F, collisionGetter.clip(new ClipContext(maxHeight, defaultPosition, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, entity)).getLocation(), maxHeight);
        Vec3 decidedHeight = collisionGetter.clip(new ClipContext(defaultPosition, defaultPosition.subtract(0.0D, maxVerticalOffset * 2.0D, 0.0D), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, entity)).getLocation();
        this.desiredPosition = contained(decidedHeight.add(0.0D, 0.3D, 0.0D), 0.95F);
    }

    private int moveProgress = 0;

    private Vec3 oldPos = Vec3.ZERO;
    private boolean moved = false;
    private void updatePosition(float walkCycleProgress) {
        if (this.moveProgress > 0) {
            this.moveProgress--;

            float progress = 1.0F - (float) moveProgress / this.moveTime;

            float height = (1.0F - Mth.square(progress - 0.5F) * 4.0F) * this.liftHeight;

            this.position = Maths.lerpVec3(progress, oldPos, desiredPosition).add(0.0D, height, 0.0D);
        }
        else if (!moved && (walkCycleProgress > this.movePointInCycle || position.distanceToSqr(connectionPoint.get()) > position.distanceToSqr(oppositeConnectionPoint.get()))) {
            this.startMove();
        }
        if (position.distanceToSqr(start()) > length * length){
            this.contain();
        }
    }

    private Vec3 contained(Vec3 pos, float in) {
        float l = length * in;
        if (pos.distanceToSqr(start()) < l * l) {
            return pos;
        }
        Vec3 startToPos = pos.subtract(start());
        return startToPos.normalize().scale(l).add(start());
    }

    private void contain() {
        position = contained(position, 1.0F);
        this.startMove();
    }

    private void startMove() {
        this.moved = true;
        this.moveProgress = this.moveTime;
        this.oldPos = this.position();
    }
}
