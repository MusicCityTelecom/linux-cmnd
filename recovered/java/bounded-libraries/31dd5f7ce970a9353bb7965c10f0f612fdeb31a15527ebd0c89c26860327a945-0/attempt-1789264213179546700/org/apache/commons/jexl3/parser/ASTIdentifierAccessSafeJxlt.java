/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.jexl3.parser;

import org.apache.commons.jexl3.parser.ASTIdentifierAccessJxlt;
import org.apache.commons.jexl3.parser.Parser;

public class ASTIdentifierAccessSafeJxlt
extends ASTIdentifierAccessJxlt {
    ASTIdentifierAccessSafeJxlt(int id) {
        super(id);
    }

    ASTIdentifierAccessSafeJxlt(Parser p, int id) {
        super(p, id);
    }

    @Override
    public boolean isSafe() {
        return true;
    }
}

