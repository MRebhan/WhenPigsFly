package com.github.mrebhan.whenpigsfly.item;

import com.github.mrebhan.whenpigsfly.entity.EntityWeakEnderCrystal;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemWeakEnderCrystal extends Item {
	public static final Item item = new ItemWeakEnderCrystal()
	.setCreativeTab(CreativeTabs.tabMisc)
	.setTextureName("whenpigsfly:endercrystal")
	.setUnlocalizedName("ItemWeakEnderCrystal");

	/**
	 * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
	 * True if something happen and false if it don't. This is for ITEMS, not BLOCKS
	 */
	@Override
	public boolean onItemUse(ItemStack p_77648_1_, EntityPlayer p_77648_2_, World p_77648_3_, int p_77648_4_, int p_77648_5_, int p_77648_6_, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_)
	{
		if (!p_77648_3_.isRemote) {
			EntityWeakEnderCrystal enderCrystal = new EntityWeakEnderCrystal(p_77648_3_);
			enderCrystal.posX = p_77648_4_ + .5;
			enderCrystal.posY = p_77648_5_ + 1;
			enderCrystal.posZ = p_77648_6_ + .5;
			p_77648_3_.spawnEntityInWorld(enderCrystal);
			p_77648_1_.stackSize--;
		}
		
		return true;
	}


}
