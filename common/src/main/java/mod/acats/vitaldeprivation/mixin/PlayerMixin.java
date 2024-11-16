package mod.acats.vitaldeprivation.mixin;

import mod.acats.vitaldeprivation.interfaces.VDPlayer;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity implements VDPlayer {
    private PlayerMixin(EntityType<? extends LivingEntity> $$0, Level $$1) {
        super($$0, $$1);
    }
    @Unique
    private static final EntityDataAccessor<Float> VITAL_DEPRIVATION = SynchedEntityData.defineId(Player.class, EntityDataSerializers.FLOAT);

    @Inject(method = "defineSynchedData", at = @At("TAIL"))
    private void vd$defineSynchedData(CallbackInfo ci) {
        this.entityData.define(VITAL_DEPRIVATION, 0.0F);
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void vd$tick(CallbackInfo ci) {
        if (this.level().isClientSide()) {
            return;
        }
        if (vd$deprivationTime > 0) {
            vd$deprivationTime--;
            if (vd$getDeprivation() < 1.0F) {
                vd$setDeprivation(Math.min(vd$getDeprivation() + 0.0006F, 1.0F));
            } else {
                kill();
                vd$deprivationTime = 0;
                vd$setDeprivation(0.0F);
            }
        } else {
            if (vd$getDeprivation() > 0.0F) {
                vd$setDeprivation(Math.max(vd$getDeprivation() - 0.01F, 0.0F));
            }
        }
    }

    @Override
    public void vd$setDeprivation(float value) {
        this.entityData.set(VITAL_DEPRIVATION, value);
    }

    @Override
    public float vd$getDeprivation() {
        return this.entityData.get(VITAL_DEPRIVATION);
    }

    @Unique
    private int vd$deprivationTime = 0;
    @Override
    public void vd$deprive() {
        vd$deprivationTime = 5;
    }
}
