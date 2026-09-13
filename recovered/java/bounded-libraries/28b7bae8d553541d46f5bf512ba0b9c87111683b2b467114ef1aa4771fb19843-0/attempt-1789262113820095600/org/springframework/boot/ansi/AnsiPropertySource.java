/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.env.PropertySource
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot.ansi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;
import java.util.function.IntFunction;
import org.springframework.boot.ansi.Ansi8BitColor;
import org.springframework.boot.ansi.AnsiBackground;
import org.springframework.boot.ansi.AnsiColor;
import org.springframework.boot.ansi.AnsiElement;
import org.springframework.boot.ansi.AnsiOutput;
import org.springframework.boot.ansi.AnsiStyle;
import org.springframework.core.env.PropertySource;
import org.springframework.util.StringUtils;

public class AnsiPropertySource
extends PropertySource<AnsiElement> {
    private static final Iterable<Mapping> MAPPINGS;
    private final boolean encode;

    public AnsiPropertySource(String name, boolean encode) {
        super(name);
        this.encode = encode;
    }

    public Object getProperty(String name) {
        if (StringUtils.hasLength((String)name)) {
            for (Mapping mapping : MAPPINGS) {
                String postfix;
                AnsiElement element;
                String prefix = mapping.getPrefix();
                if (!name.startsWith(prefix) || (element = mapping.getElement(postfix = name.substring(prefix.length()))) == null) continue;
                return this.encode ? AnsiOutput.encode(element) : element;
            }
        }
        return null;
    }

    static {
        ArrayList<Mapping> mappings = new ArrayList<Mapping>();
        mappings.add(new EnumMapping<AnsiStyle>("AnsiStyle.", AnsiStyle.class));
        mappings.add(new EnumMapping<AnsiColor>("AnsiColor.", AnsiColor.class));
        mappings.add(new Ansi8BitColorMapping("AnsiColor.", Ansi8BitColor::foreground));
        mappings.add(new EnumMapping<AnsiBackground>("AnsiBackground.", AnsiBackground.class));
        mappings.add(new Ansi8BitColorMapping("AnsiBackground.", Ansi8BitColor::background));
        mappings.add(new EnumMapping<AnsiStyle>("Ansi.", AnsiStyle.class));
        mappings.add(new EnumMapping<AnsiColor>("Ansi.", AnsiColor.class));
        mappings.add(new EnumMapping<AnsiBackground>("Ansi.BG_", AnsiBackground.class));
        MAPPINGS = Collections.unmodifiableList(mappings);
    }

    private static class Ansi8BitColorMapping
    extends Mapping {
        private final IntFunction<Ansi8BitColor> factory;

        Ansi8BitColorMapping(String prefix, IntFunction<Ansi8BitColor> factory) {
            super(prefix);
            this.factory = factory;
        }

        @Override
        AnsiElement getElement(String postfix) {
            if (this.containsOnlyDigits(postfix)) {
                try {
                    return this.factory.apply(Integer.parseInt(postfix));
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    // empty catch block
                }
            }
            return null;
        }

        private boolean containsOnlyDigits(String postfix) {
            for (int i = 0; i < postfix.length(); ++i) {
                if (Character.isDigit(postfix.charAt(i))) continue;
                return false;
            }
            return !postfix.isEmpty();
        }
    }

    private static class EnumMapping<E extends Enum<E>>
    extends Mapping {
        private final Set<E> enums;

        EnumMapping(String prefix, Class<E> enumType) {
            super(prefix);
            this.enums = EnumSet.allOf(enumType);
        }

        @Override
        AnsiElement getElement(String postfix) {
            for (Enum candidate : this.enums) {
                if (!candidate.name().equals(postfix)) continue;
                return (AnsiElement)((Object)candidate);
            }
            return null;
        }
    }

    private static abstract class Mapping {
        private final String prefix;

        Mapping(String prefix) {
            this.prefix = prefix;
        }

        String getPrefix() {
            return this.prefix;
        }

        abstract AnsiElement getElement(String var1);
    }
}

