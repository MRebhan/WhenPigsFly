package com.github.mrebhan.whenpigsfly.entity;

import com.github.mrebhan.whenpigsfly.item.ItemWeakEnderCrystal;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityWeakEnderCrystal extends EntityEnderCrystal {

	public EntityWeakEnderCrystal(World p_i1699_1_, double p_i1699_2_,
			double p_i1699_4_, double p_i1699_6_) {
		super(p_i1699_1_, p_i1699_2_, p_i1699_4_, p_i1699_6_);
		// TODO Auto-generated constructor stub
	}

	public EntityWeakEnderCrystal(World p_i1698_1_) {
		super(p_i1698_1_);
		// TODO Auto-generated constructor stub
	}
	
    /**
     * Called when the entity is attacked.
     */
	@Override
    public boolean attackEntityFrom(DamageSource p_70097_1_, float p_70097_2_)
    {
        if (this.isEntityInvulnerable())
        {
            return false;
        }
        else
        {
            if (!this.isDead && !this.worldObj.isRemote)
            {
                this.health = 0;

                if (this.health <= 0)
                {
                    this.setDead();

                    if (!this.worldObj.isRemote)
                    {
                        this.worldObj.createExplosion((Entity)null, this.posX, this.posY, this.posZ, 0.0F, true);
                        this.dropItem(ItemWeakEnderCrystal.item, 1);
                    }
                }
            }

            return true;
        }
    }
	
}
