package modtweaker2.mods.actuallyadditions;

import de.ellpeck.actuallyadditions.mod.util.ItemUtil;
import modtweaker2.mods.actuallyadditions.helper.GenericLens;
import modtweaker2.mods.actuallyadditions.helper.GenericLensRecipe;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ActuallyAdditionsExtension {

    public static final ArrayList<GenericLensRecipe> GenericLensReconstructRecipe = new ArrayList<>();

    public static ArrayList<GenericLensRecipe> getRecipesFor(ItemStack input, GenericLens genericLens) {

        ArrayList<GenericLensRecipe> possibleRecipes = new ArrayList<>();

        List<GenericLensRecipe> recipes = GenericLensReconstructRecipe.stream().filter(
                genericLensRecipe -> genericLensRecipe.getLens().getLens().equals(genericLens.getLens())
        ).collect(Collectors.toList());

        for (GenericLensRecipe recipe : recipes) {
            if (ItemUtil.contains(recipe.getInputs(), input, true)) {
                possibleRecipes.add(recipe);
            }
        }

        return possibleRecipes;

    }

}
