/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.classgen.asm.indy.sc;

import groovyjarjarasm.asm.Handle;
import groovyjarjarasm.asm.MethodVisitor;
import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import org.codehaus.groovy.classgen.asm.BinaryExpressionWriter;
import org.codehaus.groovy.classgen.asm.MethodCaller;
import org.codehaus.groovy.classgen.asm.WriterController;
import org.codehaus.groovy.classgen.asm.sc.StaticTypesBinaryExpressionMultiTypeDispatcher;
import org.codehaus.groovy.vmplugin.v8.IndyInterface;

public class IndyStaticTypesMultiTypeDispatcher
extends StaticTypesBinaryExpressionMultiTypeDispatcher {
    private static final String INDY_INTERFACE_NAME = IndyInterface.class.getName().replace('.', '/');
    private static final String BSM_METHOD_TYPE_DESCRIPTOR = MethodType.methodType(CallSite.class, MethodHandles.Lookup.class, String.class, MethodType.class).toMethodDescriptorString();
    private static final Handle BSM = new Handle(6, INDY_INTERFACE_NAME, "staticArrayAccess", BSM_METHOD_TYPE_DESCRIPTOR, false);

    public IndyStaticTypesMultiTypeDispatcher(WriterController wc) {
        super(wc);
    }

    @Override
    protected BinaryExpressionWriter[] initializeDelegateHelpers() {
        BinaryExpressionWriter[] bewArray = super.initializeDelegateHelpers();
        bewArray[1].setArraySetAndGet(new GenericArrayAccess("set", "([III)V"), new GenericArrayAccess("get", "([II)I"));
        bewArray[2].setArraySetAndGet(new GenericArrayAccess("set", "([JIJ)V"), new GenericArrayAccess("get", "([JI)J"));
        bewArray[3].setArraySetAndGet(new GenericArrayAccess("set", "([DID)V"), new GenericArrayAccess("get", "([DI)D"));
        bewArray[4].setArraySetAndGet(new GenericArrayAccess("set", "([CIC)V"), new GenericArrayAccess("get", "([CI)C"));
        bewArray[5].setArraySetAndGet(new GenericArrayAccess("set", "([BIB)V"), new GenericArrayAccess("get", "([BI)B"));
        bewArray[6].setArraySetAndGet(new GenericArrayAccess("set", "([SIS)V"), new GenericArrayAccess("get", "([SI)S"));
        bewArray[7].setArraySetAndGet(new GenericArrayAccess("get", "([FIF)V"), new GenericArrayAccess("set", "([FI)F"));
        bewArray[8].setArraySetAndGet(new GenericArrayAccess("get", "([ZIZ)V"), new GenericArrayAccess("set", "([ZI)Z"));
        return bewArray;
    }

    private static class GenericArrayAccess
    extends MethodCaller {
        private final String name;
        private final String signature;

        public GenericArrayAccess(String name, String signature) {
            this.name = name;
            this.signature = signature;
        }

        @Override
        public void call(MethodVisitor mv) {
            mv.visitInvokeDynamicInsn(this.name, this.signature, BSM, new Object[0]);
        }
    }
}

