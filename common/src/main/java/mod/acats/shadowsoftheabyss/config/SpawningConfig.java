package mod.acats.vitaldeprivation.config;

import mod.acats.fromanotherlibrary.config.v2.ModConfig;
import mod.acats.fromanotherlibrary.config.v2.properties.IntegerProperty;

public class SpawningConfig extends ModConfig {
    public static final SpawningConfig INSTANCE = new SpawningConfig();

    @Override
    public String name() {
        return "spawning";
    }

    @Override
    protected int version() {
        return 0;
    }

    public final IntegerProperty timeUntilMaxSpawns = addProperty(new IntegerProperty(
            "time_until_max_spawns",
            "The time (in minutes) after starting a new world until the maximum creature spawn rate is reached.\n" +
                    "Scales linearly, for example at half way to the max spawn time, creatures will spawn at half of their maximum rate.",
            1200,
            false
    ));

    public final IntegerProperty maxSpawns = addProperty(new IntegerProperty(
            "max_spawns",
            "The maximum creature spawn weight.",
            200,
            true
    ));
}
