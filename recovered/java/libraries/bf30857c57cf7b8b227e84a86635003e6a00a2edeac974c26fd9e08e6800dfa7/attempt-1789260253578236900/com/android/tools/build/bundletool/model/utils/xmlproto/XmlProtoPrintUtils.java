/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.model.utils.xmlproto;

import com.android.aapt.Resources;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Splitter;
import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Ordering;
import java.util.Comparator;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class XmlProtoPrintUtils {
    public static String getValueAsString(Resources.Value value) {
        switch (value.getValueCase()) {
            case ITEM: {
                return XmlProtoPrintUtils.getItemValueAsQuotedString(value.getItem());
            }
            case COMPOUND_VALUE: {
                return XmlProtoPrintUtils.getCompoundValueAsString(value.getCompoundValue());
            }
        }
        return "";
    }

    public static String getCompoundValueAsString(Resources.CompoundValue compoundValue) {
        switch (compoundValue.getValueCase()) {
            case ATTR: {
                return compoundValue.getAttr().getSymbolList().stream().map(symbol -> symbol.getName().getName() + "=" + symbol.getValue()).collect(Collectors.joining(", ", "{", "}"));
            }
            case ARRAY: {
                return compoundValue.getArray().getElementList().stream().map(element -> XmlProtoPrintUtils.getItemValueAsQuotedString(element.getItem())).collect(Collectors.joining(", ", "[", "]"));
            }
            case PLURAL: {
                return compoundValue.getPlural().getEntryList().stream().map(entry -> entry.getArity() + "=" + XmlProtoPrintUtils.getItemValueAsQuotedString(entry.getItem())).collect(Collectors.joining(", ", "{", "}"));
            }
            case STYLE: {
                return compoundValue.getStyle().getEntryList().stream().map(entry -> XmlProtoPrintUtils.getItemValueAsQuotedString(entry.getItem())).collect(Collectors.joining(", ", "[", "]"));
            }
            case STYLEABLE: {
                return compoundValue.getStyleable().getEntryList().stream().map(entry -> XmlProtoPrintUtils.getRefAsString(entry.getAttr())).collect(Collectors.joining(", ", "[", "]"));
            }
        }
        return "";
    }

    @VisibleForTesting
    static String getRefAsString(Resources.Reference ref) {
        if (!ref.getName().isEmpty()) {
            return (ref.getType().equals(Resources.Reference.Type.REFERENCE) ? "@" : "?") + ref.getName();
        }
        return String.format("0x%08x", ref.getId());
    }

    public static String getValueTypeAsString(Resources.Value value) {
        switch (value.getValueCase()) {
            case ITEM: {
                Resources.Item item = value.getItem();
                switch (item.getValueCase()) {
                    case PRIM: {
                        return item.getPrim().getOneofValueCase().toString().replace("_VALUE", "");
                    }
                }
                return item.getValueCase().toString();
            }
            case COMPOUND_VALUE: {
                return value.getCompoundValue().getValueCase().toString();
            }
        }
        return "";
    }

    public static String getItemValueAsString(Resources.Item item) {
        switch (item.getValueCase()) {
            case PRIM: {
                return XmlProtoPrintUtils.getPrimitiveValueAsString(item.getPrim());
            }
            case FILE: {
                return item.getFile().getPath();
            }
            case ID: {
                return "<ID>";
            }
            case RAW_STR: {
                return item.getRawStr().getValue();
            }
            case REF: {
                return XmlProtoPrintUtils.getRefAsString(item.getRef());
            }
            case STR: {
                return item.getStr().getValue();
            }
            case STYLED_STR: {
                return XmlProtoPrintUtils.processStyledString(item.getStyledStr());
            }
        }
        return "";
    }

    public static String getItemValueAsQuotedString(Resources.Item item) {
        switch (item.getValueCase()) {
            case RAW_STR: 
            case STR: 
            case STYLED_STR: {
                return "\"" + XmlProtoPrintUtils.escapeString(XmlProtoPrintUtils.getItemValueAsString(item)) + "\"";
            }
        }
        return XmlProtoPrintUtils.getItemValueAsString(item);
    }

    public static String getPrimitiveValueAsString(Resources.Primitive primitive) {
        switch (primitive.getOneofValueCase()) {
            case NULL_VALUE: 
            case EMPTY_VALUE: {
                return "";
            }
            case FLOAT_VALUE: {
                return Float.toString(primitive.getFloatValue());
            }
            case DIMENSION_VALUE: {
                return String.valueOf(primitive.getDimensionValue());
            }
            case FRACTION_VALUE: {
                return String.valueOf(primitive.getFractionValue());
            }
            case INT_DECIMAL_VALUE: {
                return Integer.toString(primitive.getIntDecimalValue());
            }
            case INT_HEXADECIMAL_VALUE: {
                return String.format("0x%08x", primitive.getIntHexadecimalValue());
            }
            case BOOLEAN_VALUE: {
                return Boolean.toString(primitive.getBooleanValue());
            }
            case COLOR_ARGB8_VALUE: {
                return String.format("#%08x", primitive.getColorArgb8Value());
            }
            case COLOR_RGB8_VALUE: {
                return String.format("#%06x", primitive.getColorRgb8Value() & 0xFFFFFF);
            }
            case COLOR_ARGB4_VALUE: {
                return String.format("#%08x", primitive.getColorArgb4Value());
            }
            case COLOR_RGB4_VALUE: {
                return String.format("#%06x", primitive.getColorRgb4Value() & 0xFFFFFF);
            }
        }
        return "";
    }

    @VisibleForTesting
    static String processStyledString(Resources.StyledString styledString) {
        StringBuilder string = new StringBuilder(styledString.getValue());
        styledString.getSpanList().stream().flatMap(span -> Stream.of(new Tag(span.getTag(), Tag.Type.OPEN, span.getFirstChar(), span.getLastChar() + 1), new Tag(span.getTag(), Tag.Type.CLOSE, span.getLastChar() + 1, span.getFirstChar()))).sorted(Comparator.reverseOrder()).forEach(tag -> string.insert(((Tag)tag).position, tag.toString()));
        return string.toString();
    }

    private static String escapeString(String s3) {
        return s3.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "\\r").replace("\u000b", "\\u000B").replace("\f", "\\u000C").replace("\u0085", "\\u0085").replace("\u2028", "\\u2028").replace("\u2029", "\\u2029");
    }

    private XmlProtoPrintUtils() {
    }

    private static class Tag
    implements Comparable<Tag> {
        private static final Splitter.MapSplitter ATTRIBUTES_SPLITTER = Splitter.on(';').withKeyValueSeparator(Splitter.on('=').limit(2));
        private final String tag;
        private final Type type;
        private final int position;
        private final int matchingTagPosition;

        Tag(String tag, Type type, int position, int matchingTagPosition) {
            this.tag = tag;
            this.type = type;
            this.position = position;
            this.matchingTagPosition = matchingTagPosition;
        }

        @Override
        public int compareTo(Tag o3) {
            return ComparisonChain.start().compare(this.position, o3.position).compare(this.type, o3.type, Ordering.explicit(Type.CLOSE, Type.OPEN)).compare(this.matchingTagPosition, o3.matchingTagPosition, Comparator.reverseOrder()).compare(this.tag, o3.tag, this.type.equals((Object)Type.OPEN) ? Comparator.naturalOrder() : Comparator.reverseOrder()).result();
        }

        public String toString() {
            int separatorIdx = this.tag.indexOf(59);
            String actualTag = separatorIdx == -1 ? this.tag : this.tag.substring(0, separatorIdx);
            switch (this.type) {
                case OPEN: {
                    if (separatorIdx != -1) {
                        StringJoiner attributes = new StringJoiner(" ");
                        ATTRIBUTES_SPLITTER.split(this.tag.substring(separatorIdx + 1)).forEach((key, value) -> attributes.add(String.format("%s=\"%s\"", key, value)));
                        return String.format("<%s %s>", actualTag, attributes);
                    }
                    return String.format("<%s>", actualTag);
                }
                case CLOSE: {
                    return String.format("</%s>", actualTag);
                }
            }
            throw new IllegalStateException();
        }

        private static enum Type {
            OPEN,
            CLOSE;

        }
    }
}

