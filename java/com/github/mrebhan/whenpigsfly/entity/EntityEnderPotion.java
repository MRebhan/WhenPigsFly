package com.github.mrebhan.whenpigsfly.entity;

import com.github.mrebhan.whenpigsfly.item.ItemEnderPotion;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;

public class EntityEnderPotion extends EntityThrowable {

	public EntityEnderPotion(World p_i1776_1_) {
		super(p_i1776_1_);
		// TODO Auto-generated constructor stub
	}

	public EntityEnderPotion(World p_i1778_1_, double p_i1778_2_,
			double p_i1778_4_, double p_i1778_6_) {
		super(p_i1778_1_, p_i1778_2_, p_i1778_4_, p_i1778_6_);
		// TODO Auto-generated constructor stub
	}

	public EntityEnderPotion(World p_i1777_1_, EntityLivingBase p_i1777_2_) {
		super(p_i1777_1_, p_i1777_2_);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onImpact(MovingObjectPosition p_70184_1_) {
		// TODO Auto-generated method stub

		if (p_70184_1_.typeOfHit == MovingObjectType.ENTITY) {
			Entity e = p_70184_1_.entityHit;

			if (e instanceof EntityPig) {
				e.setDead();
				this.setDead();
				EntityEnderPig newEntity = new EntityEnderPig(worldObj);
				newEntity.setPositionAndRotation(e.posX, e.posY, e.posZ, e.rotationPitch, e.rotationYaw);
				if (((EntityPig) e).getSaddled())
					newEntity.setSaddled(true);
				this.worldObj.spawnEntityInWorld(newEntity);
				
			} else {
				this.setDead();
				if (!this.worldObj.isRemote) { 
					this.dropItem(ItemEnderPotion.item, 1);
				}
			}
		} else {
			this.setDead();
			if (!this.worldObj.isRemote) { 
				this.dropItem(ItemEnderPotion.item, 1);
			}
		}
	}

}
