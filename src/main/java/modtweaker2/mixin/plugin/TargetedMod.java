package modtweaker2.mixin.plugin;

import com.falsepattern.lib.mixin.ITargetedMod;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.function.Predicate;

import static com.falsepattern.lib.mixin.ITargetedMod.PredicateHelpers.contains;

@Getter
@RequiredArgsConstructor
public enum TargetedMod implements ITargetedMod {

    ActuallyAdditions("ActuallyAdditions", true, contains("ActuallyAdditions"));

    private final String modName;
    private final boolean loadInDevelopment;
    private final Predicate<String> condition;

}
