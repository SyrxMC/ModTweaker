package modtweaker2.mods.actuallyadditions.handlers;

import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.api.lens.Lens;
import de.ellpeck.actuallyadditions.api.recipe.LensNoneRecipe;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import modtweaker2.helpers.LogHelper;
import modtweaker2.mods.actuallyadditions.helper.GenericLens;
import modtweaker2.mods.actuallyadditions.helper.GenericLensRecipe;
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

@ZenClass("mods.actuallyadditions.AtomicReconstructor")
public class AtomicReconstructor {

    private static final String name = "ActuallyAdditions AtomicReconstructor";

    @ZenMethod
    public static void addRecipe(IItemStack input, IItemStack outputOne, int energyUsage) {
        LensNoneRecipe lensNoneRecipe = new LensNoneRecipe(toStack(input), toStack(outputOne), energyUsage);
        MineTweakerAPI.apply(new AddRecipe(lensNoneRecipe));
    }

    @ZenMethod
    public static void addRecipe(IItemStack lens, IItemStack input, IItemStack outputOne, int energyUsage) {

        GenericLens genericLens = null;
        ItemStack stack;

        if ((stack = toStack(lens)) != null) {
            for (Lens l : ActuallyAdditionsAPI.reconstructorLenses) {
                if (l instanceof GenericLens && ((GenericLens) l).getItem().equals(stack.getItem())) {
                    genericLens = (GenericLens) l;
                    break;
                }
            }
        }

        if (genericLens == null) {
            LogHelper.logWarning(String.format("No %s lens found for %s. Command ignored!", name, lens));
            return;
        }

        GenericLensRecipe genericLensRecipe = new GenericLensRecipe(genericLens, toStack(input), toStack(outputOne), energyUsage);
        MineTweakerAPI.apply(new AddRecipe(genericLensRecipe));

    }

    @ZenMethod
    public static void remove(IIngredient output) {

        List<LensNoneRecipe> lensNoneRecipes = ActuallyAdditionsAPI.reconstructorLensNoneRecipes.stream()
                .filter(recipe -> contentMatch(recipe.getOutputs(), output))
                .distinct()
                .collect(Collectors.toList());

        if (!lensNoneRecipes.isEmpty()) {
            MineTweakerAPI.apply(new Remove(lensNoneRecipes));
        } else {
            LogHelper.logWarning(
                    String.format("No %s Recipe found for %s. Command ignored!", name, output.toString()));
        }

    }


    @ZenMethod
    public static void remove(IItemStack input, IIngredient output) {

        List<LensNoneRecipe> lensNoneRecipes = ActuallyAdditionsAPI.reconstructorLensNoneRecipes.stream()
                .filter(recipe -> contentMatch(recipe.getOutputs(), output))
                .filter(recipe -> contentMatch(recipe.getInputs(), input))
                .distinct()
                .collect(Collectors.toList());

        if (!lensNoneRecipes.isEmpty()) {
            MineTweakerAPI.apply(new Remove(lensNoneRecipes));
        } else {
            LogHelper.logWarning(
                    String.format("No %s Recipe found for %s. Command ignored!", name, output.toString()));
        }

    }

    @ZenMethod
    public static void remove(IItemStack lens, IItemStack input, IIngredient output) {

        GenericLens genericLens = null;
        ItemStack stack;

        if ((stack = toStack(lens)) != null) {
            for (Lens l : ActuallyAdditionsAPI.reconstructorLenses) {
                if (l instanceof GenericLens && ((GenericLens) l).getItem().equals(stack.getItem())) {
                    genericLens = (GenericLens) l;
                    break;
                }
            }
        }

        if (genericLens == null) {
            LogHelper.logWarning(String.format("No %s lens found for %s. Command ignored!", name, lens));
            return;
        }

        GenericLens finalGenericLens = genericLens;

        List<LensNoneRecipe> lensNoneRecipes = ActuallyAdditionsAPI.reconstructorLensNoneRecipes.stream()
                .filter(recipe -> recipe instanceof GenericLensRecipe)
                .filter(recipe -> ((GenericLensRecipe) recipe).getLens().getItem().equals(finalGenericLens.getItem()))
                .filter(recipe -> contentMatch(recipe.getOutputs(), output))
                .filter(recipe -> contentMatch(recipe.getInputs(), input))
                .distinct()
                .collect(Collectors.toList());

        if (!lensNoneRecipes.isEmpty()) {
            MineTweakerAPI.apply(new Remove(lensNoneRecipes));
        } else {
            LogHelper.logWarning(
                    String.format("No %s Recipe found for %s. Command ignored!", name, output.toString()));
        }

    }

    private static boolean contentMatch(List<ItemStack> recipe, IIngredient output) {
        return recipe.stream().anyMatch(item -> matches(output, toIItemStack(item)));
    }


    public static class AddRecipe extends BaseListAddition<LensNoneRecipe> {

        protected AddRecipe(GenericLensRecipe recipe) {
            super(AtomicReconstructor.name, ActuallyAdditionsAPI.reconstructorLensNoneRecipes);
            recipes.add(recipe);
        }

        protected AddRecipe(LensNoneRecipe recipe) {
            super(AtomicReconstructor.name, ActuallyAdditionsAPI.reconstructorLensNoneRecipes);
            recipes.add(recipe);
        }

        @Override
        protected String getRecipeInfo(LensNoneRecipe recipe) {
            return LogHelper.getStackDescription(recipe.getOutputs());
        }

    }

    private static class Remove extends BaseListRemoval<LensNoneRecipe> {

        protected Remove(List<LensNoneRecipe> lensNoneRecipe) {
            super(AtomicReconstructor.name, ActuallyAdditionsAPI.reconstructorLensNoneRecipes, lensNoneRecipe);
        }

        @Override
        protected String getRecipeInfo(LensNoneRecipe recipe) {
            return LogHelper.getStackDescription(recipe.getOutputs());
        }

    }

}
