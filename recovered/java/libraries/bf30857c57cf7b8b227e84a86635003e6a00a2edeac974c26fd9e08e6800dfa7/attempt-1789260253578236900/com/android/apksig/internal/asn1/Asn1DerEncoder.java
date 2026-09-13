/*
 * Decompiled with CFR 0.152.
 */
package com.android.apksig.internal.asn1;

import com.android.apksig.internal.asn1.Asn1Class;
import com.android.apksig.internal.asn1.Asn1EncodingException;
import com.android.apksig.internal.asn1.Asn1Field;
import com.android.apksig.internal.asn1.Asn1OpaqueObject;
import com.android.apksig.internal.asn1.Asn1TagClass;
import com.android.apksig.internal.asn1.Asn1Tagging;
import com.android.apksig.internal.asn1.Asn1Type;
import com.android.apksig.internal.asn1.ber.BerEncoding;
import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public final class Asn1DerEncoder {
    private Asn1DerEncoder() {
    }

    public static byte[] encode(Object container) throws Asn1EncodingException {
        Class<?> containerClass = container.getClass();
        Asn1Class containerAnnotation = containerClass.getDeclaredAnnotation(Asn1Class.class);
        if (containerAnnotation == null) {
            throw new Asn1EncodingException(containerClass.getName() + " not annotated with " + Asn1Class.class.getName());
        }
        Asn1Type containerType = containerAnnotation.type();
        switch (containerType) {
            case CHOICE: {
                return Asn1DerEncoder.toChoice(container);
            }
            case SEQUENCE: {
                return Asn1DerEncoder.toSequence(container);
            }
        }
        throw new Asn1EncodingException("Unsupported container type: " + (Object)((Object)containerType));
    }

    private static byte[] toChoice(Object container) throws Asn1EncodingException {
        Class<?> containerClass = container.getClass();
        List<AnnotatedField> fields = Asn1DerEncoder.getAnnotatedFields(container);
        if (fields.isEmpty()) {
            throw new Asn1EncodingException("No fields annotated with " + Asn1Field.class.getName() + " in CHOICE class " + containerClass.getName());
        }
        AnnotatedField resultField = null;
        for (AnnotatedField field : fields) {
            Object fieldValue = Asn1DerEncoder.getMemberFieldValue(container, field.getField());
            if (fieldValue == null) continue;
            if (resultField != null) {
                throw new Asn1EncodingException("Multiple non-null fields in CHOICE class " + containerClass.getName() + ": " + resultField.getField().getName() + ", " + field.getField().getName());
            }
            resultField = field;
        }
        if (resultField == null) {
            throw new Asn1EncodingException("No non-null fields in CHOICE class " + containerClass.getName());
        }
        return resultField.toDer();
    }

    private static byte[] toSequence(Object container) throws Asn1EncodingException {
        Class<?> containerClass = container.getClass();
        List<AnnotatedField> fields = Asn1DerEncoder.getAnnotatedFields(container);
        Collections.sort(fields, (f12, f2) -> f12.getAnnotation().index() - f2.getAnnotation().index());
        if (fields.size() > 1) {
            AnnotatedField lastField = null;
            for (AnnotatedField field : fields) {
                if (lastField != null && lastField.getAnnotation().index() == field.getAnnotation().index()) {
                    throw new Asn1EncodingException("Fields have the same index: " + containerClass.getName() + "." + lastField.getField().getName() + " and ." + field.getField().getName());
                }
                lastField = field;
            }
        }
        ArrayList<byte[]> serializedFields = new ArrayList<byte[]>(fields.size());
        for (AnnotatedField field : fields) {
            byte[] serializedField;
            try {
                serializedField = field.toDer();
            }
            catch (Asn1EncodingException e2) {
                throw new Asn1EncodingException("Failed to encode " + containerClass.getName() + "." + field.getField().getName(), e2);
            }
            if (serializedField == null) continue;
            serializedFields.add(serializedField);
        }
        return Asn1DerEncoder.createTag(0, true, 16, (byte[][])serializedFields.toArray((T[])new byte[0][]));
    }

    private static byte[] toSetOf(Collection<?> values2, Asn1Type elementType) throws Asn1EncodingException {
        ArrayList<byte[]> serializedValues = new ArrayList<byte[]>(values2.size());
        for (Object value : values2) {
            serializedValues.add(JavaToDerConverter.toDer(value, elementType, null));
        }
        if (serializedValues.size() > 1) {
            Collections.sort(serializedValues, ByteArrayLexicographicComparator.INSTANCE);
        }
        return Asn1DerEncoder.createTag(0, true, 17, (byte[][])serializedValues.toArray((T[])new byte[0][]));
    }

    private static List<AnnotatedField> getAnnotatedFields(Object container) throws Asn1EncodingException {
        Class<?> containerClass = container.getClass();
        Field[] declaredFields = containerClass.getDeclaredFields();
        ArrayList<AnnotatedField> result = new ArrayList<AnnotatedField>(declaredFields.length);
        for (Field field : declaredFields) {
            AnnotatedField annotatedField;
            Asn1Field annotation = field.getDeclaredAnnotation(Asn1Field.class);
            if (annotation == null) continue;
            if (Modifier.isStatic(field.getModifiers())) {
                throw new Asn1EncodingException(Asn1Field.class.getName() + " used on a static field: " + containerClass.getName() + "." + field.getName());
            }
            try {
                annotatedField = new AnnotatedField(container, field, annotation);
            }
            catch (Asn1EncodingException e2) {
                throw new Asn1EncodingException("Invalid ASN.1 annotation on " + containerClass.getName() + "." + field.getName(), e2);
            }
            result.add(annotatedField);
        }
        return result;
    }

    private static byte[] toInteger(int value) {
        return Asn1DerEncoder.toInteger((long)value);
    }

    private static byte[] toInteger(long value) {
        return Asn1DerEncoder.toInteger(BigInteger.valueOf(value));
    }

    private static byte[] toInteger(BigInteger value) {
        return Asn1DerEncoder.createTag(0, false, 2, new byte[][]{value.toByteArray()});
    }

    private static byte[] toOid(String oid) throws Asn1EncodingException {
        int secondNode;
        int firstNode;
        ByteArrayOutputStream encodedValue = new ByteArrayOutputStream();
        String[] nodes = oid.split("\\.");
        if (nodes.length < 2) {
            throw new Asn1EncodingException("OBJECT IDENTIFIER must contain at least two nodes: " + oid);
        }
        try {
            firstNode = Integer.parseInt(nodes[0]);
        }
        catch (NumberFormatException e2) {
            throw new Asn1EncodingException("Node #1 not numeric: " + nodes[0]);
        }
        if (firstNode > 6 || firstNode < 0) {
            throw new Asn1EncodingException("Invalid value for node #1: " + firstNode);
        }
        try {
            secondNode = Integer.parseInt(nodes[1]);
        }
        catch (NumberFormatException e3) {
            throw new Asn1EncodingException("Node #2 not numeric: " + nodes[1]);
        }
        if (secondNode >= 40 || secondNode < 0) {
            throw new Asn1EncodingException("Invalid value for node #2: " + secondNode);
        }
        int firstByte = firstNode * 40 + secondNode;
        if (firstByte > 255) {
            throw new Asn1EncodingException("First two nodes out of range: " + firstNode + "." + secondNode);
        }
        encodedValue.write(firstByte);
        for (int i2 = 2; i2 < nodes.length; ++i2) {
            int node;
            String nodeString = nodes[i2];
            try {
                node = Integer.parseInt(nodeString);
            }
            catch (NumberFormatException e4) {
                throw new Asn1EncodingException("Node #" + (i2 + 1) + " not numeric: " + nodeString);
            }
            if (node < 0) {
                throw new Asn1EncodingException("Invalid value for node #" + (i2 + 1) + ": " + node);
            }
            if (node <= 127) {
                encodedValue.write(node);
                continue;
            }
            if (node < 16384) {
                encodedValue.write(0x80 | node >> 7);
                encodedValue.write(node & 0x7F);
                continue;
            }
            if (node < 0x200000) {
                encodedValue.write(0x80 | node >> 14);
                encodedValue.write(0x80 | node >> 7 & 0x7F);
                encodedValue.write(node & 0x7F);
                continue;
            }
            throw new Asn1EncodingException("Node #" + (i2 + 1) + " too large: " + node);
        }
        return Asn1DerEncoder.createTag(0, false, 6, new byte[][]{encodedValue.toByteArray()});
    }

    private static Object getMemberFieldValue(Object obj, Field field) throws Asn1EncodingException {
        try {
            return field.get(obj);
        }
        catch (ReflectiveOperationException e2) {
            throw new Asn1EncodingException("Failed to read " + obj.getClass().getName() + "." + field.getName(), e2);
        }
    }

    private static byte[] createTag(int tagClass, boolean constructed, int tagNumber, byte[] ... contents) {
        byte[] result;
        int contentsPosInResult;
        if (tagNumber >= 31) {
            throw new IllegalArgumentException("High tag numbers not supported: " + tagNumber);
        }
        byte firstIdentifierByte = (byte)(tagClass << 6 | (constructed ? 32 : 0) | tagNumber);
        int contentsLength = 0;
        for (byte[] c2 : contents) {
            contentsLength += c2.length;
        }
        if (contentsLength < 128) {
            contentsPosInResult = 2;
            result = new byte[contentsPosInResult + contentsLength];
            result[0] = firstIdentifierByte;
            result[1] = (byte)contentsLength;
        } else {
            if (contentsLength <= 255) {
                contentsPosInResult = 3;
                result = new byte[contentsPosInResult + contentsLength];
                result[1] = -127;
                result[2] = (byte)contentsLength;
            } else if (contentsLength <= 65535) {
                contentsPosInResult = 4;
                result = new byte[contentsPosInResult + contentsLength];
                result[1] = -126;
                result[2] = (byte)(contentsLength >> 8);
                result[3] = (byte)(contentsLength & 0xFF);
            } else if (contentsLength <= 0xFFFFFF) {
                contentsPosInResult = 5;
                result = new byte[contentsPosInResult + contentsLength];
                result[1] = -125;
                result[2] = (byte)(contentsLength >> 16);
                result[3] = (byte)(contentsLength >> 8 & 0xFF);
                result[4] = (byte)(contentsLength & 0xFF);
            } else {
                contentsPosInResult = 6;
                result = new byte[contentsPosInResult + contentsLength];
                result[1] = -124;
                result[2] = (byte)(contentsLength >> 24);
                result[3] = (byte)(contentsLength >> 16 & 0xFF);
                result[4] = (byte)(contentsLength >> 8 & 0xFF);
                result[5] = (byte)(contentsLength & 0xFF);
            }
            result[0] = firstIdentifierByte;
        }
        for (byte[] c3 : contents) {
            System.arraycopy(c3, 0, result, contentsPosInResult, c3.length);
            contentsPosInResult += c3.length;
        }
        return result;
    }

    private static final class JavaToDerConverter {
        private JavaToDerConverter() {
        }

        public static byte[] toDer(Object source, Asn1Type targetType, Asn1Type targetElementType) throws Asn1EncodingException {
            Class<?> sourceType = source.getClass();
            if (Asn1OpaqueObject.class.equals(sourceType)) {
                ByteBuffer buf = ((Asn1OpaqueObject)source).getEncoded();
                byte[] result = new byte[buf.remaining()];
                buf.get(result);
                return result;
            }
            if (targetType == null || targetType == Asn1Type.ANY) {
                return Asn1DerEncoder.encode(source);
            }
            switch (targetType) {
                case OCTET_STRING: {
                    byte[] value = null;
                    if (source instanceof ByteBuffer) {
                        ByteBuffer buf = (ByteBuffer)source;
                        value = new byte[buf.remaining()];
                        buf.slice().get(value);
                    } else if (source instanceof byte[]) {
                        value = (byte[])source;
                    }
                    if (value == null) break;
                    return Asn1DerEncoder.createTag(0, false, 4, new byte[][]{value});
                }
                case INTEGER: {
                    if (source instanceof Integer) {
                        return Asn1DerEncoder.toInteger((Integer)source);
                    }
                    if (source instanceof Long) {
                        return Asn1DerEncoder.toInteger((Long)source);
                    }
                    if (!(source instanceof BigInteger)) break;
                    return Asn1DerEncoder.toInteger((BigInteger)source);
                }
                case OBJECT_IDENTIFIER: {
                    if (!(source instanceof String)) break;
                    return Asn1DerEncoder.toOid((String)source);
                }
                case SEQUENCE: {
                    Asn1Class containerAnnotation = sourceType.getDeclaredAnnotation(Asn1Class.class);
                    if (containerAnnotation == null || containerAnnotation.type() != Asn1Type.SEQUENCE) break;
                    return Asn1DerEncoder.toSequence(source);
                }
                case CHOICE: {
                    Asn1Class containerAnnotation = sourceType.getDeclaredAnnotation(Asn1Class.class);
                    if (containerAnnotation == null || containerAnnotation.type() != Asn1Type.CHOICE) break;
                    return Asn1DerEncoder.toChoice(source);
                }
                case SET_OF: {
                    return Asn1DerEncoder.toSetOf((Collection)source, targetElementType);
                }
            }
            throw new Asn1EncodingException("Unsupported conversion: " + sourceType.getName() + " to ASN.1 " + (Object)((Object)targetType));
        }
    }

    private static final class AnnotatedField {
        private final Field mField;
        private final Object mObject;
        private final Asn1Field mAnnotation;
        private final Asn1Type mDataType;
        private final Asn1Type mElementDataType;
        private final Asn1TagClass mTagClass;
        private final int mDerTagClass;
        private final int mDerTagNumber;
        private final Asn1Tagging mTagging;
        private final boolean mOptional;

        public AnnotatedField(Object obj, Field field, Asn1Field annotation) throws Asn1EncodingException {
            this.mObject = obj;
            this.mField = field;
            this.mAnnotation = annotation;
            this.mDataType = annotation.type();
            this.mElementDataType = annotation.elementType();
            Asn1TagClass tagClass = annotation.cls();
            if (tagClass == Asn1TagClass.AUTOMATIC) {
                tagClass = annotation.tagNumber() != -1 ? Asn1TagClass.CONTEXT_SPECIFIC : Asn1TagClass.UNIVERSAL;
            }
            this.mTagClass = tagClass;
            this.mDerTagClass = BerEncoding.getTagClass(this.mTagClass);
            int tagNumber = annotation.tagNumber() != -1 ? annotation.tagNumber() : (this.mDataType == Asn1Type.CHOICE || this.mDataType == Asn1Type.ANY ? -1 : BerEncoding.getTagNumber(this.mDataType));
            this.mDerTagNumber = tagNumber;
            this.mTagging = annotation.tagging();
            if ((this.mTagging == Asn1Tagging.EXPLICIT || this.mTagging == Asn1Tagging.IMPLICIT) && annotation.tagNumber() == -1) {
                throw new Asn1EncodingException("Tag number must be specified when tagging mode is " + (Object)((Object)this.mTagging));
            }
            this.mOptional = annotation.optional();
        }

        public Field getField() {
            return this.mField;
        }

        public Asn1Field getAnnotation() {
            return this.mAnnotation;
        }

        public byte[] toDer() throws Asn1EncodingException {
            Object fieldValue = Asn1DerEncoder.getMemberFieldValue(this.mObject, this.mField);
            if (fieldValue == null) {
                if (this.mOptional) {
                    return null;
                }
                throw new Asn1EncodingException("Required field not set");
            }
            byte[] encoded = JavaToDerConverter.toDer(fieldValue, this.mDataType, this.mElementDataType);
            switch (this.mTagging) {
                case NORMAL: {
                    return encoded;
                }
                case EXPLICIT: {
                    return Asn1DerEncoder.createTag(this.mDerTagClass, true, this.mDerTagNumber, new byte[][]{encoded});
                }
                case IMPLICIT: {
                    int originalTagNumber = BerEncoding.getTagNumber(encoded[0]);
                    if (originalTagNumber == 31) {
                        throw new Asn1EncodingException("High-tag-number form not supported");
                    }
                    if (this.mDerTagNumber >= 31) {
                        throw new Asn1EncodingException("Unsupported high tag number: " + this.mDerTagNumber);
                    }
                    encoded[0] = BerEncoding.setTagNumber(encoded[0], this.mDerTagNumber);
                    encoded[0] = BerEncoding.setTagClass(encoded[0], this.mDerTagClass);
                    return encoded;
                }
            }
            throw new RuntimeException("Unknown tagging mode: " + (Object)((Object)this.mTagging));
        }
    }

    private static class ByteArrayLexicographicComparator
    implements Comparator<byte[]> {
        private static final ByteArrayLexicographicComparator INSTANCE = new ByteArrayLexicographicComparator();

        private ByteArrayLexicographicComparator() {
        }

        @Override
        public int compare(byte[] arr1, byte[] arr2) {
            int commonLength = Math.min(arr1.length, arr2.length);
            for (int i2 = 0; i2 < commonLength; ++i2) {
                int diff = (arr1[i2] & 0xFF) - (arr2[i2] & 0xFF);
                if (diff == 0) continue;
                return diff;
            }
            return arr1.length - arr2.length;
        }
    }
}

