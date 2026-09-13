/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  groovy.lang.GroovyObject
 *  groovy.lang.MetaClass
 *  groovy.transform.Generated
 *  groovy.transform.Internal
 *  org.codehaus.groovy.reflection.ClassInfo
 *  org.codehaus.groovy.runtime.ScriptBytecodeAdapter
 */
package org.apache.groovy.groovysh.completion;

import groovy.lang.GroovyObject;
import groovy.lang.MetaClass;
import groovy.transform.Generated;
import groovy.transform.Internal;
import java.beans.Transient;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.codehaus.groovy.reflection.ClassInfo;
import org.codehaus.groovy.runtime.ScriptBytecodeAdapter;

public class ReflectionCompletionCandidate
implements Comparable<ReflectionCompletionCandidate>,
GroovyObject {
    private final String value;
    private final List<String> jAnsiCodes;
    private static /* synthetic */ ClassInfo $staticClassInfo;
    public static transient /* synthetic */ boolean __$stMC;
    private transient /* synthetic */ MetaClass metaClass;

    public ReflectionCompletionCandidate(String value, String ... jAnsiCodes) {
        String string;
        MetaClass metaClass;
        this.metaClass = metaClass = this.$getStaticMetaClass();
        this.value = string = value;
        ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(jAnsiCodes));
        this.jAnsiCodes = arrayList;
    }

    public String getValue() {
        return this.value;
    }

    public List<String> getjAnsiCodes() {
        return this.jAnsiCodes;
    }

    @Override
    public int compareTo(ReflectionCompletionCandidate o) {
        boolean hasBracket = this.value.contains("(");
        boolean otherBracket = o.getValue().contains("(");
        if (ScriptBytecodeAdapter.compareEqual((Object)hasBracket, (Object)otherBracket)) {
            return this.value.compareTo(o.getValue());
        }
        if (hasBracket && !otherBracket) {
            return -1;
        }
        return 1;
    }

    public String toString() {
        return this.value;
    }

    public int hashCode() {
        return this.value.hashCode();
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (ScriptBytecodeAdapter.compareNotEqual(this.getClass(), o.getClass())) {
            return false;
        }
        ReflectionCompletionCandidate that = (ReflectionCompletionCandidate)ScriptBytecodeAdapter.castToType((Object)o, ReflectionCompletionCandidate.class);
        return ScriptBytecodeAdapter.compareEqual((Object)this.value, (Object)that.getValue());
    }

    protected /* synthetic */ MetaClass $getStaticMetaClass() {
        if (this.getClass() != ReflectionCompletionCandidate.class) {
            return ScriptBytecodeAdapter.initMetaClass((Object)this);
        }
        ClassInfo classInfo = $staticClassInfo;
        if (classInfo == null) {
            $staticClassInfo = classInfo = ClassInfo.getClassInfo(this.getClass());
        }
        return classInfo.getMetaClass();
    }

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

    @Generated
    @Internal
    public void setMetaClass(MetaClass metaClass) {
        this.metaClass = metaClass;
    }

    public static /* synthetic */ MethodHandles.Lookup $getLookup() {
        return MethodHandles.lookup();
    }
}

