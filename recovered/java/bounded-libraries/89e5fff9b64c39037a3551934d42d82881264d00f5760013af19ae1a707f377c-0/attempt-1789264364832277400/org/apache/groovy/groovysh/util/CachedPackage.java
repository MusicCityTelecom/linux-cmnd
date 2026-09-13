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
package org.apache.groovy.groovysh.util;

import groovy.lang.GroovyObject;
import groovy.lang.MetaClass;
import groovy.transform.Generated;
import groovy.transform.Internal;
import java.beans.Transient;
import java.lang.invoke.MethodHandles;
import java.net.URL;
import java.util.Map;
import java.util.Set;
import org.codehaus.groovy.reflection.ClassInfo;
import org.codehaus.groovy.runtime.ScriptBytecodeAdapter;

public class CachedPackage
implements GroovyObject {
    private String name;
    private boolean containsClasses;
    private boolean checked;
    private Map<String, CachedPackage> childPackages;
    private Set<URL> sources;
    private static /* synthetic */ ClassInfo $staticClassInfo;
    public static transient /* synthetic */ boolean __$stMC;
    private transient /* synthetic */ MetaClass metaClass;

    public CachedPackage(String name, Set<URL> sources) {
        String string;
        MetaClass metaClass;
        this.metaClass = metaClass = this.$getStaticMetaClass();
        this.name = string = name;
        Set<URL> set = sources;
        this.sources = set;
    }

    protected /* synthetic */ MetaClass $getStaticMetaClass() {
        if (this.getClass() != CachedPackage.class) {
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

    @Generated
    public String getName() {
        return this.name;
    }

    @Generated
    public void setName(String string) {
        this.name = string;
    }

    @Generated
    public boolean getContainsClasses() {
        return this.containsClasses;
    }

    @Generated
    public boolean isContainsClasses() {
        return this.containsClasses;
    }

    @Generated
    public void setContainsClasses(boolean bl) {
        this.containsClasses = bl;
    }

    @Generated
    public boolean getChecked() {
        return this.checked;
    }

    @Generated
    public boolean isChecked() {
        return this.checked;
    }

    @Generated
    public void setChecked(boolean bl) {
        this.checked = bl;
    }

    @Generated
    public Map<String, CachedPackage> getChildPackages() {
        return this.childPackages;
    }

    @Generated
    public void setChildPackages(Map<String, CachedPackage> map) {
        this.childPackages = map;
    }

    @Generated
    public Set<URL> getSources() {
        return this.sources;
    }

    @Generated
    public void setSources(Set<URL> set) {
        this.sources = set;
    }
}

