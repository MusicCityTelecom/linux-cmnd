/*
 * Decompiled with CFR 0.152.
 */
package groovy.transform.options;

import groovy.transform.options.PropertyHandler;
import groovy.transform.stc.POJO;
import java.util.List;
import org.apache.groovy.ast.tools.ConstructorNodeUtils;
import org.codehaus.groovy.ast.AnnotationNode;
import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.FieldNode;
import org.codehaus.groovy.ast.Parameter;
import org.codehaus.groovy.ast.PropertyNode;
import org.codehaus.groovy.ast.expr.ArgumentListExpression;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.MapExpression;
import org.codehaus.groovy.ast.expr.MethodCallExpression;
import org.codehaus.groovy.ast.expr.VariableExpression;
import org.codehaus.groovy.ast.stmt.BlockStatement;
import org.codehaus.groovy.ast.stmt.Statement;
import org.codehaus.groovy.ast.tools.GeneralUtils;
import org.codehaus.groovy.transform.AbstractASTTransformation;
import org.codehaus.groovy.transform.MapConstructorASTTransformation;

public class DefaultPropertyHandler
extends PropertyHandler {
    private static final ClassNode POJO_TYPE = ClassHelper.make(POJO.class);

    @Override
    public boolean validateAttributes(AbstractASTTransformation xform, AnnotationNode anno) {
        boolean success = true;
        return success;
    }

    @Override
    public boolean validateProperties(AbstractASTTransformation xform, BlockStatement body, ClassNode cNode, List<PropertyNode> props) {
        if (xform instanceof MapConstructorASTTransformation) {
            VariableExpression namedArgs = GeneralUtils.varX("args");
            body.addStatement(GeneralUtils.ifS((Expression)GeneralUtils.equalsNullX(namedArgs), GeneralUtils.assignS(namedArgs, new MapExpression())));
            boolean pojo = !cNode.getAnnotations(POJO_TYPE).isEmpty();
            body.addStatement(ConstructorNodeUtils.checkPropNamesS(namedArgs, pojo, props));
        }
        return super.validateProperties(xform, body, cNode, props);
    }

    @Override
    public Statement createPropInit(AbstractASTTransformation xform, AnnotationNode anno, ClassNode cNode, PropertyNode pNode, Parameter namedArgsMap) {
        boolean hasSetter;
        String name = pNode.getName();
        FieldNode fNode = pNode.getField();
        boolean useSetters = xform.memberHasValue(anno, "useSetters", Boolean.TRUE);
        boolean bl = hasSetter = cNode.getProperty(name) != null && !fNode.isFinal();
        if (namedArgsMap != null) {
            return DefaultPropertyHandler.assignFieldS(useSetters, namedArgsMap, name);
        }
        VariableExpression var = GeneralUtils.varX(name);
        if (useSetters && hasSetter) {
            return DefaultPropertyHandler.setViaSetterS(name, var);
        }
        return DefaultPropertyHandler.assignToFieldS(name, var);
    }

    private static Statement assignToFieldS(String name, Expression var) {
        return GeneralUtils.assignS(GeneralUtils.propX((Expression)GeneralUtils.varX("this"), name), var);
    }

    private static Statement setViaSetterS(String name, Expression var) {
        return GeneralUtils.stmt(GeneralUtils.callThisX(GeneralUtils.getSetterName(name), var));
    }

    private static Statement assignFieldS(boolean useSetters, Parameter map, String name) {
        ArgumentListExpression nameArg = GeneralUtils.args(GeneralUtils.constX(name));
        MethodCallExpression var = GeneralUtils.callX((Expression)GeneralUtils.varX(map), "get", (Expression)nameArg);
        var.setImplicitThis(false);
        MethodCallExpression containsKey = GeneralUtils.callX((Expression)GeneralUtils.varX(map), "containsKey", (Expression)nameArg);
        containsKey.setImplicitThis(false);
        return GeneralUtils.ifS((Expression)containsKey, useSetters ? DefaultPropertyHandler.setViaSetterS(name, var) : DefaultPropertyHandler.assignToFieldS(name, var));
    }
}

