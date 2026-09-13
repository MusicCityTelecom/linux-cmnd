/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.hql.internal.ast.tree;

import org.hibernate.hql.internal.ast.tree.FromElement;

public class ImpliedFromElement
extends FromElement {
    private boolean impliedInFromClause;
    private boolean inProjectionList;

    @Override
    public boolean isImplied() {
        return true;
    }

    @Override
    public void setImpliedInFromClause(boolean flag) {
        this.impliedInFromClause = flag;
    }

    @Override
    public boolean isImpliedInFromClause() {
        return this.impliedInFromClause;
    }

    @Override
    public void setInProjectionList(boolean inProjectionList) {
        this.inProjectionList = inProjectionList;
    }

    @Override
    public boolean inProjectionList() {
        return this.inProjectionList && this.isFromOrJoinFragment();
    }

    @Override
    public boolean isIncludeSubclasses() {
        return false;
    }

    @Override
    public String getDisplayText() {
        StringBuilder buf = new StringBuilder();
        buf.append("ImpliedFromElement{");
        this.appendDisplayText(buf);
        buf.append("}");
        return buf.toString();
    }
}

