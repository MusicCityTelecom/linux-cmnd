/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  groovy.lang.GroovyObject
 *  groovy.lang.GroovyShell
 *  groovy.lang.MetaClass
 *  groovy.transform.Generated
 *  groovy.transform.Internal
 *  jline.console.completer.FileNameCompleter
 *  jline.internal.Configuration
 *  jline.internal.Preconditions
 *  org.codehaus.groovy.reflection.ClassInfo
 *  org.codehaus.groovy.runtime.BytecodeInterface8
 *  org.codehaus.groovy.runtime.GStringImpl
 *  org.codehaus.groovy.runtime.ScriptBytecodeAdapter
 *  org.codehaus.groovy.runtime.callsite.CallSite
 *  org.codehaus.groovy.runtime.callsite.CallSiteArray
 *  org.codehaus.groovy.runtime.typehandling.DefaultTypeTransformation
 *  org.codehaus.groovy.runtime.typehandling.ShortTypeHandling
 */
package org.apache.groovy.groovysh.completion;

import groovy.lang.GroovyObject;
import groovy.lang.GroovyShell;
import groovy.lang.MetaClass;
import groovy.transform.Generated;
import groovy.transform.Internal;
import java.beans.Transient;
import java.io.File;
import java.lang.invoke.MethodHandles;
import java.lang.ref.SoftReference;
import java.util.Iterator;
import java.util.List;
import jline.internal.Configuration;
import jline.internal.Preconditions;
import org.codehaus.groovy.reflection.ClassInfo;
import org.codehaus.groovy.runtime.BytecodeInterface8;
import org.codehaus.groovy.runtime.GStringImpl;
import org.codehaus.groovy.runtime.ScriptBytecodeAdapter;
import org.codehaus.groovy.runtime.callsite.CallSite;
import org.codehaus.groovy.runtime.callsite.CallSiteArray;
import org.codehaus.groovy.runtime.typehandling.DefaultTypeTransformation;
import org.codehaus.groovy.runtime.typehandling.ShortTypeHandling;

