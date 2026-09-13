/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.proguard;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

public class ProguardMap {
    private static final String ARRAY_SYMBOL = "[]";
    private Map<String, ClassData> mClassesFromClearName = new HashMap<String, ClassData>();
    private Map<String, ClassData> mClassesFromObfuscatedName = new HashMap<String, ClassData>();

    private static void parseException(String msg) throws ParseException {
        throw new ParseException(msg, 0);
    }

    public void readFromFile(File mapFile) throws FileNotFoundException, IOException, ParseException {
        this.readFromReader(new FileReader(mapFile));
    }

    public void readFromReader(Reader mapReader) throws IOException, ParseException {
        BufferedReader reader = new BufferedReader(mapReader);
        String line = reader.readLine();
        while (line != null) {
            int sep = line.indexOf(" -> ");
            if (sep == -1 || sep + 5 >= line.length()) {
                ProguardMap.parseException("Error parsing class line: '" + line + "'");
            }
            String clearClassName = line.substring(0, sep);
            String obfuscatedClassName = line.substring(sep + 4, line.length() - 1);
            ClassData classData = new ClassData(clearClassName);
            this.mClassesFromClearName.put(clearClassName, classData);
            this.mClassesFromObfuscatedName.put(obfuscatedClassName, classData);
            line = reader.readLine();
            while (line != null && line.startsWith("    ")) {
                String trimmed = line.trim();
                int ws = trimmed.indexOf(32);
                sep = trimmed.indexOf(" -> ");
                if (ws == -1 || sep == -1) {
                    ProguardMap.parseException("Error parse field/method line: '" + line + "'");
                }
                String type = trimmed.substring(0, ws);
                String clearName = trimmed.substring(ws + 1, sep);
                String obfuscatedName = trimmed.substring(sep + 4, trimmed.length());
                if (clearName.indexOf(40) == -1) {
                    classData.addField(obfuscatedName, clearName);
                } else {
                    int obfuscatedLine = 0;
                    int colon = type.indexOf(58);
                    if (colon != -1) {
                        obfuscatedLine = Integer.parseInt(type.substring(0, colon));
                        type = type.substring(colon + 1);
                    }
                    if ((colon = type.indexOf(58)) != -1) {
                        type = type.substring(colon + 1);
                    }
                    int op = clearName.indexOf(40);
                    int cp = clearName.indexOf(41);
                    if (op == -1 || cp == -1) {
                        ProguardMap.parseException("Error parse method line: '" + line + "'");
                    }
                    String sig = clearName.substring(op, cp + 1);
                    int clearLine = obfuscatedLine;
                    colon = clearName.lastIndexOf(58);
                    if (colon != -1) {
                        clearLine = Integer.parseInt(clearName.substring(colon + 1));
                        clearName = clearName.substring(0, colon);
                    }
                    if ((colon = clearName.lastIndexOf(58)) != -1) {
                        clearLine = Integer.parseInt(clearName.substring(colon + 1));
                        clearName = clearName.substring(0, colon);
                    }
                    clearName = clearName.substring(0, op);
                    String clearSig = ProguardMap.fromProguardSignature(sig + type);
                    classData.addFrame(obfuscatedName, clearName, clearSig, obfuscatedLine, clearLine);
                }
                line = reader.readLine();
            }
        }
        reader.close();
    }

    public String getClassName(String obfuscatedClassName) {
        String baseName = obfuscatedClassName;
        String arraySuffix = "";
        while (baseName.endsWith(ARRAY_SYMBOL)) {
            arraySuffix = arraySuffix + ARRAY_SYMBOL;
            baseName = baseName.substring(0, baseName.length() - ARRAY_SYMBOL.length());
        }
        ClassData classData = this.mClassesFromObfuscatedName.get(baseName);
        String clearBaseName = classData == null ? baseName : classData.getClearName();
        return clearBaseName + arraySuffix;
    }

    public String getFieldName(String clearClass, String obfuscatedField) {
        ClassData classData = this.mClassesFromClearName.get(clearClass);
        if (classData == null) {
            return obfuscatedField;
        }
        return classData.getField(obfuscatedField);
    }

    public Frame getFrame(String clearClassName, String obfuscatedMethodName, String obfuscatedSignature, String obfuscatedFilename, int obfuscatedLine) {
        String clearSignature = this.getSignature(obfuscatedSignature);
        ClassData classData = this.mClassesFromClearName.get(clearClassName);
        if (classData == null) {
            return new Frame(obfuscatedMethodName, clearSignature, obfuscatedFilename, obfuscatedLine);
        }
        return classData.getFrame(clearClassName, obfuscatedMethodName, clearSignature, obfuscatedFilename, obfuscatedLine);
    }

