/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  groovy.namespace.QName
 *  groovy.util.BuilderSupport
 *  groovy.util.IndentPrinter
 *  org.codehaus.groovy.runtime.StringGroovyMethods
 *  org.codehaus.groovy.vmplugin.v8.PluginDefaultGroovyMethods
 */
package groovy.xml;

import groovy.namespace.QName;
import groovy.util.BuilderSupport;
import groovy.util.IndentPrinter;
import groovy.xml.MarkupBuilderHelper;
import groovy.xml.markupsupport.DoubleQuoteFilter;
import groovy.xml.markupsupport.SingleQuoteFilter;
import groovy.xml.markupsupport.StandardXmlAttributeFilter;
import groovy.xml.markupsupport.StandardXmlFilter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import org.codehaus.groovy.runtime.StringGroovyMethods;
import org.codehaus.groovy.vmplugin.v8.PluginDefaultGroovyMethods;

public class MarkupBuilder
extends BuilderSupport {
    private IndentPrinter out;
    private boolean nospace;
    private int state;
    private boolean nodeIsEmpty = true;
    private boolean useDoubleQuotes = false;
    private boolean omitNullAttributes = false;
    private boolean omitEmptyAttributes = false;
    private boolean expandEmptyElements = false;
    private boolean escapeAttributes = true;
    private List<Function<Character, Optional<String>>> additionalFilters = null;

    public List<Function<Character, Optional<String>>> getAdditionalFilters() {
        return this.additionalFilters;
    }

    public void setAdditionalFilters(List<Function<Character, Optional<String>>> additionalFilters) {
        this.additionalFilters = additionalFilters;
    }

    public boolean isEscapeAttributes() {
        return this.escapeAttributes;
    }

    public void setEscapeAttributes(boolean escapeAttributes) {
        this.escapeAttributes = escapeAttributes;
    }

    public MarkupBuilder() {
        this(new IndentPrinter());
    }

    public MarkupBuilder(PrintWriter pw) {
        this(new IndentPrinter((Writer)pw));
    }

    public MarkupBuilder(Writer writer) {
        this(new IndentPrinter((Writer)new PrintWriter(writer)));
    }

    public MarkupBuilder(IndentPrinter out) {
        this.out = out;
    }

    public boolean getDoubleQuotes() {
        return this.useDoubleQuotes;
    }

    public void setDoubleQuotes(boolean useDoubleQuotes) {
        this.useDoubleQuotes = useDoubleQuotes;
    }

    public boolean isOmitNullAttributes() {
        return this.omitNullAttributes;
    }

    public void setOmitNullAttributes(boolean omitNullAttributes) {
        this.omitNullAttributes = omitNullAttributes;
    }

    public boolean isOmitEmptyAttributes() {
        return this.omitEmptyAttributes;
    }

    public void setOmitEmptyAttributes(boolean omitEmptyAttributes) {
        this.omitEmptyAttributes = omitEmptyAttributes;
    }

    public boolean isExpandEmptyElements() {
        return this.expandEmptyElements;
    }

    public void setExpandEmptyElements(boolean expandEmptyElements) {
        this.expandEmptyElements = expandEmptyElements;
    }

    protected IndentPrinter getPrinter() {
        return this.out;
    }

    protected void setParent(Object parent, Object child) {
    }

    public MarkupBuilderHelper getMkp() {
        return new MarkupBuilderHelper(this);
    }

    void pi(Map<String, Map<String, Object>> args) {
        Iterator<Map.Entry<String, Map<String, Object>>> iterator = args.entrySet().iterator();
        if (iterator.hasNext()) {
            Map.Entry<String, Map<String, Object>> mapEntry = iterator.next();
            this.createNode((Object)("?" + mapEntry.getKey()), mapEntry.getValue());
            this.state = 2;
            this.out.println("?>");
        }
    }

    void yield(String value, boolean escaping) {
        if (this.state == 1) {
            this.state = 2;
            this.nodeIsEmpty = false;
            this.out.print(">");
        }
        if (this.state == 0 || this.state == 2 || this.state == 3) {
            this.out.print(escaping ? this.escapeElementContent(value) : value);
        }
    }

    protected Object createNode(Object name) {
        Object theName = MarkupBuilder.getName(name);
        this.toState(1, theName);
        this.nodeIsEmpty = true;
        return theName;
    }

    protected Object createNode(Object name, Object value) {
        Object theName = MarkupBuilder.getName(name);
        if (value == null) {
            return this.createNode(theName);
        }
        this.toState(2, theName);
        this.nodeIsEmpty = false;
        this.out.print(">");
        this.out.print(this.escapeElementContent(value.toString()));
        return theName;
    }

    protected Object createNode(Object name, Map attributes, Object value) {
        Object theName = MarkupBuilder.getName(name);
        this.toState(1, theName);
        for (Map.Entry p : attributes.entrySet()) {
            boolean skipEmpty;
            Map.Entry entry = p;
            Object attributeValue = entry.getValue();
            boolean skipNull = attributeValue == null && this.omitNullAttributes;
            boolean bl = skipEmpty = attributeValue != null && this.omitEmptyAttributes && attributeValue.toString().length() == 0;
            if (skipNull || skipEmpty) continue;
            this.out.print(" ");
            this.print(entry.getKey().toString());
            this.out.print(this.useDoubleQuotes ? "=\"" : "='");
            this.print(attributeValue == null ? "" : (this.escapeAttributes ? this.escapeAttributeValue(attributeValue.toString()) : attributeValue.toString()));
            this.out.print(this.useDoubleQuotes ? "\"" : "'");
        }
        if (value != null) {
            this.yield(value.toString(), true);
        } else {
            this.nodeIsEmpty = true;
        }
        return theName;
    }

    protected Object createNode(Object name, Map attributes) {
        return this.createNode(name, attributes, null);
    }

    protected void nodeCompleted(Object parent, Object node) {
        this.toState(3, node);
        this.out.flush();
    }

    protected void print(Object node) {
        this.out.print(node == null ? "null" : node.toString());
    }

    protected Object getName(String methodName) {
        return super.getName(methodName);
    }

    private String escapeAttributeValue(String value) {
        return this.escapeXmlValue(value, true);
    }

    private String escapeElementContent(String value) {
        return this.escapeXmlValue(value, false);
    }

    private String escapeXmlValue(String value, boolean isAttrValue) {
        if (value == null) {
            throw new IllegalArgumentException();
        }
        ArrayList<Function<Character, Optional<String>>> transforms = new ArrayList<Function<Character, Optional<String>>>();
        transforms.add(new DefaultXmlEscapingFunction(isAttrValue, this.useDoubleQuotes));
        if (this.additionalFilters != null) {
            transforms.addAll(this.additionalFilters);
        }
        return StringGroovyMethods.collectReplacements((String)value, transforms);
    }

    private void toState(int next, Object name) {
        block0 : switch (this.state) {
            case 0: {
                switch (next) {
                    case 1: 
                    case 2: {
                        this.out.print("<");
                        this.print(name);
                        break block0;
                    }
                    case 3: {
                        throw new Error();
                    }
                }
                break;
            }
            case 1: {
                switch (next) {
                    case 1: 
                    case 2: {
                        this.out.print(">");
                        if (this.nospace) {
                            this.nospace = false;
                        } else {
                            this.out.println();
                            this.out.incrementIndent();
                            this.out.printIndent();
                        }
                        this.out.print("<");
                        this.print(name);
                        break;
                    }
                    case 3: {
                        if (!this.nodeIsEmpty) break;
                        if (this.expandEmptyElements) {
                            this.out.print("></");
                            this.print(name);
                            this.out.print(">");
                            break;
                        }
                        this.out.print(" />");
                    }
                }
                break;
            }
            case 2: {
                switch (next) {
                    case 1: 
                    case 2: {
                        if (!this.nodeIsEmpty) {
                            this.out.println();
                            this.out.incrementIndent();
                            this.out.printIndent();
                        }
                        this.out.print("<");
                        this.print(name);
                        break;
                    }
                    case 3: {
                        this.out.print("</");
                        this.print(name);
                        this.out.print(">");
                    }
                }
                break;
            }
            case 3: {
                switch (next) {
                    case 1: 
                    case 2: {
                        if (this.nospace) {
                            this.nospace = false;
                        } else {
                            this.out.println();
                            this.out.printIndent();
                        }
                        this.out.print("<");
                        this.print(name);
                        break block0;
                    }
                    case 3: {
                        if (this.nospace) {
                            this.nospace = false;
                        } else {
                            this.out.println();
                            this.out.decrementIndent();
                            this.out.printIndent();
                        }
                        this.out.print("</");
                        this.print(name);
                        this.out.print(">");
                    }
                }
            }
        }
        this.state = next;
    }

    private static Object getName(Object name) {
        if (name instanceof QName) {
            return ((QName)name).getQualifiedName();
        }
        return name;
    }

    public static class DefaultXmlEscapingFunction
    implements Function<Character, Optional<String>> {
        private final boolean isAttrValue;
        private final Function<Character, Optional<String>> stdFilter = new StandardXmlFilter();
        private final Function<Character, Optional<String>> attrFilter = new StandardXmlAttributeFilter();
        private final Function<Character, Optional<String>> quoteFilter;

        public DefaultXmlEscapingFunction(boolean isAttrValue, boolean useDoubleQuotes) {
            this.isAttrValue = isAttrValue;
            this.quoteFilter = useDoubleQuotes ? new DoubleQuoteFilter() : new SingleQuoteFilter();
        }

        @Override
        public Optional<String> apply(Character ch) {
            return PluginDefaultGroovyMethods.orOptional(this.stdFilter.apply(ch), () -> {
                if (this.isAttrValue) {
                    return PluginDefaultGroovyMethods.orOptional(this.attrFilter.apply(ch), () -> this.quoteFilter.apply(ch));
                }
                return Optional.empty();
            });
        }
    }

    public static enum CharFilter {
        XML_STRICT,
        XML_ALL,
        NONE;

    }
}

