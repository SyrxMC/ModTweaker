package modtweaker2.mods.actuallyadditions.handlers;

import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.api.recipe.BallOfFurReturn;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import modtweaker2.helpers.LogHelper;
import modtweaker2.utils.BaseListAddition;
import modtweaker2.utils.BaseListRemoval;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.List;
import java.util.stream.Collectors;

import static modtweaker2.helpers.InputHelper.toIItemStack;
import static modtweaker2.helpers.InputHelper.toStack;
import static modtweaker2.helpers.StackHelper.matches;

@ZenClass("mods.actuallyadditions.BallOfFur")
public class BallOfFur {

    private static final String name = "ActuallyAdditions BallOfFur";

    @ZenMethod
    public static void add(IItemStack returnItem, int chance) {
        MineTweakerAPI.apply(new AddReturn(new BallOfFurReturn(toStack(returnItem), chance)));
    }

    @ZenMethod
    public static void remove(IIngredient output) {

        List<BallOfFurReturn> ballOfFurReturns = ActuallyAdditionsAPI.ballOfFurReturnItems.stream()
                .filter(recipe -> matches(output, toIItemStack(recipe.returnItem)))
                .distinct()
                .collect(Collectors.toList());

        if (!ballOfFurReturns.isEmpty()) {
            MineTweakerAPI.apply(new Remove(ballOfFurReturns));
        } else {
            LogHelper.logWarning(
                    String.format("No %s Recipe found for %s. Command ignored!", name, output.toString()));
        }

    }


    public static class AddReturn extends BaseListAddition<BallOfFurReturn> {

        protected AddReturn(BallOfFurReturn recipe) {
            super(BallOfFur.name, ActuallyAdditionsAPI.ballOfFurReturnItems);
            recipes.add(recipe);
        }

        @Override
        protected String getRecipeInfo(BallOfFurReturn recipe) {
            return LogHelper.getStackDescription(recipe.returnItem);
        }

    }

    private static class Remove extends BaseListRemoval<BallOfFurReturn> {

        public Remove(List<BallOfFurReturn> recipes) {
            super(BallOfFur.name, ActuallyAdditionsAPI.ballOfFurReturnItems, recipes);
        }

        @Override
        public void apply() {

            super.apply();

            if(ActuallyAdditionsAPI.ballOfFurReturnItems.isEmpty()){
                ActuallyAdditionsAPI.addBallOfFurReturnItem(new ItemStack(Items.string), 100);
                LogHelper.logInfo("You removed all possible returns from Ball Of Fur, adding a default string return to avoid problems.");
            }

        }

        @Override
        protected String getRecipeInfo(BallOfFurReturn recipe) {
            return LogHelper.getStackDescription(recipe.returnItem);
        }

    }

}
