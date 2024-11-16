package mod.acats.vitaldeprivation.compat;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import mod.acats.fromanotherlibrary.client.screen.ConfigListScreen;
import mod.acats.vitaldeprivation.VitalDeprivation;

public class VDModMenu implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return prev -> new ConfigListScreen(VitalDeprivation.MOD_ID, new VitalDeprivation().getConfigs().orElseThrow(), prev);
    }
}
