package modtweaker2.mixin.mixins.common.actuallyadditions;

import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.api.recipe.LensNoneRecipe;
import de.ellpeck.actuallyadditions.mod.items.lens.LensNoneRecipeHandler;
import modtweaker2.mods.actuallyadditions.helper.GenericLensRecipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.List;
import java.util.stream.Collectors;

@Mixin(value = LensNoneRecipeHandler.class, remap = false)
public class LensNoneRecipeHandlerMixin {

    @Redirect(method = "getRecipesFor", at = @At(value = "FIELD", target = "Lde/ellpeck/actuallyadditions/api/ActuallyAdditionsAPI;reconstructorLensNoneRecipes:Ljava/util/List;"))
    private static List<LensNoneRecipe> getRecipe() {
        return ActuallyAdditionsAPI.reconstructorLensNoneRecipes
                .stream()
                .filter(lensNoneRecipe -> !(lensNoneRecipe instanceof GenericLensRecipe))
                .collect(Collectors.toList());
    }

}
