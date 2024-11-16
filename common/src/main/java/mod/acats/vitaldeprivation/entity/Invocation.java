package mod.acats.vitaldeprivation.entity;

import mod.acats.vitaldeprivation.interfaces.VDPlayer;
import mod.acats.vitaldeprivation.registry.EntityRegistry;
import mod.acats.vitaldeprivation.registry.ParticleRegistry;
import mod.acats.vitaldeprivation.scaling.Scaling;
import mod.acats.vitaldeprivation.tags.VDBiomeTags;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import org.lwjgl.system.NonnullDefault;

@NonnullDefault
public class Invocation extends Entity {
    public Invocation(EntityType<?> type, Level level) {
        super(type, level);
        counter = 0;
    }

    public static void create(Level level, Vec3 location) {

        if (!level.getBiome(BlockPos.containing(location)).is(VDBiomeTags.INVOCATION_ALLOWED)) {
            return;
        }

        Invocation invocation = EntityRegistry.INVOCATION.get().create(level);
        if (invocation != null) {
            invocation.setPos(location.subtract(0.0D, 8.0D, 0.0D));
            level.addFreshEntity(invocation);
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (level().isClientSide()) {
            clientTick();
        } else {
            serverTick();
        }
    }

    private void clientTick() {
        level().addParticle(ParticleRegistry.INVOCATION_ORB.get(), getRandomX(0.5D), getRandomY(), getRandomZ(0.5D), 0.0D, 0.0D, 0.0D);
    }

    private int counter;

    private void serverTick() {
        counter++;
        if (counter % 3 == 0) {
            for (Player player : level().players()) {
                if (getBoundingBox().contains(player.getEyePosition())) {
                    ((VDPlayer) player).vd$deprive();
                }
            }
            if (counter > 600) {
                int creatures = Scaling.of(level()).creaturesPerInvocation();
                for (int i = 0; i < creatures; i++) {
                    invokeCreature();
                }
                discard();
            }
        }
    }

    private void invokeCreature() {
        Creature invoked = EntityRegistry.CREATURE.get().create(level());
        if (invoked != null) {

            int attempts = 0;

            while (attempts < 100) {
                attempts++;
                invoked.setPos(new Vec3(getRandomX(0.5D), getRandomY(), getRandomZ(0.5D)));
                if (level().noCollision(invoked)) {
                    break;
                }
            }

            if (attempts == 100) {
                invoked.setPos(position().add(0.0D, 8.0D, 0.0D));
            }

            invoked.setPos(level().clip(new ClipContext(invoked.position(), invoked.position().subtract(0.0D, 16.0D, 0.0D), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, invoked)).getLocation());

            ServerLevelAccessor serverLevelAccessor = (ServerLevelAccessor) level();
            invoked.finalizeSpawn(
                    serverLevelAccessor,
                    serverLevelAccessor.getCurrentDifficultyAt(blockPosition()),
                    MobSpawnType.NATURAL,
                    null,
                    null
            );
            level().addFreshEntity(invoked);
        }
    }

    @Override
    protected void defineSynchedData() {
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag var1) {
        counter = var1.getInt("InvocationCounter");
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag var1) {
        var1.putInt("InvocationCounter", counter);
    }

    @Override
    public boolean isInvisible() {
        return true;
    }
}
