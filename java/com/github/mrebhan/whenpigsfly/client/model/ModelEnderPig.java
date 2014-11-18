package com.github.mrebhan.whenpigsfly.client.model;

import org.lwjgl.opengl.GL11;

import com.github.mrebhan.whenpigsfly.entity.EntityEnderPig;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;

public class ModelEnderPig extends ModelBase {
	/** The wing Model renderer of the dragon */
	private ModelRenderer wing;
	/** The wing tip Model renderer of the dragon */
	private ModelRenderer wingTip;
	public ModelRenderer head;
	public ModelRenderer body;
	public ModelRenderer leg1;
	public ModelRenderer leg2;
	public ModelRenderer leg3;
	public ModelRenderer leg4;
	protected float field_78145_g = 4.0F;
	protected float field_78151_h = 4.0F;
	private float partialTicks;
	private static final String __OBFID = "CL_00000870";

	public ModelEnderPig(float p_i1169_1_)
	{
		int p_i1154_1_ = 6;
		int p_i1154_2_ = 0;
		this.textureWidth = 256;
		this.textureHeight = 256;
		this.setTextureOffset("wing.skin", -56, 88);
		this.setTextureOffset("wingtip.skin", -56, 144);
		this.setTextureOffset("wing.bone", 112, 88);
		this.setTextureOffset("wingtip.bone", 112, 136);
		this.wing = new ModelRenderer(this, "wing");
		this.wing.setRotationPoint(-12.0F, 5.0F, 2.0F);
		this.wing.addBox("bone", -56.0F, -4.0F, -4.0F, 56, 8, 8);
		this.wing.addBox("skin", -56.0F, 0.0F, 2.0F, 56, 0, 56);
		this.wingTip = new ModelRenderer(this, "wingtip");
		this.wingTip.setRotationPoint(-56.0F, 0.0F, 0.0F);
		this.wingTip.addBox("bone", -56.0F, -2.0F, -2.0F, 56, 4, 4);
		this.wingTip.addBox("skin", -56.0F, 0.0F, 2.0F, 56, 0, 56);
		this.wing.addChild(this.wingTip);
		this.head = new ModelRenderer(this, 0, 0);
		this.head.addBox(-4.0F, -4.0F, -8.0F, 8, 8, 8, p_i1154_2_);
		this.head.setRotationPoint(0.0F, (float)(18 - p_i1154_1_), -6.0F);
		this.body = new ModelRenderer(this, 28, 8);
		this.body.addBox(-5.0F, -10.0F, -7.0F, 10, 16, 8, p_i1154_2_);
		this.body.setRotationPoint(0.0F, (float)(17 - p_i1154_1_), 2.0F);
		this.leg1 = new ModelRenderer(this, 0, 16);
		this.leg1.addBox(-2.0F, 0.0F, -2.0F, 4, p_i1154_1_, 4, p_i1154_2_);
		this.leg1.setRotationPoint(-3.0F, (float)(24 - p_i1154_1_), 7.0F);
		this.leg2 = new ModelRenderer(this, 0, 16);
		this.leg2.addBox(-2.0F, 0.0F, -2.0F, 4, p_i1154_1_, 4, p_i1154_2_);
		this.leg2.setRotationPoint(3.0F, (float)(24 - p_i1154_1_), 7.0F);
		this.leg3 = new ModelRenderer(this, 0, 16);
		this.leg3.addBox(-2.0F, 0.0F, -2.0F, 4, p_i1154_1_, 4, p_i1154_2_);
		this.leg3.setRotationPoint(-3.0F, (float)(24 - p_i1154_1_), -5.0F);
		this.leg4 = new ModelRenderer(this, 0, 16);
		this.leg4.addBox(-2.0F, 0.0F, -2.0F, 4, p_i1154_1_, 4, p_i1154_2_);
		this.leg4.setRotationPoint(3.0F, (float)(24 - p_i1154_1_), -5.0F);
		this.head.setTextureOffset(16, 16).addBox(-2.0F, 0.0F, -9.0F, 4, 3, 1, p_i1154_2_);
	}

	/**
	 * Used for easily adding entity-dependent animations. The second and third float params here are the same second
	 * and third as in the setRotationAngles method.
	 */
	public void setLivingAnimations(EntityLivingBase p_78086_1_, float p_78086_2_, float p_78086_3_, float p_78086_4_)
	{
		this.partialTicks = p_78086_4_;
	}

