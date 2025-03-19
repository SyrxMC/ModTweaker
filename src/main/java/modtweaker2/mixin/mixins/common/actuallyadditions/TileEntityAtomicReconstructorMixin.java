package modtweaker2.mixin.mixins.common.actuallyadditions;

import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.api.lens.ILensItem;
import de.ellpeck.actuallyadditions.api.lens.Lens;
import de.ellpeck.actuallyadditions.mod.items.lens.Lenses;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityAtomicReconstructor;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityInventoryBase;
import modtweaker2.mods.actuallyadditions.helper.GenericLens;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = TileEntityAtomicReconstructor.class, remap = false)
public abstract class TileEntityAtomicReconstructorMixin extends TileEntityInventoryBase {

    public TileEntityAtomicReconstructorMixin(int slots, String name) {
        super(slots, name);
    }

    @Inject(method = "isItemValidForSlot(ILnet/minecraft/item/ItemStack;)Z", at = @At("HEAD"), cancellable = true)
    public void isValid(int i, ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        System.out.println("IS VALID: " + stack);
        if (ActuallyAdditionsAPI.reconstructorLenses.stream().filter(el -> el instanceof GenericLens).anyMatch(el -> ((GenericLens) el).getItem().equals(stack.getItem()))) {
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "getCurrentLens", at = @At("HEAD"), cancellable = true)
    public void getLens(CallbackInfoReturnable<Lens> cir) {

        if (this.slots[0] == null){
            cir.setReturnValue(Lenses.LENS_NONE);
            return;
        }

        Item item = this.slots[0].getItem();
        System.out.println(item);

        if (item instanceof ILensItem){
            cir.setReturnValue((((ILensItem) item).getLens()));
            return;
        }

        for (Lens lens : ActuallyAdditionsAPI.reconstructorLenses)
            if (lens instanceof GenericLens && ((GenericLens) lens).getItem().equals(item)){
                cir.setReturnValue(lens);
                return;
            }

        cir.setReturnValue(Lenses.LENS_NONE);
    }


}
