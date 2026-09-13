/*
 * Decompiled with CFR 0.152.
 */
package groovy.xml.markupsupport;

import java.util.Optional;
import java.util.function.Function;

public class StandardControlToUndefined
implements Function<Character, Optional<String>> {
    @Override
    public Optional<String> apply(Character ch) {
        if (ch.charValue() < ' ' && !this.isXmlAllowedControl(ch.charValue())) {
            return Optional.of("\ufffd");
        }
        return Optional.empty();
    }

    private boolean isXmlAllowedControl(char ch) {
        return ch == '\t' || ch == '\n' || ch == '\f' || ch == '\r';
    }
}

