package modtweaker2.mods.actuallyadditions.helper;

import de.ellpeck.actuallyadditions.api.recipe.LensNoneRecipe;
import lombok.Getter;
import net.minecraft.item.ItemStack;

@Getter
public class GenericLensRecipe extends LensNoneRecipe {

    private final GenericLens lens;

    public GenericLensRecipe(GenericLens lens, ItemStack input, ItemStack output, int energyUse) {
        super(input, output, energyUse);
        this.lens = lens;
    }

    public GenericLensRecipe(GenericLens lens, String input, String output, int energyUse) {
        super(input, output, energyUse);
        this.lens = lens;
    }

}
