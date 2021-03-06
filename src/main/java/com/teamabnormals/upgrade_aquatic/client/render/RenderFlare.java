package com.teamabnormals.upgrade_aquatic.client.render;

import com.mojang.blaze3d.platform.GlStateManager;
import com.teamabnormals.upgrade_aquatic.client.model.ModelFlare;
import com.teamabnormals.upgrade_aquatic.client.render.overlay.RenderLayerFlareEyes;
import com.teamabnormals.upgrade_aquatic.common.entities.EntityFlare;
import com.teamabnormals.upgrade_aquatic.core.util.Reference;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RenderFlare extends MobRenderer<EntityFlare, ModelFlare<EntityFlare>> {

	public RenderFlare(EntityRendererManager manager) {
		super(manager, new ModelFlare<>(), 0.9F);
		this.addLayer(new RenderLayerFlareEyes<>(this));
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityFlare entity) {
		return new ResourceLocation(Reference.MODID, "textures/entity/flare/flare.png");
	}
	
	@Override
	protected void preRenderCallback(EntityFlare flare, float partialTickTime) {
		int i = flare.getPhantomSize();
		float f = 1.0F + 0.15F * (float)i;
		GlStateManager.scalef(f, f, f);
	}

	@Override
	protected void applyRotations(EntityFlare flare, float ageInTicks, float rotationYaw, float partialTicks) {
		super.applyRotations(flare, ageInTicks, rotationYaw, partialTicks);
		GlStateManager.rotatef(flare.rotationPitch, 1.0F, 0.0F, 0.0F);
	}

}