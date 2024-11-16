package mod.acats.vitaldeprivation.entity.client;

import mod.acats.vitaldeprivation.client.shader.DarknessController;
import mod.acats.vitaldeprivation.entity.Creature;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;

public class ClientOnlyEntityMethods {
    public static void tickSubjectiveActuality(Creature creature) {
        Player player = Minecraft.getInstance().player;
        if (player == null) {
            creature.subjectiveActuality = 1.0F;
        } else {
            if (player.isCreative() || player.isSpectator()) {
                creature.subjectiveActuality = 1.0F;
            } else if (creature.getCurrentTargetID() == player.getId()) {
                creature.subjectiveActuality = Math.min(creature.subjectiveActuality + 0.025F, 1.0F);
            } else {
                creature.subjectiveActuality = Math.max(creature.subjectiveActuality - 0.025F, 0.0F);
            }
        }
    }
}
