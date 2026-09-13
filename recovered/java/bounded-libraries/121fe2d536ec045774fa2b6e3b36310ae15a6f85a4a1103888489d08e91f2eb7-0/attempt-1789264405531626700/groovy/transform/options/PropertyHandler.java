/*
 * Decompiled with CFR 0.152.
 */
package groovy.transform.options;

import groovy.lang.GroovyClassLoader;
import groovy.transform.PropertyOptions;
import groovy.transform.options.DefaultPropertyHandler;
import java.lang.annotation.Annotation;
import java.util.List;
import org.apache.groovy.lang.annotation.Incubating;
import org.codehaus.groovy.ast.AnnotationNode;
import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.Parameter;
import org.codehaus.groovy.ast.PropertyNode;
import org.codehaus.groovy.ast.stmt.BlockStatement;
import org.codehaus.groovy.ast.stmt.Statement;
import org.codehaus.groovy.transform.AbstractASTTransformation;

@Incubating
public abstract class PropertyHandler {
    private static final Class<? extends Annotation> PROPERTY_OPTIONS_CLASS = PropertyOptions.class;
    public static final ClassNode PROPERTY_OPTIONS_TYPE = ClassHelper.makeWithoutCaching(PROPERTY_OPTIONS_CLASS, false);

    public abstract boolean validateAttributes(AbstractASTTransformation var1, AnnotationNode var2);

    public boolean validateProperties(AbstractASTTransformation xform, BlockStatement body, ClassNode cNode, List<PropertyNode> props) {
        return true;
    }

    public abstract Statement createPropInit(AbstractASTTransformation var1, AnnotationNode var2, ClassNode var3, PropertyNode var4, Parameter var5);

    public Statement createPropGetter(PropertyNode pNode) {
        return pNode.getGetterBlock();
    }

    public Statement createPropSetter(PropertyNode pNode) {
        return pNode.getSetterBlock();
    }

    protected boolean isValidAttribute(AbstractASTTransformation xform, AnnotationNode anno, String memberName) {
        if (xform.getMemberValue(anno, memberName) != null) {
            xform.addError("Error during " + xform.getAnnotationName() + " processing: Annotation attribute '" + memberName + "' not supported for property handler " + this.getClass().getSimpleName(), anno);
            return false;
        }
        return true;
    }

    public static PropertyHandler createPropertyHandler(AbstractASTTransformation xform, GroovyClassLoader loader, ClassNode cNode) {
        AnnotationNode anno;
        List<AnnotationNode> annotations = cNode.getAnnotations(PROPERTY_OPTIONS_TYPE);
        AnnotationNode annotationNode = anno = annotations.isEmpty() ? null : annotations.get(0);
        if (anno == null) {
            return new DefaultPropertyHandler();
        }
        ClassNode handlerClass = xform.getMemberClassValue(anno, "propertyHandler", ClassHelper.make(DefaultPropertyHandler.class));
        if (handlerClass == null) {
            xform.addError("Couldn't determine propertyHandler class", anno);
            return null;
        }
        String className = handlerClass.getName();
        try {
            Object instance = loader.loadClass(className).getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
            if (!PropertyHandler.class.isAssignableFrom(instance.getClass())) {
                xform.addError("The propertyHandler class '" + handlerClass.getName() + "' on " + xform.getAnnotationName() + " is not a propertyHandler", anno);
                return null;
            }
            return (PropertyHandler)instance;
        }
        catch (Exception e) {
            xform.addError("Can't load propertyHandler '" + className + "' " + e, anno);
            return null;
        }
    }
}

