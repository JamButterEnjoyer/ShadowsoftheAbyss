package mod.acats.vitaldeprivation.config;

import mod.acats.fromanotherlibrary.config.v2.ModConfig;
import mod.acats.fromanotherlibrary.config.v2.properties.BooleanProperty;

public class ErosionGasConfig extends ModConfig {

    public static final ErosionGasConfig INSTANCE = new ErosionGasConfig();

    @Override
    public String name() {
        return "erosion_gas";
    }

    @Override
    protected int version() {
        return 0;
    }

    public final BooleanProperty enabled = addProperty(new BooleanProperty(
            "enabled",
            """
                    Should creatures naturally place erosion gas?
                    If disabled, existing erosion gas will gradually decay instead of expanding.
                    Disabled by default, this was originally meant to be a major feature but I couldn't get it to work well :(
                    """,
            false,
            false
    ));
}
