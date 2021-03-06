package me.mrCookieSlime.Slimefun.Objects.SlimefunItem.machines.electric.geo;

import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import me.mrCookieSlime.Slimefun.GEO.GEOScanner;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SimpleSlimefunItem;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockUseHandler;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;

public class GEOScannerBlock extends SimpleSlimefunItem<BlockUseHandler> {

	public GEOScannerBlock(Category category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
		super(category, item, recipeType, recipe);
	}
	
	@Override
	protected boolean areItemHandlersPrivate() {
		return false;
	}

	@Override
	public BlockUseHandler getItemHandler() {
		return e -> {
			Block b = e.getClickedBlock().get();
			
			e.cancel();
			GEOScanner.scanChunk(e.getPlayer(), b.getChunk());
		};
	}
}
