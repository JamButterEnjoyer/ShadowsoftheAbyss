package mod.acats.vitaldeprivation;

import net.minecraftforge.fml.common.Mod;

@Mod(VitalDeprivation.MOD_ID)
public class VitalDeprivationForge {
    
    public VitalDeprivationForge() {
        new VitalDeprivation().init();
    }
}