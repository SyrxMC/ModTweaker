package modtweaker2.mods.actuallyadditions;

import minetweaker.MineTweakerAPI;
import modtweaker2.mods.actuallyadditions.handlers.BallOfFur;
import modtweaker2.mods.actuallyadditions.handlers.Crusher;

public class ActuallyAdditions {

    public ActuallyAdditions() {
        MineTweakerAPI.registerClass(Crusher.class);
        MineTweakerAPI.registerClass(BallOfFur.class);
    }

}
