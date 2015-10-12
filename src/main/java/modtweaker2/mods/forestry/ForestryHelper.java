package modtweaker2.mods.forestry;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import modtweaker2.helpers.ReflectionHelper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import forestry.factory.tiles.TileCarpenter;

public class ForestryHelper {

	@SuppressWarnings("unchecked")
	public static void addCarpenterRecipeBox(ItemStack box) {
		List<ItemStack> recipeBoxes = (ArrayList<ItemStack>) ReflectionHelper.getStaticObject(TileCarpenter.RecipeManager.class, "boxes");

		if(recipeBoxes != null) {
			recipeBoxes.add(box);
		}
	}

	@SuppressWarnings("unchecked")
	public static void addCarpenterRecipeFluids(Fluid newFluid) {
		Set<Fluid> recipeFluids = (HashSet<Fluid>) ReflectionHelper.getStaticObject(TileCarpenter.RecipeManager.class, "recipeFluids");

		if(recipeFluids != null) {
			recipeFluids.add(newFluid);
		}
	}
}
