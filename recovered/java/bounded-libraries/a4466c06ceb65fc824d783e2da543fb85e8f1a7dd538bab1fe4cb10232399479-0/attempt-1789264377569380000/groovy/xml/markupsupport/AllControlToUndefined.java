/*
 * Decompiled with CFR 0.152.
 */
package groovy.xml.markupsupport;

import groovy.xml.markupsupport.StandardControlToUndefined;
import java.util.Optional;

public class AllControlToUndefined
extends StandardControlToUndefined {
    @Override
    public Optional<String> apply(Character ch) {
        if (Character.isISOControl(ch.charValue()) || this.isNonCharacter(ch.charValue())) {
            return Optional.of("\ufffd");
        }
        return super.apply(ch);
    }

    private boolean isNonCharacter(char ch) {
        return '\ufdd0' <= ch && ch <= '\ufdef' || (ch ^ 0xFFFE) == 0 || (ch ^ 0xFFFF) == 0;
    }
}

