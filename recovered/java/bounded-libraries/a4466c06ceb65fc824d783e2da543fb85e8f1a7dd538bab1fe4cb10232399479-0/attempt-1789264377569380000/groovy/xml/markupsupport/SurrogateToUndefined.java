/*
 * Decompiled with CFR 0.152.
 */
package groovy.xml.markupsupport;

import java.util.Optional;
import java.util.function.Function;

public class SurrogateToUndefined
implements Function<Character, Optional<String>> {
    @Override
    public Optional<String> apply(Character ch) {
        if (Character.isSurrogate(ch.charValue())) {
            return Optional.of("\ufffd");
        }
        return Optional.empty();
    }
}

