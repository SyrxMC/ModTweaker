package modtweaker2.mods.actuallyadditions;

import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.api.recipe.LensNoneRecipe;
import de.ellpeck.actuallyadditions.mod.util.ItemUtil;
import modtweaker2.mods.actuallyadditions.helper.GenericLens;
import modtweaker2.mods.actuallyadditions.helper.GenericLensRecipe;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ActuallyAdditionsExtension {

    public static ArrayList<GenericLensRecipe> getRecipesFor(ItemStack input, GenericLens genericLens) {

        ArrayList<GenericLensRecipe> possibleRecipes = new ArrayList<>();

        List<LensNoneRecipe> recipes = ActuallyAdditionsAPI.reconstructorLensNoneRecipes.stream().filter(el -> el instanceof GenericLensRecipe).filter(
                genericLensRecipe -> ((GenericLensRecipe) genericLensRecipe).getLens().getItem().equals(genericLens.getItem())
        ).collect(Collectors.toList());

        for (LensNoneRecipe recipe : recipes) {
            if (ItemUtil.contains(recipe.getInputs(), input, true)) {
                possibleRecipes.add((GenericLensRecipe) recipe);
            }
        }

        return possibleRecipes;

    }

}
