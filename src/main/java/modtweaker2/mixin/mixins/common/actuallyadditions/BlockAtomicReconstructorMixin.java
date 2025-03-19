package modtweaker2.mixin.mixins.common.actuallyadditions;

import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.api.lens.ILensItem;
import de.ellpeck.actuallyadditions.mod.blocks.BlockAtomicReconstructor;
import de.ellpeck.actuallyadditions.mod.blocks.base.BlockContainerBase;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityAtomicReconstructor;
import modtweaker2.mods.actuallyadditions.helper.GenericLens;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRedstoneTorch;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = BlockAtomicReconstructor.class, remap = false)
public abstract class BlockAtomicReconstructorMixin extends BlockContainerBase {

    public BlockAtomicReconstructorMixin(Material material, String name) {
        super(material, name);
    }

    @Inject(method = "onBlockActivated(Lnet/minecraft/world/World;IIILnet/minecraft/entity/player/EntityPlayer;IFFF)Z", at = @At("HEAD"), cancellable = true)
    public void onActivate(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9, CallbackInfoReturnable<Boolean> cir) {

        if (world.isRemote)
            return;

        ItemStack stack = player.getCurrentEquippedItem();

        if (stack != null && Block.getBlockFromItem(stack.getItem()) instanceof BlockRedstoneTorch)
            return;

        TileEntityAtomicReconstructor reconstructor = (TileEntityAtomicReconstructor) world.getTileEntity(x, y, z);

        if (reconstructor == null || reconstructor.getStackInSlot(0) != null)
            return;

        if (stack != null && !(stack.getItem() instanceof ILensItem)) {
            if (ActuallyAdditionsAPI.reconstructorLenses.stream()
                    .filter(el -> el instanceof GenericLens)
                    .anyMatch(el -> ((GenericLens) el).getItem().equals(stack.getItem()))
            ) {
                ItemStack toPut = stack.copy();
                toPut.stackSize = 1;
                reconstructor.setInventorySlotContents(0, toPut);
                player.inventory.decrStackSize(player.inventory.currentItem, 1);
                cir.setReturnValue(true);
            }
        }
    }

}
