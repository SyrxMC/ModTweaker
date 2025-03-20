package modtweaker2.mods.actuallyadditions;

import minetweaker.MineTweakerAPI;
import modtweaker2.mods.actuallyadditions.handlers.*;

public class ActuallyAdditions {

    public ActuallyAdditions() {
        MineTweakerAPI.registerClass(Crusher.class);
        MineTweakerAPI.registerClass(BallOfFur.class);
        MineTweakerAPI.registerClass(TreasureChest.class);
        MineTweakerAPI.registerClass(GenericLenses.class);
        MineTweakerAPI.registerClass(AtomicReconstructor.class);
    }

}
