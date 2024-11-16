package mod.acats.vitaldeprivation.registry.client;

import mod.acats.fromanotherlibrary.registry.client.EntityRendererEntry;
import mod.acats.vitaldeprivation.entity.client.model.CreatureModel;
import mod.acats.vitaldeprivation.entity.client.renderer.CreatureRenderer;
import mod.acats.vitaldeprivation.registry.EntityRegistry;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.entity.NoopRenderer;

import java.util.HashMap;
import java.util.List;
import java.util.function.Supplier;

public class ClientEntityRegistry {
    public static Iterable<EntityRendererEntry<?>> registerEntityRenderers() {
        return List.of(
                new EntityRendererEntry<>(EntityRegistry.CREATURE.get(), CreatureRenderer::new),
                new EntityRendererEntry<>(EntityRegistry.INVOCATION.get(), NoopRenderer::new)
        );
    }

    public static HashMap<ModelLayerLocation, Supplier<LayerDefinition>> registerModelLayers() {
        HashMap<ModelLayerLocation, Supplier<LayerDefinition>> map = new HashMap<>();

        map.put(CreatureModel.LAYER_LOCATION, CreatureModel::createBodyLayer);

        return map;
    }
}
