package com.github.mrebhan.whenpigsfly.render;

import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.github.mrebhan.whenpigsfly.client.model.ModelEnderPig;
import com.github.mrebhan.whenpigsfly.entity.EntityEnderPig;

public class RenderEnderPig extends RenderLiving {
//    private static final ResourceLocation enderDragonExplodingTextures = new ResourceLocation("textures/entity/enderdragon/dragon_exploding.png");
    private static final ResourceLocation enderDragonCrystalBeamTextures = new ResourceLocation("textures/entity/endercrystal/endercrystal_beam.png");
//    private static final ResourceLocation enderDragonEyesTextures = new ResourceLocation("textures/entity/enderdragon/dragon_eyes.png");
    private static final ResourceLocation enderPigTextures = new ResourceLocation("whenpigsfly:textures/entity/enderpig/TexEnderPig.png");
    private static final ResourceLocation saddledPigTextures = new ResourceLocation("whenpigsfly:textures/entity/enderpig/TexSaddle.png");
    /** An instance of the dragon model in RenderDragon */
    protected ModelEnderPig modelDragon;

    public RenderEnderPig()
    {
        super(new ModelEnderPig(0.0F), 0.5F);
        this.modelDragon = (ModelEnderPig)this.mainModel;
        this.setRenderPassModel(this.mainModel);
    }

