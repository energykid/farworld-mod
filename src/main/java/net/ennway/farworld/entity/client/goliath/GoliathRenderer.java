package net.ennway.farworld.entity.client.goliath;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.ennway.farworld.Farworld;
import net.ennway.farworld.entity.client.bloomed.BloomedEyesLayer;
import net.ennway.farworld.entity.client.bloomed.BloomedFlowerLayer;
import net.ennway.farworld.entity.client.bloomed.BloomedModel;
import net.ennway.farworld.entity.custom.BloomedEntity;
import net.ennway.farworld.entity.custom.GoliathEntity;
import net.ennway.farworld.registries.ModParticles;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class GoliathRenderer extends MobRenderer<GoliathEntity, GoliathModel<GoliathEntity>> {

    public GoliathRenderer(EntityRendererProvider.Context context) {
        super(context, new GoliathModel<>(context.bakeLayer(GoliathModel.LAYER_LOCATION)), 0.4f);
        this.addLayer(new GoliathGlowLayer<>(this));
        this.addLayer(new GoliathSaddleLayer<>(this));
    }

    @Override
    public ResourceLocation getTextureLocation(GoliathEntity entity) {
        if (this.getModel().isPet) return ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "textures/entity/goliath_pet.png");
        return ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "textures/entity/goliath.png");
    }

    @Override
    protected float getShadowRadius(GoliathEntity entity) {
        return 0f;
    }

    float angleX = 0f;
    float angleZ = 0f;

    @Override
    public void render(GoliathEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        if (entity.isBaby())
        {
            poseStack.scale(0.5f, 0.5f, 0.5f);
        }

        Vec3[] vec3s = new Vec3[4];

        for (int i = 0; i < vec3s.length; i++) {
            vec3s[i] = nearestSolidVerticalSpot(entity.level(), getHorizontalOffsetPos(entity, (float)Math.toRadians((i * 90f)), 2f, partialTicks)).add(0, 1, 0);
        }

        Vector3f front = vec3s[0].toVector3f();
        Vector3f right = vec3s[1].toVector3f();
        Vector3f back = vec3s[2].toVector3f();
        Vector3f left = vec3s[3].toVector3f();

        angleX = Mth.lerp(0.05f, angleX, left.y - right.y);
        angleZ = Mth.lerp(0.05f, angleZ, front.y - back.y);

        poseStack.mulPose(Axis.XP.rotationDegrees(angleX * 3));
        poseStack.mulPose(Axis.ZP.rotationDegrees(angleZ * 3));

        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    public Vector3f getHorizontalOffsetPos(GoliathEntity entity, double rot, float dist, float partialTicks)
    {
        Vector2f v2 = rotateVector2f(new Vector2f(dist, 0), rot);

        return (entity.position().toVector3f().add(new Vector3f(v2.x(), 0, v2.y())));
    }

    public Vector2f rotateVector2f(Vector2f vec2f, double n)
    {
        float rx = (float)((vec2f.x * Math.cos(n)) - (vec2f.y * Math.sin(n)));
        float ry = (float)((vec2f.x * Math.sin(n)) + (vec2f.y * Math.cos(n)));
        return new Vector2f(rx, ry);
    }

    public Vec3 nearestSolidVerticalSpot(Level lvl, Vector3f pos)
    {
        double yy = pos.y;

        AABB box = new AABB(pos.x - 0.2, pos.y - 0.2, pos.z - 0.2, pos.x + 0.2, pos.y, pos.z + 0.2);
        for (int i = 0; i < 20; i++)
        {
            if (lvl.noCollision(box))
            {
                box.move(0, -0.25, 0);
                yy -= 0.25;
            }
            else break;
        }

        return new Vec3(pos.x, yy, pos.z);
    }
}
