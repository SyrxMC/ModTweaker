package modtweaker2.mods.actuallyadditions.handlers;

import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.api.lens.Lens;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IItemStack;
import modtweaker2.helpers.LogHelper;
import modtweaker2.mods.actuallyadditions.helper.GenericLens;
import modtweaker2.utils.BaseListAddition;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import static modtweaker2.helpers.InputHelper.toStack;

@ZenClass("mods.actuallyadditions.Lens")
public class GenericLenses {

    private static final String name = "ActuallyAdditions Lens";

    @ZenMethod
    public static void addLens(IItemStack item, int distance, int color) {

        float RED = ((color >> 16) & 0xFF) / 255F;
        float GREEN = ((color >> 8) & 0xFF) / 255F;
        float BLUE = ((color) & 0xFF) / 255F;

        GenericLens genericLens = new GenericLens(toStack(item).getItem(), distance, new float[]{RED, GREEN, BLUE});
        MineTweakerAPI.apply(new AddLens(genericLens));

    }

    public static class AddLens extends BaseListAddition<Lens> {

        protected AddLens(GenericLens lens) {
            super(GenericLenses.name, ActuallyAdditionsAPI.reconstructorLenses);
            recipes.add(lens);
        }

        @Override
        protected String getRecipeInfo(Lens recipe) {
            return LogHelper.getStackDescription(recipe);
        }

    }


}
