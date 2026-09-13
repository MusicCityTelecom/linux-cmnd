/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.jexl3.parser;

import org.apache.commons.jexl3.parser.ASTAddNode;
import org.apache.commons.jexl3.parser.ASTAmbiguous;
import org.apache.commons.jexl3.parser.ASTAndNode;
import org.apache.commons.jexl3.parser.ASTAnnotatedStatement;
import org.apache.commons.jexl3.parser.ASTAnnotation;
import org.apache.commons.jexl3.parser.ASTArguments;
import org.apache.commons.jexl3.parser.ASTArrayAccess;
import org.apache.commons.jexl3.parser.ASTArrayLiteral;
import org.apache.commons.jexl3.parser.ASTAssignment;
import org.apache.commons.jexl3.parser.ASTBitwiseAndNode;
import org.apache.commons.jexl3.parser.ASTBitwiseComplNode;
import org.apache.commons.jexl3.parser.ASTBitwiseOrNode;
import org.apache.commons.jexl3.parser.ASTBitwiseXorNode;
import org.apache.commons.jexl3.parser.ASTBlock;
import org.apache.commons.jexl3.parser.ASTBreak;
import org.apache.commons.jexl3.parser.ASTConstructorNode;
import org.apache.commons.jexl3.parser.ASTContinue;
import org.apache.commons.jexl3.parser.ASTDivNode;
import org.apache.commons.jexl3.parser.ASTDoWhileStatement;
import org.apache.commons.jexl3.parser.ASTEQNode;
import org.apache.commons.jexl3.parser.ASTERNode;
import org.apache.commons.jexl3.parser.ASTEWNode;
import org.apache.commons.jexl3.parser.ASTEmptyFunction;
import org.apache.commons.jexl3.parser.ASTExtendedLiteral;
import org.apache.commons.jexl3.parser.ASTFalseNode;
import org.apache.commons.jexl3.parser.ASTForeachStatement;
import org.apache.commons.jexl3.parser.ASTFunctionNode;
import org.apache.commons.jexl3.parser.ASTGENode;
import org.apache.commons.jexl3.parser.ASTGTNode;
import org.apache.commons.jexl3.parser.ASTIdentifier;
import org.apache.commons.jexl3.parser.ASTIdentifierAccess;
import org.apache.commons.jexl3.parser.ASTIfStatement;
import org.apache.commons.jexl3.parser.ASTJexlScript;
import org.apache.commons.jexl3.parser.ASTJxltLiteral;
import org.apache.commons.jexl3.parser.ASTLENode;
import org.apache.commons.jexl3.parser.ASTLTNode;
import org.apache.commons.jexl3.parser.ASTMapEntry;
import org.apache.commons.jexl3.parser.ASTMapLiteral;
import org.apache.commons.jexl3.parser.ASTMethodNode;
import org.apache.commons.jexl3.parser.ASTModNode;
import org.apache.commons.jexl3.parser.ASTMulNode;
import org.apache.commons.jexl3.parser.ASTNENode;
import org.apache.commons.jexl3.parser.ASTNEWNode;
import org.apache.commons.jexl3.parser.ASTNRNode;
import org.apache.commons.jexl3.parser.ASTNSWNode;
import org.apache.commons.jexl3.parser.ASTNotNode;
import org.apache.commons.jexl3.parser.ASTNullLiteral;
import org.apache.commons.jexl3.parser.ASTNullpNode;
import org.apache.commons.jexl3.parser.ASTNumberLiteral;
import org.apache.commons.jexl3.parser.ASTOrNode;
import org.apache.commons.jexl3.parser.ASTRangeNode;
import org.apache.commons.jexl3.parser.ASTReference;
import org.apache.commons.jexl3.parser.ASTReferenceExpression;
import org.apache.commons.jexl3.parser.ASTRegexLiteral;
import org.apache.commons.jexl3.parser.ASTReturnStatement;
import org.apache.commons.jexl3.parser.ASTSWNode;
import org.apache.commons.jexl3.parser.ASTSetAddNode;
import org.apache.commons.jexl3.parser.ASTSetAndNode;
import org.apache.commons.jexl3.parser.ASTSetDivNode;
import org.apache.commons.jexl3.parser.ASTSetLiteral;
import org.apache.commons.jexl3.parser.ASTSetModNode;
import org.apache.commons.jexl3.parser.ASTSetMultNode;
import org.apache.commons.jexl3.parser.ASTSetOrNode;
import org.apache.commons.jexl3.parser.ASTSetSubNode;
import org.apache.commons.jexl3.parser.ASTSetXorNode;
import org.apache.commons.jexl3.parser.ASTSizeFunction;
import org.apache.commons.jexl3.parser.ASTStringLiteral;
import org.apache.commons.jexl3.parser.ASTSubNode;
import org.apache.commons.jexl3.parser.ASTTernaryNode;
import org.apache.commons.jexl3.parser.ASTTrueNode;
import org.apache.commons.jexl3.parser.ASTUnaryMinusNode;
import org.apache.commons.jexl3.parser.ASTUnaryPlusNode;
import org.apache.commons.jexl3.parser.ASTVar;
import org.apache.commons.jexl3.parser.ASTWhileStatement;
import org.apache.commons.jexl3.parser.SimpleNode;

public abstract class ParserVisitor {
    protected final Object visit(SimpleNode node, Object data) {
        throw new UnsupportedOperationException(node.getClass().getSimpleName() + " : not supported yet.");
    }

