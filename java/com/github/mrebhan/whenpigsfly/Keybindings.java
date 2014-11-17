package com.github.mrebhan.whenpigsfly;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.client.registry.ClientRegistry;
import net.minecraft.client.settings.KeyBinding;

public class Keybindings {
	private static Keybindings instance;
	
	public KeyBinding ascend;
	public KeyBinding descend;
	
	public static Keybindings instance() {
		if (instance == null)
			instance = new Keybindings();
		return instance;
	}
	
	private Keybindings() {
		this.ascend = new KeyBinding("key.pig.ascend", Keyboard.KEY_R, "key.categories.whenpigsfly");
		this.descend = new KeyBinding("key.pig.descend", Keyboard.KEY_F, "key.categories.whenpigsfly");
		
		ClientRegistry.registerKeyBinding(ascend);
		ClientRegistry.registerKeyBinding(descend);
	}
}
