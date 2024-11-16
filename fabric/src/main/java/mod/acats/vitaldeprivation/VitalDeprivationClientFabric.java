package mod.acats.vitaldeprivation;

import mod.acats.fromanotherlibrary.registry.client.ClientRegistryFabric;
import net.fabricmc.api.ClientModInitializer;

public class VitalDeprivationClientFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientRegistryFabric.registerClient(new VitalDeprivation());
    }
}
