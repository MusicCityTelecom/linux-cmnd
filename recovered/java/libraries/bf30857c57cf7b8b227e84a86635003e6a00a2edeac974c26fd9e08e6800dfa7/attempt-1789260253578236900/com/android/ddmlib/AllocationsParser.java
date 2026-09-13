/*
 * Decompiled with CFR 0.152.
 */
package com.android.ddmlib;

import com.android.ddmlib.AllocationInfo;
import com.android.ddmlib.ByteBufferUtil;
import java.nio.ByteBuffer;

public class AllocationsParser {
    private static String descriptorToDot(String str) {
        int array = 0;
        while (str.startsWith("[")) {
            str = str.substring(1);
            ++array;
        }
        int len = str.length();
        if (len >= 2 && str.charAt(0) == 'L' && str.charAt(len - 1) == ';') {
            str = str.substring(1, len - 1);
            str = str.replace('/', '.');
        } else if ("C".equals(str)) {
            str = "char";
        } else if ("B".equals(str)) {
            str = "byte";
        } else if ("Z".equals(str)) {
            str = "boolean";
        } else if ("S".equals(str)) {
            str = "short";
        } else if ("I".equals(str)) {
            str = "int";
        } else if ("J".equals(str)) {
            str = "long";
        } else if ("F".equals(str)) {
            str = "float";
        } else if ("D".equals(str)) {
            str = "double";
        }
        for (int a2 = 0; a2 < array; ++a2) {
            str = str + "[]";
        }
        return str;
    }

    private static void readStringTable(ByteBuffer data, String[] strings) {
        int count = strings.length;
        for (int i2 = 0; i2 < count; ++i2) {
            int nameLen = data.getInt();
            String descriptor = ByteBufferUtil.getString(data, nameLen);
            strings[i2] = AllocationsParser.descriptorToDot(descriptor);
        }
    }

    public static AllocationInfo[] parse(ByteBuffer data) {
        data = AllocationsParser.fixAllocOverflow(data);
        int messageHdrLen = data.get() & 0xFF;
        int entryHdrLen = data.get() & 0xFF;
        int stackFrameLen = data.get() & 0xFF;
        int numEntries = data.getShort() & 0xFFFF;
        int offsetToStrings = data.getInt();
        int numClassNames = data.getShort() & 0xFFFF;
        int numMethodNames = data.getShort() & 0xFFFF;
        int numFileNames = data.getShort() & 0xFFFF;
        data.position(offsetToStrings);
        String[] classNames = new String[numClassNames];
        String[] methodNames = new String[numMethodNames];
        String[] fileNames = new String[numFileNames];
        AllocationsParser.readStringTable(data, classNames);
        AllocationsParser.readStringTable(data, methodNames);
        AllocationsParser.readStringTable(data, fileNames);
        data.position(messageHdrLen);
        AllocationInfo[] allocations = new AllocationInfo[numEntries];
        for (int i2 = 0; i2 < numEntries; ++i2) {
            int totalSize = data.getInt();
            int threadId = data.getShort() & 0xFFFF;
            int classNameIndex = data.getShort() & 0xFFFF;
            int stackDepth = data.get() & 0xFF;
            for (int skip = 9; skip < entryHdrLen; ++skip) {
                data.get();
            }
            StackTraceElement[] steArray = new StackTraceElement[stackDepth];
            for (int sti = 0; sti < stackDepth; ++sti) {
                int methodClassNameIndex = data.getShort() & 0xFFFF;
                int methodNameIndex = data.getShort() & 0xFFFF;
                int methodSourceFileIndex = data.getShort() & 0xFFFF;
                short lineNumber = data.getShort();
                String methodClassName = classNames[methodClassNameIndex];
                String methodName = methodNames[methodNameIndex];
                String methodSourceFile = fileNames[methodSourceFileIndex];
                steArray[sti] = new StackTraceElement(methodClassName, methodName, methodSourceFile, lineNumber);
                for (int skip = 8; skip < stackFrameLen; ++skip) {
                    data.get();
                }
            }
            allocations[i2] = new AllocationInfo(numEntries - i2, classNames[classNameIndex], totalSize, (short)threadId, steArray);
        }
        return allocations;
    }

    private static ByteBuffer fixAllocOverflow(ByteBuffer original) {
        int i2;
        ByteBuffer output = ByteBuffer.allocate(original.capacity());
        int messageHdrLen = original.get() & 0xFF;
        output.put((byte)messageHdrLen);
        int entryHdrLen = original.get() & 0xFF;
        output.put((byte)entryHdrLen);
        int stackFrameLen = original.get() & 0xFF;
        output.put((byte)stackFrameLen);
        int numEntries = original.getShort() & 0xFFFF;
        if (numEntries != 0) {
            original.rewind();
            return original;
        }
        int offsetToStrings = original.getInt();
        if (offsetToStrings - messageHdrLen < entryHdrLen + stackFrameLen) {
            original.rewind();
            return original;
        }
        numEntries = 65535;
        output.putShort((short)numEntries);
        output.putInt(offsetToStrings);
        int numClassNames = original.getShort() & 0xFFFF;
        output.putShort((short)numClassNames);
        int numMethodNames = original.getShort() & 0xFFFF;
        output.putShort((short)numMethodNames);
        int numFileNames = original.getShort() & 0xFFFF;
        output.putShort((short)numFileNames);
        for (i2 = output.position(); i2 < messageHdrLen; ++i2) {
            output.put((byte)0);
        }
        original.position(messageHdrLen);
        for (i2 = 0; i2 < numEntries; ++i2) {
            output.putInt(original.getInt());
            output.putShort(original.getShort());
            output.putShort(original.getShort());
            int stackDepth = original.get() & 0xFF;
            output.put((byte)stackDepth);
            for (int skip = 9; skip < entryHdrLen; ++skip) {
                output.put(original.get());
            }
            for (int sti = 0; sti < stackDepth; ++sti) {
                output.putShort(original.getShort());
                output.putShort(original.getShort());
                output.putShort(original.getShort());
                output.putShort(original.getShort());
                for (int skip = 8; skip < stackFrameLen; ++skip) {
                    output.put(original.get());
                }
            }
        }
        original.position(offsetToStrings);
        int stringOffset = output.position();
        while (original.hasRemaining()) {
            output.put(original.get());
        }
        int end = output.position();
        output.position(5);
        output.putInt(stringOffset);
        output.position(end);
        output.flip();
        return output;
    }
}

