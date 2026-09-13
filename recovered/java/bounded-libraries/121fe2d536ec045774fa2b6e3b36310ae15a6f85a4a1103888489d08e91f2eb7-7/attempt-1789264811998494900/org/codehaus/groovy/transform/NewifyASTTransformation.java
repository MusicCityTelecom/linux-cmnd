/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.transform;

import groovy.lang.Newify;
import groovy.lang.Reference;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import org.codehaus.groovy.GroovyBugError;
import org.codehaus.groovy.ast.ASTNode;
import org.codehaus.groovy.ast.AnnotatedNode;
import org.codehaus.groovy.ast.AnnotationNode;
import org.codehaus.groovy.ast.ClassCodeExpressionTransformer;
import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.FieldNode;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.ast.expr.ArgumentListExpression;
import org.codehaus.groovy.ast.expr.ClassExpression;
import org.codehaus.groovy.ast.expr.ClosureExpression;
import org.codehaus.groovy.ast.expr.ConstantExpression;
import org.codehaus.groovy.ast.expr.ConstructorCallExpression;
import org.codehaus.groovy.ast.expr.DeclarationExpression;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.ListExpression;
import org.codehaus.groovy.ast.expr.MethodCallExpression;
import org.codehaus.groovy.ast.expr.VariableExpression;
import org.codehaus.groovy.ast.tools.GeneralUtils;
import org.codehaus.groovy.control.CompilePhase;
import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.runtime.DefaultGroovyMethods;
import org.codehaus.groovy.transform.ASTTransformation;
import org.codehaus.groovy.transform.GroovyASTTransformation;

