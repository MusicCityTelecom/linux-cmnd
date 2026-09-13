/*
 * Decompiled with CFR 0.152.
 */
package groovy.transform.options;

import groovy.transform.options.ImmutablePropertyHandler;
import java.util.HashMap;
import java.util.List;
import org.codehaus.groovy.ast.ASTNode;
import org.codehaus.groovy.ast.AnnotationNode;
import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.FieldNode;
import org.codehaus.groovy.ast.Parameter;
import org.codehaus.groovy.ast.PropertyNode;
import org.codehaus.groovy.ast.expr.ConstantExpression;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.VariableExpression;
import org.codehaus.groovy.ast.stmt.BlockStatement;
import org.codehaus.groovy.ast.stmt.Statement;
import org.codehaus.groovy.ast.tools.GeneralUtils;
import org.codehaus.groovy.transform.AbstractASTTransformation;
import org.codehaus.groovy.transform.TupleConstructorASTTransformation;

public class LegacyHashMapPropertyHandler
extends ImmutablePropertyHandler {
    private static final ClassNode HMAP_TYPE = ClassHelper.makeWithoutCaching(HashMap.class, false);

    private Statement createLegacyConstructorStatementMapSpecial(FieldNode fNode) {
        VariableExpression fieldExpr = GeneralUtils.varX(fNode);
        ClassNode fieldType = ((Expression)fieldExpr).getType();
        Expression initExpr = fNode.getInitialValueExpression();
        Statement assignInit = initExpr == null || initExpr instanceof ConstantExpression && ((ConstantExpression)initExpr).isNullExpression() ? GeneralUtils.assignS(fieldExpr, ConstantExpression.EMPTY_EXPRESSION) : GeneralUtils.assignS(fieldExpr, this.cloneCollectionExpr(initExpr, fieldType));
        Expression namedArgs = GeneralUtils.findArg(fNode.getName());
        VariableExpression baseArgs = GeneralUtils.varX("args");
        Statement assignStmt = GeneralUtils.ifElseS(GeneralUtils.equalsNullX(namedArgs), GeneralUtils.ifElseS(GeneralUtils.isTrueX(GeneralUtils.callX((Expression)baseArgs, "containsKey", (Expression)GeneralUtils.constX(fNode.getName()))), GeneralUtils.assignS(fieldExpr, namedArgs), GeneralUtils.assignS(fieldExpr, this.cloneCollectionExpr(baseArgs, fieldType))), GeneralUtils.ifElseS(GeneralUtils.isOneX(GeneralUtils.callX(baseArgs, "size")), GeneralUtils.assignS(fieldExpr, this.cloneCollectionExpr(namedArgs, fieldType)), GeneralUtils.assignS(fieldExpr, this.cloneCollectionExpr(baseArgs, fieldType))));
        return GeneralUtils.ifElseS(GeneralUtils.equalsNullX(baseArgs), assignInit, assignStmt);
    }

    @Override
    public boolean validateAttributes(AbstractASTTransformation xform, AnnotationNode anno) {
        return !(xform instanceof TupleConstructorASTTransformation) && super.validateAttributes(xform, anno);
    }

    @Override
    public boolean validateProperties(AbstractASTTransformation xform, BlockStatement body, ClassNode cNode, List<PropertyNode> props) {
        if (props.size() != 1 || !props.get(0).getType().equals(HMAP_TYPE)) {
            xform.addError("Error during " + xform.getAnnotationName() + " processing. Property handler " + this.getClass().getName() + " only accepts a single HashMap property", props.size() == 1 ? (ASTNode)props.get(0) : cNode);
            return false;
        }
        return true;
    }

    @Override
    public Statement createPropInit(AbstractASTTransformation xform, AnnotationNode anno, ClassNode cNode, PropertyNode pNode, Parameter namedArgsMap) {
        FieldNode fNode = pNode.getField();
        if (fNode.isFinal() && fNode.isStatic()) {
            return null;
        }
        if (fNode.isFinal() && fNode.getInitialExpression() != null) {
            return this.checkFinalArgNotOverridden(cNode, fNode);
        }
        return this.createLegacyConstructorStatementMapSpecial(fNode);
    }
}

