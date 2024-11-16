package mod.acats.vitaldeprivation.entity;

import mod.acats.fromanotherlibrary.physics.Chain;
import mod.acats.fromanotherlibrary.utilities.Maths;
import mod.acats.vitaldeprivation.client.animation.feet.FeetAnimator;
import mod.acats.vitaldeprivation.client.animation.feet.VDFootPositionTracker;
import mod.acats.vitaldeprivation.client.animation.ik.legs.VDLeg;
import mod.acats.vitaldeprivation.config.ErosionGasConfig;
import mod.acats.vitaldeprivation.entity.client.ClientOnlyEntityMethods;
import mod.acats.vitaldeprivation.entity.goal.CreatureMeleeAttackGoal;
import mod.acats.vitaldeprivation.interfaces.Climber;
import mod.acats.vitaldeprivation.interfaces.VDPlayer;
import mod.acats.vitaldeprivation.registry.BlockRegistry;
import mod.acats.vitaldeprivation.registry.ParticleRegistry;
import mod.acats.vitaldeprivation.tags.VDEntityTypeTags;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;
import org.lwjgl.system.NonnullDefault;

import java.util.function.Supplier;

@NonnullDefault
public class Creature extends Monster implements Climber {
    private static final EntityDataAccessor<Float> BODY_HEIGHT = SynchedEntityData.defineId(Creature.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Integer> CURRENT_TARGET_ID = SynchedEntityData.defineId(Creature.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Vector3f> HEAD_TARGET = SynchedEntityData.defineId(Creature.class, EntityDataSerializers.VECTOR3);
    private static final EntityDataAccessor<Boolean> IN_BITE_RANGE = SynchedEntityData.defineId(Creature.class, EntityDataSerializers.BOOLEAN);
    private final FeetAnimator feetAnimator;
    private VDFootPositionTracker createFoot(float x, float z, float movePointInCycle, Supplier<Vec3> connectionPoint, Supplier<Vec3> oppositeConnectionPoint) {
        return new VDFootPositionTracker(this, 5, 0.5F, movePointInCycle, 3.125F, () -> x * getFootDistMult1(), () -> z * getFootDistMult2(), connectionPoint, oppositeConnectionPoint, 1.0F);
    }
    private float getFootDistMult1() {
        float f = getBodyHeight() / MAX_HEIGHT;
        return Mth.lerp(f, 1.5F, 1.0F);
    }
    private float getFootDistMult2() {
        float f = getBodyHeight() / MAX_HEIGHT;
        return Mth.lerp(f, 0.1F, 1.0F);
    }

    public final VDLeg frontRight = new VDLeg(1.25F, 1.875F);
    public final VDLeg frontLeft = new VDLeg(1.25F, 1.875F);
    public final VDLeg backRight = new VDLeg(1.25F, 1.875F);
    public final VDLeg backLeft = new VDLeg(1.25F, 1.875F);
    private Vec3 frontRightShoulder = Vec3.ZERO;
    private Vec3 backRightShoulder = Vec3.ZERO;
    private Vec3 frontLeftShoulder = Vec3.ZERO;
    private Vec3 backLeftShoulder = Vec3.ZERO;
    private final Chain neck;
    private static final float MAX_HEIGHT = 1.875F;
    private float globalActuality;
    private Behaviour behaviour;
    public Creature(EntityType<? extends Monster> type, Level level) {
        super(type, level);

        feetAnimator = new FeetAnimator(
                createFoot(2.5F, 1.5F, 0.0F, () -> frontRightShoulder, () -> frontLeftShoulder),
                createFoot(-0.5F, 2.0F, 0.5F, () -> backRightShoulder, () -> backLeftShoulder),
                createFoot(2.5F, -1.5F, 0.5F, () -> frontLeftShoulder, () -> frontRightShoulder),
                createFoot(-0.5F, -2.0F, 0.0F, () -> backLeftShoulder, () -> backRightShoulder)
        );
        this.globalActuality = 0.0F;
        this.subjectiveActuality = 0.0F;

        setMaxUpStep(2.0F);

        neck = new Chain(Vec3.ZERO, 4, 14, 14);
        harmlessTime = 0;
        behaviour = Behaviour.NONE;

        //setNoGravity(true);

        // TODO maybe fix?
        //this.moveControl = new SimpleClimberMoveControl(this);
    }

    //@Override
    //protected PathNavigation createNavigation(Level level) {
    //    return new SimpleClimberNavigation(this, level);
    //}

    public static AttributeSupplier.Builder createCreatureAttributes(){
        return Monster.createMonsterAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.35D)
                .add(Attributes.ATTACK_DAMAGE, 1)
                .add(Attributes.FOLLOW_RANGE, 48.0D)
                .add(Attributes.MAX_HEALTH, 1000);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(BODY_HEIGHT, MAX_HEIGHT);
        this.entityData.define(CURRENT_TARGET_ID, 0);
        this.entityData.define(HEAD_TARGET, new Vector3f(0.0F, 0.0F, 0.0F));
        this.entityData.define(IN_BITE_RANGE, false);
    }

    public float getBodyHeight() {
        return this.entityData.get(BODY_HEIGHT);
    }
    public void setBodyHeight(float value) {
        this.entityData.set(BODY_HEIGHT, value);
    }

    public int getCurrentTargetID() {
        return this.entityData.get(CURRENT_TARGET_ID);
    }
    public void setCurrentTargetId(int value) {
        this.entityData.set(CURRENT_TARGET_ID, value);
    }

    public Vec3 getHeadTarget() {
        return new Vec3(entityData.get(HEAD_TARGET));
    }
    public void setHeadTarget(Vec3 pos) {
        entityData.set(HEAD_TARGET, pos.toVector3f());
    }

    public boolean getBiting() {
        return this.entityData.get(IN_BITE_RANGE);
    }
    public void setBiting(boolean value) {
        this.entityData.set(IN_BITE_RANGE, value);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new CreatureMeleeAttackGoal(this, 1.0D));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 1.0F));

        this.targetSelector.addGoal(0, new NearestAttackableTargetGoal<>(this, LivingEntity.class, true, this::targetPredicate));
    }

    private boolean targetPredicate(LivingEntity livingEntity) {
        return livingEntity.getType().is(VDEntityTypeTags.SAPIENT) && behaviour != Behaviour.HIDE;
    }

    private float verticalOffset = 0.0F;
    private float oldVerticalOffset;
    public float verticalOffset(float partialTick) {
        return Mth.lerp(partialTick, oldVerticalOffset, verticalOffset);
    }

    private Vec3 headPos = Vec3.ZERO;

    public float headRotate;
    public float oldHeadRotate;

    private int harmlessTime;

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide()) {
            this.oldVerticalOffset = verticalOffset;

            oldNeck1Pitch = neck.segments.get(2).pitch;
            oldNeck2Pitch = neck.segments.get(1).pitch;
            oldHeadPitch = neck.segments.get(0).pitch;

            oldNeck1Yaw = neck.segments.get(2).yaw;
            oldNeck2Yaw = neck.segments.get(1).yaw;
            oldHeadYaw = neck.segments.get(0).yaw;

            if (shouldDisappear()) {
                globalActuality -= 0.05F;
            } else {
                globalActuality += 0.05F;
            }
            globalActuality = Mth.clamp(globalActuality, 0.0F, 1.0F);

            ClientOnlyEntityMethods.tickSubjectiveActuality(this);

            if (getSubjectiveActuality() > 0.0F) {
                tickProcAnims();
            } else if (getRandom().nextInt(300) == 0) {
                level().addParticle(ParticleRegistry.INVOCATION_ORB.get(), getRandomX(0.5D), getRandomY(), getRandomZ(0.5D), 0.0D, 0.0D, 0.0D);
            }
        } else {
            if (harmlessTime > 0) {
                harmlessTime--;
            }
            if (getTarget() != null) {
                if (getCurrentTargetID() != getTarget().getId()) {
                    setCurrentTargetId(getTarget().getId());
                    onSpotNewTarget();
                }
                setHeadTarget(getTarget().getEyePosition());
                setBiting(getTarget().distanceToSqr(this) < 36 && harmlessTime == 0);
                if (getTarget() instanceof VDPlayer player) {
                    player.vd$deprive();
                }
            } else {
                setHeadTarget(new Vec3(0.0D, getBodyHeight(), 3.0D).yRot(-this.yBodyRot * Mth.DEG_TO_RAD).add(this.position()));
                setBiting(false);
            }
            tickHeight();
            if (ErosionGasConfig.INSTANCE.enabled.get()) {
                tickErosion();
            }

            if (getRandom().nextInt(60) == 0) {
                BlockPos.betweenClosedStream(getBoundingBox().inflate(8.0D)).forEach(pos -> {
                    BlockState state = level().getBlockState(pos);
                    if (state.getLightEmission() > 0 &&
                            state.getDestroySpeed(level(), pos) >= 0 &&
                            state.getDestroySpeed(level(), pos) < 3.0F) {
                        level().setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
                    }
                });
            }

            tickAI();
        }
    }

    private void slow(int time) {
        this.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, time, 6, false, false));
    }

    private int changeTimer = 0;

    private void tickAI() {
        changeTimer++;
        if (changeTimer > 200 && getRandom().nextInt(200) == 0) {
            randomChangeBehaviour();
            changeTimer = 0;
        }

        if (behaviour == Behaviour.STARE) {
            slow(3);
        } else if (behaviour == Behaviour.HIDE || behaviour == Behaviour.NONE) {
            setCurrentTargetId(0);
        }
    }

    private void onSpotNewTarget() {
        harmlessTime = 20;
        slow(harmlessTime);

        if (getRandom().nextInt(3) == 0) {
            behaviour = Behaviour.STARE;
        } else {
            behaviour = Behaviour.CHASE;
        }
        changeTimer = 0;
    }

    private void randomChangeBehaviour() {
        if (getTarget() == null) {
            behaviour = Behaviour.NONE;
        }
        else if (behaviour == Behaviour.STARE) {
            if (getRandom().nextInt(3) == 0) {
                behaviour = Behaviour.HIDE;
            } else {
                behaviour = Behaviour.CHASE;
            }
        } else if (behaviour == Behaviour.CHASE) {
            if (getRandom().nextInt(7) == 0) {
                behaviour = Behaviour.HIDE;
            }
        }
    }

    private boolean shouldDisappear() {
        return getBodyHeight() < 1.0F || isInWater();
    }

    private static final int EROSION_RANGE = 3;
    private static final int EROSION_CHANCE = 600;

    private void tickErosion() {
        if (getRandom().nextInt(EROSION_CHANCE) == 0) {
            BlockPos destroyPos = blockPosition().offset(
                    getRandom().nextInt(EROSION_RANGE * 2 + 1) - EROSION_RANGE,
                    getRandom().nextInt(EROSION_RANGE * 2 + 1) - EROSION_RANGE,
                    getRandom().nextInt(EROSION_RANGE * 2 + 1) - EROSION_RANGE
            );

            level().setBlockAndUpdate(destroyPos, BlockRegistry.EROSION_GAS.get().defaultBlockState());
        }
    }

    @Override
    public boolean isPushedByFluid() {
        return false;
    }

    private void tickProcAnims() {
        feetAnimator.tick(this, 0.3F * getFootDistMult1(), level());

        frontRightShoulder = new Vec3(-0.625, 1.6875 + verticalOffset, 0.375).yRot(-this.yBodyRot * Mth.DEG_TO_RAD).add(this.position());
        backRightShoulder = new Vec3(-0.4375F, 1.5F + verticalOffset, -0.875).yRot(-this.yBodyRot * Mth.DEG_TO_RAD).add(this.position());
        frontLeftShoulder = new Vec3(0.625, 1.6875 + verticalOffset, 0.375).yRot(-this.yBodyRot * Mth.DEG_TO_RAD).add(this.position());
        backLeftShoulder = new Vec3(0.4375F, 1.5F + verticalOffset, -0.875).yRot(-this.yBodyRot * Mth.DEG_TO_RAD).add(this.position());

        frontRight.tick(frontRightShoulder, feetAnimator.feet[0].position());
        backRight.tick(backRightShoulder, feetAnimator.feet[1].position());
        frontLeft.tick(frontLeftShoulder, feetAnimator.feet[2].position());
        backLeft.tick(backLeftShoulder, feetAnimator.feet[3].position());

        this.verticalOffset = getBodyHeight() - MAX_HEIGHT + Mth.sin(level().getGameTime() / 20.0F) * getBodyHeight() / 12;

        Vec3 neckStart = new Vec3(0.0D, 1.75D + verticalOffset, 0.6875D).yRot(-this.yBodyRot * Mth.DEG_TO_RAD).add(this.position());
        Vec3 headTarget = getHeadTarget();

        headPos = neck.updatePosition(Maths.lerpVec3(0.15F, headPos, headTarget), headTarget, neckStart, 45, getBiting());

        oldHeadRotate = headRotate;
        if (getBiting() && headRotate < 1.0F) {
            headRotate = Math.min(headRotate + 0.1F, 1.0F);
        } else if (!getBiting() && headRotate > 0.0F) {
            headRotate = Math.max(headRotate - 0.1F, 0.0F);
        }
    }

    private float oldNeck1Pitch;
    public float lerpedNeck1Pitch(float partialTick) {
        return Mth.rotLerp(partialTick, oldNeck1Pitch, neck.segments.get(2).pitch) * Mth.DEG_TO_RAD;
    }
    private float oldNeck1Yaw;
    public float lerpedNeck1Yaw(float partialTick) {
        return Mth.rotLerp(partialTick, oldNeck1Yaw, neck.segments.get(2).yaw) * Mth.DEG_TO_RAD;
    }


    private float oldNeck2Pitch;
    public float lerpedNeck2Pitch(float partialTick) {
        return Mth.rotLerp(partialTick, oldNeck2Pitch, neck.segments.get(1).pitch) * Mth.DEG_TO_RAD;
    }
    private float oldNeck2Yaw;
    public float lerpedNeck2Yaw(float partialTick) {
        return Mth.rotLerp(partialTick, oldNeck2Yaw, neck.segments.get(1).yaw) * Mth.DEG_TO_RAD;
    }


    private float oldHeadPitch;
    public float lerpedHeadPitch(float partialTick) {
        return Mth.rotLerp(partialTick, oldHeadPitch, neck.segments.get(0).pitch) * Mth.DEG_TO_RAD;
    }
    private float oldHeadYaw;
    public float lerpedHeadYaw(float partialTick) {
        return Mth.rotLerp(partialTick, oldHeadYaw, neck.segments.get(0).yaw) * Mth.DEG_TO_RAD;
    }

    public float subjectiveActuality;

    public float getSubjectiveActuality() {
        return Math.min(this.subjectiveActuality, this.globalActuality);
    }

    @Override
    public @NotNull AABB getBoundingBoxForCulling() {
        return this.getBoundingBox().inflate(2.0F);
    }

    private void tickHeight() {
        BlockHitResult hitResult = level().clip(new ClipContext(this.position(), this.position().add(0.0D, 3.0D, 0.0D), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this));
        float f = (float) (hitResult.getLocation().y - this.position().y - 0.3F);
        float desiredHeight = Math.min(f, MAX_HEIGHT);
        this.setBodyHeight(Mth.lerp(0.1F, this.getBodyHeight(), desiredHeight));
        if (desiredHeight < 1.0F) {
            this.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 3, 2, false, false));
        }
    }

    @Override
    public boolean isInvulnerableTo(@NotNull DamageSource damageSource) {
        return true;
    }

    @Override
    protected void actuallyHurt(@NotNull DamageSource damageSource, float damage) {
        damage = 0;
        super.actuallyHurt(damageSource, damage);
    }

    @Override
    public boolean doHurtTarget(@NotNull Entity entity) {
        if (harmlessTime > 0) {
            return false;
        } else {
            entity.kill();
            return true;
        }
    }

    @Override
    protected float getSoundVolume() {
        return 0.0F;
    }

    @Override
    protected void playStepSound(@NotNull BlockPos $$0, @NotNull BlockState $$1) {
    }

    @Override
    protected boolean canRide(@NotNull Entity $$0) {
        return false;
    }

    @Override
    public float getWalkTargetValue(BlockPos pos, LevelReader reader) {
        return 0.0F;
    }

    @Override
    public float maxClampDist() {
        return 2.0F;
    }

    @Override
    public float minClampDist() {
        return 0.5F;
    }

    private enum Behaviour {
        STARE,
        CHASE,
        HIDE,
        NONE
    }
}
