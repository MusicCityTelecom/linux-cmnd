/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.ivy.core.module.id.ModuleRevisionId
 */
package groovy.grape;

import groovy.lang.GroovyObject;
import groovy.lang.MetaClass;
import groovy.transform.EqualsAndHashCode;
import groovy.transform.Generated;
import groovy.transform.Internal;
import java.beans.Transient;
import java.lang.invoke.MethodHandles;
import java.util.List;
import org.apache.ivy.core.module.id.ModuleRevisionId;
import org.codehaus.groovy.reflection.ClassInfo;
import org.codehaus.groovy.runtime.ScriptBytecodeAdapter;
import org.codehaus.groovy.util.HashCodeHelper;

@EqualsAndHashCode
public class IvyGrabRecord
implements GroovyObject {
    private ModuleRevisionId mrid;
    private List<String> conf;
    private String ext;
    private String type;
    private String classifier;
    private boolean force;
    private boolean changing;
    private boolean transitive;
    private static /* synthetic */ ClassInfo $staticClassInfo;
    public static transient /* synthetic */ boolean __$stMC;
    private transient /* synthetic */ MetaClass metaClass;

    @Generated
    public IvyGrabRecord() {
        MetaClass metaClass;
        this.metaClass = metaClass = this.$getStaticMetaClass();
    }

    @Generated
    public int hashCode() {
        int _result = HashCodeHelper.initHash();
        if (this.getMrid() != this) {
            int n;
            _result = n = HashCodeHelper.updateHash(_result, this.getMrid());
        }
        if (this.getConf() != this) {
            int n;
            _result = n = HashCodeHelper.updateHash(_result, this.getConf());
        }
        if (this.getExt() != this) {
            int n;
            _result = n = HashCodeHelper.updateHash(_result, this.getExt());
        }
        if (this.getType() != this) {
            int n;
            _result = n = HashCodeHelper.updateHash(_result, this.getType());
        }
        if (this.getClassifier() != this) {
            int n;
            _result = n = HashCodeHelper.updateHash(_result, this.getClassifier());
        }
        if (ScriptBytecodeAdapter.compareNotIdentical(this.isForce(), this)) {
            int n;
            _result = n = HashCodeHelper.updateHash(_result, this.isForce());
        }
        if (ScriptBytecodeAdapter.compareNotIdentical(this.isChanging(), this)) {
            int n;
            _result = n = HashCodeHelper.updateHash(_result, this.isChanging());
        }
        if (ScriptBytecodeAdapter.compareNotIdentical(this.isTransitive(), this)) {
            int n;
            _result = n = HashCodeHelper.updateHash(_result, this.isTransitive());
        }
        return _result;
    }

    @Generated
    public boolean canEqual(Object other) {
        return other instanceof IvyGrabRecord;
    }

    @Generated
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (this == other) {
            return true;
        }
        if (!(other instanceof IvyGrabRecord)) {
            return false;
        }
        IvyGrabRecord otherTyped = (IvyGrabRecord)other;
        if (!otherTyped.canEqual(this)) {
            return false;
        }
        if (!ScriptBytecodeAdapter.compareEqual(this.getMrid(), otherTyped.getMrid())) {
            return false;
        }
        if (!ScriptBytecodeAdapter.compareEqual(this.getConf(), otherTyped.getConf())) {
            return false;
        }
        if (!ScriptBytecodeAdapter.compareEqual(this.getExt(), otherTyped.getExt())) {
            return false;
        }
        if (!ScriptBytecodeAdapter.compareEqual(this.getType(), otherTyped.getType())) {
            return false;
        }
        if (!ScriptBytecodeAdapter.compareEqual(this.getClassifier(), otherTyped.getClassifier())) {
            return false;
        }
        if (!ScriptBytecodeAdapter.compareEqual(this.isForce(), otherTyped.isForce())) {
            return false;
        }
        if (!ScriptBytecodeAdapter.compareEqual(this.isChanging(), otherTyped.isChanging())) {
            return false;
        }
        return !(!ScriptBytecodeAdapter.compareEqual(this.isTransitive(), otherTyped.isTransitive()));
    }

    protected /* synthetic */ MetaClass $getStaticMetaClass() {
        if (this.getClass() != IvyGrabRecord.class) {
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

    @Generated
    public ModuleRevisionId getMrid() {
        return this.mrid;
    }

    @Generated
    public void setMrid(ModuleRevisionId moduleRevisionId) {
        this.mrid = moduleRevisionId;
    }

    @Generated
    public List<String> getConf() {
        return this.conf;
    }

    @Generated
    public void setConf(List<String> list) {
        this.conf = list;
    }

    @Generated
    public String getExt() {
        return this.ext;
    }

    @Generated
    public void setExt(String string) {
        this.ext = string;
    }

    @Generated
    public String getType() {
        return this.type;
    }

    @Generated
    public void setType(String string) {
        this.type = string;
    }

    @Generated
    public String getClassifier() {
        return this.classifier;
    }

    @Generated
    public void setClassifier(String string) {
        this.classifier = string;
    }

    @Generated
    public boolean getForce() {
        return this.force;
    }

    @Generated
    public boolean isForce() {
        return this.force;
    }

    @Generated
    public void setForce(boolean bl) {
        this.force = bl;
    }

    @Generated
    public boolean getChanging() {
        return this.changing;
    }

    @Generated
    public boolean isChanging() {
        return this.changing;
    }

    @Generated
    public void setChanging(boolean bl) {
        this.changing = bl;
    }

    @Generated
    public boolean getTransitive() {
        return this.transitive;
    }

    @Generated
    public boolean isTransitive() {
        return this.transitive;
    }

    @Generated
    public void setTransitive(boolean bl) {
        this.transitive = bl;
    }
}

