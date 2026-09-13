/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.jexl3.parser;

import org.apache.commons.jexl3.JexlException;
import org.apache.commons.jexl3.JexlFeatures;
import org.apache.commons.jexl3.JexlInfo;
import org.apache.commons.jexl3.internal.ScriptVisitor;
import org.apache.commons.jexl3.parser.ASTAnnotation;
import org.apache.commons.jexl3.parser.ASTArrayAccess;
import org.apache.commons.jexl3.parser.ASTArrayLiteral;
import org.apache.commons.jexl3.parser.ASTAssignment;
import org.apache.commons.jexl3.parser.ASTConstructorNode;
import org.apache.commons.jexl3.parser.ASTDoWhileStatement;
import org.apache.commons.jexl3.parser.ASTForeachStatement;
import org.apache.commons.jexl3.parser.ASTMapLiteral;
import org.apache.commons.jexl3.parser.ASTMethodNode;
import org.apache.commons.jexl3.parser.ASTNumberLiteral;
import org.apache.commons.jexl3.parser.ASTRangeNode;
import org.apache.commons.jexl3.parser.ASTSetAddNode;
import org.apache.commons.jexl3.parser.ASTSetAndNode;
import org.apache.commons.jexl3.parser.ASTSetDivNode;
import org.apache.commons.jexl3.parser.ASTSetLiteral;
import org.apache.commons.jexl3.parser.ASTSetMultNode;
import org.apache.commons.jexl3.parser.ASTSetOrNode;
import org.apache.commons.jexl3.parser.ASTSetSubNode;
import org.apache.commons.jexl3.parser.ASTSetXorNode;
import org.apache.commons.jexl3.parser.ASTStringLiteral;
import org.apache.commons.jexl3.parser.ASTWhileStatement;
import org.apache.commons.jexl3.parser.JexlNode;

public class FeatureController
extends ScriptVisitor {
    private JexlFeatures features = null;

    public FeatureController(JexlFeatures features) {
        this.features = features;
    }

    public void setFeatures(JexlFeatures fdesc) {
        this.features = fdesc;
    }

    public JexlFeatures getFeatures() {
        return this.features;
    }

    public void controlNode(JexlNode node) {
        node.jjtAccept(this, null);
    }

    @Override
    protected Object visitNode(JexlNode node, Object data) {
        return data;
    }

    public void throwFeatureException(int feature, JexlNode node) {
        JexlInfo dbgInfo = node.jexlInfo();
        throw new JexlException.Feature(dbgInfo, feature, "");
    }

    private boolean isArrayReferenceLiteral(JexlNode child) {
        if (child instanceof ASTStringLiteral) {
            return true;
        }
        return child instanceof ASTNumberLiteral && ((ASTNumberLiteral)child).isInteger();
    }

    @Override
    protected Object visit(ASTArrayAccess node, Object data) {
        if (!this.features.supportsArrayReferenceExpr()) {
            for (int i = 0; i < node.jjtGetNumChildren(); ++i) {
                JexlNode child = node.jjtGetChild(i);
                if (this.isArrayReferenceLiteral(child)) continue;
                this.throwFeatureException(5, child);
            }
        }
        return data;
    }

    @Override
    protected Object visit(ASTWhileStatement node, Object data) {
        if (!this.features.supportsLoops()) {
            this.throwFeatureException(7, node);
        }
        return data;
    }

    @Override
    protected Object visit(ASTDoWhileStatement node, Object data) {
        if (!this.features.supportsLoops()) {
            this.throwFeatureException(7, node);
        }
        return data;
    }

    @Override
    protected Object visit(ASTForeachStatement node, Object data) {
        if (!this.features.supportsLoops()) {
            this.throwFeatureException(7, node);
        }
        return data;
    }

    @Override
    protected Object visit(ASTConstructorNode node, Object data) {
        if (!this.features.supportsNewInstance()) {
            this.throwFeatureException(6, node);
        }
        return data;
    }

    @Override
    protected Object visit(ASTMethodNode node, Object data) {
        if (!this.features.supportsMethodCall()) {
            this.throwFeatureException(9, node);
        }
        return data;
    }

    @Override
    protected Object visit(ASTAnnotation node, Object data) {
        if (!this.features.supportsAnnotation()) {
            this.throwFeatureException(12, node);
        }
        return data;
    }

    @Override
    protected Object visit(ASTArrayLiteral node, Object data) {
        if (!this.features.supportsStructuredLiteral()) {
            this.throwFeatureException(10, node);
        }
        return data;
    }

    @Override
    protected Object visit(ASTMapLiteral node, Object data) {
        if (!this.features.supportsStructuredLiteral()) {
            this.throwFeatureException(10, node);
        }
        return data;
    }

    @Override
    protected Object visit(ASTSetLiteral node, Object data) {
        if (!this.features.supportsStructuredLiteral()) {
            this.throwFeatureException(10, node);
        }
        return data;
    }

    @Override
    protected Object visit(ASTRangeNode node, Object data) {
        if (!this.features.supportsStructuredLiteral()) {
            this.throwFeatureException(10, node);
        }
        return data;
    }

    private Object controlSideEffect(JexlNode node, Object data) {
        JexlNode lv = node.jjtGetChild(0);
        if (!this.features.supportsSideEffectGlobal() && lv.isGlobalVar()) {
            this.throwFeatureException(4, lv);
        }
        if (!this.features.supportsSideEffect()) {
            this.throwFeatureException(3, lv);
        }
        return data;
    }

    @Override
    protected Object visit(ASTAssignment node, Object data) {
        return this.controlSideEffect(node, data);
    }

    @Override
    protected Object visit(ASTSetAddNode node, Object data) {
        return this.controlSideEffect(node, data);
    }

    @Override
    protected Object visit(ASTSetMultNode node, Object data) {
        return this.controlSideEffect(node, data);
    }

    @Override
    protected Object visit(ASTSetDivNode node, Object data) {
        return this.controlSideEffect(node, data);
    }

    @Override
    protected Object visit(ASTSetAndNode node, Object data) {
        return this.controlSideEffect(node, data);
    }

    @Override
    protected Object visit(ASTSetOrNode node, Object data) {
        return this.controlSideEffect(node, data);
    }

    @Override
    protected Object visit(ASTSetXorNode node, Object data) {
        return this.controlSideEffect(node, data);
    }

    @Override
    protected Object visit(ASTSetSubNode node, Object data) {
        return this.controlSideEffect(node, data);
    }
}

