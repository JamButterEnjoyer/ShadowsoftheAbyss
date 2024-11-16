package mod.acats.vitaldeprivation;

import net.fabricmc.api.ModInitializer;

public class VitalDeprivationFabric implements ModInitializer {
    
    @Override
    public void onInitialize() {
        new VitalDeprivation().init();
    }
}
