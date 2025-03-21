package modtweaker2.mixin.mixins.client.actuallyadditions;

import de.ellpeck.actuallyadditions.api.recipe.LensNoneRecipe;
import de.ellpeck.actuallyadditions.mod.nei.NEIReconstructorRecipe;
import modtweaker2.mods.actuallyadditions.helper.CachedReconstructorRecipeAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = NEIReconstructorRecipe.CachedReconstructorRecipe.class, remap = false)
public class CachedReconstructorRecipeMixin implements CachedReconstructorRecipeAccessor {

    @Unique
    private LensNoneRecipe modTweaker$lensNoneRecipe;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void init(NEIReconstructorRecipe this$0, LensNoneRecipe recipe, boolean showColorLens, CallbackInfo ci){
        this.modTweaker$lensNoneRecipe = recipe;
    }

    @Override
    public LensNoneRecipe modTweaker$getLensNoneRecipe() {
        return modTweaker$lensNoneRecipe;
    }

}