	/**
	 * Sets the models various rotation angles then renders the model.
	 */
	public void render(Entity p_78088_1_, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float p_78088_7_)
	{	
		GL11.glPushMatrix();
		EntityEnderPig entitydragon = (EntityEnderPig)p_78088_1_;
		float f6 = entitydragon.prevAnimTime + (entitydragon.animTime - entitydragon.prevAnimTime) * this.partialTicks;
		float f7 = (float)(Math.sin((double)(f6 * (float)Math.PI * 2.0F - 1.0F)) + 1.0D);
		f7 = (f7 * f7 * 1.0F + f7 * 2.0F) * 0.05F;
		//		GL11.glTranslatef(0.0F, f7 - 2.0F, -3.0F);
		GL11.glTranslated(0, .5, -.75);
		GL11.glRotated(180, 0, 1, 0);
		GL11.glRotatef(f7 * 2.0F, 1.0F, 0.0F, 0.0F);
		float f11 = 1.5F;
		float f12 = this.updateRotations(entitydragon.getMovementOffsets(5, this.partialTicks)[0] - entitydragon.getMovementOffsets(10, this.partialTicks)[0]);
		float f13 = this.updateRotations(entitydragon.getMovementOffsets(5, this.partialTicks)[0] + (double)(f12 / 2.0F));
		float f14 = f6 * (float)Math.PI * 2.0F;
		float f9 = -12.0F;
		float f15;
		GL11.glPushMatrix();
		GL11.glTranslatef(0.0F, 1.0F, 0.0F);
		if (((EntityEnderPig) p_78088_1_).isInAir())
			GL11.glRotatef(-f12 * f11 * 1.0F, 0.0F, 0.0F, 1.0F);
		GL11.glTranslatef(0.0F, -1.0F, 0.0F);
		for (int j = 0; j < 2; ++j)
		{
			GL11.glEnable(GL11.GL_CULL_FACE);
			f15 = f6 * (float)Math.PI * 2.0F;
			if (!((EntityEnderPig) p_78088_1_).isInAir()) {
				this.wing.rotateAngleX = 0;
				this.wing.rotateAngleY = 0;
				this.wing.rotateAngleZ = 45.5F;
				this.wingTip.rotateAngleZ = -2.9F;
				((EntityEnderPig) p_78088_1_).animTime = 0;
			} else {
				this.wing.rotateAngleX = 0.125F - (float)Math.cos((double)f15) * 0.2F;
				this.wing.rotateAngleY = 0.25F;
				this.wing.rotateAngleZ = (float)(Math.sin((double)f15) + 0.125D) * 0.8F;
				this.wingTip.rotateAngleZ = -((float)(Math.sin((double)(f15 + 2.0F)) + 0.5D)) * 0.75F;
			}
			GL11.glScalef(.5F, .5F, .5F);
			this.wing.render(p_78088_7_);
			GL11.glScalef(2, 2, 2);
			//            this.frontLeg.render(p_78088_7_);
			//            this.rearLeg.render(p_78088_7_);
			GL11.glScalef(-1.0F, 1.0F, 1.0F);

			if (j == 0)
			{
				GL11.glCullFace(GL11.GL_FRONT);
			}
		}

		GL11.glPopMatrix();
		GL11.glCullFace(GL11.GL_BACK);
		GL11.glDisable(GL11.GL_CULL_FACE);
		float f16 = -((float)Math.sin((double)(f6 * (float)Math.PI * 2.0F))) * 0.0F;
		f14 = f6 * (float)Math.PI * 2.0F;
		f9 = 60.0F;		
		GL11.glPopMatrix();
		GL11.glPushMatrix();
		GL11.glTranslated(0, 0, -1.1);
		GL11.glRotated(180, 0, 1, 0);
		if (((EntityEnderPig) p_78088_1_).isInAir()) {
			GL11.glTranslatef(0.0F, 1.0F, 0.0F);
			GL11.glRotatef(-f12 * f11 * 1.0F, 0.0F, 0.0F, 1.0F);
			GL11.glTranslatef(0.0F, -1.0F, 0.0F);
		}
		this.setRotationAngles(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, p_78088_7_, p_78088_1_);

		this.head.render(p_78088_7_);
		this.body.render(p_78088_7_);
		this.leg1.render(p_78088_7_);
		this.leg2.render(p_78088_7_);
		this.leg3.render(p_78088_7_);
		this.leg4.render(p_78088_7_);
		GL11.glPopMatrix();
	}

	/**
	 * Updates the rotations in the parameters for rotations greater than 180 degrees or less than -180 degrees. It adds
	 * or subtracts 360 degrees, so that the appearance is the same, although the numbers are then simplified to range -
	 * 180 to 180
	 */
	private float updateRotations(double p_78214_1_)
	{
		while (p_78214_1_ >= 180.0D)
		{
			p_78214_1_ -= 360.0D;
		}

		while (p_78214_1_ < -180.0D)
		{
			p_78214_1_ += 360.0D;
		}

		return (float)p_78214_1_;
	}

	private double range(double max, double min, double val) {
		if (val > max) {
			return max;
		}
		if (val < min) {
			return min;
		}
		return val;
	}

	/**
	 * Sets the model's various rotation angles. For bipeds, par1 and par2 are used for animating the movement of arms
	 * and legs, where par1 represents the time(so that arms and legs swing back and forth) and par2 represents how
	 * "far" arms and legs can swing at most.
	 */
	public void setRotationAngles(float p_78087_1_, float p_78087_2_, float p_78087_3_, float p_78087_4_, float p_78087_5_, float p_78087_6_, Entity p_78087_7_)
	{
		float f6 = (180F / (float)Math.PI);
		this.head.rotateAngleX = p_78087_5_ / (180F / (float)Math.PI);
		this.head.rotateAngleY = p_78087_4_ / (180F / (float)Math.PI);
		this.body.rotateAngleX = ((float)Math.PI / 2F);
		EntityEnderPig enderPig = (EntityEnderPig) p_78087_7_;
		if (enderPig.isInAir()) {
			this.leg1.rotateAngleX = 45;
			this.leg2.rotateAngleX = 45;
			this.leg3.rotateAngleX = 45;
			this.leg4.rotateAngleX = 45;
		} else {
			this.leg1.rotateAngleX = MathHelper.cos(p_78087_1_ * 0.6662F) * 1.4F * p_78087_2_;
			this.leg2.rotateAngleX = MathHelper.cos(p_78087_1_ * 0.6662F + (float)Math.PI) * 1.4F * p_78087_2_;
			this.leg3.rotateAngleX = MathHelper.cos(p_78087_1_ * 0.6662F + (float)Math.PI) * 1.4F * p_78087_2_;
			this.leg4.rotateAngleX = MathHelper.cos(p_78087_1_ * 0.6662F) * 1.4F * p_78087_2_;
		}
	}

}
