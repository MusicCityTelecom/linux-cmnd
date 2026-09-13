/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.jexl3.parser;

import org.apache.commons.jexl3.internal.ScriptVisitor;
import org.apache.commons.jexl3.parser.ASTAddNode;
import org.apache.commons.jexl3.parser.ASTBitwiseAndNode;
import org.apache.commons.jexl3.parser.ASTBitwiseComplNode;
import org.apache.commons.jexl3.parser.ASTBitwiseOrNode;
import org.apache.commons.jexl3.parser.ASTBitwiseXorNode;
import org.apache.commons.jexl3.parser.ASTDivNode;
import org.apache.commons.jexl3.parser.ASTEQNode;
import org.apache.commons.jexl3.parser.ASTERNode;
import org.apache.commons.jexl3.parser.ASTEWNode;
import org.apache.commons.jexl3.parser.ASTGENode;
import org.apache.commons.jexl3.parser.ASTGTNode;
import org.apache.commons.jexl3.parser.ASTLENode;
import org.apache.commons.jexl3.parser.ASTLTNode;
import org.apache.commons.jexl3.parser.ASTModNode;
import org.apache.commons.jexl3.parser.ASTMulNode;
import org.apache.commons.jexl3.parser.ASTNENode;
import org.apache.commons.jexl3.parser.ASTNEWNode;
import org.apache.commons.jexl3.parser.ASTNRNode;
import org.apache.commons.jexl3.parser.ASTNSWNode;
import org.apache.commons.jexl3.parser.ASTNotNode;
import org.apache.commons.jexl3.parser.ASTSWNode;
import org.apache.commons.jexl3.parser.ASTSetAddNode;
import org.apache.commons.jexl3.parser.ASTSetAndNode;
import org.apache.commons.jexl3.parser.ASTSetDivNode;
import org.apache.commons.jexl3.parser.ASTSetModNode;
import org.apache.commons.jexl3.parser.ASTSetMultNode;
import org.apache.commons.jexl3.parser.ASTSetOrNode;
import org.apache.commons.jexl3.parser.ASTSetSubNode;
import org.apache.commons.jexl3.parser.ASTSetXorNode;
import org.apache.commons.jexl3.parser.ASTSubNode;
import org.apache.commons.jexl3.parser.JexlNode;

class OperatorController
extends ScriptVisitor {
    static final OperatorController INSTANCE = new OperatorController();

    OperatorController() {
    }

    boolean control(JexlNode node, Boolean safe) {
        return Boolean.TRUE.equals(node.jjtAccept(this, safe));
    }

    @Override
    protected Object visitNode(JexlNode node, Object data) {
        return false;
    }

    @Override
    protected Object visit(ASTNotNode node, Object data) {
        return true;
    }

    @Override
    protected Object visit(ASTAddNode node, Object data) {
        return true;
    }

    @Override
    protected Object visit(ASTSetAddNode node, Object data) {
        return true;
    }

    @Override
    protected Object visit(ASTMulNode node, Object data) {
        return true;
    }

    @Override
    protected Object visit(ASTSetMultNode node, Object data) {
        return true;
    }

    @Override
    protected Object visit(ASTModNode node, Object data) {
        return true;
    }

    @Override
    protected Object visit(ASTSetModNode node, Object data) {
        return true;
    }

    @Override
    protected Object visit(ASTDivNode node, Object data) {
        return true;
    }

    @Override
    protected Object visit(ASTSetDivNode node, Object data) {
        return true;
    }

    @Override
    protected Object visit(ASTBitwiseAndNode node, Object data) {
        return true;
    }

    @Override
    protected Object visit(ASTSetAndNode node, Object data) {
        return true;
    }

    @Override
    protected Object visit(ASTBitwiseOrNode node, Object data) {
        return true;
    }

    @Override
    protected Object visit(ASTSetOrNode node, Object data) {
        return true;
    }

    @Override
    protected Object visit(ASTBitwiseXorNode node, Object data) {
        return true;
    }

    @Override
    protected Object visit(ASTSetXorNode node, Object data) {
        return true;
    }

    @Override
    protected Object visit(ASTBitwiseComplNode node, Object data) {
        return true;
    }

    @Override
    protected Object visit(ASTSubNode node, Object data) {
        return true;
    }

    @Override
    protected Object visit(ASTSetSubNode node, Object data) {
        return true;
    }

    @Override
    protected Object visit(ASTEQNode node, Object data) {
        return data;
    }

    @Override
    protected Object visit(ASTNENode node, Object data) {
        return data;
    }

    @Override
    protected Object visit(ASTGTNode node, Object data) {
        return true;
    }

    @Override
    protected Object visit(ASTGENode node, Object data) {
        return true;
    }

    @Override
    protected Object visit(ASTLTNode node, Object data) {
        return true;
    }

    @Override
    protected Object visit(ASTLENode node, Object data) {
        return true;
    }

    @Override
    protected Object visit(ASTSWNode node, Object data) {
        return true;
    }

    @Override
    protected Object visit(ASTNSWNode node, Object data) {
        return true;
    }

    @Override
    protected Object visit(ASTEWNode node, Object data) {
        return true;
    }

    @Override
    protected Object visit(ASTNEWNode node, Object data) {
        return true;
    }

    @Override
    protected Object visit(ASTERNode node, Object data) {
        return true;
    }

    @Override
    protected Object visit(ASTNRNode node, Object data) {
        return true;
    }
}

