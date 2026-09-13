/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  groovy.lang.GroovyObject
 *  groovy.lang.MetaClass
 *  groovy.transform.Generated
 *  groovy.transform.Internal
 *  jline.console.completer.ArgumentCompleter
 *  jline.console.completer.ArgumentCompleter$ArgumentDelimiter
 *  jline.console.completer.ArgumentCompleter$ArgumentList
 *  jline.console.completer.Completer
 *  jline.internal.Log
 *  jline.internal.Preconditions
 *  org.codehaus.groovy.reflection.ClassInfo
 *  org.codehaus.groovy.runtime.BytecodeInterface8
 *  org.codehaus.groovy.runtime.ScriptBytecodeAdapter
 *  org.codehaus.groovy.runtime.typehandling.ShortTypeHandling
 */
package org.apache.groovy.groovysh.completion;

import groovy.lang.GroovyObject;
import groovy.lang.MetaClass;
import groovy.transform.Generated;
import groovy.transform.Internal;
import java.beans.Transient;
import java.lang.invoke.MethodHandles;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import jline.console.completer.ArgumentCompleter;
import jline.console.completer.Completer;
import jline.internal.Log;
import jline.internal.Preconditions;
import org.codehaus.groovy.reflection.ClassInfo;
import org.codehaus.groovy.runtime.BytecodeInterface8;
import org.codehaus.groovy.runtime.ScriptBytecodeAdapter;
import org.codehaus.groovy.runtime.typehandling.ShortTypeHandling;

public class StricterArgumentCompleter
extends ArgumentCompleter
implements GroovyObject {
    private static /* synthetic */ ClassInfo $staticClassInfo;
    public static transient /* synthetic */ boolean __$stMC;
    private transient /* synthetic */ MetaClass metaClass;

    public StricterArgumentCompleter(List<Completer> completers) {
        super(completers);
        MetaClass metaClass;
        this.metaClass = metaClass = this.$getStaticMetaClass();
    }

    public int complete(String buffer, int cursor, List<CharSequence> candidates) {
        Preconditions.checkNotNull(candidates);
        ArgumentCompleter.ArgumentDelimiter delim = this.getDelimiter();
        ArgumentCompleter.ArgumentList list = delim.delimit((CharSequence)buffer, cursor);
        int argpos = list.getArgumentPosition();
        int argIndex = list.getCursorArgumentIndex();
        if (argIndex < 0) {
            return -1;
        }
        List completers = this.getCompleters();
        Completer completer = null;
        if (argIndex >= completers.size()) {
            Object e = completers.get(completers.size() - 1);
            completer = (Completer)ScriptBytecodeAdapter.castToType(e, Completer.class);
        } else {
            Object e = completers.get(argIndex);
            completer = (Completer)ScriptBytecodeAdapter.castToType(e, Completer.class);
        }
        int i = 0;
        while (this.isStrict() && i < argIndex) {
            Completer sub = (Completer)ScriptBytecodeAdapter.castToType(completers.get(i >= completers.size() ? completers.size() - 1 : i), Completer.class);
            Object[] args = list.getArguments();
            String arg = args == null || i >= args.length ? "" : ShortTypeHandling.castToString((Object)BytecodeInterface8.objectArrayGet((Object[])args, (int)i));
            LinkedList subCandidates = new LinkedList();
            int offset = sub.complete(arg, arg.length(), subCandidates);
            if (offset == -1) {
                return -1;
            }
            boolean candidateMatches = false;
            CharSequence subCandidate = null;
            LinkedList linkedList = subCandidates;
            Iterator iterator = linkedList != null ? linkedList.iterator() : null;
            if (iterator != null) {
                while (iterator.hasNext()) {
                    boolean bl;
                    subCandidate = (CharSequence)ScriptBytecodeAdapter.castToType(iterator.next(), CharSequence.class);
                    Object[] candidateDelimList = delim.delimit(subCandidate, 0).getArguments();
                    if (candidateDelimList.length == 0) continue;
                    String trimmedCand = ShortTypeHandling.castToString((Object)BytecodeInterface8.objectArrayGet((Object[])candidateDelimList, (int)0));
                    if (!trimmedCand.equals(arg.substring(offset))) continue;
                    candidateMatches = bl = true;
                    break;
                }
            }
            if (!candidateMatches) {
                return -1;
            }
            int n = i;
            int cfr_ignored_0 = n + 1;
        }
        int ret = completer.complete(list.getCursorArgument(), argpos, candidates);
        if (ret == -1) {
            return -1;
        }
        int pos = ret + list.getBufferPosition() - argpos;
        if (cursor != buffer.length() && delim.isDelimiter((CharSequence)buffer, cursor)) {
            int i2 = 0;
            while (i2 < candidates.size()) {
                CharSequence val = (CharSequence)ScriptBytecodeAdapter.castToType((Object)candidates.get(i2), CharSequence.class);
                while (val.length() > 0 && delim.isDelimiter(val, val.length() - 1)) {
                    CharSequence charSequence;
                    val = charSequence = val.subSequence(0, val.length() - 1);
                }
                candidates.set(i2, val);
                int n = i2;
                int cfr_ignored_1 = n + 1;
            }
        }
        Log.trace((Object[])new Object[]{"Completing ", buffer, " (pos=", cursor, ") with: ", candidates, ": offset=", pos});
        return pos;
    }

    protected /* synthetic */ MetaClass $getStaticMetaClass() {
        if (((Object)((Object)this)).getClass() != StricterArgumentCompleter.class) {
            return ScriptBytecodeAdapter.initMetaClass((Object)((Object)this));
        }
        ClassInfo classInfo = $staticClassInfo;
        if (classInfo == null) {
            $staticClassInfo = classInfo = ClassInfo.getClassInfo(((Object)((Object)this)).getClass());
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