public class FileNameCompleter
extends jline.console.completer.FileNameCompleter
implements GroovyObject {
    private static final boolean OS_IS_WINDOWS;
    private final GroovyShell gs;
    private boolean printSpaceAfterFullCompletion;
    private boolean escapeBackslash;
    private boolean escapeSpaces;
    private String separator;
    private static /* synthetic */ ClassInfo $staticClassInfo;
    public static transient /* synthetic */ boolean __$stMC;
    private transient /* synthetic */ MetaClass metaClass;
    private static /* synthetic */ SoftReference $callSiteArray;

    public FileNameCompleter(boolean printSpaceAfterFullCompletion, boolean escapeBackslash, boolean escapeSpaces) {
        boolean bl;
        boolean bl2;
        boolean bl3;
        MetaClass metaClass;
        CallSite[] callSiteArray = FileNameCompleter.$getCallSiteArray();
        Object object = callSiteArray[0].callConstructor(GroovyShell.class);
        this.gs = (GroovyShell)ScriptBytecodeAdapter.castToType((Object)object, GroovyShell.class);
        this.metaClass = metaClass = this.$getStaticMetaClass();
        this.printSpaceAfterFullCompletion = bl3 = printSpaceAfterFullCompletion;
        this.escapeBackslash = bl2 = escapeBackslash;
        if (OS_IS_WINDOWS) {
            String string;
            this.separator = string = escapeBackslash ? "\\\\" : "\\";
        }
        this.escapeSpaces = bl = escapeSpaces;
    }

    @Generated
    public FileNameCompleter(boolean printSpaceAfterFullCompletion, boolean escapeBackslash) {
        CallSite[] callSiteArray = FileNameCompleter.$getCallSiteArray();
        this(printSpaceAfterFullCompletion, escapeBackslash, true);
    }

    @Generated
    public FileNameCompleter(boolean printSpaceAfterFullCompletion) {
        CallSite[] callSiteArray = FileNameCompleter.$getCallSiteArray();
        this(printSpaceAfterFullCompletion, false, true);
    }

    @Generated
    public FileNameCompleter() {
        CallSite[] callSiteArray = FileNameCompleter.$getCallSiteArray();
        this(true, false, true);
    }

    private static boolean isWindowsSubsystemForLinux() {
        CallSite[] callSiteArray = FileNameCompleter.$getCallSiteArray();
        return DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[1].call(callSiteArray[2].call(System.class, (Object)"os.name"), (Object)"Linux")) && DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[3].call(callSiteArray[4].call(System.class, (Object)"os.version"), (Object)"Microsoft"));
    }

    public int complete(String buffer, int cursor, List<CharSequence> candidates) {
        String string;
        CallSite[] callSiteArray = FileNameCompleter.$getCallSiteArray();
        callSiteArray[5].callStatic(Preconditions.class, candidates);
        String string2 = buffer;
        buffer = string = DefaultTypeTransformation.booleanUnbox((Object)string2) ? string2 : "";
        String translated = buffer;
        int adjustment = 0;
        if (this.escapeBackslash) {
            Object object = callSiteArray[6].call((Object)this.gs, (Object)new GStringImpl(new Object[]{translated}, new String[]{"'", "'"}));
            translated = ShortTypeHandling.castToString((Object)object);
            Object object2 = callSiteArray[7].call(callSiteArray[8].call((Object)buffer), callSiteArray[9].call((Object)translated));
            adjustment = DefaultTypeTransformation.intUnbox((Object)object2);
        }
        if (!BytecodeInterface8.isOrigZ() || __$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
            if (DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[10].call((Object)translated, (Object)"~"))) {
                File homeDir = (File)ScriptBytecodeAdapter.castToType((Object)callSiteArray[11].callCurrent((GroovyObject)this), File.class);
                if ((OS_IS_WINDOWS || FileNameCompleter.isWindowsSubsystemForLinux()) && (DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[12].call((Object)translated, callSiteArray[13].call((Object)"~", callSiteArray[14].callCurrent((GroovyObject)this)))) || DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[15].call((Object)translated, (Object)"~/")))) {
                    Object adjustSize = callSiteArray[16].call((Object)translated);
                    String result = null;
                    String temp = ShortTypeHandling.castToString((Object)callSiteArray[17].call(callSiteArray[18].call(callSiteArray[19].call(callSiteArray[20].call(callSiteArray[21].callGetProperty((Object)homeDir), callSiteArray[22].call((Object)translated, callSiteArray[23].call(callSiteArray[24].callCurrent((GroovyObject)this))))), (Object)"\"", (Object)"\\\""), (Object)"'", (Object)"\\'"));
                    if (this.escapeBackslash) {
                        Object object = callSiteArray[25].call((Object)temp, (Object)"\\", (Object)"\\\\");
                        temp = ShortTypeHandling.castToString((Object)object);
                    }
                    String string3 = this.escapeSpaces ? callSiteArray[26].call((Object)temp, (Object)" ", (Object)"\\ ") : temp;
                    result = ShortTypeHandling.castToString((Object)string3);
                    callSiteArray[27].call(candidates, (Object)result);
                    return DefaultTypeTransformation.intUnbox((Object)callSiteArray[28].call(callSiteArray[29].call((Object)cursor, adjustSize), (Object)adjustment));
                }
                if (DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[30].call((Object)translated, (Object)"~/"))) {
                    Object object = callSiteArray[31].call(callSiteArray[32].callGetProperty((Object)homeDir), callSiteArray[33].call((Object)translated, (Object)2));
                    translated = ShortTypeHandling.castToString((Object)object);
                } else {
                    Object object = callSiteArray[34].call(callSiteArray[35].call(callSiteArray[36].callGetProperty(callSiteArray[37].callGetProperty((Object)homeDir)), callSiteArray[38].callCurrent((GroovyObject)this)), callSiteArray[39].call((Object)translated, (Object)1));
                    translated = ShortTypeHandling.castToString((Object)object);
                }
            } else if (!DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[40].call(callSiteArray[41].callGetProperty(callSiteArray[42].callConstructor(File.class, (Object)translated)))) && !DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[43].callSafe(callSiteArray[44].callGetProperty(callSiteArray[45].callGetProperty(callSiteArray[46].callConstructor(File.class, (Object)translated)))))) {
                String cwd = ShortTypeHandling.castToString((Object)callSiteArray[47].callGetProperty(callSiteArray[48].callCurrent((GroovyObject)this)));
                Object object = callSiteArray[49].call(callSiteArray[50].call((Object)cwd, callSiteArray[51].callCurrent((GroovyObject)this)), (Object)translated);
                translated = ShortTypeHandling.castToString((Object)object);
            }
        } else if (DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[52].call((Object)translated, (Object)"~"))) {
            File homeDir = (File)ScriptBytecodeAdapter.castToType((Object)callSiteArray[53].callCurrent((GroovyObject)this), File.class);
            if ((OS_IS_WINDOWS || FileNameCompleter.isWindowsSubsystemForLinux()) && (DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[54].call((Object)translated, callSiteArray[55].call((Object)"~", callSiteArray[56].callCurrent((GroovyObject)this)))) || DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[57].call((Object)translated, (Object)"~/")))) {
                Object adjustSize = callSiteArray[58].call((Object)translated);
                String result = null;
                String temp = ShortTypeHandling.castToString((Object)callSiteArray[59].call(callSiteArray[60].call(callSiteArray[61].call(callSiteArray[62].call(callSiteArray[63].callGetProperty((Object)homeDir), callSiteArray[64].call((Object)translated, callSiteArray[65].call(callSiteArray[66].callCurrent((GroovyObject)this))))), (Object)"\"", (Object)"\\\""), (Object)"'", (Object)"\\'"));
                if (this.escapeBackslash) {
                    Object object = callSiteArray[67].call((Object)temp, (Object)"\\", (Object)"\\\\");
                    temp = ShortTypeHandling.castToString((Object)object);
                }
                String string4 = this.escapeSpaces ? callSiteArray[68].call((Object)temp, (Object)" ", (Object)"\\ ") : temp;
                result = ShortTypeHandling.castToString((Object)string4);
                callSiteArray[69].call(candidates, (Object)result);
                return DefaultTypeTransformation.intUnbox((Object)callSiteArray[70].call(callSiteArray[71].call((Object)cursor, adjustSize), (Object)adjustment));
            }
            if (DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[72].call((Object)translated, (Object)"~/"))) {
                Object object = callSiteArray[73].call(callSiteArray[74].callGetProperty((Object)homeDir), callSiteArray[75].call((Object)translated, (Object)2));
                translated = ShortTypeHandling.castToString((Object)object);
            } else {
                Object object = callSiteArray[76].call(callSiteArray[77].call(callSiteArray[78].callGetProperty(callSiteArray[79].callGetProperty((Object)homeDir)), callSiteArray[80].callCurrent((GroovyObject)this)), callSiteArray[81].call((Object)translated, (Object)1));
                translated = ShortTypeHandling.castToString((Object)object);
            }
        } else if (!DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[82].call(callSiteArray[83].callGetProperty(callSiteArray[84].callConstructor(File.class, (Object)translated)))) && !DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[85].callSafe(callSiteArray[86].callGetProperty(callSiteArray[87].callGetProperty(callSiteArray[88].callConstructor(File.class, (Object)translated)))))) {
            String cwd = ShortTypeHandling.castToString((Object)callSiteArray[89].callGetProperty(callSiteArray[90].callCurrent((GroovyObject)this)));
            Object object = callSiteArray[91].call(callSiteArray[92].call((Object)cwd, callSiteArray[93].callCurrent((GroovyObject)this)), (Object)translated);
            translated = ShortTypeHandling.castToString((Object)object);
        }
        File file = (File)ScriptBytecodeAdapter.castToType((Object)callSiteArray[94].callConstructor(File.class, (Object)translated), File.class);
        File dir = null;
        if (OS_IS_WINDOWS && DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[95].call((Object)translated, callSiteArray[96].callCurrent((GroovyObject)this))) || DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[97].call((Object)translated, (Object)"/"))) {
            File file2;
            dir = file2 = file;
        } else {
            Object object = callSiteArray[98].callGetProperty((Object)file);
            dir = (File)ScriptBytecodeAdapter.castToType((Object)object, File.class);
        }
        File[] entries = (File[])ScriptBytecodeAdapter.castToType((Object)(ScriptBytecodeAdapter.compareEqual((Object)dir, null) ? new File[]{} : callSiteArray[99].call((Object)dir)), File[].class);
        return DefaultTypeTransformation.intUnbox((Object)callSiteArray[100].callCurrent((GroovyObject)this, (Object)buffer, (Object)translated, (Object)entries, candidates));
    }

    private static String canonicalForm(String raw) {
        CallSite[] callSiteArray = FileNameCompleter.$getCallSiteArray();
        String result = ShortTypeHandling.castToString((Object)callSiteArray[101].call((Object)raw, (Object)"\\", (Object)"/"));
        return ShortTypeHandling.castToString((Object)(OS_IS_WINDOWS ? callSiteArray[102].call((Object)result) : result));
    }

    protected int matchFiles(String buffer, String translated, File[] files, List<CharSequence> candidates) {
        CallSite[] callSiteArray = FileNameCompleter.$getCallSiteArray();
        if (ScriptBytecodeAdapter.compareEqual((Object)files, null)) {
            return -1;
        }
        if (__$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
            File file = null;
            Iterator iterator = (Iterator)ScriptBytecodeAdapter.castToType((Object)callSiteArray[103].call((Object)files), Iterator.class);
            if (iterator != null) {
                while (iterator.hasNext()) {
                    file = (File)ScriptBytecodeAdapter.castToType(iterator.next(), File.class);
                    if (!DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[104].call(callSiteArray[105].callStatic(FileNameCompleter.class, callSiteArray[106].call((Object)file)), (Object)FileNameCompleter.canonicalForm(translated)))) continue;
                    CharSequence name = (CharSequence)ScriptBytecodeAdapter.castToType((Object)callSiteArray[107].callGetProperty((Object)file), CharSequence.class);
                    String renderedName = ShortTypeHandling.castToString((Object)callSiteArray[108].call(callSiteArray[109].callCurrent((GroovyObject)this, (Object)name)));
                    if (DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[110].call((Object)file))) {
                        renderedName = ShortTypeHandling.castToString((Object)callSiteArray[111].call((Object)renderedName, (Object)this.separator));
                    } else if (this.printSpaceAfterFullCompletion) {
                        renderedName = ShortTypeHandling.castToString((Object)callSiteArray[112].call((Object)renderedName, (Object)" "));
                    }
                    callSiteArray[113].call(candidates, (Object)renderedName);
                }
            }
        } else {
            File file = null;
            Iterator iterator = (Iterator)ScriptBytecodeAdapter.castToType((Object)callSiteArray[114].call((Object)files), Iterator.class);
            if (iterator != null) {
                while (iterator.hasNext()) {
                    file = (File)ScriptBytecodeAdapter.castToType(iterator.next(), File.class);
                    if (!DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[115].call(callSiteArray[116].callStatic(FileNameCompleter.class, callSiteArray[117].call((Object)file)), (Object)FileNameCompleter.canonicalForm(translated)))) continue;
                    CharSequence name = (CharSequence)ScriptBytecodeAdapter.castToType((Object)callSiteArray[118].callGetProperty((Object)file), CharSequence.class);
                    String renderedName = ShortTypeHandling.castToString((Object)callSiteArray[119].call(callSiteArray[120].callCurrent((GroovyObject)this, (Object)name)));
                    if (DefaultTypeTransformation.booleanUnbox((Object)callSiteArray[121].call((Object)file))) {
                        renderedName = ShortTypeHandling.castToString((Object)callSiteArray[122].call((Object)renderedName, (Object)this.separator));
                    } else if (this.printSpaceAfterFullCompletion) {
                        renderedName = ShortTypeHandling.castToString((Object)callSiteArray[123].call((Object)renderedName, (Object)" "));
                    }
                    callSiteArray[124].call(candidates, (Object)renderedName);
                }
            }
        }
        int index = -1;
        int sizeAdjust = 0;
        if (DefaultTypeTransformation.booleanUnbox((Object)this.separator)) {
            Object object = callSiteArray[125].call((Object)buffer, (Object)this.separator);
            index = DefaultTypeTransformation.intUnbox((Object)object);
            Object object2 = callSiteArray[126].call((Object)this.separator);
            sizeAdjust = DefaultTypeTransformation.intUnbox((Object)object2);
        }
        int slashIndex = DefaultTypeTransformation.intUnbox((Object)callSiteArray[127].call((Object)buffer, (Object)"/"));
        if (!BytecodeInterface8.isOrigInt() || !BytecodeInterface8.isOrigZ() || __$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
            if (slashIndex >= 0 && slashIndex > index) {
                int n;
                int n2;
                index = n2 = slashIndex;
                sizeAdjust = n = 1;
            }
        } else if (slashIndex >= 0 && slashIndex > index) {
            int n;
            int n3;
            index = n3 = slashIndex;
            sizeAdjust = n = 1;
        }
        if (!BytecodeInterface8.isOrigInt() || __$stMC || BytecodeInterface8.disabledStandardMetaClass()) {
            return DefaultTypeTransformation.intUnbox((Object)callSiteArray[128].call((Object)index, (Object)sizeAdjust));
        }
        return index + sizeAdjust;
    }

    CharSequence render(CharSequence name) {
        CallSite[] callSiteArray = FileNameCompleter.$getCallSiteArray();
        String temp = ShortTypeHandling.castToString((Object)callSiteArray[129].call(callSiteArray[130].call(callSiteArray[131].call((Object)name), (Object)"\"", (Object)"\\\""), (Object)"'", (Object)"\\'"));
        if (this.escapeBackslash) {
            Object object = callSiteArray[132].call((Object)temp, (Object)"\\", (Object)"\\\\");
            temp = ShortTypeHandling.castToString((Object)object);
        }
        return (CharSequence)ScriptBytecodeAdapter.castToType((Object)(this.escapeSpaces ? callSiteArray[133].call((Object)temp, (Object)" ", (Object)"\\ ") : temp), CharSequence.class);
    }

    protected /* synthetic */ MetaClass $getStaticMetaClass() {
        if (((Object)((Object)this)).getClass() != FileNameCompleter.class) {
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

    static {
        Object object = FileNameCompleter.$getCallSiteArray()[134].call(Configuration.class);
        OS_IS_WINDOWS = DefaultTypeTransformation.booleanUnbox((Object)object);
    }

    @Generated
    public boolean getPrintSpaceAfterFullCompletion() {
        return this.printSpaceAfterFullCompletion;
    }

    @Generated
    public boolean isPrintSpaceAfterFullCompletion() {
        return this.printSpaceAfterFullCompletion;
    }

    @Generated
    public void setPrintSpaceAfterFullCompletion(boolean bl) {
        this.printSpaceAfterFullCompletion = bl;
    }

    @Generated
    public boolean getEscapeBackslash() {
        return this.escapeBackslash;
    }

    @Generated
    public boolean isEscapeBackslash() {
        return this.escapeBackslash;
    }

    @Generated
    public void setEscapeBackslash(boolean bl) {
        this.escapeBackslash = bl;
    }

    @Generated
    public boolean getEscapeSpaces() {
        return this.escapeSpaces;
    }

    @Generated
    public boolean isEscapeSpaces() {
        return this.escapeSpaces;
    }

    @Generated
    public void setEscapeSpaces(boolean bl) {
        this.escapeSpaces = bl;
    }

    public /* synthetic */ int super$2$matchFiles(String string, String string2, File[] fileArray, List list) {
        return super.matchFiles(string, string2, fileArray, list);
    }

    public /* synthetic */ int super$2$complete(String string, int n, List list) {
        return super.complete(string, n, list);
    }

    private static /* synthetic */ void $createCallSiteArray_1(String[] stringArray) {
        stringArray[0] = "<$constructor$>";
        stringArray[1] = "contains";
        stringArray[2] = "getProperty";
        stringArray[3] = "contains";
        stringArray[4] = "getProperty";
        stringArray[5] = "checkNotNull";
        stringArray[6] = "evaluate";
        stringArray[7] = "minus";
        stringArray[8] = "size";
        stringArray[9] = "size";
        stringArray[10] = "startsWith";
        stringArray[11] = "getUserHome";
        stringArray[12] = "equals";
        stringArray[13] = "plus";
        stringArray[14] = "separator";
        stringArray[15] = "equals";
        stringArray[16] = "size";
        stringArray[17] = "replace";
        stringArray[18] = "replace";
        stringArray[19] = "toString";
        stringArray[20] = "plus";
        stringArray[21] = "path";
        stringArray[22] = "substring";
        stringArray[23] = "size";
        stringArray[24] = "separator";
        stringArray[25] = "replace";
        stringArray[26] = "replace";
        stringArray[27] = "leftShift";
        stringArray[28] = "minus";
        stringArray[29] = "minus";
        stringArray[30] = "startsWith";
        stringArray[31] = "plus";
        stringArray[32] = "path";
        stringArray[33] = "substring";
        stringArray[34] = "plus";
        stringArray[35] = "plus";
        stringArray[36] = "absolutePath";
        stringArray[37] = "parentFile";
        stringArray[38] = "separator";
        stringArray[39] = "substring";
        stringArray[40] = "exists";
        stringArray[41] = "canonicalFile";
        stringArray[42] = "<$constructor$>";
        stringArray[43] = "exists";
        stringArray[44] = "parentFile";
        stringArray[45] = "canonicalFile";
        stringArray[46] = "<$constructor$>";
        stringArray[47] = "absolutePath";
        stringArray[48] = "getUserDir";
        stringArray[49] = "plus";
        stringArray[50] = "plus";
        stringArray[51] = "separator";
        stringArray[52] = "startsWith";
        stringArray[53] = "getUserHome";
        stringArray[54] = "equals";
        stringArray[55] = "plus";
        stringArray[56] = "separator";
        stringArray[57] = "equals";
        stringArray[58] = "size";
        stringArray[59] = "replace";
        stringArray[60] = "replace";
        stringArray[61] = "toString";
        stringArray[62] = "plus";
        stringArray[63] = "path";
        stringArray[64] = "substring";
        stringArray[65] = "size";
        stringArray[66] = "separator";
        stringArray[67] = "replace";
        stringArray[68] = "replace";
        stringArray[69] = "leftShift";
        stringArray[70] = "minus";
        stringArray[71] = "minus";
        stringArray[72] = "startsWith";
        stringArray[73] = "plus";
        stringArray[74] = "path";
        stringArray[75] = "substring";
        stringArray[76] = "plus";
        stringArray[77] = "plus";
        stringArray[78] = "absolutePath";
        stringArray[79] = "parentFile";
        stringArray[80] = "separator";
        stringArray[81] = "substring";
        stringArray[82] = "exists";
        stringArray[83] = "canonicalFile";
        stringArray[84] = "<$constructor$>";
        stringArray[85] = "exists";
        stringArray[86] = "parentFile";
        stringArray[87] = "canonicalFile";
        stringArray[88] = "<$constructor$>";
        stringArray[89] = "absolutePath";
        stringArray[90] = "getUserDir";
        stringArray[91] = "plus";
        stringArray[92] = "plus";
        stringArray[93] = "separator";
        stringArray[94] = "<$constructor$>";
        stringArray[95] = "endsWith";
        stringArray[96] = "separator";
        stringArray[97] = "endsWith";
        stringArray[98] = "parentFile";
        stringArray[99] = "listFiles";
        stringArray[100] = "matchFiles";
        stringArray[101] = "replace";
        stringArray[102] = "toLowerCase";
        stringArray[103] = "iterator";
        stringArray[104] = "startsWith";
        stringArray[105] = "canonicalForm";
        stringArray[106] = "getAbsolutePath";
        stringArray[107] = "name";
        stringArray[108] = "toString";
        stringArray[109] = "render";
        stringArray[110] = "isDirectory";
        stringArray[111] = "plus";
        stringArray[112] = "plus";
        stringArray[113] = "add";
        stringArray[114] = "iterator";
        stringArray[115] = "startsWith";
        stringArray[116] = "canonicalForm";
        stringArray[117] = "getAbsolutePath";
        stringArray[118] = "name";
        stringArray[119] = "toString";
        stringArray[120] = "render";
        stringArray[121] = "isDirectory";
        stringArray[122] = "plus";
        stringArray[123] = "plus";
        stringArray[124] = "add";
        stringArray[125] = "lastIndexOf";
        stringArray[126] = "size";
        stringArray[127] = "lastIndexOf";
        stringArray[128] = "plus";
        stringArray[129] = "replace";
        stringArray[130] = "replace";
        stringArray[131] = "toString";
        stringArray[132] = "replace";
        stringArray[133] = "replace";
        stringArray[134] = "isWindows";
    }

    private static /* synthetic */ CallSiteArray $createCallSiteArray() {
        String[] stringArray = new String[135];
        FileNameCompleter.$createCallSiteArray_1(stringArray);
        return new CallSiteArray(FileNameCompleter.class, stringArray);
    }

    private static /* synthetic */ CallSite[] $getCallSiteArray() {
        CallSiteArray callSiteArray;
        if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
            callSiteArray = FileNameCompleter.$createCallSiteArray();
            $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
        }
        return callSiteArray.array;
    }
}

