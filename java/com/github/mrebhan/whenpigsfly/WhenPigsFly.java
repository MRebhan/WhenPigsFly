package com.github.mrebhan.whenpigsfly;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;

import com.github.mrebhan.core.helper.EntityHelper;
import com.github.mrebhan.whenpigsfly.entity.EntityEnderPig;
import com.github.mrebhan.whenpigsfly.entity.EntityEnderPotion;
import com.github.mrebhan.whenpigsfly.entity.EntityWeakEnderCrystal;
import com.github.mrebhan.whenpigsfly.item.ItemEnderPotion;
import com.github.mrebhan.whenpigsfly.item.ItemWeakEnderCrystal;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;

@Mod(modid="whenpigsfly", name="When Pigs Fly", version="r0", dependencies="required-after:CraftCore")
public class WhenPigsFly {
	@Instance(value="whenpigsfly")
	public static WhenPigsFly instance;
	
	@SidedProxy(clientSide="com.github.mrebhan.whenpigsfly.client.ClientProxy", serverSide="com.github.mrebhan.whenpigsfly.CommonProxy")
	public static CommonProxy proxy;
	
	public SimpleNetworkWrapper wrapper;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent e) {
		System.out.println("Initializing...");
		long millis = System.currentTimeMillis();
		EntityHelper.registerEntity(EntityEnderPig.class, "EntityEnderPig", this, true);
		EntityHelper.registerEntity(EntityWeakEnderCrystal.class, "EntityWeakEnderCrystal", this, false);
		EntityHelper.registerEntity(EntityEnderPotion.class, "EntityEnderPotion", this, false);
		GameRegistry.registerItem(ItemWeakEnderCrystal.item, "ItemWeakEnderCrystal");
		GameRegistry.registerItem(ItemEnderPotion.item, "ItemEnderPotion");
		GameRegistry.addRecipe(new ItemStack(ItemWeakEnderCrystal.item, 4), " s ", "   ", "ccc", 's', new ItemStack(Items.nether_star), 'c', new ItemStack(Blocks.cobblestone));
		proxy.registerRenderers();
		MinecraftForge.EVENT_BUS.register(new com.github.mrebhan.whenpigsfly.EventHandler());
		FMLCommonHandler.instance().bus().register(new com.github.mrebhan.whenpigsfly.EventHandler());
		this.wrapper = NetworkRegistry.INSTANCE.newSimpleChannel("MRebhan:wpf");
		Keybindings.instance();
		this.wrapper.registerMessage(PacketKeys.Handler.class, PacketKeys.class, 0, Side.CLIENT);
		this.wrapper.registerMessage(PacketKeys.Handler.class, PacketKeys.class, 0, Side.SERVER);
		long duration = System.currentTimeMillis() - millis;
		System.out.println("Initialization complete. Time elapsed: " + duration);
	}
}
