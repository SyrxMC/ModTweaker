package modtweaker2.mods.actuallyadditions.handlers;

import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.api.recipe.TreasureChestLoot;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import modtweaker2.helpers.LogHelper;
import modtweaker2.utils.BaseListAddition;
import modtweaker2.utils.BaseListRemoval;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.List;
import java.util.stream.Collectors;

import static modtweaker2.helpers.InputHelper.toIItemStack;
import static modtweaker2.helpers.InputHelper.toStack;
import static modtweaker2.helpers.StackHelper.matches;

@ZenClass("mods.actuallyadditions.TreasureChest")
public class TreasureChest {

    private static final String name = "ActuallyAdditions TreasureChest";

    @ZenMethod
    public static void add(IItemStack returnItem, int chance, int minAmount, int maxAmount) {
        TreasureChestLoot treasureChestLoot = new TreasureChestLoot(toStack(returnItem), chance, minAmount, maxAmount);
        MineTweakerAPI.apply(new AddLoot(treasureChestLoot));
    }

    @ZenMethod
    public static void remove(IIngredient output) {

        List<TreasureChestLoot> crusherRecipes = ActuallyAdditionsAPI.treasureChestLoot.stream()
                .filter(recipe -> matches(output, toIItemStack(recipe.returnItem)))
                .distinct()
                .collect(Collectors.toList());


        if (!crusherRecipes.isEmpty()) {
            MineTweakerAPI.apply(new Remove(crusherRecipes));
        } else {
            LogHelper.logWarning(
                    String.format("No %s Recipe found for %s. Command ignored!", TreasureChest.name, output.toString())
            );
        }

    }

    public static class AddLoot extends BaseListAddition<TreasureChestLoot> {

        protected AddLoot(TreasureChestLoot loot) {
            super(TreasureChest.name, ActuallyAdditionsAPI.treasureChestLoot);
            recipes.add(loot);
        }

        @Override
        protected String getRecipeInfo(TreasureChestLoot recipe) {
            return LogHelper.getStackDescription(recipe.returnItem);
        }

    }

    private static class Remove extends BaseListRemoval<TreasureChestLoot> {

        public Remove(List<TreasureChestLoot> recipes) {
            super(TreasureChest.name, ActuallyAdditionsAPI.treasureChestLoot, recipes);
        }

        @Override
        public void apply() {

            super.apply();

            if (ActuallyAdditionsAPI.treasureChestLoot.isEmpty()) {
                ActuallyAdditionsAPI.addTreasureChestLoot(new ItemStack(Blocks.cobblestone), 1, 1, 1);
                LogHelper.logInfo("You removed all possible returns from Ball Of Fur, adding a default string return to avoid problems.");
            }

        }

        @Override
        protected String getRecipeInfo(TreasureChestLoot recipe) {
            return LogHelper.getStackDescription(recipe.returnItem);
        }

    }

}
