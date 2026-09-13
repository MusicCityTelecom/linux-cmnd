/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.util.Assert
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot.context.properties.source;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import org.springframework.boot.context.properties.source.InvalidConfigurationPropertyNameException;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

public final class ConfigurationPropertyName
implements Comparable<ConfigurationPropertyName> {
    private static final String EMPTY_STRING = "";
    public static final ConfigurationPropertyName EMPTY = new ConfigurationPropertyName(Elements.EMPTY);
    private Elements elements;
    private final CharSequence[] uniformElements;
    private String string;
    private int hashCode;

    private ConfigurationPropertyName(Elements elements) {
        this.elements = elements;
        this.uniformElements = new CharSequence[elements.getSize()];
    }

    public boolean isEmpty() {
        return this.elements.getSize() == 0;
    }

    public boolean isLastElementIndexed() {
        int size = this.getNumberOfElements();
        return size > 0 && this.isIndexed(size - 1);
    }

    public boolean hasIndexedElement() {
        for (int i = 0; i < this.getNumberOfElements(); ++i) {
            if (!this.isIndexed(i)) continue;
            return true;
        }
        return false;
    }

    boolean isIndexed(int elementIndex) {
        return this.elements.getType(elementIndex).isIndexed();
    }

    public boolean isNumericIndex(int elementIndex) {
        return this.elements.getType(elementIndex) == ElementType.NUMERICALLY_INDEXED;
    }

    public String getLastElement(Form form) {
        int size = this.getNumberOfElements();
        return size != 0 ? this.getElement(size - 1, form) : EMPTY_STRING;
    }

    public String getElement(int elementIndex, Form form) {
        CharSequence element = this.elements.get(elementIndex);
        ElementType type = this.elements.getType(elementIndex);
        if (type.isIndexed()) {
            return element.toString();
        }
        if (form == Form.ORIGINAL) {
            if (type != ElementType.NON_UNIFORM) {
                return element.toString();
            }
            return this.convertToOriginalForm(element).toString();
        }
        if (form == Form.DASHED) {
            if (type == ElementType.UNIFORM || type == ElementType.DASHED) {
                return element.toString();
            }
            return this.convertToDashedElement(element).toString();
        }
        CharSequence uniformElement = this.uniformElements[elementIndex];
        if (uniformElement == null) {
            uniformElement = type != ElementType.UNIFORM ? this.convertToUniformElement(element) : element;
            this.uniformElements[elementIndex] = uniformElement.toString();
        }
        return uniformElement.toString();
    }

    private CharSequence convertToOriginalForm(CharSequence element) {
        return this.convertElement(element, false, (ch, i) -> ch == '_' || ElementsParser.isValidChar(Character.toLowerCase(ch), i));
    }

    private CharSequence convertToDashedElement(CharSequence element) {
        return this.convertElement(element, true, ElementsParser::isValidChar);
    }

    private CharSequence convertToUniformElement(CharSequence element) {
        return this.convertElement(element, true, (ch, i) -> ElementsParser.isAlphaNumeric(ch));
    }

    private CharSequence convertElement(CharSequence element, boolean lowercase, ElementCharPredicate filter) {
        StringBuilder result = new StringBuilder(element.length());
        for (int i = 0; i < element.length(); ++i) {
            char ch;
            char c = ch = lowercase ? Character.toLowerCase(element.charAt(i)) : element.charAt(i);
            if (!filter.test(ch, i)) continue;
            result.append(ch);
        }
        return result;
    }

    public int getNumberOfElements() {
        return this.elements.getSize();
    }

    public ConfigurationPropertyName append(String suffix) {
        if (!StringUtils.hasLength((String)suffix)) {
            return this;
        }
        Elements additionalElements = ConfigurationPropertyName.probablySingleElementOf(suffix);
        return new ConfigurationPropertyName(this.elements.append(additionalElements));
    }

    public ConfigurationPropertyName append(ConfigurationPropertyName suffix) {
        if (suffix == null) {
            return this;
        }
        return new ConfigurationPropertyName(this.elements.append(suffix.elements));
    }

    public ConfigurationPropertyName getParent() {
        int numberOfElements = this.getNumberOfElements();
        return numberOfElements <= 1 ? EMPTY : this.chop(numberOfElements - 1);
    }

    public ConfigurationPropertyName chop(int size) {
        if (size >= this.getNumberOfElements()) {
            return this;
        }
        return new ConfigurationPropertyName(this.elements.chop(size));
    }

    public ConfigurationPropertyName subName(int offset) {
        if (offset == 0) {
            return this;
        }
        if (offset == this.getNumberOfElements()) {
            return EMPTY;
        }
        if (offset < 0 || offset > this.getNumberOfElements()) {
            throw new IndexOutOfBoundsException("Offset: " + offset + ", NumberOfElements: " + this.getNumberOfElements());
        }
        return new ConfigurationPropertyName(this.elements.subElements(offset));
    }

    public boolean isParentOf(ConfigurationPropertyName name) {
        Assert.notNull((Object)name, (String)"Name must not be null");
        if (this.getNumberOfElements() != name.getNumberOfElements() - 1) {
            return false;
        }
        return this.isAncestorOf(name);
    }

    public boolean isAncestorOf(ConfigurationPropertyName name) {
        Assert.notNull((Object)name, (String)"Name must not be null");
        if (this.getNumberOfElements() >= name.getNumberOfElements()) {
            return false;
        }
        return this.elementsEqual(name);
    }

    @Override
    public int compareTo(ConfigurationPropertyName other) {
        return this.compare(this, other);
    }

    private int compare(ConfigurationPropertyName n1, ConfigurationPropertyName n2) {
        int l1 = n1.getNumberOfElements();
        int l2 = n2.getNumberOfElements();
        int i1 = 0;
        int i2 = 0;
        while (i1 < l1 || i2 < l2) {
            try {
                ElementType type2;
                String e2;
                ElementType type1;
                String e1 = i1 < l1 ? n1.getElement(i1++, Form.UNIFORM) : null;
                int result = this.compare(e1, type1 = i1 < l1 ? n1.elements.getType(i1) : null, e2 = i2 < l2 ? n2.getElement(i2++, Form.UNIFORM) : null, type2 = i2 < l2 ? n2.elements.getType(i2) : null);
                if (result == 0) continue;
                return result;
            }
            catch (ArrayIndexOutOfBoundsException ex) {
                throw new RuntimeException(ex);
            }
        }
        return 0;
    }

    private int compare(String e1, ElementType type1, String e2, ElementType type2) {
        if (e1 == null) {
            return -1;
        }
        if (e2 == null) {
            return 1;
        }
        int result = Boolean.compare(type2.isIndexed(), type1.isIndexed());
        if (result != 0) {
            return result;
        }
        if (type1 == ElementType.NUMERICALLY_INDEXED && type2 == ElementType.NUMERICALLY_INDEXED) {
            long v1 = Long.parseLong(e1);
            long v2 = Long.parseLong(e2);
            return Long.compare(v1, v2);
        }
        return e1.compareTo(e2);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        ConfigurationPropertyName other = (ConfigurationPropertyName)obj;
        if (this.getNumberOfElements() != other.getNumberOfElements()) {
            return false;
        }
        if (this.elements.canShortcutWithSource(ElementType.UNIFORM) && other.elements.canShortcutWithSource(ElementType.UNIFORM)) {
            return this.toString().equals(other.toString());
        }
        return this.elementsEqual(other);
    }

    private boolean elementsEqual(ConfigurationPropertyName name) {
        for (int i = this.elements.getSize() - 1; i >= 0; --i) {
            if (!this.elementDiffers(this.elements, name.elements, i)) continue;
            return false;
        }
        return true;
    }

    private boolean elementDiffers(Elements e1, Elements e2, int i) {
        ElementType type1 = e1.getType(i);
        ElementType type2 = e2.getType(i);
        if (type1.allowsFastEqualityCheck() && type2.allowsFastEqualityCheck()) {
            return !this.fastElementEquals(e1, e2, i);
        }
        if (type1.allowsDashIgnoringEqualityCheck() && type2.allowsDashIgnoringEqualityCheck()) {
            return !this.dashIgnoringElementEquals(e1, e2, i);
        }
        return !this.defaultElementEquals(e1, e2, i);
    }

    private boolean fastElementEquals(Elements e1, Elements e2, int i) {
        int length2;
        int length1 = e1.getLength(i);
        if (length1 == (length2 = e2.getLength(i))) {
            int i1 = 0;
            while (length1-- != 0) {
                char ch2;
                char ch1 = e1.charAt(i, i1);
                if (ch1 != (ch2 = e2.charAt(i, i1))) {
                    return false;
                }
                ++i1;
            }
            return true;
        }
        return false;
    }

    private boolean dashIgnoringElementEquals(Elements e1, Elements e2, int i) {
        int l1 = e1.getLength(i);
        int l2 = e2.getLength(i);
        int i1 = 0;
        int i2 = 0;
        while (i1 < l1) {
            if (i2 >= l2) {
                return false;
            }
            char ch1 = e1.charAt(i, i1);
            char ch2 = e2.charAt(i, i2);
            if (ch1 == '-') {
                ++i1;
                continue;
            }
            if (ch2 == '-') {
                ++i2;
                continue;
            }
            if (ch1 != ch2) {
                return false;
            }
            ++i1;
            ++i2;
        }
        if (i2 < l2) {
            if (e2.getType(i).isIndexed()) {
                return false;
            }
            do {
                char ch2;
                if ((ch2 = e2.charAt(i, i2++)) == '-') continue;
                return false;
            } while (i2 < l2);
        }
        return true;
    }

    private boolean defaultElementEquals(Elements e1, Elements e2, int i) {
        int l1 = e1.getLength(i);
        int l2 = e2.getLength(i);
        boolean indexed1 = e1.getType(i).isIndexed();
        boolean indexed2 = e2.getType(i).isIndexed();
        int i1 = 0;
        int i2 = 0;
        while (i1 < l1) {
            char ch2;
            if (i2 >= l2) {
                return this.remainderIsNotAlphanumeric(e1, i, i1);
            }
            char ch1 = indexed1 ? e1.charAt(i, i1) : Character.toLowerCase(e1.charAt(i, i1));
            char c = ch2 = indexed2 ? e2.charAt(i, i2) : Character.toLowerCase(e2.charAt(i, i2));
            if (!indexed1 && !ElementsParser.isAlphaNumeric(ch1)) {
                ++i1;
                continue;
            }
            if (!indexed2 && !ElementsParser.isAlphaNumeric(ch2)) {
                ++i2;
                continue;
            }
            if (ch1 != ch2) {
                return false;
            }
            ++i1;
            ++i2;
        }
        if (i2 < l2) {
            return this.remainderIsNotAlphanumeric(e2, i, i2);
        }
        return true;
    }

    private boolean remainderIsNotAlphanumeric(Elements elements, int element, int index) {
        if (elements.getType(element).isIndexed()) {
            return false;
        }
        int length = elements.getLength(element);
        do {
            char c;
            if (!ElementsParser.isAlphaNumeric(c = Character.toLowerCase(elements.charAt(element, index++)))) continue;
            return false;
        } while (index < length);
        return true;
    }

    public int hashCode() {
        int hashCode = this.hashCode;
        Elements elements = this.elements;
        if (hashCode == 0 && elements.getSize() != 0) {
            for (int elementIndex = 0; elementIndex < elements.getSize(); ++elementIndex) {
                int elementHashCode = 0;
                boolean indexed = elements.getType(elementIndex).isIndexed();
                int length = elements.getLength(elementIndex);
                for (int i = 0; i < length; ++i) {
                    char ch = elements.charAt(elementIndex, i);
                    if (!indexed) {
                        ch = Character.toLowerCase(ch);
                    }
                    if (!ElementsParser.isAlphaNumeric(ch)) continue;
                    elementHashCode = 31 * elementHashCode + ch;
                }
                hashCode = 31 * hashCode + elementHashCode;
            }
            this.hashCode = hashCode;
        }
        return hashCode;
    }

    public String toString() {
        if (this.string == null) {
            this.string = this.buildToString();
        }
        return this.string;
    }

    private String buildToString() {
        if (this.elements.canShortcutWithSource(ElementType.UNIFORM, ElementType.DASHED)) {
            return this.elements.getSource().toString();
        }
        int elements = this.getNumberOfElements();
        StringBuilder result = new StringBuilder(elements * 8);
        for (int i = 0; i < elements; ++i) {
            boolean indexed = this.isIndexed(i);
            if (result.length() > 0 && !indexed) {
                result.append('.');
            }
            if (indexed) {
                result.append('[');
                result.append(this.getElement(i, Form.ORIGINAL));
                result.append(']');
                continue;
            }
            result.append(this.getElement(i, Form.DASHED));
        }
        return result.toString();
    }

    public static boolean isValid(CharSequence name) {
        return ConfigurationPropertyName.of(name, true) != null;
    }

    public static ConfigurationPropertyName of(CharSequence name) {
        return ConfigurationPropertyName.of(name, false);
    }

    public static ConfigurationPropertyName ofIfValid(CharSequence name) {
        return ConfigurationPropertyName.of(name, true);
    }

    static ConfigurationPropertyName of(CharSequence name, boolean returnNullIfInvalid) {
        Elements elements = ConfigurationPropertyName.elementsOf(name, returnNullIfInvalid);
        return elements != null ? new ConfigurationPropertyName(elements) : null;
    }

    private static Elements probablySingleElementOf(CharSequence name) {
        return ConfigurationPropertyName.elementsOf(name, false, 1);
    }

    private static Elements elementsOf(CharSequence name, boolean returnNullIfInvalid) {
        return ConfigurationPropertyName.elementsOf(name, returnNullIfInvalid, 6);
    }

    private static Elements elementsOf(CharSequence name, boolean returnNullIfInvalid, int parserCapacity) {
        if (name == null) {
            Assert.isTrue((boolean)returnNullIfInvalid, (String)"Name must not be null");
            return null;
        }
        if (name.length() == 0) {
            return Elements.EMPTY;
        }
        if (name.charAt(0) == '.' || name.charAt(name.length() - 1) == '.') {
            if (returnNullIfInvalid) {
                return null;
            }
            throw new InvalidConfigurationPropertyNameException(name, Collections.singletonList(Character.valueOf('.')));
        }
        Elements elements = new ElementsParser(name, '.', parserCapacity).parse();
        for (int i = 0; i < elements.getSize(); ++i) {
            if (elements.getType(i) != ElementType.NON_UNIFORM) continue;
            if (returnNullIfInvalid) {
                return null;
            }
            throw new InvalidConfigurationPropertyNameException(name, ConfigurationPropertyName.getInvalidChars(elements, i));
        }
        return elements;
    }

    private static List<Character> getInvalidChars(Elements elements, int index) {
        ArrayList<Character> invalidChars = new ArrayList<Character>();
        for (int charIndex = 0; charIndex < elements.getLength(index); ++charIndex) {
            char ch = elements.charAt(index, charIndex);
            if (ElementsParser.isValidChar(ch, charIndex)) continue;
            invalidChars.add(Character.valueOf(ch));
        }
        return invalidChars;
    }

    public static ConfigurationPropertyName adapt(CharSequence name, char separator) {
        return ConfigurationPropertyName.adapt(name, separator, null);
    }

    static ConfigurationPropertyName adapt(CharSequence name, char separator, Function<CharSequence, CharSequence> elementValueProcessor) {
        Assert.notNull((Object)name, (String)"Name must not be null");
        if (name.length() == 0) {
            return EMPTY;
        }
        Elements elements = new ElementsParser(name, separator).parse(elementValueProcessor);
        if (elements.getSize() == 0) {
            return EMPTY;
        }
        return new ConfigurationPropertyName(elements);
    }

    private static interface ElementCharPredicate {
        public boolean test(char var1, int var2);
    }

    private static enum ElementType {
        EMPTY(false),
        UNIFORM(false),
        DASHED(false),
        NON_UNIFORM(false),
        INDEXED(true),
        NUMERICALLY_INDEXED(true);

        private final boolean indexed;

        private ElementType(boolean indexed) {
            this.indexed = indexed;
        }

        public boolean isIndexed() {
            return this.indexed;
        }

        public boolean allowsFastEqualityCheck() {
            return this == UNIFORM || this == NUMERICALLY_INDEXED;
        }

        public boolean allowsDashIgnoringEqualityCheck() {
            return this.allowsFastEqualityCheck() || this == DASHED;
        }
    }

    private static class ElementsParser {
        private static final int DEFAULT_CAPACITY = 6;
        private final CharSequence source;
        private final char separator;
        private int size;
        private int[] start;
        private int[] end;
        private ElementType[] type;
        private CharSequence[] resolved;

        ElementsParser(CharSequence source, char separator) {
            this(source, separator, 6);
        }

        ElementsParser(CharSequence source, char separator, int capacity) {
            this.source = source;
            this.separator = separator;
            this.start = new int[capacity];
            this.end = new int[capacity];
            this.type = new ElementType[capacity];
        }

        Elements parse() {
            return this.parse(null);
        }

        Elements parse(Function<CharSequence, CharSequence> valueProcessor) {
            int length = this.source.length();
            int openBracketCount = 0;
            int start = 0;
            ElementType type = ElementType.EMPTY;
            for (int i = 0; i < length; ++i) {
                char ch = this.source.charAt(i);
                if (ch == '[') {
                    if (openBracketCount == 0) {
                        this.add(start, i, type, valueProcessor);
                        start = i + 1;
                        type = ElementType.NUMERICALLY_INDEXED;
                    }
                    ++openBracketCount;
                    continue;
                }
                if (ch == ']') {
                    if (--openBracketCount != 0) continue;
                    this.add(start, i, type, valueProcessor);
                    start = i + 1;
                    type = ElementType.EMPTY;
                    continue;
                }
                if (!type.isIndexed() && ch == this.separator) {
                    this.add(start, i, type, valueProcessor);
                    start = i + 1;
                    type = ElementType.EMPTY;
                    continue;
                }
                type = this.updateType(type, ch, i - start);
            }
            if (openBracketCount != 0) {
                type = ElementType.NON_UNIFORM;
            }
            this.add(start, length, type, valueProcessor);
            return new Elements(this.source, this.size, this.start, this.end, this.type, this.resolved);
        }

        private ElementType updateType(ElementType existingType, char ch, int index) {
            if (existingType.isIndexed()) {
                if (existingType == ElementType.NUMERICALLY_INDEXED && !ElementsParser.isNumeric(ch)) {
                    return ElementType.INDEXED;
                }
                return existingType;
            }
            if (existingType == ElementType.EMPTY && ElementsParser.isValidChar(ch, index)) {
                return index == 0 ? ElementType.UNIFORM : ElementType.NON_UNIFORM;
            }
            if (existingType == ElementType.UNIFORM && ch == '-') {
                return ElementType.DASHED;
            }
            if (!ElementsParser.isValidChar(ch, index)) {
                if (existingType == ElementType.EMPTY && !ElementsParser.isValidChar(Character.toLowerCase(ch), index)) {
                    return ElementType.EMPTY;
                }
                return ElementType.NON_UNIFORM;
            }
            return existingType;
        }

        private void add(int start, int end, ElementType type, Function<CharSequence, CharSequence> valueProcessor) {
            if (end - start < 1 || type == ElementType.EMPTY) {
                return;
            }
            if (this.start.length == this.size) {
                this.start = this.expand(this.start);
                this.end = this.expand(this.end);
                this.type = this.expand(this.type);
                this.resolved = this.expand(this.resolved);
            }
            if (valueProcessor != null) {
                CharSequence resolved;
                Elements resolvedElements;
                if (this.resolved == null) {
                    this.resolved = new CharSequence[this.start.length];
                }
                Assert.state(((resolvedElements = new ElementsParser(resolved = valueProcessor.apply(this.source.subSequence(start, end)), '.').parse()).getSize() == 1 ? 1 : 0) != 0, (String)"Resolved element must not contain multiple elements");
                this.resolved[this.size] = resolvedElements.get(0);
                type = resolvedElements.getType(0);
            }
            this.start[this.size] = start;
            this.end[this.size] = end;
            this.type[this.size] = type;
            ++this.size;
        }

        private int[] expand(int[] src) {
            int[] dest = new int[src.length + 6];
            System.arraycopy(src, 0, dest, 0, src.length);
            return dest;
        }

        private ElementType[] expand(ElementType[] src) {
            ElementType[] dest = new ElementType[src.length + 6];
            System.arraycopy(src, 0, dest, 0, src.length);
            return dest;
        }

        private CharSequence[] expand(CharSequence[] src) {
            if (src == null) {
                return null;
            }
            CharSequence[] dest = new CharSequence[src.length + 6];
            System.arraycopy(src, 0, dest, 0, src.length);
            return dest;
        }

        static boolean isValidChar(char ch, int index) {
            return ElementsParser.isAlpha(ch) || ElementsParser.isNumeric(ch) || index != 0 && ch == '-';
        }

        static boolean isAlphaNumeric(char ch) {
            return ElementsParser.isAlpha(ch) || ElementsParser.isNumeric(ch);
        }

        private static boolean isAlpha(char ch) {
            return ch >= 'a' && ch <= 'z';
        }

        private static boolean isNumeric(char ch) {
            return ch >= '0' && ch <= '9';
        }
    }

    private static class Elements {
        private static final int[] NO_POSITION = new int[0];
        private static final ElementType[] NO_TYPE = new ElementType[0];
        public static final Elements EMPTY = new Elements("", 0, NO_POSITION, NO_POSITION, NO_TYPE, null);
        private final CharSequence source;
        private final int size;
        private final int[] start;
        private final int[] end;
        private final ElementType[] type;
        private final CharSequence[] resolved;

        Elements(CharSequence source, int size, int[] start, int[] end, ElementType[] type, CharSequence[] resolved) {
            this.source = source;
            this.size = size;
            this.start = start;
            this.end = end;
            this.type = type;
            this.resolved = resolved;
        }

        Elements append(Elements additional) {
            int size = this.size + additional.size;
            ElementType[] type = new ElementType[size];
            System.arraycopy(this.type, 0, type, 0, this.size);
            System.arraycopy(additional.type, 0, type, this.size, additional.size);
            CharSequence[] resolved = this.newResolved(size);
            for (int i = 0; i < additional.size; ++i) {
                resolved[this.size + i] = additional.get(i);
            }
            return new Elements(this.source, size, this.start, this.end, type, resolved);
        }

        Elements chop(int size) {
            CharSequence[] resolved = this.newResolved(size);
            return new Elements(this.source, size, this.start, this.end, this.type, resolved);
        }

        Elements subElements(int offset) {
            int size = this.size - offset;
            CharSequence[] resolved = this.newResolved(size);
            int[] start = new int[size];
            System.arraycopy(this.start, offset, start, 0, size);
            int[] end = new int[size];
            System.arraycopy(this.end, offset, end, 0, size);
            ElementType[] type = new ElementType[size];
            System.arraycopy(this.type, offset, type, 0, size);
            return new Elements(this.source, size, start, end, type, resolved);
        }

        private CharSequence[] newResolved(int size) {
            CharSequence[] resolved = new CharSequence[size];
            if (this.resolved != null) {
                System.arraycopy(this.resolved, 0, resolved, 0, Math.min(size, this.size));
            }
            return resolved;
        }

        int getSize() {
            return this.size;
        }

        CharSequence get(int index) {
            if (this.resolved != null && this.resolved[index] != null) {
                return this.resolved[index];
            }
            int start = this.start[index];
            int end = this.end[index];
            return this.source.subSequence(start, end);
        }

        int getLength(int index) {
            if (this.resolved != null && this.resolved[index] != null) {
                return this.resolved[index].length();
            }
            int start = this.start[index];
            int end = this.end[index];
            return end - start;
        }

        char charAt(int index, int charIndex) {
            if (this.resolved != null && this.resolved[index] != null) {
                return this.resolved[index].charAt(charIndex);
            }
            int start = this.start[index];
            return this.source.charAt(start + charIndex);
        }

        ElementType getType(int index) {
            return this.type[index];
        }

        CharSequence getSource() {
            return this.source;
        }

        boolean canShortcutWithSource(ElementType requiredType) {
            return this.canShortcutWithSource(requiredType, requiredType);
        }

        boolean canShortcutWithSource(ElementType requiredType, ElementType alternativeType) {
            if (this.resolved != null) {
                return false;
            }
            for (int i = 0; i < this.size; ++i) {
                ElementType type = this.type[i];
                if (type != requiredType && type != alternativeType) {
                    return false;
                }
                if (i <= 0 || this.end[i - 1] + 1 == this.start[i]) continue;
                return false;
            }
            return true;
        }
    }

    public static enum Form {
        ORIGINAL,
        DASHED,
        UNIFORM;

    }
}

