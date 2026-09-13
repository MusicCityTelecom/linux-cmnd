/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.asn1.ASN1Encodable
 *  org.bouncycastle.asn1.ASN1Integer
 */
package org.bouncycastle.oer;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Integer;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class OERDefinition {
    private static final BigInteger[] uIntMax = new BigInteger[]{new BigInteger("256"), new BigInteger("65536"), new BigInteger("4294967296"), new BigInteger("18446744073709551616")};
    private static final BigInteger[][] sIntRange = new BigInteger[][]{{new BigInteger("-128"), new BigInteger("127")}, {new BigInteger("-32768"), new BigInteger("32767")}, {new BigInteger("-2147483648"), new BigInteger("2147483647")}, {new BigInteger("-9223372036854775808"), new BigInteger("9223372036854775807")}};

    public static Builder integer() {
        return new Builder(BaseType.INT);
    }

    public static Builder integer(long l) {
        return new Builder(BaseType.INT).defaultValue((ASN1Encodable)new ASN1Integer(l));
    }

    public static Builder bitString(long l) {
        return new Builder(BaseType.BIT_STRING).fixedSize(l);
    }

    public static Builder integer(BigInteger bigInteger, BigInteger bigInteger2) {
        return new Builder(BaseType.INT).range(bigInteger, bigInteger2);
    }

    public static Builder integer(long l, long l2) {
        return new Builder(BaseType.INT).range(BigInteger.valueOf(l), BigInteger.valueOf(l2));
    }

    public static Builder integer(long l, long l2, ASN1Encodable aSN1Encodable) {
        return new Builder(BaseType.INT).range(l, l2, aSN1Encodable);
    }

    public static Builder nullValue() {
        return new Builder(BaseType.NULL);
    }

    public static Builder seq() {
        return new Builder(BaseType.SEQ);
    }

    public static Builder seq(Object ... objectArray) {
        return new Builder(BaseType.SEQ).items(objectArray);
    }

    public static Builder enumItem(String string) {
        return new Builder(BaseType.ENUM_ITEM).label(string);
    }

    public static Builder enumItem(String string, BigInteger bigInteger) {
        return new Builder(BaseType.ENUM_ITEM).enumValue(bigInteger).label(string);
    }

    public static Builder enumeration(Object ... objectArray) {
        return new Builder(BaseType.ENUM).items(objectArray);
    }

    public static Builder choice(Object ... objectArray) {
        return new Builder(BaseType.CHOICE).items(objectArray);
    }

    public static Builder placeholder() {
        return new Builder(null);
    }

    public static Builder seqof(Object ... objectArray) {
        return new Builder(BaseType.SEQ_OF).items(objectArray);
    }

    public static Builder octets() {
        return new Builder(BaseType.OCTET_STRING).unbounded();
    }

    public static Builder octets(int n) {
        return new Builder(BaseType.OCTET_STRING).fixedSize(n);
    }

    public static Builder octets(int n, int n2) {
        return new Builder(BaseType.OCTET_STRING).range(BigInteger.valueOf(n), BigInteger.valueOf(n2));
    }

    public static Builder utf8String() {
        return new Builder(BaseType.UTF8_STRING);
    }

    public static Builder utf8String(int n) {
        return new Builder(BaseType.UTF8_STRING).rangeToMAXFrom(n);
    }

    public static Builder utf8String(int n, int n2) {
        return new Builder(BaseType.UTF8_STRING).range(BigInteger.valueOf(n), BigInteger.valueOf(n2));
    }

    public static Builder opaque() {
        return new Builder(BaseType.OCTET_STRING).unbounded();
    }

    public static List<Object> optional(Object ... objectArray) {
        return new OptionalList(Arrays.asList(objectArray));
    }

    public static Builder extension() {
        return new Builder(BaseType.EXTENSION).label("extension");
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    public static enum BaseType {
        SEQ,
        SEQ_OF,
        CHOICE,
        ENUM,
        INT,
        OCTET_STRING,
        UTF8_STRING,
        BIT_STRING,
        NULL,
        EXTENSION,
        ENUM_ITEM,
        BOOLEAN,
        IS0646String,
        PrintableString,
        NumericString,
        BMPString,
        UniversalString,
        IA5String,
        VisibleString;

    }

    public static class Builder {
        protected final BaseType baseType;
        protected ArrayList<Builder> children = new ArrayList();
        protected boolean explicit = false;
        protected String label;
        protected BigInteger upperBound;
        protected BigInteger lowerBound;
        protected BigInteger enumValue;
        protected ASN1Encodable defaultValue;
        protected Builder placeholderValue;

        public Builder copy() {
            Builder builder = new Builder(this.baseType);
            for (Builder builder2 : this.children) {
                builder.children.add(builder2.copy());
            }
            builder.explicit = this.explicit;
            builder.label = this.label;
            builder.upperBound = this.upperBound;
            builder.lowerBound = this.lowerBound;
            builder.defaultValue = this.defaultValue;
            builder.enumValue = this.enumValue;
            return builder;
        }

        public Builder unbounded() {
            Builder builder = this.copy();
            builder.lowerBound = null;
            builder.upperBound = null;
            return builder;
        }

        public Builder labelPrefix(String string) {
            Builder builder = this.copy();
            builder.label = string + " " + this.label;
            return builder;
        }

        public Builder(BaseType baseType) {
            this.baseType = baseType;
        }

        public Builder explicit(boolean bl) {
            Builder builder = this.copy();
            builder.explicit = bl;
            return builder;
        }

        public Builder defaultValue(ASN1Encodable aSN1Encodable) {
            Builder builder = this.copy();
            builder.defaultValue = aSN1Encodable;
            return builder;
        }

        private Builder wrap(boolean bl, Object object) {
            if (object instanceof Builder) {
                return ((Builder)object).explicit(bl);
            }
            if (object instanceof BaseType) {
                return new Builder((BaseType)((Object)object)).explicit(bl);
            }
            throw new IllegalStateException("Unable to wrap item in builder");
        }

        public Builder items(Object ... objectArray) {
            Builder builder = this.copy();
            for (int i = 0; i != objectArray.length; ++i) {
                Object object = objectArray[i];
                if (object instanceof OptionalList) {
                    Iterator iterator = ((List)object).iterator();
                    while (iterator.hasNext()) {
                        builder.children.add(this.wrap(false, iterator.next()));
                    }
                    continue;
                }
                if (object.getClass().isArray()) {
                    this.items((Object[])object);
                    continue;
                }
                builder.children.add(this.wrap(true, object));
            }
            return builder;
        }

        public Builder label(String string) {
            Builder builder = this.copy();
            if (string != null) {
                builder.label = string;
            }
            builder.explicit = this.explicit;
            return builder;
        }

        public Element build() {
            ArrayList<Element> arrayList = new ArrayList<Element>();
            boolean bl = false;
            if (this.baseType == BaseType.ENUM) {
                int n = 0;
                HashSet<BigInteger> object = new HashSet<BigInteger>();
                for (int i = 0; i < this.children.size(); ++i) {
                    Builder builder = this.children.get(i);
                    if (builder.enumValue == null) {
                        builder.enumValue = BigInteger.valueOf(n);
                        ++n;
                    }
                    if (object.contains(builder.enumValue)) {
                        throw new IllegalStateException("duplicate enum value at index " + i);
                    }
                    object.add(builder.enumValue);
                }
            }
            for (Builder builder : this.children) {
                if (!bl && builder.baseType == BaseType.EXTENSION) {
                    bl = true;
                    if (builder.children.isEmpty() && this.baseType != BaseType.CHOICE) continue;
                }
                arrayList.add(builder.build());
            }
            return new Element(this.baseType, arrayList, this.defaultValue == null && this.explicit, this.label, this.lowerBound, this.upperBound, bl, this.enumValue, this.defaultValue);
        }

        public Builder range(BigInteger bigInteger, BigInteger bigInteger2) {
            Builder builder = this.copy();
            builder.lowerBound = bigInteger;
            builder.upperBound = bigInteger2;
            return builder;
        }

        public Builder rangeToMAXFrom(long l) {
            Builder builder = this.copy();
            builder.lowerBound = BigInteger.valueOf(l);
            builder.upperBound = null;
            return builder;
        }

        public Builder rangeZeroTo(long l) {
            Builder builder = this.copy();
            builder.upperBound = BigInteger.valueOf(l);
            builder.lowerBound = BigInteger.ZERO;
            return builder;
        }

        public Builder fixedSize(long l) {
            Builder builder = this.copy();
            builder.upperBound = BigInteger.valueOf(l);
            builder.lowerBound = BigInteger.valueOf(l);
            return builder;
        }

        public Builder range(long l, long l2, ASN1Encodable aSN1Encodable) {
            Builder builder = this.copy();
            builder.lowerBound = BigInteger.valueOf(l);
            builder.upperBound = BigInteger.valueOf(l2);
            builder.defaultValue = aSN1Encodable;
            return builder;
        }

        public Builder enumValue(BigInteger bigInteger) {
            Builder builder = this.copy();
            this.enumValue = bigInteger;
            return builder;
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    public static class Element {
        public final BaseType baseType;
        public final List<Element> children;
        public final boolean explicit;
        public final String label;
        private List<Element> optionalChildrenInOrder;
        public final BigInteger lowerBound;
        public final BigInteger upperBound;
        public final boolean extensionsInDefinition;
        public final BigInteger enumValue;
        public final ASN1Encodable defaultValue;

        public Element(BaseType baseType, List<Element> list, boolean bl, String string, BigInteger bigInteger, BigInteger bigInteger2, boolean bl2, BigInteger bigInteger3, ASN1Encodable aSN1Encodable) {
            this.baseType = baseType;
            this.children = list;
            this.explicit = bl;
            this.label = string;
            this.lowerBound = bigInteger;
            this.upperBound = bigInteger2;
            this.extensionsInDefinition = bl2;
            this.enumValue = bigInteger3;
            this.defaultValue = aSN1Encodable;
        }

        public String rangeExpression() {
            return "(" + (this.lowerBound != null ? this.lowerBound.toString() : "MIN") + " ... " + (this.upperBound != null ? this.upperBound.toString() : "MAX") + ")";
        }

        public String appendLabel(String string) {
            return "[" + (this.label == null ? "" : this.label) + (this.explicit ? " (E)" : "") + "] " + string;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        public List<Element> optionalOrDefaultChildrenInOrder() {
            Element element = this;
            synchronized (element) {
                if (this.optionalChildrenInOrder == null) {
                    ArrayList<Element> arrayList = new ArrayList<Element>();
                    for (Element element2 : this.children) {
                        if (element2.explicit && element2.getDefaultValue() == null) continue;
                        arrayList.add(element2);
                    }
                    this.optionalChildrenInOrder = Collections.unmodifiableList(arrayList);
                }
                return this.optionalChildrenInOrder;
            }
        }

        public boolean isUnbounded() {
            return this.upperBound == null && this.lowerBound == null;
        }

        public boolean isLowerRangeZero() {
            return BigInteger.ZERO.equals(this.lowerBound);
        }

        public boolean isUnsignedWithRange() {
            return this.isLowerRangeZero() && this.upperBound != null && BigInteger.ZERO.compareTo(this.upperBound) < 0;
        }

        public boolean canBeNegative() {
            return this.lowerBound != null && BigInteger.ZERO.compareTo(this.lowerBound) > 0;
        }

        public int intBytesForRange() {
            block6: {
                if (this.lowerBound == null || this.upperBound == null) break block6;
                if (BigInteger.ZERO.equals(this.lowerBound)) {
                    int n = 0;
                    int n2 = 1;
                    while (n < uIntMax.length) {
                        if (this.upperBound.compareTo(uIntMax[n]) < 0) {
                            return n2;
                        }
                        ++n;
                        n2 *= 2;
                    }
                } else {
                    int n = 0;
                    int n3 = 1;
                    while (n < sIntRange.length) {
                        if (this.lowerBound.compareTo(sIntRange[n][0]) >= 0 && this.upperBound.compareTo(sIntRange[n][1]) < 0) {
                            return -n3;
                        }
                        ++n;
                        n3 *= 2;
                    }
                }
            }
            return 0;
        }

        public boolean hasPopulatedExtension() {
            for (Element element : this.children) {
                if (element.baseType != BaseType.EXTENSION) continue;
                return true;
            }
            return false;
        }

        public boolean hasDefaultChildren() {
            for (Element element : this.children) {
                if (element.defaultValue == null) continue;
                return true;
            }
            return false;
        }

        public ASN1Encodable getDefaultValue() {
            return this.defaultValue;
        }

        public Element getFirstChid() {
            return this.children.get(0);
        }

        public boolean isFixedLength() {
            return this.lowerBound != null && this.lowerBound.equals(this.upperBound);
        }
    }

    public static class MutableBuilder
    extends Builder {
        private boolean frozen = false;

        public MutableBuilder(BaseType baseType) {
            super(baseType);
        }

        public void addItemsAndFreeze(Builder ... builderArray) {
            if (this.frozen) {
                throw new IllegalStateException("build cannot be modified and must be copied only");
            }
            for (int i = 0; i != builderArray.length; ++i) {
                Builder builder = builderArray[i];
                this.children.add(builder);
            }
            this.frozen = true;
        }
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    private static class OptionalList
    extends ArrayList<Object> {
        public OptionalList(List<Object> list) {
            this.addAll(list);
        }
    }
}

