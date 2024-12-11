package modtweaker2.stubpackage.naruto1310.extendedWorkbench.crafting;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.List;

public class ExtendedShapelessRecipes implements IExtendedRecipe {

    public ExtendedShapelessRecipes(ItemStack output, List<ItemStack> input) {
        throw new UnsupportedOperationException();
    }

    public boolean matches(InventoryCrafting inventory, World world) {
        throw new UnsupportedOperationException();
    }

    public ItemStack getCraftingResult(InventoryCrafting inventory) {
        throw new UnsupportedOperationException();
    }

    public int getRecipeSize() {
        throw new UnsupportedOperationException();
    }

    public ItemStack getRecipeOutput() {
        throw new UnsupportedOperationException();
    }

}
