package net.ennway.farworld.entity.client;


import com.mojang.blaze3d.vertex.PoseStack;
import net.ennway.farworld.Farworld;
import net.ennway.farworld.event.ExtraModelEvent;
import net.ennway.farworld.utils.AccessoryUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.List;
import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class PlayerAccessoryLayer<T extends Player, M extends HumanoidModel<T>, A extends HumanoidModel<T>> extends HumanoidArmorLayer<T, M, A> {
    private static final ResourceLocation OUTER_LAYER_LOC = ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "textures/models/armor/goggles.png");
    private static final ResourceLocation DROWNED_OUTER_LAYER_LOCATION = ResourceLocation.withDefaultNamespace("textures/entity/zombie/drowned_outer_layer.png");

    private final HumanoidModel<T> model;

    public PlayerAccessoryLayer(RenderLayerParent<T, M> renderer, A innerModel, A outerModel, ModelManager modelManager) {
        super(renderer, innerModel, outerModel, modelManager);
        MeshDefinition def = HumanoidModel.createMesh(new CubeDeformation(1.05f, 1.05f, 1.05f), 0f);
        PartDefinition root = def.getRoot();
        model = new HumanoidModel<>(root.bake(64, 64));
        innerModel.copyPropertiesTo(model);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, T livingEntity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {

        poseStack.pushPose();

        this.getParentModel().copyPropertiesTo(model);

        if (livingEntity instanceof Player plr)
        {
            for (ItemStack st : AccessoryUtils.getPlayerAccessoryStacks(plr))
            {
                ResourceLocation loc = ResourceLocation.fromNamespaceAndPath(Farworld.MOD_ID, "textures/models/armor/"+ ExtraModelEvent.getExtraModelName(st.getItem()) +".png");

                if (Minecraft.getInstance().getResourceManager().getResource(loc).isPresent()) {
                    renderColoredCutoutModel(
                            model,
                            loc,
                            poseStack,
                            buffer,
                            packedLight,
                            livingEntity,
                            16777215
                    );
                }
            }
        }

        poseStack.popPose();
    }
}

