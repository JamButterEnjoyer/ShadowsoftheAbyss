package mod.acats.vitaldeprivation.scaling;

import mod.acats.vitaldeprivation.config.SpawningConfig;
import net.minecraft.world.level.Level;

public class Scaling {
    private final Level level;

    private Scaling(Level level) {
        this.level = level;
    }

    public static Scaling of(Level level) {
        return new Scaling(level);
    }

    private long timeInMinutes() {
        return (level.getDayTime() / 20) / 60;
    }

    private float timeInDays() {
        return timeInMinutes() / 20.0F;
    }

    public int creaturesPerInvocation() {
        return (int) Math.min((timeInMinutes() + 45) / 60, 6);
    }

    public boolean intelligentDeathsInvokeCreatures() {
        return true;
    }

    public float spawnPotential() {
        return (float) timeInMinutes() / (float) SpawningConfig.INSTANCE.timeUntilMaxSpawns.get();
    }

    public boolean canSpawnInDaylight() {
        return timeInDays() > 3.0F;
    }
}
