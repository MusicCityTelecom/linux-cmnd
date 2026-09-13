/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.jexl3.parser;

import org.apache.commons.jexl3.parser.ASTJexlScript;
import org.apache.commons.jexl3.parser.Parser;

public final class ASTJexlLambda
extends ASTJexlScript {
    ASTJexlLambda(int id) {
        super(id);
    }

    ASTJexlLambda(Parser p, int id) {
        super(p, id);
    }

    public boolean isTopLevel() {
        return this.jjtGetParent() == null;
    }
}

