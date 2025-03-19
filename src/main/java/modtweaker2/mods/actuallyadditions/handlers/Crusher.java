package modtweaker2.mods.actuallyadditions.handlers;

import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.api.recipe.CrusherRecipe;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import modtweaker2.helpers.LogHelper;
import modtweaker2.utils.BaseListAddition;
import modtweaker2.utils.BaseListRemoval;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.List;
import java.util.stream.Collectors;

import static modtweaker2.helpers.InputHelper.toIItemStack;
import static modtweaker2.helpers.InputHelper.toStack;
import static modtweaker2.helpers.StackHelper.matches;

@ZenClass("mods.actuallyadditions.Crusher")
public class Crusher {

    private static final String name = "ActuallyAdditions Crusher";

    @ZenMethod
    public static void addRecipe(IItemStack input, IItemStack outputOne, IItemStack outputTwo, int outputTwoChance) {
        CrusherRecipe crusherRecipe = new CrusherRecipe(toStack(input), toStack(outputOne), toStack(outputTwo), outputTwoChance);
        MineTweakerAPI.apply(new AddRecipe(crusherRecipe));
    }

    @ZenMethod
    public static void removeRecipe(IIngredient output) {

        List<CrusherRecipe> crusherRecipes = ActuallyAdditionsAPI.crusherRecipes.stream()
                .filter(recipe -> contentMatch(recipe.getRecipeOutputOnes(), output))
                .distinct()
                .collect(Collectors.toList());


        if (!crusherRecipes.isEmpty()) {
            MineTweakerAPI.apply(new Remove(crusherRecipes));
        } else {
            LogHelper.logWarning(
                    String.format("No %s Recipe found for %s. Command ignored!", Crusher.name, output.toString()));
        }

    }

    @ZenMethod
    public static void removeRecipe(IItemStack input, IIngredient output) {

        List<CrusherRecipe> crusherRecipes = ActuallyAdditionsAPI.crusherRecipes.stream()
                .filter(recipe -> contentMatch(recipe.getRecipeOutputOnes(), output))
                .filter(recipe -> contentMatch(recipe.getRecipeInputs(), input))
                .distinct()
                .collect(Collectors.toList());

        if (!crusherRecipes.isEmpty()) {
            MineTweakerAPI.apply(new Remove(crusherRecipes));
        } else {
            LogHelper.logWarning(
                    String.format("No %s Recipe found for %s. Command ignored!", Crusher.name, output.toString()));
        }

    }

    private static boolean contentMatch(List<ItemStack> recipe, IIngredient output) {
        return recipe.stream().anyMatch(item -> matches(output, toIItemStack(item)));
    }

    public static class AddRecipe extends BaseListAddition<CrusherRecipe> {

        protected AddRecipe(CrusherRecipe recipe) {
            super(Crusher.name, ActuallyAdditionsAPI.crusherRecipes);
            recipes.add(recipe);
        }

        @Override
        protected String getRecipeInfo(CrusherRecipe recipe) {
            return LogHelper.getStackDescription(recipe.getRecipeInputs());
        }

    }

    private static class Remove extends BaseListRemoval<CrusherRecipe> {

        public Remove(List<CrusherRecipe> recipes) {
            super(Crusher.name, ActuallyAdditionsAPI.crusherRecipes, recipes);
        }

        @Override
        protected String getRecipeInfo(CrusherRecipe recipe) {
            return LogHelper.getStackDescription(new Object[]{recipe.getRecipeOutputOnes(), recipe.getRecipeOutputTwos()});
        }

    }


}
