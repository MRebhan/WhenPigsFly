package com.github.mrebhan.whenpigsfly.entity;

import java.util.Iterator;
import java.util.List;

import org.lwjgl.input.Mouse;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityFlying;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IEntityMultiPart;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.boss.EntityDragonPart;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.AchievementList;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class EntityEnderPig extends EntityCreature {

	public EntityEnderCrystal healingEnderCrystal;
	/** Animation time at previous tick. */
	public float prevAnimTime;
	/** Animation time, used to control the speed of the animation cycles (wings flapping, jaw opening, etc.) */
	public float animTime;
	/** Ring buffer array for the last 64 Y-positions and yaw rotations. Used to calculate offsets for the animations. */
	public double[][] ringBuffer = new double[64][3];
	/** Index into the ring buffer. Incremented once per tick and restarts at 0 once it reaches the end of the buffer. */
	public int ringBufferIndex = -1;

	public boolean saddled;
	public boolean playerascend;
	public boolean playerdescend;
	public boolean fly;

	public EntityEnderPig(World p_i1595_1_) {
		super(p_i1595_1_);
		this.isImmuneToFire = true;
		this.setHealth(this.getMaxHealth());
		this.ignoreFrustumCheck = true;
		this.getNavigator().setAvoidsWater(true);
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(1, new EntityAIPanic(this, 1.25D));
		this.tasks.addTask(2, new EntityAIWander(this, 1));
		this.tasks.addTask(3, new EntityAIWatchClosest(this, EntityPlayer.class, 16F));
		this.tasks.addTask(4, new EntityAILookIdle(this));
	}

	public boolean isNoDespawnRequired()
	{
		return true;
	}

	protected boolean canDespawn()
	{
		return false;
	}

	protected void updateAITasks()
	{
		++this.entityAge;
		if (this.riddenByEntity != null)
			return;
		this.worldObj.theProfiler.startSection("checkDespawn");
		this.despawnEntity();
		this.worldObj.theProfiler.endSection();
		this.worldObj.theProfiler.startSection("sensing");
		this.getEntitySenses().clearSensingCache();
		this.worldObj.theProfiler.endSection();
		this.worldObj.theProfiler.startSection("targetSelector");
		this.targetTasks.onUpdateTasks();
		this.worldObj.theProfiler.endSection();
		this.worldObj.theProfiler.startSection("goalSelector");
		this.tasks.onUpdateTasks();
		this.worldObj.theProfiler.endSection();
		this.worldObj.theProfiler.startSection("navigation");
		this.getNavigator().onUpdateNavigation();
		this.worldObj.theProfiler.endSection();
		this.worldObj.theProfiler.startSection("mob tick");
		this.updateAITick();
		this.worldObj.theProfiler.endSection();
		this.worldObj.theProfiler.startSection("controls");
		this.worldObj.theProfiler.startSection("move");
		this.getMoveHelper().onUpdateMoveHelper();
		this.worldObj.theProfiler.endStartSection("look");
		this.getLookHelper().onUpdateLook();
		this.worldObj.theProfiler.endStartSection("jump");
		this.getJumpHelper().doJump();
		this.worldObj.theProfiler.endSection();
		this.worldObj.theProfiler.endSection();
	}

	protected void entityInit()
	{
		super.entityInit();
		this.dataWatcher.addObject(16, Byte.valueOf((byte)0));
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(100.0D);
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
	}

	/**
	 * Returns a double[3] array with movement offsets, used to calculate trailing tail/neck positions. [0] = yaw
	 * offset, [1] = y offset, [2] = unused, always 0. Parameters: buffer index offset, partial ticks.
	 */
	public double[] getMovementOffsets(int p_70974_1_, float p_70974_2_)
	{
		if (this.getHealth() <= 0.0F)
		{
			p_70974_2_ = 0.0F;
		}

		p_70974_2_ = 1.0F - p_70974_2_;
		int j = this.ringBufferIndex - p_70974_1_ * 1 & 63;
		int k = this.ringBufferIndex - p_70974_1_ * 1 - 1 & 63;
		double[] adouble = new double[3];
		double d0 = this.ringBuffer[j][0];
		double d1 = MathHelper.wrapAngleTo180_double(this.ringBuffer[k][0] - d0);
		adouble[0] = d0 + d1 * (double)p_70974_2_;
		d0 = this.ringBuffer[j][1];
		d1 = this.ringBuffer[k][1] - d0;
		adouble[1] = d0 + d1 * (double)p_70974_2_;
		adouble[2] = this.ringBuffer[j][2] + (this.ringBuffer[k][2] - this.ringBuffer[j][2]) * (double)p_70974_2_;
		return adouble;
	}

	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		this.stepHeight = 1;
		if (this.motionY < -0.1 && !fly) {
			this.motionY = -0.1;
		}
		float f;
		float f1;

		if (this.worldObj.isRemote)
		{
			f = MathHelper.cos(this.animTime * (float)Math.PI * 2.0F);
			f1 = MathHelper.cos(this.prevAnimTime * (float)Math.PI * 2.0F);

			if (f1 <= -0.3F && f >= -0.3F)
			{
				this.worldObj.playSound(this.posX, this.posY, this.posZ, "mob.enderdragon.wings", 5.0F, 0.8F + this.rand.nextFloat() * 0.3F, false);
			}
		}

		this.prevAnimTime = this.animTime;

		float f2;

		if (this.getHealth() <= 0.0F) {
			f = (this.rand.nextFloat() - 0.5F) * 8.0F;
			f1 = (this.rand.nextFloat() - 0.5F) * 4.0F;
			f2 = (this.rand.nextFloat() - 0.5F) * 8.0F;
			this.worldObj.spawnParticle("largeexplode", this.posX + (double)f, this.posY + 2.0D + (double)f1, this.posZ + (double)f2, 0.0D, 0.0D, 0.0D);
		} else {
			this.updateDragonEnderCrystal();
			f = 0.2F / (MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ) * 10.0F + 1.0F);
			f *= (float)Math.pow(2.0D, this.motionY);
			this.animTime += f * 0.5F;
			this.rotationYaw = MathHelper.wrapAngleTo180_float(this.rotationYaw);
			if (this.ringBufferIndex < 0)
			{
				for (int i = 0; i < this.ringBuffer.length; ++i)
				{
					this.ringBuffer[i][0] = (double)this.rotationYaw;
					this.ringBuffer[i][1] = this.posY;
				}
			}

			if (++this.ringBufferIndex == this.ringBuffer.length)
			{
				this.ringBufferIndex = 0;
			}

			this.ringBuffer[this.ringBufferIndex][0] = (double)this.rotationYaw;
			this.ringBuffer[this.ringBufferIndex][1] = this.posY;
			if (this.riddenByEntity != null && this.riddenByEntity instanceof EntityPlayer) {
				EntityPlayer thePlayer = (EntityPlayer) this.riddenByEntity;
				if (this.onGround)
					this.fly = false;
				if (playerascend)
					this.fly = true;
				this.rotationYaw = thePlayer.rotationYaw;
				this.rotationPitch = thePlayer.rotationPitch;
				this.rotationYawHead = thePlayer.rotationYawHead;
				f = this.riddenByEntity.rotationYaw + -thePlayer.moveStrafing * 90.0F;
				this.motionX += -Math.sin((double)(f * (float)Math.PI / 180.0F)) * thePlayer.moveForward * 0.1;
				this.motionZ += Math.cos((double)(f * (float)Math.PI / 180.0F)) * thePlayer.moveForward * 0.1;
				if (fly)
					this.motionY = this.playerascend ? 0.3 : (this.playerdescend ? -0.3 : 0);
			} else {
				this.playerascend = this.playerdescend = this.fly = false;
			}
		}

		this.setSize(1F, 0.9F);
	}

	@Override
	public void fall(float par0) {

	}

	/**
	 * Updates the state of the enderdragon's current endercrystal.
	 */
	private void updateDragonEnderCrystal()
	{
		if (this.healingEnderCrystal != null)
		{
			if (this.healingEnderCrystal.isDead)
			{
				if (!this.worldObj.isRemote)
				{
					this.attackEntityFrom(DamageSource.setExplosionSource((Explosion)null), 0);
				}

				this.healingEnderCrystal = null;
			}
			else if (this.ticksExisted % 5 == 0 && this.getHealth() < this.getMaxHealth())
			{
				this.setHealth(this.getHealth() + 1.0F);
			}
		}

		if (this.rand.nextInt(10) == 0)
		{
			float f = 32.0F;
			List list = this.worldObj.getEntitiesWithinAABB(EntityEnderCrystal.class, this.boundingBox.expand((double)f, (double)f, (double)f));
			EntityEnderCrystal entityendercrystal = null;
			double d0 = Double.MAX_VALUE;
			Iterator iterator = list.iterator();

			while (iterator.hasNext())
			{
				EntityEnderCrystal entityendercrystal1 = (EntityEnderCrystal)iterator.next();
				double d1 = entityendercrystal1.getDistanceSqToEntity(this);

				if (d1 < d0)
				{
					d0 = d1;
					entityendercrystal = entityendercrystal1;
				}
			}

			this.healingEnderCrystal = entityendercrystal;
		}
	}

	/**
	 * Returns the sound this mob makes while it's alive.
	 */
	protected String getLivingSound()
	{
		return "mob.pig.say";
	}

	/**
	 * Returns the sound this mob makes when it is hurt.
	 */
	protected String getHurtSound()
	{
		return "mob.enderdragon.growl";
	}

	/**
	 * Returns the volume for the sounds this mob makes.
	 */
	protected float getSoundVolume()
	{
		return 5.0F;
	}

	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	public void writeEntityToNBT(NBTTagCompound p_70014_1_)
	{
		super.writeEntityToNBT(p_70014_1_);
		p_70014_1_.setBoolean("Saddle", this.getSaddled());
	}

	/**
	 * Returns true if the pig is saddled.
	 */
	public boolean getSaddled()
	{
		return (this.dataWatcher.getWatchableObjectByte(16) & 1) != 0;
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	public void readEntityFromNBT(NBTTagCompound p_70037_1_)
	{
		super.readEntityFromNBT(p_70037_1_);
		this.setSaddled(p_70037_1_.getBoolean("Saddle"));
	}

	/**
	 * Set or remove the saddle of the pig.
	 */
	public void setSaddled(boolean p_70900_1_)
	{
		if (p_70900_1_)
		{
			this.dataWatcher.updateObject(16, Byte.valueOf((byte)1));
		}
		else
		{
			this.dataWatcher.updateObject(16, Byte.valueOf((byte)0));
		}
	}

	@Override
	public boolean interact(EntityPlayer p_70085_1_)
	{
		if (super.interact(p_70085_1_))
		{
			return true;
		}
		else if (this.getSaddled() && !this.worldObj.isRemote && (this.riddenByEntity == null || this.riddenByEntity == p_70085_1_))
		{
			p_70085_1_.mountEntity(this);
			p_70085_1_.triggerAchievement(AchievementList.flyPig);
			return true;
		} else if (!this.getSaddled() && !this.worldObj.isRemote) {
			if (p_70085_1_.inventory.mainInventory[p_70085_1_.inventory.currentItem] != null && p_70085_1_.inventory.mainInventory[p_70085_1_.inventory.currentItem].getItem() == Items.saddle) {
				p_70085_1_.inventory.mainInventory[p_70085_1_.inventory.currentItem].stackSize--;
				this.setSaddled(true);
				return true;
			}
			return false;
		}
		else
		{
			return false;
		}
	}

	/**
	 * Drop 0-2 items of this living's type. @param par1 - Whether this entity has recently been hit by a player. @param
	 * par2 - Level of Looting used to kill this mob.
	 */
	protected void dropFewItems(boolean p_70628_1_, int p_70628_2_)
	{
		int j = this.rand.nextInt(3) + 1 + this.rand.nextInt(1 + p_70628_2_);

		for (int k = 0; k < j; ++k)
		{
			if (this.isBurning())
			{
				this.dropItem(Items.cooked_porkchop, 1);
			}
			else
			{
				this.dropItem(Items.porkchop, 1);
			}
		}

		if (this.getSaddled())
		{
			this.dropItem(Items.saddle, 1);
		}
	}

	public boolean isInAir() {
		return !this.onGround && !this.isInWater();
	}

}
