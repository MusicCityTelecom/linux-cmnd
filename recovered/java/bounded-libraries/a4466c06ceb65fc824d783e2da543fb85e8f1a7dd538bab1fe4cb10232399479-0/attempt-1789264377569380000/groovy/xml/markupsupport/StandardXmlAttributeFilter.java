/*
 * Decompiled with CFR 0.152.
 */
package groovy.xml.markupsupport;

import java.util.Optional;
import java.util.function.Function;

public class StandardXmlAttributeFilter
implements Function<Character, Optional<String>> {
    @Override
    public Optional<String> apply(Character ch) {
        String result = null;
        switch (ch.charValue()) {
            case '\n': {
                result = "&#10;";
                break;
            }
            case '\r': {
                result = "&#13;";
                break;
            }
            case '\t': {
                result = "&#09;";
            }
        }
        return Optional.ofNullable(result);
    }
}

