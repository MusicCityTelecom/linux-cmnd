/*
 * Decompiled with CFR 0.152.
 */
package groovy.xml.markupsupport;

import java.util.Optional;
import java.util.function.Function;

public class StandardXmlFilter
implements Function<Character, Optional<String>> {
    @Override
    public Optional<String> apply(Character ch) {
        String result = null;
        switch (ch.charValue()) {
            case '&': {
                result = "&amp;";
                break;
            }
            case '<': {
                result = "&lt;";
                break;
            }
            case '>': {
                result = "&gt;";
            }
        }
        return Optional.ofNullable(result);
    }
}

