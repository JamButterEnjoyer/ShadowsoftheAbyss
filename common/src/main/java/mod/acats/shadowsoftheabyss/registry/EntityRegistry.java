package mod.acats.vitaldeprivation.registry;

import mod.acats.fromanotherlibrary.registry.FALRegister;
import mod.acats.fromanotherlibrary.registry.FALRegistryObject;
import mod.acats.vitaldeprivation.entity.Creature;
import mod.acats.vitaldeprivation.entity.Invocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;

import java.util.HashMap;
import java.util.function.Supplier;

public class EntityRegistry {
    public static final FALRegister<EntityType<?>> ENTITY_REGISTER = new FALRegister<>();

    public static final FALRegistryObject<EntityType<Creature>> CREATURE = ENTITY_REGISTER.register(
            "creature",
            () -> EntityType.Builder.of(Creature::new, MobCategory.MONSTER).sized(0.5F, 0.5F).fireImmune().build("creature")
    );

    public static final FALRegistryObject<EntityType<Invocation>> INVOCATION = ENTITY_REGISTER.register(
            "invocation",
            () -> EntityType.Builder.of(Invocation::new, MobCategory.MONSTER).sized(16.0F, 16.0F).fireImmune().build("invocation")
    );

    public static HashMap<EntityType<? extends LivingEntity>, Supplier<AttributeSupplier.Builder>> getAttributes() {
        HashMap<EntityType<? extends LivingEntity>, Supplier<AttributeSupplier.Builder>> map = new HashMap<>();
        map.put(CREATURE.get(), Creature::createCreatureAttributes);
        return map;
    }
}
