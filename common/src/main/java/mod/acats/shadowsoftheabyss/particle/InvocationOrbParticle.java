package mod.acats.vitaldeprivation.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.system.NonnullDefault;

@NonnullDefault
public class InvocationOrbParticle extends TextureSheetParticle {
    public InvocationOrbParticle(ClientLevel level,
                                 double x,
                                 double y,
                                 double z,
                                 SpriteSet spriteSet,
                                 double xd,
                                 double yd,
                                 double zd) {
        super(level, x, y, z, xd, yd, zd);

        this.friction = 0.8F;
        this.xd = xd;
        this.yd = yd;
        this.zd = zd;
        this.lifetime = 40;
        this.setSpriteFromAge(spriteSet);
        this.rCol = 1.0F;
        this.gCol = 1.0F;
        this.bCol = 1.0F;
        this.scale(3.0F + level.getRandom().nextFloat() * 2.0F);
    }

    @Override
    public void tick() {
        xd = rand();
        yd = rand();
        zd = rand();
        super.tick();
    }

    private double rand() {
        return (level.getRandom().nextDouble() - 0.5D) * 0.25D;
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Override
    protected int getLightColor(float $$0) {
        return 255;
    }

    public static class Provider implements ParticleProvider<SimpleParticleType> {

        private final SpriteSet spriteSet;

        public Provider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Nullable
        @Override
        public Particle createParticle(SimpleParticleType var1, ClientLevel var2, double var3, double var5, double var7, double var9, double var11, double var13) {
            return new InvocationOrbParticle(var2, var3, var5, var7, spriteSet, var9, var11, var13);
        }
    }
}
