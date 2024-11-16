package mod.acats.vitaldeprivation;

import mod.acats.fromanotherlibrary.event.client.ClientLevelTick;
import mod.acats.fromanotherlibrary.event.client.shader.SetShaderUniforms;
import mod.acats.fromanotherlibrary.registry.client.ClientMod;
import mod.acats.fromanotherlibrary.registry.client.EntityRendererEntry;
import mod.acats.fromanotherlibrary.registry.client.ParticleClientEntry;
import mod.acats.vitaldeprivation.client.shader.DarknessController;
import mod.acats.vitaldeprivation.particle.InvocationOrbParticle;
import mod.acats.vitaldeprivation.registry.ParticleRegistry;
import mod.acats.vitaldeprivation.registry.client.ClientEntityRegistry;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public class VitalDeprivationClient implements ClientMod {

    @Override
    public Optional<Iterable<EntityRendererEntry<?>>> getEntityRendererEntries() {
        return Optional.of(ClientEntityRegistry.registerEntityRenderers());
    }

    @Override
    public Optional<HashMap<ModelLayerLocation, Supplier<LayerDefinition>>> getModelLayerRegister() {
        return Optional.of(ClientEntityRegistry.registerModelLayers());
    }

    @Override
    public Optional<Iterable<ParticleClientEntry<?>>> getParticleClientEntries() {
        return Optional.of(List.of(
                new ParticleClientEntry<>(ParticleRegistry.INVOCATION_ORB.get(), InvocationOrbParticle.Provider::new)
        ));
    }

    @Override
    public void setupClient() {
        ClientLevelTick.EVENT.add(DarknessController::tick);
        SetShaderUniforms.EVENT.add(DarknessController::setUniforms);
    }
}
