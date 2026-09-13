/*
 * Decompiled with CFR 0.152.
 */
package com.android.ddmlib.jdwp;

public class JdwpCommands {
    public static final int SET_VM = 1;
    public static final int CMD_VM_VERSION = 1;
    public static final int CMD_VM_CLASSESBYSIGNATURE = 2;
    public static final int CMD_VM_ALLCLASSES = 3;
    public static final int CMD_VM_ALLTHREADS = 4;
    public static final int CMD_VM_TOPLEVELTHREADGROUPS = 5;
    public static final int CMD_VM_DISPOSE = 6;
    public static final int CMD_VM_IDSIZES = 7;
    public static final int CMD_VM_SUSPEND = 8;
    public static final int CMD_VM_RESUME = 9;
    public static final int CMD_VM_EXIT = 10;
    public static final int CMD_VM_CREATESTRING = 11;
    public static final int CMD_VM_CAPABILITIES = 12;
    public static final int CMD_VM_CLASSPATHS = 13;
    public static final int CMD_VM_DISPOSEOBJECTS = 14;
    public static final int CMD_VM_HOLDEVENTS = 15;
    public static final int CMD_VM_RELEASEEVENTS = 16;
    public static final int CMD_VM_CAPABILITIESNEW = 17;
    public static final int CMD_VM_REDEFINECLASSES = 18;
    public static final int CMD_VM_SETDEFAULTSTRATUM = 19;
    public static final int CMD_VM_ALLCLASSESWITHGENERIC = 20;
    public static final int SET_REFTYPE = 2;
    public static final int CMD_REFTYPE_SIGNATURE = 1;
    public static final int CMD_REFTYPE_CLASSLOADER = 2;
    public static final int CMD_REFTYPE_MODIFIERS = 3;
    public static final int CMD_REFTYPE_FIELDS = 4;
    public static final int CMD_REFTYPE_METHODS = 5;
    public static final int CMD_REFTYPE_GETVALUES = 6;
    public static final int CMD_REFTYPE_SOURCEFILE = 7;
    public static final int CMD_REFTYPE_NESTEDTYPES = 8;
    public static final int CMD_REFTYPE_STATUS = 9;
    public static final int CMD_REFTYPE_INTERFACES = 10;
    public static final int CMD_REFTYPE_CLASSOBJECT = 11;
    public static final int CMD_REFTYPE_SOURCEDEBUGEXTENSION = 12;
    public static final int CMD_REFTYPE_SIGNATUREWITHGENERIC = 13;
    public static final int CMD_REFTYPE_FIELDSWITHGENERIC = 14;
    public static final int CMD_REFTYPE_METHODSWITHGENERIC = 15;
    public static final int SET_CLASSTYPE = 3;
    public static final int CMD_CLASSTYPE_SUPERCLASS = 1;
    public static final int CMD_CLASSTYPE_SETVALUES = 2;
    public static final int CMD_CLASSTYPE_INVOKEMETHOD = 3;
    public static final int CMD_CLASSTYPE_NEWINSTANCE = 4;
    public static final int SET_ARRAYTYPE = 4;
    public static final int CMD_ARRAYTYPE_NEWINSTANCE = 1;
    public static final int SET_INTERFACETYPE = 5;
    public static final int SET_METHOD = 6;
    public static final int CMD_METHOD_LINETABLE = 1;
    public static final int CMD_METHOD_VARIABLETABLE = 2;
    public static final int CMD_METHOD_BYTECODES = 3;
    public static final int CMD_METHOD_ISOBSOLETE = 4;
    public static final int CMD_METHOD_VARIABLETABLEWITHGENERIC = 5;
    public static final int SET_FIELD = 8;
    public static final int SET_OBJREF = 9;
    public static final int CMD_OBJREF_REFERENCETYPE = 1;
    public static final int CMD_OBJREF_GETVALUES = 2;
    public static final int CMD_OBJREF_SETVALUES = 3;
    public static final int CMD_OBJREF_MONITORINFO = 5;
    public static final int CMD_OBJREF_INVOKEMETHOD = 6;
    public static final int CMD_OBJREF_DISABLECOLLECTION = 7;
    public static final int CMD_OBJREF_ENABLECOLLECTION = 8;
    public static final int CMD_OBJREF_ISCOLLECTED = 9;
    public static final int SET_STRINGREF = 10;
    public static final int CMD_STRINGREF_VALUE = 1;
    public static final int SET_THREADREF = 11;
    public static final int CMD_THREADREF_NAME = 1;
    public static final int CMD_THREADREF_SUSPEND = 2;
    public static final int CMD_THREADREF_RESUME = 3;
    public static final int CMD_THREADREF_STATUS = 4;
    public static final int CMD_THREADREF_THREADGROUP = 5;
    public static final int CMD_THREADREF_FRAMES = 6;
    public static final int CMD_THREADREF_FRAMECOUNT = 7;
    public static final int CMD_THREADREF_OWNEDMONITORS = 8;
    public static final int CMD_THREADREF_CURRENTCONTENDEDMONITOR = 9;
    public static final int CMD_THREADREF_STOP = 10;
    public static final int CMD_THREADREF_INTERRUPT = 11;
    public static final int CMD_THREADREF_SUSPENDCOUNT = 12;
    public static final int SET_THREADGROUPREF = 12;
    public static final int CMD_THREADGROUPREF_NAME = 1;
    public static final int CMD_THREADGROUPREF_PARENT = 2;
    public static final int CMD_THREADGROUPREF_CHILDREN = 3;
    public static final int SET_ARRAYREF = 13;
    public static final int CMD_ARRAYREF_LENGTH = 1;
    public static final int CMD_ARRAYREF_GETVALUES = 2;
    public static final int CMD_ARRAYREF_SETVALUES = 3;
    public static final int SET_CLASSLOADERREF = 14;
    public static final int CMD_CLASSLOADERREF_VISIBLECLASSES = 1;
    public static final int SET_EVENTREQUEST = 15;
    public static final int CMD_EVENTREQUEST_SET = 1;
    public static final int CMD_EVENTREQUEST_CLEAR = 2;
    public static final int CMD_EVENTREQUEST_CLEARALLBREAKPOINTS = 3;
    public static final int SET_STACKFRAME = 16;
    public static final int CMD_STACKFRAME_GETVALUES = 1;
    public static final int CMD_STACKFRAME_SETVALUES = 2;
    public static final int CMD_STACKFRAME_THISOBJECT = 3;
    public static final int CMD_STACKFRAME_POPFRAMES = 4;
    public static final int SET_CLASSOBJECTREF = 17;
    public static final int CMD_CLASSOBJECTREF_REFLECTEDTYPE = 1;
    public static final int SET_EVENT = 64;
    public static final int CMD_EVENT_COMPOSITE = 100;

