package modtweaker2.mixin.plugin;

import com.falsepattern.lib.mixin.IMixin;
import com.falsepattern.lib.mixin.ITargetedMod;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.function.Predicate;

import static com.falsepattern.lib.mixin.IMixin.PredicateHelpers.require;

@Getter
@RequiredArgsConstructor
public enum Mixin implements IMixin {

    ActuallyAdditionsTileEntityAtomicReconstructorMixin(Side.COMMON, require(TargetedMod.ActuallyAdditions), "actuallyadditions.TileEntityAtomicReconstructorMixin"),
    ActuallyAdditionsBlockAtomicReconstructorMixin(Side.COMMON, require(TargetedMod.ActuallyAdditions), "actuallyadditions.BlockAtomicReconstructorMixin"),
    ActuallyAdditionsLensNoneRecipeHandlerMixin(Side.COMMON, require(TargetedMod.ActuallyAdditions), "actuallyadditions.LensNoneRecipeHandlerMixin"),
    ActuallyAdditionsRenderReconstructorLensMixin(Side.CLIENT, require(TargetedMod.ActuallyAdditions), "actuallyadditions.RenderReconstructorLensMixin"),
    ActuallyAdditionsCachedReconstructorRecipeMixin(Side.CLIENT, require(TargetedMod.ActuallyAdditions), "actuallyadditions.CachedReconstructorRecipeMixin"),
    ActuallyAdditionsNEIReconstructorRecipeMixin(Side.CLIENT, require(TargetedMod.ActuallyAdditions), "actuallyadditions.NEIReconstructorRecipeMixin");

    private final Side side;
    private final Predicate<List<ITargetedMod>> filter;
    private final String mixin;
}
