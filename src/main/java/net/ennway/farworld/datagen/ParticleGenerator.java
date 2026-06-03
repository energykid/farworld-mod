package net.ennway.farworld.datagen;

import com.google.common.base.Preconditions;
import net.ennway.farworld.Farworld;
import net.ennway.farworld.registries.ModParticles;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.ParticleDescriptionProvider;

import java.util.Iterator;

public class ParticleGenerator extends ParticleDescriptionProvider {
    protected ParticleGenerator(PackOutput output, ExistingFileHelper fileHelper) {
        super(output, fileHelper);
    }

    @Override
    protected void addDescriptions() {
        mySpriteSet(ModParticles.SLIME_STREAKS.get(), ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "gloom_shockwave/gloom_shockwave"), 4, false);
    }

    protected void mySpriteSet(ParticleType<?> type, ResourceLocation baseName, int numOfTextures, boolean reverse) {
        Preconditions.checkArgument(numOfTextures > 0, "The number of textures to generate must be positive");
        this.spriteSet(type, () -> {
            return new Iterator<ResourceLocation>() {
                private int counter = 1;

                public boolean hasNext() {
                    return this.counter < numOfTextures;
                }

                public ResourceLocation next() {
                    int var10001 = reverse ? numOfTextures - this.counter - 1 : this.counter;
                    ResourceLocation texture = baseName.withSuffix(Integer.toString(var10001));
                    ++this.counter;
                    return texture;
                }
            };
        });
    }
}
