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
package org.apache.groovy.groovysh;

import groovy.lang.GroovyObject;
import groovy.lang.MetaClass;
import groovy.transform.Generated;
import groovy.transform.Internal;
import java.beans.Transient;
import java.lang.invoke.MethodHandles;
import org.apache.groovy.groovysh.Command;
import org.codehaus.groovy.reflection.ClassInfo;
import org.codehaus.groovy.runtime.ScriptBytecodeAdapter;

public class CommandException
extends Exception
implements GroovyObject {
    private final Command command;
    private static final long serialVersionUID = 3904423461953446923L;
    private static /* synthetic */ long $const$0;
    private static /* synthetic */ ClassInfo $staticClassInfo;
    public static transient /* synthetic */ boolean __$stMC;
    private transient /* synthetic */ MetaClass metaClass;

    public CommandException(Command command, String msg) {
        super(msg);
        Command command2;
        MetaClass metaClass;
        this.metaClass = metaClass = this.$getStaticMetaClass();
        this.command = command2 = command;
    }

    public CommandException(Command command, String msg, Throwable cause) {
        super(msg, cause);
        Command command2;
        MetaClass metaClass;
        this.metaClass = metaClass = this.$getStaticMetaClass();
        this.command = command2 = command;
    }

    public Command getCommand() {
        return this.command;
    }

    protected /* synthetic */ MetaClass $getStaticMetaClass() {
        if (this.getClass() != CommandException.class) {
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

    public static /* synthetic */ void __$swapInit() {
        long l;
        $const$0 = l = 3904423461953446923L;
    }

    static {
        CommandException.__$swapInit();
    }
}