    private static String fromProguardSignature(String sig) throws ParseException {
        if (sig.startsWith("(")) {
            int end = sig.indexOf(41);
            if (end == -1) {
                ProguardMap.parseException("Error parsing signature: " + sig);
            }
            StringBuilder converted = new StringBuilder();
            converted.append('(');
            if (end > 1) {
                for (String arg : sig.substring(1, end).split(",")) {
                    converted.append(ProguardMap.fromProguardSignature(arg));
                }
            }
            converted.append(')');
            converted.append(ProguardMap.fromProguardSignature(sig.substring(end + 1)));
            return converted.toString();
        }
        if (sig.endsWith(ARRAY_SYMBOL)) {
            return "[" + ProguardMap.fromProguardSignature(sig.substring(0, sig.length() - 2));
        }
        if (sig.equals("boolean")) {
            return "Z";
        }
        if (sig.equals("byte")) {
            return "B";
        }
        if (sig.equals("char")) {
            return "C";
        }
        if (sig.equals("short")) {
            return "S";
        }
        if (sig.equals("int")) {
            return "I";
        }
        if (sig.equals("long")) {
            return "J";
        }
        if (sig.equals("float")) {
            return "F";
        }
        if (sig.equals("double")) {
            return "D";
        }
        if (sig.equals("void")) {
            return "V";
        }
        return "L" + sig.replace('.', '/') + ";";
    }

    private String getSignature(String obfuscatedSig) {
        StringBuilder builder = new StringBuilder();
        for (int i2 = 0; i2 < obfuscatedSig.length(); ++i2) {
            if (obfuscatedSig.charAt(i2) == 'L') {
                int e2 = obfuscatedSig.indexOf(59, i2);
                builder.append('L');
                String cls = obfuscatedSig.substring(i2 + 1, e2).replace('/', '.');
                builder.append(this.getClassName(cls).replace('.', '/'));
                builder.append(';');
                i2 = e2;
                continue;
            }
            builder.append(obfuscatedSig.charAt(i2));
        }
        return builder.toString();
    }

    private static String getFileName(String clearClass) {
        int dollar;
        String filename = clearClass;
        int dot = filename.lastIndexOf(46);
        if (dot != -1) {
            filename = filename.substring(dot + 1);
        }
        if ((dollar = filename.indexOf(36)) != -1) {
            filename = filename.substring(0, dollar);
        }
        return filename + ".java";
    }

    public static class Frame {
        public final String methodName;
        public final String signature;
        public final String filename;
        public final int line;

        public Frame(String methodName, String signature, String filename, int line) {
            this.methodName = methodName;
            this.signature = signature;
            this.filename = filename;
            this.line = line;
        }
    }

    private static class ClassData {
        private String mClearName;
        private Map<String, String> mFields = new HashMap<String, String>();
        private Map<String, FrameData> mFrames = new HashMap<String, FrameData>();

        public ClassData(String clearName) {
            this.mClearName = clearName;
        }

        public String getClearName() {
            return this.mClearName;
        }

        public void addField(String obfuscatedName, String clearName) {
            this.mFields.put(obfuscatedName, clearName);
        }

        public String getField(String obfuscatedName) {
            String clearField = this.mFields.get(obfuscatedName);
            return clearField == null ? obfuscatedName : clearField;
        }

        public void addFrame(String obfuscatedMethodName, String clearMethodName, String clearSignature, int obfuscatedLine, int clearLine) {
            String key = obfuscatedMethodName + clearSignature;
            this.mFrames.put(key, new FrameData(clearMethodName, obfuscatedLine - clearLine));
        }

        public Frame getFrame(String clearClassName, String obfuscatedMethodName, String clearSignature, String obfuscatedFilename, int obfuscatedLine) {
            String key = obfuscatedMethodName + clearSignature;
            FrameData frame = this.mFrames.get(key);
            if (frame == null) {
                frame = new FrameData(obfuscatedMethodName, 0);
            }
            return new Frame(frame.clearMethodName, clearSignature, ProguardMap.getFileName(clearClassName), obfuscatedLine - frame.lineDelta);
        }
    }

    private static class FrameData {
        public String clearMethodName;
        public int lineDelta;

        public FrameData(String clearMethodName, int lineDelta) {
            this.clearMethodName = clearMethodName;
            this.lineDelta = lineDelta;
        }
    }
}

