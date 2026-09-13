/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.util;

import groovy.lang.Closure;
import groovy.lang.GroovyObject;
import groovy.lang.MetaClass;
import groovy.lang.Reference;
import groovy.transform.Generated;
import groovy.transform.Internal;
import java.beans.Transient;
import java.lang.invoke.MethodHandles;
import org.codehaus.groovy.reflection.ClassInfo;
import org.codehaus.groovy.runtime.DefaultGroovyMethods;
import org.codehaus.groovy.runtime.GeneratedClosure;
import org.codehaus.groovy.runtime.ScriptBytecodeAdapter;
import org.codehaus.groovy.runtime.StringGroovyMethods;
import org.codehaus.groovy.runtime.typehandling.DefaultTypeTransformation;

public class StringUtil
implements GroovyObject {
    private static /* synthetic */ ClassInfo $staticClassInfo;
    public static transient /* synthetic */ boolean __$stMC;
    private transient /* synthetic */ MetaClass metaClass;

    @Generated
    public StringUtil() {
        MetaClass metaClass;
        this.metaClass = metaClass = this.$getStaticMetaClass();
    }

    /*
     * Unable to fully structure code
     */
    public static String tr(String text, String source, String replacement) {
        source = new Reference<String>(source);
        replacement = new Reference<void>(var2_2);
        v0 = text;
        if ((v0 == null ? false : DefaultTypeTransformation.booleanUnbox(v0)) == false) ** GOTO lbl-1000
        v1 = source.get();
        if ((v1 == null ? false : DefaultTypeTransformation.booleanUnbox(v1)) == false) lbl-1000:
        // 2 sources

        {
            v2 = true;
        } else {
            v2 = false;
        }
        if (v2) {
            return text;
        }
        var5_5 = StringUtil.expandHyphen(source.get());
        source.set(var5_5);
        var6_6 = StringUtil.expandHyphen((String)replacement.get());
        replacement.set((void)var6_6);
        var7_7 = StringGroovyMethods.padRight((String)replacement.get(), StringGroovyMethods.size((CharSequence)source.get()), StringGroovyMethods.getAt((String)replacement.get(), -1));
        replacement.set((void)var7_7);
        return DefaultGroovyMethods.join(DefaultGroovyMethods.collect(text, new _tr_closure1(StringUtil.class, StringUtil.class, source, replacement)), "");
    }

    private static String expandHyphen(String text) {
        if (!text.contains("-")) {
            return text;
        }
        public final class _expandHyphen_closure2
        extends Closure
        implements GeneratedClosure {
            private static /* synthetic */ ClassInfo $staticClassInfo;
            public static transient /* synthetic */ boolean __$stMC;

            public _expandHyphen_closure2(Object _outerInstance, Object _thisObject) {
                super(_outerInstance, _thisObject);
            }

            public String doCall(Object all, Object begin, Object end) {
                return DefaultGroovyMethods.join(ScriptBytecodeAdapter.createRange(begin, end, false, false), "");
            }

            @Generated
            public String call(Object all, Object begin, Object end) {
                return this.doCall(all, begin, end);
            }

            protected /* synthetic */ MetaClass $getStaticMetaClass() {
                if (this.getClass() != _expandHyphen_closure2.class) {
                    return ScriptBytecodeAdapter.initMetaClass(this);
                }
                ClassInfo classInfo = $staticClassInfo;
                if (classInfo == null) {
                    $staticClassInfo = classInfo = ClassInfo.getClassInfo(this.getClass());
                }
                return classInfo.getMetaClass();
            }

            public /* synthetic */ MethodHandles.Lookup $getLookup() {
                return MethodHandles.lookup();
            }
        }
        return StringGroovyMethods.replaceAll((CharSequence)text, (CharSequence)"(.)-(.)", (Closure)new _expandHyphen_closure2(StringUtil.class, StringUtil.class));
    }

    protected /* synthetic */ MetaClass $getStaticMetaClass() {
        if (this.getClass() != StringUtil.class) {
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

