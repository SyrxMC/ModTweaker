package modtweaker2.mixin.mixins.client.actuallyadditions;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.recipe.TemplateRecipeHandler;
import de.ellpeck.actuallyadditions.api.recipe.LensNoneRecipe;
import de.ellpeck.actuallyadditions.mod.nei.NEIReconstructorRecipe;
import modtweaker2.mods.actuallyadditions.helper.CachedReconstructorRecipeAccessor;
import modtweaker2.mods.actuallyadditions.helper.GenericLensRecipe;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = NEIReconstructorRecipe.class, remap = false)
public abstract class NEIReconstructorRecipeMixin extends TemplateRecipeHandler {

    @Inject(method = "drawForeground", at = @At("TAIL"), cancellable = true)
    private void drawForeground(int recipe, CallbackInfo ci) {

        NEIReconstructorRecipe.CachedReconstructorRecipe reconstructorRecipe = (NEIReconstructorRecipe.CachedReconstructorRecipe) this.arecipes.get(recipe);

        if (reconstructorRecipe != null) {

            LensNoneRecipe lensNoneRecipe = ((CachedReconstructorRecipeAccessor) reconstructorRecipe).modTweaker$getLensNoneRecipe();

            if (lensNoneRecipe instanceof GenericLensRecipe) {
                Item item = ((GenericLensRecipe) lensNoneRecipe).getLens().getItem();
                String text = item.getItemStackDisplayName(new ItemStack(item));
                GuiDraw.drawString(text, 0, 44, 4210752, false);
            }

        }

    }

}
