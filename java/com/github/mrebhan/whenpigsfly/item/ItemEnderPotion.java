package com.github.mrebhan.whenpigsfly.item;

import com.github.mrebhan.whenpigsfly.entity.EntityEnderPotion;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemEnderPotion extends Item {
	public static final Item item = new ItemEnderPotion()
	.setCreativeTab(CreativeTabs.tabBrewing)
	.setMaxStackSize(32)
	.setTextureName("whenpigsfly:enderpotion")
	.setUnlocalizedName("ItemEnderPotion");


	/**
	 * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
	 */
	public ItemStack onItemRightClick(ItemStack p_77659_1_, World p_77659_2_, EntityPlayer p_77659_3_)
	{
		--p_77659_1_.stackSize;

		p_77659_2_.playSoundAtEntity(p_77659_3_, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

		if (!p_77659_2_.isRemote)
		{
			p_77659_2_.spawnEntityInWorld(new EntityEnderPotion(p_77659_2_, p_77659_3_));
		}

		return p_77659_1_;
	}

}
