/*
 * Decompiled with CFR 0.152.
 */
package com.android.apksig.internal.asn1;

import com.android.apksig.internal.asn1.Asn1Class;
import com.android.apksig.internal.asn1.Asn1DecodingException;
import com.android.apksig.internal.asn1.Asn1Field;
import com.android.apksig.internal.asn1.Asn1OpaqueObject;
import com.android.apksig.internal.asn1.Asn1TagClass;
import com.android.apksig.internal.asn1.Asn1Tagging;
import com.android.apksig.internal.asn1.Asn1Type;
import com.android.apksig.internal.asn1.ber.BerDataValue;
import com.android.apksig.internal.asn1.ber.BerDataValueFormatException;
import com.android.apksig.internal.asn1.ber.BerDataValueReader;
import com.android.apksig.internal.asn1.ber.BerEncoding;
import com.android.apksig.internal.asn1.ber.ByteBufferBerDataValueReader;
import com.android.apksig.internal.util.ByteBufferUtils;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Asn1BerParser {
    private Asn1BerParser() {
    }

    public static <T> T parse(ByteBuffer encoded, Class<T> containerClass) throws Asn1DecodingException {
        BerDataValue containerDataValue;
        try {
            containerDataValue = new ByteBufferBerDataValueReader(encoded).readDataValue();
        }
        catch (BerDataValueFormatException e2) {
            throw new Asn1DecodingException("Failed to decode top-level data value", e2);
        }
        if (containerDataValue == null) {
            throw new Asn1DecodingException("Empty input");
        }
        return Asn1BerParser.parse(containerDataValue, containerClass);
    }

    public static <T> List<T> parseImplicitSetOf(ByteBuffer encoded, Class<T> elementClass) throws Asn1DecodingException {
        BerDataValue containerDataValue;
        try {
            containerDataValue = new ByteBufferBerDataValueReader(encoded).readDataValue();
        }
        catch (BerDataValueFormatException e2) {
            throw new Asn1DecodingException("Failed to decode top-level data value", e2);
        }
        if (containerDataValue == null) {
            throw new Asn1DecodingException("Empty input");
        }
        return Asn1BerParser.parseSetOf(containerDataValue, elementClass);
    }

    private static <T> T parse(BerDataValue container, Class<T> containerClass) throws Asn1DecodingException {
        if (container == null) {
            throw new NullPointerException("container == null");
        }
        if (containerClass == null) {
            throw new NullPointerException("containerClass == null");
        }
        Asn1Type dataType = Asn1BerParser.getContainerAsn1Type(containerClass);
        switch (dataType) {
            case CHOICE: {
                return Asn1BerParser.parseChoice(container, containerClass);
            }
            case SEQUENCE: {
                int expectedTagClass = 0;
                int expectedTagNumber = BerEncoding.getTagNumber(dataType);
                if (container.getTagClass() != expectedTagClass || container.getTagNumber() != expectedTagNumber) {
                    throw new Asn1UnexpectedTagException("Unexpected data value read as " + containerClass.getName() + ". Expected " + BerEncoding.tagClassAndNumberToString(expectedTagClass, expectedTagNumber) + ", but read: " + BerEncoding.tagClassAndNumberToString(container.getTagClass(), container.getTagNumber()));
                }
                return Asn1BerParser.parseSequence(container, containerClass);
            }
        }
        throw new Asn1DecodingException("Parsing container " + (Object)((Object)dataType) + " not supported");
    }

    private static <T> T parseChoice(BerDataValue dataValue, Class<T> containerClass) throws Asn1DecodingException {
        T obj;
        List<AnnotatedField> fields = Asn1BerParser.getAnnotatedFields(containerClass);
        if (fields.isEmpty()) {
            throw new Asn1DecodingException("No fields annotated with " + Asn1Field.class.getName() + " in CHOICE class " + containerClass.getName());
        }
        for (int i2 = 0; i2 < fields.size() - 1; ++i2) {
            AnnotatedField f12 = fields.get(i2);
            int tagNumber1 = f12.getBerTagNumber();
            int tagClass1 = f12.getBerTagClass();
            for (int j2 = i2 + 1; j2 < fields.size(); ++j2) {
                AnnotatedField f2 = fields.get(j2);
                int tagNumber2 = f2.getBerTagNumber();
                int tagClass2 = f2.getBerTagClass();
                if (tagNumber1 != tagNumber2 || tagClass1 != tagClass2) continue;
                throw new Asn1DecodingException("CHOICE fields are indistinguishable because they have the same tag class and number: " + containerClass.getName() + "." + f12.getField().getName() + " and ." + f2.getField().getName());
            }
        }
        try {
            obj = containerClass.getConstructor(new Class[0]).newInstance(new Object[0]);
        }
        catch (IllegalArgumentException | ReflectiveOperationException e2) {
            throw new Asn1DecodingException("Failed to instantiate " + containerClass.getName(), e2);
        }
        for (AnnotatedField field : fields) {
            try {
                field.setValueFrom(dataValue, obj);
                return obj;
            }
            catch (Asn1UnexpectedTagException asn1UnexpectedTagException) {
            }
        }
        throw new Asn1DecodingException("No options of CHOICE " + containerClass.getName() + " matched");
    }

    private static <T> T parseSequence(BerDataValue container, Class<T> containerClass) throws Asn1DecodingException {
        T t3;
        List<AnnotatedField> fields = Asn1BerParser.getAnnotatedFields(containerClass);
        Collections.sort(fields, (f12, f2) -> f12.getAnnotation().index() - f2.getAnnotation().index());
        if (fields.size() > 1) {
            AnnotatedField lastField = null;
            for (AnnotatedField field : fields) {
                if (lastField != null && lastField.getAnnotation().index() == field.getAnnotation().index()) {
                    throw new Asn1DecodingException("Fields have the same index: " + containerClass.getName() + "." + lastField.getField().getName() + " and ." + field.getField().getName());
                }
                lastField = field;
            }
        }
        try {
            t3 = containerClass.getConstructor(new Class[0]).newInstance(new Object[0]);
        }
        catch (IllegalArgumentException | ReflectiveOperationException e2) {
            throw new Asn1DecodingException("Failed to instantiate " + containerClass.getName(), e2);
        }
        int nextUnreadFieldIndex = 0;
        BerDataValueReader elementsReader = container.contentsReader();
        block9: while (nextUnreadFieldIndex < fields.size()) {
            BerDataValue dataValue;
            try {
                dataValue = elementsReader.readDataValue();
            }
            catch (BerDataValueFormatException e3) {
                throw new Asn1DecodingException("Malformed data value", e3);
            }
            if (dataValue == null) break;
            for (int i2 = nextUnreadFieldIndex; i2 < fields.size(); ++i2) {
                AnnotatedField field = fields.get(i2);
                try {
                    if (field.isOptional()) {
                        try {
                            field.setValueFrom(dataValue, t3);
                            nextUnreadFieldIndex = i2 + 1;
                            continue block9;
                        }
                        catch (Asn1UnexpectedTagException e4) {
                            continue;
                        }
                    }
                    field.setValueFrom(dataValue, t3);
                    nextUnreadFieldIndex = i2 + 1;
                    continue block9;
                }
                catch (Asn1DecodingException e5) {
                    throw new Asn1DecodingException("Failed to parse " + containerClass.getName() + "." + field.getField().getName(), e5);
                }
            }
        }
        return t3;
    }

    private static <T> List<T> parseSetOf(BerDataValue container, Class<T> elementClass) throws Asn1DecodingException {
        ArrayList<ByteBuffer> result = new ArrayList<ByteBuffer>();
        BerDataValueReader elementsReader = container.contentsReader();
        while (true) {
            BerDataValue dataValue;
            try {
                dataValue = elementsReader.readDataValue();
            }
            catch (BerDataValueFormatException e2) {
                throw new Asn1DecodingException("Malformed data value", e2);
            }
            if (dataValue == null) break;
            Object element = ByteBuffer.class.equals(elementClass) ? dataValue.getEncodedContents() : (Asn1OpaqueObject.class.equals(elementClass) ? new Asn1OpaqueObject(dataValue.getEncoded()) : Asn1BerParser.parse(dataValue, elementClass));
            result.add((ByteBuffer)element);
        }
        return result;
    }

    private static Asn1Type getContainerAsn1Type(Class<?> containerClass) throws Asn1DecodingException {
        Asn1Class containerAnnotation = containerClass.getDeclaredAnnotation(Asn1Class.class);
        if (containerAnnotation == null) {
            throw new Asn1DecodingException(containerClass.getName() + " is not annotated with " + Asn1Class.class.getName());
        }
        switch (containerAnnotation.type()) {
            case CHOICE: 
            case SEQUENCE: {
                return containerAnnotation.type();
            }
        }
        throw new Asn1DecodingException("Unsupported ASN.1 container annotation type: " + (Object)((Object)containerAnnotation.type()));
    }

    private static Class<?> getElementType(Field field) throws Asn1DecodingException, ClassNotFoundException {
        String type = field.getGenericType().getTypeName();
        int delimiterIndex = type.indexOf(60);
        if (delimiterIndex == -1) {
            throw new Asn1DecodingException("Not a container type: " + field.getGenericType());
        }
        int startIndex = delimiterIndex + 1;
        int endIndex = type.indexOf(62, startIndex);
        if (endIndex == -1) {
            throw new Asn1DecodingException("Not a container type: " + field.getGenericType());
        }
        String elementClassName = type.substring(startIndex, endIndex);
        return Class.forName(elementClassName);
    }

    private static String oidToString(ByteBuffer encodedOid) throws Asn1DecodingException {
        if (!encodedOid.hasRemaining()) {
            throw new Asn1DecodingException("Empty OBJECT IDENTIFIER");
        }
        long firstComponent = Asn1BerParser.decodeBase128UnsignedLong(encodedOid);
        int firstNode = (int)Math.min(firstComponent / 40L, 2L);
        long secondNode = firstComponent - (long)(firstNode * 40);
        StringBuilder result = new StringBuilder();
        result.append(Long.toString(firstNode)).append('.').append(Long.toString(secondNode));
        while (encodedOid.hasRemaining()) {
            long node = Asn1BerParser.decodeBase128UnsignedLong(encodedOid);
            result.append('.').append(Long.toString(node));
        }
        return result.toString();
    }

    private static long decodeBase128UnsignedLong(ByteBuffer encoded) throws Asn1DecodingException {
        if (!encoded.hasRemaining()) {
            return 0L;
        }
        long result = 0L;
        while (encoded.hasRemaining()) {
            if (result > 0xFFFFFFFFFFFFFFL) {
                throw new Asn1DecodingException("Base-128 number too large");
            }
            int b2 = encoded.get() & 0xFF;
            result <<= 7;
            result |= (long)(b2 & 0x7F);
            if ((b2 & 0x80) != 0) continue;
            return result;
        }
        throw new Asn1DecodingException("Truncated base-128 encoded input: missing terminating byte, with highest bit not set");
    }

    private static BigInteger integerToBigInteger(ByteBuffer encoded) {
        if (!encoded.hasRemaining()) {
            return BigInteger.ZERO;
        }
        return new BigInteger(ByteBufferUtils.toByteArray(encoded));
    }

    private static int integerToInt(ByteBuffer encoded) throws Asn1DecodingException {
        BigInteger value = Asn1BerParser.integerToBigInteger(encoded);
        try {
            return value.intValueExact();
        }
        catch (ArithmeticException e2) {
            throw new Asn1DecodingException(String.format("INTEGER cannot be represented as int: %1$d (0x%1$x)", value), e2);
        }
    }

    private static long integerToLong(ByteBuffer encoded) throws Asn1DecodingException {
        BigInteger value = Asn1BerParser.integerToBigInteger(encoded);
        try {
            return value.longValueExact();
        }
        catch (ArithmeticException e2) {
            throw new Asn1DecodingException(String.format("INTEGER cannot be represented as long: %1$d (0x%1$x)", value), e2);
        }
    }

    private static List<AnnotatedField> getAnnotatedFields(Class<?> containerClass) throws Asn1DecodingException {
        Field[] declaredFields = containerClass.getDeclaredFields();
        ArrayList<AnnotatedField> result = new ArrayList<AnnotatedField>(declaredFields.length);
        for (Field field : declaredFields) {
            AnnotatedField annotatedField;
            Asn1Field annotation = field.getDeclaredAnnotation(Asn1Field.class);
            if (annotation == null) continue;
            if (Modifier.isStatic(field.getModifiers())) {
                throw new Asn1DecodingException(Asn1Field.class.getName() + " used on a static field: " + containerClass.getName() + "." + field.getName());
            }
            try {
                annotatedField = new AnnotatedField(field, annotation);
            }
            catch (Asn1DecodingException e2) {
                throw new Asn1DecodingException("Invalid ASN.1 annotation on " + containerClass.getName() + "." + field.getName(), e2);
            }
            result.add(annotatedField);
        }
        return result;
    }

    private static final class BerToJavaConverter {
        private static final byte[] EMPTY_BYTE_ARRAY = new byte[0];

        private BerToJavaConverter() {
        }

        public static void setFieldValue(Object obj, Field field, Asn1Type type, BerDataValue dataValue) throws Asn1DecodingException {
            try {
                switch (type) {
                    case SET_OF: 
                    case SEQUENCE_OF: {
                        if (Asn1OpaqueObject.class.equals(field.getType())) {
                            field.set(obj, BerToJavaConverter.convert(type, dataValue, field.getType()));
                        } else {
                            field.set(obj, Asn1BerParser.parseSetOf(dataValue, Asn1BerParser.getElementType(field)));
                        }
                        return;
                    }
                }
                field.set(obj, BerToJavaConverter.convert(type, dataValue, field.getType()));
            }
            catch (ReflectiveOperationException e2) {
                throw new Asn1DecodingException("Failed to set value of " + obj.getClass().getName() + "." + field.getName(), e2);
            }
        }

        public static <T> T convert(Asn1Type sourceType, BerDataValue dataValue, Class<T> targetType) throws Asn1DecodingException {
            if (ByteBuffer.class.equals(targetType)) {
                return (T)dataValue.getEncodedContents();
            }
            if (byte[].class.equals(targetType)) {
                ByteBuffer resultBuf = dataValue.getEncodedContents();
                if (!resultBuf.hasRemaining()) {
                    return (T)EMPTY_BYTE_ARRAY;
                }
                byte[] result = new byte[resultBuf.remaining()];
                resultBuf.get(result);
                return (T)result;
            }
            if (Asn1OpaqueObject.class.equals(targetType)) {
                return (T)new Asn1OpaqueObject(dataValue.getEncoded());
            }
            ByteBuffer encodedContents = dataValue.getEncodedContents();
            switch (sourceType) {
                case INTEGER: {
                    if (Integer.TYPE.equals(targetType) || Integer.class.equals(targetType)) {
                        return (T)Integer.valueOf(Asn1BerParser.integerToInt(encodedContents));
                    }
                    if (Long.TYPE.equals(targetType) || Long.class.equals(targetType)) {
                        return (T)Long.valueOf(Asn1BerParser.integerToLong(encodedContents));
                    }
                    if (!BigInteger.class.equals(targetType)) break;
                    return (T)Asn1BerParser.integerToBigInteger(encodedContents);
                }
                case OBJECT_IDENTIFIER: {
                    if (!String.class.equals(targetType)) break;
                    return (T)Asn1BerParser.oidToString(encodedContents);
                }
                case SEQUENCE: {
                    Asn1Class containerAnnotation = targetType.getDeclaredAnnotation(Asn1Class.class);
                    if (containerAnnotation == null || containerAnnotation.type() != Asn1Type.SEQUENCE) break;
                    return (T)Asn1BerParser.parseSequence(dataValue, targetType);
                }
                case CHOICE: {
                    Asn1Class containerAnnotation = targetType.getDeclaredAnnotation(Asn1Class.class);
                    if (containerAnnotation == null || containerAnnotation.type() != Asn1Type.CHOICE) break;
                    return (T)Asn1BerParser.parseChoice(dataValue, targetType);
                }
            }
            throw new Asn1DecodingException("Unsupported conversion: ASN.1 " + (Object)((Object)sourceType) + " to " + targetType.getName());
        }
    }

    private static class Asn1UnexpectedTagException
    extends Asn1DecodingException {
        private static final long serialVersionUID = 1L;

        public Asn1UnexpectedTagException(String message) {
            super(message);
        }
    }

    private static final class AnnotatedField {
        private final Field mField;
        private final Asn1Field mAnnotation;
        private final Asn1Type mDataType;
        private final Asn1TagClass mTagClass;
        private final int mBerTagClass;
        private final int mBerTagNumber;
        private final Asn1Tagging mTagging;
        private final boolean mOptional;

        public AnnotatedField(Field field, Asn1Field annotation) throws Asn1DecodingException {
            this.mField = field;
            this.mAnnotation = annotation;
            this.mDataType = annotation.type();
            Asn1TagClass tagClass = annotation.cls();
            if (tagClass == Asn1TagClass.AUTOMATIC) {
                tagClass = annotation.tagNumber() != -1 ? Asn1TagClass.CONTEXT_SPECIFIC : Asn1TagClass.UNIVERSAL;
            }
            this.mTagClass = tagClass;
            this.mBerTagClass = BerEncoding.getTagClass(this.mTagClass);
            int tagNumber = annotation.tagNumber() != -1 ? annotation.tagNumber() : (this.mDataType == Asn1Type.CHOICE || this.mDataType == Asn1Type.ANY ? -1 : BerEncoding.getTagNumber(this.mDataType));
            this.mBerTagNumber = tagNumber;
            this.mTagging = annotation.tagging();
            if ((this.mTagging == Asn1Tagging.EXPLICIT || this.mTagging == Asn1Tagging.IMPLICIT) && annotation.tagNumber() == -1) {
                throw new Asn1DecodingException("Tag number must be specified when tagging mode is " + (Object)((Object)this.mTagging));
            }
            this.mOptional = annotation.optional();
        }

        public Field getField() {
            return this.mField;
        }

        public Asn1Field getAnnotation() {
            return this.mAnnotation;
        }

        public boolean isOptional() {
            return this.mOptional;
        }

        public int getBerTagClass() {
            return this.mBerTagClass;
        }

        public int getBerTagNumber() {
            return this.mBerTagNumber;
        }

        public void setValueFrom(BerDataValue dataValue, Object obj) throws Asn1DecodingException {
            int readTagClass = dataValue.getTagClass();
            if (this.mBerTagNumber != -1) {
                int readTagNumber = dataValue.getTagNumber();
                if (readTagClass != this.mBerTagClass || readTagNumber != this.mBerTagNumber) {
                    throw new Asn1UnexpectedTagException("Tag mismatch. Expected: " + BerEncoding.tagClassAndNumberToString(this.mBerTagClass, this.mBerTagNumber) + ", but found " + BerEncoding.tagClassAndNumberToString(readTagClass, readTagNumber));
                }
            } else if (readTagClass != this.mBerTagClass) {
                throw new Asn1UnexpectedTagException("Tag mismatch. Expected class: " + BerEncoding.tagClassToString(this.mBerTagClass) + ", but found " + BerEncoding.tagClassToString(readTagClass));
            }
            if (this.mTagging == Asn1Tagging.EXPLICIT) {
                try {
                    dataValue = dataValue.contentsReader().readDataValue();
                }
                catch (BerDataValueFormatException e2) {
                    throw new Asn1DecodingException("Failed to read contents of EXPLICIT data value", e2);
                }
            }
            BerToJavaConverter.setFieldValue(obj, this.mField, this.mDataType, dataValue);
        }
    }
}

