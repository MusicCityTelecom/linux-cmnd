/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.ast.stmt;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.codehaus.groovy.ast.ASTNode;

public class Statement
extends ASTNode {
    private List<String> statementLabels;

    public List<String> getStatementLabels() {
        return this.statementLabels;
    }

    @Deprecated
    public String getStatementLabel() {
        return this.statementLabels == null ? null : this.statementLabels.get(0);
    }

    public void setStatementLabel(String label) {
        if (label != null) {
            this.addStatementLabel(label);
        }
    }

    public void addStatementLabel(String label) {
        if (this.statementLabels == null) {
            this.statementLabels = new LinkedList<String>();
        }
        this.statementLabels.add(Objects.requireNonNull(label));
    }

    public void copyStatementLabels(Statement that) {
        Optional.ofNullable(that.getStatementLabels()).ifPresent(labels -> labels.forEach(this::addStatementLabel));
    }

    public boolean isEmpty() {
        return false;
    }
}

