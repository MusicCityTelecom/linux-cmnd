/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.jexl3.parser;

import org.apache.commons.jexl3.parser.ASTIdentifier;

public class ASTNamespaceIdentifier
extends ASTIdentifier {
    private String namespace;

    public ASTNamespaceIdentifier(int id) {
        super(id);
    }

    @Override
    public String getNamespace() {
        return this.namespace;
    }

    public void setNamespace(String ns, String id) {
        this.namespace = ns;
        this.name = id;
    }
}

