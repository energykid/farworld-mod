package net.ennway.farworld.particle.base;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.ennway.farworld.utils.QuaternionUtils;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.checkerframework.checker.units.qual.Angle;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class FlatParticle extends TextureSheetParticle {
	protected SpriteSet sprites;
	public Quaternionf QUATERNION = new Quaternionf(0F, -0.7F, 0.7F, 0F);

	public float rot = 0;

	protected FlatParticle(ClientLevel world, double x, double y, double z, SpriteSet sprites) {
		super(world, x, y, z, 0.0, 0.0, 0.0);
		this.quadSize = 1;
		this.setParticleSpeed(0D, 0D, 0D);
		this.lifetime = 20;
		this.sprites = sprites;
		this.setSpriteFromAge(sprites);
	}

	@Override
	public void render(VertexConsumer buffer, Camera camera, float ticks) {
		Vec3 vec3 = camera.getPosition();
		float x = (float) (Mth.lerp(ticks, this.xo, this.x) - vec3.x());
		float y = (float) (Mth.lerp(ticks, this.yo, this.y) - vec3.y());
		float z = (float) (Mth.lerp(ticks, this.zo, this.z) - vec3.z());
		Vector3f[] vector3fs = new Vector3f[]{new Vector3f(-1.0F, -1.0F, 0.0F), new Vector3f(-1.0F, 1.0F, 0.0F), new Vector3f(1.0F, 1.0F, 0.0F), new Vector3f(1.0F, -1.0F, 0.0F)};
		Vector3f[] vector3fsBottom = new Vector3f[]{new Vector3f(-1.0F, -1.0F, 0.0F), new Vector3f(1.0F, -1.0F, 0.0F), new Vector3f(1.0F, -1.0F, 0.0F), new Vector3f(-1.0F, -1.0F, 0.0F)};
		float f4 = this.getQuadSize(ticks);
		for (int i = 0; i < 4; ++i) {

			float rotation_angle = this.roll;
			Vector3f rotation_axis = new Vector3f(0.0f, 0.0f, 1.0f);

			Quaternionf quaternionf = QuaternionUtils.flatQuaternion().rotateLocalY(this.roll);

			double half_angle_rad = Math.toRadians(rotation_angle / 2.0);
			double rot_w = Math.cos(half_angle_rad);
			double rot_x = rotation_axis.x * Math.sin(half_angle_rad);
			double rot_y = rotation_axis.y * Math.sin(half_angle_rad);
			double rot_z = rotation_axis.z * Math.sin(half_angle_rad);

			Quaternionf qu2 = new Quaternionf(rot_x, rot_y, rot_z, rot_w);
			Vector3f vector3f = vector3fs[i];
			vector3f.rotate(quaternionf);
			vector3f.mul(f4);
			vector3f.add(x, y, z);
			Vector3f vector3fBottom = vector3fsBottom[i];
			vector3fBottom.rotate(quaternionf);
			vector3fBottom.mul(f4);
			vector3fBottom.add(x, y - 0.1F, z);
		}
		float f7 = this.getU0();
		float f8 = this.getU1();
		float f5 = this.getV0();
		float f6 = this.getV1();
		int light = this.getLightColor(ticks);
		buffer.addVertex(vector3fs[0].x(), vector3fs[0].y(), vector3fs[0].z()).setUv(f8, f6).setColor(this.rCol, this.gCol, this.bCol, this.alpha).setLight(light);
		buffer.addVertex(vector3fs[1].x(), vector3fs[1].y(), vector3fs[1].z()).setUv(f8, f5).setColor(this.rCol, this.gCol, this.bCol, this.alpha).setLight(light);
		buffer.addVertex(vector3fs[2].x(), vector3fs[2].y(), vector3fs[2].z()).setUv(f7, f5).setColor(this.rCol, this.gCol, this.bCol, this.alpha).setLight(light);
		buffer.addVertex(vector3fs[3].x(), vector3fs[3].y(), vector3fs[3].z()).setUv(f7, f6).setColor(this.rCol, this.gCol, this.bCol, this.alpha).setLight(light);
		buffer.addVertex(vector3fs[3].x(), vector3fs[3].y(), vector3fs[3].z()).setUv(f7, f6).setColor(this.rCol, this.gCol, this.bCol, this.alpha).setLight(light);
		buffer.addVertex(vector3fs[2].x(), vector3fs[2].y(), vector3fs[2].z()).setUv(f7, f5).setColor(this.rCol, this.gCol, this.bCol, this.alpha).setLight(light);
		buffer.addVertex(vector3fs[1].x(), vector3fs[1].y(), vector3fs[1].z()).setUv(f8, f5).setColor(this.rCol, this.gCol, this.bCol, this.alpha).setLight(light);
		buffer.addVertex(vector3fs[0].x(), vector3fs[0].y(), vector3fs[0].z()).setUv(f8, f6).setColor(this.rCol, this.gCol, this.bCol, this.alpha).setLight(light);
	}

	@Override
	public void tick() {
		super.tick();
		this.setSpriteFromAge(this.sprites);
	}

	@Override
	public ParticleRenderType getRenderType() {
		return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
	}
}
