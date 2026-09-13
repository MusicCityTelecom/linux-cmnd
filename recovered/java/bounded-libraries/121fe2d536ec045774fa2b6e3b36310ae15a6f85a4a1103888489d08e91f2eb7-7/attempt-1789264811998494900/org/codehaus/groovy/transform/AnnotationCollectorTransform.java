/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.transform;

import groovy.lang.GroovyRuntimeException;
import groovy.transform.AnnotationCollector;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import org.apache.groovy.ast.tools.ClassNodeUtils;
import org.codehaus.groovy.GroovyBugError;
import org.codehaus.groovy.ast.ASTNode;
import org.codehaus.groovy.ast.AnnotatedNode;
import org.codehaus.groovy.ast.AnnotationNode;
import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.InnerClassNode;
import org.codehaus.groovy.ast.Parameter;
import org.codehaus.groovy.ast.expr.AnnotationConstantExpression;
import org.codehaus.groovy.ast.expr.ArrayExpression;
import org.codehaus.groovy.ast.expr.ClassExpression;
import org.codehaus.groovy.ast.expr.ConstantExpression;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.ListExpression;
import org.codehaus.groovy.ast.expr.MapExpression;
import org.codehaus.groovy.ast.stmt.ReturnStatement;
import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.transform.trait.TraitComposer;

public class AnnotationCollectorTransform {
    private static List<AnnotationNode> getMeta(ClassNode cn) {
        List<AnnotationNode> meta = (List<AnnotationNode>)cn.getNodeMetaData(AnnotationCollector.class);
        if (meta == null) {
            meta = cn.isPrimaryClassNode() ? AnnotationCollectorTransform.getTargetListFromAnnotations(cn) : AnnotationCollectorTransform.getTargetListFromClass(cn);
            cn.setNodeMetaData(AnnotationCollector.class, meta);
        }
        return meta;
    }

    protected void addError(String message, ASTNode node, SourceUnit source) {
        source.getErrorCollector().addErrorAndContinue(message, node, source);
    }

    private List<AnnotationNode> getTargetListFromValue(AnnotationNode collector, AnnotationNode aliasAnnotationUsage, SourceUnit source) {
        Expression memberValue = collector.getMember("value");
        if (memberValue == null) {
            return Collections.emptyList();
        }
        if (!(memberValue instanceof ListExpression)) {
            this.addError("Annotation collector expected a list of classes, but got a " + memberValue.getClass(), collector, source);
            return Collections.emptyList();
        }
        ListExpression memberListExp = (ListExpression)memberValue;
        List<Expression> memberList = memberListExp.getExpressions();
        if (memberList.isEmpty()) {
            return Collections.emptyList();
        }
        ArrayList<AnnotationNode> ret = new ArrayList<AnnotationNode>();
        for (Expression e : memberList) {
            AnnotationNode toAdd = new AnnotationNode(e.getType());
            toAdd.setSourcePosition(aliasAnnotationUsage);
            ret.add(toAdd);
        }
        return ret;
    }

    private static List<AnnotationNode> getStoredTargetList(AnnotationNode aliasAnnotationUsage, SourceUnit source) {
        ClassNode alias = aliasAnnotationUsage.getClassNode().redirect();
        List<AnnotationNode> ret = AnnotationCollectorTransform.getMeta(alias);
        return AnnotationCollectorTransform.copy(ret, aliasAnnotationUsage);
    }

    private static List<AnnotationNode> copy(List<AnnotationNode> orig, AnnotationNode aliasAnnotationUsage) {
        if (orig.isEmpty()) {
            return orig;
        }
        ArrayList<AnnotationNode> ret = new ArrayList<AnnotationNode>(orig.size());
        for (AnnotationNode an : orig) {
            AnnotationNode newAn = new AnnotationNode(an.getClassNode());
            AnnotationCollectorTransform.copyMembers(an, newAn);
            newAn.setSourcePosition(aliasAnnotationUsage);
            ret.add(newAn);
        }
        return ret;
    }

    private static List<AnnotationNode> getTargetListFromAnnotations(ClassNode alias) {
        List<AnnotationNode> annotations = alias.getAnnotations();
        if (annotations.size() < 2) {
            return Collections.emptyList();
        }
        ArrayList<AnnotationNode> ret = new ArrayList<AnnotationNode>(annotations.size());
        for (AnnotationNode an : annotations) {
            ClassNode type = an.getClassNode();
            if (type.getName().equals(AnnotationCollector.class.getName()) || "java.lang.annotation".equals(type.getPackageName()) || "org.apache.groovy.lang.annotation.Incubating".equals(type.getName())) continue;
            AnnotationNode toAdd = new AnnotationNode(type);
            AnnotationCollectorTransform.copyMembers(an, toAdd);
            ret.add(toAdd);
        }
        return ret;
    }