    public static String commandSetToString(int cmdSet) {
        switch (cmdSet) {
            case 1: {
                return "SET_VM";
            }
            case 2: {
                return "SET_REFTYPE";
            }
            case 3: {
                return "SET_CLASSTYPE";
            }
            case 4: {
                return "SET_ARRAYTYPE";
            }
            case 5: {
                return "SET_INTERFACETYPE";
            }
            case 6: {
                return "SET_METHOD";
            }
            case 8: {
                return "SET_FIELD";
            }
            case 9: {
                return "SET_OBJREF";
            }
            case 10: {
                return "SET_STRINGREF";
            }
            case 11: {
                return "SET_THREADREF";
            }
            case 12: {
                return "SET_THREADGROUPREF";
            }
            case 13: {
                return "SET_ARRAYREF";
            }
            case 14: {
                return "SET_CLASSLOADERREF";
            }
            case 15: {
                return "SET_EVENTREQUEST";
            }
            case 16: {
                return "SET_STACKFRAME";
            }
            case 17: {
                return "SET_CLASSOBJECTREF";
            }
            case 64: {
                return "SET_EVENT";
            }
        }
        return String.format("SET_%02X", cmdSet);
    }

    public static String commandToString(int cmdSet, int cmd) {
        switch (cmdSet) {
            case 1: {
                switch (cmd) {
                    case 1: {
                        return "CMD_VM_VERSION";
                    }
                    case 2: {
                        return "CMD_VM_CLASSESBYSIGNATURE";
                    }
                    case 3: {
                        return "CMD_VM_ALLCLASSES";
                    }
                    case 4: {
                        return "CMD_VM_ALLTHREADS";
                    }
                    case 5: {
                        return "CMD_VM_TOPLEVELTHREADGROUPS";
                    }
                    case 6: {
                        return "CMD_VM_DISPOSE";
                    }
                    case 7: {
                        return "CMD_VM_IDSIZES";
                    }
                    case 8: {
                        return "CMD_VM_SUSPEND";
                    }
                    case 9: {
                        return "CMD_VM_RESUME";
                    }
                    case 10: {
                        return "CMD_VM_EXIT";
                    }
                    case 11: {
                        return "CMD_VM_CREATESTRING";
                    }
                    case 12: {
                        return "CMD_VM_CAPABILITIES";
                    }
                    case 13: {
                        return "CMD_VM_CLASSPATHS";
                    }
                    case 14: {
                        return "CMD_VM_DISPOSEOBJECTS";
                    }
                    case 15: {
                        return "CMD_VM_HOLDEVENTS";
                    }
                    case 16: {
                        return "CMD_VM_RELEASEEVENTS";
                    }
                    case 17: {
                        return "CMD_VM_CAPABILITIESNEW";
                    }
                    case 18: {
                        return "CMD_VM_REDEFINECLASSES";
                    }
                    case 19: {
                        return "CMD_VM_SETDEFAULTSTRATUM";
                    }
                    case 20: {
                        return "CMD_VM_ALLCLASSESWITHGENERIC";
                    }
                }
                return JdwpCommands.unknownCommandToString(cmdSet, cmd);
            }
            case 2: {
                switch (cmd) {
                    case 1: {
                        return "CMD_REFTYPE_SIGNATURE";
                    }
                    case 2: {
                        return "CMD_REFTYPE_CLASSLOADER";
                    }
                    case 3: {
                        return "CMD_REFTYPE_MODIFIERS";
                    }
                    case 4: {
                        return "CMD_REFTYPE_FIELDS";
                    }
                    case 5: {
                        return "CMD_REFTYPE_METHODS";
                    }
                    case 6: {
                        return "CMD_REFTYPE_GETVALUES";
                    }
                    case 7: {
                        return "CMD_REFTYPE_SOURCEFILE";
                    }
                    case 8: {
                        return "CMD_REFTYPE_NESTEDTYPES";
                    }
                    case 9: {
                        return "CMD_REFTYPE_STATUS";
                    }
                    case 10: {
                        return "CMD_REFTYPE_INTERFACES";
                    }
                    case 11: {
                        return "CMD_REFTYPE_CLASSOBJECT";
                    }
                    case 12: {
                        return "CMD_REFTYPE_SOURCEDEBUGEXTENSION";
                    }
                    case 13: {
                        return "CMD_REFTYPE_SIGNATUREWITHGENERIC";
                    }
                    case 14: {
                        return "CMD_REFTYPE_FIELDSWITHGENERIC";
                    }
                    case 15: {
                        return "CMD_REFTYPE_METHODSWITHGENERIC";
                    }
                }
                return JdwpCommands.unknownCommandToString(cmdSet, cmd);
            }
            case 3: {
                switch (cmd) {
                    case 1: {
                        return "CMD_CLASSTYPE_SUPERCLASS";
                    }
                    case 2: {
                        return "CMD_CLASSTYPE_SETVALUES";
                    }
                    case 3: {
                        return "CMD_CLASSTYPE_INVOKEMETHOD";
                    }
                    case 4: {
                        return "CMD_CLASSTYPE_NEWINSTANCE";
                    }
                }
                return JdwpCommands.unknownCommandToString(cmdSet, cmd);
            }
            case 4: {
                switch (cmd) {
                    case 1: {
                        return "CMD_ARRAYTYPE_NEWINSTANCE";
                    }
                }
                return JdwpCommands.unknownCommandToString(cmdSet, cmd);
            }
            case 5: {
                switch (cmd) {
                    default: 
                }
                return JdwpCommands.unknownCommandToString(cmdSet, cmd);
            }
            case 6: {
                switch (cmd) {
                    case 1: {
                        return "CMD_METHOD_LINETABLE";
                    }
                    case 2: {
                        return "CMD_METHOD_VARIABLETABLE";
                    }
                    case 3: {
                        return "CMD_METHOD_BYTECODES";
                    }
                    case 4: {
                        return "CMD_METHOD_ISOBSOLETE";
                    }
                    case 5: {
                        return "CMD_METHOD_VARIABLETABLEWITHGENERIC";
                    }
                }
                return JdwpCommands.unknownCommandToString(cmdSet, cmd);
            }
            case 8: {
                switch (cmd) {
                    default: 
                }
                return JdwpCommands.unknownCommandToString(cmdSet, cmd);
            }
            case 9: {
                switch (cmd) {
                    case 1: {
                        return "CMD_OBJREF_REFERENCETYPE";
                    }
                    case 2: {
                        return "CMD_OBJREF_GETVALUES";
                    }
                    case 3: {
                        return "CMD_OBJREF_SETVALUES";
                    }
                    case 5: {
                        return "CMD_OBJREF_MONITORINFO";
                    }
                    case 6: {
                        return "CMD_OBJREF_INVOKEMETHOD";
                    }
                    case 7: {
                        return "CMD_OBJREF_DISABLECOLLECTION";
                    }
                    case 8: {
                        return "CMD_OBJREF_ENABLECOLLECTION";
                    }
                    case 9: {
                        return "CMD_OBJREF_ISCOLLECTED";
                    }
                }
                return JdwpCommands.unknownCommandToString(cmdSet, cmd);
            }
            case 10: {
                switch (cmd) {
                    case 1: {
                        return "CMD_STRINGREF_VALUE";
                    }
                }
                return JdwpCommands.unknownCommandToString(cmdSet, cmd);
            }
            case 11: {
                switch (cmd) {
                    case 1: {
                        return "CMD_THREADREF_NAME";
                    }
                    case 2: {
                        return "CMD_THREADREF_SUSPEND";
                    }
                    case 3: {
                        return "CMD_THREADREF_RESUME";
                    }
                    case 4: {
                        return "CMD_THREADREF_STATUS";
                    }
                    case 5: {
                        return "CMD_THREADREF_THREADGROUP";
                    }
                    case 6: {
                        return "CMD_THREADREF_FRAMES";
                    }
                    case 7: {
                        return "CMD_THREADREF_FRAMECOUNT";
                    }
                    case 8: {
                        return "CMD_THREADREF_OWNEDMONITORS";
                    }
                    case 9: {
                        return "CMD_THREADREF_CURRENTCONTENDEDMONITOR";
                    }
                    case 10: {
                        return "CMD_THREADREF_STOP";
                    }
                    case 11: {
                        return "CMD_THREADREF_INTERRUPT";
                    }
                    case 12: {
                        return "CMD_THREADREF_SUSPENDCOUNT";
                    }
                }
                return JdwpCommands.unknownCommandToString(cmdSet, cmd);
            }
            case 12: {
                switch (cmd) {
                    case 1: {
                        return "CMD_THREADGROUPREF_NAME";
                    }
                    case 2: {
                        return "CMD_THREADGROUPREF_PARENT";
                    }
                    case 3: {
                        return "CMD_THREADGROUPREF_CHILDREN";
                    }
                }
                return JdwpCommands.unknownCommandToString(cmdSet, cmd);
            }
            case 13: {
                switch (cmd) {
                    case 1: {
                        return "CMD_ARRAYREF_LENGTH";
                    }
                    case 2: {
                        return "CMD_ARRAYREF_GETVALUES";
                    }
                    case 3: {
                        return "CMD_ARRAYREF_SETVALUES";
                    }
                }
                return JdwpCommands.unknownCommandToString(cmdSet, cmd);
            }
            case 14: {
                switch (cmd) {
                    case 1: {
                        return "CMD_CLASSLOADERREF_VISIBLECLASSES";
                    }
                }
                return JdwpCommands.unknownCommandToString(cmdSet, cmd);
            }
            case 15: {
                switch (cmd) {
                    case 1: {
                        return "CMD_EVENTREQUEST_SET";
                    }
                    case 2: {
                        return "CMD_EVENTREQUEST_CLEAR";
                    }
                    case 3: {
                        return "CMD_EVENTREQUEST_CLEARALLBREAKPOINTS";
                    }
                }
                return JdwpCommands.unknownCommandToString(cmdSet, cmd);
            }
            case 16: {
                switch (cmd) {
                    case 1: {
                        return "CMD_STACKFRAME_GETVALUES";
                    }
                    case 2: {
                        return "CMD_STACKFRAME_SETVALUES";
                    }
                    case 3: {
                        return "CMD_STACKFRAME_THISOBJECT";
                    }
                    case 4: {
                        return "CMD_STACKFRAME_POPFRAMES";
                    }
                }
                return JdwpCommands.unknownCommandToString(cmdSet, cmd);
            }
            case 17: {
                switch (cmd) {
                    case 1: {
                        return "CMD_CLASSOBJECTREF_REFLECTEDTYPE";
                    }
                }
                return JdwpCommands.unknownCommandToString(cmdSet, cmd);
            }
            case 64: {
                switch (cmd) {
                    case 100: {
                        return "CMD_EVENT_COMPOSITE";
                    }
                }
                return JdwpCommands.unknownCommandToString(cmdSet, cmd);
            }
        }
        return JdwpCommands.unknownCommandToString(cmdSet, cmd);
    }

    private static String unknownCommandToString(int cmdSet, int command) {
        return String.format("CMD_%s_%02X", JdwpCommands.commandSetToString(cmdSet).substring(4), command);
    }
}

