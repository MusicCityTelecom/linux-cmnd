/*
 * Decompiled with CFR 0.152.
 */
package org.aopalliance.instrument;

import org.aopalliance.instrument.Instrumentation;
import org.aopalliance.instrument.InstrumentationError;
import org.aopalliance.instrument.UndoNotSupportedException;
import org.aopalliance.reflect.ClassLocator;
import org.aopalliance.reflect.Code;
import org.aopalliance.reflect.CodeLocator;

public interface Instrumentor {
    public ClassLocator createClass(String var1) throws InstrumentationError;

    public Instrumentation addInterface(ClassLocator var1, String var2) throws InstrumentationError;

    public Instrumentation setSuperClass(ClassLocator var1, String var2) throws InstrumentationError;

    public Instrumentation addClass(ClassLocator var1, String var2) throws InstrumentationError;

    public Instrumentation addMethod(ClassLocator var1, String var2, String[] var3, String[] var4, Code var5) throws InstrumentationError;

    public Instrumentation addField(ClassLocator var1, String var2, String var3, Code var4) throws InstrumentationError;

    public Instrumentation addBeforeCode(CodeLocator var1, Code var2, Instrumentation var3, Instrumentation var4) throws InstrumentationError;

    public Instrumentation addAfterCode(CodeLocator var1, Code var2, Instrumentation var3, Instrumentation var4) throws InstrumentationError;

    public Instrumentation addAroundCode(CodeLocator var1, Code var2, String var3, Instrumentation var4, Instrumentation var5) throws InstrumentationError;

    public void undo(Instrumentation var1) throws UndoNotSupportedException;
}