    private static void copyMembers(AnnotationNode from, AnnotationNode to) {
        Map<String, Expression> members = from.getMembers();
        AnnotationCollectorTransform.copyMembers(members, to);
    }

    private static void copyMembers(Map<String, Expression> members, AnnotationNode to) {
        for (Map.Entry<String, Expression> entry : members.entrySet()) {
            to.addMember(entry.getKey(), entry.getValue());
        }
    }

    private static List<AnnotationNode> getTargetListFromClass(ClassNode alias) {
        ClassNode cn = AnnotationCollectorTransform.getSerializeClass(alias);
        Class c = cn.getTypeClass();
        try {
            Method m = c.getMethod("value", new Class[0]);
            if (!Modifier.isStatic(m.getModifiers())) {
                throw new NoSuchMethodException("non-static value()");
            }
            Object[][] data = (Object[][])m.invoke(null, new Object[0]);
            return AnnotationCollectorTransform.makeListOfAnnotations(data);
        }
        catch (ClassCastException | NoSuchMethodException e) {
            throw new GroovyRuntimeException("Expecting static method `Object[][] value()` in " + cn.toString(false) + ". Was it compiled from a Java source?");
        }
        catch (Exception e) {
            throw new GroovyBugError(e);
        }
    }

    private static ClassNode getSerializeClass(ClassNode alias) {
        List<AnnotationNode> collectors = alias.getAnnotations(ClassHelper.make(AnnotationCollector.class));
        if (!collectors.isEmpty()) {
            ClassNode serializeClassType;
            assert (collectors.size() == 1);
            AnnotationNode collectorNode = collectors.get(0);
            Expression serializeClass = collectorNode.getMember("serializeClass");
            if (serializeClass instanceof ClassExpression && !(serializeClassType = serializeClass.getType()).getName().equals(AnnotationCollector.class.getName())) {
                return serializeClassType;
            }
        }
        return alias;
    }

    private static List<AnnotationNode> makeListOfAnnotations(Object[][] data) {
        if (data.length == 0) {
            return Collections.emptyList();
        }
        ArrayList<AnnotationNode> ret = new ArrayList<AnnotationNode>(data.length);
        for (Object[] inner : data) {
            Class anno = (Class)inner[0];
            AnnotationNode toAdd = new AnnotationNode(ClassHelper.make(anno));
            ret.add(toAdd);
            Map member = (Map)inner[1];
            if (member.isEmpty()) continue;
            HashMap<String, Expression> generated = new HashMap<String, Expression>(member.size());
            for (Map.Entry entry : member.entrySet()) {
                generated.put((String)entry.getKey(), AnnotationCollectorTransform.makeExpression(entry.getValue()));
            }
            AnnotationCollectorTransform.copyMembers(generated, toAdd);
        }
        return ret;
    }

    private static Expression makeExpression(Object o) {
        if (o instanceof Class) {
            return new ClassExpression(ClassHelper.make((Class)o));
        }
        if (o instanceof Object[][]) {
            List<AnnotationNode> annotations = AnnotationCollectorTransform.makeListOfAnnotations((Object[][])o);
            ListExpression le = new ListExpression();
            for (AnnotationNode an : annotations) {
                le.addExpression(new AnnotationConstantExpression(an));
            }
            return le;
        }
        if (o instanceof Object[]) {
            Object[] values;
            ListExpression le = new ListExpression();
            for (Object val : values = (Object[])o) {
                le.addExpression(AnnotationCollectorTransform.makeExpression(val));
            }
            return le;
        }
        return new ConstantExpression(o, true);
    }

    protected List<AnnotationNode> getTargetAnnotationList(AnnotationNode collector, AnnotationNode aliasAnnotationUsage, SourceUnit source) {
        List<AnnotationNode> stored = AnnotationCollectorTransform.getStoredTargetList(aliasAnnotationUsage, source);
        List<AnnotationNode> targetList = this.getTargetListFromValue(collector, aliasAnnotationUsage, source);
        int size = targetList.size() + stored.size();
        if (size == 0) {
            return Collections.emptyList();
        }
        ArrayList<AnnotationNode> ret = new ArrayList<AnnotationNode>(size);
        ret.addAll(stored);
        ret.addAll(targetList);
        return ret;
    }

