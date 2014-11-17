package com.github.mrebhan.whenpigsfly;

import com.github.mrebhan.core.helper.EntityHelper;
import com.github.mrebhan.whenpigsfly.entity.EntityEnderPig;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid="whenpigsfly", name="When Pigs Fly", version="r0", dependencies="required-after:CraftCore")
public class WhenPigsFly {
	@Instance(value="whenpigsfly")
	public static WhenPigsFly instance;
	
	@SidedProxy(clientSide="com.github.mrebhan.whenpigsfly.client.ClientProxy", serverSide="com.github.mrebhan.whenpigsfly.CommonProxy")
	public static CommonProxy proxy;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent e) {
		EntityHelper.registerEntity(EntityEnderPig.class, "EntityEnderPig", this, true);
		proxy.registerRenderers();
	}
}