    protected final Object visit(ASTAmbiguous node, Object data) {
        throw new UnsupportedOperationException("unexpected type of node");
    }

    protected abstract Object visit(ASTJexlScript var1, Object var2);

    protected abstract Object visit(ASTBlock var1, Object var2);

    protected abstract Object visit(ASTIfStatement var1, Object var2);

    protected abstract Object visit(ASTWhileStatement var1, Object var2);

    protected abstract Object visit(ASTDoWhileStatement var1, Object var2);

    protected abstract Object visit(ASTContinue var1, Object var2);

    protected abstract Object visit(ASTBreak var1, Object var2);

    protected abstract Object visit(ASTForeachStatement var1, Object var2);

    protected abstract Object visit(ASTReturnStatement var1, Object var2);

    protected abstract Object visit(ASTAssignment var1, Object var2);

    protected abstract Object visit(ASTVar var1, Object var2);

    protected abstract Object visit(ASTReference var1, Object var2);

    protected abstract Object visit(ASTTernaryNode var1, Object var2);

    protected abstract Object visit(ASTNullpNode var1, Object var2);

    protected abstract Object visit(ASTOrNode var1, Object var2);

    protected abstract Object visit(ASTAndNode var1, Object var2);

    protected abstract Object visit(ASTBitwiseOrNode var1, Object var2);

    protected abstract Object visit(ASTBitwiseXorNode var1, Object var2);

    protected abstract Object visit(ASTBitwiseAndNode var1, Object var2);

    protected abstract Object visit(ASTEQNode var1, Object var2);

    protected abstract Object visit(ASTNENode var1, Object var2);

    protected abstract Object visit(ASTLTNode var1, Object var2);

    protected abstract Object visit(ASTGTNode var1, Object var2);

    protected abstract Object visit(ASTLENode var1, Object var2);

    protected abstract Object visit(ASTGENode var1, Object var2);

    protected abstract Object visit(ASTERNode var1, Object var2);

    protected abstract Object visit(ASTNRNode var1, Object var2);

    protected abstract Object visit(ASTSWNode var1, Object var2);

    protected abstract Object visit(ASTNSWNode var1, Object var2);

    protected abstract Object visit(ASTEWNode var1, Object var2);

    protected abstract Object visit(ASTNEWNode var1, Object var2);

    protected abstract Object visit(ASTAddNode var1, Object var2);

    protected abstract Object visit(ASTSubNode var1, Object var2);

    protected abstract Object visit(ASTMulNode var1, Object var2);

    protected abstract Object visit(ASTDivNode var1, Object var2);

    protected abstract Object visit(ASTModNode var1, Object var2);

    protected abstract Object visit(ASTUnaryMinusNode var1, Object var2);

    protected abstract Object visit(ASTUnaryPlusNode var1, Object var2);

    protected abstract Object visit(ASTBitwiseComplNode var1, Object var2);

    protected abstract Object visit(ASTNotNode var1, Object var2);

    protected abstract Object visit(ASTIdentifier var1, Object var2);

    protected abstract Object visit(ASTNullLiteral var1, Object var2);

    protected abstract Object visit(ASTTrueNode var1, Object var2);

    protected abstract Object visit(ASTFalseNode var1, Object var2);

    protected abstract Object visit(ASTNumberLiteral var1, Object var2);

    protected abstract Object visit(ASTStringLiteral var1, Object var2);

    protected abstract Object visit(ASTRegexLiteral var1, Object var2);

    protected abstract Object visit(ASTSetLiteral var1, Object var2);

    protected abstract Object visit(ASTExtendedLiteral var1, Object var2);

    protected abstract Object visit(ASTArrayLiteral var1, Object var2);

    protected abstract Object visit(ASTRangeNode var1, Object var2);

    protected abstract Object visit(ASTMapLiteral var1, Object var2);

    protected abstract Object visit(ASTMapEntry var1, Object var2);

    protected abstract Object visit(ASTEmptyFunction var1, Object var2);

    protected abstract Object visit(ASTSizeFunction var1, Object var2);

    protected abstract Object visit(ASTFunctionNode var1, Object var2);

    protected abstract Object visit(ASTMethodNode var1, Object var2);

    protected abstract Object visit(ASTConstructorNode var1, Object var2);

    protected abstract Object visit(ASTArrayAccess var1, Object var2);

    protected abstract Object visit(ASTIdentifierAccess var1, Object var2);

    protected abstract Object visit(ASTArguments var1, Object var2);

    protected abstract Object visit(ASTReferenceExpression var1, Object var2);

    protected abstract Object visit(ASTSetAddNode var1, Object var2);

    protected abstract Object visit(ASTSetSubNode var1, Object var2);

    protected abstract Object visit(ASTSetMultNode var1, Object var2);

    protected abstract Object visit(ASTSetDivNode var1, Object var2);

    protected abstract Object visit(ASTSetModNode var1, Object var2);

    protected abstract Object visit(ASTSetAndNode var1, Object var2);

    protected abstract Object visit(ASTSetOrNode var1, Object var2);

    protected abstract Object visit(ASTSetXorNode var1, Object var2);

    protected abstract Object visit(ASTJxltLiteral var1, Object var2);

    protected abstract Object visit(ASTAnnotation var1, Object var2);

    protected abstract Object visit(ASTAnnotatedStatement var1, Object var2);
}

