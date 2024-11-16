package mod.acats.vitaldeprivation.registry;

import mod.acats.fromanotherlibrary.registry.FALRegister;
import mod.acats.fromanotherlibrary.registry.FALRegistryObject;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;

public class ParticleRegistry {
    public static final FALRegister<ParticleType<?>> PARTICLE_REGISTRY = new FALRegister<>();
    public static final FALRegistryObject<SimpleParticleType> INVOCATION_ORB = PARTICLE_REGISTRY.register("invocation_orb", () -> new SimpleParticleType(true) {});
}