@GroovyASTTransformation(phase=CompilePhase.CANONICALIZATION)
public class NewifyASTTransformation
extends ClassCodeExpressionTransformer
implements ASTTransformation {
    private static final ClassNode MY_TYPE = ClassHelper.make(Newify.class);
    private static final String MY_NAME = MY_TYPE.getNameWithoutPackage();
    private static final String BASE_BAD_PARAM_ERROR = "Error during @" + MY_NAME + " processing. Annotation parameter must be a class or list of classes but found ";
    private SourceUnit source;
    private ListExpression classesToNewify;
    private DeclarationExpression candidate;
    private boolean auto;
    private Pattern classNamePattern;
    private static Map<String, ClassNode> nameToGlobalClassesNodesMap;
    private Map<String, NewifyClassData> nameToInnerClassesNodesMap;
    private static final Class[] globalClasses;
    private static final Pattern extractNamePattern;

    public static String extractName(String s) {
        return extractNamePattern.matcher(s).replaceFirst("$1");
    }

    @Override
    public void visit(ASTNode[] nodes, SourceUnit source) {
        this.source = source;
        if (nodes.length != 2 || !(nodes[0] instanceof AnnotationNode) || !(nodes[1] instanceof AnnotatedNode)) {
            NewifyASTTransformation.internalError("Expecting [AnnotationNode, AnnotatedClass] but got: " + Arrays.asList(nodes));
        }
        AnnotatedNode parent = (AnnotatedNode)nodes[1];
        AnnotationNode node = (AnnotationNode)nodes[0];
        if (!MY_TYPE.equals(node.getClassNode())) {
            NewifyASTTransformation.internalError("Transformation called from wrong annotation: " + node.getClassNode().getName());
        }
        boolean autoFlag = NewifyASTTransformation.determineAutoFlag(node.getMember("auto"));
        Expression classNames = node.getMember("value");
        Pattern cnPattern = this.determineClassNamePattern(node.getMember("pattern"));
        if (parent instanceof ClassNode) {
            this.newifyClass((ClassNode)parent, autoFlag, this.determineClasses(classNames, false), cnPattern);
        } else if (parent instanceof MethodNode || parent instanceof FieldNode) {
            this.newifyMethodOrField(parent, autoFlag, this.determineClasses(classNames, false), cnPattern);
        } else if (parent instanceof DeclarationExpression) {
            this.newifyDeclaration((DeclarationExpression)parent, autoFlag, this.determineClasses(classNames, true), cnPattern);
        }
    }

    private void newifyClass(ClassNode cNode, boolean autoFlag, ListExpression list, Pattern cnPattern) {
        String cName = cNode.getName();
        if (cNode.isInterface()) {
            this.addError("Error processing interface '" + cName + "'. @" + MY_NAME + " not allowed for interfaces.", cNode);
        }
        ListExpression oldClassesToNewify = this.classesToNewify;
        boolean oldAuto = this.auto;
        Pattern oldCnPattern = this.classNamePattern;
        this.classesToNewify = list;
        this.auto = autoFlag;
        this.classNamePattern = cnPattern;
        super.visitClass(cNode);
        this.classesToNewify = oldClassesToNewify;
        this.auto = oldAuto;
        this.classNamePattern = oldCnPattern;
    }

    private void newifyMethodOrField(AnnotatedNode parent, boolean autoFlag, ListExpression list, Pattern cnPattern) {
        ListExpression oldClassesToNewify = this.classesToNewify;
        boolean oldAuto = this.auto;
        Pattern oldCnPattern = this.classNamePattern;
        this.checkClassLevelClashes(list);
        this.checkAutoClash(autoFlag, parent);
        this.classesToNewify = list;
        this.auto = autoFlag;
        this.classNamePattern = cnPattern;
        if (parent instanceof FieldNode) {
            super.visitField((FieldNode)parent);
        } else {
            super.visitMethod((MethodNode)parent);
        }
        this.classesToNewify = oldClassesToNewify;
        this.auto = oldAuto;
        this.classNamePattern = oldCnPattern;
    }

    private void newifyDeclaration(DeclarationExpression de, boolean autoFlag, ListExpression list, Pattern cnPattern) {
        ClassNode cNode = de.getDeclaringClass();
        this.candidate = de;
        ListExpression oldClassesToNewify = this.classesToNewify;
        boolean oldAuto = this.auto;
        Pattern oldCnPattern = this.classNamePattern;
        this.classesToNewify = list;
        this.auto = autoFlag;
        this.classNamePattern = cnPattern;
        super.visitClass(cNode);
        this.classesToNewify = oldClassesToNewify;
        this.auto = oldAuto;
        this.classNamePattern = oldCnPattern;
    }

    private static boolean determineAutoFlag(Expression autoExpr) {
        return !(autoExpr instanceof ConstantExpression) || !((ConstantExpression)autoExpr).getValue().equals(false);
    }

    private Pattern determineClassNamePattern(Expression expr) {
        if (!(expr instanceof ConstantExpression)) {
            return null;
        }
        ConstantExpression constExpr = (ConstantExpression)expr;
        String text = constExpr.getText();
        if (constExpr.getValue() == null || text.isEmpty()) {
            return null;
        }
        try {
            Pattern pattern = Pattern.compile(text);
            return pattern;
        }
        catch (PatternSyntaxException e) {
            this.addError("Invalid class name pattern: " + e.getMessage(), expr);
            return null;
        }
    }

    private ListExpression determineClasses(Expression expr, boolean searchSourceUnit) {
        ListExpression list = new ListExpression();
        if (expr instanceof ClassExpression) {
            list.addExpression(expr);
        } else if (expr instanceof VariableExpression && searchSourceUnit) {
            VariableExpression ve = (VariableExpression)expr;
            ClassNode fromSourceUnit = this.getSourceUnitClass(ve);
            if (fromSourceUnit != null) {
                ClassExpression found = GeneralUtils.classX(fromSourceUnit);
                found.setSourcePosition(ve);
                list.addExpression(found);
            } else {
                this.addError(BASE_BAD_PARAM_ERROR + "an unresolvable reference to '" + ve.getName() + "'.", expr);
            }
        } else if (expr instanceof ListExpression) {
            list = (ListExpression)expr;
            List<Expression> expressions = list.getExpressions();
            for (int i = 0; i < expressions.size(); ++i) {
                Expression next = expressions.get(i);
                if (next instanceof VariableExpression && searchSourceUnit) {
                    VariableExpression ve = (VariableExpression)next;
                    ClassNode fromSourceUnit = this.getSourceUnitClass(ve);
                    if (fromSourceUnit != null) {
                        ClassExpression found = GeneralUtils.classX(fromSourceUnit);
                        found.setSourcePosition(ve);
                        expressions.set(i, found);
                        continue;
                    }
                    this.addError(BASE_BAD_PARAM_ERROR + "a list containing an unresolvable reference to '" + ve.getName() + "'.", next);
                    continue;
                }
                if (next instanceof ClassExpression) continue;
                this.addError(BASE_BAD_PARAM_ERROR + "a list containing type: " + next.getType().getName() + ".", next);
            }
            this.checkDuplicateNameClashes(list);
        } else if (expr != null) {
            this.addError(BASE_BAD_PARAM_ERROR + "a type: " + expr.getType().getName() + ".", expr);
        }
        return list;
    }

    private ClassNode getSourceUnitClass(VariableExpression ve) {
        List<ClassNode> classes = this.source.getAST().getClasses();
        for (ClassNode classNode : classes) {
            if (!classNode.getNameWithoutPackage().equals(ve.getName())) continue;
            return classNode;
        }
        return null;
    }

    @Override
    public Expression transform(Expression expr) {
        if (expr == null) {
            return null;
        }
        if (expr instanceof MethodCallExpression && this.candidate == null) {
            MethodCallExpression mce = (MethodCallExpression)expr;
            Expression args = this.transform(mce.getArguments());
            if (this.isNewifyCandidate(mce)) {
                Expression transformed = this.transformMethodCall(mce, args);
                transformed.setSourcePosition(mce);
                return transformed;
            }
            Expression method = this.transform(mce.getMethod());
            Expression object = this.transform(mce.getObjectExpression());
            MethodCallExpression transformed = GeneralUtils.callX(object, method, args);
            transformed.setImplicitThis(mce.isImplicitThis());
            transformed.setSafe(mce.isSafe());
            transformed.setSourcePosition(mce);
            return transformed;
        }
        if (expr instanceof ClosureExpression) {
            ClosureExpression ce = (ClosureExpression)expr;
            ce.getCode().visit(this);
        } else if (expr instanceof ConstructorCallExpression) {
            ConstructorCallExpression cce = (ConstructorCallExpression)expr;
            if (cce.isUsingAnonymousInnerClass()) {
                cce.getType().visitContents(this);
            }
        } else if (expr instanceof DeclarationExpression) {
            DeclarationExpression de = (DeclarationExpression)expr;
            if (this.shouldTransform(de)) {
                this.candidate = null;
                Expression left = de.getLeftExpression();
                Expression right = this.transform(de.getRightExpression());
                DeclarationExpression newDecl = new DeclarationExpression(left, de.getOperation(), right);
                newDecl.addAnnotations(de.getAnnotations());
                return newDecl;
            }
            return de;
        }
        return expr.transformExpression(this);
    }

    private boolean shouldTransform(DeclarationExpression exp) {
        return exp == this.candidate || this.auto || this.hasClassesToNewify();
    }

    private boolean hasClassesToNewify() {
        return this.classesToNewify != null && !this.classesToNewify.getExpressions().isEmpty() || this.classNamePattern != null;
    }

    private void checkDuplicateNameClashes(ListExpression list) {
        HashSet<String> seen = new HashSet<String>();
        List<Expression> classes = list.getExpressions();
        for (ClassExpression classExpression : classes) {
            String name = classExpression.getType().getNameWithoutPackage();
            if (seen.contains(name)) {
                this.addError("Duplicate name '" + name + "' found during @" + MY_NAME + " processing.", classExpression);
            }
            seen.add(name);
        }
    }

    private void checkAutoClash(boolean autoFlag, AnnotatedNode parent) {
        if (this.auto && !autoFlag) {
            this.addError("Error during @" + MY_NAME + " processing. The 'auto' flag can't be false at method/constructor/field level if it is true at the class level.", parent);
        }
    }

    private void checkClassLevelClashes(ListExpression list) {
        List<Expression> classes = list.getExpressions();
        for (ClassExpression classExpression : classes) {
            String name = classExpression.getType().getNameWithoutPackage();
            if (!this.findClassWithMatchingBasename(name)) continue;
            this.addError("Error during @" + MY_NAME + " processing. Class '" + name + "' can't appear at method/constructor/field level if it already appears at the class level.", classExpression);
        }
    }

    private boolean isNewifyCandidate(MethodCallExpression mce) {
        return this.auto && NewifyASTTransformation.isNewMethodStyle(mce) || mce.isImplicitThis() && mce.getObjectExpression() instanceof VariableExpression && ((VariableExpression)mce.getObjectExpression()).isThisExpression();
    }

    private static boolean isNewMethodStyle(MethodCallExpression mce) {
        Expression obj = mce.getObjectExpression();
        Expression meth = mce.getMethod();
        return obj instanceof ClassExpression && meth instanceof ConstantExpression && ((ConstantExpression)meth).getValue().equals("new");
    }

    private Expression transformMethodCall(MethodCallExpression mce, Expression argsExp) {
        ClassNode classType = NewifyASTTransformation.isNewMethodStyle(mce) ? mce.getObjectExpression().getType() : this.findMatchingCandidateClass(mce);
        if (classType != null) {
            Expression argsToUse = argsExp;
            if (classType.getOuterClass() != null && (classType.getModifiers() & 8) == 0) {
                if (!(argsExp instanceof ArgumentListExpression)) {
                    this.addError("Non-static inner constructor arguments must be an argument list expression; pass 'this' pointer explicitly as first constructor argument otherwise.", mce);
                    return mce;
                }
                ArgumentListExpression argsListExp = (ArgumentListExpression)argsExp;
                List<Expression> argExpList = argsListExp.getExpressions();
                VariableExpression thisVarExp = new VariableExpression("this");
                ArrayList<Expression> expressionsWithThis = new ArrayList<Expression>(argExpList.size() + 1);
                expressionsWithThis.add(thisVarExp);
                expressionsWithThis.addAll(argExpList);
                argsToUse = new ArgumentListExpression(expressionsWithThis);
            }
            return new ConstructorCallExpression(classType, argsToUse);
        }
        mce.setArguments(argsExp);
        return mce;
    }

    private boolean findClassWithMatchingBasename(String nameWithoutPackage) {
        if (this.classNamePattern != null && this.classNamePattern.matcher(nameWithoutPackage).matches()) {
            return true;
        }
        if (this.classesToNewify != null) {
            List<Expression> classes = this.classesToNewify.getExpressions();
            for (ClassExpression classExpression : classes) {
                if (!classExpression.getType().getNameWithoutPackage().equals(nameWithoutPackage)) continue;
                return true;
            }
        }
        return false;
    }

    private ClassNode findMatchingCandidateClass(MethodCallExpression mce) {
        String methodName = mce.getMethodAsString();
        if (this.classesToNewify != null) {
            List<Expression> classes = this.classesToNewify.getExpressions();
            for (ClassExpression classExpression : classes) {
                ClassNode type = classExpression.getType();
                if (!type.getNameWithoutPackage().equals(methodName)) continue;
                return type;
            }
        }
        if (this.classNamePattern != null && this.classNamePattern.matcher(methodName).matches()) {
            NewifyClassData innerTypeClassData;
            if (this.nameToInnerClassesNodesMap == null) {
                List<ClassNode> innerClassNodes = this.source.getAST().getClasses();
                this.nameToInnerClassesNodesMap = new HashMap<String, NewifyClassData>(innerClassNodes.size());
                for (ClassNode classNode : innerClassNodes) {
                    String pureClassName = NewifyASTTransformation.extractName(classNode.getNameWithoutPackage());
                    NewifyClassData classData = this.nameToInnerClassesNodesMap.get(pureClassName);
                    if (classData == null) {
                        this.nameToInnerClassesNodesMap.put(pureClassName, new NewifyClassData(pureClassName, classNode));
                        continue;
                    }
                    classData.addAdditionalType(classNode);
                }
            }
            if ((innerTypeClassData = this.nameToInnerClassesNodesMap.get(methodName)) != null) {
                if (innerTypeClassData.types != null) {
                    this.addError("Inner class name lookup is ambiguous between the following classes: " + DefaultGroovyMethods.join(innerTypeClassData.types, ", ") + ". Use new keyword and qualify name to break ambiguity.", mce);
                    return null;
                }
                return innerTypeClassData.type;
            }
            ClassNode importedType = this.source.getAST().getImportType(methodName);
            if (importedType != null) {
                return importedType;
            }
            ClassNode classNode = nameToGlobalClassesNodesMap.get(methodName);
            if (classNode != null) {
                return classNode;
            }
        }
        return null;
    }

    private static void internalError(String message) {
        throw new GroovyBugError("Internal error: " + message);
    }

    @Override
    protected SourceUnit getSourceUnit() {
        return this.source;
    }

    static {
        globalClasses = new Class[]{Object.class, Boolean.TYPE, Character.TYPE, Byte.TYPE, Short.TYPE, Integer.TYPE, Long.TYPE, Double.TYPE, Float.TYPE, String.class, Boolean.class, Character.class, Byte.class, Short.class, Integer.class, Long.class, Double.class, Float.class, BigDecimal.class, BigInteger.class, Reference.class};
        nameToGlobalClassesNodesMap = new ConcurrentHashMap<String, ClassNode>(16, 0.9f, 1);
        for (Class globalClass : globalClasses) {
            nameToGlobalClassesNodesMap.put(globalClass.getSimpleName(), ClassHelper.makeCached(globalClass));
        }
        extractNamePattern = Pattern.compile("^(?:.*\\$|)(.*)$");
    }

    private static class NewifyClassData {
        final String name;
        final ClassNode type;
        List<ClassNode> types;

        public NewifyClassData(String name, ClassNode type) {
            this.name = name;
            this.type = type;
        }

        public void addAdditionalType(ClassNode additionalType) {
            if (this.types == null) {
                this.types = new LinkedList<ClassNode>();
                this.types.add(this.type);
            }
            this.types.add(additionalType);
        }
    }
}

