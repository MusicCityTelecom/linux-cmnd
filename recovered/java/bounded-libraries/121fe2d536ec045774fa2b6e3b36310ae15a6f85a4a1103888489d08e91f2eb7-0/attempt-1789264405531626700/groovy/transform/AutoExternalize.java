/*
 * Decompiled with CFR 0.152.
 */
package groovy.transform;

import groovy.lang.GroovyObject;
import groovy.lang.MetaClass;
import groovy.transform.AnnotationCollector;
import groovy.transform.ExternalizeMethods;
import groovy.transform.ExternalizeVerifier;
import groovy.transform.Generated;
import groovy.transform.Internal;
import java.beans.Transient;
import java.lang.invoke.MethodHandles;
import org.codehaus.groovy.reflection.ClassInfo;
import org.codehaus.groovy.runtime.BytecodeInterface8;
import org.codehaus.groovy.runtime.GStringImpl;
import org.codehaus.groovy.runtime.ScriptBytecodeAdapter;
import org.codehaus.groovy.runtime.typehandling.ShortTypeHandling;

@AnnotationCollector(serializeClass=CollectorHelper.class, value={ExternalizeMethods.class, ExternalizeVerifier.class})
public @interface AutoExternalize {

    public static final class CollectorHelper
    implements GroovyObject {
        private static /* synthetic */ ClassInfo $staticClassInfo;
        public static transient /* synthetic */ boolean __$stMC;
        private transient /* synthetic */ MetaClass metaClass;

        @Generated
        public CollectorHelper() {
            MetaClass metaClass;
            this.metaClass = metaClass = this.$getStaticMetaClass();
        }

        @Generated
        public static Object[][] value() {
            return new Object[0][];
        }

        public /* synthetic */ Object methodMissing(String name, Object args) {
            if (!(args instanceof Object[])) {
                return ScriptBytecodeAdapter.invokeMethodN(CollectorHelper.class, AutoExternalize.class, ShortTypeHandling.castToString(new GStringImpl(new Object[]{name}, new String[]{"", ""})), new Object[]{args});
            }
            if (((Object[])ScriptBytecodeAdapter.castToType(args, Object[].class)).length == 1) {
                return ScriptBytecodeAdapter.invokeMethodN(CollectorHelper.class, AutoExternalize.class, ShortTypeHandling.castToString(new GStringImpl(new Object[]{name}, new String[]{"", ""})), new Object[]{BytecodeInterface8.objectArrayGet((Object[])ScriptBytecodeAdapter.castToType(args, Object[].class), 0)});
            }
            return ScriptBytecodeAdapter.invokeMethodN(CollectorHelper.class, AutoExternalize.class, ShortTypeHandling.castToString(new GStringImpl(new Object[]{name}, new String[]{"", ""})), ScriptBytecodeAdapter.despreadList(new Object[0], new Object[]{args}, new int[]{0}));
        }

        public static /* synthetic */ Object $static_methodMissing(String name, Object args) {
            if (!(args instanceof Object[])) {
                return ScriptBytecodeAdapter.invokeMethodN(CollectorHelper.class, AutoExternalize.class, ShortTypeHandling.castToString(new GStringImpl(new Object[]{name}, new String[]{"", ""})), new Object[]{args});
            }
            if (((Object[])ScriptBytecodeAdapter.castToType(args, Object[].class)).length == 1) {
                return ScriptBytecodeAdapter.invokeMethodN(CollectorHelper.class, AutoExternalize.class, ShortTypeHandling.castToString(new GStringImpl(new Object[]{name}, new String[]{"", ""})), new Object[]{BytecodeInterface8.objectArrayGet((Object[])ScriptBytecodeAdapter.castToType(args, Object[].class), 0)});
            }
            return ScriptBytecodeAdapter.invokeMethodN(CollectorHelper.class, AutoExternalize.class, ShortTypeHandling.castToString(new GStringImpl(new Object[]{name}, new String[]{"", ""})), ScriptBytecodeAdapter.despreadList(new Object[0], new Object[]{args}, new int[]{0}));
        }

        public /* synthetic */ void propertyMissing(String name, Object value) {
            Object object = value;
            ScriptBytecodeAdapter.setProperty(object, null, AutoExternalize.class, ShortTypeHandling.castToString(new GStringImpl(new Object[]{name}, new String[]{"", ""})));
        }

        public static /* synthetic */ void $static_propertyMissing(String name, Object value) {
            Object object = value;
            ScriptBytecodeAdapter.setProperty(object, null, AutoExternalize.class, ShortTypeHandling.castToString(new GStringImpl(new Object[]{name}, new String[]{"", ""})));
        }

        public /* synthetic */ Object propertyMissing(String name) {
            return ScriptBytecodeAdapter.getProperty(CollectorHelper.class, AutoExternalize.class, ShortTypeHandling.castToString(new GStringImpl(new Object[]{name}, new String[]{"", ""})));
        }

        public static /* synthetic */ Object $static_propertyMissing(String name) {
            return ScriptBytecodeAdapter.getProperty(CollectorHelper.class, AutoExternalize.class, ShortTypeHandling.castToString(new GStringImpl(new Object[]{name}, new String[]{"", ""})));
        }

        protected /* synthetic */ MetaClass $getStaticMetaClass() {
            if (this.getClass() != CollectorHelper.class) {
                return ScriptBytecodeAdapter.initMetaClass(this);
            }
            ClassInfo classInfo = $staticClassInfo;
            if (classInfo == null) {
                $staticClassInfo = classInfo = ClassInfo.getClassInfo(this.getClass());
            }
            return classInfo.getMetaClass();
        }

        @Override
        @Generated
        @Internal
        @Transient
        public MetaClass getMetaClass() {
            MetaClass metaClass = this.metaClass;
            if (metaClass != null) {
                return metaClass;
            }
            this.metaClass = this.$getStaticMetaClass();
            return this.metaClass;
        }

        @Override
        @Generated
        @Internal
        public void setMetaClass(MetaClass metaClass) {
            this.metaClass = metaClass;
        }

        public static /* synthetic */ MethodHandles.Lookup $getLookup() {
            return MethodHandles.lookup();
        }
    }
}

