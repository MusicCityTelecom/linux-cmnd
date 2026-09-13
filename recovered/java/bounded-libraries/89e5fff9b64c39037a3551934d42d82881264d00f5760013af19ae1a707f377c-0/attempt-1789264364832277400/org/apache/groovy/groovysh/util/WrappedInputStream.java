/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  groovy.lang.GroovyObject
 *  groovy.lang.MetaClass
 *  groovy.transform.Generated
 *  groovy.transform.Internal
 *  org.codehaus.groovy.reflection.ClassInfo
 *  org.codehaus.groovy.runtime.BytecodeInterface8
 *  org.codehaus.groovy.runtime.ScriptBytecodeAdapter
 *  org.codehaus.groovy.runtime.callsite.CallSite
 *  org.codehaus.groovy.runtime.callsite.CallSiteArray
 *  org.codehaus.groovy.runtime.typehandling.DefaultTypeTransformation
 */
package org.apache.groovy.groovysh.util;

import groovy.lang.GroovyObject;
import groovy.lang.MetaClass;
import groovy.transform.Generated;
import groovy.transform.Internal;
import java.beans.Transient;
import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.lang.invoke.MethodHandles;
import java.lang.ref.SoftReference;
import org.codehaus.groovy.reflection.ClassInfo;
import org.codehaus.groovy.runtime.BytecodeInterface8;
import org.codehaus.groovy.runtime.ScriptBytecodeAdapter;
import org.codehaus.groovy.runtime.callsite.CallSite;
import org.codehaus.groovy.runtime.callsite.CallSiteArray;
import org.codehaus.groovy.runtime.typehandling.DefaultTypeTransformation;

