/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.jexl3.parser;

import java.util.Map;
import org.apache.commons.jexl3.JexlFeatures;
import org.apache.commons.jexl3.internal.Frame;
import org.apache.commons.jexl3.internal.Scope;
import org.apache.commons.jexl3.parser.ASTJexlLambda;
import org.apache.commons.jexl3.parser.JexlLexicalNode;
import org.apache.commons.jexl3.parser.Parser;
import org.apache.commons.jexl3.parser.ParserVisitor;

public class ASTJexlScript
extends JexlLexicalNode {
    private Map<String, Object> pragmas = null;
    private JexlFeatures features = null;
    private Scope scope = null;

    public ASTJexlScript(int id) {
        super(id);
    }

    public ASTJexlScript(Parser p, int id) {
        super(p, id);
    }

    public ASTJexlScript script() {
        if (this.scope == null && this.jjtGetNumChildren() == 1 && this.jjtGetChild(0) instanceof ASTJexlLambda) {
            ASTJexlLambda lambda = (ASTJexlLambda)this.jjtGetChild(0);
            lambda.jjtSetParent(null);
            return lambda;
        }
        return this;
    }

    @Override
    public Object jjtAccept(ParserVisitor visitor, Object data) {
        return visitor.visit(this, data);
    }

    public void setPragmas(Map<String, Object> thePragmas) {
        this.pragmas = thePragmas;
    }

    public Map<String, Object> getPragmas() {
        return this.pragmas;
    }

    public void setFeatures(JexlFeatures theFeatures) {
        this.features = theFeatures;
    }

    public JexlFeatures getFeatures() {
        return this.features;
    }

    public void setScope(Scope theScope) {
        this.scope = theScope;
        if (theScope != null) {
            for (int a = 0; a < theScope.getArgCount(); ++a) {
                this.declareSymbol(a);
            }
        }
    }

    public Scope getScope() {
        return this.scope;
    }

    public Frame createFrame(Frame caller, Object ... values) {
        return this.scope != null ? this.scope.createFrame(caller, values) : null;
    }

    public Frame createFrame(Object ... values) {
        return this.createFrame(null, values);
    }

    public int getArgCount() {
        return this.scope != null ? this.scope.getArgCount() : 0;
    }

    public String[] getSymbols() {
        return this.scope != null ? this.scope.getSymbols() : null;
    }

    public String[] getParameters() {
        return this.scope != null ? this.scope.getParameters() : null;
    }

    public String[] getLocalVariables() {
        return this.scope != null ? this.scope.getLocalVariables() : null;
    }

    public boolean isCapturedSymbol(int symbol) {
        return this.scope != null && this.scope.isCapturedSymbol(symbol);
    }
}