    public List<AnnotationNode> visit(AnnotationNode collector, AnnotationNode aliasAnnotationUsage, AnnotatedNode aliasAnnotated, SourceUnit source) {
        List<AnnotationNode> ret = this.getTargetAnnotationList(collector, aliasAnnotationUsage, source);
        HashSet<String> unusedNames = new HashSet<String>(aliasAnnotationUsage.getMembers().keySet());
        for (AnnotationNode an : ret) {
            for (String name : aliasAnnotationUsage.getMembers().keySet()) {
                if (!an.getClassNode().hasMethod(name, Parameter.EMPTY_ARRAY)) continue;
                unusedNames.remove(name);
                an.setMember(name, aliasAnnotationUsage.getMember(name));
            }
        }
        if (!unusedNames.isEmpty()) {
            String message = "Annotation collector got unmapped names " + ((Object)unusedNames).toString() + ".";
            this.addError(message, aliasAnnotationUsage, source);
        }
        return ret;
    }

    public static class ClassChanger {
        public void transformClass(ClassNode cn) {
            AnnotationNode collector = null;
            for (AnnotationNode an : cn.getAnnotations()) {
                if (!an.getClassNode().getName().equals(AnnotationCollector.class.getName())) continue;
                collector = an;
                break;
            }
            if (collector == null) {
                return;
            }
            boolean legacySerialization = false;
            Expression member = collector.getMember("serializeClass");
            if (member instanceof ClassExpression) {
                ClassExpression ce = (ClassExpression)member;
                legacySerialization = ce.getType().getName().equals(cn.getName());
            }
            ClassNode helper = cn;
            if (legacySerialization) {
                helper.setModifiers((0x10 | helper.getModifiers()) & 0xFFFF99FF);
                helper.setSuperClass(ClassHelper.OBJECT_TYPE);
                helper.setInterfaces(ClassNode.EMPTY_ARRAY);
            } else {
                helper = new InnerClassNode(cn, cn.getName() + "$CollectorHelper", 25, ClassHelper.OBJECT_TYPE.getPlainNodeReference());
                cn.getModule().addClass(helper);
                helper.addAnnotation(new AnnotationNode(TraitComposer.COMPILESTATIC_CLASSNODE));
                collector.setMember("serializeClass", new ClassExpression(helper.getPlainNodeReference()));
            }
            List meta = AnnotationCollectorTransform.getMeta(cn);
            ArrayList<Expression> outer = new ArrayList<Expression>(meta.size());
            for (AnnotationNode an : meta) {
                Expression serialized = this.serialize(an);
                outer.add(serialized);
            }
            ArrayExpression ae = new ArrayExpression(ClassHelper.OBJECT_TYPE.makeArray(), outer);
            ReturnStatement code = new ReturnStatement(ae);
            ClassNodeUtils.addGeneratedMethod(helper, "value", 9, ClassHelper.OBJECT_TYPE.makeArray().makeArray(), Parameter.EMPTY_ARRAY, ClassNode.EMPTY_ARRAY, code);
            ListIterator<AnnotationNode> it = cn.getAnnotations().listIterator();
            while (it.hasNext()) {
                AnnotationNode an = it.next();
                if (an == collector || "java.lang.annotation".equals(an.getClassNode().getPackageName())) continue;
                it.remove();
            }
        }

        private Expression serialize(Expression e) {
            if (e instanceof AnnotationConstantExpression) {
                AnnotationConstantExpression ace = (AnnotationConstantExpression)e;
                return this.serialize((AnnotationNode)ace.getValue());
            }
            if (e instanceof ListExpression) {
                boolean annotationConstant = false;
                ListExpression le = (ListExpression)e;
                List<Expression> list = le.getExpressions();
                ArrayList<Expression> newList = new ArrayList<Expression>(list.size());
                for (Expression exp : list) {
                    annotationConstant = annotationConstant || exp instanceof AnnotationConstantExpression;
                    newList.add(this.serialize(exp));
                }
                ClassNode type = ClassHelper.OBJECT_TYPE;
                if (annotationConstant) {
                    type = type.makeArray();
                }
                return new ArrayExpression(type, newList);
            }
            return e;
        }

        private Expression serialize(AnnotationNode an) {
            ClassExpression type = new ClassExpression(an.getClassNode());
            type.setSourcePosition(an.getClassNode());
            MapExpression map = new MapExpression();
            for (Map.Entry<String, Expression> entry : an.getMembers().entrySet()) {
                ConstantExpression key = new ConstantExpression(entry.getKey());
                Expression val = this.serialize(entry.getValue());
                map.addMapEntryExpression(key, val);
            }
            return new ArrayExpression(ClassHelper.OBJECT_TYPE, Arrays.asList(type, map));
        }
    }
}

