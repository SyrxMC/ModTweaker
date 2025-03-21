package modtweaker2.mods.actuallyadditions.helper;

import de.ellpeck.actuallyadditions.api.Position;
import de.ellpeck.actuallyadditions.api.internal.IAtomicReconstructor;
import de.ellpeck.actuallyadditions.api.lens.ILensItem;
import de.ellpeck.actuallyadditions.api.lens.Lens;
import de.ellpeck.actuallyadditions.api.recipe.LensNoneRecipe;
import lombok.Getter;
import modtweaker2.mods.actuallyadditions.ActuallyAdditionsExtension;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;

import java.util.ArrayList;
import java.util.List;

@Getter
public class GenericLens extends Lens implements ILensItem {

    private final int distance;
    private final float[] color;

    public GenericLens(Item item, int distance, float[] color) {
        super();
        this.lensItem = item;
        this.distance = distance;
        this.color = color;
    }

    public Lens getLens() {
        return this;
    }

    public Item getItem() {
        return this.lensItem;
    }

    public boolean invoke(Position hitBlock, IAtomicReconstructor tile) {

        if (hitBlock != null && !hitBlock.getBlock(tile.getWorld()).isAir(tile.getWorld(), hitBlock.getX(), hitBlock.getY(), hitBlock.getZ())) {

            int range = 2;

            for (int reachX = -range; reachX < range + 1; ++reachX) {
                for (int reachZ = -range; reachZ < range + 1; ++reachZ) {
                    for (int reachY = -range; reachY < range + 1; ++reachY) {

                        Position pos = new Position(hitBlock.getX() + reachX, hitBlock.getY() + reachY, hitBlock.getZ() + reachZ);

                        ArrayList<GenericLensRecipe> recipes = ActuallyAdditionsExtension.getRecipesFor(
                                new ItemStack(pos.getBlock(tile.getWorld()), 1, pos.getMetadata(tile.getWorld())), this
                        );

                        for (GenericLensRecipe recipe : recipes) {
                            if (recipe != null && tile.getEnergy() >= recipe.energyUse) {
                                List<ItemStack> outputs = recipe.getOutputs();
                                if (outputs != null && !outputs.isEmpty()) {
                                    ItemStack output = outputs.get(0);
                                    if (output.getItem() instanceof ItemBlock) {
                                        tile.getWorld().playAuxSFX(2001, pos.getX(), pos.getY(), pos.getZ(), Block.getIdFromBlock(pos.getBlock(tile.getWorld())) + (pos.getMetadata(tile.getWorld()) << 12));
                                        pos.setBlock(tile.getWorld(), Block.getBlockFromItem(output.getItem()), output.getItemDamage(), 2);
                                    } else {
                                        EntityItem item = new EntityItem(tile.getWorld(), (double) pos.getX() + (double) 0.5F, (double) pos.getY() + (double) 0.5F, (double) pos.getZ() + (double) 0.5F, output.copy());
                                        tile.getWorld().spawnEntityInWorld(item);
                                    }

                                    tile.extractEnergy(recipe.energyUse);
                                    break;
                                }
                            }
                        }

                    }
                }
            }

            for (EntityItem item : tile.getWorld().getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getBoundingBox(hitBlock.getX() - range, hitBlock.getY() - range, hitBlock.getZ() - range, hitBlock.getX() + range, hitBlock.getY() + range, hitBlock.getZ() + range))) {
                ItemStack stack = item.getEntityItem();
                if (stack != null) {
                    for (GenericLensRecipe recipe : ActuallyAdditionsExtension.getRecipesFor(stack, this)) {
                        if (recipe != null && tile.getEnergy() >= recipe.energyUse) {
                            List<ItemStack> outputs = recipe.getOutputs();
                            if (outputs != null && !outputs.isEmpty()) {
                                ItemStack outputCopy = outputs.get(0).copy();
                                outputCopy.stackSize = stack.stackSize;
                                item.setEntityItemStack(outputCopy);
                                tile.extractEnergy(recipe.energyUse);
                                break;
                            }
                        }
                    }
                }
            }

            return true;
        } else {
            return false;
        }
    }

}