public class WrappedInputStream
extends InputStream
implements Closeable,
GroovyObject {
    private final InputStream wrapped;
    private ByteArrayInputStream inserted;
    private static /* synthetic */ ClassInfo $staticClassInfo;
    public static transient /* synthetic */ boolean __$stMC;
    private transient /* synthetic */ MetaClass metaClass;
    private static /* synthetic */ SoftReference $callSiteArray;

    public WrappedInputStream(InputStream wrapped) {
        InputStream inputStream;
        MetaClass metaClass;
        CallSite[] callSiteArray = WrappedInputStream.$getCallSiteArray();
        Object object = callSiteArray[0].callConstructor(ByteArrayInputStream.class);
        this.inserted = (ByteArrayInputStream)ScriptBytecodeAdapter.castToType((Object)object, ByteArrayInputStream.class);
        this.metaClass = metaClass = this.$getStaticMetaClass();
        this.wrapped = inputStream = wrapped;
    }

    @Override
    public int read() throws IOException {
        CallSite[] callSiteArray = WrappedInputStream.$getCallSiteArray();
        if (!BytecodeInterface8.isOrigInt() || !BytecodeInterface8.isOrigZ() || __$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
            if (ScriptBytecodeAdapter.compareNotEqual((Object)this.inserted, null) && ScriptBytecodeAdapter.compareGreaterThan((Object)callSiteArray[1].call((Object)this.inserted), (Object)0)) {
                return DefaultTypeTransformation.intUnbox((Object)callSiteArray[2].call((Object)this.inserted));
            }
        } else if (ScriptBytecodeAdapter.compareNotEqual((Object)this.inserted, null) && ScriptBytecodeAdapter.compareGreaterThan((Object)callSiteArray[3].call((Object)this.inserted), (Object)0)) {
            return DefaultTypeTransformation.intUnbox((Object)callSiteArray[4].call((Object)this.inserted));
        }
        return DefaultTypeTransformation.intUnbox((Object)callSiteArray[5].call((Object)this.wrapped));
    }

    public void insert(String chars) {
        CallSite[] callSiteArray = WrappedInputStream.$getCallSiteArray();
        callSiteArray[6].call((Object)this.inserted);
        Object object = callSiteArray[7].callConstructor(ByteArrayInputStream.class, callSiteArray[8].call((Object)chars, (Object)"UTF-8"));
        this.inserted = (ByteArrayInputStream)ScriptBytecodeAdapter.castToType((Object)object, ByteArrayInputStream.class);
    }

    @Override
    public int read(byte ... b) throws IOException {
        CallSite[] callSiteArray = WrappedInputStream.$getCallSiteArray();
        Object insertb = callSiteArray[9].call((Object)this.inserted, (Object)b);
        if (ScriptBytecodeAdapter.compareGreaterThan((Object)insertb, (Object)0)) {
            return DefaultTypeTransformation.intUnbox((Object)insertb);
        }
        return DefaultTypeTransformation.intUnbox((Object)callSiteArray[10].call((Object)this.wrapped, (Object)b));
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        CallSite[] callSiteArray = WrappedInputStream.$getCallSiteArray();
        Object insertb = callSiteArray[11].call((Object)this.inserted, (Object)b, (Object)off, (Object)len);
        if (ScriptBytecodeAdapter.compareGreaterThan((Object)insertb, (Object)0)) {
            return DefaultTypeTransformation.intUnbox((Object)insertb);
        }
        return DefaultTypeTransformation.intUnbox((Object)callSiteArray[12].call((Object)this.wrapped, (Object)b, (Object)off, (Object)len));
    }

    @Override
    public long skip(long n) throws IOException {
        CallSite[] callSiteArray = WrappedInputStream.$getCallSiteArray();
        Object skipb = callSiteArray[13].call((Object)this.inserted, (Object)n);
        if (ScriptBytecodeAdapter.compareGreaterThan((Object)skipb, (Object)0)) {
            return DefaultTypeTransformation.longUnbox((Object)skipb);
        }
        return DefaultTypeTransformation.longUnbox((Object)callSiteArray[14].call((Object)this.wrapped, (Object)n));
    }

    @Override
    public int available() throws IOException {
        CallSite[] callSiteArray = WrappedInputStream.$getCallSiteArray();
        int x = DefaultTypeTransformation.intUnbox((Object)callSiteArray[15].call((Object)this.inserted));
        if (!BytecodeInterface8.isOrigInt() || !BytecodeInterface8.isOrigZ() || __$stMC || BytecodeInterface8.disabledStandardMetaClass() ? x > 0 : x > 0) {
            return x;
        }
        return DefaultTypeTransformation.intUnbox((Object)callSiteArray[16].call((Object)this.wrapped));
    }

    @Override
    public void close() throws IOException {
        CallSite[] callSiteArray = WrappedInputStream.$getCallSiteArray();
        callSiteArray[17].call((Object)this.wrapped);
        callSiteArray[18].call((Object)this.inserted);
    }

    @Override
    public synchronized void mark(int readlimit) {
        CallSite[] callSiteArray = WrappedInputStream.$getCallSiteArray();
        throw (Throwable)callSiteArray[19].callConstructor(UnsupportedOperationException.class);
    }

    @Override
    public synchronized void reset() throws IOException {
        CallSite[] callSiteArray = WrappedInputStream.$getCallSiteArray();
        throw (Throwable)callSiteArray[20].callConstructor(UnsupportedOperationException.class);
    }

    @Override
    public boolean markSupported() {
        CallSite[] callSiteArray = WrappedInputStream.$getCallSiteArray();
        return false;
    }

    protected /* synthetic */ MetaClass $getStaticMetaClass() {
        if (this.getClass() != WrappedInputStream.class) {
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
    public final InputStream getWrapped() {
        return this.wrapped;
    }

    @Generated
    public ByteArrayInputStream getInserted() {
        return this.inserted;
    }

    @Generated
    public void setInserted(ByteArrayInputStream byteArrayInputStream) {
        this.inserted = byteArrayInputStream;
    }

    public /* synthetic */ long super$2$skip(long l) {
        return super.skip(l);
    }

    public /* synthetic */ boolean super$2$markSupported() {
        return super.markSupported();
    }

    public /* synthetic */ void super$2$close() {
        super.close();
    }

    public /* synthetic */ void super$2$mark(int n) {
        super.mark(n);
    }

    public /* synthetic */ int super$2$read(byte[] byArray) {
        return super.read(byArray);
    }

    public /* synthetic */ int super$2$available() {
        return super.available();
    }

    public /* synthetic */ void super$2$reset() {
        super.reset();
    }

    public /* synthetic */ int super$2$read(byte[] byArray, int n, int n2) {
        return super.read(byArray, n, n2);
    }

    private static /* synthetic */ void $createCallSiteArray_1(String[] stringArray) {
        stringArray[0] = "<$constructor$>";
        stringArray[1] = "available";
        stringArray[2] = "read";
        stringArray[3] = "available";
        stringArray[4] = "read";
        stringArray[5] = "read";
        stringArray[6] = "close";
        stringArray[7] = "<$constructor$>";
        stringArray[8] = "getBytes";
        stringArray[9] = "read";
        stringArray[10] = "read";
        stringArray[11] = "read";
        stringArray[12] = "read";
        stringArray[13] = "skip";
        stringArray[14] = "skip";
        stringArray[15] = "available";
        stringArray[16] = "available";
        stringArray[17] = "close";
        stringArray[18] = "close";
        stringArray[19] = "<$constructor$>";
        stringArray[20] = "<$constructor$>";
    }

    private static /* synthetic */ CallSiteArray $createCallSiteArray() {
        String[] stringArray = new String[21];
        WrappedInputStream.$createCallSiteArray_1(stringArray);
        return new CallSiteArray(WrappedInputStream.class, stringArray);
    }

    private static /* synthetic */ CallSite[] $getCallSiteArray() {
        CallSiteArray callSiteArray;
        if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
            callSiteArray = WrappedInputStream.$createCallSiteArray();
            $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
        }
        return callSiteArray.array;
    }
}

