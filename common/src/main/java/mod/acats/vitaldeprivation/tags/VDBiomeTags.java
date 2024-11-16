package mod.acats.vitaldeprivation.tags;

import mod.acats.vitaldeprivation.VitalDeprivation;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

public class VDBiomeTags {
    public static final TagKey<Biome> NORMAL_CREATURE_SPAWNING = biomeTag("normal_creature_spawning");
    public static final TagKey<Biome> EXTREME_CREATURE_SPAWNING = biomeTag("extreme_creature_spawning");
    public static final TagKey<Biome> INVOCATION_ALLOWED = biomeTag("invocation_allowed");
    public static final TagKey<Biome> CREATURE_SPAWNING = biomeTag("creature_spawning");

    private static TagKey<Biome> biomeTag(String id) {
        return TagKey.create(Registries.BIOME, new ResourceLocation(VitalDeprivation.MOD_ID, id));
    }
}
