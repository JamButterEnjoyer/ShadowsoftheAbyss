package mod.acats.vitaldeprivation.client.animation.ik;

import mod.acats.fromanotherlibrary.FromAnotherLibrary;
import mod.acats.fromanotherlibrary.utilities.Maths;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class InverseKinematics {
    public static void printImportantInfo(ModelPart root, String... path) {
        String message = "IK INFO:";
        for (String s:
             path) {
            root = root.getChild(s);
            float dist = (float) Math.sqrt(root.x * root.x + root.y * root.y + root.z * root.z);
            message = String.join("\n", message,
                    "-" + s + ":\n"
                    + "--Distance from previous part: " + dist);
        }
        FromAnotherLibrary.LOGGER.info(message);
    }

    public static void testParticleLine(Level level, Vec3 pos1, Vec3 pos2) {
        float dist = (float) pos1.distanceTo(pos2);
        for (float f = 0; f < dist; f += dist / 20.0F) {
            Vec3 bubblePos = Maths.lerpVec3(f / dist, pos1, pos2);
            level.addParticle(ParticleTypes.BUBBLE, bubblePos.x, bubblePos.y, bubblePos.z, 0.0D, 0.0D, 0.0D);
        }
    }
}
