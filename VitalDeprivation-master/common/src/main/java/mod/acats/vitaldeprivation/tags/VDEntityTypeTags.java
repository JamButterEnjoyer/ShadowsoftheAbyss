package mod.acats.vitaldeprivation.tags;

import mod.acats.vitaldeprivation.VitalDeprivation;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;

public class VDEntityTypeTags {
    public static final TagKey<EntityType<?>> SAPIENT = entityTag("sapient");

    private static TagKey<EntityType<?>> entityTag(String id) {
        return TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation(VitalDeprivation.MOD_ID, id));
    }
}