    protected void rotateCorpse0(EntityEnderPig p_77043_1_, float p_77043_2_, float p_77043_3_, float p_77043_4_)
    {
        float f3 = (float)p_77043_1_.getMovementOffsets(7, p_77043_4_)[0];
        float f4 = (float)(p_77043_1_.getMovementOffsets(5, p_77043_4_)[1] - p_77043_1_.getMovementOffsets(10, p_77043_4_)[1]);
        GL11.glRotatef(-f3, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(f4 * 10.0F, 1.0F, 0.0F, 0.0F);
        GL11.glTranslatef(0.0F, 0.0F, 1.0F);

        if (p_77043_1_.deathTime > 0)
        {
            float f5 = ((float)p_77043_1_.deathTime + p_77043_4_ - 1.0F) / 20.0F * 1.6F;
            f5 = MathHelper.sqrt_float(f5);

            if (f5 > 1.0F)
            {
                f5 = 1.0F;
            }

            GL11.glRotatef(f5 * this.getDeathMaxRotation(p_77043_1_), 0.0F, 0.0F, 1.0F);
        }
    }

    /**
     * Renders the model in RenderLiving
     */
    protected void renderModel(EntityEnderPig p_77036_1_, float p_77036_2_, float p_77036_3_, float p_77036_4_, float p_77036_5_, float p_77036_6_, float p_77036_7_)
    {
    	if (p_77036_1_.getSaddled()) {
    		this.bindTexture(saddledPigTextures);
    		GL11.glPushMatrix();
    		GL11.glScaled(1.1, 1.1, 1.1);
    		GL11.glTranslated(0, -0.08, 0);
    		this.mainModel.render(p_77036_1_, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, p_77036_7_);
    		GL11.glPopMatrix();
    	}
    	
        this.bindEntityTexture(p_77036_1_);
        this.mainModel.render(p_77036_1_, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, p_77036_7_);

        if (p_77036_1_.hurtTime > 0)
        {
            GL11.glDepthFunc(GL11.GL_EQUAL);
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GL11.glColor4f(1.0F, 0.0F, 0.0F, 0.5F);
            this.mainModel.render(p_77036_1_, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, p_77036_7_);
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glDepthFunc(GL11.GL_LEQUAL);
        }
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void func_76986_a(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    public void doRender(EntityEnderPig p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_)
    {
        super.doRender((EntityLiving)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);

        if (p_76986_1_.healingEnderCrystal != null)
        {
            float f2 = (float)p_76986_1_.healingEnderCrystal.innerRotation + p_76986_9_;
            float f3 = MathHelper.sin(f2 * 0.2F) / 2.0F + 0.5F;
            f3 = (f3 * f3 + f3) * 0.2F;
            float f4 = (float)(p_76986_1_.healingEnderCrystal.posX - p_76986_1_.posX - (p_76986_1_.prevPosX - p_76986_1_.posX) * (double)(1.0F - p_76986_9_));
            float f5 = (float)((double)f3 + p_76986_1_.healingEnderCrystal.posY - 1.0D - p_76986_1_.posY - (p_76986_1_.prevPosY - p_76986_1_.posY) * (double)(1.0F - p_76986_9_));
            float f6 = (float)(p_76986_1_.healingEnderCrystal.posZ - p_76986_1_.posZ - (p_76986_1_.prevPosZ - p_76986_1_.posZ) * (double)(1.0F - p_76986_9_));
            float f7 = MathHelper.sqrt_float(f4 * f4 + f6 * f6);
            float f8 = MathHelper.sqrt_float(f4 * f4 + f5 * f5 + f6 * f6);
            GL11.glPushMatrix();
            GL11.glTranslatef((float)p_76986_2_, (float)p_76986_4_ + 2.0F, (float)p_76986_6_);
            GL11.glRotatef((float)(-Math.atan2((double)f6, (double)f4)) * 180.0F / (float)Math.PI - 90.0F, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef((float)(-Math.atan2((double)f7, (double)f5)) * 180.0F / (float)Math.PI - 90.0F, 1.0F, 0.0F, 0.0F);
            Tessellator tessellator = Tessellator.instance;
            RenderHelper.disableStandardItemLighting();
            GL11.glDisable(GL11.GL_CULL_FACE);
            this.bindTexture(enderDragonCrystalBeamTextures);
            GL11.glShadeModel(GL11.GL_SMOOTH);
            float f9 = 0.0F - ((float)p_76986_1_.ticksExisted + p_76986_9_) * 0.01F;
            float f10 = MathHelper.sqrt_float(f4 * f4 + f5 * f5 + f6 * f6) / 32.0F - ((float)p_76986_1_.ticksExisted + p_76986_9_) * 0.01F;
            tessellator.startDrawing(5);
            byte b0 = 8;

            for (int i = 0; i <= b0; ++i)
            {
                float f11 = MathHelper.sin((float)(i % b0) * (float)Math.PI * 2.0F / (float)b0) * 0.75F;
                float f12 = MathHelper.cos((float)(i % b0) * (float)Math.PI * 2.0F / (float)b0) * 0.75F;
                float f13 = (float)(i % b0) * 1.0F / (float)b0;
                tessellator.setColorOpaque_I(0);
                tessellator.addVertexWithUV((double)(f11 * 0.2F), (double)(f12 * 0.2F) + 1.5, 0.0D, (double)f13, (double)f10);
                tessellator.setColorOpaque_I(16777215);
                tessellator.addVertexWithUV((double)f11, (double)f12, (double)f8, (double)f13, (double)f9);
            }

            tessellator.draw();
            GL11.glEnable(GL11.GL_CULL_FACE);
            GL11.glShadeModel(GL11.GL_FLAT);
            RenderHelper.enableStandardItemLighting();
            GL11.glPopMatrix();
        }
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture0(EntityEnderPig p_110775_1_)
    {
        return enderPigTextures;
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void func_76986_a(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    public void doRender(EntityLiving p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_)
    {
        this.doRender((EntityEnderPig)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }

    protected void rotateCorpse(EntityLivingBase p_77043_1_, float p_77043_2_, float p_77043_3_, float p_77043_4_)
    {
        this.rotateCorpse0((EntityEnderPig)p_77043_1_, p_77043_2_, p_77043_3_, p_77043_4_);
    }

    /**
     * Renders the model in RenderLiving
     */
    protected void renderModel(EntityLivingBase p_77036_1_, float p_77036_2_, float p_77036_3_, float p_77036_4_, float p_77036_5_, float p_77036_6_, float p_77036_7_)
    {
        this.renderModel((EntityEnderPig)p_77036_1_, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, p_77036_7_);
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void func_76986_a(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    public void doRender(EntityLivingBase p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_)
    {
        this.doRender((EntityEnderPig)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(Entity p_110775_1_)
    {
        return this.getEntityTexture0((EntityEnderPig)p_110775_1_);
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void func_76986_a(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    public void doRender(Entity p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_)
    {
        this.doRender((EntityEnderPig)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }

}
