/*
 * Decompiled with CFR 0.152.
 */
package groovy.util;

import groovy.lang.Closure;
import groovy.lang.DelegatesTo;
import groovy.lang.GroovyObject;
import groovy.lang.MetaClass;
import groovy.transform.Generated;
import groovy.transform.Internal;
import java.beans.Transient;
import java.io.File;
import java.lang.invoke.MethodHandles;
import java.lang.ref.SoftReference;
import org.codehaus.groovy.reflection.ClassInfo;
import org.codehaus.groovy.runtime.BytecodeInterface8;
import org.codehaus.groovy.runtime.ResourceGroovyMethods;
import org.codehaus.groovy.runtime.ScriptBytecodeAdapter;
import org.codehaus.groovy.runtime.callsite.CallSite;
import org.codehaus.groovy.runtime.callsite.CallSiteArray;

public class FileTreeBuilder
implements GroovyObject {
    private File baseDir;
    private static /* synthetic */ ClassInfo $staticClassInfo;
    public static transient /* synthetic */ boolean __$stMC;
    private transient /* synthetic */ MetaClass metaClass;
    private static /* synthetic */ SoftReference $callSiteArray;

    public FileTreeBuilder(File baseDir) {
        File file;
        MetaClass metaClass;
        this.metaClass = metaClass = this.$getStaticMetaClass();
        this.baseDir = file = baseDir;
    }

    @Generated
    public FileTreeBuilder() {
        CallSite[] callSiteArray = FileTreeBuilder.$getCallSiteArray();
        this((File)callSiteArray[0].callConstructor(File.class, "."));
    }

    public File file(String name, CharSequence contents) {
        return ResourceGroovyMethods.leftShift(new File(this.baseDir, name), contents);
    }

    public File file(String name, byte ... contents) {
        return ResourceGroovyMethods.leftShift(new File(this.baseDir, name), contents);
    }

    public File file(String name, File source) {
        return this.file(name, ResourceGroovyMethods.getBytes(source));
    }

    public File file(String name, @DelegatesTo(strategy=1, value=File.class) Closure spec) {
        File file = new File(this.baseDir, name);
        Closure clone = (Closure)ScriptBytecodeAdapter.castToType(spec.clone(), Closure.class);
        File file2 = file;
        clone.setDelegate(file2);
        int n = Closure.DELEGATE_FIRST;
        clone.setResolveStrategy(n);
        clone.call((Object)file);
        return file;
    }

    public File dir(String name) {
        File f = new File(this.baseDir, name);
        f.mkdirs();
        return f;
    }

    public File dir(String name, @DelegatesTo(strategy=1, value=FileTreeBuilder.class) Closure cl) {
        File oldBase = this.baseDir;
        File newBase = this.dir(name);
        try {
            File file;
            this.baseDir = file = newBase;
            FileTreeBuilder fileTreeBuilder = this;
            cl.setDelegate(fileTreeBuilder);
            int n = Closure.DELEGATE_FIRST;
            cl.setResolveStrategy(n);
            cl.call();
        }
        finally {
            File file;
            this.baseDir = file = oldBase;
        }
        return newBase;
    }

    public File call(@DelegatesTo(strategy=1, value=FileTreeBuilder.class) Closure spec) {
        Closure clone = (Closure)ScriptBytecodeAdapter.castToType(spec.clone(), Closure.class);
        FileTreeBuilder fileTreeBuilder = this;
        clone.setDelegate(fileTreeBuilder);
        int n = Closure.DELEGATE_FIRST;
        clone.setResolveStrategy(n);
        clone.call();
        return this.baseDir;
    }

    public Object methodMissing(String name, Object args) {
        if (args instanceof Object[] && ((Object[])ScriptBytecodeAdapter.castToType(args, Object[].class)).length == 1) {
            Object arg = BytecodeInterface8.objectArrayGet((Object[])ScriptBytecodeAdapter.castToType(args, Object[].class), 0);
            if (arg instanceof Closure) {
                return this.dir(name, (Closure)ScriptBytecodeAdapter.castToType(arg, Closure.class));
            }
            if (arg instanceof CharSequence) {
                return this.file(name, ((CharSequence)arg).toString());
            }
            if (arg instanceof byte[]) {
                return this.file(name, (byte[])ScriptBytecodeAdapter.castToType(arg, byte[].class));
            }
            if (arg instanceof File) {
                return this.file(name, (File)ScriptBytecodeAdapter.castToType(arg, File.class));
            }
        }
        return null;
    }

    protected /* synthetic */ MetaClass $getStaticMetaClass() {
        if (this.getClass() != FileTreeBuilder.class) {
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
    public File getBaseDir() {
        return this.baseDir;
    }

    @Generated
    public void setBaseDir(File file) {
        this.baseDir = file;
    }

    private static /* synthetic */ CallSiteArray $createCallSiteArray() {
        String[] stringArray = new String[1];
        stringArray[0] = "<$constructor$>";
        return new CallSiteArray(FileTreeBuilder.class, stringArray);
    }

    private static /* synthetic */ CallSite[] $getCallSiteArray() {
        CallSiteArray callSiteArray;
        if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
            callSiteArray = FileTreeBuilder.$createCallSiteArray();
            $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
        }
        return callSiteArray.array;
    }
}

