package modtweaker2.mixin.plugin;

import com.falsepattern.lib.mixin.IMixin;
import com.falsepattern.lib.mixin.IMixinPlugin;
import com.falsepattern.lib.mixin.ITargetedMod;
import lombok.Getter;
import modtweaker2.ModProps;
import org.apache.logging.log4j.Logger;

public class MixinPlugin implements IMixinPlugin {

    public static final Logger LOG = IMixinPlugin.createLogger(ModProps.NAME);

    @Getter
    private final Logger logger = LOG;

    @Override
    public IMixin[] getMixinEnumValues() {
        return Mixin.values();
    }

    @Override
    public ITargetedMod[] getTargetedModEnumValues() {
        return TargetedMod.values();
    }

}
