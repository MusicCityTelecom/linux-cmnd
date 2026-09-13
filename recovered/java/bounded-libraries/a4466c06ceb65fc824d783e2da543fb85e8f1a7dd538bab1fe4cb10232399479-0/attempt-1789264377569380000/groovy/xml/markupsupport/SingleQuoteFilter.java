/*
 * Decompiled with CFR 0.152.
 */
package groovy.xml.markupsupport;

import java.util.Optional;
import java.util.function.Function;

public class SingleQuoteFilter
implements Function<Character, Optional<String>> {
    @Override
    public Optional<String> apply(Character ch) {
        if (ch.charValue() == '\'') {
            return Optional.of("&apos;");
        }
        return Optional.empty();
    }
}

